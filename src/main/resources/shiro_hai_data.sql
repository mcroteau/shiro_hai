
insert into shiro_user (id, password_hash, username, name, email)
	values (1, '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', 'admin', 'admin@email.com');
insert into shiro_user (id, password_hash, username, name, email)
	values (2, 'dea26157fa355301663174eac368538cff8939f36681d6712dedba439ab98b70', 'customer1', 'customer1', 'customer1@email.com');
insert into shiro_user (id, password_hash, username, name, email)
	values (3, 'c8c7cb5b9e8f7a1b3d1d02602ada62327132391dbe0e8ee07913cd550eea1f3b', 'customer2', 'customer2', 'customer2@email.com');
-- passwords 'admin','customer1','customer2' --
  	

insert into shiro_role (id, name)
	values (1, 'ROLE_ADMIN');
insert into shiro_role (id, name)
	values (2, 'ROLE_CUSTOMER');
		
	
insert into shiro_user_permissions (shiro_user_id, permissions_string)
	values (2, 'user:2:edit');
insert into shiro_user_permissions (shiro_user_id, permissions_string)
	values (2, 'user:2:update');
insert into shiro_user_permissions (shiro_user_id, permissions_string)
	values (3, 'user:3:edit');
insert into shiro_user_permissions (shiro_user_id, permissions_string)
	values (3, 'user:3:update');


insert into shiro_user_roles (shiro_role_id, shiro_user_id)
	values (1, 1);
insert into shiro_user_roles (shiro_role_id, shiro_user_id)
	values (2, 2);
insert into shiro_user_roles (shiro_role_id, shiro_user_id)
	values (2, 3);