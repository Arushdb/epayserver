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

@PropertySource("classpath:message.properties")
@Service
public class HostelServiceImpl implements HostelService {

	@Autowired
	EntityManager em;


	HostelFeeRepository theHostelFeeRepository;
	
	

	public HostelServiceImpl(HostelFeeRepository hostelFeeRepository) {
	
		this.theHostelFeeRepository = hostelFeeRepository;
	

	}



	@Override
	public List<HostelFee> gethostelfee(int hostelid, String yearmonth) throws Exception {
		
		List<HostelFee> hostelfee = new ArrayList();
		
		hostelfee=theHostelFeeRepository.findByYearmonthAndHostelid(yearmonth, hostelid);
		if (hostelfee.size()>0)
			return hostelfee ;
		else 
			throw new Exception("Hostel Fee not available");
	}
	
	@Override
	public List<Student> getapplicant(String appno) throws Exception {
		
		List<Student> theapplicant = new ArrayList<>();
		theapplicant = (List<Student>) em.createNamedQuery("getapplicantforhostel", Student.class)
				.setParameter("appno", appno).getResultList();
		
		if (theapplicant.size()>0) {
			
			return theapplicant;
		}
		else {throw new Exception("Hostel Fee not available,Please try with Roll number");
		}
		
		

		
	

	}
	
	
}
