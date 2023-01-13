package com.dayalbagh.epay.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.service.StudentService;
import com.google.gson.Gson;

import net.minidev.json.JSONArray;

@RestController
@RequestMapping("/api/test")
public class StudentController {

	private StudentService studentservice;

	@Autowired
	public StudentController(StudentService thestudentservice) {
		studentservice = thestudentservice;
	}

	@GetMapping("/student")
	public String getstudent(@RequestParam String rollno,@RequestParam String semestercode) throws Exception {

		List<Student> thestudent = studentservice.getstudentdetail(rollno);
		Student studentfee = new Student();
		
		String pgm = thestudent.get(0).getProgramid();
		String branch = thestudent.get(0).getBranchid();
		String spec = thestudent.get(0).getSpecializationid();
		String learningmode = thestudent.get(0).getMode(); 
				
		if(!(learningmode.equalsIgnoreCase("DEC")))
			learningmode="REG";
			
		String latefee ="N";
		String lab ="N"; // will use  to charge lab fee;
		Character type = thestudent.get(0).getType();
		List<Programfeedates> epaymentstatus = studentservice.getepaymentstatus(type);
		String ssd = epaymentstatus.get(0).getSemesterstartdate();
		String sed = epaymentstatus.get(0).getSemesterenddate();
	    
		java.sql.Date semesterstartdate = studentservice.stringToDate(ssd);
		java.sql.Date semesterenddate = studentservice.stringToDate(sed);
				
		
		List<Student> defaulter = studentservice.getdefaulter(rollno);
		
		if(defaulter.size()>0) {
			defaulter.forEach(rec -> {
				rec.setStudentname(thestudent.get(0).getStudentname());
				rec.setFeepending("D"); // D for  defaulter

			});
			
			Gson gson = new Gson();
			
			return gson.toJson(defaulter);
		
			 //return defaulter;
			
		}
		
		List<Student> thependingfee = studentservice.getpendingfee(rollno,semesterstartdate);
		latefee="S";
		
			
			for(int i=0;i<thependingfee.size();i++) {
									
			studentfee =studentservice.getFeeAmount(pgm, thependingfee.get(i).getSemestercode(), branch, spec, learningmode, latefee, lab);
			if (studentfee.getAmount()==0)
			continue;
			thestudent.get(0).setAmount(studentfee.getAmount());
			thestudent.get(0).setLatefee(studentfee.getLatefee());
			thestudent.get(0).setLabfee(studentfee.getLabfee()); 
			thestudent.get(0).setSemestercode(thependingfee.get(i).getSemestercode());
			thestudent.get(0).setFeepending("Y");
			thestudent.get(0).setMode(learningmode);
			thestudent.get(0).setSemesterstartdate(thependingfee.get(i).getSemesterstartdate());
			thestudent.get(0).setSemesterenddate(thependingfee.get(i).getSemesterenddate())	;		
			break;
			

		}
		
		
		
		if(thestudent.get(0).getAmount()>0) {
			Gson gson = new Gson();
			
			return gson.toJson(thestudent);
		
		}else {

			
			
			List<Student> thevalidsemesters = studentservice.getvalidsemesters(pgm, ssd, sed);
		
			Boolean semvalid = false;
			
			for(int i=0 ;i<thevalidsemesters.size();i++) {
				
				if (thevalidsemesters.get(i).getSemestercode().equalsIgnoreCase(semestercode)) {
					semvalid=true;
					break;
				}
			}
			
			if(semvalid) {
				
			Boolean feepaid =	studentservice.isfeealreadypaid(rollno, semestercode, pgm, semesterstartdate , semesterenddate);
				
				Boolean feeisdelayed = studentservice.getpaymentdelaystatus(type);
				if(feeisdelayed)
					latefee="L";
				else
					latefee="N";
				studentfee=	studentservice.getFeeAmount(pgm,semestercode , branch, spec, learningmode, latefee, lab);
				
				thestudent.get(0).setAmount(studentfee.getAmount());
				thestudent.get(0).setLatefee(studentfee.getLatefee());
				
				thestudent.get(0).setLabfee(studentfee.getLabfee());
				thestudent.get(0).setSemestercode(semestercode);
				thestudent.get(0).setFeepending("N");
				thestudent.get(0).setStatus(latefee);
			
			}else {
				throw new Exception("Semester:"+semestercode+" is invalid for current period");
			}
				
				Gson gson = new Gson();
			
			return gson.toJson(thestudent);
			
			//return thestudent;
		}

	}

}
