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
	public StudentController (StudentService thestudentservice) {
		studentservice=thestudentservice;
	}
	
	@GetMapping("/student")
	public List getstudent(@RequestParam String rollno) throws Exception {
		

		List <Student> thestudent =studentservice.getstudentdetail(rollno);
		List<Programfeedates> epaymentstatus = studentservice.getepaymentstatus();
		return thestudent;
	}
	
}
