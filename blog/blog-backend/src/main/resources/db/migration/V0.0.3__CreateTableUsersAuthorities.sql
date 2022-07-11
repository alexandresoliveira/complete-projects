create table users_authorities (
  user_entity_id uuid not null, 
  authorities_id uuid not null, 
  constraint users_authorities_pkey primary key (user_entity_id, authorities_id), 
  constraint fk_authorities foreign key (authorities_id) references authorities(id), 
  constraint fk_users foreign key (user_entity_id) references users(id)
);
