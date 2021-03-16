package com.webjjang.qna.service;

import com.webjjang.qna.dao.QnaDAO;
import com.webjjang.main.controller.Service;

import com.webjjang.util.PageObject;

public class QnaListService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	QnaDAO dao;
	
	// 기본 생성자 만들기 -> 확인 시 필요하다.
	public QnaListService() {
		// TODO Auto-generated constructor stub
		System.out.println("QnaListService.QnaListService() - 생성완료");
	}
	
	@Override
	public void setDAO(Object dao) {
		System.out.println("QnaListService.setDAO().dao : " + dao);
		this.dao = (QnaDAO) dao;
	}
	
	// url 요청에 따른 처리
	// 넘어오는 데이터가 PageObject ==> obj
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 넘어오는 데이터 확인
		System.out.println("QnaListService.obj :" + obj);
		// 전체 데이터를 가져오기 
		long totalRow = dao.getTotalRow();
		// 넘어오는 obj가 Object 타입으로 넘어오지만 PageObject가 들어 있어서 PageObject로 캐스팅해서 사용한다.
		PageObject pageObject = (PageObject) obj;
		pageObject.setTotalRow(totalRow);
		// 전체 페이지 셋팅 후 페이지 객체 출력
		System.out.println("QnaListService.pageObject :" + pageObject);
		return dao.list(pageObject);
	}

}
