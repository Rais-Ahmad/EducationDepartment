package com.example.EducationDepartment.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Repository.ResultRepository;
import com.example.EducationDepartment.Repository.StudentRepository;

@Service
public class ResultService {
private final ResultRepository resultRepository;
	
	public ResultService(ResultRepository resultRepository) {
	
		this.resultRepository = resultRepository;
	}
		/**
		 * @author RaisAhmad
		 * @date 1/11/2021
		 * @return
		 */
	 public List<Result> listAllUser() {
	        return resultRepository.findAll();
	    }
	
}
