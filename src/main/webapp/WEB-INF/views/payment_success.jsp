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

<
<body>
    <div align="center">
        <h2>Payment Succeeded!</h2>
        <span>Data:</span><span>${message}</span><br/>
         <span>Merchant ID:</span><span>${merchIdVal}</span><br/>
          <span>Bank Code:</span><span>${Bank_Code}</span><br/>
          
       
    </div>
</body>
</html>