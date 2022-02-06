SET ROLE 'invoice-grp';
BEGIN TRANSACTION;

    ALTER TABLE address ALTER COLUMN zip TYPE character varying;
    ALTER TABLE subject ALTER COLUMN business_idnumber TYPE character varying;
    ALTER TABLE subject ALTER COLUMN vat_idnumber TYPE character varying;
    ALTER TABLE subject ALTER COLUMN phone_number TYPE character varying;

COMMIT TRANSACTION;
