<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.util.PageObject"%>
<%@page import="com.webjjang.message.vo.MessageVO"%>
<%@page import="java.util.List"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageObject" tagdir="/WEB-INF/tags" %>    
<% 
// 자바 부분
// 페이지 처리를 위한 객체 사용
PageObject pageObject = new PageObject();
// 넘어오는 데이터 받기 
long curPage = 1; 
long perPageNum = 10; // 한페이지에 표시되는 갯수
//page는 jsp에서 기본객체로 사용하고 있다. -> 페이지의 정보가 담겨져 있다.
String strCurPage = request.getParameter("page");
//한페이지에 표시할 데이터의 수를 받는다.
String strPerPageNum = request.getParameter("perPageNum");
//넘어오는 페이지가 있는 경우는 넘어오는 페이지를 현재 페이지로 셋팅. 그렇지 않으면 1이 셋팅된다.
if(strCurPage != null) pageObject.setPage(Long.parseLong(strCurPage));
//한 페이지당 표시할 데이터의 수가 안넘어오면 10으로 셋팅된다. 넘어오면 넘어 오는 데이터를 사용한다.
if(strPerPageNum != null) pageObject.setPerPageNum(Long.parseLong(strPerPageNum));

// 내 아이디를 가져와서 pageObject에 저장을 해둔다.
pageObject.setAccepter(((LoginVO) session.getAttribute("login")).getId());


// DATA 가져오기
// String url = request.getServletPath();
@SuppressWarnings("unchecked") // 경고를 무시 시키자.
List<MessageVO> list = (List<MessageVO>) ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);

// 서버 객체에 저장
request.setAttribute("list", list);
request.setAttribute("pageObject", pageObject);

%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 리스트</title>
<style type="text/css">
tr{
	color: #777;

}
.noRead{
	color: #4d0026;
}
.dataRow:hover{
	cursor: pointer;
	background: #eee;
}
</style>
<script type="text/javascript">
$(function(){
	// 이벤트 처리
	// 메시지 보기로 이동
	$(".dataRow").click(function(){
		// alert("data 보기 클릭"); 
		// $(this) : 자기 자신(이벤트가 일어난 곳 - 현재는 tr태그). 클래스가 no인 객체를 찾아라.태그안에 있는 글자 가져오기
		var no = $(this).find(".no").text();
		location = "view.jsp?no=" + no;
	});
});

</script>


</head>
<body>
<div class="container">
<h1>메시지 리스트</h1>
<table class="table">
	<!-- 제목 -->
		<tr>
			<th>번호</th>
			<th>보낸사람</th>
			<th>보낸날짜</th>
			<th>받는사람</th>
			<th>받은날짜</th>
		</tr>
		<!-- 데이터가 있는 만큼 반복이 되어 지는 시작 부분 -->
		<c:forEach items="${list }" var="vo">
			<tr class='dataRow ${(empty vo.acceptDate)?"noRead":"" }'>
				<td class="no">${vo.no }</td>
				<td>${vo.sender }</td>
				<td>${vo.sendDate }</td>
				<td>${vo.accepter }</td>
				<td>${(empty vo.acceptDate)?"읽지 않음":vo.acceptDate }</td>
			</tr>
		</c:forEach>
		<!-- 데이터가 있는 만큼 반복이 되어 지는 끝 부분 -->
		<tr>
			<td colspan="5">
				<pageObject:pageNav listURI="list.jsp" pageObject="${pageObject }" />
			</td>
		</tr>
		<tr>
			<td colspan="5">
				<a href="writeForm.jsp" class="btn btn-default">보내기</a>
			</td>
		</tr>
</table>
</div>
</body>
</html>