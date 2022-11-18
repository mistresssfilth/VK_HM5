CREATE TABLE organizations(
    id SERIAL,
    name VARCHAR NOT NULL,
    inn int NOT NULL,
    checking_account int NOT NULL,
    CONSTRAINT org_pk PRIMARY KEY (id)
);
CREATE TABLE invoices(
    id SERIAL,
    date DATE NOT NULL,
    org_id int NOT NULL REFERENCES organizations(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT invoices_pk PRIMARY KEY (id)
);
CREATE TABLE products(
    id SERIAL,
    name VARCHAR NOT NULL,
    code int NOT NULL,
    CONSTRAINT products_pk PRIMARY KEY (id)
);
CREATE TABLE positions(
    id SERIAL,
    price int NOT NULL,
    product_id int NOT NULL REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE,
    count int,
    CONSTRAINT pos_pk PRIMARY KEY (id)
);