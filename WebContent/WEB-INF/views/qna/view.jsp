<%@page import="com.webjjang.qna.vo.QnaVO"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%
// 자바 부분
// 데이터 수집 - 글번호, 조회수 증가 여부
String strNo = request.getParameter("no");
String strInc = request.getParameter("inc");
Long no = Long.parseLong(strNo);
Long inc = Long.parseLong(strInc);

// DB에서 데이터 가져오기.
QnaVO vo = (QnaVO) ExeService.execute(Beans.get(AuthorityFilter.url), new Long[]{no, inc});

// 서버 객체에 저장
request.setAttribute("vo", vo);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문답변 보기</title>
</head>
<body>
<div class="container">
	<h1>질문답변 보기</h1>
	<table class="table">
		<tr>
			<th>번호</th>
			<td>${vo.no }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${vo.title }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><pre style="border: none; background: whtie; padding: 0px">${vo.content }</pre></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.name }(${vo.id })</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${vo.writeDate }</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${vo.hit }</td>
		</tr>
		<tr>
			<td colspan="2">
				<a href="answerForm.jsp?no=${vo.no }" class="btn btn-default">답변</a>
				<c:if test="${vo.id == login.id || login.gradeNo == 9}" >
				<a href="delete.jsp?no=${vo.no }" class="btn btn-default">삭제</a>
				</c:if>
				<a href="list.jsp" class="btn btn-default">리스트</a>
			</td>
		</tr>
	</table>
</div>
</body>
</html>