package com.dayalbagh.epay.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "program_fee")
public class ProgramFee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	private float amount;
	
	@Column(name = "semester_code")
	private String semestercode;

	private String programid;
	private String branchid;
	
	@Column(name = "specializationid")
	private String specializationid;
	private String mode;
	private int lab;
	
	
	
	public ProgramFee() {
		
	}
	public float getAmount() {
		return amount;
	}
	public String getSemestercode() {
		return semestercode;
	}
	public String getProgramid() {
		return programid;
	}
	public String getBranchid() {
		return branchid;
	}
	
	public String getMode() {
		return mode;
	}
	public int getLab() {
		return lab;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public void setSemestercode(String semestercode) {
		this.semestercode = semestercode;
	}
	public void setProgramid(String programid) {
		this.programid = programid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setLab(int lab) {
		this.lab = lab;
	}
	public String getSpecializationid() {
		return specializationid;
	}
	public void setSpecializationid(String specializationid) {
		this.specializationid = specializationid;
	}
	
	
	
	
	
}
