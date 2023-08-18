<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Form</title>


<style type="text/css">

  .flex-container {
   background-color:aqua;
   height:auto;
   display: flex;
   margin: 10px;
   padding: 10px;
  }



  .flex-item:nth-of-type(1) {flex-grow: 1;}
  .flex-item:nth-of-type(2) {flex-grow: 6;
  text-align: center;
   margin: 10px;
   padding:10px;
}
    

  
  
    
</style>



</head>
<body onload="submitform()">

<!--  
<div class="flex-container">
<div class="flex-item"  >
  
  <span style="padding-right:3px; padding-top: 3px; display:inline-block;">

    <img width="90px" height=auto src="logo.png"></img>
    
    </span>


  </div>
<div  class="flex-item" >
  <h1>DEI EPAY System</h1>
   <h2>Dayalbagh Educational Institute</h2>
   <h2>Make Payment</h2>
    

  </div>
</div>
-->
    <div  style="text-align: center;">
        
        <form:form id="form1"  action="https://test.sbiepay.sbi/secure/AggregatorHostedListener" method="post" modelAttribute="student">
             
            <form:input type="hidden"  path="EncryptTrans"/><br/>
          
          	<form:input type="hidden"  path="merchIdVal"/><br/>
         <!--   
            <form:button type="hidden" style="width:300px ;height:50px;background-color:dodgerblue;font-size:40px " >Continue</form:button>
        -->
</form:form>
    </div>
    
    <script>
    
    function submitform(){
    	
    	form1.submit();
    	
    }
    </script>
</body>
</html>