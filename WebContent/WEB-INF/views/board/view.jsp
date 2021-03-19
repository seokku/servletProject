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
<title>게시판 글보기</title>
  
  <!-- 부트스트랩 라이브러리 등록 - CDN 방식 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script type="text/javascript">
// 객체 선택에 문제가 있다. 아래 Document가 다 로딩이 되면 실행되는 스크립트 작성
// jquery -> $(function(){처리문 만들기;}) = jquery(function(){처리문 만들기;})
$(function(){ // jquery에서 익명함수를 전달해서 저장해놨다가 Document가 로딩이 다되면 호출해서 처리해준다.
	// 삭제 버튼을 클릭하면 실제적으로 삭제를 진행할 건지에 대한 여부를 물어본다.
	$("#deleteBtn").click(function(){
		if(!confirm("정말로 삭제하시겠습니까?")) return false; // a tag의 이동 취소
	});
	
	// 댓글 등록 모달창 열기
	$("#writeReplyBtn").click(function(){
	// 		alert("댓글 달기 ");
	// 모달창의 제목 바꾸기
	$(".modal-title").text("댓글 달기");
	// 댓글 번호 안보이기 
	$("#modal_rno_div").hide();
	// 댓글 쓰기 처리 URL 설정
	$("#replayForm").attr("action", "replyWrite.do");
	// 댓글 내용과 작성자를 비워 둔다. - 수정을 하다가 취소를 누르면 내용이 그대로 있기 때문에
	$("#modal_content").val("");
	$("#modal_writer").val("");
	// 2개-등록과 수정버튼을 안보이게
	$("#modal_writeReplyBtn, #modal_updateReplyBtn").hide();
	// 등록 버튼을 보이게
	$("#modal_writeReplyBtn").show();
	
	});
	
	// 댓글 수정 모달창 열기
	$(".replyUpdateBtn").click(function(){
// 		alert("댓글 수정");
		// 모달 제목 바꾸기
		$(".modal-title").text("댓글 수정");
		// 수정이 포함된 댓글의 내용을 가져와서 모달창에 셋팅하기
		var dataRow = $(this).closest(".dataRow");
// 		alert(dataRow);
		// js : parseInt() -> str안에 처음 만나는 정수형 숫자를 숫자로 꺼낸다. 
		var rno = parseInt(dataRow.find(".rno").text());
		$("#modal_rno").val(rno); // val()안에 데이터가 있으면 setter, 없으면 getter
		var content = dataRow.find(".content").text();
		$("#modal_content").val(content); // val()안에 데이터가 있으면 setter, 없으면 getter
		var writer = dataRow.find(".writer").text();
		$("#modal_writer").val(writer); // val()안에 데이터가 있으면 setter, 없으면 getter
		// 2개-등록과 수정버튼을 안보이게
		$("#modal_writeReplyBtn, #modal_updateReplyBtn").hide();
		// 등록 버튼을 보이게
		$("#modal_updateReplyBtn").show();
		// 댓글 번호 보이게
		$("#modal_rno_div").show();
		
	});
	
	// 모달 창 안에 입력한 데이터를 댓글 등록 처리
	$("#modal_writeReplyBtn").click(function(){
// 		alert("등록 전송");
		// 데이터 유효성 검사. -> 생략
		
		// 데이터를 받는 URL 정한다.
		$("#replyForm").attr("action", "replyWrite.do?page=1&perPageNum=10&no=${vo.no}");
		// 페이지 이동을 시키면서 데이터 전송 -> submit()
		$("#replyForm").submit();
	});
	
	// 모달 창 안에 입력한 데이터를 댓글 수정 처리
	$("#modal_updateReplyBtn").click(function(){
// 		alert("수정 전송");
		// 데이터 유효성 검사. -> 생략
		
		// 데이터를 받는 URL 정한다.
		$("#replyForm").attr("action", "replyUpdate.do");
		// 페이지 이동을 시키면서 데이터 전송 -> submit()
		$("#replyForm").submit();
	});
	
});
</script>

</head>
<body>
<div class="container">
<h1>게시판 글보기</h1>
<table class="table">
	<tbody>
		<tr>
			<th>글번호</th>
			<td class="no">${vo.no }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${vo.title }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><pre style="background: #fff; border: none; padding: 0px;">${vo.content }</pre></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.writer }</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${vo.writeDate }</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${vo.hit }</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="2">
				<a href="updateForm.do?no=${vo.no }&page=${pageObject.page }&perPageNum=${pageObject.perPageNum}" class="btn btn-default">글수정</a>
				<a href="delete.do?no=${vo.no }&perPageNum=${pageObject.perPageNum}" class="btn btn-default" 
				 id="deleteBtn">글삭제</a>
				<a href="list.do?page=${pageObject.page }&perPageNum=${pageObject.perPageNum}" class="btn btn-default">리스트</a>
			</td>
		</tr>
	</tfoot>
</table>

<!-- 댓글 처리 -->
<h2>댓글<button class="pull-right btn btn-default" data-toggle="modal" data-target="#myModal" id="writeReplyBtn">댓글 달기</button></h2>
<ul class="list-group">
	<c:if test="${empty list }">
    	<li class="list-group-item list-group-item-danger">댓글이 존재하지 않습니다.</li>
    </c:if>
    <c:if test="${!empty list }">
  	  <c:forEach items="${list }" var="vo">
		    <li class="list-group-item list-group-item-success dataRow">
		    	<pre style="border: none; padding: 0px; background: none;"><span class="rno">${vo.rno }.</span><span class="content">${vo.content }</span></pre>
		    	<span class="writer">${vo.writer }</span> - ${vo.writeDate }
		    	<span class="pull-right"><button class="btn btn-default replyUpdateBtn" data-toggle="modal" data-target="#myModal">수정</button>
		    	<a href="deleteReply.do?rno=10" class="btn btn-default">삭제</a></span>
		    </li>
	  </c:forEach>
    </c:if>
<!--     <li class="list-group-item list-group-item-warning">노란색</li> -->
<!--      <li class="list-group-item list-group-item-info">파란색</li> -->
  </ul>
</div>

<!-- Modal : 댓글 쓰기, 수정에서 폼 사용 -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3 class="modal-title">Modal Header</h3>
      </div>
      <div class="modal-body">
		<form action="replyWrite.do" method="post" id="replyForm">
			<input name="no" type="hidden" value="${vo.no }" />
			<div class="form-group" id="modal_rno_div">
				<label for="rno">댓글 번호</label>
				<input name="rno" readonly="readonly" id="modal_rno" class="form-control">
			</div>
			<div class="form-group">
				<label for="content">내용</label>
				<textarea name="content" rows="3" class="form-control" id="modal_content"></textarea>
			</div>
			<div class="form-group" id="modal_rno">
				<label for="writer">작성자</label>
				<input name="writer" id="modal_writer" class="form-control">
			</div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" id="modal_writeReplyBtn">등록</button>
        <button type="button" class="btn btn-default" id="modal_updateReplyBtn">수정</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
      </div>
    </div>

  </div>
</div>

</body>
</html>