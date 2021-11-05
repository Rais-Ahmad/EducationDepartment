package com.example.EducationDepartment.Service;

import java.util.Calendar;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Admin;
import com.example.EducationDepartment.Model.Curriculum;
import com.example.EducationDepartment.Model.Degree;
import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Model.Exam;
import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.Teacher;
import com.example.EducationDepartment.Repository.AdminRepository;
import com.example.EducationDepartment.Repository.CurriculumRepository;
import com.example.EducationDepartment.Repository.DegreeRepository;
import com.example.EducationDepartment.Repository.DepartmentRepository;
import com.example.EducationDepartment.Repository.ExamRepository;
import com.example.EducationDepartment.Repository.InstitutionRepository;
import com.example.EducationDepartment.Repository.StudentRepository;
import com.example.EducationDepartment.Repository.TeacherRepository;
import com.example.EducationDepartment.Util.Util;

@Service
public class AdminService {

	private final AdminRepository adminRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final DepartmentRepository departmentRepository;
	private final InstitutionRepository institutionRepository;
	private final CurriculumRepository curriculumRepository;
	private final DegreeRepository degreeRepository;
	private final ExamRepository examRepository;
	private final JavaMailSender javaMailSender;

	private final String ACCOUNT_SID = "AC31b2c9f66d33e1256230d66f8eb72516";

	private final String AUTH_TOKEN = "d79c329739ec39dcf6399b156806132e";

	private final String FROM_NUMBER = "+14135531059";

