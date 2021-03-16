<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.board.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 자바 부분입니다.

// 한글처리
request.setCharacterEncoding("utf-8");

// 1. 데이터 수집
String strNo = request.getParameter("no");
long no = Long.parseLong(strNo);

// 2. DB 처리 - delete.jsp -> service -> dao
String url = request.getServletPath();
Integer result = (Integer) ExeService.execute(Beans.get(url), no);


// 3. list로 자동 이동
response.sendRedirect("list.jsp");

%>

