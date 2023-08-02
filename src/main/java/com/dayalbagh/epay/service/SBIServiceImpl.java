package com.dayalbagh.epay.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.dayalbagh.epay.AES256Bit;
import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.repository.PaymentRepository;


@PropertySource("classpath:message.properties")
@Service
public class SBIServiceImpl implements SBIService {
	//String key_Array = "A7C9F96EEE0602A61F184F4F1B92F0566B9E61D98059729EAD3229F882E81C3A";
	//static String gatewayUrl = "https://test.sbiepay.sbi/payagg/statusQuery/getStatusQuery" ;
	//static String Merchant_ID =  "1000112";
	
	@Value("${paymentgatewaylive}")
	private String paymentgatewaylive;
	
	@Value("${testmerchantid}")
	private String testmerchantid;
	
	@Value("${testAggregatorId}")
	private String testAggregatorId;
	
	@Value("${testgatewayUrl}")
	private String testgatewayUrl;
	
	@Value("${testSuccessURL}")
	private String testSuccessURL;
	
	@Value("${testFailURL}")
	private String testFailURL;
	
	@Value("${testkey_Array}")
	private String testkey_Array;
	
	//////
	
	@Value("${livemerchantid}")
	private String livemerchantid;
	
	@Value("${liveAggregatorId}")
	private String liveAggregatorId;
	
	@Value("${livegatewayUrl}")
	private String livegatewayUrl;
	
	@Value("${liveSuccessURL}")
	private String liveSuccessURL;
	
	@Value("${liveFailURL}")
	private String liveFailURL;
	
	
	
	@Value("${livekey_Array}")
	private String livekey_Array;
	
	static String MerchantId;
	static String AggregatorId;
	
	public String getMerchantId() {
		return MerchantId;
	}

	static String SuccessURL;
	static String FailURL;
	static String gatewayUrl;
	static String key_Array;
	
	
	PaymentRepository thepaymentrepository ;
	
		
	public SBIServiceImpl(PaymentRepository paymentrepository) {
		
		this.thepaymentrepository = paymentrepository;
	}







	@PostConstruct
	private void init() {
		if (paymentgatewaylive.equalsIgnoreCase("no")){
			MerchantId =testmerchantid;
			AggregatorId=testAggregatorId;
			SuccessURL =testSuccessURL;
			FailURL=testFailURL;
			gatewayUrl=testgatewayUrl;
			key_Array=testkey_Array;
		}
		if (paymentgatewaylive.equalsIgnoreCase("yes")){
			MerchantId =livemerchantid;
			AggregatorId=liveAggregatorId;
			SuccessURL =liveSuccessURL;
			FailURL=liveFailURL;
			gatewayUrl=livegatewayUrl;
			key_Array=livekey_Array;
		}
		
		
	}
	
	
	

	


