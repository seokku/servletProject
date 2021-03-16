<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.qna.vo.QnaVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 자바 부분
// 질문 답변 보기 처리가 필요하다.
// 데이터 수집 - 글번호, 조회수 증가는 하지 않는다.
String strNo = request.getParameter("no");
Long no = Long.parseLong(strNo);

// DB에서 데이터 가져오기.
QnaVO vo = (QnaVO) ExeService.execute(Beans.get("/qna/view.jsp"), new Long[]{no, 0L});

// 서버 객체에 저장
request.setAttribute("vo", vo);


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대답하기</title>

<!-- formUtil.js 등록 -->
<script type="text/javascript" src="../js/formUtil.js"></script>

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
<h1>대답하기</h1>
<form action="answer.jsp" id="writeForm" method="post">
	<!-- 안보이면서 넘겨지는 데이터 셋팅 -->
	<input name="refNo" value="${vo.refNo }" type="hidden">
	<input name="ordNo" value="${vo.ordNo }" type="hidden">
	<input name="levNo" value="${vo.levNo }" type="hidden">
	<!-- 보여지는 데이터 셋팅 -->
	<div class="form-group">
		<label for="no">번호</label>
		<input name="no" id="no" class="form-control" readonly="readonly"
		value="${vo.no }"
		placeholder="제목을 4자이상 입력하셔야 합니다."/>
	</div>
	<div class="form-group">
		<label for="title">제목</label>
		<input name="title" id="title" class="form-control" required="required"
		value="[답변]${vo.title }"
		placeholder="제목을 4자이상 입력하셔야 합니다."/>
	</div>
	<div class="form-group">
		<label for="content">내용</label>
		<textarea name="content" id="content" rows="10" required="required"
		placeholder="내용은 4자 이상 입력하셔야 합니다." class="form-control">


↓ ↓ ↓ ↓ 질문 내용 ↓ ↓ ↓ ↓ 
${vo.content }
		</textarea>
	</div>
	
	<button class="btn btn-default">등록</button>
	<button type="reset" class="btn btn-default">새로입력</button>
	<button type="button" id="cancelBtn" class="btn btn-default">취소</button>
</form>
</div>
</body>
</html>