create table comments (
	id uuid primary key, 
  	description varchar(300) not null unique, 
  	user_id uuid not null,
  	post_id uuid not null,
  	created_at timestamp with time zone not null, 
  	updated_at timestamp with time zone not null,
  	constraint fk_users foreign key (user_id) references users(id),
  	constraint fk_posts foreign key (post_id) references posts(id)
);

create index idx_comments on comments(id);
