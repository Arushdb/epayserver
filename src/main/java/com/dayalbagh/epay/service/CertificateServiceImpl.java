package com.dayalbagh.epay.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.dayalbagh.epay.model.Certificate;
import com.dayalbagh.epay.model.Certificatesemester;
import com.dayalbagh.epay.model.Defaulters;
import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.ProgramFee;
import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;
import com.dayalbagh.epay.repository.AdmfeedatesRepository;
import com.dayalbagh.epay.repository.CertificateRepository;
import com.dayalbagh.epay.repository.CertificateSemesterRepository;
import com.dayalbagh.epay.repository.DefaulterRepository;
import com.dayalbagh.epay.repository.FeedatesRepository;
import com.dayalbagh.epay.repository.FeereceiptRepository;
import com.dayalbagh.epay.repository.ProgramFeeRepository;

@PropertySource("classpath:message.properties")
@Service
public class CertificateServiceImpl implements CertificateService {

	@Autowired
	EntityManager em;
	@Autowired
	SBIService  sbiservice;
	
	 @Autowired
	 CertificateRepository   theCertificateRepository;
	 @Autowired
	 CertificateSemesterRepository theCertificateSemesterRepository;
	
	@Value("${migrationcertificatefee}")
	private int migrationcertificatefee;
	@Value("${triplicatemigrationcertificatefee}")
	private int triplicatemigrationcertificatefee;
	@Value("${postalcharges}")
	private int postalcharges;

	@Value("${degree}")
	private int degree;
	@Value("${transcript}")
	private int transcript;
	@Value("${provisional}")
	private int provisional;
	@Value("${resultcard}")
	private int resultcard;

	

	
	
	@Override
	public List<Student> getcertificatedetail(String rollno, String mode, String type,String dob,String enrolno,String semesters) throws Exception {

		List<Student> student = new ArrayList<Student>();
		
		
		student =validateStudent(rollno,dob,enrolno,type);
		int feeamount = 0;
		switch (type) {
		case "mig": 
			
			feeamount = getmigrationamount(student.get(0).getRoll_number(), mode);
			break;
		case "trn":
			feeamount = transcript;
			if (mode.equalsIgnoreCase("bypost"))
				feeamount = feeamount + postalcharges;
			break;

		case "deg":
			feeamount = degree;
			if (mode.equalsIgnoreCase("bypost"))
				feeamount = feeamount + postalcharges;
			break;

		case "pro":
			feeamount = provisional;
			if (mode.equalsIgnoreCase("bypost"))
				feeamount = feeamount + postalcharges;
			break;

		case "res":
			feeamount = resultcard;
			String [] ary =semesters.split(",");
			
			feeamount=feeamount*ary.length;
			if (mode.equalsIgnoreCase("bypost"))
				feeamount = feeamount + postalcharges;
			break;

		}

		student.get(0).setAmount(feeamount);
		student.get(0).setSemestercode(semesters.replace(",", ":"));
		return student;

		// TODO Auto-generated method stub

	}

	private int getmigrationamount(String rollno, String mode) {

		String migcount = "";
		int seqno = 0;

		int feetripamount = 0;

		int feemigration = 0;

		Object obj = em.createNamedQuery("getmigrationdetail").setParameter("rollno", rollno).getSingleResult();
		if(obj != null)
			migcount=obj.toString();
		

		if (mode.equalsIgnoreCase("bypost")) {

			feetripamount = triplicatemigrationcertificatefee + postalcharges;
			feemigration = migrationcertificatefee + postalcharges;

		} else {
			feetripamount = triplicatemigrationcertificatefee;
			feemigration = migrationcertificatefee;
		}

		if (!(migcount.equalsIgnoreCase("")))
			seqno = Integer.parseInt(migcount);

		seqno++;
		if (seqno > 2)
			return feetripamount;

		else
			return feemigration;

	}
	
	public List<Student> validateStudent(String rollno,String dob,String enrolno,String type) throws Exception{
		
		List<Student> student = new ArrayList<>();
		
		if (type.equalsIgnoreCase("deg") || type.equalsIgnoreCase("pro")
				|| type.equalsIgnoreCase("res"))
		student = (List<Student>) em.createNamedQuery("valid_roll_enrol_dob", Student.class)
				.setParameter("rollno", rollno)
				.setParameter("dob", dob)
				.setParameter("enrolno", enrolno).getResultList();
		
		if (type.equalsIgnoreCase("mig") || type.equalsIgnoreCase("trn"))
				
		student = (List<Student>) em.createNamedQuery("valid_enrol_dob", Student.class)
				.setParameter("dob", dob)
				.setParameter("enrolno", enrolno).getResultList();
		
		
		
		if(student.size()<=0)
			throw new Exception("Student Detail not found");
		
		return student;
		
	}



	@Override
	public String savecertificateDetail(Student student) {
		String writestatus = "success";
		List <Certificate> cersemList = new ArrayList<>();
		Certificatesemester cerobj=null;
		Certificate cresem = null;
		try {
			int paymentid =student.getPayment().getId();
			Certificate certificate=  theCertificateRepository.findByPayment_id(paymentid);
			if(certificate==null)
			   certificate = new Certificate();
			
			  certificate.setAddress(student.getAddress());
			  certificate.setMode(student.getMode());
			  certificate.setPhone(student.getPhone());
			  certificate.setPincode(student.getPincode());
			  certificate.setRollno(student.getRoll_number());
			  certificate.setEnrolmentnumber(student.getEnrolno());
			  
			  certificate.setInserttime(new Timestamp(System.currentTimeMillis()));
			  certificate.setType(student.getCertificatetype());
			  
//			  Payment payobj = sbiservice.findPaymentByATRN(student.getATRN());
//				 
//				if (payobj==null)
//					payobj = sbiservice.findPaymentByMerchantorderno(student.getMerchantorderno());
//				if (payobj==null) {
//					
//					sbiservice.logerror(student.getATRN(), student.getMerchantorderno(), String.valueOf(student.getAmount()) , "Record not found in Payment table","savecertificateDetail");
//					writestatus="error";
//				}
			  certificate.setPayment(student.getPayment());
			  
			  theCertificateRepository.save(certificate); 
			  
			  
			  if (!(student.getSemestercode().isEmpty())) {
				 String str = student.getSemestercode();
				 String resdata[]= str.split("\\:");
				 
				  for(String sem:resdata) {
					 
					cerobj=  theCertificateSemesterRepository.findByCertificateAndSemester(certificate,sem);
					  
					  if(cerobj==null) 
					  cerobj = new Certificatesemester();
					  
					  cerobj.setSemester(sem);
					  cerobj.setCertificate(certificate);
					  theCertificateSemesterRepository.save(cerobj);
				  }
				 
				 
			  }
			  
			
			  
		}catch(Exception e) {
			sbiservice.logerror(student.getATRN(), student.getMerchantorderno(), String.valueOf(student.getAmount()) , e.getMessage(),"savecertificateDetail");
		    return "error";
		    
		}
		  
		  return writestatus;
	}


	

}
