CREATE TYPE customer_role AS ENUM ('ADMIN', 'CLIENT');

CREATE TABLE customers (
    id serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    document varchar(14) NOT NULL UNIQUE,
    phone varchar(15) NOT NULL UNIQUE,
    role customer_role NOT NULL,
    created_at timestamp,
    updated_at timestamp
);