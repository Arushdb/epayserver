package com.dayalbagh.epay.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.dayalbagh.epay.AES256Bit;
import com.dayalbagh.epay.model.Student;

@Service
public class SBIServiceImpl implements SBIService {
	String key_Array = "A7C9F96EEE0602A61F184F4F1B92F0566B9E61D98059729EAD3229F882E81C3A";
	@Override
	public String encrypt(String amount,String Otherdetail) {
		// TODO Auto-generated method stub
		
		String MerchantId = "1000112";
        String AggregatorId = "SBIEPAY";
        String SuccessURL = "https://admission.dei.ac.in/epay/paymentsuccess";
        
        String FailURL = "https://admission.dei.ac.in/epay/paymentfailure";
        String  OperatingMode = "DOM";
        String  MerchantCountry = "IN";
        String  MerchantCurrency = "INR";
       
        String TotalDueAmount =amount;
        	
        //String  MerchantOrderNo = "TXN202105061303111539"; //Order no shoud be unique every time
        String MerchantOrderNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
            
        
        String  MerchantCustomerID = "2";
        String  Paymode = "NB";
        String Accesmedium = "ONLINE";
        String  TransactionSource = "ONLINE";
        String studentdetail ="arush";

        
        
        
        

   		String Single_Request = MerchantId + "|" + OperatingMode + "|" + MerchantCountry + "|" + MerchantCurrency + "|" + TotalDueAmount + "|" + Otherdetail + "|" + SuccessURL + "|" + FailURL + "|" + AggregatorId + "|" + MerchantOrderNo 
   				+ "|" + MerchantCustomerID + "|" + Paymode + "|" + Accesmedium + "|" + TransactionSource;

        
        SecretKeySpec key=    AES256Bit.readKeyBytes(key_Array);
        
        String s1 =AES256Bit.encrypt(Single_Request, key);
       
        
        //detail.setSbistr(s1);
        return s1; 
	}

	@Override
	public String decrypt(String str) {
		// TODO Auto-generated method stub
		
		SecretKeySpec key=    AES256Bit.readKeyBytes(key_Array);
		 return AES256Bit.decrypt(str, key);
		
	}

	@Override
	public String verifyPayment() {
		// TODO Auto-generated method stub
		return null;
	}

}
