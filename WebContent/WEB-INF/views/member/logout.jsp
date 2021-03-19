<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// JSP의 저장 기본 객체 - 변수 선언한 바가 없는데 미리 JSP들어오기 전에 만들어 놓은 객체 -> 기본객체
// 저장 - pageContext, request, session, application
session.invalidate();
response.sendRedirect("../main/main.do");

%>