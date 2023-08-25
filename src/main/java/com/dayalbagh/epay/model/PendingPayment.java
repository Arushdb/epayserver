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
@Table(name = "pending_payments")
public class PendingPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	private String ATRN;
	private String Merchant_Order_Number;
	@Column(name="Amount", precision = 17 ,scale = 3)
	private BigDecimal amount;
	@Column(name = "trx_status")
	private String trxstatus;
	
	@Column(name = "process_time", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date process_time;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_id", referencedColumnName = "id")
	private Payment payment;
		
	@Column(name = "insert_time", columnDefinition = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insert_time;
	
	@Column(name = "modification_time", columnDefinition = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modification_time;
	
	
	private String created_by ;
	@Column(name = "modify_by")
	private String modifiedBy;
	
	
	

	public String getCreated_by() {
		return created_by;
	}




	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}




	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}




	public PendingPayment() {
		
	}

	


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	

	
	public Date getInsert_time() {
		return insert_time;
	}

	
	

	

	
	

	public String getATRN() {
		return ATRN;
	}

	

	

	public Date getModification_time() {
		return modification_time;
	}

	

	public void setATRN(String aTRN) {
		ATRN = aTRN;
	}



	public void setModification_time(Date modification_time) {
		this.modification_time = modification_time;
	}




	public String getMerchant_Order_Number() {
		return Merchant_Order_Number;
	}




	




	public Date getProcess_time() {
		return process_time;
	}




	public Payment getPayment() {
		return payment;
	}




	public void setMerchant_Order_Number(String merchant_Order_Number) {
		Merchant_Order_Number = merchant_Order_Number;
	}




	




	public String getTrxstatus() {
		return trxstatus;
	}




	public void setTrxstatus(String trxstatus) {
		this.trxstatus = trxstatus;
	}




	public void setProcess_time(Date process_time) {
		this.process_time = process_time;
	}




	public void setPayment(Payment payment) {
		this.payment = payment;
	}




	public String getModifiedBy() {
		return modifiedBy;
	}




	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	

}
