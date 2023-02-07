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
	public List<Student> getpendingfee(String rollno, java.sql.Date semesterstartdate) {
		List<Student> student = new ArrayList<>();

		student = (List<Student>) em.createNamedQuery("getpendingfee", Student.class).setParameter("rollno", rollno)
				.setParameter("epaystartdate", epaystartdate).setParameter("semesterstartdate", semesterstartdate)
				.getResultList();

		return student;
	}

	@Override
	public Boolean isfeealreadypaid(String rollno, String sem, String pgm, java.sql.Date ssd, java.sql.Date sed)
			throws Exception {
		List<Studentfeereceipt> feereceipt1 = null;
		List<Studentfeereceipt> feereceipt2 = null;
		List<Student> student = new ArrayList<>();

		// check if student try to pay again in same period.
		feereceipt1 = thefeereceiptRepository
				.findAllByRollnumberAndSemesterAndProgramidAndSemesterstartdateAndSemesterenddate(rollno, sem, pgm, ssd,
						sed);

		// check if student try to pay again for previous passed semester .
		// feereceipt2
		// =thefeereceiptRepository.findAllByRollnumberAndSemesterAndProgramidAndIdNot(rollno,
		// sem, pgm, id);

		if (feereceipt1.size() > 0)
			throw new Exception("Fee Already Paid");

//		if(feereceipt2.size()>0) {
//			student = (List<Student>) em.createNamedQuery("getsemesterstatus", Student.class)
//					.setParameter("semester",sem)
//					.setParameter("rollno" , rollno)
//					
//					.setParameter("pgmid",pgm)
//					.getResultList();
//			if (student.size()>0) 
//			throw new Exception("Fee Already Paid") ;	
//		}
//			

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
	public List<Student> getapplicantdetail(String appno) {
		// TODO Auto-generated method stub

		List<Student> theapplicant = new ArrayList<>();
		theapplicant = (List<Student>) em.createNamedQuery("getapplicantdetail", Student.class)
				.setParameter("appno", appno).getResultList();
		;

		return theapplicant;

	}

	@Override
	public Student getadmissiondetail(String appno) throws Exception {
		// TODO Auto-generated method stub

		String aplno = appno.substring(2);
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
			theapplicant = getFeeAmount(theadmfeedates.get(0).getProgramid(), theadmfeedates.get(0).getSemester(),
					theadmfeedates.get(0).getBranchid(), "00", theadmfeedates.get(0).getLearningmode(), "N", "N");

			String studentname = em.createNamedQuery("getapplicantname").setParameter("appno", aplno).getSingleResult()
					.toString();
			theapplicant.setStudentname(studentname);

		}

		return theapplicant;

	}

	
}
