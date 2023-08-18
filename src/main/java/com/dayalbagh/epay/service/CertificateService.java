package com.dayalbagh.epay.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;

public interface CertificateService {

		
	

	List<Student> getcertificatedetail(String rollno, String mode, String type, String dob, String enrolno,
			String semesters) throws Exception;

	

	String savecertificateDetail(Student student);

	

	
	

	

	
}
