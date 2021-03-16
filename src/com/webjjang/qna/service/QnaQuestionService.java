package com.webjjang.qna.service;

import com.webjjang.qna.dao.QnaDAO;
import com.webjjang.qna.vo.QnaVO;
import com.webjjang.main.controller.Service;


public class QnaQuestionService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	QnaDAO dao;
	
	// 기본 생성자 만들기 -> 확인 시 필요하다.
	public QnaQuestionService() {
		// TODO Auto-generated constructor stub
		System.out.println("QnaQuestionService.QnaQuestionService() - 생성완료");
	}
	
	@Override
	public void setDAO(Object dao) {
		System.out.println("QnaQuestionService.setDAO().dao : " + dao);
		this.dao = (QnaDAO) dao;
	}
	
	// url 요청에 따른 처리
	// 넘어오는 데이터가 PageObject ==> obj
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 넘어오는 데이터 확인
		// 전체 데이터를 가져오기 
		System.out.println("QnaQuestionService.pageObject :" + obj);
		return dao.question((QnaVO) obj);
	}

}
