INSERT INTO products(name, code)
VALUES
    ('Item 1', 101),
    ('Item 2', 112),
    ('Item 3', 141),
    ('Item 4', 353),
    ('Item 5', 242);
INSERT INTO organizations(name, inn, checking_account)
VALUES
    ('Provider 1', 1287, 258193),
    ('Provider 2', 1589, 158538),
    ('Provider 3', 1940, 563139),
    ('Provider 4', 1481, 614783),
    ('Provider 5', 1417, 157850);
INSERT INTO invoices(date, org_id)
VALUES
    ('2022-05-03', 1),
    ('2021-08-10', 1),
    ('2022-11-13', 4),
    ('2019-03-06', 2),
    ('2022-11-08', 3);
INSERT INTO positions(price, product_id, count, invoice_id)
VALUES
    (1100, 1, 1, 1),
    (1300, 4, 2, 1),
    (12200, 3, 1, 3),
    (3369, 2, 4, 4),
    (390, 5, 10, 2);