create table posts (
	id uuid primary key, 
  	type varchar(30) not null, 
  	user_id uuid not null,
  	created_at timestamp with time zone not null, 
  	updated_at timestamp with time zone not null,
  	constraint fk_users foreign key (user_id) references users(id)
);

create index idx_posts on posts(id);
