# Closed Product (`New_ClosedProduct.jsp`)

## Legacy purpose

**Closed Product** (legacy title **Closed Product** / panel **Closed Product List**) is the queue where **`ship_date_from_commercial` is set** — units that have completed the commercial dispatch step. It complements **SC Closed Product** (`closedproduct.jsp`), which uses the **same** ship-from-SC and repair-stock conditions but requires **`ship_date_from_commercial IS NULL`** (still waiting on commercial).

## Filter SQL (`New_ClosedController`)

```sql
ship_dt_frm_ser_cntr IS NOT NULL
AND repaired_brd_stk_date IS NOT NULL
AND ship_date_from_commercial IS NOT NULL
```

**Sc RNo:** legacy servlet used prefix `LOWER(sc_ref_no) LIKE LOWER(prefix%)`. **Migrated REST** uses **substring** match on `sc_ref_no` (same as other queues).  
**Global search:** FRN no. or Def GIR no. (contains).

## Grid columns (same as SC Closed Product)

14 columns — **no Final Remarks**; last column **Days taken to complete** (legacy servlet: days from `ser_centre_received_date` to today; API uses `pendingDays`).

## Legacy flow

| Step | Artifact |
|------|----------|
| Page | `New_ClosedProduct.jsp` — DataTables → `New_ClosedController` |
| Export POST | `Export_NewClosedController` → `New_ClosedDao.NewClosedExcel` |

## REST mapping (migrated)

| Operation | Method | Path |
|-----------|--------|------|
| Paged list | GET | `/api/services/new-closed` — `page`, `size`, `scRef`, `search`, `sort` |
| Excel export | GET | `/api/services/export/new-closed` — `scRef`, `search` |

## Frontend

| Route | File |
|-------|------|
| `/dashboard/new-closed` | `schiller-frontend/src/app/(dashboard)/dashboard/new-closed/page.tsx` |

Excel uses the same **14-column** builder as SC Closed; sheet name **Closed Product**.

## Comparison

| | SC Closed (`closedproduct.jsp`) | Closed Product (`New_ClosedProduct.jsp`) |
|--|--------------------------------|------------------------------------------|
| `ship_date_from_commercial` | **IS NULL** | **IS NOT NULL** |
| Other conditions | Ship from SC set, repair stock set | Same |
