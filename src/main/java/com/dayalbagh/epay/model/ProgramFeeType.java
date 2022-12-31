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
@Table(name = "program_fee_type")
public class ProgramFeeType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "programid")
	private String programid;

	private String feetype;
	
	

	public ProgramFeeType(String programid, String feetype) {
		
		this.programid = programid;
		this.feetype = feetype;
	}

	public String getProgramid() {
		return programid;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setProgramid(String programid) {
		this.programid = programid;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	
	
	
	
}
