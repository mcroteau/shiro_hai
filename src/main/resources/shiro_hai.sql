create table users (
	user_id integer  not null,
	username varchar(45) not null,
	password varchar(45) not null,
	enabled boolean not null,
	first_name varchar(75) not null,
	last_name varchar(75) not null,
	email varchar(75) not null
);
alter table users add primary key (user_id);

create table user_roles (
	user_role_id integer not null,
	user_id integer not null,
	authority varchar(45) not null
);
alter table user_roles add primary key (user_role_id);

create table role_permissions (
	role_permission_id integer not null,
	role_id integer not null,
	permission varchar(75) not null
);
alter table user_roles add primary key (user_role_id);


insert into users (user_id, username, password, enabled, first_name, last_name, email)
values (1, 'admin', 'admin', true, 'Admin', 'Admin', 'admin@email.com');
 
insert into user_roles (user_role_id, user_id, authority)
values (1, 1, 'ROLE_ADMIN');

insert into user_roles (user_role_id, user_id, authority)
values (1, 1, 'ROLE_ADMIN');