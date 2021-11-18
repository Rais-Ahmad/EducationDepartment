package com.example.EducationDepartment.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EducationDepartment.Model.Teacher;
import com.example.EducationDepartment.Model.User;

@Repository
public interface UserRepositry extends JpaRepository<User, Long> {

	User findByEmail(@Param(value = "email") String email);

	List<User> findAllByVerificationStatus(boolean status);

	Optional<User> findByCnic(String cnic);

	Optional<User> findByCnicAndDegree(String cnic, String degree);

	List<User> findAllByOrderByDateDesc();

	Optional<User> findUserByUsername(String username);

	User findByUsername(String username);

	List<User> findAllByClassSection(String classSection);

	Optional<User> findByEmailAndPassword(String email, String password);

	User findByFirstName(String username);

	User findByIdAndEmailTokenAndSmsToken(long id, int emailToken, int smsToken);

}
