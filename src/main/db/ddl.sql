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
