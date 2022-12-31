package com.dayalbagh.epay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "fee_dates")
public class Programfeedates {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private String session;

	private String duedate;
	private String lastdate;
	private String cutoffdate;
	private Character semestertype;

	@Column(name = "semester_start_date")
	private String semesterstartdate;

	@Column(name = "semester_end_date")
	private String semesterenddate;

	private int seqno;
	private int status;

//	@Column(name = "fee_period_to", columnDefinition = "Date")
//	@Temporal(TemporalType.DATE)
//	private Date feeperiodto;

	

	public Programfeedates() {

	}

	
	
	
	public Programfeedates(String duedate, String lastdate, String cutoffdate, Character semestertype,
			String semesterstartdate, String semesterenddate, int seqno, int status) {
		
		this.duedate = duedate;
		this.lastdate = lastdate;
		this.cutoffdate = cutoffdate;
		this.semestertype = semestertype;
		this.semesterstartdate = semesterstartdate;
		this.semesterenddate = semesterenddate;
		this.seqno = seqno;
		this.status = status;
	}




	

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	
	public String getDuedate() {
		return duedate;
	}

	public String getLastdate() {
		return lastdate;
	}

	public String getCutoffdate() {
		return cutoffdate;
	}

	public Character getSemestertype() {
		return semestertype;
	}

	public String getSemesterstartdate() {
		return semesterstartdate;
	}

	public String getSemesterenddate() {
		return semesterenddate;
	}

	public int getSeqno() {
		return seqno;
	}

	public int getStatus() {
		return status;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}

	public void setCutoffdate(String cutoffdate) {
		this.cutoffdate = cutoffdate;
	}

	public void setSemestertype(Character semestertype) {
		this.semestertype = semestertype;
	}

	public void setSemesterstartdate(String semesterstartdate) {
		this.semesterstartdate = semesterstartdate;
	}

	public void setSemesterenddate(String semesterenddate) {
		this.semesterenddate = semesterenddate;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
