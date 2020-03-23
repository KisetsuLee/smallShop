create table `user`
(
    id          bigint primary key auto_increment,
    name        varchar(100),
    phone       varchar(100) unique,
    avatar_url  varchar(1024),
    create_at   timestamp default now(),
    modified_at timestamp default now()
)
