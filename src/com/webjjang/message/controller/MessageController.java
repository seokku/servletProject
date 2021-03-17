package com.webjjang.message.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.multipart.ExceededSizeException;
import com.webjjang.message.vo.MessageVO;
import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class MessageController implements Controller {
	
	private final String MODULE = "message";
	private String jspInfo = null;
	
	@Override
	public String execute(HttpServletRequest request) throws Exception{
		System.out.println("MessageController.execute()");
		
		// 페이지를 위한 처리
		PageObject pageObject = PageObject.getInstance(request);
		request.setAttribute("pageObject", pageObject); // 페이지를 보여주기 위해 서버객체에 담는다.
		
		switch (AuthorityFilter.url) {
		// 1. 메시지 리스트 
		case "/" + MODULE + "/list.do":
			// service - dao --> request에 저장까지 해준다.
			list(request, pageObject);
		
			// "message/list" 넘긴다 -> WEB-INF/views/ + message/list + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/list";
			break;
			
		// 2. 메시지 글보기
		case "/" + MODULE + "/view.do":
			// service - dao --> request에 저장까지 해준다.
			view(request);
		
			// "message/view" 넘긴다 -> WEB-INF/views/ + message/view + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/view";
			break;
			
		// 3-1. 메시지 글쓰기 폼
		case "/" + MODULE + "/writeForm.do":
			// "message/view" 넘긴다 -> WEB-INF/views/ + message/writeForm + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/writeForm";
			break;
		
		// 3-2. 메시지 글쓰기 처리
		case "/" + MODULE + "/write.do":
			// service - dao --> request에 저장까지 해준다.
			write(request);
		
			// "message/view" 넘긴다 -> WEB-INF/views/ + message/view + .jsp를 이용해서 HTML을 만든다.
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;
			
		
		// 4. 메시지 글삭제 처리
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
		return jspInfo;
		
	}
	// 1. 메시지 리스트 처리
	private void list(HttpServletRequest request, PageObject pageObject) throws Exception{
		// 자바 부분
		// 페이지 처리를 위한 객체 사용
//		PageObject pageObject = new PageObject();
		
		// 넘어오는 데이터 받기 
		long curPage = 1; 
		long perPageNum = 10; // 한페이지에 표시되는 갯수
		//page는 jsp에서 기본객체로 사용하고 있다. -> 페이지의 정보가 담겨져 있다.
		String strCurPage = request.getParameter("page");
		//한페이지에 표시할 데이터의 수를 받는다.
		String strPerPageNum = request.getParameter("perPageNum");
		//넘어오는 페이지가 있는 경우는 넘어오는 페이지를 현재 페이지로 셋팅. 그렇지 않으면 1이 셋팅된다.
		if(strCurPage != null) pageObject.setPage(Long.parseLong(strCurPage));
		//한 페이지당 표시할 데이터의 수가 안넘어오면 10으로 셋팅된다. 넘어오면 넘어 오는 데이터를 사용한다.
		if(strPerPageNum != null) pageObject.setPerPageNum(Long.parseLong(strPerPageNum));

		// 내 아이디를 가져와서 pageObject에 저장을 해둔다.
		pageObject.setAccepter(((LoginVO) request.getSession().getAttribute("login")).getId());


		// DATA 가져오기
		// String url = request.getServletPath();
		@SuppressWarnings("unchecked") // 경고를 무시 시키자.
		List<MessageVO> list = (List<MessageVO>) ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);

		// 서버 객체에 저장
		request.setAttribute("list", list);
		request.setAttribute("pageObject", pageObject);
	}

	// 2. 메시지 보기 처리.
	private void view(HttpServletRequest request) throws Exception{
		
		// 자바
		// 넘어오는 데이터 받기 - 메시지 번호
		String strNo = request.getParameter("no");
		// Long no = Long.parseLong(strNo);


		// 내 아이디 정보를 꺼내야 한다.
		String id = (((LoginVO) request.getSession().getAttribute("login")).getId());

		// vo객체 생성 - 데이터 셋팅
		MessageVO vo = new MessageVO();
		vo.setNo(Long.parseLong(strNo)); // Long no = Long.parseLong(strNo); 이거를 거치지 않고 그냥 바로 넣어버린 것. 2개쓸것을 1개로 줄임.
		vo.setAccepter(id); // 받는 사람이 나인(본인) 데이터를 읽기 표시 하기 위해서 

		// DB처리 데이터 가져오기
		// 1. 받은 사람이 로그인한 사람과 같아야 하고 (받은 메시지) 번호가 같고 받은 날짜가 null인 메시지를 (읽지않은)
//				읽음 표시를 한다.(acceptDate를 현재 날짜로 넣어준다 - update)
		// 2. 메시지 번호에 맞는 전체 메시지 정보 가져오기
		MessageVO viewVO = (MessageVO) ExeService.execute(Beans.get(AuthorityFilter.url), vo); // 그냥 쓰면 믹스매치 에러뜸 -> 캐스팅해라 (MessageVO) 해결
		//↘ Beans.get(AuthorityFilter.url) 이걸안하면  String url = request.getServletPath(); 이걸써야함 
		//url에 들어가보면 url = req.getServletPath(); 라고 설정이 되어있기 때문에 따로 설정해줄 필요없이 AuthorityFilter.url를 사용하면됨

		// 서버객체 request에 담는다.
		request.setAttribute("vo", viewVO); // "vo"는 키값이기 때문에 위에서 셋팅한 vo랑은 상관없음
		
	}
	
	// 3. 메시지 쓰기 처리.
	private void write(HttpServletRequest request) throws Exception{
		
		// 자바 부분
		// 넘어오는 데이터를 수집 - 받는 사람 아이디, 내용
		String accepter = request.getParameter("accepter");
		String content = request.getParameter("content");

		// session에서 내 아이디 가져오기
		// session의 내용은 /member/login.jsp (28번째줄) 확인. 이때 key = login이라는것이 다르면 null이 나온다.  
		LoginVO vo =  (LoginVO) request.getSession().getAttribute("login");
		String sender = vo.getId(); // 아이디를 가져와서 sender에 넣는다.

		// vo 객체를 생성하고 데이터를 넣는다.
		MessageVO messageVO = new MessageVO();
		messageVO.setContent(content);
		messageVO.setSender(sender);
		messageVO.setAccepter(accepter);

		// db 처리 : jsp - serivce - dao -db
		// ExeService.execute(실행할 Service, Service에 전달되는 데이터)
		ExeService.execute(Beans.get(AuthorityFilter.url), messageVO); 
		// ↘ Beans.get(AuthorityFilter.url) 이걸안하면  String url = request.getServletPath(); 이걸써야함 
		// url에 들어가보면 url = req.getServletPath(); 라고 설정이 되어있기 때문에 따로 설정해줄 필요없이 AuthorityFilter.url를 사용하면됨

		// 리스트로 자동 이동
//		response.sendRedirect("list.do");
	}
	
	// 4. 메시지 글삭제 처리
	private void delete(HttpServletRequest request) throws Exception{
		
		// 1. 데이터 수집
		String strNo = request.getParameter("no");
		long no = Long.parseLong(strNo);

		// 2. DB 처리 - delete.jsp -> service -> dao

		ExeService.execute(Beans.get(AuthorityFilter.url), no);

		// 3. list로 자동 이동
//		response.sendRedirect("list.jsp");
	}

}
