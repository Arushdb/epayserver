package com.dayalbagh.epay.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dayalbagh.epay.model.Programfeedates;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.service.StudentService;

@RestController
@RequestMapping("/api/test")
public class StudentController {

	private StudentService studentservice;

	@Autowired
	public StudentController(StudentService thestudentservice) {
		studentservice = thestudentservice;
	}

	@GetMapping("/student")
	public List getstudent(@RequestParam String rollno, @RequestParam String semester) throws Exception {

		List<Student> thestudent = studentservice.getstudentdetail(rollno);

		String program = thestudent.get(0).getProgramid();
		Character type = thestudent.get(0).getType();
		List<Programfeedates> epaymentstatus = studentservice.getepaymentstatus(type);
		int feedateid = epaymentstatus.get(0).getId();
		List<Student> thependingfee = studentservice.getpendingfee(rollno);

		thependingfee.forEach(rec -> {
			rec.setStudentname(thestudent.get(0).getStudentname());
			rec.setFeepending("Y");

		});

		if (thependingfee.size() > 0)
			return thependingfee;
		else {
			Boolean feepaid = studentservice.isfeealreadypaid(rollno, semester, program, feedateid);
            if (feepaid)  
            	throw new Exception("Fee Already Paid");
		 
		}
		return thestudent;
	}

}
