#!/usr/bin/env python3
"""
Truncate application tables and load MySQL-exported CSVs into Docker Postgres (schillerdb).

Prerequisites:
  - docker compose up (schiller-db healthy)
  - CSVs from mysql_dump_to_csv.py (default: scripts/csv_export_2026/)
  - Flyway V23+ applied (repair_master legacy shape) — script can apply V23 DDL if needed

Usage:
  python3 import_csv_to_postgres.py
  python3 import_csv_to_postgres.py --csv-dir ./csv_export_2026 --dry-run
"""

from __future__ import annotations

import argparse
import csv
import io
import re
import subprocess
import sys
from pathlib import Path

SCRIPT_DIR = Path(__file__).resolve().parent
DEFAULT_CSV_DIR = SCRIPT_DIR / "csv_export_2026"
CONTAINER = "schiller-db"
DB_USER = "schiller"
DB_NAME = "schillerdb"

TRUNCATE_TABLES = [
    "jobsheet",
    "service_master",
    "repair_master",
    "birmaster",
    "nonsaleablemaster",
    "prfobmaster",
    "callregister",
    "callregister_demo",
    "mail_id_entry",
    "email_config",
    "employee",
    "dealer",
    "branch",
    "model",
    "productmaster",
    "region",
    "dropdownmaster",
    "dropdown_master",
    "companymaster",
    "partnumber_entry",
    "sparepart_master",
    "sparemaster",
    "pendingact_master",
    "supplier",
]


def run_psql(sql: str, stdin: bytes | None = None, dry_run: bool = False) -> None:
    if dry_run:
        print(f"-- psql: {sql[:200]}{'...' if len(sql) > 200 else ''}")
        if stdin:
            print(f"--   (+ {len(stdin)} bytes stdin)")
        return
    cmd = [
        "docker",
        "exec",
        "-i",
        CONTAINER,
        "psql",
        "-U",
        DB_USER,
        "-d",
        DB_NAME,
        "-v",
        "ON_ERROR_STOP=1",
    ]
    payload = sql.encode("utf-8") if stdin is None else sql.encode("utf-8") + stdin
    proc = subprocess.run(cmd, input=payload, capture_output=True)
    if proc.returncode != 0:
        err = proc.stderr.decode(errors="replace")
        out = proc.stdout.decode(errors="replace")
        raise RuntimeError(f"psql failed:\n{err}\n{out}")


def copy_csv(table: str, columns: list[str], csv_text: str, dry_run: bool, *, empty_as_null: bool = True) -> None:
    col_list = ", ".join(f'"{c}"' if c == "current_date" else c for c in columns)
    null_opt = ", NULL ''" if empty_as_null else ""
    sql = f"COPY {table} ({col_list}) FROM STDIN WITH (FORMAT csv, HEADER true{null_opt});\n"
    if dry_run:
        lines = csv_text.strip().splitlines()
        print(f"  COPY {table}: {max(0, len(lines) - 1)} rows")
        return
    run_psql(sql, stdin=csv_text.encode("utf-8"))


def setval(table: str, column: str, dry_run: bool) -> None:
    sql = (
        f"SELECT setval(pg_get_serial_sequence('{table}', '{column}'), "
        f"COALESCE((SELECT MAX({column}) FROM {table}), 1), true);\n"
    )
    run_psql(sql, dry_run=dry_run)


def empty(val: str | None) -> bool:
    return val is None or str(val).strip() == ""


def clean_date(val: str | None) -> str:
    if empty(val) or str(val).strip().lower() in ("na", "null", "none"):
        return ""
    s = str(val).strip()
    if re.fullmatch(r"\d{4}-\d{2}-\d{2}", s):
        return s
    m = re.fullmatch(r"(\d{1,2})-(\d{1,2})-(\d{4})", s)
    if m:
        d, mo, y = m.groups()
        return f"{y}-{mo.zfill(2)}-{d.zfill(2)}"
    m = re.fullmatch(r"(\d{1,2})/(\d{1,2})/(\d{4})", s)
    if m:
        d, mo, y = m.groups()
        return f"{y}-{mo.zfill(2)}-{d.zfill(2)}"
    return ""


def clean_int(val: str | None) -> str:
    if empty(val):
        return ""
    try:
        return str(int(float(str(val).strip())))
    except ValueError:
        return ""


