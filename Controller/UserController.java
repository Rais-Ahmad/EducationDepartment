package com.example.EducationDepartment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.JwtRequest;
import com.example.EducationDepartment.Model.JwtResponse;
import com.example.EducationDepartment.Model.User;
import com.example.EducationDepartment.Model.ProjectInterface.StudentRegistation;
import com.example.EducationDepartment.Service.UserService;
import com.example.EducationDepartment.config.JwtTokenUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	
	private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";
	UserService userService;
	private JwtTokenUtil jwtTokenUtil;
	private AuthenticationManager authenticationManager;
	
	  public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
	        this.userService = userService;
	        this.authenticationManager = authenticationManager;
	        this.jwtTokenUtil = jwtTokenUtil;
	    }
	  
	  public boolean authorization(String token) {
	        
	        return UserController.token.equals(token);
	    }

	  public ResponseEntity<Object> unAuthorizeUser() {
	        return new ResponseEntity<>("Kindly do the authorization first", HttpStatus.UNAUTHORIZED);
	    }
	  
	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param paramEmail
	 * @param paramPassword
	 * @return
	 */
	@GetMapping("/login")

	public ResponseEntity<Object> login(@RequestParam(value = "Email") String email,
			@Parameter @Schema(format = "password") @RequestParam(value = "password") String password) {

		return userService.getUserByNameAndPassword(email, password);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allUsers")

	public ResponseEntity<Object> studentList() {
		return (ResponseEntity<Object>) userService.listAllUsersByDate();

	}

	@PostMapping("/userRegisteration")

	public ResponseEntity<Object> userRegistrationDTO(@RequestBody StudentRegistation user) {

		return userService.registerUser(user);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/userPersonalDetails")

	public ResponseEntity<Object> PersonalDetails() {
		return userService.getPersonalDetails();

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@PutMapping("/updateUser'sName")

	public ResponseEntity<Object> updateName(@RequestHeader String firstName, @RequestHeader String lastName) {

		return userService.updateUserName(firstName, lastName);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param password
	 * @return
	 */
	@PutMapping("/updateUser'sPassword")

	public ResponseEntity<Object> updatePassword(@RequestHeader String password) {
		return userService.updateUserPassword(password);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param phone
	 * @return
	 */
	@PutMapping("/updateUser'sPhone")

	public ResponseEntity<Object> updatePhone(@RequestHeader String phone) {
		return userService.updateUserPhone(phone);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */
	@GetMapping("/Verification")
	public ResponseEntity<Object> verifyAccount(@RequestHeader long id, @RequestHeader int emailToken,
			@RequestHeader int smsToken) {

		return userService.verify(id, emailToken, smsToken);

	}

	/**
	 * @author RaisAhmad
	 * @param id
	 * @return
	 */
	@PutMapping("/sendToken/{id}")
	public ResponseEntity<Object> sendToken(@RequestHeader long id) {

		return userService.sendTokens(id);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 */
	@DeleteMapping("/{id}")

	public ResponseEntity<Object> hideUser(@PathVariable Long id) {

		return userService.hideUser(id);

	}

	/**
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @return
	 */
	@GetMapping("/getResultByStudent")
	public ResponseEntity<Object> getResultByStudentId() {

		return userService.getResultByStudentId();

	}

	/**
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @param id
	 * @return
	 */
	@GetMapping("/degreeVerification")
	public ResponseEntity<Object> verifyDegree(@RequestHeader String degreeName) {

		return userService.verifyDegree(degreeName);

	}

	@GetMapping("/cnicVerification")
	public boolean verifyCNIC(@RequestHeader String cnic) {

		return userService.verifyCNIC(cnic);

	}

	@GetMapping("/qualificationVerification")
	public boolean verifyQualification(@RequestHeader String cnic, @RequestHeader String degreeName) {

		return userService.verifyQualification(cnic, degreeName);

	}

	/**
	 * @author RaisAhmad
	 * @return
	 */
	@GetMapping("/allUsersListByClass")

	public ResponseEntity<Object> StudentListByClass(@RequestHeader String classSection) {

		return userService.listAllStudentsByClass(classSection);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	@GetMapping("/allVerifiedUsers'List")

	public ResponseEntity<Object> verifiedStudentsList() {

		return (ResponseEntity<Object>) userService.listAllVerifiedUsers();

	}

	/**
	 * @author RaisAhmad
	 * @date 11/11/2021
	 * @param studentId
	 * @param degreeId
	 * @return
	 */
	@PutMapping("/updateStudent'sDegreeById")

	public ResponseEntity<Object> updateStudentDegree(@RequestHeader long studentId, @RequestHeader long degreeId) {

		return userService.updateStudentDegree(studentId, degreeId);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @return
	 */
	@PutMapping("/updateAllUserInformation")

	public ResponseEntity<Object> updateUser(@RequestBody User user) {

		return userService.updateUser(user);
	}
	
	  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

	        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

	        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

	        final String token = jwtTokenUtil.generateToken(userDetails);

	        return ResponseEntity.ok(new JwtResponse(token));
	    }

	    private void authenticate(String username, String password) throws Exception {
	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        } catch (DisabledException e) {
	            throw new Exception("USER_DISABLED", e);
	        } catch (BadCredentialsException e) {
	            throw new Exception("INVALID_CREDENTIALS", e);
	        }
	    }

}
