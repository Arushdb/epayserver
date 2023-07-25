package com.dayalbagh.epay;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;


public class Double_verification {
	
//	public static void main(String[] args) {
//		Double_verification d = new  Double_verification();
//		// d.serverVerification();
//		urlConnection("https://www.sbiepay.sbi/payagg/statusQuery/getStatusQuery", "|1000112|72a1a7f7-5e31-406d-b431-427627477c02|10", "1000112");
//		}

		public static String urlConnection(String gatewayUrl,String messageData,String mecode) {

		        String responseCode = "";
		        InputStream stream = null;
		        InputStreamReader isr = null;
		        BufferedReader reader = null;

		        try {

		               HashMap<String, String> params = new HashMap<String,String>();

		               String  merchant_code  = mecode ;

		               String encdata = messageData;

//		                   params.put("encdata", encdata);

		                   params.put("queryRequest", messageData);
		        params.put("aggregatorId", "SBIEPAY");
		        params.put("merchantId", mecode);
		       
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

		                     OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

		                     writer.write(requestParams.toString());

		                     writer.flush();



		                     //Response Code

		                     int responseMsg = httpConn.getResponseCode();

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
		                     System.out.println(responseCode);
		               }

		        } catch (MalformedURLException e) {

//		               GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");

		        } catch (ProtocolException e) {

//		               GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");

		        } catch (IOException e) {

//		               GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");

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

//		                     GenericExceptionLog.exceptionJava(e, "Exception Occured in :: urlConnection() for "+gatewayUrl, "AggGatewayURLConnection");

		               }

		        }



		        return responseCode;
	

}
}
