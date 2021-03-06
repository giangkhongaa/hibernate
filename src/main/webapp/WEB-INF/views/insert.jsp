<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@page import="com.giang.service.StudentService"%>
<%@page import="com.giang.model.Student"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css"><%@ include file="/WEB-INF/css/style.css"%> </style>
<style type="text/css"><%@ include file="/WEB-INF/css/stylecalendar.css"%> </style>

<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.js">
	
</script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.js">
	
</script>
<script src="${pageContext.request.contextPath}/js/CheckValidate.js">
	
</script>
<title>Insert Student</title>
<script type="text/javascript">
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
</head>
<body>
	<h1>THÊM SINH VIÊN MỚI</h1>

	<div class="insert-block">
		<spring:form method="post" modelAttribute="student"
			action="${pageContext.request.contextPath}/student/insert">
			<label for="studentName">Name</label>
			<spring:input id="studentName" class="insert-block--input"
				path="studentName" placeholder="Name" maxlength="30" />
			<br />
			<spring:errors path="studentName" cssClass="error" />
			<label for="studentCode">Code</label>
			<spring:input id="studentCode" class="insert-block--input"
				path="studentCode" placeholder="Code" maxlength="10" />
			<br />
			<spring:errors path="studentCode" cssClass="error" />
			<label for="studentAddress">Address</label>
			<spring:input id="studentAddress" class="insert-block--input"
				path="address" placeholder="Address" maxlength="200" />
			<br />
			<spring:errors path="address" cssClass="error" />
			<label for="score">Score</label>
			<spring:input id="score" class="insert-block--input"
				path="averageScore" placeholder="Score" maxlength="5" />
			<br />
			<spring:errors path="averageScore" cssClass="error" />
			<label>Birth</label>
			<c:choose>
				<c:when test="${student.getDayOfBirth() != null }">
					<fmt:formatDate pattern="MM/dd/yyyy" var="dateFormat"
						value="${student.getDayOfBirth()}" />
				</c:when>
				<c:otherwise>
					<c:set var="dateFormat" value="" />
				</c:otherwise>
			</c:choose>
			<spring:input class="insert-block--input" path="dayOfBirth"
				placeholder="Date of birth" id="datepicker" readonly="true"
				value="${dateFormat}" />
			<br />
			<spring:errors path="dayOfBirth" cssClass="error" />

			<input class="insert-block--radio" id="radioNam" name="sex"
				type="radio" value="Nam" checked="checked" />
			<label for="radioNam">Nam</label>
			<input class="insert-block--radio" id="radioNu" name="sex"
				type="radio" value="Nữ" />
			<label for="radioNu">Nữ</label>
			<label class="label-class">Class</label>
			<div class="dropdown">
				<spring:select id="class" class="dropdown-select"
					path="classStudent.classId">
					<spring:options items="${classList}" itemValue="classId"
						itemLabel="className" />
				</spring:select>
			</div>
			<div class="insert-block--button">
				<a href="${pageContext.request.contextPath}/student/home">Come
					Back</a>
			</div>
			<input class="insert-block--button" type="submit" value="Insert">
		</spring:form>
	</div>

</body>
</html>