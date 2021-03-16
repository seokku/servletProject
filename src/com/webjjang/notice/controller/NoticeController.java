package com.webjjang.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class NoticeController implements Controller {
	private final String MODULE = "notice";
	private String jspInfo = null;
	
	@Override
	public String execute(HttpServletRequest request) throws Exception{
		System.out.println("NoticeController.execute()");
		
		// 페이지 처리를 한다.
		PageObject pageObject = PageObject.getInstance(request);
		request.setAttribute("pageObject", pageObject);
		// url에 맞는 처리를 한다.
		// switch  case를 이용한다.
		switch (AuthorityFilter.url) {
		// 1. 게시판 리스트 
		case "/" + MODULE + "/list.do":
			// service - dao --> request에 저장까지 해준다.
			list(request, pageObject);
		
			// "notice/list" 넘긴다 -> WEB-INF/views/ + notice/list + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/list";
			break;
			
		// 2. 게시판 글보기
		case "/" + MODULE + "/view.do":
			// service - dao --> request에 저장까지 해준다.
			view(request);
		
			// "notice/view" 넘긴다 -> WEB-INF/views/ + notice/view + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/view";
			break;
			
		// 3-1. 게시판 글쓰기 폼
		case "/" + MODULE + "/writeForm.do":
			// "notice/view" 넘긴다 -> WEB-INF/views/ + notice/writeForm + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/writeForm";
			break;
		
		// 3-2. 게시판 글쓰기 처리
		case "/" + MODULE + "/write.do":
			// service - dao --> request에 저장까지 해준다.
			write(request);
		
			// "notice/view" 넘긴다 -> WEB-INF/views/ + notice/view + .jsp를 이용해서 HTML을 만든다.
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;
			
		// 4-1. 게시판 글수정 폼
		case "/" + MODULE + "/updateForm.do":
			updateForm(request);
		
			// "notice/view" 넘긴다 -> WEB-INF/views/ + notice/updateForm + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/updateForm";		
			break;
		
		// 4-2. 게시판 글수정 처리
		case "/" + MODULE + "/update.do":
			// service - dao --> request에 저장까지 해준다.
			Long no = update(request);
			
			// "notice/view" 넘긴다 -> view.do로 자동으로 이동 
			jspInfo = "redirect:view.do?no=" + no +"&inc=0&page=" + pageObject.getPage() + "&perPageNum=" + pageObject.getPerPageNum();
			break;
		
		// 5. 게시판 글삭제 처리
		case "/" + MODULE + "/delete.do":
			// service - dao --> request에 저장까지 해준다.
			delete(request);
		
			// list.do로 자동으로 이동 
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;
		
		default:
			throw new Exception("페이지 오류 404 - 존재하지 않는 페이지입니다.");
		}
		// jsp의 정보를 가지고 리턴한다.
		
//		return "notice/list";
		return jspInfo;
		
	}
	
		// 1. 공지사항 리스트 처리
		private void list(HttpServletRequest request, PageObject pageObject) throws Exception{
			// 여기가 자바 코드입니다. servlet-controller(*)-Service-DAO
			
			@SuppressWarnings("unchecked")
			List<NoticeVO> list = (List<NoticeVO>) ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);
			// 서버객체 request에 담는다.
			request.setAttribute("list", list);
						
		}
		
		// 2. 공지사항 글보기 처리.
		private void view(HttpServletRequest request) throws Exception{
			// 여기가 자바 코드입니다. servlet-Controller-Service-DAO -> /notice/view.do
		
			// 넘어오는 데이터 받기 
			// - 글번호
			String strNo = request.getParameter("no");
			long no = Long.parseLong(strNo);
		
			NoticeVO vo = (NoticeVO) ExeService.execute(Beans.get(AuthorityFilter.url), no);
			// 서버객체 request에 담는다.
			request.setAttribute("vo", vo);
			
		}
		
		// 3. 공지사항 글쓰기 처리.
		private void write(HttpServletRequest request) throws Exception{
			
			// 넘어오는 데이터를 받는다.
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");

			// vo객체를 생성하고 저장한다.
			NoticeVO vo = new NoticeVO();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setStartDate(startDate);
			vo.setEndDate(endDate);

			// DB에 데이터를 저장 jsp(controller) - NoticeWriteService - NoticeDAO - notice table insert
			ExeService.execute(Beans.get(AuthorityFilter.url), vo);
		}
		
		// 4. 공지사항 글수정 폼
		private void updateForm(HttpServletRequest request) throws Exception{
			
			// 자바 부분입니다.
			// 1. 넘어오는 데이터 받기 - 글번호
			String strNo = request.getParameter("no");
			long no = Long.parseLong(strNo);
			// 조회수 1증가하는 부분은 inc=0으로 강제 셋팅해서 넘긴다.
			// 2. 글번호에 맞는 데이터 가져오기 -> NoticeViewService => /notice/view.jsp
			String url = "/notice/view.do"; // 현재 URL과 다르므로 강제 셋팅했다.
			NoticeVO vo = (NoticeVO) ExeService.execute(Beans.get(url), new Long[]{no, 0L});
		
			// 3. 서버 객체에 넣기
			request.setAttribute("vo", vo);
		}
		
		// 4-2. 공지사항 글수정 처리
		private Long update(HttpServletRequest request) throws Exception {
			
			// 1. 데이터 수집
			String strNo = request.getParameter("no");
			long no = Long.parseLong(strNo);
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
		
			NoticeVO vo = new NoticeVO();
			vo.setNo(no);
			vo.setTitle(title);
			vo.setContent(content);
			vo.setWriteDate(writer);
		
			// 2. DB 처리 - update.jsp -> service -> dao
			String url = request.getServletPath();
			Integer result = (Integer) ExeService.execute(Beans.get(url), vo);
			
			if(result < 1) throw new Exception("게시판 글수정 - 수정할 데이터가 존재하지 않습니다.");
			
			return no;
		
		}
		
		// 5. 공지사항 글삭제 처리
		private void delete(HttpServletRequest request) throws Exception{
			
			// 1. 데이터 수집
			String strNo = request.getParameter("no");
			long no = Long.parseLong(strNo);
		
			// 2. DB 처리 - delete.jsp -> service -> dao
			String url = request.getServletPath();
			Integer result = (Integer) ExeService.execute(Beans.get(url), no);
			if(result ==0) throw new Exception("게시판 글삭제 오류 - 존재하지 않는 글은 삭제할 수 없습니다.");
		}
		

}
