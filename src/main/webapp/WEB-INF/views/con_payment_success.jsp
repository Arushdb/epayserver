
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" %> 
<%@ page import = "java.util.ResourceBundle" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Form</title>


<style type="text/css">
label {
	display: inline-block;
	width: 200px;
	margin: 5px;
	text-align: left;
}

input[type=text], input[type=password], select {
	width: 200px;
}

input[type=radio] {
	display: inline-block;
	margin-left: 45px;
}

input[type=checkbox] {
	display: inline-block;
	margin-right: 190px;
}

.mybutton {
	padding: 10px;
	margin: 10px;
	size: 20px;
	color: navy;
	
}

h2 {
	background-color: ;
}
</style>


</head>





<body onload="closewindow()">

<script>

  
  function closewindow() {
	  
	  window.history.go(1);
	  setTimeout(function() {
		 
		  window.close();
	  }, 60000);

  }
   
 

</script>


<%  session.setMaxInactiveInterval(60);


%>

<% ResourceBundle resource = ResourceBundle.getBundle("message");
  String testdownloadpdf=resource.getString("testdownloadpdf");
  String livedownloadpdf=resource.getString("livedownloadpdf");
  String downloadpdfpath;
  
 
  
  String path = request.getRequestURL().toString();
  if (path.contains("local"))
	  downloadpdfpath=testdownloadpdf;
  else
	  downloadpdfpath=livedownloadpdf;
  
  String trxstatus= request.getAttribute("trxstatus").toString();
 
  String paystatus="";
  if (trxstatus.equalsIgnoreCase("success")){
	  paystatus = "Successfully Completed";
	  
  }
		  
  
  if (trxstatus.equalsIgnoreCase("pending"))
	  paystatus = "pending.Please notedown all the below details  for future reference."+
	  " and check status after some time.";
  
  if (trxstatus.equalsIgnoreCase("fail"))
	  paystatus = "failed.Please make payment again";
   %>

  
<h2 id="demo"></h2>

	<div id="divid" align="center">
	
		<button id="printbutton"    style="color: blue;size: 100px;width: 100px;height: 50px;font-size: 20px" onclick="window.print();window.history.back(); ">Print</button>
		<h2>
			Your Payment is <span><%=paystatus %></span> 
		</h2>
		
		<!--  
        <a href="http://localhost:8080/epay/download">DownLoad PDF </a>
        -->
		<form:form id="myform"  action="<%= downloadpdfpath %>" 
			method="post" modelAttribute="student">
			 
			<form:input type="hidden" path="semesterstartdate"  />
			
			<form:input type ="hidden"  path="semesterenddate"  />
			<form:input type ="hidden"  path="message"  />
			<form:input type ="hidden"  path="category"  />
			<form:input type ="hidden"  path="transactiondate"  />
			<form:input type ="hidden"  path="programname" />
			<form:input type ="hidden"  path="semestercode" />
			<form:input type ="hidden"  path="enrolno" />
		
			<form:input type ="hidden"  path="bankReferenceNumber"  />
			<form:input type ="hidden"  path="certificatetype"  />
			

			<form:button id="btn" class="mybutton"  type="submit"   >Download</form:button>


			<table>
			
			
			<tr>
					<td>Your Payment status is  </td>
					<td><%=trxstatus %> </td>
				</tr>
				
					<tr>
					<td>SBIePay Reference </td>
					<td><form:input readonly="true" path="ATRN" /></td>
					</tr>
					
					<tr>
					<td>Merchant Order Number:</td>
					<td><form:input readonly="true" path="merchantorderno" /></td>
					</tr>
	
				<tr>
					<td>Name:</td>
					<td><form:input readonly="true" path="studentname" /></td>
				</tr>
				<tr>
					<td>Roll/Enrollment/Application Number</td>
					<td><form:input readonly="true" path="roll_number" /></td>
				</tr>
				
			
				
				<tr>
					<td>Amount:</td>
					<td><form:input   readonly="true" path="amount" /></td>
				</tr>



			</table>


		</form:form>

	</div>
	<script>
 
  var trxsts = "<%=trxstatus %>";
  trxsts =trxsts.toUpperCase();
 
  
  const button = document.getElementById("btn");
  const div = document.getElementById("divid");
  const printbutton = document.getElementById("printbutton");
  if (trxsts=="SUCCESS"){
	  button.disabled=false;
	  div.style.color="green"; 
	  printbutton.disabled=true;
	  printbutton.hidden=true;
  }
	   
  
  if (trxsts=="PENDING"){
	  button.disabled=true;
	  button.hidden=true;
	  div.style.color="orange";   
  }
  
  if (trxsts=="FAIL"){
	  button.disabled=true;
	  button.hidden=true;
	  div.style.color="red";   
  }
  
  button.addEventListener('click', () => {
	  console.log('Button clicked!');
	 // button.disabled=true;
	  button.hidden=true;
	  printbutton.disabled=false;
	  printbutton.hidden=false;
	  window.history.back(); 
	});
 
  
  </script>

	
	
</body>
</html>