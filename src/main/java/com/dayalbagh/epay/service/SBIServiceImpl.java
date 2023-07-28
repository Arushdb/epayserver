package com.dayalbagh.epay.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.dayalbagh.epay.AES256Bit;
import com.dayalbagh.epay.model.Student;



@Service
public class SBIServiceImpl implements SBIService {
	String key_Array = "A7C9F96EEE0602A61F184F4F1B92F0566B9E61D98059729EAD3229F882E81C3A";
	static String gatewayUrl = "https://www.test.sbiepay.sbi/payagg/statusQuery/getStatusQuery" ;
	static String Merchant_ID =  "1000112";
	
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
			        params.put("merchantId", Merchant_ID);
       
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
		
		
		String queryRequest = ATRN+"|"+Merchant_ID+"|"+Merchant_Order_Number+"|"+Amount;
		
		String status =DoubleVerification(queryRequest);
		
		return status;
	}
}


