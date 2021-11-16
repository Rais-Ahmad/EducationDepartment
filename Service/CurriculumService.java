package com.example.EducationDepartment.Service;

import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Curriculum;
import com.example.EducationDepartment.Repository.CurriculumRepository;

@Service

public class CurriculumService {
	private static final Logger LOG = LogManager.getLogger(CurriculumService.class);
	private final CurriculumRepository curriculumRepository;

	public CurriculumService(CurriculumRepository curriculumRepository) {

		this.curriculumRepository = curriculumRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllUser() {
		List<Curriculum> userList = curriculumRepository.findAll();
		if (userList.isEmpty()) {
			LOG.info("List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("List of Curriculum : " + userList);
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 * @return
	 */
	public ResponseEntity<Object> saveCurriculum(Curriculum curriculum) {

		try {

			if (curriculum.getDiscription() == null) {
				return new ResponseEntity<>("Please Enter description ", HttpStatus.BAD_REQUEST);
			} else if (curriculum.getClass() == null) {
				return new ResponseEntity<>("Please Enter Class name ", HttpStatus.BAD_REQUEST);
			} else {

				Calendar date = Calendar.getInstance();
				curriculum.setDate(date.getTime());
				LOG.info("Curriculum added ");
				return ResponseEntity.ok().body(curriculumRepository.save(curriculum));
			}
		} catch (Exception e) {
			LOG.info("Curriculum not added ");
			return new ResponseEntity<>("Curriculum already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 */

	public ResponseEntity<Object> updateCurriculum(Curriculum curriculum) {
		try {
			Calendar date = Calendar.getInstance();
			curriculum.setUpdatedDate(date.getTime());
			curriculumRepository.save(curriculum);
			LOG.info("Curriculum updated ");
			return new ResponseEntity<>("Curriculum updated", HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Curriculum not updated ");
			return new ResponseEntity<>("Curriculum not updated ", HttpStatus.OK);
		}
	}

}
