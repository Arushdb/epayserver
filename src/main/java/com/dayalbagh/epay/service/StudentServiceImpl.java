package com.dayalbagh.epay.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dayalbagh.epay.exception.ResourceNotFoundException;
import com.dayalbagh.epay.model.Admfeedates;
import com.dayalbagh.epay.model.Defaulters;
import com.dayalbagh.epay.model.Payment;
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
public class StudentServiceImpl implements StudentService {

	@Autowired
	EntityManager em;

	FeedatesRepository thefeedatesRepository;
	FeereceiptRepository thefeereceiptRepository;
	ProgramFeeRepository theprogramFeeRepository;
	@Autowired
	DefaulterRepository theDefaulterRepository;
	@Autowired
	AdmfeedatesRepository theadmfeedatesRepository;
	
	
	
	

	@Value("${epaystartdate}")
	private String epaystartdate;
	@Value("${delaycharges1}")
	private int delaycharges1;
	@Value("${delaycharges2}")
	private int delaycharges2;
	@Value("${labfee}")
	private int labfee;

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
	
	@Autowired
	SBIService sbiservice;

	public StudentServiceImpl(FeedatesRepository feedatesRepository, FeereceiptRepository feereceiptRepository,
			ProgramFeeRepository programFeeRepository) {
		this.thefeedatesRepository = feedatesRepository;
		this.thefeereceiptRepository = feereceiptRepository;
		this.theprogramFeeRepository = programFeeRepository;

	}

	@Override
	public List<Student> getstudentdetail(String rollno) throws Exception {
		List<Student> student = null;

		student = (List<Student>) em.createNamedQuery("getstudentdetail", Student.class).setParameter("rollno", rollno)
				.getResultList();
		if (student.size() > 0)
			return student;
		else
			throw new ResourceNotFoundException("Student Detail not found");

		// List<Map<String, Object>> list =thefeedatesRepository.getpending(rollno);

	}

	@Override
	public List<Programfeedates> getepaymentstatus(Character type) throws Exception {
		// TODO Auto-generated method stub

		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		List<Programfeedates> feedates = thefeedatesRepository.getepaymentstatus(date, type);

		if (feedates.size() > 0)
			return feedates;
		else
			throw new ResourceNotFoundException("E Payment not available");

	}

	@Override
	public Boolean getpaymentdelaystatus(Character type) {
		// TODO Auto-generated method stub

		Boolean delay = false;
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		List<Programfeedates> feedates = thefeedatesRepository.getpaymentdelaytatus(date, type);

		if (feedates.size() > 0) {
			delay = true;
		}
		return delay;

	}

	@Override
	public List<Student> getpendingfee(String rollno, java.sql.Date semesterstartdate,java.sql.Date semesterenddate) {
		List<Student> student = new ArrayList<Student>();
		List<String> app = new ArrayList<>();
		String applno ="";
		List<Student> pendingfee = new ArrayList<>();
		boolean feepaid = false;

		student = (List<Student>) em.createNamedQuery("getpendingfee", Student.class).setParameter("rollno", rollno)
				.setParameter("epaystartdate", epaystartdate).setParameter("semesterstartdate", semesterstartdate)
				.getResultList();
		
			
		
			
		app = em.createNativeQuery("select a.appno as applicationnumber from cms_live.student_application_number a where a.roll_number=?1")
					.setParameter(1, rollno)
					.getResultList();
					
					
			 
			 if (app.size()>0) {
				 
				 applno = app.get(0);
		// look for paid fee via application number .
		for(int i=0;i<student.size();i++) {
			
			java.sql.Date ssd =(java.sql.Date)student.get(i).getSemesterstartdate();
			java.sql.Date sed =(java.sql.Date)student.get(i).getSemesterenddate();
			
			feepaid =isfeealreadypaid(applno,student.get(i).getSemestercode(),student.get(i).getProgramid(),
					ssd,sed,"A");
			if(!feepaid) {
				pendingfee.add(student.get(i));
			}
		}
		return pendingfee; 
			 }else {
				 return student;
			 }
		
		
		
		
		
		

		
	}

	@Override
	public boolean isfeealreadypaid(String rollno, String sem, String pgm, java.sql.Date ssd, java.sql.Date sed,String reftype)
			{
		List<Studentfeereceipt> feereceipt1 = null;
		List<Studentfeereceipt> feereceipt2 = null;
		List<Student> student = new ArrayList<>();


		
		feereceipt1=thefeereceiptRepository.findAllByRollnumberAndSemesterAndProgramidAndSemesterstartdateAndSemesterenddateAndReftype
				(rollno, sem, pgm, ssd, sed, reftype);

	
		if (feereceipt1.size() > 0)
			return true;



		return false;
	}

