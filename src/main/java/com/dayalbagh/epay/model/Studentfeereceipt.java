package com.dayalbagh.epay.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "student_fee_receipt")
public class Studentfeereceipt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "referenceno")
	private String rollnumber;

	@Column(name = "semester_start_date", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date semesterstartdate;

	@Column(name = "semester_end_date", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date semesterenddate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "receipt_id", referencedColumnName = "id")
	private Receipt receipt;

	private String remarks;
	@Column(name = "program_id")
	private String programid;

	@Column(name = "semester_code")
	private String semester;

	@Temporal(TemporalType.TIMESTAMP)
	private Date insert_time;
	
	private String reftype ;

	public Studentfeereceipt() {

	}

	public String getRollnumber() {
		return rollnumber;
	}

	public void setRollnumber(String rollnumber) {
		this.rollnumber = rollnumber;
	}



	public Date getSemesterstartdate() {
		return semesterstartdate;
	}

	public Date getSemesterenddate() {
		return semesterenddate;
	}

	public void setSemesterstartdate(Date semesterstartdate) {
		this.semesterstartdate = semesterstartdate;
	}

	public void setSemesterenddate(Date semesterenddate) {
		this.semesterenddate = semesterenddate;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

	public String getProgramid() {
		return programid;
	}

	public String getSemester() {
		return semester;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setProgramid(String programid) {
		this.programid = programid;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	public String getReftype() {
		return reftype;
	}

	public void setReftype(String reftype) {
		this.reftype = reftype;
	}

}
