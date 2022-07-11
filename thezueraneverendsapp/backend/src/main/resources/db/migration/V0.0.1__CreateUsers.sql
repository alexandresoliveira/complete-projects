create table users (
    id uuid primary key,
    name varchar(100) not null,
    email varchar(100) not null unique,
    password varchar not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create index users_idx on users(id);
