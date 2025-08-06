CREATE TABLE IF NOT EXISTS users (
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR UNIQUE NOT NULL,
    email             VARCHAR UNIQUE NOT NULL,
    email_confirmed   BOOLEAN        NOT NULL,
    verification_code VARCHAR,
    password          VARCHAR        NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT  NOT NULL REFERENCES users ON DELETE CASCADE,
    role    VARCHAR NOT NULL,
    PRIMARY KEY (user_id, role)
);

CREATE TABLE IF NOT EXISTS menu (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR,
    price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS cart (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users ON DELETE CASCADE,
    menu_id INTEGER NOT NULL REFERENCES menu ON DELETE CASCADE,
    quantity INTEGER NOT NULL DEFAULT 1,
    UNIQUE (user_id, menu_id)
);

CREATE OR REPLACE FUNCTION delete_if_zero_quantity()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.quantity <= 0 THEN
        DELETE FROM cart WHERE id = NEW.id;
        RETURN NULL;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_delete_if_zero_quantity
    AFTER INSERT OR UPDATE ON cart
    FOR EACH ROW
EXECUTE FUNCTION delete_if_zero_quantity();
