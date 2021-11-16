package com.example.EducationDepartment.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Service.DepartmentService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/department")
public class DepartmentController {
	private static final Logger LOG = LogManager.getLogger(DepartmentController.class);
	DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allDepartments")

	public ResponseEntity<Object> departmentList() {

		return departmentService.listAllUser();

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param department
	 * @return
	 */

	@PostMapping("/addDepartment")

	public ResponseEntity<Object> addDepartment(@RequestBody Department department) {

		return departmentService.saveDepartment(department);
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param department
	 * @return
	 */
	@PutMapping("/updateDepartment")
	public ResponseEntity<Object> updateDepartment(@RequestBody Department department) {

		return departmentService.updateDepartment(department);

	}

}