def ensure_repair_master_schema(dry_run: bool) -> None:
    check = (
        "SELECT 1 FROM information_schema.columns "
        "WHERE table_schema='public' AND table_name='repair_master' AND column_name='category';\n"
    )
    if dry_run:
        print("-- ensure repair_master legacy schema (V23)")
        return
    cmd = [
        "docker",
        "exec",
        CONTAINER,
        "psql",
        "-U",
        DB_USER,
        "-d",
        DB_NAME,
        "-tAc",
        "SELECT 1 FROM information_schema.columns "
        "WHERE table_schema='public' AND table_name='repair_master' AND column_name='category'",
    ]
    proc = subprocess.run(cmd, capture_output=True, text=True)
    if proc.stdout.strip() == "1":
        return
    ddl_path = (
        SCRIPT_DIR.parent
        / "src/main/resources/db/migration/V23__repair_master_legacy_shape.sql"
    )
    if not ddl_path.is_file():
        raise RuntimeError("repair_master schema mismatch and V23 migration file not found")
    print("Applying V23 repair_master DDL...")
    ddl = ddl_path.read_text(encoding="utf-8")
    run_psql(ddl, dry_run=False)


def load_product_lookup(csv_dir: Path) -> dict[str, str]:
    path = csv_dir / "productmaster.csv"
    lookup: dict[str, str] = {}
    with path.open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            lookup[row["prod_id"]] = row["prod_name"]
    return lookup


