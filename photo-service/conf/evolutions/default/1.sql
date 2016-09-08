# Photos schema

# --- !Ups

CREATE SEQUENCE photo_id_seq;
CREATE TABLE photos(
  id integer NOT NULL DEFAULT nextval('photo_id_seq') PRIMARY KEY,
  user varchar(255),
  description text,
  url text
);

# --- !Downs

DROP TABLE photos;
DROP SEQUENCE photo_id_seq;
