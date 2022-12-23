package com.dayalbagh.epay.model;

import java.math.BigDecimal;
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
@Table(name = "receipt")
public class Receipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	private String refnumber;
	private String session;
	private String payment_mode;
		
	@Column(name = "transaction_date", columnDefinition = "Date")
	@Temporal(TemporalType.DATE)
	private Date transaction_date;
	
	@Column(name="amount", precision = 10 ,scale = 3)
	private BigDecimal amount;
	
	private String type;
	private String semester_code;
	private String bankrefno;
	
	@OneToOne(mappedBy = "receipt")
	private Studentfeereceipt feereceipt;
	
	@Column(name = "insert_time", columnDefinition = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insert_time;
	
	
	
	

	public Receipt() {
		
	}

	public String getRefnumber() {
		return refnumber;
	}

	public void setRefnumber(String refnumber) {
		this.refnumber = refnumber;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public Date getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(Date transaction_date) {
		this.transaction_date = transaction_date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public String getBankrefno() {
		return bankrefno;
	}

	public void setBankrefno(String bankrefno) {
		this.bankrefno = bankrefno;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	

	public Studentfeereceipt getFeereceipt() {
		return feereceipt;
	}

	public void setFeereceipt(Studentfeereceipt feereceipt) {
		this.feereceipt = feereceipt;
	}

	public String getSemester_code() {
		return semester_code;
	}

	public void setSemester_code(String semester_code) {
		this.semester_code = semester_code;
	}
	
	
	
	

}
