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
	
	@Column(name="mode",columnDefinition="char(3)")
	private String mode;
	@Column(name="branchid",columnDefinition="char(2)")
	private String branchid;
	@Column(name="specializationid",columnDefinition="char(2)")
	private String specializationid;
	
	
	private float amount ;
	private float latefee;
	private float labfee;	
	
	public Student() {
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	public Student(String roll_number, String studentname, String programid, Character type, String programname,
			String mode, String branchid, String specializationid) {
		
		this.roll_number = roll_number;
		this.studentname = studentname;
		this.programid = programid;
		this.type = type;
		this.programname = programname;
		this.mode = mode;
		this.branchid = branchid;
		this.specializationid = specializationid;
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
	
	public Character getType() {
		return type;
	}
	public void setType(Character type) {
		this.type = type;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getMode() {
		return mode;
	}
	public String getBranchid() {
		return branchid;
	}
	public String getSpecializationid() {
		return specializationid;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public void setSpecializationid(String specializationid) {
		this.specializationid = specializationid;
	}
	public String getSemestercode() {
		return semestercode;
	}
	public void setSemestercode(String semestercode) {
		this.semestercode = semestercode;
	}
	public float getLatefee() {
		return latefee;
	}
	public void setLatefee(float latefee) {
		this.latefee = latefee;
	}
	public float getLabfee() {
		return labfee;
	}
	public void setLabfee(float labfee) {
		this.labfee = labfee;
	}
	
	
	

}
