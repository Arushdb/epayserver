<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Form</title>

<!--  
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
    
    .myheader {
    
     margin-left: 5px;
     height:100px;
     background-color: #00FFFF;
         
    }
    img {
    margin:40px 
    }
     
    button {
        padding: 10px;
        margin: 10px;
    }
    
    .grid-container {
  display: grid;
  grid-template-columns: auto auto ;
  height:150px;
  background-color: #00FFFF;
  padding: 1px;
}

.grid-container > div {
 
  text-align: center;
  padding: 5px 0;
  font-size: 15px;
}

.item1 {
  grid-row: 1 / span 2;
}
</style>
-->
<link href="bootstrap-grid.css" rel="stylesheet" type="text/css">

</head>
<body>
<%@ include file = "header.html" %>
    <div >
        <h2>Make Payment</h2>
        <form:form action="https://test.sbiepay.sbi/secure/AggregatorHostedListener" method="post" modelAttribute="student">
             
            <form:input type="hidden"  path="EncryptTrans"/><br/>
          
          	<form:input type="hidden"  path="merchantid"/><br/>
         
            <form:button>Confirm</form:button>
        </form:form>
    </div>
</body>
</html>