-----------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------
--
-- crear roles
--
-----------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------

CREATE ROLE editors
NOSUPERUSER
CREATEDB
NOCREATEROLE
NOINHERIT
REPLICATION;

CREATE ROLE viewers
NOSUPERUSER
NOCREATEDB
NOCREATEROLE
NOINHERIT
REPLICATION;

CREATE USER goku
LOGIN
ENCRYPTED PASSWORD 'cdf98ud.sd'
SUPERUSER
CREATEDB
CREATEROLE
REPLICATION
INHERIT
CONNECTION LIMIT 5;

GRANT editors TO "goku";

CREATE USER netup
LOGIN
ENCRYPTED PASSWORD 'sd90c.c9sd'
NOSUPERUSER
NOCREATEDB
NOCREATEROLE
REPLICATION
INHERIT
CONNECTION LIMIT 20;

-- GRANT editors TO "netup";

