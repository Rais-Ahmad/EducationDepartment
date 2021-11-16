package com.example.EducationDepartment.Service;

import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Exam;
import com.example.EducationDepartment.Repository.ExamRepository;
@Service
public class ExamService {
	private static final Logger LOG = LogManager.getLogger(ExamService.class);
	private final ExamRepository examRepository;

	public ExamService(ExamRepository examRepository) {

		this.examRepository = examRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public ResponseEntity<Object> listAllExam() {
		List<Exam> examList = examRepository.findAll();
		if (examList.isEmpty()) {
			LOG.info("List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("List of exams : " + examList);
			return new ResponseEntity<>(examList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param exam
	 * @return
	 */
	public ResponseEntity<Object> saveExam(Exam exam) {

		try {

			Calendar date = Calendar.getInstance();
			exam.setDate(date.getTime());

			if (exam.getDiscription() == null) {
				return new ResponseEntity<>("Exam name can't be empty", HttpStatus.BAD_REQUEST);
			} else {
				LOG.info("Exam added " );
				return ResponseEntity.ok().body(examRepository.save(exam));
			}
		} catch (Exception e) {
			LOG.info("Exam already exists ");
			return new ResponseEntity<>("Exam already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param exam
	 */

	public ResponseEntity<Object> updateExam(Exam exam) {
		try {
			Calendar date = Calendar.getInstance();
			exam.setUpdatedDate(date.getTime());
			examRepository.save(exam);
			return new ResponseEntity<>("Exam updated ", HttpStatus.CONFLICT);
		}catch (Exception e) {
			return new ResponseEntity<>("Exam nt updated ", HttpStatus.CONFLICT);	
		}
	}

}
