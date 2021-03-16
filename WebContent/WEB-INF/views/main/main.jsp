<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
<h1>메인 페이지</h1>
<p>로그인 : session에 정보가 있으면 로그인 되었다.-> 로그아웃이 보여야 한다.</p>

<c:if test="${empty login }">
	<!-- 로그인을 하지 않은 상태 -->
	<div>로그인 정보가 없습니다.</div>
	<a href="../member/loginForm.jsp" class="btn btn-default">로그인</a>
</c:if>

<c:if test="${!empty login }">
	<!-- 로그인을 한 상태 -->
	<div id="userInfo">
		<span>${login.name }(${login.id })</span>님은 ${login.gradeName } 권한으로 로그인 되어 있습니다.
	</div>
	<a href="../member/logout.jsp" class="btn btn-default">로그아웃</a>
	<a href="../member/view.jsp" class="btn btn-default">내정보 보기</a>
	<c:if test="${login.gradeNo == 9 }">
		<!-- 괸리자 메뉴 -->
		<a href="../member/list.jsp" class="btn btn-default">회원 리스트</a>
		</c:if>
</c:if>
</div>
</body>
</html>