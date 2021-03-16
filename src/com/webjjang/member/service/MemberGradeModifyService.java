package com.webjjang.member.service;

import com.webjjang.main.controller.Service;
import com.webjjang.member.dao.MemberDAO;
import com.webjjang.member.vo.MemberVO;

public class MemberGradeModifyService implements Service {

	private MemberDAO dao;
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MemberGradeModifyService.service().obj : " + obj);
		System.out.println("MemberGradeModifyService.service().dao : " + dao);
		// dao가 null이면 dao.gradeModify() 메서드를 호출해서 사용하려고 하면 NullPointException이 일어난다.
		return dao.gradeModify((MemberVO) obj);
	}

	@Override
	public void setDAO(Object dao) {
		// TODO Auto-generated method stub
		// Init.init() 객체 생성 후 조립할때 실행 - 서버가 시작이 되면서 확인 - 이클립스 console창에 확인
		System.out.println("MemberGradeModifyService.setDAO().dao : " + dao);
		this.dao = (MemberDAO) dao;

	}

}
