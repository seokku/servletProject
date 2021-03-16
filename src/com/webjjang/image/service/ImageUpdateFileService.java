package com.webjjang.image.service;

import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.controller.Service;

public class ImageUpdateFileService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	// 여기안에서만 사용하는것이기 때문에 private를 써주는게 좋음. 
	private ImageDAO dao;
	
	// 기본 생성자 만들기 -> 확인 시 필요하다.
	public ImageUpdateFileService() {
		// TODO Auto-generated constructor stub
		System.out.println("ImageUpdateFileService.ImageUpdateFileService() - 생성완료");
	}
	
	@Override
	public void setDAO(Object dao) {
		System.out.println("ImageUpdateFileService.setDAO().dao : " + dao);
		this.dao = (ImageDAO) dao;
	}
	
	// url 요청에 따른 처리
	// 넘어오는 데이터가 ImageVO ==> obj
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 넘어오는 데이터 확인
		System.out.println("ImageUpdateFileService.obj :" + obj);
		// 전체 페이지 셋팅 후 페이지 객체 출력
		return dao.updateFile((ImageVO) obj);
	}

}
