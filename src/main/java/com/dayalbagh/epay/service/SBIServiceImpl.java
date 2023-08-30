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
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dayalbagh.epay.AES256Bit;
import com.dayalbagh.epay.model.Certificate;
import com.dayalbagh.epay.model.Defaulters;
import com.dayalbagh.epay.model.Epayerrorlog;
import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.PendingPayment;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;
import com.dayalbagh.epay.repository.CertificateRepository;
import com.dayalbagh.epay.repository.DefaulterRepository;
import com.dayalbagh.epay.repository.EpayExceptionRepository;
import com.dayalbagh.epay.repository.FeereceiptRepository;
import com.dayalbagh.epay.repository.PaymentRepository;
import com.dayalbagh.epay.repository.PendingPaymentRepository;


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
	
	@Value("${testaeskey}")
	private String testaeskey;
	
	@Value("${liveaeskey}")
	private String liveaeskey;
	
	
	static String MerchantId;
	static String AggregatorId;
	
	public String getMerchantId() {
		return MerchantId;
	}
	
	@Value("${run-frquency.minutes}")
	private int rate;

	static String SuccessURL;
	static String FailURL;
	static String gatewayUrl;
	static String key_Array;
	static String aeskey;
	
	
	
	@Autowired
	PaymentRepository thepaymentrepository ;
	
	@Autowired
	PendingPaymentRepository thePendingPaymentRepository ;
	
	 @Autowired
	 EpayExceptionRepository theEpayExceptionRepository;
	 
	 @Autowired
	 DefaulterRepository theDefaulterRepository;
	 
	 
	
	
	
		
