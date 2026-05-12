import sys
import re

DUMP_FILE = "/Users/dinesharumugam/Documents/SIS Software/Dump20260314.sql"
OUTPUT_FILE = "dealer_patch.sql"

def parse_row_values(row_str):
    inner = row_str[1:-1]
    values = []
    i = 0
    current = []
    in_string = False
    escape_next = False
    
    while i < len(inner):
        ch = inner[i]
        
        if escape_next:
            current.append(ch)
            escape_next = False
            i += 1
            continue
        
        if ch == '\\':
            escape_next = True
            current.append(ch)
            i += 1
            continue
        
        if ch == "'":
            in_string = not in_string
            current.append(ch)
            i += 1
            continue
            
        if ch == ',' and not in_string:
            values.append(''.join(current).strip())
            current = []
        else:
            current.append(ch)
        i += 1
    
    if current:
        values.append(''.join(current).strip())
        
    return values

def extract_inserts(content, table_name):
    # Match INSERT INTO `table_name` VALUES (...)
    pattern = re.compile(r"INSERT INTO `?" + table_name + r"`?.*?VALUES\s*(.*?;)", re.IGNORECASE | re.DOTALL)
    matches = pattern.findall(content)
    
    inserts = []
    for match in matches:
        inserts.append(match)
    return inserts

def parse_values_tuples(values_str):
    rows = []
    
    i = 0
    depth = 0
    start = -1
    in_string = False
    escape_next = False
    
    while i < len(values_str):
        ch = values_str[i]
        
        if escape_next:
            escape_next = False
            i += 1
            continue
        
        if ch == '\\':
            escape_next = True
            i += 1
            continue
        
        if ch == "'":
            in_string = not in_string
        elif not in_string:
            if ch == '(':
                if depth == 0:
                    start = i
                depth += 1
            elif ch == ')':
                depth -= 1
                if depth == 0 and start >= 0:
                    rows.append(values_str[start:i+1])
                    start = -1
        
        i += 1
    
    return rows

def convert_value(val):
    if val == 'NULL':
        return 'NULL'
    if val.startswith("'") and val.endswith("'"):
        inner = val[1:-1]
        inner = inner.replace("\\'", "''")
        inner = inner.replace('\\"', '"')
        inner = inner.replace('\\\\', '\\')
        inner = inner.replace('\\0', '')
        return "'" + inner + "'"
    return val

def main():
    try:
        with open(DUMP_FILE, 'r', encoding='utf-8', errors='replace') as f:
            content = f.read()
    except Exception as e:
        print(f"Error reading {DUMP_FILE}: {e}")
        return

    # Restrict to 2026 DB
    marker = "Current Database: `schillerindiaservices_2025`"
    idx = content.find(marker)
    if idx > 0:
        content = content[:idx]

    inserts = extract_inserts(content, 'dealermaster')
    if not inserts:
        print("No dealermaster inserts found")
        return

    out_lines = []
    out_lines.append("ALTER TABLE dealer ADD COLUMN IF NOT EXISTS dealer_address varchar(255);")
    out_lines.append("ALTER TABLE dealer ADD COLUMN IF NOT EXISTS dealer_mail varchar(255);")
    out_lines.append("ALTER TABLE dealer ADD COLUMN IF NOT EXISTS dealer_phone varchar(50);")
    out_lines.append("BEGIN;")

    for values_str in inserts:
        rows = parse_values_tuples(values_str)
        for row in rows:
            vals = parse_row_values(row)
            if len(vals) >= 5:
                # dealer_id(0), dealer_name(1), dealer_address(2), dealer_mail(3), dealer_phone(4), dealer_region_id(5)
                did = vals[0]
                addr = convert_value(vals[2])
                mail = convert_value(vals[3])
                phone = convert_value(vals[4])
                
                out_lines.append(f"UPDATE dealer SET dealer_address = {addr}, dealer_mail = {mail}, dealer_phone = {phone} WHERE dealer_id = {did};")

    out_lines.append("COMMIT;")
    
    res = "\n".join(out_lines)
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        f.write(res)
    
    print(f"Wrote {len(out_lines)} lines to {OUTPUT_FILE}")

if __name__ == '__main__':
    main()
