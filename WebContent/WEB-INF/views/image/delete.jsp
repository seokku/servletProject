<%@page import="com.webjjang.util.file.FileUtil"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 자바
// 넘어오는 데이터 받기
String strNo = request.getParameter("no");
String strPerPageNum = request.getParameter("perPageNum");
String deleteFile = request.getParameter("deleteFile");

System.out.println("/image/delete.jsp [no] :" + strNo);
System.out.println("/image/delete.jsp [perPageNum] :" + strPerPageNum);
System.out.println("/image/delete.jsp [deleteFile] :" + deleteFile);

Long no = Long.parseLong(strNo);
// DB에서 데이터 삭제하기
ExeService.execute(Beans.get(AuthorityFilter.url), no);
// 이미지 파일 지우기
FileUtil.remove(request.getServletContext().getRealPath("/") + deleteFile);
// 리스트 - 페이지:1, 한페이지에 표시할 갯수: 넘겨받은 데이터 
response.sendRedirect("list.jsp?page=1&perPageNum=" + strPerPageNum);
%>
 