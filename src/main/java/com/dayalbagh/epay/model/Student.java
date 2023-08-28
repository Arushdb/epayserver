package com.dayalbagh.epay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class Student {
	

     private long id;
	private String roll_number;
	private String studentname;
	private String status;
	private String programid;
	
	@Column(name="program_type",columnDefinition="char(1)")
	private Character type;
	
	public String getFeepending() {
		return feepending;
	}
	public void setFeepending(String feepending) {
		this.feepending = feepending;
	}
	private String programname;
	@Column(name="semester_code",columnDefinition="char(4)")
	private String semestercode;
	private String feepending;
	
	@Column(name="mode",columnDefinition="char(3)")
	private String mode;
	@Column(name="branchid",columnDefinition="char(2)")
	private String branchid;
	@Column(name="specializationid",columnDefinition="char(2)")
	private String specializationid;
	
	private String applicationnumber;
	private String bypost;
	private String certificatetype;
	
	
	private float amount ;
	private float latefee;
	private float labfee;	
	private String merchIdVal ; 
	private String category ; 
	private String merchantorderno;
	private String ATRN;
	private Payment payment;
	private String defaulter;
	
	
	private java.math.BigDecimal  appfee ;
	
	
	
	
	
	@Temporal(TemporalType.DATE)
	private Date semesterstartdate;
	
	@Temporal(TemporalType.DATE)
	private Date semesterenddate;
	
	private String message;
	private String entityid;
	
	private String EncryptTrans;
	
	private String feetype;
	private String reftype;
	private String enrolno ;
	private String address;
	private String pincode;
	private String phone;
	private String method; // push response or browser response
	
	public Student() {
		
		// TODO Auto-generated constructor stub
	}
	
		
	public Date getSemesterstartdate() {
		return semesterstartdate;
	}
	public Date getSemesterenddate() {
		return semesterenddate;
	}
	public void setSemesterstartdate(Date semesterstartdate) {
		this.semesterstartdate = semesterstartdate;
	}
	public void setSemesterenddate(Date semesterenddate) {
		this.semesterenddate = semesterenddate;
	}
	
	public Student(String roll_number, String studentname, String programid, Character type, String programname,
			String mode, String branchid, String specializationid,String entityid) {
		
		this.roll_number = roll_number;
		this.studentname = studentname;
		this.programid = programid;
		this.type = type;
		this.programname = programname;
		this.mode = mode;
		this.branchid = branchid;
		this.specializationid = specializationid;
		this.entityid=entityid ;
	}
	public Student(String roll_number, String programname) {
		
		this.roll_number = roll_number;
		this.programname = programname;
	}
	public Student(String roll_number, String programname, String semester_code,
			Date semesterstartdate,Date semesterenddate,String programid,String branchid ,String specializationid 
			,String entityid
			) {
		
		this.roll_number = roll_number;
		this.programname = programname;
		this.semestercode = semester_code;
		this.semesterstartdate=semesterstartdate;
		this.semesterenddate=semesterenddate;
		this.programid=programid;
		this.branchid = branchid;
		this.specializationid = specializationid;
		this.entityid =entityid;
	}
	public Student(String roll_number, String studentname, String programid, Character type, String programname) {
		
		this.roll_number = roll_number;
		this.studentname = studentname;
		this.programid = programid;
		this.type = type;
		this.programname = programname;
		
	}
	
	
	
	
	

	public Student(String studentname, String applicationnumber, java.math.BigDecimal appfee,String message) {
		
		this.studentname = studentname;
		this.applicationnumber = applicationnumber;
		this.appfee = appfee;
		this.message = message;
		
	}
	
	
	public String getRoll_number() {
		return roll_number;
	}
	public void setRoll_number(String roll_number) {
		this.roll_number = roll_number;
	}
	
	public String getStudentname() {
		return studentname;
	}
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgramid() {
		return programid;
	}
	public void setProgramid(String programid) {
		this.programid = programid;
	}
	public String getProgramname() {
		return programname;
	}
	public void setProgramname(String programname) {
		this.programname = programname;
	}
	
	public Character getType() {
		return type;
	}
	public void setType(Character type) {
		this.type = type;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getMode() {
		return mode;
	}
	public String getBranchid() {
		return branchid;
	}
	public String getSpecializationid() {
		return specializationid;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public void setSpecializationid(String specializationid) {
		this.specializationid = specializationid;
	}
	public String getSemestercode() {
		return semestercode;
	}
	public void setSemestercode(String semestercode) {
		this.semestercode = semestercode;
	}
	public float getLatefee() {
		return latefee;
	}
	public void setLatefee(float latefee) { 
		this.latefee = latefee;
	}
	public float getLabfee() {
		return labfee;
	}
	public void setLabfee(float labfee) {
		this.labfee = labfee;
	}
	public String getApplicationnumber() {
		return applicationnumber;
	}
	public void setApplicationnumber(String applicationnumber) {
		this.applicationnumber = applicationnumber;
	}
	public java.math.BigDecimal getAppfee() {
		return appfee;
	}
	public void setAppfee(java.math.BigDecimal appfee) {
		this.appfee = appfee;
	}
	public String getBypost() {
		return bypost;
	}
	public void setBypost(String bypost) {
		this.bypost = bypost;
	}
	public String getCertificatetype() {
		return certificatetype;
	}
	public void setCertificatetype(String certificatetype) {
		this.certificatetype = certificatetype;
	}
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEncryptTrans() {
		return EncryptTrans;
	}
	public void setEncryptTrans(String encryptTrans) {
		EncryptTrans = encryptTrans;
	}
	

	public String getEntityid() {
		return entityid;
	}
	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}
	public String getMerchIdVal() {
		return merchIdVal;
	}
	public void setMerchIdVal(String merchIdVal) {
		this.merchIdVal = merchIdVal;
	}
	public String getFeetype() {
		return feetype;
	}
	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMerchantorderno() {
		return merchantorderno;
	}
	public String getATRN() {
		return ATRN;
	}
	public void setMerchantorderno(String merchantorderno) {
		this.merchantorderno = merchantorderno;
	}
	public void setATRN(String aTRN) {
		ATRN = aTRN;
	}
	public String getReftype() {
		return reftype;
	}
	public void setReftype(String reftype) {
		this.reftype = reftype;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public String getEnrolno() {
		return enrolno;
	}
	public void setEnrolno(String enrolno) {
		this.enrolno = enrolno;
	}
	public String getAddress() {
		return address;
	}
	public String getPincode() {
		return pincode;
	}
	public String getPhone() {
		return phone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDefaulter() {
		return defaulter;
	}
	public void setDefaulter(String defaulter) {
		this.defaulter = defaulter;
	}
	
	
	

}