	@Override
	public List<Student> getvalidsemesters(String pgm, String ssd, String sed) throws Exception {
		// TODO Auto-generated method stub
		List<Student> validsemesters = new ArrayList<>();

		validsemesters = (List<Student>) em.createNamedQuery("getsemesters", Student.class).setParameter("ssd", ssd)
				.setParameter("sed", sed)

				.setParameter("pgm", pgm).getResultList();
		if (validsemesters.size() == 0)
			throw new Exception("Semester not valid for " + pgm + "for  semester dates:" + ssd + " and " + sed);
		else
			return validsemesters;
	}

	@Override
	public List<Student> getdefaulter(String rollno) {

		List<Defaulters> thedefaulter = null;
		List<Student> defaulterlist = new ArrayList<>();
		thedefaulter = theDefaulterRepository.findAllByRollnumberAndDefaulter(rollno, 1);

		if (thedefaulter.size() > 0) {

			Student studentobj = new Student();
			thedefaulter.forEach(rec -> {
				studentobj.setRoll_number(rec.getRollnumber());
				studentobj.setSemestercode(rec.getSemestercode());

				studentobj.setProgramid(rec.getProgramid());
				studentobj.setProgramname(rec.getProgramname());
				studentobj.setAmount(rec.getAmount() + rec.getFine());
				studentobj.setDefaulter("Y");
				studentobj.setEntityid(rec.getEntity_id());
				studentobj.setSemesterstartdate(rec.getSemester_start_date());
				studentobj.setSemesterenddate(rec.getSemester_end_date());
				studentobj.setFeetype(rec.getFeetype());

				defaulterlist.add(studentobj);
			});

		}
		return defaulterlist;
	}

	public Student getFeeAmount(String pgm, String sem, String branch, String spec, String learningmode, String latefee,
			String lab) throws Exception {

		Student studentfee = new Student();
		float feeamount = 0;
		List<ProgramFee> programfee = new ArrayList<ProgramFee>();
		programfee = theprogramFeeRepository.findByProgramidAndSemestercodeAndBranchidAndSpecializationidAndMode(pgm,
				sem, branch, spec, learningmode);
		if (programfee.size() > 0) {

		} else {
			programfee = theprogramFeeRepository.findByProgramidAndSemestercodeAndBranchidAndMode(pgm, sem, branch,
					learningmode);
			if (programfee.size() > 0) {

			} else {
				programfee = theprogramFeeRepository.findByProgramidAndSemestercodeAndMode(pgm, sem, learningmode);
				if (programfee.size() > 0) {

				} else {
					throw new Exception("Fee Set up not ready for Program :" + pgm + " Branch :" + branch
							+ " Speclization :" + spec + " And :" + sem);
				}
			}
		}

		feeamount = programfee.get(0).getAmount();
		studentfee.setAmount(feeamount);
		studentfee.setFeetype(programfee.get(0).getFeetype());
		if (feeamount == 0)
			return studentfee;

		if (latefee.equalsIgnoreCase("S"))
			studentfee.setLatefee(delaycharges2);

		if (latefee.equalsIgnoreCase("L"))
			studentfee.setLatefee(delaycharges1);

		if (lab.equalsIgnoreCase("Y"))
			studentfee.setLabfee(labfee);

		return studentfee;
	}

	@Override
	public java.sql.Date stringToDate(String dt) throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String today = df.format(new Date());
		String year = today.substring(0, 4);

		dt = year + '-' + dt;
		java.sql.Date date = java.sql.Date.valueOf(dt);

		return date;

	}
	
	
	@Override
	public java.sql.Date  subYear(java.sql.Date date, int noofyears) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        
       c.add(c.YEAR, -noofyears); 
       return new java.sql.Date(c.getTime().getTime());
