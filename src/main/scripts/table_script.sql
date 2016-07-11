create table users
(
username varchar(25) not null,
password varchar(100) not null,
datetime_created timestamp,
constraint users_pkey primary key (username,password)
);
