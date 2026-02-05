# SQLite / Room – SQL Cheat Sheet

This file contains the most common and useful SQL commands you can run
inside **Android Studio → App Inspector → Database → Run Query**.

These are the same commands that **Room uses internally**.

---

## Read all rows from a table
```sql
SELECT * FROM contacts;
```

---

## Read specific columns
```sql
SELECT name, phone FROM contacts;
```

---

## Insert a new row
```sql
INSERT INTO contacts (name, phone)
VALUES ('Ali', '09123456789');
```

---

---

## Insert new records
```sql
INSERT INTO contacts (name, phone) VALUES
('Ali Rezaei', '09120000001'),
('Sara Mohammadi', '09120000002'),
('Reza Karimi', '09120000003'),
('Negin Ahmadi', '09120000004'),
('Mohammad Hosseini', '09120000005'),
('Elham Ghasemi', '09120000006'),
('Amir Tavakoli', '09120000007'),
('Zahra Ebrahimi', '09120000008'),
('Pouya Shafiei', '09120000009'),
('Maryam Jalili', '09120000010'),
('Sina Rahmani', '09120000011'),
('Fatemeh Kiani', '09120000012'),
('Arman Rostami', '09120000013'),
('Leila Moradi', '09120000014'),
('Mehdi Fallahi', '09120000015'),
('Parisa Soltani', '09120000016'),
('Navid Azizi', '09120000017'),
('Hanieh Yektayi', '09120000018'),
('Farhad Akbari', '09120000019'),
('Yasmin Ranjbar', '09120000020');
```

---

## Update existing data
```sql
UPDATE contacts
SET phone = '09999999999'
WHERE name = 'Ali';
```

> If no record matches, the query runs successfully but updates **0 rows**.

---

## Delete a specific row
```sql
DELETE FROM contacts
WHERE name = 'Ali';
```

---

## Delete all rows (⚠ dangerous)
```sql
DELETE FROM contacts;
```

---

## Count rows
```sql
SELECT COUNT(*) FROM contacts;
```

---

## Sort results
Ascending: 
```sql -- alphabetical
SELECT * FROM contacts ORDER BY name ASC; 
```

Descending:
```sql -- reverse alphabetical
SELECT * FROM contacts ORDER BY name DESC;
```

---

## Limit number of results
```sql
SELECT * FROM contacts LIMIT 5;
```
