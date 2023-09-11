package com.dayalbagh.epay.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;

public interface PrintService {

		public String exportContinuePDF(Student student) throws MalformedURLException, IOException ;

		public Student getFeeData(String ordeno, String aTRN, String amount) throws Exception;
		
		

	

	
}
