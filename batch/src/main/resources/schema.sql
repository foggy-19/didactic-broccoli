drop table if exists dogs ;

create table if not exists dogs
(
    id serial primary key,
    name text not null,
    description text not null,
    owner   text
);