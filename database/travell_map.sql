show databases;
use map;
show tables;

-- 建一个用户表
create table User(
	id int not null auto_increment primary key,
	username varchar(20) not null unique,
	password varchar(20) not null
);
select * from User;

insert into User(username, password) values('gao', 123);