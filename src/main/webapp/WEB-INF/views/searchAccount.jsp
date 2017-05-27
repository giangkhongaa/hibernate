<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Manager user</title>
<style type="text/css"><%@ include file="/WEB-INF/css/style.css"%> </style>
<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.js">

</script>
<script src="${pageContext.request.contextPath}/js/crypto-md5.js">

</script>
<script src="${pageContext.request.contextPath}/js/search.js">
	
</script>

</head>
<body>
	<a href="${pageContext.request.contextPath}/student/home">come back</a>
	<div id="container">
		<h2>Account Management Use Ajax</h2>
		<label for="search">Search</label> <input type="text" id="search"
			name="search">
		<div id="info"></div>
		</div>
				<center><h3>Khi edit chỉ cần nhập password bình thường sẽ chuyển sang MD5</h3></center>
		<table id="loadTable" class="table tr">
			<thead>
				<tr>
					<th>ID</th>
					<th>User Name</th>
					<th>Password</th>
					<th>Role</th>
					<th>Function</th>
				</tr>
			</thead>
			<tbody id="tbody">
			</tbody>
		</table>

	

</body>
</html>