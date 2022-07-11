create table authorities (
  id uuid primary key, 
  name varchar(100) not null unique, 
  created_at timestamp with time zone not null, 
  updated_at timestamp with time zone not null
);

create index idx_authorities on authorities(id);
