package com.example.EducationDepartment.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.User;
import com.example.EducationDepartment.Model.ProjectInterface.ResultDTO;
import com.example.EducationDepartment.Model.ProjectInterface.StudentDTO;
import com.example.EducationDepartment.Model.ProjectInterface.StudentRegistation;
import com.example.EducationDepartment.Model.ProjectInterface.StudentResultDto;
import com.example.EducationDepartment.Model.Degree;
import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Repository.CurriculumRepository;
import com.example.EducationDepartment.Repository.DegreeRepository;
import com.example.EducationDepartment.Repository.DepartmentRepository;
import com.example.EducationDepartment.Repository.ExamRepository;
import com.example.EducationDepartment.Repository.InstitutionRepository;
import com.example.EducationDepartment.Repository.ResultRepository;
import com.example.EducationDepartment.Repository.UserRepositry;
import com.example.EducationDepartment.Util.Util;

@Service
public class UserService {
	private static final Logger LOG = LogManager.getLogger(UserService.class);
	private final UserRepositry userRepository;
	private final DepartmentRepository departmentRepository;
	private final InstitutionRepository institutionRepository;
	private final CurriculumRepository curriculumRepository;
	private final DegreeRepository degreeRepository;
	private final ResultRepository resultRepository;
	private final ExamRepository examRepository;
	private final JavaMailSender javaMailSender;
	private static boolean isLogin = false;
	private static long idd;

	private final String ACCOUNT_SID = "AC31b2c9f66d33e1256230d66f8eb72516";

	private final String AUTH_TOKEN = "878e85a8be95077b40d9ab4e9856f25b";

	private final String FROM_NUMBER = "+14135531059";

