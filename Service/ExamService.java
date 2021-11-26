package com.example.EducationDepartment.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Exam;
import com.example.EducationDepartment.Repository.ExamRepository;
import com.example.EducationDepartment.Util.ResponseHandler;
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

	public ResponseEntity<Object> listAllExam() throws ParseException {
		List<Exam> examList = examRepository.findAll();
		if (examList.isEmpty()) {
			LOG.info("List is empty ");
			//return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND,true,"There are no exams in the database",null);

		} else {
			LOG.info("List of exams : " + examList);
			//return new ResponseEntity<>(examList, HttpStatus.OK);
			 return ResponseHandler.generateResponse(HttpStatus.OK,false,"List of All exams",examList);

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