	public AdminService(AdminRepository adminRepository, StudentRepository studentRepository,
			TeacherRepository teacherRepository, JavaMailSender javaMailSender,
			DepartmentRepository departmentRepository, InstitutionRepository institutionRepository,
			CurriculumRepository curriculumRepository, DegreeRepository degreeRepository,
			ExamRepository examRepository) {

		this.adminRepository = adminRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
		this.departmentRepository = departmentRepository;
		this.institutionRepository = institutionRepository;
		this.curriculumRepository = curriculumRepository;
		this.degreeRepository = degreeRepository;
		this.examRepository = examRepository;
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param admin
	 * @return
	 */

	public ResponseEntity<Object> saveAdmin(Admin admin) {

		try {

			Calendar date = Calendar.getInstance();
			admin.setDate(date.getTime());

			admin.setStatus(false);

			adminRepository.save(admin);
			return new ResponseEntity<>(
					"Registration performed Successfully! Your registration id is :" + admin.getId(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Admin already exist at this E-mail Address ", HttpStatus.CONFLICT);
		}

	}

	public ResponseEntity<Object> sendTokens(Long id) {
		Optional<Admin> admin = adminRepository.findById(id);
		try {
			if (admin.isPresent()) {
				Calendar date = Calendar.getInstance();
				admin.get().setDate(date.getTime());
				Random rnd = new Random();

				admin.get().setEmailToken(rnd.nextInt(999999));
				admin.get().setSmsToken(rnd.nextInt(999999));
				String sms = ("Token: " + admin.get().getSmsToken());
				Util util = new Util();
				String phone = admin.get().getPhone();
				util.send(phone, sms);

				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(admin.get().getEmail(), admin.get().getEmail());
				String eToken = ("Token: " + admin.get().getEmailToken());
				msg.setSubject("Your Token");
				msg.setText(eToken);
				javaMailSender.send(msg);
				long timeInSecs = date.getTimeInMillis();
				Date afterAdding3Mins = new Date(timeInSecs + (3 * 60 * 1000));
				admin.get().setExpirationDate(afterAdding3Mins);

				admin.get().setStatus(false);
				return ResponseEntity.ok().body(adminRepository.save(admin.get()));

			} else
				return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

		} catch (Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param email
	 * @return
	 */
	public Admin getEmail(String email) {
		return adminRepository.findByEmail(email);
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public List<Teacher> listAllTeachersByDate() {
		return teacherRepository.findAllByOrderByDateDesc();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public List<Degree> listAllDegrees() {
		return degreeRepository.findAll();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public List<Student> listAllVerifiedStudents() {
		return studentRepository.findAllByStatus(true);
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public List<Teacher> listAllVerifiedTeachers() {
		return teacherRepository.findAllByStatus(true);
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public List<Student> listAllStudentsByDate() {
		return studentRepository.findAllByOrderByDateDesc();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public List<Admin> listAllAdminsByDate() {
		return adminRepository.findAllByOrderByDateDesc();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @return
	 */
	public Admin getAdmin(long id) {
		return adminRepository.findById(id).get();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */

	public ResponseEntity<Object> verify(long id, int emailToken, int smsToken) {
		try {
			Admin admin = adminRepository.findByIdAndEmailTokenAndSmsToken(id, emailToken, smsToken);

			Calendar date = Calendar.getInstance();
			if (date.getTime().before(admin.getExpirationDate())) {
				admin.setStatus(true);
				System.out.println("Admin is:  " + admin.toString());
				adminRepository.save(admin);
				return new ResponseEntity<>("Admin has been successfully Verified", HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("Admin has not been Verified! Token Expired!", HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Admin is not Verified ", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param admin
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public ResponseEntity<Object> updateAdminName(Admin admin, String firstName, String lastName) {
		try {
			admin.setFirstName(firstName);
			admin.setLastName(lastName);
			Calendar date = Calendar.getInstance();
			admin.setDate(date.getTime());
			adminRepository.save(admin);
			return new ResponseEntity<>("Admin has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Admin is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param admin
	 * @param password
	 * @return
	 */
	public ResponseEntity<Object> updateAdminPassword(Admin admin, String password) {
		try {
			admin.setPassword(password);
			Calendar date = Calendar.getInstance();
			admin.setUpdatedDate(date.getTime());
			adminRepository.save(admin);
			return new ResponseEntity<>("Admin has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Admin is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @return
	 */

	public ResponseEntity<Object> updateStudent(Student student) {
		try {
			Calendar date = Calendar.getInstance();
			student.setUpdatedDate(date.getTime());
			studentRepository.save(student);

			return new ResponseEntity<>("Student has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Student is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @return
	 */

	public ResponseEntity<Object> updateTeacher(Teacher teacher) {
		try {
			Calendar date = Calendar.getInstance();
			teacher.setUpdatedDate(date.getTime());
			teacherRepository.save(teacher);

			return new ResponseEntity<>("Teacher has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Teacher is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param department
	 * @return
	 */
	public ResponseEntity<Object> updateDepartment(Department department) {
		try {
			Calendar date = Calendar.getInstance();
			department.setUpdatedDate(date.getTime());
			departmentRepository.save(department);

			return new ResponseEntity<>("Department has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Department is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 * @return
	 */
	public ResponseEntity<Object> updateCurriculum(Curriculum curriculum) {
		try {
			Calendar date = Calendar.getInstance();
			curriculum.setUpdatedDate(date.getTime());
			curriculumRepository.save(curriculum);

			return new ResponseEntity<>("Curriculum has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Curriculum is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */
	public ResponseEntity<Object> updateInstitution(Institution institution) {
		try {
			Calendar date = Calendar.getInstance();
			institution.setUpdatedDate(date.getTime());

			institutionRepository.save(institution);

			return new ResponseEntity<>("Institution has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Institution is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */
	public ResponseEntity<Object> saveDegree(Degree degree) {

		try {

			Calendar date = Calendar.getInstance();
			degree.setDate(date.getTime());
			return ResponseEntity.ok().body(degreeRepository.save(degree));
		} catch (Exception e) {
			return new ResponseEntity<>("User already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */
	public ResponseEntity<Object> updateDegree(Degree degree) {
		try {
			Calendar date = Calendar.getInstance();
			degree.setUpdatedDate(date.getTime());
			degreeRepository.save(degree);

			return new ResponseEntity<>("Degree has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Degree is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param id
	 * @return
	 */

	public ResponseEntity<Object> deleteTeacher(long id) {
		try {
			Optional<Teacher> teacher = teacherRepository.findById(id);
			if (teacher.isPresent()) {

				teacher.get().setStatus(false);

				Calendar date = Calendar.getInstance();

				teacher.get().setUpdatedDate(date.getTime());
				teacherRepository.save(teacher.get());
				return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("Teacher does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {

			return new ResponseEntity<>("Teacher could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param id
	 * @return
	 */

	public ResponseEntity<Object> deleteStudent(long id) {
		try {
			Optional<Student> student = studentRepository.findById(id);
			if (student.isPresent()) {

				student.get().setStatus(false);

				Calendar date = Calendar.getInstance();

				student.get().setUpdatedDate(date.getTime());
				studentRepository.save(student.get());
				return new ResponseEntity<>("student deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("student does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {

			return new ResponseEntity<>("student could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	public ResponseEntity<Object> verifyDegree(long id) {
		try {
			Optional<Degree> degree = degreeRepository.findById(id);
			if (degree.isPresent()) {
				degree.get().setStatus(true);
				System.out.println("Degree is:  " + degree.toString());
				degreeRepository.save(degree.get());
				return new ResponseEntity<>("Degree has been successfully Verified", HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("Degree has not been Verified!", HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Degree is not Verified ", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param exam
	 * @return
	 */
	public Object saveExam(Exam exam) {

		try {

			Calendar date = Calendar.getInstance();
			exam.setDate(date.getTime());

			return ResponseEntity.ok().body(examRepository.save(exam));
		} catch (Exception e) {
			return new ResponseEntity<>("User already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @return
	 */
	public List<Exam> listAllExams() {
		return examRepository.findAll();
	}

}
