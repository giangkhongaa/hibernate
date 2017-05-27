<%@page import="java.util.ArrayList"%>
<%@page import="com.giang.model.Class"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.giang.model.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="com.giang.paging.Page"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css"><%@ include file="/WEB-INF/css/style.css"%></style>
<style type="text/css"><%@ include file="/WEB-INF/css/style.css"%> </style>
<style type="text/css"><%@ include file="/WEB-INF/css/stylecalendar.css"%> </style>
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.js">
	
</script>

<script src="${pageContext.request.contextPath}/js/CheckValidate.js">
	
</script>

<script src="${pageContext.request.contextPath}/js/jquery-ui.js">
	
</script>
<script src="${pageContext.request.contextPath}/js/moment.js">
	
</script>
<script src="${pageContext.request.contextPath}/js/search.js">
	
</script>
<title>Book Students</title>
<script type="text/javascript">
	$(function() {
		$("#datepickerStart").datepicker();
		$("#datepickerEnd").datepicker();
	});
</script>

<title>Home</title>
</head>
<body>
	<h1>DANH SÁCH SINH VIÊN</h1>
	<security:authorize access="hasRole('ROLE_ADMIN')">
		<spring:form method="get" commandName="student"
			action="${pageContext.request.contextPath}/student/insert">
			<input class="add-block--button" type="submit"
				value="Add new student">
		</spring:form>
	</security:authorize>
	<%
		// case load first
		Student studentSession = (Student) session.getAttribute("keySearch");
		if (studentSession == null) {
			studentSession = new Student();
		}
		// get current date
		Date startDate = null;
		Date endDate = null;
		List<Date> dateList = (List<Date>) session.getAttribute("keySearchDate");
		// case load first
		if (dateList == null) {
			dateList = new ArrayList<Date>();
		}
		if (dateList.size() != 0) {
			startDate = dateList.get(0);
			endDate = dateList.get(1);
		}
		String message= request.getParameter("message");
	%>
	<spring:form method="post"
		action="${pageContext.request.contextPath}/student/search">
		<label class="labelName"> Name:</label>
		<input id="studentName" class="search-block--input" name="studentName"
			placeholder="Search Name" maxlength="30"
			value='<%=studentSession.getStudentName()%>' />
		<label class="labelSex"> Sex:</label>
		<INPUT class="search-block-radio-nam" TYPE="radio" NAME="sexStudent"
			VALUE="No"
			<c:if test='<%=!studentSession.getSex().equals("Nam") || !studentSession.getSex().equals("Nữ")%>'>CHECKED</c:if>>
		<p class="search-block-label-nam">Both</p>
		<INPUT class="search-block-radio-nu" TYPE="radio" NAME="sexStudent"
			VALUE="Nam"
			<c:if test='<%=studentSession.getSex().equals("Nam")%>'>CHECKED</c:if>>
		<p class="search-block-label-nu">Nam</p>
		<INPUT class="search-block-radio-none" TYPE="radio" NAME="sexStudent"
			VALUE="Nữ"
			<c:if test='<%=studentSession.getSex().equals("Nữ")%>'>CHECKED</c:if>>
		<p class="search-block-label-none">Nữ</p>
		<label class="labelScore"> Score:</label>
		<input id="averageScore" class="search-block--input--score"
			name="averageScore" placeholder="Score"
			value='<%=studentSession.getAverageScore()%>'
			onchange="checkScoreSearch()" />
		<c:choose>
			<c:when test='<%=startDate != null && endDate != null%>'>
				<fmt:formatDate pattern="MM/dd/yyyy" var="startDateFormat"
					value='<%=startDate%>' />
				<fmt:formatDate pattern="MM/dd/yyyy" var="endDateFormat"
					value='<%=endDate%>' />
			</c:when>
			<c:otherwise>
				<c:set var="startDateFormat" value="" />
				<c:set var="endDateFormat" value="" />
			</c:otherwise>
		</c:choose>
		<label class="labelDate"> Birth:</label>
		<label class="labelDateFrom"> From:</label>
		<input class="search-block--input-datestart" type="text"
			name="startDate" id="datepickerStart" placeholder="MM/dd/yyyy"
			value="${startDateFormat }"
			onchange="checkDateSearch('datepickerStart')">
		<br>
		<label class="labelDateTo"> To:</label>
		<input class="search-block--input-dateend" type="text" name="endDate"
			id="datepickerEnd" placeholder="MM/dd/yyyy" value="${endDateFormat }"
			onchange="checkDateSearch('datepickerEnd')">
		<br>
		<p class="dropdown-label">Class:</p>
		<div class="container-dropdown">
			<div class="dropdown">
				<select class="dropdown-select" name="classId">
					<option value="0">----Chọn lớp----</option>
					<c:forEach var="classStudentRQ" items="${classList}">
						<option value="${classStudentRQ.getClassId()}"
							${classStudentRQ.getClassId() == keySearch.getClassStudent().getClassId() ? 'selected="selected"' : ''}>${classStudentRQ.getClassName()}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<INPUT class="search-block--radio" TYPE="submit" VALUE="Search">
	</spring:form>
	<security:authorize access="hasRole('ROLE_ADMIN')">
		<form
			action="${pageContext.request.contextPath}/student/accountsearch"
			method="get">
			<input class="search-block--button-manager" type="submit"
				value="Manager">
		</form>
	</security:authorize>
	<a class="logout" href="<c:url value='/student/logout'></c:url>">Log
		out</a>

	<script type="text/javascript">
		function submitForm() {
			document.getElementById('mainForm').submit();
		}
	</script>
	<spring:form method="get" commandName="student"
		action="${pageContext.request.contextPath}/student/removeSession">
		<input class="cancel-block--button" type="submit" value="Cancel">
	</spring:form>
	<div class="divTable">
		<div class="divTableBody">
			<div class="divTableRow">
				<div class="divTableTitle">STT</div>
				<div class="divTableTitle">User name</div>
				<div class="divTableTitle">Code</div>
				<div class="divTableTitle">Sex</div>
				<div class="divTableTitle">Address</div>
				<div class="divTableTitle">Score</div>
				<div class="divTableTitle">Date of birth</div>
				<div class="divTableTitle">Class</div>
				<div class="divTableTitle">Grade</div>
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<div class="divTableTitle">Function</div>
					<div class="divTableTitle">Function</div>
				</security:authorize>
			</div>
			<c:forEach items="${studentList}" var="student" varStatus="status">
				<div class="divTableRow">
					<div class="divTableCell">${page.getCurrentPosition() *10 + status.count - 10}</div>
					<div class="divTableCell text-left min-width1"><c:out value="${student.getStudentName()}" escapeXml="true"/></div>
					<div class="divTableCell"><c:out value="${student.getStudentCode()}" escapeXml="true"/></div>
					<div class="divTableCell">${student.getSex()}</div>
					<div class="divTableCell text-left min-width2 scroll"><c:out value="${student.getAddress()}" escapeXml="true"/></div>
					<div class="divTableCell"><c:out value="${student.getAverageScore()}" escapeXml="true"/></div>
					<div class="divTableCell">
						<fmt:formatDate pattern="MM/dd/yyyy"
							value="${student.getDayOfBirth()}" />
					</div>
					<div class="divTableCell text-left">${student.getClassName()}</div>
					<div class="divTableCell text-left">${student.getGradeName()}</div>
					<security:authorize access="hasRole('ROLE_ADMIN')">
						<div class="divTableCell">
							<form method="get"
								action="${pageContext.request.contextPath}/student/delete/${student.getStudentId()}"
								onsubmit="return confirm('Are you sure?') ? true : false;">
								<input type="submit" value="Delete">
							</form>
						</div>
						<div class="divTableCell">
							<form method="get"
								action="${pageContext.request.contextPath}/student/update/${student.getStudentId()}">
								<input type="submit" value="Update">
							</form>
						</div>
					</security:authorize>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="container-page">
		<c:choose>
			<c:when
				test='<%=(studentSession.getStudentName() == "" && studentSession.getSex() == "No"
							&& studentSession.getAverageScore() == 0 && dateList.size() == 0)%>'>
				<%-- begin v1 --%>
				<c:choose>
					<c:when
						test="${page.getCurrentPosition() <= 1 ||  page.getCurrentPosition() == 0}">
						<div class="container-page-record display--none">
							<a class="delete-link"
								href="<c:url value='/student/page/${page.getFirstPage() }'></c:url>">
								<< </a>
						</div>
						<div class="container-page-record display--none">
							<a
								href="<c:url value='/student/page/${page.getPrevPage() }'></c:url>">
								< </a>
						</div>
						<div class="container-page-record-none"></div>
						<div class="container-page-record-none"></div>
					</c:when>
					<c:otherwise>
						<div class="container-page-record">
							<a
								href="<c:url value='/student/page/${page.getFirstPage() }'></c:url>">
								<< </a>
						</div>
						<div class="container-page-record">
							<a
								href="<c:url value='/student/page/${page.getPrevPage() }'></c:url>">
								< </a>
						</div>
					</c:otherwise>
				</c:choose>
				<c:forEach var="page_id" items="${page.getNumberPageArray()}">
					<c:choose>
						<c:when test="${page_id == page.getCurrentPosition()}">
							<div class="container-page-record container-page-record--current">
								<a href="<c:url value='/student/page/${page_id}'></c:url>">
									<c:out value="${page_id}" />
								</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="container-page-record">
								<a href="<c:url value='/student/page/${page_id}'></c:url>">
									<c:out value="${page_id}" />
								</a>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<%-- end v1 --%>
				<c:choose>
					<c:when test="${ page.getCurrentPosition() >= page.getSize()}">
						<div class="container-page-record display--none">

							<a
								href="<c:url value='/student/page/${page.getNextPage() }'></c:url>">
								> </a>
						</div>
						<div class="container-page-record display--none">
							<a
								href="<c:url value='/student/page/${page.getEndPage() }'></c:url>">
								>> </a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="container-page-record ">

							<a
								href="<c:url value='/student/page/${page.getNextPage() }'></c:url>">
								> </a>
						</div>
						<div class="container-page-record ">
							<a
								href="<c:url value='/student/page/${page.getEndPage() }'></c:url>">
								>> </a>
						</div>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<%-- begin v1 --%>
				<c:choose>
					<c:when
						test="${page.getCurrentPosition() <= 1 ||  page.getCurrentPosition() == 0 }">
						<div class="container-page-record display--none">
							<a
								href="<c:url value='/student/search/${page.getFirstPage() }'></c:url>">
								<< </a>
						</div>
						<div class="container-page-record display--none">
							<a
								href="<c:url value='/student/search/${page.getPrevPage() }'></c:url>">
								< </a>
						</div>
						<div class="container-page-record-none"></div>
						<div class="container-page-record-none"></div>
					</c:when>
					<c:otherwise>
						<div class="container-page-record">
							<a
								href="<c:url value='/student/search/${page.getFirstPage() }'></c:url>">
								<< </a>
						</div>
						<div class="container-page-record">
							<a
								href="<c:url value='/student/search/${page.getPrevPage() }'></c:url>">
								< </a>
						</div>
					</c:otherwise>
				</c:choose>
				<c:forEach var="page_id" items="${page.getNumberPageArray()}">
					<c:choose>
						<c:when test="${page_id == page.getCurrentPosition()}">
							<div class="container-page-record container-page-record--current">
								<a href="<c:url value='/student/search/${page_id}'></c:url>">
									<c:out value="${page_id}" />
								</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="container-page-record">
								<a href="<c:url value='/student/search/${page_id}'></c:url>">
									<c:out value="${page_id}" />
								</a>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<%-- end v1 --%>
				<c:choose>
					<c:when test="${ page.getCurrentPosition() >= page.getSize()}">
						<div class="container-page-record display--none">

							<a
								href="<c:url value='/student/search/${page.getNextPage() }'></c:url>">
								> </a>
						</div>
						<div class="container-page-record display--none">
							<a
								href="<c:url value='/student/search/${page.getEndPage() }'></c:url>">
								>> </a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="container-page-record ">

							<a
								href="<c:url value='/student/search/${page.getNextPage() }'></c:url>">
								> </a>
						</div>
						<div class="container-page-record ">
							<a
								href="<c:url value='/student/search/${page.getEndPage() }'></c:url>">
								>> </a>
						</div>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</div>
<script type="text/javascript"> window.onload = alertName('<%= message %>'); </script>
</body>
</html>
