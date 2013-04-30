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




-- BELOW IS OUTPUT FROM GRAILS SHIRO QUICK START SETUP

CREATE TABLE shiro_role
(
  id bigint NOT NULL,
  version bigint NOT NULL,
  name character varying(255) NOT NULL,
  CONSTRAINT shiro_role_pkey PRIMARY KEY (id),
  CONSTRAINT shiro_role_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE shiro_role
  OWNER TO postgres;
  

CREATE TABLE shiro_role_permissions
(
  shiro_role_id bigint,
  permissions_string character varying(255),
  CONSTRAINT fk389b46c98ba4b1d FOREIGN KEY (shiro_role_id)
      REFERENCES shiro_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE shiro_role_permissions
  OWNER TO postgres;
	


CREATE TABLE shiro_user
(
  id bigint NOT NULL,
  version bigint NOT NULL,
  password_hash character varying(255) NOT NULL,
  username character varying(255) NOT NULL,
  CONSTRAINT shiro_user_pkey PRIMARY KEY (id),
  CONSTRAINT shiro_user_username_key UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE shiro_user
  OWNER TO postgres;
	  
	  

CREATE TABLE shiro_user_permissions
(
  shiro_user_id bigint,
  permissions_string character varying(255),
  CONSTRAINT fk34555a9eade50efd FOREIGN KEY (shiro_user_id)
      REFERENCES shiro_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE shiro_user_permissions
  OWNER TO postgres;



CREATE TABLE shiro_user_roles
(
  shiro_role_id bigint NOT NULL,
  shiro_user_id bigint NOT NULL,
  CONSTRAINT shiro_user_roles_pkey PRIMARY KEY (shiro_user_id, shiro_role_id),
  CONSTRAINT fkba2210578ba4b1d FOREIGN KEY (shiro_role_id)
      REFERENCES shiro_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkba221057ade50efd FOREIGN KEY (shiro_user_id)
      REFERENCES shiro_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE shiro_user_roles
  OWNER TO postgres;

