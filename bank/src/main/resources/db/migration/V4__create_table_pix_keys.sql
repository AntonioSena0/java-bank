CREATE TYPE pix_key_type AS ENUM ('CPF', 'EMAIL', 'PHONE', 'RANDOM');

CREATE TABLE pix_keys (

    id serial PRIMARY KEY,
    keyValue varchar(100) NOT NULL,
    type pix_key_type NOT NULL,
    account_id INTEGER,
    created_at timestamp,
    updated_at timestamp,

    CONSTRAINT fk_pix_keys_accounts FOREIGN KEY (account_id) REFERENCES accounts(id)

);