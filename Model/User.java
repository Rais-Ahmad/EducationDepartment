package com.example.EducationDepartment.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "t_user", indexes = {@Index(name = "user_cnic",  columnList="cnic"),
		@Index(name = "user_verificationStatus", columnList="verificationStatus" ) })

public class User {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	@Min(value=1, message = "age should be greater than 0")
    @Digits(integer = 3, fraction = 0)
	private int age;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phone;

	@Column(name ="cnic" ,nullable = false, unique = true)
	private String cnic;

	@Column(nullable = false)
	private String address;

	@Column(nullable = true)
	private Date date;

	@Column(nullable = true)
	private Date updatedDate;

	@Column(nullable = true)
	private String classSection;

	@Column(nullable = true)
	private int smsToken;

	@Column(nullable = true)
	private int emailToken;

	@Column(name = "verificationStatus" ,nullable = true)
	private boolean verificationStatus;

	@Column(nullable = true)
	private Date expirationDate;
	
	
	
	/**
	 *  Many To Many relationship between user and roles
	 */
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToMany(targetEntity = Roles.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)

	@JoinTable(name = "t_UserRole", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })

	private List<Roles> roles = new ArrayList<>();
	
	/**
	 * On To Many Relationship between user and result
	 */
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = Result.class, cascade = CascadeType.MERGE)
	@JoinColumn(name = "resultId", referencedColumnName = "id")
	private List<Result> result = new ArrayList<>();
	
	/**
	 * Many To Many Relationship between user and department
	 */
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToMany(targetEntity = Department.class, cascade = { CascadeType.MERGE })

	@JoinTable(name = "t_UserDepartment", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "departmentId") })

	private List<Department> departments = new ArrayList<>();
	
	/**
	 * One to Many Relationship user and degree
	 */
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = Degree.class, cascade = CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "degreeId", referencedColumnName = "id")
	private List<Degree> degree = new ArrayList<>();

	

	
	public List<Degree> getDegree() {
		return degree;
	}

	public void setDegree(List<Degree> degree) {
		this.degree = degree;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getClassSection() {
		return classSection;
	}

	public void setClassSection(String classSection) {
		this.classSection = classSection;
	}

	public int getSmsToken() {
		return smsToken;
	}

	public void setSmsToken(int smsToken) {
		this.smsToken = smsToken;
	}

	public int getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(int emailToken) {
		this.emailToken = emailToken;
	}

	public boolean isVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

}
