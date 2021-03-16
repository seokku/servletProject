<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.image.vo.ImageVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
// 자바
// 넘어오는 데이터 받기 - 번호[, 페이지, 한페이지당 이미지 갯수]
String strNo = request.getParameter("no");
Long no = Long.parseLong(strNo);

// DB에서 데이터 가져오기
ImageVO vo = (ImageVO) ExeService.execute(Beans.get(AuthorityFilter.url), no);

// 프로젝트 패스 구하기
String path = request.getContextPath();

// 서버객체 request에 저장하기
request.setAttribute("vo", vo);
request.setAttribute("path", path);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 보기</title>

<script type="text/javascript">
$(function(){
	// 허용되는 이미지 파일 형식들
	var imageExt = ["JPG", "JPEG", "GIF", "PNG"];

	
	// 이벤트 처리
	// 이미지 바꾸기 모달창의 바꾸기 버튼
	$("#changeBtn").click(function(){
		// alert("change");
		// 파일이 비어 있는지 확인
		var fileName = $("#imageFile").val();
		// alert(fileName);
		// 바꿀 파일란이 비어 있는 경우의 처리
		if(!fileName){
			alert("바꿀 이미지를 반드시 선택하셔야 합니다.");
			$("#imageFile").focus();
			return false;
		}
		// 바꿀 파일란이 비어 있지 않는 경우의 처리 - 지원하는 이미지 확인 처리
		// substring(시작[, 끝]) - 부분 문자열 잘라내기
		// lastIndexOf(찾는 문자열) - 뒤에서 부터 찾는 문자열의 위치. 찾는 문자열이 없으면 -1이 된다.
		// toUpperCase() -> 모든 영문자를 대문자로 만들어 준다. <--> toLowerCase()
		var ext = fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
		// alert(ext);
		
		// 이미지 확장자인지 확인하는 반복문 - imageExt가 위에 배열로 선언이 되어져 있어야만 한다. 
		var checkExt = false; // 지원하지 않는 확장자를 기본으로 셋팅
		for(i = 0; i < imageExt.length; i++){
			if(ext == imageExt[i]){
				checkExt = true; // 지원하는 확장자로 바꾼다.
				break;
			}
		}
		// 지원하지 않는 이미지 파일 선택경의 처리
		if(!checkExt){
			alert("지원하지 않는 이미지 파일입니다.");
			$("#imageFile").focus();
			return false;
		}

		// 강제적으로 폼 데이터를 전송한다.
		$("#updateFileForm").submit();
	});
});
</script>

</head>
<body>
<div class="container">
	<h1>이미지 보기</h1>
	<table class="table">
		<tr>
			<td colspan="2">
				<c:if test="${vo.id == login.id || login.gradeNo == 9}">
					<!-- 작성자가 로그인한 회원인 경우와 관리자에게만 나타나는 메뉴 -->
					<a href="updateForm.jsp?no=${vo.no }" class="btn btn-default">수정(제목,내용)</a>
					<button type="button" class="btn btn-default"
					 data-toggle="modal" data-target="#myModal">파일바꾸기</button>
					<a href="delete.jsp?no=${vo.no }&perPageNum=${param.perPageNum}&deleteFile=${vo.fileName}" class="btn btn-default">삭제</a>
				</c:if>
				<!-- EL 객체 - param.page => rquerst.getParameter("page") -->
				<a href="list.jsp?page=${param.page }&perPageNum=${param.perPageNum}"
				 class="btn btn-default">리스트</a>
			</td>
		</tr>
		<tr>
			<th style="width: 100px;">번호</th>
			<td>${vo.no }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${vo.title }</td>
		</tr>
		<tr>
			<th>이미지</th>
			<td><img src="${path }${vo.fileName }" style="width: 50%" /></td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${vo.content }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.name }(${vo.id })</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${vo.writeDate }</td>
		</tr>
		<tr>
			<td colspan="2">
			
			</td>
		</tr>
	</table>


	
	  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">이미지 바꾸기</h4>
        </div>
        <div class="modal-body">
          <p>바꿀 이미지 파일을 선택하세요.</p>
          <form action="updateFile.jsp" method="post" enctype="multipart/form-data" 
          id="updateFileForm">
          	<!-- 페이지 정보를 숨겨서 넘긴다. -->
          	<input name="page" value="${param.page }" type="hidden" />
          	<input name="perPageNum" value="${param.perPageNum }" type="hidden" />
          	<div class="form-group">
          		<label for="no">번호</label>
          		<input name="no" id="no" class="form-control" value="${vo.no }" 
          		 readonly="readonly"/>
          	</div>
          	<div class="form-group">
          		<label for="deleteFile">원본파일</label>
          		<input name="deleteFile" id="deleteFile" class="form-control"
          		 value="${vo.fileName }" readonly="readonly"/>
          	</div>
          	<div class="form-group">
          		<label for="imageFile">바꿀 파일 선택</label>
          		<input name="imageFile" id="imageFile" class="form-control"
          		 type="file" />
          	</div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" id="changeBtn">바꾸기</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
	
	
</div>
</body>
</html>