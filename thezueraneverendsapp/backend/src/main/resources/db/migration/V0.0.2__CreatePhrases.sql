create table phrases (
    id uuid primary key,
    text varchar(300) not null unique,
    user_id uuid not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint fk_user_id foreign key (user_id) references users(id)
);

create index phrases_idx on phrases(id);