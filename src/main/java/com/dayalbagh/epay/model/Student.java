package com.dayalbagh.epay.model;

public class Student {
	
	private String roll_number;
	private String studentname;
	private String status;
	private String programid;
	private String programname;
	private String semester;
	
	
	
	public Student(String roll_number, String studentname, String programid, String programname) {
	
		this.roll_number = roll_number;
		this.studentname = studentname;
		this.programid = programid;
		this.programname = programname;
	}
	
	
	public String getRoll_number() {
		return roll_number;
	}
	public void setRoll_number(String roll_number) {
		this.roll_number = roll_number;
	}
	
	public String getStudentname() {
		return studentname;
	}
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgramid() {
		return programid;
	}
	public void setProgramid(String programid) {
		this.programid = programid;
	}
	public String getProgramname() {
		return programname;
	}
	public void setProgramname(String programname) {
		this.programname = programname;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	
	

}
