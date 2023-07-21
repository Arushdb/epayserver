package com.dayalbagh.epay.controller;

import java.util.Arrays;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dayalbagh.epay.AES256Bit;
import com.dayalbagh.epay.model.Student;

import com.dayalbagh.epay.service.SBIService;

@Controller
public class PaymentController {
	
	@Autowired
	private SBIService sbiservice;

	 @GetMapping("/makepayment")
	    public String showForm(Model model,HttpServletRequest request) {
	      Student student = new Student();
	      
	     String totalfee = request.getParameter("totalfee");
	     String Otherdetail=request.getParameter("Otherdetail");
	     //for testing
	     totalfee = "1.00" ;
	     
	     String str1 = sbiservice.encrypt(totalfee,Otherdetail);
	     
	     
	     System.out.println("encrypted String:"+str1);
	     
	     System.out.println("Decrypted String:"+sbiservice.decrypt(str1));
	     
	     
	     
	      student.setEncryptTrans(str1);
	        model.addAttribute("student", student);
	      //  model.addAttribute("EncryptTrans",str1);
	         
	      
	         
	        return "payment_request";
	    }
	

	 @PostMapping("/paymentsuccess")
	 public String submitForm(@ModelAttribute("encData") String encData,
			 @ModelAttribute("merchIdVal") String merchIdVal,
			 @ModelAttribute("Bank_Code") String Bank_Code) {
		 
		
	      
	     System.out.println("encrypted Data:"+encData);
	     System.out.println("Decrypted String:"+sbiservice.decrypt(encData));
	     System.out.println(merchIdVal);
	     System.out.println(Bank_Code);
	      
	     return "payment_success";
	 }
	 
	 
	 @PostMapping("/paymentfailure")
	 public String paymentFailure(@ModelAttribute("encData") String encData,
			 @ModelAttribute("merchIdVal") String merchIdVal,
			 @ModelAttribute("Bank_Code") String Bank_Code) {
	      
		 System.out.println("encrypted Data:"+encData);
	     System.out.println("Decrypted String:"+sbiservice.decrypt(encData));
	     System.out.println(merchIdVal);
	     System.out.println(Bank_Code);
	      
	     return "payment_failure";
	 }
	 
	 

}
