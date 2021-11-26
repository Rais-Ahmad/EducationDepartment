package com.example.EducationDepartment.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Roles;
import com.example.EducationDepartment.Repository.RoleRepository;
import com.example.EducationDepartment.Util.ResponseHandler;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

@Service
public class RoleService {
	private static final Logger LOG = LogManager.getLogger(RoleService.class);
	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {

		this.roleRepository = roleRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public ResponseEntity<Object> listAllRoles() throws ParseException{
		List<Roles> roleList = roleRepository.findAll();

		if (roleList.isEmpty()) {
			LOG.info("List is empty ");
			//return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND,true,"There are no roles in the database",null);

		} else {
			LOG.info("List of roles : " + roleList);
			//return new ResponseEntity<>(roleList, HttpStatus.OK);
			 return ResponseHandler.generateResponse(HttpStatus.OK,false,"List of All roles",roleList);

		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param role
	 * @return
	 */
	public ResponseEntity<Object> saveRoles(Roles role) {

		try {

			Calendar date = Calendar.getInstance();
			role.setCreatedDate(date.getTime());

			if (role.getName() == null) {
				
				return new ResponseEntity<>("Roles name can't be empty", HttpStatus.BAD_REQUEST);
			} else {
				LOG.info("Role saved ");
				role.setActive(false);
				return ResponseEntity.ok().body(roleRepository.save(role));
			}
		} catch (Exception e) {
			LOG.info("Role already exists ");
			return new ResponseEntity<>("Roles already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param role
	 */

	public ResponseEntity<Object> updateRoles(Roles role) {
		try {
			Calendar date = Calendar.getInstance();
			role.setUpdatedDate(date.getTime());
			roleRepository.save(role);
			LOG.info("Role updated ");
			return new ResponseEntity<>("Role updated ", HttpStatus.OK);
		}catch (Exception e) {
			LOG.info("Role does not exists ");
			return new ResponseEntity<>("Roles does not exist ", HttpStatus.OK);
		}
	}

}
