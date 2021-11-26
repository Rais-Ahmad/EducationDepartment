package com.example.EducationDepartment.Controller;

import java.text.ParseException;
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

import com.example.EducationDepartment.Model.Curriculum;
import com.example.EducationDepartment.Service.CurriculumService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/curriculum")
public class CurriculumController {
	private static final Logger LOG = LogManager.getLogger(DepartmentController.class);
	@Autowired
	CurriculumService curriculumService;

	public CurriculumController(CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	@GetMapping("/allCurriculum")

	public ResponseEntity<Object> curriculumList() throws ParseException {

		return curriculumService.listAllCurriculum();

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 */
	@PostMapping("/addCurriculum")

	public ResponseEntity<Object> addCurriculum(@RequestBody Curriculum curriculum) {

		return curriculumService.saveCurriculum(curriculum);
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 * @return
	 */
	@PutMapping("/updateCurriculum")
	public ResponseEntity<Object> updateCurriculum(@RequestBody Curriculum curriculum) {

		return curriculumService.updateCurriculum(curriculum);

	}

}
