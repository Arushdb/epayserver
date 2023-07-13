package com.dayalbagh.epay.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.dayalbagh.epay.AES256Bit;
import com.dayalbagh.epay.model.Student;

@Service
public class SBIServiceImpl implements SBIService {

	@Override
	public Student encrypt(Student detail) {
		// TODO Auto-generated method stub
		String MerchantId = "1000112";
        String AggregatorId = "SBIEPAY";
        String SuccessURL = "https://admission.dei.ac.in:8443/epay80/";
        String FailURL = "https://admission.dei.ac.in:8443/epay80/";
        String  OperatingMode = "DOM";
        String  MerchantCountry = "IN";
        String  MerchantCurrency = "INR";
       
        float feeamount =0;
        feeamount = detail.getAmount()+detail.getLatefee()+detail.getLabfee();
        String TotalDueAmount =String.valueOf(feeamount);
        	
        //String  MerchantOrderNo = "TXN202105061303111539"; //Order no shoud be unique every time
        String MerchantOrderNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
               MerchantOrderNo=detail.getRoll_number()+MerchantOrderNo;
        
        String  MerchantCustomerID = "2";
        String  Paymode = "NB";
        String Accesmedium = "ONLINE";
        String  TransactionSource = "ONLINE";
        String studentdetail ="";
        if (!(detail.getRoll_number().equalsIgnoreCase("")))
        	studentdetail=detail.getRoll_number();
        else
        	studentdetail=detail.getApplicationnumber();
        
        if (!(detail.getProgramid().equalsIgnoreCase("")))
        	studentdetail=studentdetail+detail.getProgramid();
        
        if (!(detail.getSemestercode().equalsIgnoreCase("")))
        	studentdetail=studentdetail+detail.getSemestercode();
        
        String Otherdetail = studentdetail+detail.getStudentname();
        		//"Arush;212099;SM1;";
        String key_Array = "A7C9F96EEE0602A61F184F4F1B92F0566B9E61D98059729EAD3229F882E81C3A";
        
        
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());

   		String Single_Request = MerchantId + "|" + OperatingMode + "|" + MerchantCountry + "|" + MerchantCurrency + "|" + TotalDueAmount + "|" + Otherdetail + "|" + SuccessURL + "|" + FailURL + "|" + AggregatorId + "|" + MerchantOrderNo 
   				+ "|" + MerchantCustomerID + "|" + Paymode + "|" + Accesmedium + "|" + TransactionSource;

        
        SecretKeySpec key=    AES256Bit.readKeyBytes(key_Array);
        
        String s1 =AES256Bit.encrypt(Single_Request, key);
        
        
        detail.setSbistr(s1);
        return detail; 
	}

	@Override
	public String decrypt(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String verifyPayment() {
		// TODO Auto-generated method stub
		return null;
	}

}
