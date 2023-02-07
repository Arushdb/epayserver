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
import com.dayalbagh.epay.model.ProgramFee;
import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;
import com.dayalbagh.epay.repository.AdmfeedatesRepository;
import com.dayalbagh.epay.repository.DefaulterRepository;
import com.dayalbagh.epay.repository.FeedatesRepository;
import com.dayalbagh.epay.repository.FeereceiptRepository;
import com.dayalbagh.epay.repository.ProgramFeeRepository;

@PropertySource("classpath:message.properties")
@Service
public class CertificateServiceImpl implements CertificateService {

	@Autowired
	EntityManager em;


	FeereceiptRepository thefeereceiptRepository;
	
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

	public CertificateServiceImpl(FeereceiptRepository feereceiptRepository) {
	
		this.thefeereceiptRepository = feereceiptRepository;
	

	}

	
	
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

}
