package com.example.EducationDepartment.Service;

import java.util.Calendar;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Curriculum;
import com.example.EducationDepartment.Repository.CurriculumRepository;

@Service

public class CurriculumService {
	private final CurriculumRepository curriculumRepository;

	public CurriculumService(CurriculumRepository curriculumRepository) {

		this.curriculumRepository = curriculumRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public List<Curriculum> listAllUser() {
		return curriculumRepository.findAll();
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
				return ResponseEntity.ok().body(curriculumRepository.save(curriculum));
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Curriculum already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 */

	public void updateCurriculum(Curriculum curriculum) {
		Calendar date = Calendar.getInstance();
		curriculum.setUpdatedDate(date.getTime());
		curriculumRepository.save(curriculum);
	}

}
