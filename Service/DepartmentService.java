package com.example.EducationDepartment.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Repository.DepartmentRepository;

@Service
public class DepartmentService {
	 private final DepartmentRepository departmentRepository;
		
	 public DepartmentService(DepartmentRepository departmentRepository) {
	
		this.departmentRepository = departmentRepository;
	 }
	 

	 /**
	  * @author RaisAhmad
	  * @date 29/10/2021
	  * @return
	  */
	 public List<Department> listAllUser() {
	        return departmentRepository.findAll();
	    }
	 
	 /**
	  * @author RaisAhmad
	  * @date 29/10/2021
	  * @param department
	  * @return
	  */
	 public Object saveDepartment(Department department) {
         
	        try {
	        	
	        	Calendar date = Calendar.getInstance();
				department.setDate(date.getTime());
	            return ResponseEntity.ok().body(departmentRepository.save(department));
	        } catch (Exception e) {
	            return new ResponseEntity<>("User already exist ", HttpStatus.CONFLICT);
	        }
	        
	    }
	 
	 /**
	  * @author RaisAhmad
	  * @date 29/10/2021
	  * @param department
	  */
	  public void updateDepartment(Department department){
		  Calendar date = Calendar.getInstance();
			department.setUpdatedDate(date.getTime());
	        departmentRepository.save(department);
	    }
	  

}
