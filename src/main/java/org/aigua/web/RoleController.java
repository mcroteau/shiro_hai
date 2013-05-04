package org.aigua.web;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

import org.aigua.domain.Role;
import org.aigua.dao.RoleDao;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;

import static org.aigua.common.ShiroHaiConstants.*;


@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleDao roleDao;	
	
	private static int RESULTS_PER_PAGE = 10;
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String create(ModelMap model, HttpServletRequest request){
		List<String> roles = new ArrayList<String>();
		roles.add(ADMIN_ROLE);
		
		if (!SecurityUtils.getSubject().hasRole(ADMIN_ROLE)){
	    	System.out.println("\n\nOperation not permitted");
	      	throw new AuthorizationException("No Permission"); 
	    }
	
		model.addAttribute("title", "Create New Role");
		model.addAttribute("addRoleActive", "active");
		return "role/create";
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveRole(ModelMap model, @RequestBody String roleJson){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			Role role = mapper.readValue(roleJson, Role.class);
			Role savedRole = roleDao.save(role);		
			model.addAttribute("role", savedRole);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return "role/action";
	}
	
	
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String edit(ModelMap model,
					   HttpServletRequest request, 
					   @PathVariable String id){
		
		if (!SecurityUtils.getSubject().isPermitted("role:2:edit")){
			System.out.println("\n\nOperation not permitted");
		  	throw new AuthorizationException("No Permission"); 
		}
						
		Role role = roleDao.findById(Integer.parseInt(id));
		model.addAttribute("title", "Edit Role : " + id);
		model.addAttribute("role", role);
		
		return "role/edit";
	}	
	
	
	
	
	@RequestMapping(value="/show/{id}", method=RequestMethod.GET)
	public String show(ModelMap model,
					   HttpServletRequest request, 
					   @PathVariable String id){
				
		Role role = roleDao.findById(Integer.parseInt(id));
		model.addAttribute("title", "Show Role : " + id);
		model.addAttribute("role", role);
		
		return "role/show";
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
		
		List<Role> roles;
		
		if(offset != null) {
			int m = RESULTS_PER_PAGE;
			if(max != null){
				m = Integer.parseInt(max);
			}
			int o = Integer.parseInt(offset);
			roles = roleDao.findAllOffset(m, o);	
		}else{
			roles = roleDao.findAll();	
		} 
		
		int count = roleDao.count();
		
		model.addAttribute("roles", roles);
		model.addAttribute("total", count);
		
		model.addAttribute("title", "List Properties");
		model.addAttribute("resultsPerPage", RESULTS_PER_PAGE);
		model.addAttribute("activePage", page);
		
		return "role/list";
	}
	
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String updateRole(ModelMap model, 
							 @PathVariable String id,
							 @RequestBody String roleJson){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			Role role = mapper.readValue(roleJson, Role.class);
			
			role.setId(Integer.parseInt(id));
			roleDao.update(role);
			
			Role updatedRole = roleDao.findById(Integer.parseInt(id));
			model.addAttribute("updatedRole", updatedRole);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "role/action";
	}
	
	
		
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String deleteRole(ModelMap model, 
						     @PathVariable String id){
			
		System.out.println("\n\n id : " + Integer.parseInt(id) + "\n\n");
		Role role = roleDao.findById(Integer.parseInt(id));	
		
		System.out.println(role);
		roleDao.delete(role.getId());
		
		List<Role> roles = roleDao.findAll();
	 	model.addAttribute("roles", roles);
	
		return "role/action";
		
	}	
	
	

}