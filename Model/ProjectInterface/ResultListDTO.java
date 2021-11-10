package com.example.EducationDepartment.Model.ProjectInterface;

public class ResultListDTO {
	private String totalMarks;
	private String obtainedMarks;
	private String classAndSec;
	private long studentId;
	public String getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(String totalMarks) {
		this.totalMarks = totalMarks;
	}
	public String getObtainedMarks() {
		return obtainedMarks;
	}
	public void setObtainedMarks(String obtainedMarks) {
		this.obtainedMarks = obtainedMarks;
	}
	public String getClassAndSec() {
		return classAndSec;
	}
	public void setClassAndSec(String classAndSec) {
		this.classAndSec = classAndSec;
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	
}
