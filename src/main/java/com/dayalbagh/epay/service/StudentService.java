package com.dayalbagh.epay.service;

import java.util.Date;
import java.util.List;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;

public interface StudentService {

	List <Student> getstudentdetail(String rollno);
	
	List<Programfeedates> getepaymentstatus() throws Exception;
}
