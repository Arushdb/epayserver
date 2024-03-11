package com.dayalbagh.epay.model;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "certificate")
public class Certificate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	
	
	@Column(name = "roll_number")
	private String rollno;
	
	@Column(name = "enrolment_number")
	private String enrolmentnumber;
	

	private String address;
	
	private String pincode;
	private String mode;
	private String type;
	private String phone;
	private String email;
	

	
	@Column(name = "insert_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inserttime;
	
	
	@OneToMany(mappedBy = "certificate")
	private List<Certificatesemester> certificatesemester;
	
	@OneToOne
	@JoinColumn(name = "payment_id", referencedColumnName = "id")
	private Payment payment;
	@Column(name = "process_status")
	private String processstatus;
	
	@Column(name = "processed_by")
	private String processedby;
	
	@Column(name = "process_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date processtime;
	

	public Certificate() {
		
	}

	


	



	public Certificate(String rollno, String enrolmentnumber, String address, String pincode, String mode, String type,
			String phone, String email, Date inserttime) {
		super();
		this.rollno = rollno;
		this.enrolmentnumber = enrolmentnumber;
		this.address = address;
		this.pincode = pincode;
		this.mode = mode;
		this.type = type;
		this.phone = phone;
		this.email = email;
		this.inserttime = inserttime;
	}








	public String getAddress() {
		return address;
	}

	

	public String getPincode() {
		return pincode;
	}

	public String getMode() {
		return mode;
	}

	public String getType() {
		return type;
	}

	public String getPhone() {
		return phone;
	}

	public Date getInserttime() {
		return inserttime;
	}

	
	public void setAddress(String address) {
		this.address = address;
	}

	

	

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}



	public String getRollno() {
		return rollno;
	}



	public void setRollno(String rollno) {
		this.rollno = rollno;
	}




	public String getEnrolmentnumber() {
		return enrolmentnumber;
	}




	




	public Payment getPayment() {
		return payment;
	}




	public void setEnrolmentnumber(String enrolmentnumber) {
		this.enrolmentnumber = enrolmentnumber;
	}




	




	public void setPayment(Payment payment) {
		this.payment = payment;
	}




	public List<Certificatesemester> getCertificatesemester() {
		return certificatesemester;
	}




	public void setCertificatesemester(List<Certificatesemester> certificatesemester) {
		this.certificatesemester = certificatesemester;
	}




	public int getId() {
		return id;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}








	public String getProcessstatus() {
		return processstatus;
	}








	public String getProcessedby() {
		return processedby;
	}








	public Date getProcesstime() {
		return processtime;
	}








	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}








	public void setProcessedby(String processedby) {
		this.processedby = processedby;
	}








	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
	}
	
	

}
