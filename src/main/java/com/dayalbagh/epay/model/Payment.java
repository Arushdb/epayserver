package com.dayalbagh.epay.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Table(name = "payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	private String refnumber;
	
	private String payment_mode;
		
	@Column(name = "transaction_date", columnDefinition = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transaction_date;
	
	@Column(name="amount", precision = 17 ,scale = 3)
	private BigDecimal amount;
	
	@Column(name="Total_Fee_GST", precision = 17 ,scale = 3)
	private BigDecimal Total_Fee_GST;
	
	private String type;
	
	
	
	private String otherdetail;
	private String bank_code;
	@Column(name="merchant_orderno")
	private String merchantorderno;
	
	private String ATRN;
	
	private String dv_status;
	private String Bank_Reference_Number;
	
	private String fee_account;
	private String country ;
	private String CIN ;
	private String reason ;
	private String Merchant_ID ;
	private String transaction_status;
	private String currency;
	private String Category;
	private String createdby;
	
	@Column(name="status_description")
	private String statusdescription;
	
	
	@OneToOne(mappedBy = "payment")
	private Studentfeereceipt feereceipt;
	
	@OneToOne(mappedBy = "payment")
	private Certificate certificate;
	
	@Column(name = "insert_time" ,columnDefinition = "insert_time")
	
	private Date insert_time ;
	
	@Column(name = "modification_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modification_time;
	
	private String modifiedby;
	
	
	

	public Payment() {
		
	}

	public String getRefnumber() {
		return refnumber;
	}

	public void setRefnumber(String refnumber) {
		this.refnumber = refnumber;
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

	

	
	

	

	public Certificate getCertificate() {
		return certificate;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
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

	

	

	public String getOtherdetail() {
		return otherdetail;
	}

	public String getBank_code() {
		return bank_code;
	}

	

	public String getATRN() {
		return ATRN;
	}

	

	public String getDv_status() {
		return dv_status;
	}

	public String getBank_Reference_Number() {
		return Bank_Reference_Number;
	}

	

	public String getFee_account() {
		return fee_account;
	}

	public Date getModification_time() {
		return modification_time;
	}

	

	public void setOtherdetail(String otherdetail) {
		this.otherdetail = otherdetail;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	

	public void setATRN(String aTRN) {
		ATRN = aTRN;
	}

	

	public void setDv_status(String dv_status) {
		this.dv_status = dv_status;
	}

	public void setBank_Reference_Number(String bank_Reference_Number) {
		Bank_Reference_Number = bank_Reference_Number;
	}



	public void setFee_account(String fee_account) {
		this.fee_account = fee_account;
	}

	public void setModification_time(Date modification_time) {
		this.modification_time = modification_time;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCIN() {
		return CIN;
	}

	public String getReason() {
		return reason;
	}

	public String getMerchant_ID() {
		return Merchant_ID;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setMerchant_ID(String merchant_ID) {
		Merchant_ID = merchant_ID;
	}

	public String getTransaction_status() {
		return transaction_status;
	}

	public void setTransaction_status(String transaction_status) {
		this.transaction_status = transaction_status;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getTotal_Fee_GST() {
		return Total_Fee_GST;
	}

	public void setTotal_Fee_GST(BigDecimal total_Fee_GST) {
		Total_Fee_GST = total_Fee_GST;
	}

	public int getId() {
		return id;
	}

	public String getMerchantorderno() {
		return merchantorderno;
	}

	public void setMerchantorderno(String merchantorderno) {
		this.merchantorderno = merchantorderno;
	}

	public String getStatusdescription() {
		return statusdescription;
	}

	public void setStatusdescription(String statusdescription) {
		this.statusdescription = statusdescription;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	
	
	

}
