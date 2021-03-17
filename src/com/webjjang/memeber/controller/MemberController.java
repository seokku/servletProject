package com.webjjang.memeber.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.multipart.ExceededSizeException;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class MemberController implements Controller {
	
	private final String MODULE = "member";
	private String jspInfo = null;
	
	@Override
	public String execute(HttpServletRequest request) throws Exception{
		System.out.println("MemberController.execute()");
		
		// 페이지를 위한 처리
		PageObject pageObject = PageObject.getInstance(request);
		request.setAttribute("pageObject", pageObject); // 페이지를 보여주기 위해 서버객체에 담는다.
		
		switch (AuthorityFilter.url) {
		// 1. 회원 리스트 처리
		case "/" + MODULE + "/list.do":
			// service - dao --> request에 저장까지 해준다.
			list(request, pageObject);
		
			// "member/list" 넘긴다 -> WEB-INF/views/ + member/list + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/list";
			break;
			
		// 2. 회원 정보 보기
		case "/" + MODULE +"/view.do":
			// service - dao --> request에 저장까지 해준다.
			view(request);
		
			// "member/view" 넘긴다. -> /WEB-INF/views/ + member/view + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/view";			
			break;
					
		// 3. 로그인 처리
		case "/" + MODULE + "/login.do":
			// service - dao --> request에 저장까지 해준다.
			login(request);
		
			// "member/view" 넘긴다 -> WEB-INF/views/ + member/view + .jsp를 이용해서 HTML을 만든다.
			jspInfo = "redirect:/board/list.do";
			break;
			
		// 3-1. 로그인 폼 처리
		case "/" + MODULE + "/loginForm.do":
			// "member/view" 넘긴다 -> WEB-INF/views/ + member/writeForm + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/loginForm";
			break;
			
		// 4. 회원가입 처리
		case "/" + MODULE + "/write.do":
			// service - dao --> request에 저장까지 해준다.
			write(request);
		
		// "member/view" 넘긴다 -> WEB-INF/views/ + member/view + .jsp를 이용해서 HTML을 만든다.
		jspInfo = "redirect:/board/list.do";
		break;
		
		// 4-1. 회원가입 폼 처리
		case "/" + MODULE + "/writeForm.do":
			// "member/view" 넘긴다 -> WEB-INF/views/ + member/writeForm + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/writeForm";
		break;
			
			
		// 4. 로그아웃  처리
		case "/" + MODULE + "/logout.do":
			// "member/view" 넘긴다 -> WEB-INF/views/ + member/writeForm + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/logout";
		break;
		
		
		default:
			throw new Exception("페이지 오류 404 - 존재하지 않는 페이지입니다.");
		}
		// jsp의 정보를 가지고 리턴한다.
		return jspInfo;
		
	}
	// 1. 회원 리스트 처리
	private void list(HttpServletRequest request, PageObject pageObject) throws Exception{
		// 여기가 자바 코드입니다. servlet-controller(*)-Service-DAO
		
		@SuppressWarnings("unchecked")
		List<MemberVO> list = (List<MemberVO>) ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);
		// 서버객체 request에 담는다.
		request.setAttribute("list", list);
					
	}

	// 2. 회원 로그인 처리
	private void login(HttpServletRequest request) throws Exception{
		
		// 여기가 자바 입니다.
		// 로그인 정보를 가져오는데 성공하는 로그인 처리를 한다. 
		// 데이터 받기
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		// 받은 데이터를 VO객체에 저장을 한다.(한개를 넘겨야 해서)
		LoginVO vo = new LoginVO();
		vo.setId(id);
		vo.setPw(pw);

		// DB 처리 - 아이디, 이름, 등급번호, 등급이름을 가져온다. 
		// jsp - service - dao
		//LoginVO loginVO = ExeService.execute(Beans.get(url), vo); 그냥 쓰면 오른쪽 object가 왼쪽 loginVO로 캐스팅이 안되니까 ()로 캐스팅
		LoginVO loginVO = (LoginVO) ExeService.execute(Beans.get(AuthorityFilter.url), vo);

		// id, pw가 틀린 경우의 처리
		if(loginVO == null) throw new Exception("로그인 정보를 확인해 주세요.");

		// 로그인 처리
		request.getSession().setAttribute("login", loginVO);
//		response.sendRedirect("../main/main.jsp");
	}
	
	// 3. 회원 로그아웃 처리.
	private void logout(HttpServletRequest request) throws Exception{
		
		// 여기가 자바 코드입니다. servlet-Controller-Service-DAO -> /member/write.do
		request.getSession().invalidate();
	}
	
	// 4. 회원 정보 보기 
	private void view(HttpServletRequest request) throws Exception{
		
		// 자바 부분입니다.
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute("login");
		System.out.println(loginVO);
		
		String id = loginVO.getId();
		MemberVO vo = (MemberVO) ExeService.execute(Beans.get(AuthorityFilter.url), id);
		
		System.out.println(id);
		request.setAttribute("vo", vo);
	}
	
	// 3. 회원가입 처리.
		private void write(HttpServletRequest request) throws Exception{
			
			// 여기가 자바 코드입니다. servlet-Controller-Service-DAO -> /board/write.do
			
			// 1. 데이터 수집
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			String birth= request.getParameter("birth");
			String tel= request.getParameter("tel");
			String email= request.getParameter("email");

			MemberVO vo = new MemberVO();
			vo.setId(id);
			vo.setPw(pw);
			vo.setName(name);
			vo.setGender(gender);
			vo.setBirth(birth);
			vo.setTel(tel);
			vo.setEmail(email);

			// 2. DB 처리 - write.jsp -> service -> dao
			Integer result = (Integer) ExeService.execute(Beans.get(AuthorityFilter.url), vo);
			System.out.println("BoardController.write().result : " + result); // result는 1이 나오거나 안나와서 예외가 발생되거나
		}
//
//	// 4-2. 게시판 글수정 처리
//	private Long update(HttpServletRequest request) throws Exception {
//		
//		// 1. 데이터 수집
//		String strNo = request.getParameter("no");
//		long no = Long.parseLong(strNo);
//		String title = request.getParameter("title");
//		String content = request.getParameter("content");
//		String writer = request.getParameter("writer");
//
//		MemberVO vo = new MemberVO();
//		vo.setNo(no);
//		vo.setTitle(title);
//		vo.setContent(content);
//		vo.setWriter(writer);
//
//		// 2. DB 처리 - update.jsp -> service -> dao
//		String url = request.getServletPath();
//		Integer result = (Integer) ExeService.execute(Beans.get(url), vo);
//		
//		if(result < 1) throw new Exception("게시판 글수정 - 수정할 데이터가 존재하지 않습니다.");
//		
//		return no;
//
//	}

	// 5. 게시판 글삭제 처리
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
