-- 修改数据库中文乱码
show variables like '%char%';
set character_set_server=utf8;
set character_set_database=utf8;

show databases;
use map;
show tables;

-- 建一个用户表
create table User(
	id int not null auto_increment primary key,
	username varchar(20) not null,
	password varchar(100) not null
);
-- 好友表
create table Friend(
	id int auto_increment primary key,
    username varchar(20) not null,
    friend varchar(20) not null,
    unique index(username, friend)
);
-- 围栏表
create table Fence(
	id int auto_increment primary key,
    username varchar(20) not null,
    latitude double not null,
    longitude double not null,
    radius int not null,
    address varchar(50) not null,
    unique index(username, latitude, longitude, radius)
);
-- 签到表
create table Checkin(
	id int auto_increment primary key,
    username varchar(20) not null,
    latitude double not null,
    longitude double not null,
    address varchar(50) not null,
    dateTime datetime not null,
    description varchar(100)
);
insert into Fence values(2, '1076441856@qq.com', 200, 100, 100, '你好123编码');
-- 解决中文修改了编码集仍然不能插入
ALTER DATABASE map CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Fence CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
show variables like '%char%';
describe Fence;
drop table Fence;
select * from User;
select * from Friend;
select * from Fence;
drop table User;
drop table Friend;
delete  from User where username='1076441856@qq.com';

-- 要使用邮箱，比便加好友功能（需要邮箱验证码验证）
insert into User(username, password) values('1076441856@qq.com', '4QrcOUm6Wau+VuBX8g+IPg==');
insert into User(username, password) values('1635231358@qq.com', 123456);
delete from User where username = '1076441856@qq.com';
delete from Fence where username = '1076441856@qq.com';

alter table User modify password varchar(100);
alter table Fence drop address;
alter table Fence add column address varchar(30) not null;
alter table Fence modify column address varchar(50) not null;
describe User;
describe Fence;
describe Checkin;