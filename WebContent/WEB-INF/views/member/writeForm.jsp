<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

  <!-- jquery UI 라이브러리 등록 : datepicker() 사용 -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- formUtil.js 등록 -->
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
		// 아이디
		if(!require($("#id"), "아이디")) return false;
		// 비밀번호
		if(!require($("#pw"), "비밀번호")) return false;
		// 비밀번호 확인
		if(!require($("#pw2"), "비밀번호 확인")) return false;
		
		// 길이
		// 제목 4자 이상
		if(!checkLength($("#title"), "제목", 4)) return false;
		// 내용 4자 이상
		if(!checkLength($("#content"), "내용", 4)) return false;
		// 작성자 2자 이상
		if(!checkLength($("#writer"), "작성자", 2)) return false;
		
	});
	
	// datepicker 클래스 이벤트
	var now = new Date();
// 	alert(now);
	var startYear = now.getFullYear();
// 	alert(startYear - 100);
	var yearRange = (startYear - 100) +":" + startYear ;
// 	alert(yearRange);
    $( ".datepicker" ).datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        maxDate : new Date,
        dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
        monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
        yearRange: yearRange
      });
	
    // 비밀번호의 길이와 비밀번호 확인이 같은지 비교하는 이벤트 - 비밀번호나 비밀번호 확인 입력란에 키업이 일어나면 이벤트 발생
    var pw_error = "비밀번호와 비밀번호 확인은 4자 이상이여야 합니다.";
    var pw_equ_error = "비밀번호와 비밀번호 확인은 같아야 합니다.";
    var pw_success = "사용 가능한 비밀번호 입니다.";
    $("#pw, #pw2").keyup(function(){
    	// alert("값변경");
    	$("#checkPw").removeClass("alert-warning alert-success")
    	var pw_val = $("#pw").val();
    	var pw2_val = $("#pw2").val();
    	if(pw_val.length < 4 || pw2_val.length <4){
    		$("#checkPw").text(pw_error).addClass("alert-warning");
    		return false;
    	}
    	if(pw_val != pw2_val){
    		$("#checkPw").text(pw_equ_error).addClass("alert-warning");
    		return false;
    	}
   		$("#checkPw").text(pw_success).addClass("alert-success");
   		return true;
    	
    });
	
    // 아이디 중복 체크 메시지 선언
    var id_length_error="아이디는 3자 이상이여야 합니다.";
    // 아이디 중복 체크 하는 이벤트 처리
    $("#id").keyup(function(){
    	// 결과 디자인 - warning : 잘 안됨. success : 잘됨
    	$("#checkId").removeClass("alert-warning alert-success")
    	//alert("아이디 중복 체크");
    	var id = $("#id").val();
    	// 아이디가 입력이 안 되있거나 길이가 3미만인 경우 처리 
    	if(!id || id.length <3){
    		$("#checkId").text(id_length_error).addClass("alert-warning");
    		return false;
    	}
    	
    	// 아이디가 3자 이상인 경우 처리 - 서버에 가서 DB에 정보가 있는지 확인한 후 중복 메시지를 가져와서 div에 넣는다.
    	$("#checkId").load("/ajax/checkId.do?id=" + id,
    		// callback - load처리가 다끝나고 호출되는 함수
    		function(result){
	    		// alert(result);
		    	// 넣은 글자가 "가능한" 포함하고 있으면 CSS를 성공으로 바꾼다.
		    	// alert(result.indexOf("가능한"));
		    	if(result.indexOf("가능한") >= 0)
		    		$("#checkId").addClass("alert-success");
		    	else
		    		$("#checkId").addClass("alert-warning");
    		}
    	);
    });
	
});
</script>

</head>
<body>
<div class="container">
<h1>회원 가입</h1>
<form action="write.do" id="writeForm" method="post">
<div class="form-group">
	<label for="id">아이디</label>
	<input name="id" id="id" class="form-control" required="required"
	placeholder="아이디 입력 - 3자 이상" autocomplete="off"  maxlength="20" pattern="[A-Za-z][A-Za-z0-9]{2,19}"
	title="영문자로 시작해서 영문자 숫자를 3~20 크기로 입력하셔야 합니다." />
	<div id="checkId" class="alert alert-warning" >아이디는 3자 이상이여야 합니다.</div>
</div>
<div class="form-group">
	<label for="pw">비밀 번호</label>
	<input name="pw" id="pw" class="form-control" required="required"
	 type="password" placeholder="비밀번호 입력 - 4자 이상" maxlength="20"/>
</div>
<div class="form-group">
	<label for="pw">비밀 번호 확인</label>
	<input name="pw2" id="pw2" class="form-control" required="required"
	 type="password" placeholder="비밀번호 확인 입력 - 4자 이상" maxlength="20"/>
</div>
<div id="checkPw" class="alert alert-warning">비밀번호와 비밀번호 확인은 4자 이상이여야 합니다.</div>
<div class="form-group">
	<label for="name">이름</label>
	<input name="name" id="name" class="form-control" required="required"
	placeholder="이름 입력 - 2자 이상" maxlength="10"/>
</div>
<div class="form-group">
	<h4>성별</h4>
	<label for="gender_man" class="radio-inline">
		<input name="gender" id="gender_man" type="radio" checked="checked" value="남자" /> 남자
	</label>
	<label for="gender_woman" class="radio-inline">
		<input name="gender" id="gender_woman" type="radio" value="여자" />
		여자
	</label>
</div>
<div class="form-group">
	<label for="birth">생년월일</label>
	<input name="birth" id="birth" class="datepicker form-control" autocomplete="off" />
</div>
<div class="form-group">
	<label for="tel">연락처</label>
	<div class="form-inline">
	<input name="tel" id="tel1" class="form-control" size="3" maxlength="3"/>
	 - <input name="tel" id="tel2" class="form-control" size="4" maxlength="4"/>
	 - <input name="tel" id="tel3" class="form-control" size="4" maxlength="4"/>
	</div>
</div>
<div class="form-group">
	<label for="email">이메일</label>
	<input name="email" id="email" class="form-control" required="required"
	placeholder="email 입력 - id@site" maxlength="50"/>
</div>
<button>가입</button>
<button type="reset">새로입력</button>
<button type="button" id="cancelBtn">취소</button>
</form>
</div>
</body>
</html>