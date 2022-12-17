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
@Table(name = "program_fee_dates")
public class Programfeedates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private String programid;
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
	
	private String mode;
	

	public Programfeedates() {
		
	}

	public Programfeedates(String programid, String session, Date duedate, Date lastdate, Date cutoffdate,
			String mode) {
	
		this.programid = programid;
		this.session = session;
		this.duedate = duedate;
		this.lastdate = lastdate;
		this.cutoffdate = cutoffdate;
		this.mode = mode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProgramid() {
		return programid;
	}

	public void setProgramid(String programid) {
		this.programid = programid;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}


	
	
	
	

}
