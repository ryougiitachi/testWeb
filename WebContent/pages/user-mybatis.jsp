<%@ include file="/pages/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
<!-- 		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
		<title>User Spring MVC</title>
	</head>
	
	<body>
		<div class="modify">
			<div class="insert">
				<span>save</span><br/>
				<!-- 
					enctype = {application/x-www-form-urlencoded(default), multipart/form-data, text/plain}
				 -->
				<form action="${ctx}/mybatis/addNewUser" method="post">
					Username: <input type="text" name="username" maxlength="20" /> 
					<input type="submit" value="save" />
				</form>
			</div>
		</div>
		<div class="show">
			<div>
				<span>userServicefind</span><br/>
				<c:set value="${requestScope.userServicefind}" var="userServicefind"></c:set>
				<c:out value="${userServicefind.userID}"></c:out> -
				<c:out value="${userServicefind.username}"></c:out> - 
				<c:out value="${userServicefind.insertDate}"></c:out> -
			</div>
			<div>
				<span>listBeforeInsertTime</span><br/>
<!-- 				<ul> -->
<!-- exception won't be thrown if items is null -->
<%-- 					<c:forEach items="${requestScope.userServicefindAll}" var="item"> --%>
<%-- 						<li>${item.userID} - ${item.username} - ${item.insertDate}</li> --%>
<%-- 					</c:forEach> --%>
<!-- 				</ul> -->
				<table>
					<thead>
						<tr>
							<th>User ID</th>
							<th>Username</th>
							<th>User Status</th>
							<th>Insert Datetime</th>
							<th>Update Datetime</th>
							<th>Access Datetime</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.listBeforeInsertTime}" var="item">
							<tr>
								<!-- 
									If there is a property not found, the following exception will occur: 
									javax.el.PropertyNotFoundException: Property 'insertDate' not found on type 
									per.itachi.test.pojo.oracle.User
								 -->
								<td><c:out value="${item.userID}"></c:out></td>
								<td><c:out value="${item.username}"></c:out></td>
								<td><c:out value="${item.userStatus}"></c:out></td>
								<td><c:out value="${item.insertTime}"></c:out></td>
								<td><c:out value="${item.updateTime}"></c:out></td>
								<td><c:out value="${item.accessTime}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>
