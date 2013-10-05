
insert into shiro_user (id, username, password_hash, name, email)
	values (1, 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'admin', 'admin@email.com');
insert into shiro_user (id, username, password_hash, name, email)
	values (2, 'customer1', '8976994d0491e35f74fcac67ede9c83334a6ad34dae07c176df32f10225f93c5077ddd302c02ddd618b2406b1e4dfe50a727cbc880cfe264c552decf2d224ffc', 'customer1', 'customer1@email.com');
insert into shiro_user (id, username, password_hash, name, email)
	values (3, 'customer2', 'd5c94abd970bc35fe58ba3dff6d9419486419f2fb7775d728bd403e8bd05ab845dc41ffb0f7d7daa33e82f3f6753d875bfe37c553224554fabfdb5c8c03119a9', 'customer2', 'customer2@email.com');
	
  	

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