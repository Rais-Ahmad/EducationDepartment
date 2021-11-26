package com.example.EducationDepartment.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Model.Roles;
import com.example.EducationDepartment.Model.ProjectInterface.ResultListDTO;
import com.example.EducationDepartment.Repository.ResultRepository;
import com.example.EducationDepartment.Util.ResponseHandler;

@Service
public class ResultService {
	private static final Logger LOG = LogManager.getLogger(ResultService.class);
	private final ResultRepository resultRepository;

	public ResultService(ResultRepository resultRepository) {

		this.resultRepository = resultRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllResults() throws ParseException {
		List<Result> resultList = resultRepository.findAll();
		if (resultList.isEmpty()) {
			LOG.info("Result List is empty ");
			//return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND,true,"There are no results in the database",null);

		} else {

			List<ResultListDTO> resultDTOs = new ArrayList<ResultListDTO>();
			

			for (Result result : resultList) {
				ResultListDTO resultDTO = new ResultListDTO();

				resultDTO.setObtainedMarks(result.getObtainedMarks());
				resultDTO.setTotalMarks(result.getTotalMarks());
				resultDTO.setClassAndSec(result.getClassAndSec());
				resultDTO.setStudentId(result.getStudentId());

				resultDTOs.add(resultDTO);
			}

			LOG.info("Result list :  " + resultList);
			//return new ResponseEntity<>(resultDTOs, HttpStatus.OK);
			 return ResponseHandler.generateResponse(HttpStatus.OK,false,"List of All results",resultDTOs);

		}
	}

	/**
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @param result
	 * @return
	 */
	public ResponseEntity<Object> saveResult(Result result) {

		try {

			Calendar date = Calendar.getInstance();
			result.setDate(date.getTime());
			if (result.getClassAndSec() == null) {
				return new ResponseEntity<>("Class & Group name can't be empty", HttpStatus.BAD_REQUEST);
			} else if (result.getStudentId() == 0) {
				return new ResponseEntity<>("Student Id be empty", HttpStatus.BAD_REQUEST);
			} else if (result.getObtainedMarks() == null) {
				return new ResponseEntity<>("Obtained Marks can't be null", HttpStatus.BAD_REQUEST);
			} else if (result.getTotalMarks() == null) {
				return new ResponseEntity<>("Total Marks can't be null", HttpStatus.BAD_REQUEST);
			} else if (result.getExam().isEmpty()) {
				return new ResponseEntity<>("Enter Exam details ", HttpStatus.BAD_REQUEST);
			} else {
				LOG.info("Result added successfully : " + result);
				return ResponseEntity.ok().body(resultRepository.save(result));
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Result already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param result
	 * @return
	 */

	public ResponseEntity<Object> updateStudentMarks(Result result) {
		try {

			Calendar date = Calendar.getInstance();

			result.setDate(date.getTime());
			resultRepository.save(result);
			LOG.info("Result updated successfully : " + result);
			return new ResponseEntity<>("Result has been successfully Updated", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Result is not Updated", HttpStatus.BAD_REQUEST);
		}
	}
}
