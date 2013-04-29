package org.aigua.realm;

import org.apache.shiro.realm.AuthorizingRealm;

public class ShiroHaiRealm extends AuthorizingRealm {
	
	@Autowired
	private UserDao userDao;
	
	// svn+ssh://kaizen810@svn.kenai.com/shirospring~subversion
	
}