//       Date date1= c.getTime();
//       String dt=df.format(date1);
  //     java.sql.Date date2 = java.sql.Date.valueOf(dt);
       
        //java.sql.Date date1= (java.sql.Date)c.getTime();
               
        //return date2;
    }

	@Override
	public List<Student> getapplicantdetail(String appno) {
		// TODO Auto-generated method stub

		List<Student> theapplicant = new ArrayList<>();
		Year thisYear = Year.now();
		System.out.println(String.valueOf(thisYear));
		
		
		
		
		
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			DateTimeFormatter format =DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String str = String.valueOf(thisYear)+"-07-01";
			//Date sessionstartdate = format.parse(str,format);
			
			 LocalDate date = LocalDate.parse(str, format);
			
			theapplicant = (List<Student>) em.createNamedQuery("getapplicantdetail", Student.class)
					.setParameter("appno", appno)
					.setParameter("sessionstartdate", date)
					.getResultList();
			Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			theapplicant.get(0).setSemesterstartdate(date1);
		
			
			return theapplicant;
			
		} catch (Exception e) {
			return null;
		}
		
	

	}

	@Override
	public Student getadmissiondetail(String appno) throws Exception {
		// TODO Auto-generated method stub

		String aplno = appno.substring(2,8);
		//String aplno = appno;
		List<Student> studentlist = new ArrayList<Student>();

		Student theapplicant = new Student();
		List<Admfeedates> theadmfeedates = new ArrayList<Admfeedates>();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String today = df.format(new Date());
		java.sql.Date date = java.sql.Date.valueOf(today);
		// getFeeAmount(String pgm ,String sem ,String branch,String spec,String
		// learningmode,String latefee,String lab)

		theadmfeedates = theadmfeedatesRepository.findByApplicationnumberAndLastdateGreaterThanEqual(appno, date);
		if (theadmfeedates.size() > 0) {
			if (theadmfeedates.get(0).getFeestatus()!=null &&theadmfeedates.get(0).getFeestatus().equalsIgnoreCase("rec"))
				throw new Exception("Fee Already Paid");
			theapplicant = getFeeAmount(theadmfeedates.get(0).getProgramid(), theadmfeedates.get(0).getSemester(),
					theadmfeedates.get(0).getBranchid(), "00", theadmfeedates.get(0).getLearningmode(), "N", "N");

			String studentname = em.createNamedQuery("getapplicantname").setParameter("appno", aplno).getSingleResult()
					.toString();
			theapplicant.setStudentname(studentname);
			theapplicant.setApplicationnumber(appno);
			theapplicant.setBranchid(theadmfeedates.get(0).getBranchid());
			theapplicant.setProgramid(theadmfeedates.get(0).getProgramid());
			theapplicant.setSemestercode(theadmfeedates.get(0).getSemester());
			theapplicant.setMode(theadmfeedates.get(0).getLearningmode());
			theapplicant.setSemesterstartdate(theadmfeedates.get(0).getSemesterstartdate());
			theapplicant.setSemesterenddate(theadmfeedates.get(0).getSemesterenddate());
			theapplicant.setEntityid(theadmfeedates.get(0).getEntityid());
			theapplicant.setLatefee(0);

		}

		return theapplicant;

	}



	@Override
	public String savestudentfee(Student student)  {
		
				
		try {

		Studentfeereceipt sfr = null;	
		int paymentid =student.getPayment().getId();
		sfr =thefeereceiptRepository.findByPayment_id(paymentid);
		
		if( sfr==null) {
			
			Date  now =  new Date();
			Timestamp timestamp = new Timestamp(now.getTime());
			sfr = new Studentfeereceipt();
			sfr.setInsert_time(timestamp);
			sfr.setCreatedby(student.getMethod());
		}else {
			Date  now =  new Date();
			Timestamp timestamp = new Timestamp(now.getTime());
			sfr.setModification_time(timestamp);
			sfr.setModifiedby(student.getMethod());
		}
		
			
		sfr.setProgramid(student.getProgramid());
		sfr.setRollnumber(student.getRoll_number());
		sfr.setReftype(student.getReftype());
		sfr.setSemester(student.getSemestercode());
		sfr.setPayment(student.getPayment());
		sfr.setSemesterstartdate(student.getSemesterstartdate());
		sfr.setSemesterenddate(student.getSemesterenddate());
		sfr.setEntityid(student.getEntityid());
		sfr.setLatefee(BigDecimal.valueOf(student.getLatefee()));
		sfr.setAmount( BigDecimal.valueOf(student.getAmount())  );
		sfr.setFeetype(student.getFeetype());
		
		
		
		
		
		thefeereceiptRepository.save(sfr);

				
		}
		catch(Exception e) {
			sbiservice.logerror(student.getATRN(), student.getMerchantorderno(), String.valueOf(student.getAmount()),  e.getMessage(),"savestudentfee");
  			return "error";
		}
		
				 
		return "success";	
		
	}
	
	
	public String savestudentfeeCopy(String[] data)  {
		
		
		 //* otherdetail structure 

   	// category[0]-rollnumber[1]-studentname[2]-programname[3]-reftype[4]-semesterstartdate[5]-semesterenddate[6]
   	//-latefee[7]-entityid[8]-programid[9]-pendingsemester[10]-feepending[11]-feetype[12]

			
		
		Studentfeereceipt sfr = new Studentfeereceipt();
		
		BigDecimal latefeebig =BigDecimal.ZERO;
		BigDecimal amountbig=BigDecimal.ZERO;
		
		
		String refno ="";
		String reftype ="";
		int paymentid;
		String Semester_code="";
		String entityid ="";
		String Programid = "";
		String ssd="";
		String sed="";
		String amt="";
		String latefee="";
		String ATRN="";
		String merchantorder="";
		String category="";
		String feetype="";
		try {
			String OtherDetails = data[5];
			String resdata[]= OtherDetails.split("\\,");
		// extract data from dv data
		amt=data[7];
		ATRN=data[1];
		merchantorder=data[6];
		
		// extract student details from  other details of dv data
		category=resdata[0];
		refno = resdata[1];
		reftype = resdata[4];
		Semester_code=resdata[10];
		entityid=resdata[8];
		Programid=resdata[9];
		ssd=resdata[5];
		sed=resdata[6];
		latefee=resdata[7];
		feetype=resdata[12];
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date semstartdate=dateFormat.parse(ssd);
		Date semenddate=dateFormat.parse(sed);
		
		 if ( sbiservice.isNumeric(latefee)) {
			 	Double latefeedouble=      Double.parseDouble(latefee);
				 latefeebig =     BigDecimal.valueOf(latefeedouble);	 
		 }
		 
		 if ( sbiservice.isNumeric(amt)) {
			 	Double amtdouble=      Double.parseDouble(amt);
				 amountbig =     BigDecimal.valueOf(amtdouble);	 
		 }
		

		Payment payment = sbiservice.findPaymentByATRN(ATRN);
		if (payment==null)
			payment = sbiservice.findPaymentByMerchantorderno(merchantorder);
		if (payment==null) {
			sbiservice.logerror(ATRN, merchantorder, String.valueOf(amountbig) ,"Record not found in Payment table","savestudentfee");
 			return "error";
		}
				
				
		sfr.setProgramid(Programid);
		sfr.setRollnumber(refno);
		sfr.setReftype(reftype);
		sfr.setSemester(Semester_code);
		sfr.setPayment(payment);
		sfr.setSemesterstartdate(semstartdate);
		sfr.setSemesterenddate(semenddate);
		sfr.setEntityid(entityid);
		sfr.setLatefee(latefeebig);
		sfr.setAmount(amountbig);
		sfr.setFeetype(feetype);
		
		
		Date  now =  new Date();
		Timestamp timestamp = new Timestamp(now.getTime());

		sfr.setInsert_time(timestamp);
		
		
		thefeereceiptRepository.save(sfr);
		
		
		}
		catch(Exception e) {
			sbiservice.logerror(ATRN, merchantorder, String.valueOf(amt) , e.getMessage(),"savestudentfee");
 			return "error";
		}
		 
		return "success";	
		
	}
	
		
    @Transactional()
	@Override
	public String saveStudentAppfee(Student student) {
		
	
		try {
			
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			// formatter.
			String str= df.format(student.getSemesterstartdate());
			java.sql.Date date = java.sql.Date.valueOf(str);
		
			 
		int count = em.createNamedQuery("updatestudentappfee")
				.setParameter(1,student.getAmount())
				.setParameter(2, "saveStudentAppfee")
				.setParameter(3, student.getATRN())
				.setParameter(4, student.getMerchantorderno())
				
				.setParameter(5,student.getApplicationnumber() )
				.setParameter(6, date).executeUpdate();
			
//		javax.persistence.Query query=	em.createQuery("update admissionform_live.student_application_status set  actual_deposited_fee=:amount"
//					+" ,fee_verification_time=now(),fee_verified_by=:method ,verification_status='rec' "
//					+"  where application_number=:appno"
//					+ "  and session_start_date =:ssd"
//					);
		
//		query.setParameter(appno,student.getApplicationnumber() );
//		int count =query.executeUpdate();
//			
//			
				
				
		
		return "success";
	}catch(Exception e ) {
		e.printStackTrace();
		sbiservice.logerror(student.getATRN(), student.getMerchantorderno(), String.valueOf(student.getAmount()) , e.toString(),"saveStudentAppfee");
	    return "error";
	}
	}

	@Override
	public boolean isSemregistered(String rollno,String sem , java.sql.Date semesterstartdate,
			java.sql.Date semesterenddate) {
		
	
			
		
	String count= em.createNamedQuery("isStudentRegistered")
			.setParameter("rollno", rollno)
			
			.setParameter("ssd", semesterstartdate)
			.setParameter("sed", semesterenddate)
			
			.setParameter("sem", sem)
			.getSingleResult().toString();
	
	
	if(count.equalsIgnoreCase("0"))
		return false;
	else
		return true;
		
		
	}




	
}
