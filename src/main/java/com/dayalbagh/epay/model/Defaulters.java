package com.dayalbagh.epay.model;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "defaulters")
public class Defaulters {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "roll_number")
	private String rollnumber;
	
	@Column(name = "semester_code" ,columnDefinition = "char(4)")
	private String semestercode;
	
	
	@Column(name = "program_id")
	private String programid;
	
	
	
	@Column(name = "programname")
	private String programname;
	
	@Column(name = "defaulter" ,columnDefinition = "int")
	private int defaulter;
			
	@Column(name = "insert_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inserttime;
	
	@Column(name = "modified_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedtime;
	
	@Column(name = "createdby")
	private String createdby;
	
	@Column(name = "modifiedby")
	private String modifiedby;
	
	private float fine ;
	private float amount;

	
	public Defaulters() {
		
	}


	public Defaulters(String rollnumber, String semestercode, String programid, String programname, int defaulter) {
		
		this.rollnumber = rollnumber;
		this.semestercode = semestercode;
		this.programid = programid;
		this.programname = programname;
		this.defaulter = defaulter;
	}









	public String getRollnumber() {
		return rollnumber;
	}


	public String getProgramid() {
		return programid;
	}


	public int getDefaulter() {
		return defaulter;
	}


	public Date getInserttime() {
		return inserttime;
	}


	public Date getModifiedtime() {
		return modifiedtime;
	}


	public String getCreatedby() {
		return createdby;
	}


	public String getModifiedby() {
		return modifiedby;
	}


	public void setRollnumber(String rollnumber) {
		this.rollnumber = rollnumber;
	}


	public void setProgramid(String programid) {
		this.programid = programid;
	}


	public void setDefaulter(int defaulter) {
		this.defaulter = defaulter;
	}


	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}


	public void setModifiedtime(Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}


	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}


	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}


	public String getSemestercode() {
		return semestercode;
	}


	public void setSemestercode(String semestercode) {
		this.semestercode = semestercode;
	}




	public String getProgramname() {
		return programname;
	}




	public void setProgramname(String programname) {
		this.programname = programname;
	}


	public float getFine() {
		return fine;
	}


	public float getAmount() {
		return amount;
	}


	public void setFine(float fine) {
		this.fine = fine;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}

	
	
	

}
