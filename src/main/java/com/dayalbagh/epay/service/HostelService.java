package com.dayalbagh.epay.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.dayalbagh.epay.model.HostelFee;
import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;

public interface HostelService {

	List<HostelFee> gethostelfee(int hostelid, String yearmonth) throws Exception;

	List<Student> getapplicant(String appno) throws Exception;

		
	

	
	
	

	

	
}
