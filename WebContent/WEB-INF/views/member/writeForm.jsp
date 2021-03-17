<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
  
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
// $(function(){ // jquery에서 익명함수를 전달해서 저장해놨다가 Document가 로딩이 다되면 호출해서 처리해준다.
	
	// 이벤트 처리
	// 최소 버튼 - 이전 페이지(리스트)로 돌아간다.
	$("#cancelBtn").click(function(){
		// alert("취소");
		// 이전페이지로 이동
		history.back();
	});
	
// 	// submit() 이벤트에 데이터 검사
// 	$("#writeForm").submit(function(){
// 		// alert("데이터 전달 이벤트");
		
// 		// 필수 입력
// 		// 제목
// 		// alert(!require($("#title"), "제목"));
// 		// alert($("#title"));
// 		// alert($("#title").val());
// 		if(!require($("#title"), "제목")) return false;
// 		// 내용
// 		if(!require($("#content"), "내용")) return false;
// 		// 제목
// 		if(!require($("#writer"), "작성자")) return false;
		
// 		// 길이
// 		// 제목 4자 이상
// 		if(!checkLength($("#title"), "제목", 4)) return false;
// 		// 내용 4자 이상
// 		if(!checkLength($("#content"), "내용", 4)) return false;
// 		// 작성자 2자 이상
// 		if(!checkLength($("#writer"), "작성자", 2)) return false;
		
// 	});
	
// });
</script>

</head>
<body>
<div class="container">
<h1>회원 가입</h1>
<form action="write.do" id="writeForm" method="post">
<div class="form-group">
	<label for="id">아이디</label>
	<input name="id" id="id" class="form-control" required="required"
	placeholder="아이디 입력 - 3자 이상" autocomplete="off"/>
</div>
<div class="form-group">
	<label for="pw">비밀 번호</label>
	<input name="pw" id="pw" class="form-control" required="required"
	 type="password" placeholder="비밀번호 입력 - 4자 이상"/>
</div>
<div class="form-group">
	<label for="pw">비밀 번호 확인</label>
	<input name="pw2" id="pw2" class="form-control" required="required"
	 type="password" placeholder="비밀번호 확인 입력 - 4자 이상"/>
</div>
<div class="form-group">
	<label for="name">이름</label>
	<input name="name" id="name" class="form-control" required="required"
	placeholder="이름 입력 - 2자 이상"/>
</div>
<div class="form-group">
	<label>성별</label>
	<label class="radio-inline">
		<input name="gender" id="gender_man" class="form-control" 
		 type="radio" checked="checked" value="남자" />
		남자
	</label>
	<label class="radio-inline">
		<input name="gender" id="gender_woman" class="form-control" 
		 type="radio" value="여자" />
		여자
	</label>
</div>
<div class="form-group">
	<label for="birth">생일</label>
	<input name="birth" id="birth" required="required"
	type="date" class="form-control" >
</div>
<div class="form-group">
	<label for="tel">전화번호</label>
	<input name="tel" id="tel" class="form-control" required="required"/>
</div>
<div class="form-group">
	<label for="email">이메일</label>
	<input name="email" id="email" class="form-control" required="required"/>
</div>
<button>등록</button>
<button type="reset">새로입력</button>
<button type="button" id="cancelBtn">취소</button>
</form>
</div>
</body>
</html>