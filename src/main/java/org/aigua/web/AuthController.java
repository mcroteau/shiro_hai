package org.aigua.web;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import java.util.Map;
import java.util.HashMap;
import java.util.Date; 
import java.util.List;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.AuthenticationException;


import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/auth")
public class AuthController{
	
	private Gson gson;
	
	public AuthController(){
		System.out.println("\n\nInitializing AuthController\n\n");
		gson = new Gson();
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(ModelMap model, HttpServletRequest request){
		model.addAttribute("title", "Login");
		return "auth/login";
	}	
	
	
	
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public String authenticate(ModelMap model, @RequestBody String credsString){
		
		System.out.println("AUTHENTICATE ");
		System.out.println(credsString);
		
		model.addAttribute("creds", credsString);
		
		Map<String, String> creds = parse(credsString);
		model.addAttribute("username", creds.get("username"));
		System.out.println(creds);
		
		
		try{
		
			String username = "root";
			String password = "secret";
			
			UsernamePasswordToken token = new UsernamePasswordToken( creds.get("username"), creds.get("password") );
			token.setRememberMe(true);
			
			//With most of Shiro, you'll always want to make sure you're working with the currently executing user, referred to as the subject
			Subject currentUser = SecurityUtils.getSubject();
			
			//Authenticate the subject by passing
			//the user name and password token
			//into the login method
			currentUser.login(token);
			
			System.out.println("\n\n << AUTHENTICATED >> \n\n");
			
			model.addAttribute("currentUser", currentUser.getPrincipal());

			
		} catch ( UnknownAccountException uae ) { 
			uae.printStackTrace();
		} catch ( IncorrectCredentialsException ice ) {
			ice.printStackTrace();
		} catch ( LockedAccountException lae ) { 
			lae.printStackTrace();
		} catch ( ExcessiveAttemptsException eae ) { 
			eae.printStackTrace();
		} catch ( AuthenticationException ae ) {
			ae.printStackTrace();
		}

		return "auth/success";
	}	
	
	
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public @ResponseBody String logout(HttpServletRequest request){
		
		Subject currentUser = SecurityUtils.getSubject();

		//Authenticate the subject by passing
		//the user name and password token
		//into the login method
		currentUser.logout();		
		
		return "logged out";
	}
	
	private Map<String, String> parse(String text){
		Map<String, String> map = new HashMap<String, String>();
		for(String keyValue : text.split(" *& *")) {
		   String[] pairs = keyValue.split(" *= *", 2);
		   map.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
		}
		return map;
	}
	
}


















