package com.dayalbagh.epay.service;

import java.util.List;

import com.dayalbagh.epay.model.Student;

public interface SBIService {
	
	
	// Encrypt String
	
	Student encrypt(Student student);
	
	// Decrypt String
	
	String decrypt(String str);
	
	// Double Verify payment received
	
	String verifyPayment();

}
