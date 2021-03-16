package com.webjjang.notice.service;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.main.controller.Service;

public class NoticeViewService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	private NoticeDAO dao;
	
	public NoticeViewService() {
		System.out.println("NoticeViewService.NoticeViewService() - 질문 답변 보기 객체 생성");
		
	}
	
	@Override
	public void setDAO(Object dao) {
		this.dao = (NoticeDAO) dao;
	}
	
	@Override
	public Object service(Object obj) throws Exception {
		return dao.view((long) obj);
	}

}
