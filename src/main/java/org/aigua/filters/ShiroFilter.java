package org.aigua.filters;

import java.io.IOException;
import java.util.Date;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager; 
import org.apache.shiro.util.Factory; 
import org.apache.shiro.config.IniSecurityManagerFactory;

public class ShiroFilter implements Filter {
	
	Factory<SecurityManager> factory;
	SecurityManager securityManager; 

    public void init(FilterConfig config) throws ServletException {
		System.out.println("FILTER -> INIT ");
        String param = config.getInitParameter("auth-provider");
        System.out.println("Param: " + param);
	    factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	    securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
    }


	public void doFilter(ServletRequest req, ServletResponse res,
            			FilterChain chain) throws IOException, ServletException {
		
		System.out.println("FILTER -> DO FILTER ");
 
        HttpServletRequest request = (HttpServletRequest) req;
        String ipAddress = request.getRemoteAddr();
        System.out.println("IP "+ ipAddress + ", Time " + new Date().toString());
		
		//         Subject currentUser = SecurityUtils.getSubject();
		// System.out.println(securityManager);
		//  		System.out.println("current user " + currentUser);
		// if(currentUser.getPrincipal() != null){
		// 	System.out.println("NOT NULL >> " + currentUser.getPrincipal().toString());
		// }
        chain.doFilter(req, res);
    }


    public void destroy() {
		System.out.println("FILTER -> DESTROY ");
    }

}