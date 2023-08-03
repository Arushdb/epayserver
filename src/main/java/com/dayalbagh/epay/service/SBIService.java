package com.dayalbagh.epay.service;

import java.text.ParseException;


public interface SBIService {
	
	
	// Encrypt String
	
	String encrypt(String amount,String otherdetail);
	
	// Decrypt String
	
	String decrypt(String str);
	
	// Double Verify payment received
	
	

	String verifyPayment(String[] resdata);
	 String getMerchantId();
	 
	 void savePayment(String[] data) throws ParseException;

	void savestudentfee(String[] dvdata) throws Exception;

}
