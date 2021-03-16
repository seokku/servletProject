<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageObject" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
  
  <!-- 부트스트랩 라이브러리 등록 - CDN 방식 -->
<!--   <meta name="viewport" content="width=device-width, initial-scale=1"> -->
<!--   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"> -->
<!--   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<!--   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->

<style type="text/css">
.dataRow:hover{
	cursor: pointer;
	background: #eee;
}
</style>

<script type="text/javascript">
// 객체 선택에 문제가 있다. 아래 Document가 다 로딩이 되면 실행되는 스크립트 작성
// jquery -> $(function(){처리문 만들기;}) = jquery(function(){처리문 만들기;})
$(function(){ // jquery에서 익명함수를 전달해서 저장해놨다가 Document가 로딩이 다되면 호출해서 처리해준다.
	// 데이터 한줄 선택하기와 이벤트처리
	$(".dataRow").click(function(){
		// alert($(this));
		// $(this) : 이벤트가 일어난(현재 처리되고 있는 객체) 자신을 의미
		// .find(selector) : .앞에 객체 안에서 선택한 것을 찾아라.
		// .text() : 객체(태그) 사이에 문자를 가져온다.
		// .text(data) : 객체(태그) 사이에 문자를 셋팅한다.
		var no = $(this).find(".no").text();
		// 조회수 1 증가를 위해 inc=1을 넘긴다.
		location = "view.do?no=" + no + "&inc=1&page=${pageObject.page}" + "&perPageNum=${pageObject.perPageNum}";
	});
});
</script>

</head>
<body>
<div class="container">
<h1>게시판 리스트</h1>
<table class="table">
	<!-- 제목 -->
	<thead>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody>
		<!-- 데이터가 있는 만큼 반복이 되어 지는 시작 부분 -->
		<c:forEach items="${list }" var="vo">
		<tr class="dataRow">
			<td class="no">${vo.no }</td>
			<td>${vo.title }</td>
			<td>${vo.writer }</td>
			<td>${vo.writeDate }</td>
			<td>${vo.hit }</td>
		</tr>
		</c:forEach>
		<!-- 데이터가 있는 만큼 반복이 되어 지는 끝 부분 -->
	</tbody>
	<tfoot>
		<tr>
			<td colspan="5">
				<pageObject:pageNav listURI="list.do" pageObject="${pageObject }" />
			</td>
		</tr>
		<tr>
			<td colspan="5">
				<a href="writeForm.do?perPageNum=${pageObject.perPageNum }" class="btn btn-default">글쓰기</a>
			</td>
		</tr>
	</tfoot>
</table> 
</div>
</body>
</html>