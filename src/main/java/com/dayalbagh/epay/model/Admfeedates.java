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
@Table(name = "adm_fee_dates")
public class Admfeedates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;


	private String applicationnumber;
			
	@Column(name = "program_id")
	private String programid;
	
	private String branchid ;
	private String semester ;
		
	@Column(name = "programname")
	private String programname;
					
	@Column(name = "insert_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inserttime;
			
	@Column(name = "createdby")
	private String createdby;
	
	@Column(name = "modifiedby")
	private String modifiedby;
	
	@Column(name = "modified_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedtime;
	
	@Temporal(TemporalType.DATE)
	private Date lastdate; 
	
	private String learningmode;

	
	public Admfeedates() {
		
	}



	public String getApplicationnumber() {
		return applicationnumber;
	}



	public String getProgramid() {
		return programid;
	}



	public String getProgramname() {
		return programname;
	}



	public Date getInserttime() {
		return inserttime;
	}



	public String getCreatedby() {
		return createdby;
	}



	public String getModifiedby() {
		return modifiedby;
	}



	public void setApplicationnumber(String applicationnumber) {
		this.applicationnumber = applicationnumber;
	}



	public void setProgramid(String programid) {
		this.programid = programid;
	}



	public void setProgramname(String programname) {
		this.programname = programname;
	}



	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}



	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}



	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}



	public Date getModifiedtime() {
		return modifiedtime;
	}



	public void setModifiedtime(Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}



	public Date getLastdate() {
		return lastdate;
	}



	public String getLearningmode() {
		return learningmode;
	}



	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}



	public void setLearningmode(String learningmode) {
		this.learningmode = learningmode;
	}



	public String getBranchid() {
		return branchid;
	}



	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}



	public String getSemester() {
		return semester;
	}



	public void setSemester(String semester) {
		this.semester = semester;
	}


	
	
	
	

}
