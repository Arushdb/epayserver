package com.dayalbagh.epay.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.dayalbagh.epay.exception.ResourceNotFoundException;
import com.dayalbagh.epay.model.Admfeedates;
import com.dayalbagh.epay.model.Defaulters;
import com.dayalbagh.epay.model.HostelFee;
import com.dayalbagh.epay.model.ProgramFee;
import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;
import com.dayalbagh.epay.repository.AdmfeedatesRepository;
import com.dayalbagh.epay.repository.DefaulterRepository;
import com.dayalbagh.epay.repository.FeedatesRepository;
import com.dayalbagh.epay.repository.FeereceiptRepository;
import com.dayalbagh.epay.repository.HostelFeeRepository;
import com.dayalbagh.epay.repository.ProgramFeeRepository;


@Service
public class PostfeeServiceImpl implements PostfeeService {

	@Autowired
	EntityManager em;

	@Override
	public List<Student> getpostfee(String appno) throws Exception {
		
		List<Student> applicantpostfee = new ArrayList();
		
		applicantpostfee = (List<Student>) em.createNamedQuery("getpostfee", Student.class)
				.setParameter("appno", appno).getResultList();
		
		
		if (applicantpostfee.size()>0)
			return applicantpostfee ;
		else 
			throw new Exception("Invalid Applicantion number");
	}
	

	
}
