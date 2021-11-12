package com.example.EducationDepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EducationDepartment.Model.Curriculum;

/**
 * 
 * @author RaisAhmad
 * @date 29/10/2021
 * @Discription Curriculum Repository
 *
 */

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

}
