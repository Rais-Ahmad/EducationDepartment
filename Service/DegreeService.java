package com.example.EducationDepartment.Service;

import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Degree;
import com.example.EducationDepartment.Repository.DegreeRepository;
@Service
public class DegreeService {
	private static final Logger LOG = LogManager.getLogger(DegreeService.class);
	private final DegreeRepository degreeRepository;

	public DegreeService(DegreeRepository degreeRepository) {

		this.degreeRepository = degreeRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public ResponseEntity<Object> listAllDegree() {
		
		List<Degree> degreeList = degreeRepository.findAll();
		if (degreeList.isEmpty()) {
			LOG.info("List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("List of degrees : " + degreeList);
			return new ResponseEntity<>(degreeList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */
	public ResponseEntity<Object> saveDegree(Degree degree) {

		try {

			Calendar date = Calendar.getInstance();
			degree.setDate(date.getTime());

			if (degree.getName() == null) {
				return new ResponseEntity<>("Degree name can't be empty", HttpStatus.BAD_REQUEST);
			} else {
				LOG.info("Degree saved ");
				return ResponseEntity.ok().body(degreeRepository.save(degree));
			}
		} catch (Exception e) {
			LOG.info("Degree alreday exists ");
			return new ResponseEntity<>("Degree already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 */

	public ResponseEntity<Object> updateDegree(Degree degree) {
		try {
			Calendar date = Calendar.getInstance();
			degree.setUpdatedDate(date.getTime());
			degreeRepository.save(degree);
			LOG.info("Degree updated ");
			return new ResponseEntity<>("Degree updated ", HttpStatus.CONFLICT);
			
		}catch (Exception e) {
			LOG.info("Degree not updated ");
			return new ResponseEntity<>("Degree not found ", HttpStatus.CONFLICT);
		}
	}

}
