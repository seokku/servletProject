<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.message.vo.MessageVO"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.board.vo.BoardVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
// 자바
// 넘어오는 데이터 받기 - 메시지 번호
String strNo = request.getParameter("no");
// Long no = Long.parseLong(strNo);


// 내 아이디 정보를 꺼내야 한다.
String id = (((LoginVO) session.getAttribute("login")).getId());

// vo객체 생성 - 데이터 셋팅
MessageVO vo = new MessageVO();
vo.setNo(Long.parseLong(strNo)); // Long no = Long.parseLong(strNo); 이거를 거치지 않고 그냥 바로 넣어버린 것. 2개쓸것을 1개로 줄임.
vo.setAccepter(id); // 받는 사람이 나인(본인) 데이터를 읽기 표시 하기 위해서 

// DB처리 데이터 가져오기
// 1. 받은 사람이 로그인한 사람과 같아야 하고 (받은 메시지) 번호가 같고 받은 날짜가 null인 메시지를 (읽지않은)
//		읽음 표시를 한다.(acceptDate를 현재 날짜로 넣어준다 - update)
// 2. 메시지 번호에 맞는 전체 메시지 정보 가져오기
MessageVO viewVO = (MessageVO) ExeService.execute(Beans.get(AuthorityFilter.url), vo); // 그냥 쓰면 믹스매치 에러뜸 -> 캐스팅해라 (MessageVO) 해결
//↘ Beans.get(AuthorityFilter.url) 이걸안하면  String url = request.getServletPath(); 이걸써야함 
//url에 들어가보면 url = req.getServletPath(); 라고 설정이 되어있기 때문에 따로 설정해줄 필요없이 AuthorityFilter.url를 사용하면됨

// 서버객체 request에 담는다.
request.setAttribute("vo", viewVO); // "vo"는 키값이기 때문에 위에서 셋팅한 vo랑은 상관없음
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 보기</title>
</head>

<body>
<div class="container">
	<h1>메시지 글보기</h1>
	<table class="table">
		<tr>
			<th>글번호</th>
			<td class="no">${vo.no }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><pre style="background: #fff; border: none; padding: 0px;">${vo.content }</pre></td>
		</tr>
		<tr>
			<th>보낸사람</th>
			<td>${vo.sender }</td>
		</tr>
		<tr>
			<th>보낸날짜</th>
			<td>${vo.sendDate }</td>
		</tr>
		<tr>
			<th>받은사람</th>
			<td>${vo.accepter }</td>
		</tr>
		<tr>
			<th>받은날짜</th>
			<td>${vo.acceptDate }</td>
		</tr>
	<tfoot>
		<tr>
			<td colspan="2">
				<a href="delete.jsp?no=${vo.no }" class="btn btn-default" 
				 id="deleteBtn">글삭제</a>
				<a href="list.jsp" class="btn btn-default">리스트</a>
			</td>
		</tr>
	</tfoot>
</table>
</div>
</body>
</html>