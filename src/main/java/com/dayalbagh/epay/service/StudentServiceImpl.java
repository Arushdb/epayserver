package com.dayalbagh.epay.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.repository.FeedatesRepository;

@Service
public class StudentServiceImpl  implements StudentService{

	@Autowired
	EntityManager em;
	
	FeedatesRepository thefeedatesRepository;
	
	public StudentServiceImpl(FeedatesRepository feedatesRepository) {
		this.thefeedatesRepository =feedatesRepository;
	}
	
	@Override
	public List<Student> getstudentdetail(String rollno) {
		List<Student> student=null; 
		
		student = (List<Student>) em.createNamedQuery("getstudentdetail", Student.class)
				.setParameter("rollno" , rollno).getResultList();
		return student;
	}

	@Override
	public List<Programfeedates> getepaymentstatus() throws Exception {
		// TODO Auto-generated method stub
		
		
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());	
		List<Programfeedates> feedates =  thefeedatesRepository.getepaymentstatus(date);
				
		return feedates;
	
	}

}
