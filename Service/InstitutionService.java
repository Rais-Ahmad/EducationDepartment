package com.example.EducationDepartment.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Repository.InstitutionRepository;
import com.example.EducationDepartment.Util.ResponseHandler;

@Service

public class InstitutionService {
	private static final Logger LOG = LogManager.getLogger(InstitutionService.class);
	private final InstitutionRepository institutionRepository;

	public InstitutionService(InstitutionRepository institutionRepository) {

		this.institutionRepository = institutionRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public ResponseEntity<Object> listAllInstitutions() throws ParseException {
		List<Institution> institutionList = institutionRepository.findAll();
		if (institutionList.isEmpty()) {
			LOG.info("List is empty ");
			//return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND,true,"There are no institutions in the database",null);

		} else {
			LOG.info("List of institutions : " + institutionList);
			//return new ResponseEntity<>(institutionList, HttpStatus.OK);
			 return ResponseHandler.generateResponse(HttpStatus.OK,false,"List of All institutions",institutionList);

		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */
	public ResponseEntity<Object> saveInstitution(Institution institution) {

		try {

			Calendar date = Calendar.getInstance();
			institution.setDate(date.getTime());

			if (institution.getName() == null) {
				return new ResponseEntity<>("Institution name can't be empty", HttpStatus.BAD_REQUEST);
			} else {
				return ResponseEntity.ok().body(institutionRepository.save(institution));
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Institution already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 */

	public ResponseEntity<Object> updateInstitution(Institution institution) {
		try {
			Calendar date = Calendar.getInstance();
			institution.setDate(date.getTime());
			institutionRepository.save(institution);
			return new ResponseEntity<>("Institution updated ", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Institution not updated ", HttpStatus.OK);
		}
	}

}
