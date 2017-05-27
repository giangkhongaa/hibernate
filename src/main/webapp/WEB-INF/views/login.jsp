<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css"><%@ include file="/WEB-INF/css/stylelogin.css"%> </style>
<style type="text/css"><%@ include file="/WEB-INF/css/style.css"%> </style>
<title>Login</title>
</head>
<body>
	<c:choose>
		<c:when test="${param.error == 'true'}">
			<p class="message">${message}</p>
		</c:when>
		<c:otherwise>
			<p class="message message--success">${message}</p>
		</c:otherwise>
	</c:choose>
	<div class="login-block">
		<h1>Login</h1>
		<form method="post" name="f"
			action="${pageContext.request.contextPath}/j_spring_security_check">
			<label for="username">Username</label> <input id="username"
				class="login-block--input" name="username" placeholder="Username" />
			<label for="password">Password</label> <input id="password"
				class="login-block--input" type="password" name="password"
				placeholder="Password" /> <input class="login-block--button"
				type="submit" value="login">
		</form>
	</div>

</body>
</html>