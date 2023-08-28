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
	@Column(name = "feestatus")
	private String feestatus;
	
	private Date semester_start_date;
	private Date semester_end_date;
	private String feetype;
	private String entity_id;
	

	
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


	public String getFeestatus() {
		return feestatus;
	}


	public void setFeestatus(String feestatus) {
		this.feestatus = feestatus;
	}


	public Date getSemester_start_date() {
		return semester_start_date;
	}


	public Date getSemester_end_date() {
		return semester_end_date;
	}


	public String getFeetype() {
		return feetype;
	}


	public String getEntity_id() {
		return entity_id;
	}


	public void setSemester_start_date(Date semester_start_date) {
		this.semester_start_date = semester_start_date;
	}


	public void setSemester_end_date(Date semester_end_date) {
		this.semester_end_date = semester_end_date;
	}


	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}


	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	
	
	

}
