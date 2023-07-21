<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   
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
     
    button {
        padding: 10px;
        margin: 10px;
    }
</style>


</head>
<body>
    <div align="center">
        <h2>Make Payment</h2>
        <form:form action="https://test.sbiepay.sbi/secure/AggregatorHostedListener" method="post" modelAttribute="student">
           
            <form:input type="hidden"  path="EncryptTrans"/><br/>
         
           <input type="hidden" name="merchIdVal" value ="1000112"/>
             
           
             
            
                 
            <form:button>Confirm</form:button>
        </form:form>
    </div>
</body>
</html>