def transform_region(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(["id", "name"])
    with (csv_dir / "region.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            w.writerow([row["region_id"], row["region_name"]])
    return out.getvalue()


def transform_branch(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(["id", "branch_name", "region_id"])
    with (csv_dir / "branch.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            w.writerow([row["branchid"], row["branchname"], row["regionid"]])
    return out.getvalue()


def transform_productmaster(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(["product_id", "division", "division_name"])
    with (csv_dir / "productmaster.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            w.writerow([row["prod_id"], row["prod_name"], row["prod_description"]])
    return out.getvalue()


def transform_model(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(["model_id", "product_id", "model_name", "model_desc", "sup_name"])
    with (csv_dir / "model.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            w.writerow(
                [row["model_id"], row["product_id"], row["model_name"], row["model_desc"], row["sup_name"]]
            )
    return out.getvalue()


def transform_dealer(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(["dealer_id", "dealer_name", "region_id"])
    with (csv_dir / "dealermaster.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            w.writerow([row["dealer_id"], row["dealer_name"], row["dealer_region_id"]])
    return out.getvalue()


def transform_employee(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(
        [
            "emp_id",
            "emp_name",
            "username",
            "password",
            "role",
            "division",
            "email",
            "mobile",
            "active",
            "address",
            "department",
            "branch_id",
        ]
    )
    seen_usernames: dict[str, int] = {}
    with (csv_dir / "emploeemaster.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            email = (row.get("emp_email") or "").strip()
            username = email or f"user_{row['emp_id']}"
            key = username.lower()
            if key in seen_usernames:
                username = f"{username}_{row['emp_id']}"
            else:
                seen_usernames[key] = 1
            active = "true" if (row.get("emp_active") or "").strip().lower() == "yes" else "false"
            w.writerow(
                [
                    row["emp_id"],
                    row["emp_name"],
                    username,
                    row["emp_password"],
                    row["emp_role"],
                    row.get("emp_division") or "",
                    email,
                    row.get("emp_mobile") or "",
                    active,
                    row.get("emp_address") or "",
                    row.get("emp_dept") or "",
                    row.get("emp_branch") or "",
                ]
            )
    return out.getvalue()


def transform_companymaster(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(["company_id", "company_name", "address", "phone", "email"])
    with (csv_dir / "companymaster.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            w.writerow(
                [
                    row["comp_id"],
                    row["comp_name"],
                    row["comp_address"],
                    row["comp_phone"],
                    row["comp_email"],
                ]
            )
    return out.getvalue()


def transform_email_config(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(["email_id", "email_address", "email_password", "smtp_server", "smtp_port", "email_type"])
    with (csv_dir / "email.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            port = clean_int(row.get("port_no"))
            w.writerow(
                [
                    row["id"],
                    row["mailid"],
                    row["password_f"],
                    row["hosing_id"],
                    port,
                    row.get("direction") or row.get("msg_body") or "",
                ]
            )
    return out.getvalue()


def transform_service_master(csv_dir: Path, product_lookup: dict[str, str], emp_ids: set[str]) -> str:
    cols = [
        "id",
        "sc_ref_no",
        "entry_date",
        "division",
        "sc_engineer_id",
        "ra_engineer_id",
        "frn_no",
        "frn_date",
        "ser_comm_inward_date",
        "ser_centre_received_date",
        "stk_cust",
        "region",
        "branch",
        "dealer_name",
        "cust_name",
        "supplier_name",
        "product_model",
        "model_config",
        "unit_sl_no",
        "unit_status",
        "dod",
        "mod_brd_name",
        "def_mod_brd_name",
        "def_type",
        "type_of_acc",
        "def_part_sn",
        "def_gir_no",
        "rep_type",
        "rep_gir_no",
        "def_unit_gir_no",
        "field_remarks",
        "technical_remarks",
        "components_used_for_repair",
        "final_remarks",
        "comrcl_dtl_inv_rmrk",
        "type_of_work",
        "ship_dt_frm_ser_cntr",
        "repaired_brd_stk_date",
        "ship_date_from_commercial",
        "dc_no",
        "disp_adv_no",
        "repaired_brd_adv_no",
        "part_number",
        "compatible_models",
        "destination",
        "cost",
        "report_type",
        "status",
        "repair_status",
    ]
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(cols)

    def fk_emp(val: str | None) -> str:
        v = clean_int(val)
        return v if v and v in emp_ids else ""

    with (csv_dir / "service_master.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            div = (row.get("division_name") or "").strip()
            if not div:
                div = product_lookup.get((row.get("division") or "").strip(), "")
            cost = (row.get("cost") or "").strip()
            if cost.lower() in ("", "null", "na"):
                cost = ""
            w.writerow(
                [
                    row["ser_id"],
                    row["sc_ref_no"] or f"SCH-{row['ser_id']}",
                    clean_date(row.get("entry_date")),
                    div,
                    fk_emp(row.get("sc_engnr")),
                    fk_emp(row.get("ra_engnr")),
                    row.get("frn_no") or "",
                    clean_date(row.get("frn_date")),
                    clean_date(row.get("ser_comm_inward_date")),
                    clean_date(row.get("ser_centre_received_date")),
                    row.get("stk_cust") or "",
                    row.get("region") or "",
                    row.get("branch") or "",
                    row.get("dealer_name") or "",
                    row.get("cust_name") or "",
                    row.get("supplier_name") or "",
                    row.get("product_model") or "",
                    row.get("model_config") or "",
                    row.get("unit_slno") or "",
                    row.get("unit_status") or "",
                    clean_date(row.get("dod")),
                    row.get("mod_brd_name") or "",
                    row.get("def_mod_brd_name") or "",
                    row.get("def_type") or "",
                    row.get("type_of_acc") or "",
                    row.get("def_part_sn") or "",
                    row.get("def_gir_no") or "",
                    row.get("rep_type") or "",
                    row.get("rep_gir_no") or "",
                    row.get("def_unit_gir_no") or "",
                    row.get("field_remarks") or "",
                    row.get("technical_remarks") or "",
                    row.get("components_usedfor_repair") or "",
                    row.get("final_remarks") or "",
                    row.get("comrcl_dtl_inv_rmrk") or "",
                    row.get("type_of_work") or "",
                    clean_date(row.get("ship_dt_frm_ser_cntr")),
                    clean_date(row.get("repaired_brd_stk_date")),
                    clean_date(row.get("ship_date_from_commercial")),
                    row.get("dc_no") or "",
                    row.get("disp_adv_no") or "",
                    row.get("repaired_brd_adv_no") or "",
                    row.get("part_number") or "",
                    row.get("compatible_models") or "",
                    row.get("destination") or "",
                    cost,
                    row.get("report_type") or "",
                    row.get("status") or "",
                    row.get("repair_status") or "",
                ]
            )
    return out.getvalue()


def transform_repair_master(csv_dir: Path) -> str:
    out = io.StringIO()
    w = csv.writer(out)
    w.writerow(
        [
            "id",
            "entry_date",
            "division",
            "sc_ref_no",
            "def_gir_no",
            "category",
            "model",
            "def_brd_mod_name",
            "unit_status",
            "tech_remarks",
            "comp_used_to_repair",
            "finished_date",
            "repaired_by",
            "final_remarks",
            "status",
            "no_of_days",
            "ser_id",
            "current_date",
        ]
    )
    with (csv_dir / "repair_master.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            w.writerow(
                [
                    row["id"],
                    clean_date(row.get("entry_date")),
                    row.get("division") or "",
                    row.get("sc_ref_no") or "",
                    row.get("def_gir_no") or "",
                    row.get("category") or "",
                    row.get("model") or "",
                    row.get("def_brd_mod_name") or "",
                    row.get("unit_status") or "",
                    row.get("tech_remarks") or "",
                    row.get("comp_used_to_repair") or "",
                    clean_date(row.get("finished_date")),
                    row.get("repaired_by") or "",
                    row.get("final_remarks") or "",
                    row.get("status") or "",
                    clean_int(row.get("no_of_days")),
                    clean_int(row.get("ser_id")),
                    row.get("currentDate") or "",
                ]
            )
    return out.getvalue()


def read_csv_as_is(path: Path) -> str:
    return path.read_text(encoding="utf-8")


def transform_csv_fill_defaults(
    path: Path,
    fill_cols: list[str],
    date_cols: list[str] | None = None,
) -> str:
    """Empty strings become NULL under COPY NULL ''; NOT NULL columns need ''."""
    out = io.StringIO()
    with path.open(encoding="utf-8", newline="") as f:
        reader = csv.DictReader(f)
        if not reader.fieldnames:
            return ""
        writer = csv.DictWriter(out, fieldnames=reader.fieldnames, lineterminator="\n")
        writer.writeheader()
        for row in reader:
            for col in fill_cols:
                if empty(row.get(col)):
                    row[col] = ""
            if date_cols:
                for col in date_cols:
                    if col in row:
                        row[col] = clean_date(row.get(col))
            writer.writerow(row)
    return out.getvalue()


def transform_birmaster(csv_dir: Path) -> str:
    date_cols = [
        "entry_date",
        "fqc_in_date",
        "invoice_date",
        "ts_team_ver_date",
        "ps_team_ver_date",
        "closed_date",
        "cnr_relese_date",
        "approved_date",
        "unit_in_date",
    ]
    fill_cols = [
        "acces_chng_remark",
        "hrdwr_chang_remark",
        "sftwr_chng_remark",
        "bir_ref_no",
        "supplier",
        "sc_engg",
        "ps_engg",
        "cnr_tec_ref_num",
    ]
    return transform_csv_fill_defaults(
        csv_dir / "birmaster.csv",
        fill_cols + ["current_date"],
        date_cols,
    )


def transform_nonsaleablemaster(csv_dir: Path) -> str:
    date_cols = [
        "entry_date",
        "fqc_in_date",
        "replace_ship_date",
        "defect_unit_recived_date",
        "sc_inward_date",
        "tent_date_ship_date",
        "ship_date_fqc",
    ]
    return transform_csv_fill_defaults(csv_dir / "nonsaleablemaster.csv", ["current_date"], date_cols)


def transform_prfobmaster(csv_dir: Path) -> str:
    date_cols = ["entry_date", "raised_date", "received_date", "executed_date"]
    return transform_csv_fill_defaults(
        csv_dir / "prfobmaster.csv",
        ["part_type", "part_description"],
        date_cols,
    )


def csv_header(path: Path) -> list[str]:
    with path.open(encoding="utf-8", newline="") as f:
        return next(csv.reader(f))


def collect_emp_ids(csv_dir: Path) -> set[str]:
    ids: set[str] = set()
    with (csv_dir / "emploeemaster.csv").open(encoding="utf-8", newline="") as f:
        for row in csv.DictReader(f):
            ids.add(str(row["emp_id"]))
    return ids


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--csv-dir", type=Path, default=DEFAULT_CSV_DIR, help="Directory with table CSV files")
    ap.add_argument("--dry-run", action="store_true", help="Print actions without loading data")
    args = ap.parse_args()

    csv_dir = args.csv_dir.resolve()
    if not csv_dir.is_dir():
        print(f"CSV directory not found: {csv_dir}", file=sys.stderr)
        sys.exit(1)

    required = [
        "region.csv",
        "branch.csv",
        "productmaster.csv",
        "model.csv",
        "dealermaster.csv",
        "emploeemaster.csv",
        "service_master.csv",
    ]
    missing = [n for n in required if not (csv_dir / n).is_file()]
    if missing:
        print(f"Missing required CSV files: {', '.join(missing)}", file=sys.stderr)
        sys.exit(1)

    print(f"CSV source: {csv_dir}")
    ensure_repair_master_schema(args.dry_run)

    truncate_sql = (
        "BEGIN;\n"
        f"TRUNCATE TABLE {', '.join(TRUNCATE_TABLES)} RESTART IDENTITY CASCADE;\n"
        "COMMIT;\n"
    )
    print("Truncating application tables...")
    run_psql(truncate_sql, dry_run=args.dry_run)

    product_lookup = load_product_lookup(csv_dir)
    emp_ids = collect_emp_ids(csv_dir)

    loads: list[tuple[str, list[str], str]] = [
        ("region", ["id", "name"], transform_region(csv_dir)),
        ("productmaster", ["product_id", "division", "division_name"], transform_productmaster(csv_dir)),
        ("branch", ["id", "branch_name", "region_id"], transform_branch(csv_dir)),
        ("dealer", ["dealer_id", "dealer_name", "region_id"], transform_dealer(csv_dir)),
        ("model", ["model_id", "product_id", "model_name", "model_desc", "sup_name"], transform_model(csv_dir)),
        ("employee", ["emp_id", "emp_name", "username", "password", "role", "division", "email", "mobile", "active", "address", "department", "branch_id"], transform_employee(csv_dir)),
        ("dropdownmaster", ["dd_id", "ddname", "ddvalue"], read_csv_as_is(csv_dir / "dropdownmaster.csv")),
        ("companymaster", ["company_id", "company_name", "address", "phone", "email"], transform_companymaster(csv_dir)),
        ("mail_id_entry", ["mail_id_entry_id", "mail_id", "report_type", "temp1", "temp2"], read_csv_as_is(csv_dir / "mail_id_entry.csv")),
        ("email_config", ["email_id", "email_address", "email_password", "smtp_server", "smtp_port", "email_type"], transform_email_config(csv_dir)),
        ("repair_master", ["id", "entry_date", "division", "sc_ref_no", "def_gir_no", "category", "model", "def_brd_mod_name", "unit_status", "tech_remarks", "comp_used_to_repair", "finished_date", "repaired_by", "final_remarks", "status", "no_of_days", "ser_id", "current_date"], transform_repair_master(csv_dir)),
        ("callregister_demo", csv_header(csv_dir / "callregister_demo.csv"), read_csv_as_is(csv_dir / "callregister_demo.csv")),
    ]

    sm_cols = [
        "id", "sc_ref_no", "entry_date", "division", "sc_engineer_id", "ra_engineer_id",
        "frn_no", "frn_date", "ser_comm_inward_date", "ser_centre_received_date", "stk_cust",
        "region", "branch", "dealer_name", "cust_name", "supplier_name", "product_model",
        "model_config", "unit_sl_no", "unit_status", "dod", "mod_brd_name", "def_mod_brd_name",
        "def_type", "type_of_acc", "def_part_sn", "def_gir_no", "rep_type", "rep_gir_no",
        "def_unit_gir_no", "field_remarks", "technical_remarks", "components_used_for_repair",
        "final_remarks", "comrcl_dtl_inv_rmrk", "type_of_work", "ship_dt_frm_ser_cntr",
        "repaired_brd_stk_date", "ship_date_from_commercial", "dc_no", "disp_adv_no",
        "repaired_brd_adv_no", "part_number", "compatible_models", "destination", "cost",
        "report_type", "status", "repair_status",
    ]
    sm_data = transform_service_master(csv_dir, product_lookup, emp_ids)

    loads.extend(
        [
            ("service_master", sm_cols, sm_data),
            ("birmaster", csv_header(csv_dir / "birmaster.csv"), transform_birmaster(csv_dir)),
            ("nonsaleablemaster", csv_header(csv_dir / "nonsaleablemaster.csv"), transform_nonsaleablemaster(csv_dir)),
            ("prfobmaster", csv_header(csv_dir / "prfobmaster.csv"), transform_prfobmaster(csv_dir)),
        ]
    )

    sequences = [
        ("region", "id"),
        ("branch", "id"),
        ("dealer", "dealer_id"),
        ("productmaster", "product_id"),
        ("model", "model_id"),
        ("employee", "emp_id"),
        ("dropdownmaster", "dd_id"),
        ("companymaster", "company_id"),
        ("mail_id_entry", "mail_id_entry_id"),
        ("email_config", "email_id"),
        ("service_master", "id"),
        ("repair_master", "id"),
        ("birmaster", "id"),
        ("nonsaleablemaster", "id"),
        ("prfobmaster", "id"),
        ("callregister_demo", "id"),
    ]

    for table, columns, data in loads:
        print(f"Loading {table}...")
        copy_csv(table, columns, data, args.dry_run)

    if not args.dry_run:
        print("Updating sequences...")
        for table, col in sequences:
            setval(table, col, dry_run=False)

        print("\nRow counts:")
        count_sql = (
            "SELECT 'employee', COUNT(*) FROM employee UNION ALL "
            "SELECT 'service_master', COUNT(*) FROM service_master UNION ALL "
            "SELECT 'birmaster', COUNT(*) FROM birmaster UNION ALL "
            "SELECT 'repair_master', COUNT(*) FROM repair_master;\n"
        )
        run_psql(count_sql, dry_run=False)

    print("Done." if not args.dry_run else "Dry run complete.")


if __name__ == "__main__":
    main()
