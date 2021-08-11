CREATE TABLE users
(
  id    SERIAL PRIMARY KEY,
  name  TEXT NOT NULL,
  email TEXT NOT NULL
);

CREATE TABLE cars (
  id         SERIAL PRIMARY KEY,
  brand      TEXT NOT NULL,
  model      TEXT NOT NULL,
  style_name TEXT NOT NULL
);

CREATE DATABASE other
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE TABLE public.other_table
(
    id serial,
    other_column text NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

CREATE TABLE public."ISO639-3"
(
    "langID" character(3),
    "langName" character(30),
    PRIMARY KEY ("langID")
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public."ISO639-3"
    OWNER to postgres;

COMMENT ON TABLE public."ISO639-3"
    IS 'Codes for the representation of names of languages';