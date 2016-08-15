<%@ page errorPage="error-page.jsp" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>FilmStore</title>
</head>
<body>
	<c:redirect url="Controller">
		<c:param name="command" value="show_list_film"/>
		<c:param name="page" value="1" />
		<c:param name="start" value="1" />
	</c:redirect>
</body>
</html>