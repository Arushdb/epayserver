package com.dayalbagh.epay.service;

import java.util.Date;
import java.util.List;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;

public interface StudentService {

	List <Student> getstudentdetail(String rollno) throws Exception;
	
	List<Programfeedates> getepaymentstatus(Character type) throws Exception;

	List<Student> getpendingfee(String rollno);

	Boolean isfeealreadypaid(String rollno, String sem, String pgm, Integer id) throws Exception;

	
}
