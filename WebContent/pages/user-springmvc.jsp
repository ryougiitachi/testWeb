<%@ include file="/pages/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
<!-- 		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
		<title>User Spring MVC</title>
	</head>
	
	<body>
		<div>
			<span>userServiceload</span><br/>
			<c:set value="${requestScope.userServiceload}" var="userServiceload"></c:set>
			<c:out value="${userServiceload.userID}"></c:out> -
			<c:out value="${userServiceload.username}"></c:out> - 
			<c:out value="${userServiceload.insertDate}"></c:out> -
		</div>
		<div>
			<span>userServiceget</span><br/>
			<c:set value="${requestScope.userServiceget}" var="userServiceget"></c:set>
			<c:out value="${userServiceget.userID}"></c:out> -
			<c:out value="${userServiceget.username}"></c:out> - 
			<c:out value="${userServiceget.insertDate}"></c:out> -
		</div>
		<div>
			<span>userServicefindAll</span><br/>
			<ul>
				<c:forEach items="${requestScope.userServicefindAll}" var="item">
					<li>${item.userID} - ${item.username} - ${item.insertDate}</li>
				</c:forEach>
			</ul>
		</div>
	</body>
</html>
