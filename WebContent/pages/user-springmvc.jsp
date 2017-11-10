<%@ include file="/pages/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
<!-- 		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
		<title>User Spring MVC</title>
	</head>
	
	<body>
		<div class="show">
			<div>
				<span>userServiceload</span><br/>
				<!-- exception won't be thrown if item is null -->
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
				<span>userServicefind</span><br/>
				<c:set value="${requestScope.userServicefind}" var="userServicefind"></c:set>
				<c:out value="${userServicefind.userID}"></c:out> -
				<c:out value="${userServicefind.username}"></c:out> - 
				<c:out value="${userServicefind.insertDate}"></c:out> -
			</div>
			<div>
				<span>userServicefindAll</span><br/>
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
							<th>User Type</th>
							<th>Insert Datetime</th>
							<th>Update Datetime</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.userServicefindAll}" var="item">
							<tr>
								<td><c:out value="${item.userID}"></c:out></td>
								<td><c:out value="${item.username}"></c:out></td>
								<td><c:out value="${item.userType}"></c:out></td>
								<td><c:out value="${item.insertDate}"></c:out></td>
								<td><c:out value="${item.updateTime}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div>
				<span>userServicefindUser</span><br/>
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
							<th>User Type</th>
							<th>Insert Datetime</th>
							<th>Update Datetime</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.userServicefindUser}" var="item">
							<tr>
								<td><c:out value="${item.userID}"></c:out></td>
								<td><c:out value="${item.username}"></c:out></td>
								<td><c:out value="${item.userType}"></c:out></td>
								<td><c:out value="${item.insertDate}"></c:out></td>
								<td><c:out value="${item.updateTime}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modify">
			<div class="save">
				<span>save</span><br/>
				<!-- 
					enctype = {application/x-www-form-urlencoded(default), multipart/form-data, text/plain}
				 -->
				<form action="${ctx}/springmvc/testSaveUser" method="post">
					User ID: <input type="text" name="userID" maxlength="20" />
					Username: <input type="text" name="username" maxlength="20" /> <input
						type="submit" value="save" />
				</form>
		</div>
			<div class="update">
				<span>update</span><br/>
				<form action="${ctx}/springmvc/testUpdateUser" method="post">
					User ID: <input type="text" name="userID" maxlength="20"/> 
					Username: <input type="text" name="username" maxlength="20"/> 
					<input type="submit" value="update"/>
				</form>
			</div>
			<div class="save-or-update">
				<span>save or update</span><br/>
				<form action="${ctx}/springmvc/testSaveOrUpdateUser" method="post">
					User ID: <input type="text" name="userID" maxlength="20"/> 
					Username: <input type="text" name="username" maxlength="20"/> 
					<input type="submit" value="save-or-update"/>
				</form>
			</div>
		</div>
	</body>
</html>
