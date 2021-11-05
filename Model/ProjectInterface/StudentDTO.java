package com.example.EducationDepartment.Model.ProjectInterface;

import java.util.List;

public class StudentDTO {

	private String firstName;
	private String lastName;
	private String cnic;
	private int age;
	private String phone;
	private String email;
	private String address;
	private List<DegreeDTO> degree;
	
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
	public String getCnic() {
		return cnic;
	}
	public void setCnic(String cnic) {
		this.cnic = cnic;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<DegreeDTO> getDegree() {
		return degree;
	}
	public void setDegree(List<DegreeDTO> degree) {
		this.degree = degree;
	}
	
		
}
