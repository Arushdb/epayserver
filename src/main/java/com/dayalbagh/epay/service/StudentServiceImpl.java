package com.dayalbagh.epay.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.dayalbagh.epay.exception.ResourceNotFoundException;
import com.dayalbagh.epay.model.Defaulters;
import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;
import com.dayalbagh.epay.repository.DefaulterRepository;
import com.dayalbagh.epay.repository.FeedatesRepository;
import com.dayalbagh.epay.repository.FeereceiptRepository;

@PropertySource("classpath:message.properties" )
@Service
public class StudentServiceImpl  implements StudentService{

	@Autowired
	EntityManager em;
	
	FeedatesRepository thefeedatesRepository;
	FeereceiptRepository thefeereceiptRepository;
	@Autowired
	DefaulterRepository theDefaulterRepository ;
	
	@Value("${epaystartdate}")
	private String epaystartdate ;
	
	public StudentServiceImpl
	(FeedatesRepository feedatesRepository,
			FeereceiptRepository feereceiptRepository
			) {
		this.thefeedatesRepository =feedatesRepository;
		this.thefeereceiptRepository = feereceiptRepository;
		
	}
	
	@Override
	public List<Student> getstudentdetail(String rollno) throws Exception {
		List<Student> student=null; 
		
		student = (List<Student>) em.createNamedQuery("getstudentdetail", Student.class)
				.setParameter("rollno" , rollno).getResultList();
		if(student.size()>0)
			return student;
		else
			throw  new ResourceNotFoundException("Student Detail not found");
			
	
		//List<Map<String, Object>> list =thefeedatesRepository.getpending(rollno);
		
		
	}

	@Override
	public List<Programfeedates> getepaymentstatus(Character type) throws Exception {
		// TODO Auto-generated method stub
		
		
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());	
		List<Programfeedates> feedates =  thefeedatesRepository.getepaymentstatus(date,type);
			
		if(feedates.size()>0)
		return feedates;
		else
			throw new ResourceNotFoundException("E Payment not available");
				
	
	}
	@Override
	public List<Student> getpendingfee(String rollno) {
		List<Student> student=new ArrayList<>();
		
		
		List<Defaulters> thedefaulter =null; 
		thedefaulter = theDefaulterRepository.findAllByRollnumberAndDefaulter(rollno, 1);
		
		if(thedefaulter.size()>0) {
			List<Student> defaulterlist=new ArrayList<>();
			Student studentobj=new Student();
			thedefaulter.forEach(rec->{
				studentobj.setRoll_number(rec.getRollnumber());
				studentobj.setSemester(rec.getSemestercode());
				studentobj.setProgramid(rec.getProgramid());
				studentobj.setProgramname(rec.getProgramname());
				
				defaulterlist.add(studentobj);
				});
			return defaulterlist;
		}
		
		student = (List<Student>) em.createNamedQuery("getpendingfee", Student.class)
				.setParameter("rollno" , rollno)
				.setParameter("epaystartdate",epaystartdate)
				.getResultList();
				
		return student;
	}
	@Override
	public Boolean isfeealreadypaid(String rollno,String sem,String pgm,Integer id) throws Exception {
		List<Studentfeereceipt> feereceipt1=null; 
		List<Studentfeereceipt> feereceipt2=null; 
		List<Student> student=new ArrayList<>();
		feereceipt1 =thefeereceiptRepository.
				findAllByRollnumberAndSemesterAndProgramidAndId(rollno, sem, pgm,id);
		feereceipt2 =thefeereceiptRepository.findAllByRollnumberAndSemesterAndProgramidAndIdNot(rollno, sem, pgm, id);
	    
		
		if(feereceipt1.size()>0)
			throw new Exception("Fee Already Paid") ;
		
		if(feereceipt2.size()>0) {
			student = (List<Student>) em.createNamedQuery("getsemesterstatus", Student.class)
					.setParameter("semester",sem)
					.setParameter("rollno" , rollno)
					
					.setParameter("pgmid",pgm)
					.getResultList();
			if (student.size()>0) 
			throw new Exception("Fee Already Paid") ;	
		}
			
		
		
			
		return false;
	}
	
//	@Override
//	public List<Student> getpendingfee1(String rollno) {
//		List<Student> student=null; 
//		
//		student = (List<Student>) em.createQuery("select srsh.roll_number,pch.semester_code \r\n"
//				+ ",pch.program_id,pch.branch_id \r\n"
//				+ "from cms21112022.student_registration_semester_header srsh\r\n"
//				+ "left join student_fee_receipt sfr  on sfr.roll_number = srsh.roll_number \r\n"
//				+ "and sfr.program_course_key= srsh.program_course_key \r\n"
//				+ "and sfr.semester_start_date=srsh.session_start_date\r\n"
//				+ "join cms21112022.program_course_header pch \r\n"
//				+ "on pch.program_course_key= srsh.program_course_key\r\n"
//				+ "\r\n"
//				+ "where srsh.roll_number = :rollno\r\n"
//				+ " and srsh.status in (\'PAS\',\'FAL\',\'REM\',\'REG\')\r\n"
//				+ " and sfr.roll_number is null",Student.class)
//				.setParameter("rollno" , rollno).
//			
//				getResultList();
//		
//		
//		return student;
//	}
//	

}
