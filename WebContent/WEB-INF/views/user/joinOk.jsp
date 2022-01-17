<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite/assets/css/main.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div id="user">

		<!-- header nav영역 -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header nav영역 -->

		<div id="joinOk">
			<p class="text-Large bold">
				회원가입을 축하합니다<br> <br> <a href="/mysite/user?action=login">[로그인하기]</a>
			</p>
		</div>

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //footer영역 -->

	</div>
</body>
</html>