package com.example.EducationDepartment.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Degree;
import com.example.EducationDepartment.Service.DegreeService;
@RestController
@RequestMapping("/degree")
public class DegreeController {
	private static final Logger LOG = LogManager.getLogger(DegreeController.class);
	@Autowired
	DegreeService degreeService;

	public DegreeController(DegreeService degreeService) {
		this.degreeService = degreeService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allDegree")

	public ResponseEntity<Object> degreeList() {

		return degreeService.listAllDegree();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */
	@PostMapping("/addDegree")

	public ResponseEntity<Object> addDegree(@RequestBody Degree degree) {

		return degreeService.saveDegree(degree);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */

	@PutMapping("/updateDegree")
	public ResponseEntity<Object> updateDegree(@RequestBody Degree degree) {

			return degreeService.updateDegree(degree);
			

	}	

}
