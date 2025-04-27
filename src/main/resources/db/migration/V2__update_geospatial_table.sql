ALTER TABLE geospatial
    DROP COLUMN creation_date,
    ADD COLUMN creation_date TIMESTAMPTZ NOT NULL,
    ADD COLUMN update_date TIMESTAMPTZ  NOT NULL;