package com.webjjang.message.service;

import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.vo.MessageVO;
import com.webjjang.main.controller.Service;

public class MessageViewService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	private MessageDAO dao;
	
	// 기본 생성자 만들기 -> 확인 시 필요하다.
	public MessageViewService() {
		// TODO Auto-generated constructor stub
		// 서버가 시작될때 확인한다.
		System.out.println("MessageViewService.MessageViewService() - 생성 완료");
	}
	
	@Override
	public void setDAO(Object dao) {
		// 서버가 시작될때 확인한다.
		System.out.println("MessageViewService.setDAO().dao : " + dao);
		this.dao = (MessageDAO) dao;
	}
	
	// url 요청에 따른 처리
	// 넘어오는 데이터가 MessageVO ==> obj : no, accepter
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 넘어오는 데이터 확인
		System.out.println("MessageViewService.obj : " + obj);
		MessageVO vo = (MessageVO) obj;
		
		// 메시지 읽음 처리
		dao.viewUpdateReaded(vo);
		return dao.view(vo.getNo()); // view를 눌러보면 no가 있는데 no만 쓸수없으니까 getNo로 꺼내서 씀
	}

}
