package com.dayalbagh.epay.service;

import java.text.ParseException;

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
	 

	

	boolean isNumeric(String strNum);
	

	Payment findPaymentByATRN(String ATRN);
	Payment findPaymentByMerchantorderno(String ATRN);

	Student savePayment(String[] data, String dvstatus, String statusdecription, String method) ;



	void logerror(String ATRN, String MerchantOrderNumber, String trxamt, String message, String method);

	Student ParseDVResponse(String[] dvdata);
	
	
	
	

}
