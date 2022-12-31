package com.dayalbagh.epay.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;

public interface StudentService {

	List <Student> getstudentdetail(String rollno) throws Exception;
	
	List<Programfeedates> getepaymentstatus(Character type) throws Exception;

	List<Student> getpendingfee(String rollno, java.sql.Date semesterstartdate);

	
	public Student getFeeAmount(String pgm ,String sem ,String branch,String spec,String learningmode,String latefee,String lab)
			throws Exception;

	

	List<Student> getvalidsemesters(String pgm, String ssd, String sed) throws Exception;

	List<Student> getdefaulter(String rollno);

	Boolean getpaymentdelaystatus(Character type);

	java.sql.Date stringToDate(String dt) throws ParseException;

	Boolean isfeealreadypaid(String rollno, String sem, String pgm, java.sql.Date ssd, java.sql.Date sed)
			throws Exception;

	
	

	

	
}
