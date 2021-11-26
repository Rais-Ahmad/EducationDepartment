package com.example.EducationDepartment.Controller;

import java.text.ParseException;
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

import com.example.EducationDepartment.Model.Permissions;
import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Service.ResultService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/result")
public class ResultController {
	private static final Logger LOG = LogManager.getLogger(ResultController.class);
	@Autowired
	ResultService resultService;

	public ResultController(ResultService resultService) {
		this.resultService = resultService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allResults")

	public ResponseEntity<Object> allResults() throws ParseException {

		return (ResponseEntity<Object>) resultService.listAllResults();

	}
	
	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param permission
	 * @return
	 */
	@PostMapping("/addResult")

	public ResponseEntity<Object> addResult(@RequestBody Result result) {

		return resultService.saveResult(result);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param result
	 * @return
	 */

	@PutMapping("/updateResult")
	public ResponseEntity<Object> updateResult(@RequestBody Result result) {

		try {
			resultService.updateStudentMarks(result);
			LOG.info("Result updated successfully:  " + result);
			return new ResponseEntity<>("Result updated successfully ", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<>("Result not found incorrect id ", HttpStatus.NOT_FOUND);
		}

	}	


}
