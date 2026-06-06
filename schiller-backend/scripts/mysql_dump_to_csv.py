#!/usr/bin/env python3
"""
Extract MySQL mysqldump INSERT data into CSV files (one file per table).

Reads standard mysqldump output: CREATE TABLE `name` (...) and
INSERT INTO `name` VALUES (...),(...);

Usage:
  python3 mysql_dump_to_csv.py /path/to/dump.sql -o ./csv_out --db 2026

  # Only specific tables:
  python3 mysql_dump_to_csv.py dump.sql -o ./csv_out --tables emploeemaster service_master

Output:
  csv_out/birmaster.csv
  csv_out/_summary.txt
"""

from __future__ import annotations

import argparse
import csv
import re
import sys
from pathlib import Path
from typing import Any

USE_2026 = "USE `schillerindiaservices_2026`;"
USE_2025 = "USE `schillerindiaservices_2025`;"

CREATE_TABLE_RE = re.compile(
    r"CREATE TABLE `(\w+)`\s*\((.*?)\)\s*ENGINE\s*=",
    re.DOTALL | re.IGNORECASE,
)
COLUMN_LINE_RE = re.compile(r"^\s*`(\w+)`", re.MULTILINE)
INSERT_PREFIX_RE = re.compile(r"^INSERT INTO `(\w+)` VALUES ", re.MULTILINE)


def extract_section(text: str, which: str) -> str:
    if which == "2026":
        a = text.find(USE_2026)
        if a == -1:
            raise ValueError("Could not find schillerindiaservices_2026 section in dump")
        b = text.find(USE_2025, a)
        return text[a:b] if b != -1 else text[a:]
    if which == "2025":
        a = text.find(USE_2025)
        if a == -1:
            raise ValueError("Could not find schillerindiaservices_2025 section in dump")
        return text[a:]
    raise ValueError(f"Unknown db selector: {which}")


def parse_create_table_columns(section: str) -> dict[str, list[str]]:
    """Table name -> ordered column names from CREATE TABLE blocks."""
    tables: dict[str, list[str]] = {}
    for match in CREATE_TABLE_RE.finditer(section):
        name = match.group(1)
        body = match.group(2)
        cols: list[str] = []
        for line in body.splitlines():
            line = line.strip()
            if not line.startswith("`"):
                continue
            if line.upper().startswith(("PRIMARY ", "KEY ", "UNIQUE ", "CONSTRAINT ")):
                continue
            m = COLUMN_LINE_RE.match(line)
            if m:
                cols.append(m.group(1))
        if cols:
            tables[name] = cols
    return tables


def decode_mysql_string(s: str, start: int) -> tuple[int, str]:
    """Read MySQL '...' literal; return (index after closing quote, decoded text)."""
    assert start < len(s) and s[start] == "'"
    i = start + 1
    parts: list[str] = []
    while i < len(s):
        c = s[i]
        if c == "\\" and i + 1 < len(s):
            nxt = s[i + 1]
            escapes = {"n": "\n", "r": "\r", "t": "\t", "'": "'", "\\": "\\"}
            parts.append(escapes.get(nxt, nxt))
            i += 2
            continue
        if c == "'" and i + 1 < len(s) and s[i + 1] == "'":
            parts.append("'")
            i += 2
            continue
        if c == "'":
            return i + 1, "".join(parts)
        parts.append(c)
        i += 1
    raise ValueError("Unterminated MySQL string in INSERT VALUES")


def parse_mysql_value(s: str, i: int) -> tuple[Any, int]:
    n = len(s)
    while i < n and s[i] in " \t\n\r":
        i += 1
    if i >= n:
        raise ValueError("Unexpected end while parsing value")
    if s[i : i + 4].upper() == "NULL" and (i + 4 >= n or s[i + 4] in ",)"):
        return None, i + 4
    if s[i] == "'":
        j, text = decode_mysql_string(s, i)
        return text, j
    j = i
    if s[j] == "-":
        j += 1
    while j < n and (s[j].isdigit() or s[j] in ".eE"):
        j += 1
    if j > i and (j >= n or s[j] in ",)"):
        token = s[i:j]
        if "." in token or "e" in token.lower():
            return float(token), j
        return int(token), j
    raise ValueError(f"Cannot parse value at offset {i}: {s[i : i + 40]!r}")


