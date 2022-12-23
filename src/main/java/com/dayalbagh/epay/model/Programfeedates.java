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
			
	@Column(name = "duedate", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date duedate;
	
	@Column(name = "lastdate", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date lastdate;
	
	
	@Column(name = "cutoffdate", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date cutoffdate;
	
	
	@Column(name = "fee_period_start", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date feeperiodstart;
	
	@Column(name = "fee_period_to", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date feeperiodto;
	
	
	@OneToOne(mappedBy = "pgmfeedates")
	private Studentfeereceipt studentfeereceipt ;

	public Programfeedates() {
		
	}

	
	public Programfeedates(String session, Date duedate, Date lastdate, Date cutoffdate, Date fee_period_start,
			Date fee_period_to, Studentfeereceipt studentfeereceipt) {
		
		this.session = session;
		this.duedate = duedate;
		this.lastdate = lastdate;
		this.cutoffdate = cutoffdate;
		this.feeperiodstart = fee_period_start;
		this.feeperiodto = fee_period_to;
		this.studentfeereceipt = studentfeereceipt;
	}

	
	



	public Programfeedates(int id, Date duedate, Date cutoffdate, Date feeperiodstart, Date feeperiodto) {
		super();
		this.id = id;
		this.duedate = duedate;
		this.cutoffdate = cutoffdate;
		this.feeperiodstart = feeperiodstart;
		this.feeperiodto = feeperiodto;
	}


	public int getId() {
		return id;
	}

	
	

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public Date getCutoffdate() {
		return cutoffdate;
	}

	public void setCutoffdate(Date cutoffdate) {
		this.cutoffdate = cutoffdate;
	}


	public Date getFee_period_start() {
		return feeperiodstart;
	}


	public void setFee_period_start(Date fee_period_start) {
		this.feeperiodstart = fee_period_start;
	}


	public Date getFee_period_to() {
		return feeperiodto;
	}


	public void setFee_period_to(Date fee_period_to) {
		this.feeperiodto = fee_period_to;
	}


	public Studentfeereceipt getStudentfeereceipt() {
		return studentfeereceipt;
	}


	public void setStudentfeereceipt(Studentfeereceipt studentfeereceipt) {
		this.studentfeereceipt = studentfeereceipt;
	}

	


	
	
	
	

}
