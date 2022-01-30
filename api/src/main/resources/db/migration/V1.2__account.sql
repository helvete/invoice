SET ROLE 'invoice-grp';
BEGIN TRANSACTION;

    CREATE TABLE account (
        id serial NOT NULL,
        email character varying(255) NOT NULL,
        password character varying(255) NOT NULL,
        role int NOT NULL DEFAULT 1,
        email_verification_hash uuid NULL,
        email_verified_at timestamp NULL,
        created_at timestamp NOT NULL DEFAULT NOW(),
        deleted_at timestamp NULL
    );

    ALTER TABLE account ADD CONSTRAINT account_pk PRIMARY KEY (id);
    CREATE UNIQUE INDEX account_email_ix ON account (email);

    ALTER TABLE invoice ADD COLUMN account_id int NOT NULL;
    CREATE INDEX invoice_account_id_ix ON invoice (acceptor_id);
    ALTER TABLE invoice ADD CONSTRAINT invoice_account_id_fk
        FOREIGN KEY (account_id) REFERENCES account (id);

COMMIT TRANSACTION;
