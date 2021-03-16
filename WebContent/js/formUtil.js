/**
 * 입력 데이터 처리를 위한 JS
 */

// 필수 입력 항목 검사. - obj - input tag 객체
// if(!require(input객체, "항목이름")) return false;
// 호출하는 곳에서도 return dataCheck();
function require(obj, item){
	// alert(obj);
	// alert(obj.val());
	if(!obj.val()){ // 입력 란에 입력이 안되어 있어요.
		// 경고 메시지
		alert(item + "은(는) 반드시 입력하셔야 합니다.");
		// 커서 위치시키기
		obj.focus();
		// 데이터 전송(submit())를 취소 시키는 데이터 false 를 돌려준다.
		return false;
	}
	return true;
}


// 입력 데이터의 길이 체크(~자 이상) - obj - input tag 개체
// if(!checkLength(input객체, "항목이름", 길이)) return false;
function checkLength(obj, item, len){
	// alert(obj.val());
	if(obj.val().length < len){
		// 경고
		alert(item + "은(는) " + len + "자 이상이여야 합니다.");
		// 만약에 type이 password 인 경우 사용자 눈에 안보이므로 데이터를 제거한다.
		if(obj.type == "password") obj.value = "";
		// 커서를 위치시킨다.
		obj.focus();
		// submit()을 취소시키기 위한 false를 리턴한다.
		return false;
	}
	// 데이터가 적당하면 true를 리턴한다.
	return true;
}

// input 태그의 공백문자를 제거하는 함수.
// 값의 공백을 제거한 후 다시 입력 란의 값으로 넣어 준다.
function removeBlank(inputObj){
	//alert("removeBlank()");
	var v = inputObj.value;
	//alert("["+ v +"]");
	inputObj.value = v.trim();
	//alert("[" + inputObj.value + "]");
}


// 다음항목으로 자동 이동이 되는 함수(keyup 이벤트를 사용하여야 한다.)
function goNext(thisObj, nextObj, len){
	// 현재 입력 란의 값의 길이가 len에 도달하면 다음 입력란으로 이동시켜 준다.
	if(thisObj.value.length >= len) nextObj.focus();
}

