package com.webjjang.main.controller;

import java.io.IOException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webjjang.util.filter.AuthorityFilter;

/**
 * Servlet implementation class DispacherServlet
 */
//@WebServlet("/DispacherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 이곳에서 처리해야할 모든 URL(*.do)을 받도록 설정 -> web.xml
		System.out.println("DispatcherServlet.service()");		
		
		// /board/list.do - /board : substring(0, 6 -> 이렇게 사용가능(indexOf("/",1)) ---- 맨앞에 / 빼고 그뒤로부터 6번째부터 찾으라는 말, 맨뒤에 숫자가 거기서부터 찾자
		// /qna/list.do /qna : substring(0, 4)
		String module = AuthorityFilter.url.substring(0, AuthorityFilter.url.indexOf("/", 2));
		
		System.out.println("DispatcherServlet.service.module : " + module);
		
//		// 요청한 url을 처리해서 출력
//		String url = request.getServletPath();
//		System.out.println("DispatcherServlet.service().url - " + url);
		
		
		try {
			// "/board"로 시작을 하면 BoradController가 실행이 되게 한다.
			//  "/board"로 시작을 하면 --> url.indexOf("/board") == 0
			Controller controller = Beans.getController(module);
			if(controller == null) throw new Exception("Error 404 - 요청하신 URL이 존재하지 않습니다.");
			
			String jspInfo = controller.execute(request);
			
			// sendRedirect를 하려면 리턴되는 되는 문자열 앞에 "redirect:"를 붙여준다.
			if(jspInfo.indexOf("redirect:") == 0) {
				// "redirect:list.do" -> jspInfo.substring("redirect:".length()) -> list.do (길이가 9째가 : 임. 그뒤로 짤라내서 list.do)
				jspInfo = jspInfo.substring("redirect:".length());
				response.sendRedirect(jspInfo); // 9번째 뒤에 떼어낸 list.애를 jspInfo에 집어넣는다.
			// "redirect:" 이 없으면 jsp로 forward 된다.
			} else {
				request.getRequestDispatcher("/WEB-INF/views/" + jspInfo + ".jsp")
				.forward(request, response);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("exception", e);
			// forward 시킨 내용의 url은 변경이 되지 않는다.
			request.getRequestDispatcher("/WEB-INF/views/error/error_page.jsp")
			.forward(request, response);
			System.out.println("DispatcherServlet.service() - 예외 발생 처리");

		}
	} 
}
