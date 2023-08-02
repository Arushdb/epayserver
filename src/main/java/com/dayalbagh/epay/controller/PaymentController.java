package com.dayalbagh.epay.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dayalbagh.epay.AES256Bit;
import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;
import com.dayalbagh.epay.service.SBIService;
import com.dayalbagh.epay.service.StudentService;

@Controller

public class PaymentController {
	
	@Autowired
	private SBIService sbiservice;
	
	@Autowired
	private StudentService studentservice;

	 @GetMapping("/makepayment")
	    public String showForm(Model model ,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	      Student student = new Student();
	      
	     String totalfee = request.getParameter("totalfee");
	     String Otherdetail=request.getParameter("Otherdetail");
	     

	     //for testing
	    
	     String merchantid = sbiservice.getMerchantId();
	     
	     String encryptdata = sbiservice.encrypt(totalfee,Otherdetail);
	     
	    
	     
	     
	     System.out.println("encrypted String:"+encryptdata);
	     
	     System.out.println("Decrypted String:"+sbiservice.decrypt(encryptdata));
	     
	     //String decryptdata =sbiservice.decrypt(encryptdata);
	     
	     student.setEncryptTrans(encryptdata);
	     student.setMerchantid(merchantid);
	   
	     
	     
	        model.addAttribute("student", student);
	      //  model.addAttribute("EncryptTrans",str1);
	         
	      
	         
	        return "payment_request";
	    }
	

	 @PostMapping("/paymentsuccess")
	 public String Paymentsuccess(@ModelAttribute("encData") String encData,
			 @ModelAttribute("merchIdVal") String merchIdVal,
			 @ModelAttribute("Bank_Code") String Bank_Code,Model model) throws ParseException
	 
	 {
		 
		 String dvdata[];
		 Studentfeereceipt feereceipt =new Studentfeereceipt();
		 
		 System.out.println("encData :"+encData);
		
		
		 
		 //String str1 = request.getAttribute("encData").toString();
	
		 
		  encData = encData.replaceAll(" ", "+");
		 
		 
		 
		 String str = sbiservice.decrypt(encData);
		 
		 
		 String resdata[]= str.split("\\|");
		 System.out.println(resdata);
		  
	      String message = resdata[2];  
	      
	      sbiservice.savePayment(resdata);
	     
	      
	      if( message.equalsIgnoreCase("Success")) {
	    	  
	    	  System.out.println("in side verify payment");
	    	  // Do double verification
	    	  	    	
	    	  String dvstatus =sbiservice.verifyPayment(resdata);
	    	  
	    	 
	    	  dvdata= dvstatus.split("\\|");
	 
	    	  
	    	  model.addAttribute("message", dvdata[2]);
	    	  if (dvdata[2].equalsIgnoreCase("Success"))
	    		  
	    		  
	    		  studentservice.savestudentfee(dvdata);
	    		  
	    		  
	    		  return "payment_success";
	      }
	   
	     System.out.println("encrypted Data:"+encData);
	     System.out.println("Decrypted String:"+resdata);
	     System.out.println(merchIdVal);
	     System.out.println(Bank_Code);
	    
	     return "payment_failure";
	 }
	 
	 @PostMapping("/pushpayment")
	 public String pushpayment(@ModelAttribute("pushRespData") String encData,
			 @ModelAttribute("merchIdVal") String merchIdVal,
			 @ModelAttribute("Bank_Code") String Bank_Code,Model model) {
		 
		 
		 encData = encData.replaceAll(" ", "+");
		 String str = sbiservice.decrypt(encData);
		 String resdata[]= str.split("\\|");
		 String message = resdata[2];  
		 model.addAttribute("message", message);
		 
		 
	      
		 System.out.println("Push encrypted Data:"+encData);
	     System.out.println("Push Decrypted String:"+sbiservice.decrypt(encData));
	     System.out.println(merchIdVal);
	     System.out.println(Bank_Code);
	      
	     return "pushpayment_success";
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
