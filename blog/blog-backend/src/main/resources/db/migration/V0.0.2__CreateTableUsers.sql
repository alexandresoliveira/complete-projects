create table users (
  id uuid primary key, 
  name varchar(100) not null, 
  email varchar(100) not null unique, 
  password varchar(256) not null, 
  enabled boolean default true, 
  created_at timestamp with time zone not null, 
  updated_at timestamp with time zone not null
);

create index idx_users on users(id);
