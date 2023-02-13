package com.dayalbagh.epay.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "hostel_fee")
public class HostelFee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	private int hostelid;
		
	private String yearmonth;
	
	@Column(name = "fee")
	private float fee;
	
	@Column(name = "insert_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inserttime;
	
	@Column(name = "modification_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modification_time; 
	
	private String created_by;
	
	private String  modified_by;
	
	

	public HostelFee() {
		
	}

	public String getYearmonth() {
		return yearmonth;
	}

	public Date getInserttime() {
		return inserttime;
	}

	

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}

	

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public int getHostelid() {
		return hostelid;
	}

	public void setHostelid(int hostelid) {
		this.hostelid = hostelid;
	}

	public Date getModification_time() {
		return modification_time;
	}

	public String getCreated_by() {
		return created_by;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModification_time(Date modification_time) {
		this.modification_time = modification_time;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	

	


	

		
	

}
