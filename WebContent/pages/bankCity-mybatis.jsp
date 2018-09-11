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
<!-- 					id: <input type="text" name="id" maxlength="50" /> -->
					staff: <input type="text" name="editor" maxlength="50" />
					<input type="submit" value="save" />
				</form>
			</div>
			<div class="delete">
				<span>"delete"</span><br/>
				<form action="${ctx}/mybatis/bankCity/delete" method="post">
					id: <input type="text" name="id" maxlength="10" />
					<input type="submit" value="save" />
				</form>
			</div>
			<div class="get-one">
				<span>"get-one"</span><br/>
				<form action="${ctx}/mybatis/bankCity/getBankCityByID" method="post">
					id: <input type="text" name="bankCityID" maxlength="10" />
					<input type="submit" value="save" />
				</form>
			</div>
			<div class="all-items">
				<span>"get all items"</span><br/>
				<form action="${ctx}/mybatis/bankCity/getAllItems" method="post">
					<input type="submit" value="get" />
				</form>
				<span>"map all items"</span><br/>
				<form action="${ctx}/mybatis/bankCity/mapAllItems" method="post">
					<input type="submit" value="get" />
				</form>
			</div>
		</div>
		<div class="show">
			<div class="bank-city-one">
				<span>Bank City One</span><br/>
				<c:if test="${requestScope.bankCity != null}"></c:if>
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
					<c:set value="${requestScope.bankCity}" var="bankCity"></c:set>
					<tbody>
						<tr>
							<!-- 
								If there is a property not found, the following exception will occur: 
								javax.el.PropertyNotFoundException: Property 'insertDate' not found on type 
								per.itachi.test.pojo.oracle.User
							 -->
							<td><c:out value="${bankCity.id}"></c:out></td>
							<td><c:out value="${bankCity.code}"></c:out></td>
							<td><c:out value="${bankCity.name}"></c:out></td>
							<td><c:out value="${bankCity.status}"></c:out></td>
							<td><c:out value="${bankCity.creator}"></c:out></td>
							<td><c:out value="${bankCity.insertTime}"></c:out></td>
							<td><c:out value="${bankCity.editor}"></c:out></td>
							<td><c:out value="${bankCity.updateTime}"></c:out></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="bank-city-inserted">
				<span>Bank City Inserted</span><br/>
				<c:if test="${requestScope.bankCityInserted != null}"></c:if>
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
