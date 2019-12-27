-----------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------
--
-- crear roles
--
-----------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------

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

CREATE USER rafex_web
LOGIN
ENCRYPTED PASSWORD 'cdf98sdfud.sd'
SUPERUSER
CREATEDB
CREATEROLE
REPLICATION
INHERIT
CONNECTION LIMIT 5;

GRANT viewers_rafex TO "rafex_web";

/*
CREATE USER netup
LOGIN
ENCRYPTED PASSWORD 'sd90c.c9sd'
NOSUPERUSER
NOCREATEDB
NOCREATEROLE
REPLICATION
INHERIT
CONNECTION LIMIT 20;
*/
-- GRANT editors TO "netup";

