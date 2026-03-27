CREATE TYPE transaction_type AS ENUM ('DEPOSIT', 'WITHDRAW', 'TRANSFER');

CREATE TABLE transactions (

    id serial PRIMARY KEY,
    type transaction_type NOT NULL,
    amount decimal,
    from_account INTEGER,
    to_account INTEGER,
    created_at timestamp,

    CONSTRAINT fk_transactions_accounts FOREIGN KEY (from_account) REFERENCES accounts(id),
    CONSTRAINT fk_transactions_accounts FOREIGN KEY (to_account) REFERENCES accounts(id)

);