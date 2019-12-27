-- Puede que ya este activo
-- CREATE EXTENSION pgcrypto;
-- Es para el UUID
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE ROLE editors_rafex
NOSUPERUSER
CREATEDB
NOCREATEROLE
NOINHERIT
REPLICATION;

CREATE ROLE viewers_rafex
NOSUPERUSER
NOCREATEDB
NOCREATEROLE
NOINHERIT
REPLICATION;

CREATE USER rafex
LOGIN
ENCRYPTED PASSWORD 'sd9f4d30c.c9sd'
NOSUPERUSER
NOCREATEDB
NOCREATEROLE
REPLICATION
INHERIT
CONNECTION LIMIT 20;

GRANT viewers_rafex TO "rafex";

CREATE DATABASE rafex ENCODING 'UNICODE' TEMPLATE 'template0' OWNER postgres;

CREATE SCHEMA rafex
    AUTHORIZATION rafex;

COMMENT ON SCHEMA rafex
    IS 'Base principal de rafex web';

GRANT ALL ON SCHEMA rafex TO editors_rafex WITH GRANT OPTION;

GRANT USAGE ON SCHEMA rafex TO viewers_rafex WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA rafex
GRANT ALL ON TABLES TO editors_rafex WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA rafex
GRANT INSERT, SELECT, UPDATE ON TABLES TO viewers_rafex WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA rafex
GRANT ALL ON SEQUENCES TO editors_rafex WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA rafex
GRANT SELECT, USAGE ON SEQUENCES TO viewers_rafex WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA rafex
GRANT EXECUTE ON FUNCTIONS TO editors_rafex WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA rafex
GRANT EXECUTE ON FUNCTIONS TO viewers_rafex WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA rafex
GRANT USAGE ON TYPES TO editors_rafex WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA rafex
GRANT USAGE ON TYPES TO viewers_rafex WITH GRANT OPTION;

CREATE SEQUENCE rafex.contacto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE rafex.contacto_id_seq OWNER TO editors_rafex;

CREATE TABLE rafex.contacto
(
    identificador integer NOT NULL DEFAULT nextval('contacto_id_seq'::regclass),
    uuid character varying(36) NOT NULL DEFAULT uuid_generate_v4(),
    nombre character varying(40),
    correo character varying(100),
    telefono character varying(15),
    asunto character varying(40),
    mensaje text,
    leido boolean NOT NULL DEFAULT false,
    fecha_alta timestamp without time zone DEFAULT now() NOT NULL,
    fecha_modificacion timestamp without time zone,
    usuario_bd character varying(20) NOT NULL DEFAULT CURRENT_USER,
    borrado boolean NOT NULL DEFAULT false,
    CONSTRAINT contacto_pkey PRIMARY KEY (identificador)
);

ALTER TABLE rafex.contacto OWNER to editors_rafex;
