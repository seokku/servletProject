package com.webjjang.member.service;

import com.webjjang.main.controller.Service;
import com.webjjang.member.dao.MemberDAO;

public class MemberViewService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	MemberDAO dao;
	
	@Override
	public void setDAO(Object dao) {
	
		// Init.init() 객체 생성 후 조립할때 실행 - 서버가 시작이 되면서 확인 - 이클립스 console창에 확인
		System.out.println("MemberViewService.setDAO().dao : " + dao);
		this.dao = (MemberDAO) dao;
	}
	
	@Override
	// 넘어오는 데이터 : 아이디 - 타입 : String
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MemberViewService.service().obj : " + obj);
		System.out.println("MemberViewService.service().obj : " + dao);
		// dao가 null이면 dao.gradeModify() 메서드를 호출해서 사용하려고 하면 NullPointException이 일어난다.
		return dao.view((String) obj);
		
	}

}
