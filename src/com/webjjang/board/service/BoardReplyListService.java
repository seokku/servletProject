package com.webjjang.board.service;

import java.util.Arrays;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.main.controller.Service;

import com.webjjang.util.PageObject;

public class BoardReplyListService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	BoardDAO dao;
	
	@Override
	public void setDAO(Object dao) {
		System.out.println("BoardReplyListService.setDAO().dao : " + dao);
		this.dao = (BoardDAO) dao;
	}
	// url 요청에 따른 처리
	// 넘어오는 데이터가 배열로[]{no, PageObject} ==> obj
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 넘어오는 데이터 확인
		Object[] objs = (Object[])obj;
		System.out.println("BoardReplyListService.obj :" + Arrays.toString(objs));
		// 배열로 되어 있는 것은 순서에 맞게 데이터 분할[0] - no, [1] - pageObject
		Long no = (Long) objs[0];
		PageObject pageObject = (PageObject) objs[1];
		
		
		// 전체 데이터를 가져오기 
		int replyTotalRow = (int) dao.getReplyTotalRow(no);
//		PageObject pageObject = (PageObject) obj; 
		pageObject.setTotalRow(replyTotalRow);
		// 전체 페이지 셋팅 후 페이지 객체 출력
		System.out.println("BoardReplyListService.pageObject :" + pageObject);
		return dao.replyList(no, pageObject);
	}

}
