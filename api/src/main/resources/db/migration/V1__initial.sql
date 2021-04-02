SET ROLE 'invoice-grp';
BEGIN TRANSACTION;

    CREATE TABLE address (
        id serial NOT NULL,
        name character varying(64) NULL,
        street character varying(64) NOT NULL,
        land_registry_number character varying(8) NOT NULL,
        house_number character varying(8) NULL,
        city character varying(64) NULL,
        zip integer NOT NULL,
        notes text,
        created_at timestamp NOT NULL DEFAULT NOW(),
        deleted_at timestamp NULL
    );

    ALTER TABLE address ADD CONSTRAINT address_pk PRIMARY KEY (id);
    CREATE INDEX address_name_ix ON address (name);

    CREATE TABLE subject (
        id serial NOT NULL,
        name character varying(64) NOT NULL,
        address_id integer NOT NULL,
        business_idnumber integer NOT NULL,
        vat_idnumber integer NULL,
        phone_number integer NULL,
        email_address character varying(255),
        bank_account character varying(64),
        created_at timestamp NOT NULL DEFAULT NOW(),
        deleted_at timestamp NULL
    );

    ALTER TABLE subject ADD CONSTRAINT subject_pk PRIMARY KEY (id);
    CREATE INDEX subject_name_ix ON subject (name);
    CREATE INDEX subject_address_id_ix ON subject (address_id);
    ALTER TABLE subject ADD CONSTRAINT subject_address_id_fk
        FOREIGN KEY (address_id) REFERENCES address (id);

    CREATE TABLE invoice (
        id serial NOT NULL,
        number integer NOT NULL,
        provider_id integer NOT NULL,
        acceptor_id integer NOT NULL,
        issued_at timestamp NOT NULL,
        due_date timestamp NOT NULL,
        total integer NOT NULL,
        notes text,
        created_at timestamp NOT NULL DEFAULT NOW(),
        deleted_at timestamp NULL
    );

    ALTER TABLE invoice ADD CONSTRAINT invoice_pk PRIMARY KEY (id);
    CREATE INDEX invoice_provider_id_ix ON invoice (provider_id);
    CREATE INDEX invoice_acceptor_id_ix ON invoice (acceptor_id);
    CREATE INDEX invoice_issued_at_ix ON invoice (issued_at);
    ALTER TABLE invoice ADD CONSTRAINT invoice_provider_id_fk
        FOREIGN KEY (provider_id) REFERENCES subject (id);
    ALTER TABLE invoice ADD CONSTRAINT invoice_acceptor_id_fk
        FOREIGN KEY (acceptor_id) REFERENCES subject (id);

    CREATE TABLE item (
        id serial NOT NULL,
        invoice_id integer NOT NULL,
        name character varying(255) NOT NULL,
        price_per_unit integer NOT NULL,
        units_count numeric(14,4) NOT NULL,
        total integer NOT NULL,
        ordering integer NOT NULL
    );

    ALTER TABLE item ADD CONSTRAINT item_pk PRIMARY KEY (id);
    CREATE INDEX item_invoice_id_ix ON item (invoice_id);
    ALTER TABLE item ADD CONSTRAINT item_invoice_id_fk
        FOREIGN KEY (invoice_id) REFERENCES invoice (id);

COMMIT TRANSACTION;
