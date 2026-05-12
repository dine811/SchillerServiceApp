#!/usr/bin/env python3
"""
Convert MySQL mysqldump INSERT lines for birmaster, nonsaleablemaster, prfobmaster
to PostgreSQL, matching Flyway schema (quoted \"current_date\", etc.).

The Dump20260314.sql file contains TWO databases:
  - schillerindiaservices_2026 — smaller 2026 sample (default)
  - schillerindiaservices_2025 — larger history (more PRF/OB rows)

Usage:
  python3 import_mysql_dump_prfob_bir_nonsaleable.py /path/to/Dump20260314.sql --db 2026 \\
    | psql -h localhost -U schiller -d schillerdb -v ON_ERROR_STOP=1 -f -

  # Full PRF/OB history (and more BIR / nonsaleable rows):
  python3 ... --db 2025 | psql ...

Options:
  --db {2026,2025}   Which database block in the dump to use (default: 2026)
  --no-truncate      Do not emit TRUNCATE ... RESTART IDENTITY (append-only; may duplicate PKs)
"""

from __future__ import annotations

import argparse
import re
import sys

USE_2026 = "USE `schillerindiaservices_2026`;"
USE_2025 = "USE `schillerindiaservices_2025`;"

BIRMASTER_COLS = (
    "id, division, entry_date, fqc_in_date, model, configuration, received_qty, "
    "unit_serial_no, invoice_no, invoice_date, softwr_changes, softwr_version, "
    "hardwr_changes, hardware_details, accesory_changes, accesory_details, "
    "user_manual_update, service_manual_update, fqc_remarks, cnr_ref_no, "
    "ts_team_ver_date, ps_team_ver_date, final_status, closed_date, "
    "acces_chng_remark, hrdwr_chang_remark, sftwr_chng_remark, cnr_relese_date, "
    "bir_ref_no, supplier, sc_engg, ps_engg, approved_date, cnr_tec_ref_num, "
    'unit_in_date, tech_remarks, "current_date"'
)

NONSALEABLE_COLS = (
    "id, division, entry_date, unit_details, fqc_in_date, region, branch, engineer, "
    "dealer, supplier, model, model_s_n, unit_config, cust_name, reported_problm, "
    "replaced_unit_s_n, replace_ship_date, defect_unit_recived_date, fqc_observation, "
    "sc_inward_date, sc_engg, sc_observation, required_parts, root_cause, action_plan, "
    "tent_date_ship_date, ship_date_fqc, fqc_final_remark, final_status, "
    '"current_date"'
)

PRFOB_COLS = (
    "id, division, entry_date, sc_engg, work_type, raised_date, received_date, region, "
    "branch, engineer, dealer, model, supplier, warrenty_status, prfob_ref_no, crm_ref_no, "
    "remarks, status_type, executed_date, part_type, part_description, currentdate_time, "
    "rec_dt_frm_sc"
)


def extract_section(text: str, which: str) -> str:
    if which == "2026":
        a = text.find(USE_2026)
        if a == -1:
            raise ValueError("Could not find schillerindiaservices_2026 section")
        b = text.find(USE_2025, a)
        return text[a:b] if b != -1 else text[a:]
    if which == "2025":
        a = text.find(USE_2025)
        if a == -1:
            raise ValueError("Could not find schillerindiaservices_2025 section")
        return text[a:]
    raise ValueError(which)


def _decode_mysql_string_content(s: str, start: int) -> tuple[int, str]:
    """
    Read a MySQL single-quoted literal starting at s[start]=='\\''.
    Returns (index_after_closing_quote, postgres_literal_with_quotes).
    """
    assert start < len(s) and s[start] == "'"
    i = start + 1
    inner: list[str] = []
    while i < len(s):
        c = s[i]
        if c == "\\" and i + 1 < len(s):
            nxt = s[i + 1]
            if nxt == "'":
                inner.append("'")
                i += 2
                continue
            if nxt == "\\":
                inner.append("\\")
                i += 2
                continue
            if nxt == "n":
                inner.append("\n")
                i += 2
                continue
            if nxt == "r":
                inner.append("\r")
                i += 2
                continue
            if nxt == "t":
                inner.append("\t")
                i += 2
                continue
            inner.append(nxt)
            i += 2
            continue
        if c == "'" and i + 1 < len(s) and s[i + 1] == "'":
            inner.append("'")
            i += 2
            continue
        if c == "'":
            content = "".join(inner)
            pg = "'" + content.replace("'", "''") + "'"
            return i + 1, pg
        inner.append(c)
        i += 1
    raise ValueError("Unterminated MySQL string literal")


