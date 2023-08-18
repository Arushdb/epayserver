package com.dayalbagh.epay.model;

import java.math.BigDecimal;
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
@Table(name = "epay_error_log")
public class Epayerrorlog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	private String ATRN;
	
	@Column(name = "Merchant_Order_Number")
	private String merchantOrderNumber;
	
	
	
	private String amount;
	private String error;
	
	
			
	@Column(name = "insert_time", columnDefinition = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insert_time;
	
		
	@Column(name="createdby")
	private String created_by ;



	public String getATRN() {
		return ATRN;
	}



	


	


	public Date getInsert_time() {
		return insert_time;
	}



	public String getCreated_by() {
		return created_by;
	}



	public void setATRN(String aTRN) {
		ATRN = aTRN;
	}



	


	


	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}



	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}



	public String getError() {
		return error;
	}



	public void setError(String error) {
		this.error = error;
	}



	public String getAmount() {
		return amount;
	}



	public void setAmount(String amount) {
		this.amount = amount;
	}









	public String getMerchantOrderNumber() {
		return merchantOrderNumber;
	}









	public void setMerchantOrderNumber(String merchantOrderNumber) {
		this.merchantOrderNumber = merchantOrderNumber;
	}









	
	
	
	
	

}