def parse_insert_rows(values_fragment: str) -> list[list[Any]]:
    """Parse (a,b),(c,d) from INSERT ... VALUES fragment (may start with '(')."""
    rows: list[list[Any]] = []
    i = 0
    n = len(values_fragment)
    while i < n:
        while i < n and values_fragment[i] in " \t\n\r,":
            i += 1
        if i >= n:
            break
        if values_fragment[i] != "(":
            raise ValueError(f"Expected '(' at {i}, got {values_fragment[i : i + 20]!r}")
        i += 1
        fields: list[Any] = []
        while True:
            val, i = parse_mysql_value(values_fragment, i)
            fields.append(val)
            while i < n and values_fragment[i] in " \t\n\r":
                i += 1
            if i >= n:
                raise ValueError("Unclosed row tuple")
            if values_fragment[i] == ")":
                i += 1
                rows.append(fields)
                break
            if values_fragment[i] != ",":
                raise ValueError(f"Expected ',' or ')' at {i}, got {values_fragment[i : i + 10]!r}")
            i += 1
    return rows


def collect_inserts(section: str) -> dict[str, list[list[Any]]]:
    """All INSERT rows per table (merged if multiple INSERT lines exist)."""
    by_table: dict[str, list[list[Any]]] = {}
    for raw in section.splitlines():
        line = raw.strip()
        if not line.startswith("INSERT INTO `"):
            continue
        m = INSERT_PREFIX_RE.match(line)
        if not m:
            continue
        table = m.group(1)
        rest = line[m.end() :].rstrip().rstrip(";")
        rows = parse_insert_rows(rest)
        by_table.setdefault(table, []).extend(rows)
    return by_table


def cell_to_csv(value: Any) -> str:
    if value is None:
        return ""
    return str(value)


def write_table_csv(
    path: Path,
    columns: list[str],
    rows: list[list[Any]],
) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, quoting=csv.QUOTE_MINIMAL)
        writer.writerow(columns)
        for row in rows:
            if len(row) != len(columns):
                raise ValueError(
                    f"{path.name}: row has {len(row)} values, expected {len(columns)}"
                )
            writer.writerow([cell_to_csv(v) for v in row])


def main() -> None:
    ap = argparse.ArgumentParser(
        description=__doc__,
        formatter_class=argparse.RawDescriptionHelpFormatter,
    )
    ap.add_argument("dump_path", type=Path, help="Path to MySQL .sql mysqldump file")
    ap.add_argument(
        "-o",
        "--output-dir",
        type=Path,
        default=Path("mysql_csv_export"),
        help="Directory for CSV files (default: ./mysql_csv_export)",
    )
    ap.add_argument(
        "--db",
        choices=("2026", "2025"),
        default="2026",
        help="Which USE `schillerindiaservices_*` block to read (default: 2026)",
    )
    ap.add_argument(
        "--tables",
        nargs="*",
        help="Only export these tables (default: all tables with INSERT data)",
    )
    args = ap.parse_args()

    if not args.dump_path.is_file():
        print(f"File not found: {args.dump_path}", file=sys.stderr)
        sys.exit(1)

    text = args.dump_path.read_text(encoding="utf-8", errors="replace")
    section = extract_section(text, args.db)
    schema = parse_create_table_columns(section)
    inserts = collect_inserts(section)

    if args.tables:
        wanted = set(args.tables)
        inserts = {k: v for k, v in inserts.items() if k in wanted}
        missing = wanted - set(inserts.keys())
        if missing:
            print(f"Warning: no INSERT data for: {', '.join(sorted(missing))}", file=sys.stderr)

    if not inserts:
        print("No INSERT statements found in dump section.", file=sys.stderr)
        sys.exit(1)

    args.output_dir.mkdir(parents=True, exist_ok=True)
    summary_lines: list[str] = [
        f"Source: {args.dump_path}",
        f"Database block: schillerindiaservices_{args.db}",
        f"Output: {args.output_dir.resolve()}",
        "",
    ]

    exported = 0
    for table in sorted(inserts.keys()):
        rows = inserts[table]
        columns = schema.get(table)
        if not columns:
            print(f"Skipping {table}: no CREATE TABLE columns found", file=sys.stderr)
            continue
        if rows and len(rows[0]) != len(columns):
            print(
                f"Warning: {table} column count mismatch "
                f"(schema={len(columns)}, row={len(rows[0])}); using schema names only",
                file=sys.stderr,
            )
        out_path = args.output_dir / f"{table}.csv"
        try:
            write_table_csv(out_path, columns, rows)
        except ValueError as exc:
            print(f"Error writing {table}: {exc}", file=sys.stderr)
            sys.exit(1)
        summary_lines.append(f"{table}.csv\t{len(rows)} rows\t{len(columns)} columns")
        exported += 1

    summary_path = args.output_dir / "_summary.txt"
    summary_path.write_text("\n".join(summary_lines) + "\n", encoding="utf-8")

    print(f"Wrote {exported} CSV file(s) to {args.output_dir.resolve()}")
    print(f"Summary: {summary_path}")


if __name__ == "__main__":
    main()
