package com.example.EducationDepartment.Model.ProjectInterface;

import java.util.List;

public class ResultDTO {

	private String totalMarks;
	private String obtainedMarks;
	private ExamDto exam;

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

	public ExamDto getExam() {
		return exam;
	}

	public void setExam(ExamDto exam) {
		this.exam = exam;
	}
	

}
