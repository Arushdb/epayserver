package com.dayalbagh.epay.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;

public interface StudentService {

	List <Student> getstudentdetail(String rollno) throws Exception;
	
	List<Programfeedates> getepaymentstatus(Character type) throws Exception;

	

	
	public Student getFeeAmount(String pgm ,String sem ,String branch,String spec,String learningmode,String latefee,String lab)
			throws Exception;

	

	List<Student> getvalidsemesters(String pgm, String ssd, String sed) throws Exception;

	List<Student> getdefaulter(String rollno);

	Boolean getpaymentdelaystatus(Character type);

	java.sql.Date stringToDate(String dt) throws ParseException;


	List<Student> getapplicantdetail(String appno);

	Student getadmissiondetail(String appno) throws Exception;

	List<Student> getpendingfee(String rollno, java.sql.Date semesterstartdate, java.sql.Date semesterenddate);

	boolean isfeealreadypaid(String rollno, String sem, String pgm, java.sql.Date ssd, java.sql.Date sed,
			String reftype) ;

	java.sql.Date subYear(java.sql.Date date, int year);

	

	String saveStudentAppfee(Student student);

	String savestudentfee(Student student);

	boolean isSemregistered(String rollno, String sem,java.sql.Date semesterstartdate,
			java.sql.Date semesterenddate);

	Studentfeereceipt getstudentfeereceipt(Student student);
	
	List<Studentfeereceipt> getreceiptsbetweendates(String rollno,String startdate,String endDate );

	

	boolean isDobValid(String rollno, String dob);

	
	
	
		
	

	

	
	
	
	

	

	
}
