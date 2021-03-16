<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.qna.vo.QnaVO"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
// 자바
// 넘어오는 데이터 수집 
String strNo = request.getParameter("no");
String title = request.getParameter("title");
String content = request.getParameter("content");

// 아이디 session에서 받아오기
String id = ((LoginVO) session.getAttribute("login")).getId();
String strRefNo = request.getParameter("refNo");
String strOrdNo = request.getParameter("ordNo");
String strLevNo = request.getParameter("levNo");

// VO 객체를 생성하고 저장해 놓는다.
QnaVO vo = new QnaVO();
//번호(no)는 insert시 시퀸시를 사용한다. 넘겨받은 번호는 부모번호로 셋팅
vo.setTitle(title);
vo.setContent(content);
vo.setId(id);
vo.setRefNo(Long.parseLong(strRefNo)); // 관련글, 그대로 가져오기
vo.setOrdNo(Long.parseLong(strOrdNo) +1); // 관련 글번호가 같고 순서번호가 같거나 큰 것의 순서번호는 1증가 우선 시킨다.
vo.setLevNo(Long.parseLong(strLevNo) +1); //  들어쓰기
vo.setParentNo(Long.parseLong(strNo)); // 번호(no)는 insert시 시퀸시를 사용한다. 넘겨받은 번호는 부모번호로 셋팅

// DB 저장 처리 : jsp - service - dao - DB 
ExeService.execute(Beans.get(AuthorityFilter.url), vo);

// 처리가 다 끝나면 리스트로 자동이동시킨다.
response.sendRedirect("list.jsp");

%>    
