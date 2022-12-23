package com.dayalbagh.epay.model;

import javax.persistence.Column;

public class Student {
	

     private long id;
	private String roll_number;
	private String studentname;
	private String status;
	private String programid;
	
	@Column(name="program_type",columnDefinition="char(1)")
	private Character type;
	
	public String getFeepending() {
		return feepending;
	}
	public void setFeepending(String feepending) {
		this.feepending = feepending;
	}
	private String programname;
	@Column(name="semester_code",columnDefinition="char(4)")
	private String semestercode;
	private String feepending;
		
	
	public Student() {
		
		// TODO Auto-generated constructor stub
	}
	public Student(String roll_number, String studentname, String programid, Character type, String programname,
			String semester) {
	
		this.roll_number = roll_number;
		this.studentname = studentname;
		this.programid = programid;
		this.type = type;
		this.programname = programname;
		this.semestercode = semester;
	}
	
	
	
	
	
	public Student(String roll_number, String programname) {
		
		this.roll_number = roll_number;
		this.programname = programname;
	}
	public Student(String roll_number, String programname, String semester_code) {
		
		this.roll_number = roll_number;
		this.programname = programname;
		this.semestercode = semester_code;
	}
	public Student(String roll_number, String studentname, String programid, Character type, String programname) {
		
		this.roll_number = roll_number;
		this.studentname = studentname;
		this.programid = programid;
		this.type = type;
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
		return semestercode;
	}
	public void setSemester(String semester) {
		this.semestercode = semester;
	}
	public Character getType() {
		return type;
	}
	public void setType(Character type) {
		this.type = type;
	}
	
	
	

}