//	public SBIServiceImpl(PaymentRepository paymentrepository) {
//		
//		this.thepaymentrepository = paymentrepository;
//	}







	@PostConstruct
	private void init() {
		if (paymentgatewaylive.equalsIgnoreCase("no")){
			MerchantId =testmerchantid;
			AggregatorId=testAggregatorId;
			SuccessURL =testSuccessURL;
			FailURL=testFailURL;
			gatewayUrl=testgatewayUrl;
			key_Array=testkey_Array;
			aeskey=testaeskey;
		}
		if (paymentgatewaylive.equalsIgnoreCase("yes")){
			MerchantId =livemerchantid;
			AggregatorId=liveAggregatorId;
			SuccessURL =liveSuccessURL;
			FailURL=liveFailURL;
			gatewayUrl=livegatewayUrl;
			key_Array=livekey_Array;
			aeskey=liveaeskey;
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

         //      GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");

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
		System.out.println("in side verify payment");
		
		
		String queryRequest = ATRN+"|"+MerchantId+"|"+Merchant_Order_Number+"|"+Amount;
		
		String status =DoubleVerification(queryRequest);
		
		return status;
	}
	
	// save transaction in payment
	@Override
	public Student savePayment(String[] data,String dvstatus ,String statusdecription,String method)  	{
		
	   	
    	  //** DV response structure 
 //   	  Merchant ID|SBIePayRefID/ATRN|Transaction Status|Country|
  //  	  Currency|Other Details|MerchantOrderNumber|Amount|Status Description|
 //   	  BankCode|Bank Reference Number|Transaction Date|Pay Mode|CIN|
 //   	  Merchant ID|Total Fee GST|Ref1|Ref2|Ref3|Ref4|Ref5|Ref6|Ref7|Ref8|Ref9| Ref10
	   	  
	   	  //** Browser Response
	//	  Merchant Order Number|SBIePayRefID/ATRN|Transaction Status|Amount|
	//    Currency|Pay Mode|Other Details|Reason/Message|Bank Code|Bank Reference Number|
	// 	  Transaction Date|Country|CIN|Merchant ID|Total Fee GST |Ref1|Ref2|Ref3|Ref4|Ref5|Ref6|Ref7|Ref8|Ref9
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		  TimeZone istTimeZone= TimeZone.getTimeZone("IST");
		  df.setTimeZone(istTimeZone);
		  
		  
		  Payment payment = new Payment();
	   	  String ATRN="";
	   	  String Transaction_Status="";
	   	  String Country="";
	   	  String Currency="" ;
	   	  String Other_Details="";
	   	  String MerchantOrderNumber="";
	   	  String Amount="";
	   	  String Category="";
	  
	   	  String BankCode="";
	   	  String Bank_Reference_Number="";
	   	  String Transaction_Date="";
	   	  String CIN="" ;
	   	  String Pay_Mode="";
	   	  String Total_Fee_GST="";
	   	  String Reason="";
	   	  String Merchant_ID="";
	   	  Double trxamt=0.0;
	   	  Double gstamt=0.0;
	   	  String rollno="";
	   	  String enrollno="";
	   	  String reftype="";
	   	  String insertstatus ="success";
	   	  Student student=null;
	   	  
	   	 try {
	   	  
	   	  //Extract Data from Browser response
	   
	   		MerchantOrderNumber =data[0];  
	   		ATRN = data[1];
	   		Transaction_Status=data[2];
	   		Amount = data[3];
	   		Currency = data[4];
	   		Pay_Mode=data[5];
	   		
	   		Reason = data[7];
	   		BankCode=data[8];
	   		Bank_Reference_Number=data[9];
	   		Transaction_Date=data[10];
	   		Country =data[11];
	   		CIN=data[12];
	   		Merchant_ID=data[13];
	   		Total_Fee_GST=data[14];
	   		
	   		Other_Details=data[6];
	   		
	   	
	   		
	   		
	   		// Get ref number and ref type from other detail
	   		
	   	 String OtherDetails_data[]= Other_Details.split("\\,");  
	   	 System.out.println("OtherDetails_data"+OtherDetails_data);
	   	 
	   //* otherdetail structure 

	    	// category[0]-rollnumber[1]-studentname[2]-programname[3]-reftype[4]-semesterstartdate[5]-semesterenddate[6]
	    	//-latefee[7]-entityid[8]-programid[9]-pendingsemester[10]-feepending[11]-feetype[12]
	   	
	   	Category=OtherDetails_data[0];
	   	 student = new Student();
	   	if (Category.equalsIgnoreCase("CER"))
	   		student= otherdetailforcertificate(student, OtherDetails_data);
	   	
	   	if (Category.equalsIgnoreCase("CON")||Category.equalsIgnoreCase("newadm")|| Category.equalsIgnoreCase("appfee"))
	   		student= otherdetailforcontinue(student, OtherDetails_data);
	   	
	   	student.setATRN(ATRN);
	   	student.setMerchantorderno(MerchantOrderNumber);
	   	student.setCategory(Category);
	   	 
	   	 reftype =student.getReftype();
	   	 rollno = student.getRoll_number();
	   	 enrollno=student.getEnrolno();
	   	Timestamp timestamp = new Timestamp(0);
	   	payment =findPaymentByATRN(student.getATRN());
	  
	   	
	   	Date currentdate = new Date();
	   
//	   String strdate=	df.format(dt);
//	   Date currentdate =df.parse(strdate);
	  
	  
	   
	   
		if (payment==null) {
			
				payment = new Payment();
				payment.setCreatedby(method);

				 
				
		    	 payment.setInsert_time(currentdate);

				
			
		}else {
			
			payment.setModifiedby(method);
			//timestamp = new Timestamp(System.currentTimeMillis());;
	    	 payment.setModification_time(currentdate);
		}
			
	   	 
	   	 
	   	 //***************************
	   	  payment.setCategory(Category);
	   	  payment.setMerchantorderno(MerchantOrderNumber);
    	  payment.setATRN(ATRN);
    	  payment.setTransaction_status(Transaction_Status);
    	  
    	  if(isNumeric(Amount)) {
    		  trxamt =Double.parseDouble(Amount); 
    		  payment.setAmount(BigDecimal.valueOf(trxamt));
    	  }else {
    		  trxamt=0.0;
    	  }
    	    
    	  student.setAmount(trxamt.floatValue());
    	  payment.setCurrency(Currency);
    	  payment.setPayment_mode(Pay_Mode);
    	  payment.setOtherdetail(Other_Details);
    	  payment.setReason(Reason);
    	  payment.setBank_code(BankCode);
    	  payment.setBank_Reference_Number(Bank_Reference_Number);
    	  
    	  
    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	  
    	 
    		   
    		    Date parsedDate = formatter.parse(Transaction_Date);
    		     timestamp =new java.sql.Timestamp(parsedDate.getTime());    
    		    
    		   
    		    payment.setTransaction_date(timestamp);
    		    
    	    	
    	    	  payment.setCountry(Country);
    	  payment.setCIN(CIN);
    	  payment.setMerchant_ID(Merchant_ID);
    	  payment.setDv_status(dvstatus);
    	  payment.setStatusdescription(statusdecription);
    	  if (student.getReftype().contentEquals("E")) {
    		  payment.setRefnumber(enrollno);
    	  }else {
    		  payment.setRefnumber(rollno);
    	  }
    	  
    	  payment.setType(reftype);
    	  
    	  
    	  if(isNumeric(Total_Fee_GST)) {
    		  gstamt =Double.parseDouble(Total_Fee_GST); 
    		  payment.setTotal_Fee_GST(BigDecimal.valueOf(gstamt)); 
    	  }else {
    		  
    	  }
    		  
    	 
     		  thepaymentrepository.save(payment);
     		  student.setPayment(payment);
     		  

    		  
    		  // if transaction is not successful make an entry into pending payments
    		  // So that later on double verification can update Payment status based on pending payments 
    		  if (!(dvstatus.equalsIgnoreCase("Success"))) {
    			  
    			  PendingPayment pendingPayment = new PendingPayment();
    			  pendingPayment.setATRN(ATRN);
    			  pendingPayment.setAmount(BigDecimal.valueOf(trxamt));
    			  pendingPayment.setMerchant_Order_Number(MerchantOrderNumber);
    			  pendingPayment.setTrxstatus(dvstatus);
    			  
    			   // currentdate =  new Date();
    			     //  timestamp = new Timestamp(currentdate.getTime());

    			  
    			  pendingPayment.setInsert_time(currentdate);
    			  pendingPayment.setCreated_by("SBIService");
    		   		  
	    		  pendingPayment.setPayment(payment);
	    		  thePendingPaymentRepository.save(pendingPayment);
    		  
    		  
    		  }else {
    			  
    		//  update defaulter status 
    			  if (student.getDefaulter().equalsIgnoreCase("Y")) {
    				  
    				 Defaulters def = theDefaulterRepository.findByRollnumberAndSemestercode(student.getRoll_number(), 
    						 student.getSemestercode() );
    				 
    				 if (def!=null) {
    					 def.setDefaulter(0);
    					 def.setModifiedby(method);
    					 def.setModifiedtime(new Date());
    					 def.setFeestatus("rec");
    					 theDefaulterRepository.save(def);
    					 
    				 }
    			  }
    			  
    		  }
	
    		  
    	  }catch(DataException e) {
    		  logerror(ATRN, MerchantOrderNumber, String.valueOf(trxamt) , e.getMessage(),"savePayment");
    			insertstatus="error";
    		  
    	  }
	   	 
	   	 
	   	 catch(Exception e) { //this generic but you can control another types of exception
  		    // look the origin of excption 
  			
  			
  			logerror(ATRN, MerchantOrderNumber, String.valueOf(trxamt) , e.getMessage(),"savePayment");
  			insertstatus="error";
  			
  		}
    	  
    	 student.setMessage(insertstatus);  
	   	 return student;
	}
	
	
	@Override
	public boolean isNumeric(String strNum) {
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







	@Override
	public Payment findPaymentByATRN(String ATRN) {
		return thepaymentrepository.findByATRN(ATRN);
		
	}

	@Override
	public Payment findPaymentByMerchantorderno(String merchantorder) {
		
			return thepaymentrepository.findByMerchantorderno(merchantorder);
		
	}

	@Override
	public  void updatePaymentstatus(Payment payment) {
		
			 thepaymentrepository.save(payment);
		
	}


	  //** DV response structure 
//	  Merchant ID|SBIePayRefID/ATRN|Transaction Status|Country|
//  Currency|Other Details|MerchantOrderNumber|Amount|Status Description|
//	  BankCode|Bank Reference Number|Transaction Date|Pay Mode|CIN|
//	  Merchant ID|Total Fee GST|Ref1|Ref2|Ref3|Ref4|Ref5|Ref6|Ref7|Ref8|Ref9| Ref10

//* otherdetail structure for Student FEE

// category[0]-rollnumber[1]-studentname[2]-programname[3]-reftype[4]-semesterstartdate[5]-semesterenddate[6]
//-latefee[7]-entityid[8]-programid[9]-pendingsemester[10]-feepending[11]


  @Override
  public void logerror(String ATRN,String MerchantOrderNumber ,String trxamt,String message,String method) {
	  
	  Epayerrorlog  error =null;
	  error= theEpayExceptionRepository.findByMerchantOrderNumber(MerchantOrderNumber);
	    if (error == null)
	    	  error = new Epayerrorlog();
	    error.setATRN(ATRN);
		error.setMerchantOrderNumber(MerchantOrderNumber);
		error.setCreated_by(method);
		Date now = new Date();
		  			
		error.setInsert_time(now);
		error.setAmount(trxamt);
		error.setError(message);
		
		
		
		theEpayExceptionRepository.save(error);
  }


  @Override
   public Student ParseDVResponse(String[] data) {
	   
	  
		  
	  
	  Student student = new Student();	
		
			
		
		String amt="";
		
		String ATRN="";
		String merchantorder="";
		String category="";
		
		Float amount=0.0f;
		try {
			
			
		//*********************** extract data from dv data**************************
		amt=data[7];
		ATRN=data[1];
		if (isNumeric(amt)) {
			amount = Float.parseFloat(amt);

		}
		
		merchantorder=data[6];
		String OtherDetails = data[5];
		student.setATRN(ATRN);
		student.setMerchantorderno(merchantorder);
		student.setAmount(amount);
		
		//****************************************************************************
		
		// extract student details from  other details of dv data
		
		
		String resdata[]= OtherDetails.split("\\,");
		category=resdata[0];
		student.setCategory(category);
		
		if (
				student.getCategory().equalsIgnoreCase("CON")||
				student.getCategory().equalsIgnoreCase("newadm")||
				student.getCategory().equalsIgnoreCase("appfee")
				) {
			
			student=	otherdetailforcontinue(student, resdata);
		}
		
		if (
				student.getCategory().equalsIgnoreCase("CER")
				
				) {
			
			student=	otherdetailforcertificate(student, resdata);
		}
		
		
		}
		
		catch(Exception e) {
			student.setStatus("error");
			
			student.setMessage(e.getMessage());
			System.out.println(e.getMessage());
			
		}
   
  
        return student;  



}



  /***************************** otherdetail structure  for Certificates  category  ***************************

	// category[0]-rollno[1]-enrolno[2]-studentname[3]-programname[4]-reftype[5]-address[6]-pincode[7]
	//phone[8]-mode[9]-programid[10]-semester[11]-certificateType[12]
	 * 
	 *
	 *  this.category 
+this.coma+this.rollnumber
+this.coma+this.enrolno
+this.coma+this.studentname
+this.coma+this.programname
+this.coma+this.rectype
+this.coma+this.address
+this.coma+this.pincode 
+this.coma+this.phone
+this.coma+this.mode
+this.coma+this.programid
+this.coma+this.semester
+this.coma+this.certificatetype
/

	/********************************************************************************************************/ 



  private Student otherdetailforcertificate(Student student, String[] resdata) {
	
	 
		String rollnumber ="";
        String enrolno ="";
		String studentname="";
		String programname ="";
		String rectype = "";
		String address="";
		String pincode="";
		
		String phone="";
		String mode="";
		String programid="";
		String semester ="";
		String certificatetype ="";
		

		try {
			
				
				rollnumber = resdata[1];
				enrolno = resdata[2];
				studentname=resdata[3];
				programname=resdata[4];
				rectype=resdata[5];
				address=resdata[6];
				pincode=resdata[7];
				phone=resdata[8];
				mode=resdata[9];
				programid=resdata[10];
				semester=resdata[11];
				certificatetype=resdata[12];
				
//				Payment payment =findPaymentByATRN(student.getATRN());
//				if (payment==null)
//					payment = findPaymentByMerchantorderno(student.getMerchantorderno());
//				if (payment==null) {
//						
//					throw new Exception("Payment record not found");
//				}
					
					
					 
				
			 	student.setRoll_number(rollnumber);
			 	student.setEnrolno(enrolno);
			 	student.setStudentname(studentname);
			 	student.setProgramname(programname);
			 	student.setReftype(rectype);
			 	student.setAddress(address);
			 	student.setPincode(pincode);
			 	student.setPhone(phone);
			 	student.setMode(mode);
			 	student.setProgramid(programid);
			 	student.setSemestercode(semester);
			 	student.setCertificatetype(certificatetype);
			 	
			 	//student.setPayment(payment);
			 	
			 		        
		        
		        student.setStatus("success");
		        
		      
			
			
		}
		
		catch(Exception e) {
			student.setStatus("error");
			
			student.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		  return student;
		

	}

	  








/***************************** otherdetail structure  for continue and new admission fee category con,new adm***************************

	// category[0]-rollnumber[1]-studentname[2]-programname[3]-reftype[4]-semesterstartdate[5]-semesterenddate[6]
	//-latefee[7]-entityid[8]-programid[9]-pendingsemester[10]-feepending[11]-feetype[12]
	 * 
	 * 
	 * this.category 
       +this.coma+this.rollnumber
       +this.coma+this.studentname
       +this.coma+this.programname
       +this.coma+this.rectype
       +this.coma+this.semesterstartdate
       +this.coma+this.semesterenddate 
       +this.coma+this.latefee
       +this.coma+this.entityid
       +this.coma+this.programid
       +this.coma+this.semester
       +this.coma+this.feepending
       +this.coma+this.feetype);
	 */

	/********************************************************************************************************/ 
	  
	 

private Student otherdetailforcontinue(Student student, String[] resdata) throws Exception {
	
	String rollnumber ="";
	String studentname="";
	String programname="";
	String reftype ="";
	Date semstartdate=null;
	Date semenddate=null;
	String latefee="";
	String entityid ="";
	String Programid = "";
	String semester="";
	String feepending="";
	String feetype="";
	
	String ssd="";
	String sed="";
	String defaulter="";
	
	
	
	Float latefeeamt=0.0f;
	

	try {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			rollnumber = resdata[1];
			studentname=resdata[2];
			programname=resdata[3];
			reftype = resdata[4];
			String date1 = resdata[5];
			String date2 = resdata[6];
			
			if (isValidDate(resdata[5]))
			  semstartdate=dateFormat.parse(date1);
		
			
			if (isValidDate(resdata[6]))
				  semenddate=dateFormat.parse(date2);
			 
			 latefee=resdata[7];
			
			entityid=resdata[8];
			Programid=resdata[9];
			semester=resdata[10];
			feepending=resdata[11];
			
			feetype=resdata[12];
			defaulter =resdata[13];
			
			
				
			
				 if ( isNumeric(latefee)) {
				 	 latefeeamt=      Float.parseFloat(latefee);
					 
			 }
			
		 	student.setApplicationnumber(rollnumber);
		 	student.setRoll_number(rollnumber);
		 	student.setStudentname(studentname);
		 	student.setProgramname(programname);
		 	student.setReftype(reftype);
		 	student.setSemesterstartdate(semstartdate);
		 	student.setSemesterenddate(semenddate);
		 	student.setLatefee(latefeeamt);
		 	student.setEntityid(entityid);
		 	
	        student.setProgramid(Programid);
	        student.setSemestercode(semester);
	        student.setFeepending(feepending);
	        student.setFeetype(feetype);
	        student.setDefaulter(defaulter);
	       
	        
	        
	    
	        student.setStatus("success");
	        
	      
		
		
	} catch (ParseException e) {
		student.setStatus("error");
		
		student.setMessage(e.getMessage());
		e.printStackTrace();
	}
	
	catch(Exception e) {
		student.setStatus("error");
		
		student.setMessage(e.getMessage());
		e.printStackTrace();
	}
	
	  return student;
	

}

public static boolean isValidDate(String inDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    try {
        dateFormat.parse(inDate.trim());
    } catch (ParseException pe) {
        return false;
    }
    return true;
}
  
//@Scheduled(fixedRateString = "${run-frquency.minutes}" ,timeUnit = TimeUnit.MINUTES)
public void processpendingpayment() {
	
	String status = "success";
	Payment payment=null;
	String dvdata ="";
	String [] dvdata_ary;
	List<PendingPayment> pendingpayment = new ArrayList<>();
	String data[] = new String[5];
	System.out.println("in side process pending payment method ");
	
	pendingpayment =thePendingPaymentRepository.findAllByTrxstatusNot(status);
	if (pendingpayment.size()>0) {
		
		for (PendingPayment pending:pendingpayment) {
			data[0]=pending.getMerchant_Order_Number();
			data[1]=pending.getATRN();
			data[3]=pending.getAmount().toString();
			dvdata =verifyPayment(data);
			 dvdata_ary= dvdata.split("\\|");
			 String trxstatus = dvdata_ary[2];
		      String statusdesc=dvdata_ary[8];
		      pending.setTrxstatus(trxstatus);
		      pending.setModification_time(new Date());
		      pending.setProcess_time(null);
		      
		      
		      thePendingPaymentRepository.save(pending);
		      payment =thepaymentrepository.findByMerchantorderno(pending.getMerchant_Order_Number());
		      payment.setTransaction_status(trxstatus);
		      payment.setModifiedby("processpendingpayment");
		      payment.setModification_time(new Date());
		      thepaymentrepository.save(payment);
		      
		      
			
			
		}
		
	}
}

public String  Aesdecrypt(String cipherText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
	String secret = aeskey;
			//"René Über";
//	String cipherText =
//			"U2FsdGVkX19YLvmB4y3dU+VFtz5hSm4hYpLXtXLW1bt0pdFSbbLdKx5m8W19StZxoTunHFdJ9eOpg0MwZWmYP8JdytxMrDGp2WzRen3eb1ez2898NtYWBQTEIUqz8fuQG8q9YLsIFUgxvCfbGaaHcj5f58C4u/Y+blBNfEQD2fI=";
			//"U2FsdGVkX1+tsmZvCEFa/iGeSA0K7gvgs9KXeZKwbCDNCs2zPo+BXjvKYLrJutMK+hxTwl/hyaQLOaD7LLIRo2I5fyeRMPnroo6k8N9uwKk=";

	
	cipherText = cipherText.replaceAll(" ", "+");
			
	byte[] cipherData = Base64.getDecoder().decode(cipherText);
	byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

	MessageDigest md5 = MessageDigest.getInstance("MD5");
	final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
	SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
	IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

	byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
	Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
	aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
	byte[] decryptedData = aesCBC.doFinal(encrypted);
	String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

	System.out.println(decryptedText);
	return decryptedText;
	 
 }
/**
 * Generates a key and an initialization vector (IV) with the given salt and password.
 * <p>
 * This method is equivalent to OpenSSL's EVP_BytesToKey function
 * (see https://github.com/openssl/openssl/blob/master/crypto/evp/evp_key.c).
 * By default, OpenSSL uses a single iteration, MD5 as the algorithm and UTF-8 encoded password data.
 * </p>
 * @param keyLength the length of the generated key (in bytes)
 * @param ivLength the length of the generated IV (in bytes)
 * @param iterations the number of digestion rounds 
 * @param salt the salt data (8 bytes of data or <code>null</code>)
 * @param password the password data (optional)
 * @param md the message digest algorithm to use
 * @return an two-element array with the generated key and IV
 */
public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

    int digestLength = md.getDigestLength();
    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
    byte[] generatedData = new byte[requiredLength];
    int generatedLength = 0;

    try {
        md.reset();

        // Repeat process until sufficient data has been generated
        while (generatedLength < keyLength + ivLength) {

            // Digest data (last digest if available, password data, salt if available)
            if (generatedLength > 0)
                md.update(generatedData, generatedLength - digestLength, digestLength);
            md.update(password);
            if (salt != null)
                md.update(salt, 0, 8);
            md.digest(generatedData, generatedLength, digestLength);

            // additional rounds
            for (int i = 1; i < iterations; i++) {
                md.update(generatedData, generatedLength, digestLength);
                md.digest(generatedData, generatedLength, digestLength);
            }

            generatedLength += digestLength;
        }

        // Copy key and IV into separate byte arrays
        byte[][] result = new byte[2][];
        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
        if (ivLength > 0)
            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

        return result;

    } catch (DigestException e) {
        throw new RuntimeException(e);

    } finally {
        // Clean out temporary data
        Arrays.fill(generatedData, (byte)0);
    }
}



}





