<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.image.vo.ImageVO"%>
<%@page import="java.util.List"%>
<%@page import="com.webjjang.util.PageObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageObject" tagdir="/WEB-INF/tags" %>
<%
//자바 - 게시판 리스트 동일
// 페이지 정보의 초가값 셋팅
Long curPage = 1L;
Long perPageNum = 12L;
// 넘어오는 데이터 저장하기
String strCurPage = request.getParameter("page");
String strPerPageNum = request.getParameter("perPageNum");
 
// PageObject 생성과 셋팅
PageObject pageObject = new PageObject();
if(strCurPage != null && !strCurPage.equals("")) curPage = Long.parseLong(strCurPage);
if(strPerPageNum != null && !strPerPageNum.equals("")) perPageNum = Long.parseLong(strPerPageNum);
pageObject.setPage(curPage);
pageObject.setPerPageNum(perPageNum);

// DB에서 데이터 가져오기
@SuppressWarnings("unchecked")
List<ImageVO> list = (List<ImageVO>) ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);

// 서버 객체에 담기 - List와 PageObject, 프로젝트의 패스
request.setAttribute("list", list);
request.setAttribute("pageObject", pageObject);
request.setAttribute("path", request.getContextPath());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 리스트</title>
<style type="text/css">
	.dataRow:hover{
		background: #000;
		cursor: pointer;
/* 		border: 3px dotted #888; */
	}
	#image_gallery, .line{
		clear: both;
	}
	.line{
		height: 20px;
	}
</style>
<script type="text/javascript">
$(function(){
	$(".dataRow").click(function(){
// 		alert("click");
		var no = $(this).find(".no").text();
		location = "view.jsp?no=" + no + "&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}";
	});
	
	// 한페이지에 보여주는 데이터 선택의 이벤트 처리 -> 변경이 일어나면 처리
	$("#sel_perPageNum").change(function(){
		// alert("값 변경");
		// 다시 리스트 불러오기 - 전달 정보는 페이지:1, perPageNum을 선택된 값을 전달.
		location = "list.jsp?page=1&perPageNum=" + $(this).val();
	});
});
</script>
</head>
<body>
<div class="container">
	<h1>이미지 리스트</h1>
	<div style="padding: 10px; border-bottom: 2px solid #eee; height: 55px;">
		<div class="pull-left">
			<a href="writeForm.jsp?perPageNum=${pageObject.perPageNum }" class="btn btn-default">등록</a>
		</div>
		<div class="pull-right form-inline">
			<label>한 페이지에 표시한 데이터</label>
			<select class="form-control" id="sel_perPageNum">
				<option ${(pageObject.perPageNum == 4)?"selected":"" }>4</option>
				<option ${(pageObject.perPageNum == 8)?"selected":"" }>8</option>
				<option ${(pageObject.perPageNum == 12)?"selected":"" }>12</option>
				<option ${(pageObject.perPageNum == 16)?"selected":"" }>16</option>
			</select>
		</div>
	</div>
	<div class="line"></div>
	<div id="image_gallery">
		
		 <div class="row">
		 	<c:forEach items="${list }" var="vo" varStatus="vs">
		 		<!--  c:forEach 속성중에서 varStatus="vs" => index나 conunt를 사용할 수 있는 객체 -->
		 		<c:if test="${(vs.index  > 0) && (vs.index % 4 == 0)}">
		 			<!-- 인덱스 번호가 0이상이고 4의 배수이면 한줄(class="row")은 만든다. -->
		 			${"</div>"}
		 			${"<div class='row'>"}
		 		</c:if>
		 	
		 		<!-- BootStrap Grid : 한줄은 12칸으로 나눠서 운영하는 시스템 -->
			  <div class="col-md-3 dataRow">
			    <div class="thumbnail">
			        <img src="${path }${vo.fileName}" alt="Lights" style="width:100%">
			        <div class="caption">
			          <p>[<span class="no">${vo.no }</span>] ${vo.title }</p>
			          ${vs.index } - ${vo.writeDate } - ${vo.name }(${vo.id })
			        </div>
			    </div>
			  </div>
		  </c:forEach>
		</div>
		<div>
			<pageObject:pageNav listURI="list.jsp" pageObject="${pageObject }" />
		</div>
		<c:if test="${!empty login }">
			<div>
				<a href="writeForm.jsp?perPageNum=${pageObject.perPageNum }" class="btn btn-default">등록</a>
			</div>
		</c:if>
	</div>
</div>
</body>
</html>