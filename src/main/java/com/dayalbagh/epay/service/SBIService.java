package com.dayalbagh.epay.service;

import java.util.List;

import com.dayalbagh.epay.model.Student;

public interface SBIService {
	
	
	// Encrypt String
	
	String encrypt(String amount,String otherdetail);
	
	// Decrypt String
	
	String decrypt(String str);
	
	// Double Verify payment received
	
	

	String verifyPayment(String[] resdata);

}
