#!/usr/bin/env python3
"""
Extract INSERT rows for callregister_demo from a MySQL mysqldump and emit PostgreSQL INSERT.

Merges multiple INSERT blocks in one file: rows from later blocks get new ids (max(first block)+1 …)
so nothing is overwritten.

Usage:
  python3 scripts/generate_callregister_demo_seed.py [path/to/Dump20260314.sql] > seed.sql
"""

from __future__ import annotations

import re
import sys
from pathlib import Path

COLS = (
    "id, division, sc_engg, call_date, call_type, region, branch, dealer, engineer, "
    "model, type_of_call, type_of_communication, remarks, duration, status_type, entry_date"
)


def split_sql_tuple_rows(values_tail: str) -> list[str]:
    """values_tail is the part after 'VALUES (' through the final ');' (semicolon stripped)."""
    s = "(" + values_tail.rstrip().rstrip(";").rstrip()
    if not s.endswith(")"):
        s = s + ")"
    rows: list[str] = []
    depth = 0
    start = 0
    in_str = False
    quote = "'"
    i = 0
    escape = False
    while i < len(s):
        c = s[i]
        if not in_str:
            if c == "(":
                if depth == 0:
                    start = i
                depth += 1
            elif c == ")":
                depth -= 1
                if depth == 0:
                    rows.append(s[start : i + 1])
            elif c in ("'", '"'):
                in_str = True
                quote = c
                escape = False
        else:
            if escape:
                escape = False
            elif c == "\\":
                escape = True
            elif c == quote:
                if i + 1 < len(s) and s[i + 1] == quote:
                    i += 1
                else:
                    in_str = False
        i += 1
    return rows


def extract_rows_from_insert_line(line: str) -> list[str]:
    key = "VALUES ("
    idx = line.index(key) + len(key)
    rest = line[idx:]
    if rest.endswith(";\n"):
        rest = rest[:-2]
    elif rest.endswith(";"):
        rest = rest[:-1]
    elif rest.endswith("\n"):
        rest = rest[:-1]
    return split_sql_tuple_rows(rest)


def mysql_tuple_to_pg(row: str) -> str:
    """Convert MySQL row literal ( ... ) to PostgreSQL-safe ( ... ) for string escapes."""
    assert row.startswith("(") and row.endswith(")")
    inner = row[1:-1]
    out: list[str] = []
    i = 0
    in_str = False
    escape = False
    while i < len(inner):
        c = inner[i]
        if not in_str:
            if c == "'":
                in_str = True
                escape = False
                out.append(c)
            else:
                out.append(c)
        else:
            if escape:
                out.append(c)
                escape = False
            elif c == "\\" and i + 1 < len(inner):
                nxt = inner[i + 1]
                if nxt == "'":
                    out.append("''")
                    i += 1
                elif nxt == "\\":
                    out.append("\\")
                    i += 1
                else:
                    out.append(c)
                    escape = True
            elif c == "'":
                if i + 1 < len(inner) and inner[i + 1] == "'":
                    out.append("''")
                    i += 1
                else:
                    out.append(c)
                    in_str = False
            else:
                out.append(c)
        i += 1
    return "(" + "".join(out) + ")"


def first_field_id(row: str) -> int:
    m = re.match(r"^\((\d+),", row)
    if not m:
        raise ValueError(f"Cannot parse id: {row[:80]}")
    return int(m.group(1))


def replace_leading_id(row: str, new_id: int) -> str:
    m = re.match(r"^\((\d+),", row)
    if not m:
        raise ValueError("bad row")
    return f"({new_id}," + row[m.end() :]


def split_fields(inner: str) -> list[str]:
    """Split comma-separated fields inside tuple body (no outer parens)."""
    fields: list[str] = []
    cur: list[str] = []
    depth = 0
    in_str = False
    quote = "'"
    escape = False
    i = 0
    while i < len(inner):
        c = inner[i]
        if not in_str:
            if c == "(":
                depth += 1
                cur.append(c)
            elif c == ")":
                depth -= 1
                cur.append(c)
            elif c == "," and depth == 0:
                fields.append("".join(cur).strip())
                cur = []
            elif c in ("'", '"'):
                in_str = True
                quote = c
                escape = False
                cur.append(c)
            else:
                cur.append(c)
        else:
            cur.append(c)
            if escape:
                escape = False
            elif c == "\\":
                escape = True
            elif c == quote:
                if i + 1 < len(inner) and inner[i + 1] == quote:
                    cur.append(inner[i + 1])
                    i += 1
                else:
                    in_str = False
        i += 1
    if cur:
        fields.append("".join(cur).strip())
    return fields


def filter_completed_row(row: str) -> bool:
    inner = row[1:-1]
    fields = split_fields(inner)
    if len(fields) < 15:
        return True
    raw = fields[14].strip()
    if raw.upper() == "NULL":
        return False
    if raw.startswith("'"):
        v = raw[1:-1].replace("''", "'")
        v = re.sub(r"\\'", "'", v).lower().strip()
    else:
        v = raw.lower().strip()
    return v in ("completed", "complete")


def main() -> None:
    dump_path = Path(
        sys.argv[1]
        if len(sys.argv) > 1
        else Path(__file__).resolve().parents[1] / "schillerindiaservices" / "Dump20260314.sql"
    )
    lines = dump_path.read_text(encoding="utf-8", errors="replace").splitlines()
    insert_lines = [ln for ln in lines if ln.startswith("INSERT INTO `callregister_demo`")]
    if not insert_lines:
        print(f"-- No INSERT INTO `callregister_demo` in {dump_path}", file=sys.stderr)
        sys.exit(1)

    blocks = [extract_rows_from_insert_line(ln) for ln in insert_lines]

    merged: list[str] = []
    if not blocks:
        sys.exit(1)
    merged.extend(blocks[0])
    next_id = max(first_field_id(r) for r in blocks[0]) + 1
    for extra in blocks[1:]:
        for r in extra:
            merged.append(replace_leading_id(r, next_id))
            next_id += 1

    merged.sort(key=first_field_id)
    filtered = [r for r in merged if filter_completed_row(r)]
    pg_rows = [mysql_tuple_to_pg(r) for r in filtered]

    print("-- Seed callregister_demo from MySQL mysqldump.")
    print("-- Source:", dump_path.name)
    print("-- Rows:", len(pg_rows), file=sys.stderr)
    print(
        "-- Two INSERT blocks merged: first keeps ids 1..N; second renumbered after max(id)."
    )
    print("-- Columns: duration, status_type, entry_date (legacy names).")
    print("-- Use when table is empty; PK conflict if rows already exist.")
    print(f"INSERT INTO callregister_demo ({COLS})")
    print("VALUES")
    for i, pr in enumerate(pg_rows):
        tail = "," if i < len(pg_rows) - 1 else ";"
        print(f"  {pr}{tail}")

    print()
    print(
        "SELECT setval(pg_get_serial_sequence('callregister_demo', 'id'), "
        "COALESCE((SELECT MAX(id) FROM callregister_demo), 1));"
    )


if __name__ == "__main__":
    main()
