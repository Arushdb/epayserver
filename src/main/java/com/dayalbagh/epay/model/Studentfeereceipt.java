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
	@JoinColumn(name = "payment_id", referencedColumnName = "id")
	private Payment payment;

	
	@Column(name = "program_id")
	private String programid;

	@Column(name = "semester_code")
	private String semester;

	@Temporal(TemporalType.TIMESTAMP)
	private Date insert_time;
	

	@Temporal(TemporalType.TIMESTAMP)
	private Date modification_time;
	
	private String reftype ;
	
	@Column(name = "entity_id")
	private String entityid ;
	
	private java.math.BigDecimal  amount ;
	
	private java.math.BigDecimal  latefee ;
	
	private String feetype;
	
	private String createdby;
	
	private String modifiedby;

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

	
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
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

	public String getEntityid() {
		return entityid;
	}

	public java.math.BigDecimal getAmount() {
		return amount;
	}

	public java.math.BigDecimal getLatefee() {
		return latefee;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}

	public void setLatefee(java.math.BigDecimal latefee) {
		this.latefee = latefee;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public Date getModification_time() {
		return modification_time;
	}

	public String getCreatedby() {
		return createdby;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModification_time(Date modification_time) {
		this.modification_time = modification_time;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	

}