	@Override
	public String encrypt(String amount,String Otherdetail) {
		init();
		// TODO Auto-generated method stub
		
		//String MerchantId = "1000112";
        //String AggregatorId = "SBIEPAY";
        //String SuccessURL = "https://admission.dei.ac.in/epay/paymentsuccess";
        
        //String FailURL = "https://admission.dei.ac.in/epay/paymentfailure";
        String  OperatingMode = "DOM";
        String  MerchantCountry = "IN";
        String  MerchantCurrency = "INR";
       
        String TotalDueAmount =amount;
        	
        //String  MerchantOrderNo = "TXN202105061303111539"; //Order no shoud be unique every time
        String MerchantOrderNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
        Random randomno = new Random();
        int no = randomno.nextInt(9000)+1000;
        String strno = String.valueOf(no);
        MerchantOrderNo = MerchantOrderNo+strno ;
        
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

	
	
	
	public static String DoubleVerification(String message) {

        String responseCode = "";
        InputStream stream = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;

        try {

               HashMap<String, String> params = new HashMap<String,String>();

               

               String encdata = message;

//                   params.put("encdata", encdata);

                   params.put("queryRequest", message);
			        params.put("aggregatorId", "SBIEPAY");
			        params.put("merchantId", MerchantId);
       
               URL url = new URL(gatewayUrl);

               HttpURLConnection  httpConn = (HttpURLConnection) url.openConnection();
               httpConn.setDoInput(true); // true indicates the server returns response
               StringBuffer requestParams = new StringBuffer();

               if (params != null && params.size() > 0) {
                     httpConn.setDoOutput(true); // true indicates POST request

                     // creates the params string, encode them using URLEncoder

                     Iterator<String> paramIterator = params.keySet().iterator();

                     while (paramIterator.hasNext()) {

                            String key = paramIterator.next();

                            String value = params.get(key);

                            requestParams.append(URLEncoder.encode(key, "UTF-8"));

                            requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"));

                            requestParams.append("&");

                     }



                     // sends POST data
                     
                     System.out.println("requestParams"+requestParams);

                     OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

                     writer.write(requestParams.toString());

                     writer.flush();



                     //Response Code

                     int responseMsg = httpConn.getResponseCode();
                     
                     System.out.println("double verification response message"+responseMsg);

                     //GenericExceptionLog.log("Response Message"+responseMsg, "AggGatewayURLConnection");



                     // Reading Response

                     stream = httpConn.getInputStream();

                     isr = new InputStreamReader(stream);

                     reader = new BufferedReader(isr);

                     StringBuilder sb = new StringBuilder();

                     String line = null ;

                     while((line = reader.readLine()) != null) {

                            sb.append(line).append("\n");

                     }

                     stream.close();



                     responseCode = sb.toString() ;

                     responseCode = responseCode.trim();
                     System.out.println("double verification response"+responseCode);
               }

        } catch (MalformedURLException e) {

//               GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");

        } catch (ProtocolException e) {

//               GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");

        } catch (IOException e) {

//               GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");

        } finally {

               try {

                     if (reader != null) {

                            reader.close();

                            reader = null;

                     }

                     if (isr != null) {

                            isr.close();

                            isr = null;

                     }

                     if (stream != null) {

                            stream.close();

                            stream = null;

                     }

               } catch (Exception e) {

                   // GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");
            	   System.out.println("Exception:"+e);
               }

        }



        return responseCode;


}

	@Override
	public String verifyPayment(String[] resdata) {
		
		String Merchant_Order_Number = resdata[0];
		String ATRN = resdata[1];
		
		String Amount =resdata[3];
		
		
		String queryRequest = ATRN+"|"+MerchantId+"|"+Merchant_Order_Number+"|"+Amount;
		
		String status =DoubleVerification(queryRequest);
		
		return status;
	}
	
	// save transaction in payment
	@Override
	public void savePayment(String[] data ) throws ParseException {
		
		
	   	
    	  //** DV response structure 
 //   	  Merchant ID|SBIePayRefID/ATRN|Transaction Status|Country|
  //  	  Currency|Other Details|MerchantOrderNumber|Amount|Status Description|
 //   	  BankCode|Bank Reference Number|Transaction Date|Pay Mode|CIN|
 //   	  Merchant ID|Total Fee GST|Ref1|Ref2|Ref3|Ref4|Ref5|Ref6|Ref7|Ref8|Ref9| Ref10
	   	  
	   	  //** Browser Response
	//	  Merchant Order Number|SBIePayRefID/ATRN|Transaction Status|Amount|
	//    Currency|Pay Mode|Other Details|Reason/Message|Bank Code|Bank Reference Number|
	// 	  Transaction Date|Country|CIN|Merchant ID|Total Fee GST |Ref1|Ref2|Ref3|Ref4|Ref5|Ref6|Ref7|Ref8|Ref9
  	  
		  Payment payment = new Payment();
	   	  String ATRN="";
	   	  String Transaction_Status="";
	   	  String Country="";
	   	  String Currency="" ;
	   	  String Other_Details="";
	   	  String MerchantOrderNumber="";
	   	  String Amount="";
	   	  String Status_Description="";
	   	  String BankCode="";
	   	  String Bank_Reference_Number="";
	   	  String Transaction_Date="";
	   	  String CIN="" ;
	   	  String Pay_Mode="";
	   	  String Total_Fee_GST="";
	   	  String Reason="";
	   	  String Merchant_ID="";
	   	  Double amt=0.0;
	   	  
	   	 
	   	  
	   	  //Extract Data for Browser response
	   
	   		MerchantOrderNumber =data[0];  
	   		ATRN = data[1];
	   		Transaction_Status=data[2];
	   		Amount = data[3];
	   		Currency = data[4];
	   		Pay_Mode=data[5];
	   		Other_Details=data[6];
	   		Reason = data[7];
	   		BankCode=data[8];
	   		Bank_Reference_Number=data[9];
	   		Transaction_Date=data[10];
	   		Country =data[11];
	   		CIN=data[12];
	   		Merchant_ID=data[13];
	   		Total_Fee_GST=data[14];
	   		   	  
	   	  
	   	  payment.setMerchant_orderno(MerchantOrderNumber);
    	  payment.setATRN(ATRN);
    	  payment.setTransaction_status(Transaction_Status);
    	  
    	  if(isNumeric(Amount)) {
    		  amt =Double.parseDouble(Amount); 
    		  payment.setAmount(BigDecimal.valueOf(amt));
    	  }else {
    		  
    	  }
    	 
    	  
    	  
    	 
    	  payment.setCurrency(Currency);
    	  payment.setPayment_mode(Pay_Mode);
    	  payment.setOtherdetail(Other_Details);
    	  payment.setReason(Reason);
    	  payment.setBank_code(BankCode);
    	  payment.setBank_Reference_Number(Bank_Reference_Number);
    	  
    	  
    	  try {
    		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    		    Date parsedDate = dateFormat.parse(Transaction_Date);
    		 		    
    		    
    		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    		    payment.setTransaction_date(timestamp);
    		    
    		  
    		} catch(Exception e) { //this generic but you can control another types of exception
    		    // look the origin of excption 
    		}
    	  
    	 
    	
    	
    	
    	  LocalDateTime now = LocalDateTime.now();  

    	  payment.setInsert_time(now);
    	  payment.setCountry(Country);
    	  payment.setCIN(CIN);
    	  payment.setMerchant_ID(Merchant_ID);
    	  if(isNumeric(Total_Fee_GST)) {
    		  amt =Double.parseDouble(Total_Fee_GST); 
    		  payment.setTotal_Fee_GST(BigDecimal.valueOf(amt)); 
    	  }else {
    		  
    	  }
    		  
    	 
    	  
    	
    	  
    	    	  
    	  
    	 
       	  thepaymentrepository.save(payment);
			
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

}





