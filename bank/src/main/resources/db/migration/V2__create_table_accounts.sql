CREATE TYPE account_type AS ENUM ('CHECKING', 'SAVINGS');

CREATE TABLE accounts (

    id serial PRIMARY KEY,
    balance decimal NOT NULL,
    type account_type NOT NULL,
    customer_id INTEGER,
    created_at timestamp,

    CONSTRAINT fk_accounts_customers FOREIGN KEY (customer_id) REFERENCES customers(id)

);