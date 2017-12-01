<%@ include file="/pages/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Mybatis BankCity</title>
	</head>
	
	<body>
		<div class="modify">
			<div class="insert">
				<span>save</span><br/>
				<!-- 
					enctype = {application/x-www-form-urlencoded(default), multipart/form-data, text/plain}
				 -->
				<form action="${ctx}/mybatis/bankCity/add" method="post">
					code: <input type="text" name="code" maxlength="20" />
					name: <input type="text" name="name" maxlength="50" />
					status: 
					<select name="status">
						<option value="A">A</option>
						<option value="a">a</option>
						<option value="N">N</option>
						<option value="n">n</option>
						<option value="outofrange">outofrange</option>
						<option value="e">e</option>
					</select>
					staff: <input type="text" name="creator" maxlength="50" />
					<input type="submit" value="save" />
				</form>
			</div>
			<div class="update">
				<span>update</span><br/>
				<form action="${ctx}/mybatis/bankCity/update" method="post">
					staff: <input type="text" name="editor" maxlength="50" />
					<input type="submit" value="save" />
				</form>
			</div>
			<div class="delete">
				<span>"delete"</span><br/>
				<form action="${ctx}/mybatis/bankCity/delete" method="post">
					id: <input type="text" name="code" maxlength="10" />
					<input type="submit" value="save" />
				</form>
			</div>
		</div>
		<div class="show">
			<div>
				<span>Bank City Inserted</span><br/>
				<c:if test="${requestScope.bankCityInserted == null}"></c:if>
				<table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Code</th>
							<th>Name</th>
							<th>Status</th>
							<th>CREATOR</th>
							<th>Insert Time</th>
							<th>EDITOR</th>
							<th>Update Time</th>
						</tr>
					</thead>
					<c:set value="${requestScope.bankCityInserted}" var="bankCityInserted"></c:set>
					<tbody>
						<tr>
							<!-- 
								If there is a property not found, the following exception will occur: 
								javax.el.PropertyNotFoundException: Property 'insertDate' not found on type 
								per.itachi.test.pojo.oracle.User
							 -->
							<td><c:out value="${bankCityInserted.id}"></c:out></td>
							<td><c:out value="${bankCityInserted.code}"></c:out></td>
							<td><c:out value="${bankCityInserted.name}"></c:out></td>
							<td><c:out value="${bankCityInserted.status}"></c:out></td>
							<td><c:out value="${bankCityInserted.creator}"></c:out></td>
							<td><c:out value="${bankCityInserted.insertTime}"></c:out></td>
							<td><c:out value="${bankCityInserted.editor}"></c:out></td>
							<td><c:out value="${bankCityInserted.updateTime}"></c:out></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>
