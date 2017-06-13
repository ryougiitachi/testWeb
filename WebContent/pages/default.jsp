<%-- It doesn't work? --%>
<%-- <jsp:include page="/pages/taglibs.jsp"></jsp:include> --%>
<%@ include file="/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
		"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home Page</title>
	</head>
	<body>
		<c:out value="${ctx}"></c:out>
	</body>
</html>