def rewrite_mysql_values_fragment(fragment: str) -> str:
    """
    fragment is everything inside INSERT ... VALUES <here> (starts with '(').
    Re-writes only single-quoted string literals for PostgreSQL.
    """
    out: list[str] = []
    i = 0
    n = len(fragment)
    while i < n:
        if fragment[i] == "'":
            j, pg_lit = _decode_mysql_string_content(fragment, i)
            out.append(pg_lit)
            i = j
        else:
            out.append(fragment[i])
            i += 1
    return "".join(out)


def collect_insert_lines(section: str, table: str) -> list[str]:
    prefix = f"INSERT INTO `{table}` VALUES "
    lines: list[str] = []
    for raw in section.splitlines():
        line = raw.strip()
        if line.startswith(prefix):
            lines.append(line)
    return lines


def mysql_insert_to_pg(table: str, cols_sql: str, mysql_line: str) -> str:
    prefix = f"INSERT INTO `{table}` VALUES "
    if not mysql_line.startswith(prefix):
        raise ValueError(f"Unexpected line for {table}: {mysql_line[:80]}...")
    rest = mysql_line[len(prefix) :].rstrip().rstrip(";")
    fixed = rewrite_mysql_values_fragment(rest)
    return f"INSERT INTO {table} ({cols_sql}) VALUES {fixed};"


def emit_setval(table: str) -> str:
    return (
        f"SELECT setval(pg_get_serial_sequence('{table}', 'id'), "
        f"COALESCE((SELECT MAX(id) FROM {table}), 1), true);"
    )


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("dump_path", help="Path to MySQL .sql dump (e.g. Dump20260314.sql)")
    ap.add_argument("--db", choices=("2026", "2025"), default="2026", help="Database block inside dump")
    ap.add_argument("--no-truncate", action="store_true", help="Skip TRUNCATE (avoid PK conflicts manually)")
    args = ap.parse_args()

    with open(args.dump_path, encoding="utf-8", errors="replace") as f:
        text = f.read()

    section = extract_section(text, args.db)

    bir_lines = collect_insert_lines(section, "birmaster")
    ns_lines = collect_insert_lines(section, "nonsaleablemaster")
    prf_lines = collect_insert_lines(section, "prfobmaster")

    if not bir_lines and not ns_lines and not prf_lines:
        print("No INSERT lines found for birmaster / nonsaleablemaster / prfobmaster.", file=sys.stderr)
        sys.exit(1)

    out_lines: list[str] = [
        "-- Generated by import_mysql_dump_prfob_bir_nonsaleable.py",
        "BEGIN;",
    ]
    if not args.no_truncate:
        out_lines.append(
            "TRUNCATE birmaster, nonsaleablemaster, prfobmaster RESTART IDENTITY CASCADE;"
        )

    for line in bir_lines:
        out_lines.append(mysql_insert_to_pg("birmaster", BIRMASTER_COLS, line))
    for line in ns_lines:
        out_lines.append(mysql_insert_to_pg("nonsaleablemaster", NONSALEABLE_COLS, line))
    for line in prf_lines:
        out_lines.append(mysql_insert_to_pg("prfobmaster", PRFOB_COLS, line))

    out_lines.append(emit_setval("birmaster"))
    out_lines.append(emit_setval("nonsaleablemaster"))
    out_lines.append(emit_setval("prfobmaster"))
    out_lines.append("COMMIT;")

    sys.stdout.write("\n".join(out_lines) + "\n")


if __name__ == "__main__":
    main()
