
<%@page import="org.apache.catalina.Session"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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

h2 {
	background-color: fuchsia;
}
</style>


</head>
<body onload="closewindow()">

			function closewindow() {

			setTimeout(function() {
				window.close();

			}, 5000);

		}
	</script>


	<div align="center">
		<h2>There is some issue while processing your payment.Please send email to edrp.helpdesk@gmail.com and contact EdRP office of Dayalbagh Educational Institute</h2>


	</div>
</body>
</html>