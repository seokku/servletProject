<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.message.vo.MessageVO"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 자바 부분
// 넘어오는 데이터를 수집 - 받는 사람 아이디, 내용
String accepter = request.getParameter("accepter");
String content = request.getParameter("content");

// session에서 내 아이디 가져오기
// session의 내용은 /member/login.jsp (28번째줄) 확인. 이때 key = login이라는것이 다르면 null이 나온다.  
LoginVO vo =  (LoginVO) session.getAttribute("login");
String sender = vo.getId(); // 아이디를 가져와서 sender에 넣는다.

// vo 객체를 생성하고 데이터를 넣는다.
MessageVO messageVO = new MessageVO();
messageVO.setContent(content);
messageVO.setSender(sender);
messageVO.setAccepter(accepter);

// db 처리 : jsp - serivce - dao -db
// ExeService.execute(실행할 Service, Service에 전달되는 데이터)
ExeService.execute(Beans.get(AuthorityFilter.url), messageVO); 
// ↘ Beans.get(AuthorityFilter.url) 이걸안하면  String url = request.getServletPath(); 이걸써야함 
// url에 들어가보면 url = req.getServletPath(); 라고 설정이 되어있기 때문에 따로 설정해줄 필요없이 AuthorityFilter.url를 사용하면됨

// 리스트로 자동 이동
response.sendRedirect("list.jsp");
%>
