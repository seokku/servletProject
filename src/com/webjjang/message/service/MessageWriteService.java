package com.webjjang.message.service;

import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.vo.MessageVO;
import com.webjjang.main.controller.Service;

public class MessageWriteService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	// 여기안에서만 사용하는것이기 때문에 private를 써주는게 좋음. 
	private MessageDAO dao;
	
	// 기본 생성자 만들기 -> 확인 시 필요하다.
	public MessageWriteService() {
		// TODO Auto-generated constructor stub
		// 서버가 시작될때 확인 - 안나오면 Inin.init()을 확인해야 한다. 
		System.out.println("MessageWriteService.MessageWriteService() - 생성완료");
	}
	
	
	
	@Override
	public void setDAO(Object dao) {
		// Init.inin() 조립을 할때 dao 확인 - null이면 안된다.(서버가 시작될때 확인하는 내용)
		System.out.println("MessageWriteService.setDAO().dao : " + dao);
		// 받아온 dao를 저장한다.
		this.dao = (MessageDAO) dao;
	}
	
	
	// url 요청에 따른 처리
	// 넘어오는 데이터가 MessageVO ==> obj
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 넘어오는 데이터 확인
		System.out.println("MessageWriteService.obj :" + obj);
		// 전체 페이지 셋팅 후 페이지 객체 출력
		// dao의 write()를 실행해서 결과를 리턴해준다.
		return dao.write((MessageVO) obj);
	}

}
