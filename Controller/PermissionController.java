package com.example.EducationDepartment.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EducationDepartment.Model.Permissions;
import com.example.EducationDepartment.Service.PermissionService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/permission")

public class PermissionController {
	private static final Logger LOG = LogManager.getLogger(PermissionController.class);
	@Autowired
	PermissionService permissionService;

	public PermissionController(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allPermissions")

	public ResponseEntity<Object> permissionList() {

		return permissionService.listAllPermissions();

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param permission
	 * @return
	 */
	@PostMapping("/addPermission")

	public ResponseEntity<Object> addPermission(@RequestBody Permissions permission) {

		return permissionService.savePermissions(permission);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param permission
	 * @return
	 */

	@PutMapping("/updatePermission")
	public ResponseEntity<Object> updatePermission(@RequestBody Permissions permission) {

		return permissionService.updatePermissions(permission);

	}
}
