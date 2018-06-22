<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Sing-In(${sessionScope.login })</h1>
	<form action="lec02">
		id:<input type="text" name="id" /><br/>
		pw:<input type="text" name="pw" /><br/>
		<button type="submit">Sign-In</button>
	</form>
</body>
</html>