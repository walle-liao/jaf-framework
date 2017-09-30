
create database test;
create table t_user(fid varchar(20) primary key, fname varchar(20), fusename varchar(20), fpassword varchar(20));
insert into t_user values('1', '11', '111', '1111');
insert into t_user values('2', '22', '222', '2222');

select * from t_user;