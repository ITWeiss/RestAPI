create table tasks (
  id serial,
  title varchar(255),
  description varchar(255),
  completed boolean,
  constraint pk_users primary key (id)
);