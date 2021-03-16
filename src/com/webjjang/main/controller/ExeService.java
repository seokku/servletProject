package com.webjjang.main.controller;

public class ExeService {

	// execuet(실행할 service, 넘겨줄 데이터)
	public static Object execute(Service service, Object obj) throws Exception{
		// 실행되는 객체 정보 출력 - Service
		System.out.println("실행되는 객체:" + service.getClass().getSimpleName() 
				+ ".service()");
		// 넘어가는 데이터 출력
		System.out.println("전달되는 데이터 : " + obj);
		// 실행한다. 결과를 받는다.
		Object result = service.service(obj);
		// 결과 출력 확인
		System.out.println("실행한 결과 : " + result);
		// 결과를 넘겨준다.
		return result;
	}
	
}
