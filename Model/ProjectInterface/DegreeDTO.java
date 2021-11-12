package com.example.EducationDepartment.Model.ProjectInterface;

/**
 * DegreeDTO
 * @author RaisAhmad
 *
 */

public class DegreeDTO {

	private String name;
	private String studentCnic;
	private boolean verificationStatus;

	public String getStudentCnic() {
		return studentCnic;
	}

	public void setStudentCnic(String studentCnic) {
		this.studentCnic = studentCnic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

}
