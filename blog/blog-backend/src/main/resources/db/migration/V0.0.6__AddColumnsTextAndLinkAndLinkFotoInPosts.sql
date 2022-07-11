alter table posts
	add column text varchar(256) not null,
	add column link varchar(512),
	add column link_photo varchar(512);