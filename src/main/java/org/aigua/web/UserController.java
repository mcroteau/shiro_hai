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
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Map;
import java.util.HashMap;
import java.util.Date; 
import java.util.List;
import java.util.ArrayList;

import org.aigua.domain.User;
import org.aigua.dao.UserDao;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserDao userDao;	
	
	private static int RESULTS_PER_PAGE = 10;

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String create(ModelMap model, HttpServletRequest request){
		List<String> roles = new ArrayList<String>();
		roles.add("admin");
		
		if (!SecurityUtils.getSubject().hasRole("admin")){
	    	System.out.println("\n\nOperation not permitted");
	      	throw new AuthorizationException("No Permission"); 
	    }
	
		model.addAttribute("title", "Create New User");
		model.addAttribute("addUserActive", "active");
		return "user/create";
	}


	@RequestMapping(method=RequestMethod.POST)
	public String saveUser(ModelMap model, @RequestBody String userJson){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.readValue(userJson, User.class);
			User savedUser = userDao.save(user);		
			model.addAttribute("user", savedUser);
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return "user/action";
	}
	
	
	@RequestMapping(value="/show/{id}", method=RequestMethod.GET)
	public String show(ModelMap model,
					   HttpServletRequest request, 
					   @PathVariable String id){
		
		User user = userDao.findById(Integer.parseInt(id));
		model.addAttribute("title", "Show User : " + id);
		model.addAttribute("user", user);
		
		return "user/show";
	}	



	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(ModelMap model, 
				       HttpServletRequest request, 
					   @RequestParam(value="offset", required = false ) String offset,
					   @RequestParam(value="max", required = false ) String max,
					   @RequestParam(value="page", required = false ) String page){
		
		if(page == null){
			page = "1";
		}						
		
		List<User> users;
		
		if(offset != null) {
			int m = RESULTS_PER_PAGE;
			if(max != null){
				m = Integer.parseInt(max);
			}
			int o = Integer.parseInt(offset);
			users = userDao.findAllOffset(m, o);	
		}else{
			users = userDao.findAll();	
		} 
		
		int count = userDao.count();
		
		model.addAttribute("users", users);
		model.addAttribute("total", count);
		
		model.addAttribute("title", "List Properties");
		model.addAttribute("resultsPerPage", RESULTS_PER_PAGE);
		model.addAttribute("activePage", page);
		
		return "user/list";
	}
	


	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String updateUser(ModelMap model, 
							 @PathVariable String id,
							 @RequestBody String userJson){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.readValue(userJson, User.class);
			
			user.setId(Integer.parseInt(id));
			userDao.update(user);
			
			User updatedUser = userDao.findById(Integer.parseInt(id));
			model.addAttribute("updatedUser", updatedUser);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "user/action";
	}

	
		
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String deleteUser(ModelMap model, 
						     @PathVariable String id){
			
		System.out.println("\n\n id : " + Integer.parseInt(id) + "\n\n");
		User user = userDao.findById(Integer.parseInt(id));	
		
		System.out.println(user);
		userDao.delete(user.getId());
		
		List<User> users = userDao.findAll();
	 	model.addAttribute("users", users);
	
		return "user/action";
		
	}	
	
	

}