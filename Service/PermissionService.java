package com.example.EducationDepartment.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Permissions;
import com.example.EducationDepartment.Repository.PermissionRepository;

import java.util.Calendar;
import java.util.List;

@Service
public class PermissionService {
	private static final Logger LOG = LogManager.getLogger(PermissionService.class);
	private final PermissionRepository permissionRepository;

	public PermissionService(PermissionRepository permissionRepository) {

		this.permissionRepository = permissionRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public ResponseEntity<Object> listAllPermissions() {

		List<Permissions> permissionList = permissionRepository.findAll();

		if (permissionList.isEmpty()) {
			LOG.info("List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("List of permissions : " + permissionList);
			return new ResponseEntity<>(permissionList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param permission
	 * @return
	 */
	public ResponseEntity<Object> savePermissions(Permissions permission) {

		try {

			Calendar date = Calendar.getInstance();
			permission.setCreatedDate(date.getTime());

			if (permission.getName() == null) {
				return new ResponseEntity<>("Permissions name can't be empty", HttpStatus.BAD_REQUEST);
			} else {
				return ResponseEntity.ok().body(permissionRepository.save(permission));
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Permissions already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param permission
	 */

	public ResponseEntity<Object> updatePermissions(Permissions permission) {
		try {
			Calendar date = Calendar.getInstance();
			permission.setUpdatedDate(date.getTime());
			permissionRepository.save(permission);
			LOG.info("Permission updated ");
			return new ResponseEntity<>("Permissions updated ", HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Permission not updated ");
			return new ResponseEntity<>("Permissions not updated ", HttpStatus.OK);
		}
	}
}
