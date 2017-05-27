<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css"><%@ include file="/WEB-INF/css/stylelogin.css"%> </style>
<style type="text/css"><%@ include file="/WEB-INF/css/style.css"%> </style>
<title>Login</title>
</head>
<body>
	<div class="login-block">
		<h1>Login</h1>
		<spring:form method="post" commandName="user" action="${pageContext.request.contextPath}/student/login">
			<spring:input class="login-block--input" path="username" placeholder="Username" />
			<br/><spring:errors path="username" cssClass="error"/>
			<spring:input class="login-block--input" type="password" path="password" placeholder="Password" />
			<br/><spring:errors path="password" cssClass="error"/>
			<input class="login-block--button" type="submit" value="login">
		</spring:form>
	</div>
	<p>${message}</p>
</body>
</html>