package realm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;  
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.util.Set;

import org.aigua.dao.UserDao;


public class ShiroHaiRealm extends AuthorizingRealm {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CredentialsMatcher credentialsMatcher;
	
	@Value("${auth_query}")
	private String authenticationQuery;

	@Value("${user_roles_query}")
	private String userRolesQuery;
	
	@Value("${user_permissions_query}")
	private String permissionsQuery;
	
    protected boolean permissionsLookupEnabled = true;


    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        if (username == null)  throw new AccountException("Null usernames are not allowed by this realm.");

        String password = userDao.getUserPassword(username);

        if (password == null) throw new UnknownAccountException("No account found for user [" + username + "]");

        AuthenticationInfo info = buildAuthenticationInfo(username, password.toCharArray());

        return info;
    }


    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if (principals == null) throw new AuthorizationException("PrincipalCollection method argument cannot be null.");

        String username = (String) getAvailablePrincipal(principals);

        Set<String> roleNames = userDao.getUserRoles(username);
        Set<String> permissions = null;
        if (permissionsLookupEnabled) permissions = userDao.getUserPermissions(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        
        return info;
    }

    
    protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
        return new SimpleAuthenticationInfo(username, password, getName());
    }

 
}