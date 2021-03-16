<%@page import="com.webjjang.main.controller.Service"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="java.lang.reflect.Executable"%>
<%@page import="com.webjjang.member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
// 자바 부분 -----------------
// 실행여부 확인 - 이클립스 Console 창에서 확인하세요.
System.out.println("gradeModify.jsp - 실행 ---------");
// 전달되는 데이터를 수집
// 아이디 받아내기 - 데이터가 안나오면 list.jsp에서 부터 실행을 했었어야 한다.
//               list.jsp의 form안에 input의 name="id"이여야만하고 실제적으로 데이터가 셋팅되어 있어야한다.
String id = request.getParameter("id");
System.out.println("gradeModify.jsp [id] - " + id);
// 등급 문자열 받아내기 - 데이터가 안나오면 list.jsp에서 부터 실행을 했었어야 한다.
//               list.jsp의 form안에 input의 name="gradeNo"이여야만하고 실제적으로 데이터가 셋팅되어 있어야한다.
String strGradeNo = request.getParameter("gradeNo");
System.out.println("gradeModify.jsp [strGradeNo] - " + strGradeNo);
int gradeNo = Integer.parseInt(strGradeNo);
System.out.println("gradeModify.jsp [gradeNo] - " + gradeNo);

// 수집한 데이터를 DB처리 - jsp - service - dao : 매개변수로 전달. 값이 2개이므로 클래스(VO)를 사용
// 저장할 VO객체 생성
MemberVO vo = new MemberVO();
// 생성된 vo객체에 데이터 넣기 - setter() - 아이디, 등급번호
vo.setId(id);
vo.setGradeNo(gradeNo);
// 데이터 셋팅이 잘되었는지 확인
System.out.println("gradeModify.jsp [vo] - " + vo);

// MemberGradeModifyService, MemberDAO.gradeModify(vo), DBSQL.MEMBER_GRADE_MODIFY 작성하세요.
// Init.init() - 생성하고 조립하세요.


// 수집한 데이터를 service로 넘겨서 실행한다. : jsp - servcei - dao - member DB
// Service 선택해서 가져오기 : Beans.get(url)
String url = request.getServletPath();
System.out.println("gradeModify.jsp [url] - " + url);

Service service = Beans.get(url);
System.out.println("gradeModify.jsp [service] - " + service);

ExeService.execute(service, vo);
//ExeService.execute(Beans.get(url), vo);

// 자동으로 /member/list.jsp로 이동이 되어야 한다.
response.sendRedirect("list.jsp");



%>
