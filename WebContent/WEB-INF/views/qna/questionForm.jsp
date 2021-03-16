<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문하기</title>

<!-- formUtil.js 등로 -->
<script type="text/javascript" src="../js/formUtil.js"></script>

<style type="text/css">
.dataRow:hover{
	cursor: pointer;
	background: #eee;
}
</style>

<script type="text/javascript">
// 객체 선택에 문제가 있다. 아래 Document가 다 로딩이 되면 실행되는 스크립트 작성
// jquery -> $(function(){처리문 만들기;}) = jquery(function(){처리문 만들기;})
$(function(){ // jquery에서 익명함수를 전달해서 저장해놨다가 Document가 로딩이 다되면 호출해서 처리해준다.
	
	// 이벤트 처리
	// 최소 버튼 - 이전 페이지(리스트)로 돌아간다.
	$("#cancelBtn").click(function(){
		// alert("취소");
		// 이전페이지로 이동
		history.back();
	});
	
	// submit() 이벤트에 데이터 검사
	$("#writeForm").submit(function(){
		// alert("데이터 전달 이벤트");
		
		// 필수 입력
		// 제목
		// alert(!require($("#title"), "제목"));
		// alert($("#title"));
		// alert($("#title").val());
		if(!require($("#title"), "제목")) return false;
		// 내용
		if(!require($("#content"), "내용")) return false;
		// 제목
		
		// 길이
		// 제목 4자 이상
		if(!checkLength($("#title"), "제목", 4)) return false;
		// 내용 4자 이상
		if(!checkLength($("#content"), "내용", 4)) return false;
		
	});
	
});
</script>

</head>
<body>
<div class="container">
<h1>질문하기</h1>
<form action="question.jsp" id="writeForm" method="post">
<div class="form-group">
	<label for="title">제목</label>
	<input name="title" id="title" class="form-control" required="required"
	placeholder="제목을 4자이상 입력하셔야 합니다."/>
</div>
<div class="form-group">
	<label for="content">내용</label>
	<textarea name="content" id="content" rows="5" required="required"
	placeholder="내용은 4자 이상 입력하셔야 합니다." class="form-control"></textarea>
</div>

<button class="btn btn-default">등록</button>
<button type="reset" class="btn btn-default">새로입력</button>
<button type="button" id="cancelBtn" class="btn btn-default">취소</button>
</form>
</div>
</body>
</html>