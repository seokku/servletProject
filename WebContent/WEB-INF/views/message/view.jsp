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
				<a href="delete.do?no=${vo.no }" class="btn btn-default" 
				 id="deleteBtn">글삭제</a>
				<a href="list.do" class="btn btn-default">리스트</a>
			</td>
		</tr>
	</tfoot>
</table>
</div>
</body>
</html>