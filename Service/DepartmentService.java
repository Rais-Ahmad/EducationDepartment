package com.example.EducationDepartment.Service;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Repository.DepartmentRepository;

@Service
public class DepartmentService {
	private static final Logger LOG = LogManager.getLogger(DepartmentService.class);
	private final DepartmentRepository departmentRepository;

	public DepartmentService(DepartmentRepository departmentRepository) {

		this.departmentRepository = departmentRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllUser() {

		List<Department> departmentList = departmentRepository.findAll();

		if (departmentList.isEmpty()) {
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("List of departments displayed ");
			return new ResponseEntity<>(departmentList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param department
	 * @return
	 */
	public ResponseEntity<Object> saveDepartment(Department department) {

		try {

			if (department.getInstitution().isEmpty()) {
				return new ResponseEntity<>("Institution can't be empty ", HttpStatus.BAD_REQUEST);
			} else if (department.getCurriculum().isEmpty()) {
				return new ResponseEntity<>("Curriculum can't be empty ", HttpStatus.BAD_REQUEST);
			} else if (department.getName() == null) {
				return new ResponseEntity<>("Please Enter department name ", HttpStatus.BAD_REQUEST);
			} else {

				Calendar date = Calendar.getInstance();
				department.setDate(date.getTime());
				LOG.info("Department added successfully : " + department);
				return ResponseEntity.ok().body(departmentRepository.save(department));
			}
		} catch (Exception e) {
			LOG.info("Department could not not be added ");
			return new ResponseEntity<>("Department already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param department
	 */
	public ResponseEntity<Object> updateDepartment(Department department) {
		try {
			Calendar date = Calendar.getInstance();
			department.setUpdatedDate(date.getTime());
			LOG.info("department updated successfully " + department);
			departmentRepository.save(department);
			LOG.info("Department updated successfully : " + department);
			return new ResponseEntity<>("Department has been successfully Updated", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			LOG.info("Department is not updated ");
			return new ResponseEntity<>("Department is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

}
