package com.dayalbagh.epay.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.dayalbagh.epay.model.Certificate;
import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.Student;


public interface SBIService {
	
	
	// Encrypt String
	
	String encrypt(String amount,String otherdetail);
	
	// Decrypt String
	
	String decrypt(String str);
	
	// Double Verify payment received
	
	

	String verifyPayment(String[] resdata);
	 String getMerchantId();
		public void savemigrationrecord(Certificate crt);
	 

	

	boolean isNumeric(String strNum);
	

	Payment findPaymentByATRN(String ATRN);
	Payment findPaymentByMerchantorderno(String ATRN);
	 Payment findPaymentByByMerchantordernoAndAmount(String morderno,BigDecimal amount);
     Payment findPaymentByATRNAndAmount(String atrn,BigDecimal amount);

	Student savePayment(String[] data, String dvstatus, String statusdecription, String method) ;



	void logerror(String ATRN, String MerchantOrderNumber, String trxamt, String message, String method);

	Student ParseDVResponse(String[] dvdata);

	void updatePaymentstatus(Payment payment);
	
	public String Aesdecrypt(String text ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException ;

	public Student otherdetailforcertificate(Student student, String[] resdata);
	
	public Student otherdetailforcontinue(Student student, String[] resdata) throws Exception;
	
	

}