	public UserService(UserRepositry userRepository, InstitutionRepository institutionRepository,
			ExamRepository examRepository, DepartmentRepository departmentRepository, DegreeRepository degreeRepository,
			CurriculumRepository curriculumRepository, JavaMailSender javaMailSender,
			ResultRepository resultRepository) {

		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
		this.institutionRepository = institutionRepository;
		this.curriculumRepository = curriculumRepository;
		this.degreeRepository = degreeRepository;
		this.resultRepository = resultRepository;
		this.examRepository = examRepository;
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @param user
	 * @return
	 */
	public ResponseEntity<Object> saveUser(User user) {

		try {

			Calendar date = Calendar.getInstance();
			user.setDate(date.getTime());
			if (user.getFirstName() == null) {
				return new ResponseEntity<>("First name can't be empty", HttpStatus.BAD_REQUEST);
			} else if (user.getLastName() == null) {
				return new ResponseEntity<>("Last name can't be empty", HttpStatus.BAD_REQUEST);
			} else if (user.getAddress() == null) {
				return new ResponseEntity<>("Address can't be empty", HttpStatus.BAD_REQUEST);
			} else if (user.getAge() == 0) {
				return new ResponseEntity<>("Age can't be empty", HttpStatus.BAD_REQUEST);
			} else if (user.getPassword() == null) {
				return new ResponseEntity<>("Password can't be empty", HttpStatus.BAD_REQUEST);
			} else if (user.getCnic() == null) {
				return new ResponseEntity<>("CNIC can't be empty", HttpStatus.BAD_REQUEST);
			} else if (user.getPhone() == null) {
				return new ResponseEntity<>("Phone can't be empty", HttpStatus.BAD_REQUEST);
			} else if (user.getEmail() == null) {
				return new ResponseEntity<>("E-mail can't be empty", HttpStatus.BAD_REQUEST);
			} else {
				String cnic;
				cnic = user.getCnic();

				user.setVerificationStatus(false);
				userRepository.save(user);

				return new ResponseEntity<>(
						"Registration performed Successfully! Your registration id is :" + user.getId(), HttpStatus.OK);

			}
		} catch (Exception e) {

			return new ResponseEntity<>("User already exist at this E-mail Address or CNIC ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public ResponseEntity<Object> listAllUsersByDate() {

		List<User> userList = userRepository.findAllByOrderByDateDesc();
		if (userList.isEmpty()) {
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param email
	 * @return
	 */
	public User getEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public ResponseEntity<Object> getUserByNameAndPassword(String email, String password) {
		try {
			Optional<User> user = userRepository.findByEmailAndPassword(email, password);
			if (user.isPresent()) {
				isLogin = true;
				idd = user.get().getId();
				return new ResponseEntity<>("login successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("incorrect login details, Login failed", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("error is" + e.getCause() + " " + e.getMessage());

			return new ResponseEntity<>("Incorrect credentials ", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @return
	 */

	public ResponseEntity<Object> registerUser(StudentRegistation studentRegistation) {

		try {
			User user = new User();

			if (studentRegistation.getFirstName() == null) {
				return new ResponseEntity<>("First name can't be empty", HttpStatus.BAD_REQUEST);
			} else if (studentRegistation.getLastName() == null) {
				return new ResponseEntity<>("Last name can't be empty", HttpStatus.BAD_REQUEST);
			} else if (studentRegistation.getAddress() == null) {
				return new ResponseEntity<>("Address can't be empty", HttpStatus.BAD_REQUEST);
			} else if (studentRegistation.getAge() == 0) {
				return new ResponseEntity<>("Age can't be empty", HttpStatus.BAD_REQUEST);
			} else if (studentRegistation.getPassword() == null) {
				return new ResponseEntity<>("Password can't be empty", HttpStatus.BAD_REQUEST);
			} else if (studentRegistation.getCnic() == null) {
				return new ResponseEntity<>("CNIC can't be empty", HttpStatus.BAD_REQUEST);
			} else if (studentRegistation.getPhone() == null) {
				return new ResponseEntity<>("Phone can't be empty", HttpStatus.BAD_REQUEST);
			} else if (studentRegistation.getEmail() == null) {
				return new ResponseEntity<>("E-mail can't be empty", HttpStatus.BAD_REQUEST);
			} else {

				Calendar date = Calendar.getInstance();
				user.setDate(date.getTime());
				user.setFirstName(studentRegistation.getFirstName());
				user.setLastName(studentRegistation.getLastName());
				user.setAddress(studentRegistation.getAddress());
				user.setAge(studentRegistation.getAge());
				user.setCnic(studentRegistation.getCnic());
				user.setEmail(studentRegistation.getEmail());
				user.setPassword(studentRegistation.getPassword());
				user.setPhone(studentRegistation.getPhone());
				user.setClassSection(studentRegistation.getClassSection());
//				user.setDepartments(studentRegistation.getDepartments());
				user.setRoles(studentRegistation.getRoles());

				user.setVerificationStatus(false);

				userRepository.save(user);
				LOG.info("user added successfully : " + user);
				return new ResponseEntity<>(
						"Registration performed Successfully! Your registration id is :" + user.getId(), HttpStatus.OK);
			}

		} catch (NumberFormatException n) {
			return new ResponseEntity<>("Enter a number in age ", HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("user is not added ");

			return new ResponseEntity<>("user already exist at this E-mail Address or CNIC", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @return
	 */
	public User getUser(long id) {
		return userRepository.findById(id).get();
	}

	public ResponseEntity<Object> getPersonalDetails() {

		if (isLogin) {
			try {

				Optional<User> user = userRepository.findById(idd);
				if (user.isPresent()) {
					StudentDTO studentDTO = new StudentDTO();
					studentDTO.setFirstName(user.get().getFirstName());
					studentDTO.setLastName(user.get().getLastName());
					studentDTO.setCnic(user.get().getCnic());
					studentDTO.setAddress(user.get().getAddress());
					studentDTO.setEmail(user.get().getEmail());
					studentDTO.setAge(user.get().getAge());
					studentDTO.setPhone(user.get().getPhone());

					LOG.info("user DTO displayed  ");
					return ResponseEntity.ok().body(studentDTO);
				} else
					return new ResponseEntity<>("user not Found ", HttpStatus.BAD_REQUEST);

			} catch (NoSuchElementException e) {

				return new ResponseEntity<>("user not found, incorrect id ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public ResponseEntity<Object> updateUserName(String firstName, String lastName) {
		if (isLogin) {

			try {
				User user = userRepository.getById(idd);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				Calendar date = Calendar.getInstance();
				user.setUpdatedDate(date.getTime());
				userRepository.save(user);
				LOG.info("user updated successfully : " + user);
				return new ResponseEntity<>("user has been successfully Updated", HttpStatus.CREATED);

			} catch (NoSuchElementException e) {

				return new ResponseEntity<>("User not updated ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param password
	 * @return
	 */

	public ResponseEntity<Object> updateUserPassword(String password) {
		if (isLogin) {

			try {
				User user = userRepository.getById(idd);
				user.setPassword(password);
				Calendar date = Calendar.getInstance();
				user.setUpdatedDate(date.getTime());
				userRepository.save(user);
				LOG.info("Student password is updated :  " + user);
				return new ResponseEntity<>("Student's password has been successfully Updated", HttpStatus.CREATED);

			} catch (NoSuchElementException e) {

				return new ResponseEntity<>("User not updated ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param phone
	 * @return
	 */

	public ResponseEntity<Object> updateUserPhone(String phone) {
		if (isLogin) {

			try {
				User user = userRepository.getById(idd);
				user.setPhone(phone);
				Calendar date = Calendar.getInstance();
				user.setUpdatedDate(date.getTime());
				userRepository.save(user);
				LOG.info("Student phne number is updated :  " + user);
				return new ResponseEntity<>("Student's phne number has been successfully Updated", HttpStatus.CREATED);

			} catch (NoSuchElementException e) {

				return new ResponseEntity<>("User not updated ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */

	public ResponseEntity<Object> sendTokens(Long id) {
		Optional<User> user = userRepository.findById(id);
		try {
			if (user.isPresent()) {
				Calendar date = Calendar.getInstance();
				user.get().setDate(date.getTime());
				Random rnd = new Random();

				user.get().setEmailToken(rnd.nextInt(999999));
				user.get().setSmsToken(rnd.nextInt(999999));
				String sms = ("Token: " + user.get().getSmsToken());
				Util util = new Util();
				String phone = user.get().getPhone();
				util.send(phone, sms);

				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(user.get().getEmail(), user.get().getEmail());
				String eToken = ("Token: " + user.get().getEmailToken());
				msg.setSubject("Your Token");
				msg.setText(eToken);
				javaMailSender.send(msg);
				long timeInSecs = date.getTimeInMillis();
				Date afterAdding3Mins = new Date(timeInSecs + (3 * 60 * 1000));
				user.get().setExpirationDate(afterAdding3Mins);

				user.get().setVerificationStatus(false);
				LOG.info("Tokens sent successfully ");
				return ResponseEntity.ok().body(userRepository.save(user.get()));

			} else
				return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

		} catch (Exception exception) {
			LOG.info("Tokens can't be sent ");
			return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */
	public ResponseEntity<Object> verify(long id, int emailToken, int smsToken) {
		try {
			User user = userRepository.findByIdAndEmailTokenAndSmsToken(id, emailToken, smsToken);

			Calendar date = Calendar.getInstance();
			if (date.getTime().before(user.getExpirationDate())) {
				user.setVerificationStatus(true);
				System.out.println("User is:  " + user.toString());
				userRepository.save(user);
				LOG.info("User verified successfully : " + user);
				return new ResponseEntity<>("User has been successfully Verified", HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("User has not been Verified! Token Expired!", HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			LOG.info("User can not be verified ");
			return new ResponseEntity<>("User is not Verified ", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 */

	public ResponseEntity<Object> deleteUser(long id) {
		try {
			userRepository.deleteById(id);
			LOG.info("Student deleted successfully ");
			return new ResponseEntity<Object>("Student deleted successfully! ", HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Student can't be deleted ");
			return new ResponseEntity<Object>("Student not found! ", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param id
	 * @return
	 */

	public ResponseEntity<Object> hideUser(long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {

				user.get().setVerificationStatus(false);

				Calendar date = Calendar.getInstance();

				user.get().setUpdatedDate(date.getTime());
				userRepository.save(user.get());
				LOG.info("User deleted successfully  ");
				return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("User does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.info("User could not be deleted ");
			return new ResponseEntity<>("User could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	/**
	 * @author RaisAhmad
	 * @date 11/11/2021
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> getResultByStudentId() {
		if (isLogin) {
			try {

				Optional<User> user = userRepository.findById(idd);
				if (user.isPresent()) {
					StudentResultDto studentResultDTO = new StudentResultDto();
					studentResultDTO.setFirstName(user.get().getFirstName());
					studentResultDTO.setLastNamString(user.get().getLastName());
					studentResultDTO.setCnic(user.get().getCnic());
					ResultDTO resultDTO = new ResultDTO();
					List<ResultDTO> resultDTOs = new ArrayList<ResultDTO>();
					Result newResult = new Result();

					for (Result result : user.get().getResult()) {
						resultDTO.setObtainedMarks(result.getObtainedMarks());
						resultDTO.setTotalMarks(result.getTotalMarks());
						resultDTO.setClassAndSec(result.getClassAndSec());
						resultDTOs.add(resultDTO);

					}

					studentResultDTO.setResult(resultDTOs);
					LOG.info("Student's result has been displayed ");
					return ResponseEntity.ok().body(studentResultDTO);
				} else
					return new ResponseEntity<>("Student not Found ", HttpStatus.BAD_REQUEST);

			} catch (NoSuchElementException e) {

				return new ResponseEntity<>("User not found incorrect id ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @param id
	 * @param degreeName
	 * @return
	 */

	public ResponseEntity<Object> verifyDegree(String degreeName) {
		if (isLogin) {
			try {

				Optional<User> user = userRepository.findById(idd);

				if (user.isPresent()) {
					user.get().getDegree();
					for (Degree degree : user.get().getDegree()) {
						if (degree.getName().equals(degreeName)) {

							degree.setStatus(true);
							degreeRepository.save(degree);

							LOG.info("Degree verified ");
							return new ResponseEntity<>("Degree Verified ", HttpStatus.OK);
						}
					}
					return new ResponseEntity<>("Degree not found ", HttpStatus.NOT_FOUND);
				} else
					return new ResponseEntity<>("Student not found ", HttpStatus.NOT_FOUND);

			} catch (Exception e) {
				LOG.info("Degree not been verified ");
				return new ResponseEntity<>("Degree not Verified ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>("You are not lgged in yet!  ", HttpStatus.BAD_REQUEST);
	}

	/**
	 * @author RaisAhmad
	 * @date 11/11/2021
	 * @param cnic
	 * @return
	 */
	public boolean verifyCNIC(String cnic) {

		try {

			Optional<User> user = userRepository.findByCnic(cnic);

			if (user.isPresent()) {
				LOG.info("CNIC verified ");
				return true;
			} else
				return false;

		} catch (Exception e) {
			LOG.info("CNIC not verified ");
			return false;

		}

	}

	/**
	 * @author RaisAhmad
	 * @date 11/11/2021
	 * @param cnic
	 * @param degreeName
	 * @return
	 */
	public boolean verifyQualification(String cnic, String degreeName) {

		try {

			Optional<User> user = userRepository.findByCnic(cnic);

			if (user.isPresent()) {
				user.get().getDegree();
				for (Degree degree : user.get().getDegree()) {
					if (degree.getName().equals(degreeName)) {
						LOG.info("Qualification verified ");
						return true;
					}
				}
				return false;
			} else
				return false;

		} catch (Exception e) {
			LOG.info("Qualification not been verified ");
			return false;
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 11/11/2021
	 * @param studentId
	 * @param degreeId
	 * @return
	 */
	public ResponseEntity<Object> updateStudentDegree(long studentId, long degreeId) {
		if (isLogin) {
			try {

				Optional<User> user = userRepository.findById(studentId);
				if (user.isPresent()) {
					Optional<Degree> newDegree = degreeRepository.findById(degreeId);

					List<Degree> degreeList = degreeRepository.findAll();
					List<Degree> degreeDTOs = new ArrayList<Degree>();

					if (newDegree.isPresent()) {
						for (Degree degree : degreeList) {

							if (user.get().getCnic().equals(newDegree.get().getStudentCnic())) {
								Degree degree1 = new Degree();
								degree1.setName(newDegree.get().getName());
								degree1.setStudentCnic(newDegree.get().getStudentCnic());
								System.out.println(newDegree.get().getStudentCnic());
								degree1.setStatus(newDegree.get().isStatus());
								degree1.setId(newDegree.get().getId());
								degree1.setDate(newDegree.get().getDate());

								degreeDTOs.add(degree1);

								user.get().setDegree(degreeDTOs);
								userRepository.save(user.get());

								return new ResponseEntity<>("Degree added Successfully ", HttpStatus.OK);
							}

						}

						return new ResponseEntity<>("This degree do not belongs to provided Student Id ",
								HttpStatus.OK);
					}

					return new ResponseEntity<>("Degree not found! ", HttpStatus.OK);
				}

				return new ResponseEntity<>("Student not found ", HttpStatus.OK);
			} catch (Exception e) {
				LOG.info("Degree coud not be added ");
				return new ResponseEntity<>("Degree could not be added ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @param classSection
	 * @return
	 */
	public ResponseEntity<Object> listAllStudentsByClass(String classSection) {

		if (isLogin) {
			List<User> userList = userRepository.findAllByClassSection(classSection);
			if (userList.isEmpty()) {
				LOG.info("Student List is empty ");
				return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			} else {
				LOG.info("Student List : " + userList);
				return new ResponseEntity<>(userList, HttpStatus.OK);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllVerifiedUsers() {

		if (isLogin) {

			List<User> userList = userRepository.findAllByVerificationStatus(true);
			if (userList.isEmpty()) {
				LOG.info("Student List is empty ");
				return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			} else {
				LOG.info("Student List : " + userList);
				return new ResponseEntity<>(userList, HttpStatus.OK);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @return
	 */

	public ResponseEntity<Object> updateUser(User user) {
		if (isLogin) {

			try {
				Calendar date = Calendar.getInstance();
				user.setUpdatedDate(date.getTime());
				userRepository.save(user);
				LOG.info("user updated successfully : " + user);
				return new ResponseEntity<>("user has been successfully Updated", HttpStatus.CREATED);
			} catch (NoSuchElementException e) {
				LOG.info("user not updated ");
				return new ResponseEntity<>("user is not Updated", HttpStatus.BAD_REQUEST);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

}
