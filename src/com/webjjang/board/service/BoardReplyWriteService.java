package com.webjjang.board.service;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.vo.BoardReplyVO;
import com.webjjang.main.controller.Service;

public class BoardReplyWriteService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	private BoardDAO dao;
	
	@Override
	public void setDAO(Object dao) {
		System.out.println("BoardReplyWriteService.setDAO().dao :" + dao);
		this.dao = (BoardDAO) dao;
	}
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return dao.replyWrite((BoardReplyVO) obj);
	}

}
