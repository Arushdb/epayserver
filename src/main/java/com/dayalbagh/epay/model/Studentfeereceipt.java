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

	@Column(name = "roll_number")
	private String rollnumber;
			
	@Column(name = "semester_start_date", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date semester_start_date;
	
	@Column(name = "semester_end_date", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date semester_end_date;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "receipt_id", referencedColumnName = "id")
	private Receipt receipt ;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "program_fee_dates_id", referencedColumnName = "id")
	private Programfeedates pgmfeedates ;
	
	private String remarks;
	@Column(name = "program_id" )
	private String programid;
	
	@Column(name = "semester_code" )
	private String semester;
	
	@Temporal(TemporalType.TIMESTAMP)
	private  Date insert_time ;
	

	public Studentfeereceipt() {
		
	}


	


	public String getRollnumber() {
		return rollnumber;
	}





	public Programfeedates getPgmfeedates() {
		return pgmfeedates;
	}





	public void setRollnumber(String rollnumber) {
		this.rollnumber = rollnumber;
	}





	public void setPgmfeedates(Programfeedates pgmfeedates) {
		this.pgmfeedates = pgmfeedates;
	}





	public Date getSemester_start_date() {
		return semester_start_date;
	}


	public void setSemester_start_date(Date semester_start_date) {
		this.semester_start_date = semester_start_date;
	}


	public Date getSemester_end_date() {
		return semester_end_date;
	}


	public void setSemester_end_date(Date semester_end_date) {
		this.semester_end_date = semester_end_date;
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


	public Studentfeereceipt(String roll_number, Date semester_start_date, Date semester_end_date, Receipt receipt,
			String remarks) {
		super();
		this.rollnumber = roll_number;
		this.semester_start_date = semester_start_date;
		this.semester_end_date = semester_end_date;
		this.receipt = receipt;
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

	

	
	
	
	

}
