<%@ include file="/pages/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Rabbit MQ Testing</title>
	</head>
	<body>
		<div class="rabbitmq">
			<form id="formRabbitMQ" action="${ctx}/springmvc/testRabbitmq" method="post">
				<label for="chkRabbitmq">Rabbit MQ: </label>
				<select id="chkRabbitmq" name="rabbitmq">
					<option value="0" selected="selected">please choose</option>
					<option value="1">User Order</option>
					<option value="2">Black List</option>
					<option value="3">Pay Log</option>
				</select>
				<input type="submit" value="submit"/>
			</form>
		</div>
	</body>
</html>
