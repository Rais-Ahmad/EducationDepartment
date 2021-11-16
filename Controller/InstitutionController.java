package com.example.EducationDepartment.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Service.InstitutionService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EnableSwagger2
@RestController
@RequestMapping("/institution")
public class InstitutionController {
	private static final Logger LOG = LogManager.getLogger(InstitutionController.class);
	@Autowired
	InstitutionService institutionService;

	public InstitutionController(InstitutionService institutionService) {
		this.institutionService = institutionService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allInstitutions")

	public ResponseEntity<Object> institutionList() {

		return institutionService.listAllInstitutions();

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */
	@PostMapping("/addInstitution")

	public ResponseEntity<Object> addInstitution(@RequestBody Institution institution) {

		return institutionService.saveInstitution(institution);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */

	@PutMapping("/updateInstitute")
	public ResponseEntity<Object> updateInstitution(@RequestBody Institution institution) {

		
			return institutionService.updateInstitution(institution);
			

	}

}
