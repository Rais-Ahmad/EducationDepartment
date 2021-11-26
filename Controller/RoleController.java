package com.example.EducationDepartment.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EducationDepartment.Model.Roles;
import com.example.EducationDepartment.Service.RoleService;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/role")

public class RoleController {
	private static final Logger LOG = LogManager.getLogger(RoleController.class);
	@Autowired
	RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allRoles")

	public ResponseEntity<Object> roleList() throws ParseException{

		return roleService.listAllRoles();

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param role
	 * @return
	 */
	@PostMapping("/addRole")

	public ResponseEntity<Object> addRole(@RequestBody Roles role) {

		return roleService.saveRoles(role);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param role
	 * @return
	 */

	@PutMapping("/updateRole")
	public ResponseEntity<Object> updateRole(@RequestBody Roles role) {

		
			return roleService.updateRoles(role);
			

	}	

}