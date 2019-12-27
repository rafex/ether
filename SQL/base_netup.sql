CREATE EXTENSION pgcrypto;

CREATE ROLE editors_netup
NOSUPERUSER
CREATEDB
NOCREATEROLE
NOINHERIT
REPLICATION;

CREATE ROLE viewers_netup
NOSUPERUSER
NOCREATEDB
NOCREATEROLE
NOINHERIT
REPLICATION;

CREATE USER netup
LOGIN
ENCRYPTED PASSWORD 'sd90c.c9sd'
NOSUPERUSER
NOCREATEDB
NOCREATEROLE
REPLICATION
INHERIT
CONNECTION LIMIT 20;

GRANT viewers_netup TO "netup";

CREATE DATABASE netup ENCODING 'UNICODE' TEMPLATE 'template0' OWNER postgres;

CREATE SCHEMA netup
    AUTHORIZATION netup;

COMMENT ON SCHEMA netup
    IS 'Base principal de todos los sistemas';

GRANT ALL ON SCHEMA netup TO editors_netup WITH GRANT OPTION;

GRANT USAGE ON SCHEMA netup TO viewers_netup WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA netup
GRANT ALL ON TABLES TO editors_netup WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA netup
GRANT INSERT, SELECT, UPDATE ON TABLES TO viewers_netup WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA netup
GRANT ALL ON SEQUENCES TO editors_netup WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA netup
GRANT SELECT, USAGE ON SEQUENCES TO viewers_netup WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA netup
GRANT EXECUTE ON FUNCTIONS TO editors_netup WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA netup
GRANT EXECUTE ON FUNCTIONS TO viewers_netup WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA netup
GRANT USAGE ON TYPES TO editors_netup WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES IN SCHEMA netup
GRANT USAGE ON TYPES TO viewers_netup WITH GRANT OPTION;

CREATE SEQUENCE netup.pais_catalogo_secuencia
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE netup.pais_catalogo_secuencia OWNER TO editors_netup;

SET default_with_oids = false;

--
-- TOC entry 213 (class 1259 OID 16963)
-- Name: pais_catalogo; Type: TABLE; Schema: eucari; Owner: eucari
--

CREATE TABLE netup.pais_catalogo (
    identificador integer DEFAULT nextval('netup.pais_catalogo_secuencia'::regclass) NOT NULL,
    uuid character varying(36) NOT NULL DEFAULT uuid_generate_v4(),
    iso character varying(2),
    nombre character varying(80),
    fecha_alta timestamp without time zone DEFAULT now() NOT NULL,
    fecha_modificacion timestamp without time zone,
    usuario_bd character varying(20),
    activo boolean DEFAULT true NOT NULL,
    borrado boolean NOT NULL DEFAULT false,
    CONSTRAINT pais_catalogo_pkey PRIMARY KEY (identificador)
);

ALTER TABLE netup.pais_catalogo OWNER TO editors_netup;

CREATE SEQUENCE netup.colonia_codigo_postal_catalogo_secuencia
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE netup.colonia_codigo_postal_catalogo_secuencia OWNER TO editors_netup;

--
-- TOC entry 211 (class 1259 OID 16952)
-- Name: colonia_codigo_postal_catalogo; Type: TABLE; Schema: netup; Owner: netup
--

CREATE TABLE netup.colonia_codigo_postal_catalogo (
    identificador integer DEFAULT nextval('netup.colonia_codigo_postal_catalogo_secuencia'::regclass) NOT NULL,
    uuid character varying(36) NOT NULL DEFAULT uuid_generate_v4(),
    codigo_postal character varying(5),
    nombre character varying(100),
    municipio character varying(100),
    estado character varying(100),
    ciudad character varying(100),
    fecha_alta timestamp without time zone DEFAULT now() NOT NULL,
    fecha_modificacion timestamp without time zone,
    usuario_bd character varying(20),
    activo boolean DEFAULT true NOT NULL,
    borrado boolean NOT NULL DEFAULT false,
    CONSTRAINT colonia_codigo_postal_catalogo_pkey PRIMARY KEY (identificador)
);


ALTER TABLE netup.colonia_codigo_postal_catalogo OWNER TO editors_netup;

CREATE SEQUENCE netup.contacto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE netup.contacto_id_seq OWNER TO editors_netup;

CREATE TABLE netup.contacto
(
    identificador integer NOT NULL DEFAULT nextval('contacto_id_seq'::regclass),
    uuid character varying(36) NOT NULL DEFAULT uuid_generate_v4(),
    nombre character varying(40),
    correo character varying(100),
    asunto character varying(40),
    mensaje text,
    leido boolean NOT NULL DEFAULT false,
    fecha_alta timestamp without time zone DEFAULT now() NOT NULL,
    fecha_modificacion timestamp without time zone,
    usuario_bd character varying(20) NOT NULL DEFAULT CURRENT_USER,
    borrado boolean NOT NULL DEFAULT false,
    CONSTRAINT contacto_pkey PRIMARY KEY (identificador)
);

ALTER TABLE netup.contacto OWNER to editors_netup;
