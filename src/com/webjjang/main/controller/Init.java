package com.webjjang.main.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.webjjang.board.controller.BoardController;
import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.service.BoardDeleteService;
import com.webjjang.board.service.BoardListService;
import com.webjjang.board.service.BoardUpdateService;
import com.webjjang.board.service.BoardViewService;
import com.webjjang.board.service.BoardWriteService;
import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.service.ImageDeleteService;
import com.webjjang.image.service.ImageListService;
import com.webjjang.image.service.ImageUpdateFileService;
import com.webjjang.image.service.ImageViewService;
import com.webjjang.image.service.ImageWriteService;
import com.webjjang.member.dao.MemberDAO;
import com.webjjang.member.service.MemberGradeModifyService;
import com.webjjang.member.service.MemberListService;
import com.webjjang.member.service.MemberLoginService;
import com.webjjang.member.service.MemberViewService;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.service.MessageDeleteService;
import com.webjjang.message.service.MessageListService;
import com.webjjang.message.service.MessageViewService;
import com.webjjang.message.service.MessageWriteService;
import com.webjjang.notice.controller.NoticeController;
import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.service.NoticeListService;
import com.webjjang.notice.service.NoticeViewService;
import com.webjjang.notice.service.NoticeWriteService;
import com.webjjang.qna.dao.QnaDAO;
import com.webjjang.qna.service.QnaAnswerService;
import com.webjjang.qna.service.QnaDeleteService;
import com.webjjang.qna.service.QnaListService;
import com.webjjang.qna.service.QnaQuestionService;
import com.webjjang.qna.service.QnaViewService;

/**
 * Servlet implementation class Init
 */
@WebServlet(value = "/Init", loadOnStartup = 1)
public class Init extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Init() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("jspProject2에서의 실행--------->>>");
		System.out.println("서버가 실행을 시작할때 실행되는 처리-----");
		
		// 게시판 객체를 생성 후 저장 ====================================
		// controller 생성 저장
		Beans.putController("/board", new BoardController());
		
		// dao 생성 저장
		Beans.putDAO("boardDAO", new BoardDAO());
		
		// service 생성 저장
		Beans.put("/board/list.do", new BoardListService());
		Beans.put("/board/view.do", new BoardViewService());
		Beans.put("/board/write.do", new BoardWriteService());
		Beans.put("/board/update.do", new BoardUpdateService());
		Beans.put("/board/delete.do", new BoardDeleteService());
		
		// service에 dao 넣기 - 조립
		Beans.get("/board/list.do").setDAO(Beans.getDAO("boardDAO"));
		Beans.get("/board/view.do").setDAO(Beans.getDAO("boardDAO"));
		Beans.get("/board/write.do").setDAO(Beans.getDAO("boardDAO"));
		Beans.get("/board/update.do").setDAO(Beans.getDAO("boardDAO"));
		Beans.get("/board/delete.do").setDAO(Beans.getDAO("boardDAO"));
		
		
		// 공지사항 객체를 생성 후 저장 ====================================
		// controller 생성 저장
		Beans.putController("/notice", new NoticeController());
		// dao 생성 저장
		Beans.putDAO("noticeDAO", new NoticeDAO());
		
		// service 생성 저장
		Beans.put("/notice/list.do", new NoticeListService());
		Beans.put("/notice/write.do", new NoticeWriteService());
		Beans.put("/notice/view.do", new NoticeViewService());
		// service에 dao 넣기 - 조립
		Beans.get("/notice/list.do").setDAO(Beans.getDAO("noticeDAO"));
		Beans.get("/notice/write.do").setDAO(Beans.getDAO("noticeDAO"));
		Beans.get("/notice/view.do").setDAO(Beans.getDAO("noticeDAO"));
		
		// 이미지 객체를 생성 후 저장 ====================================
		// dao 생성 저장
		Beans.putDAO("imageDAO", new ImageDAO());
		// service 생성 저장
		Beans.put("/image/list.do", new ImageListService());
		Beans.put("/image/write.do", new ImageWriteService());
		Beans.put("/image/view.do", new ImageViewService());
		Beans.put("/image/updateFile.do", new ImageUpdateFileService());
		Beans.put("/image/delete.do", new ImageDeleteService());
		// service에 dao 넣기 - 조립
		Beans.get("/image/list.do").setDAO(Beans.getDAO("imageDAO"));
		Beans.get("/image/write.do").setDAO(Beans.getDAO("imageDAO"));
		Beans.get("/image/view.do").setDAO(Beans.getDAO("imageDAO"));
		Beans.get("/image/updateFile.do").setDAO(Beans.getDAO("imageDAO"));
		Beans.get("/image/delete.do").setDAO(Beans.getDAO("imageDAO"));
		
		
		// 질문답변 객체를 생성 후 저장 ====================================
		// dao 생성 저장
		Beans.putDAO("qnaDAO", new QnaDAO());
		// service 생성 저장
		Beans.put("/qna/list.do", new QnaListService());
		Beans.put("/qna/question.do", new QnaQuestionService());
		Beans.put("/qna/view.do", new QnaViewService());
		Beans.put("/qna/answer.do", new QnaAnswerService());
		Beans.put("/qna/delete.do", new QnaDeleteService());
		// service에 dao 넣기 - 조립
		Beans.get("/qna/list.do").setDAO(Beans.getDAO("qnaDAO"));
		Beans.get("/qna/question.do").setDAO(Beans.getDAO("qnaDAO"));
		Beans.get("/qna/view.do").setDAO(Beans.getDAO("qnaDAO"));
		Beans.get("/qna/answer.do").setDAO(Beans.getDAO("qnaDAO"));
		Beans.get("/qna/delete.do").setDAO(Beans.getDAO("qnaDAO"));
		
		
		// 메시지 객체를 생성 후 저장 ====================================
		// dao 생성 저장
		// 1. MessageDAO를 생성해서 2. key = "messageDAO"로 Beans에 저장해 놓는다. 
		Beans.putDAO("messageDAO", new MessageDAO());
		// service 생성 저장
		// Beans에서 key="/message/list.do"로 저장해 놓은것은 꺼낸다. --> MessageListService 객체를 꺼낸다.
		Beans.put("/message/list.do", new MessageListService());
		// Beans에서 key="/message/write.do"로 저장해 놓은것은 꺼낸다. --> MessageWriteService 객체를 꺼낸다.
		Beans.put("/message/write.do", new MessageWriteService());
		// Beans에서 key="/message/view.do"로 저장해 놓은것은 꺼낸다. --> MessageViewService 객체를 꺼낸다.
		Beans.put("/message/view.do", new MessageViewService());
		// Beans에서 key="/message/view.do"로 저장해 놓은것은 꺼낸다. --> MessageViewService 객체를 꺼낸다.
		Beans.put("/message/delete.do", new MessageDeleteService());
		
		// service에 dao 넣기 - 조립
		Beans.get("/message/list.do").setDAO(Beans.getDAO("messageDAO"));
		// Beans에서 key="/message/write.do"로 저장해 놓은것은 꺼낸다. --> MessageWriteService 객체를 꺼낸다.
		// Beans에서 key="message.DAO"로 저장해 놓은것은 꺼낸다. --> MessageDAO 객체를 꺼낸다.
		// 주의 : key가 다르면 null이 나온다. -> NullPointException이 나온다. + 대소문자를 잘 구분해야한다.
		Beans.get("/message/write.do").setDAO(Beans.getDAO("messageDAO"));
		// Beans에서 key="/message/view.do"로 저장해 놓은것은 꺼낸다. --> MessageViewService 객체를 꺼낸다.
		// Beans에서 key="message.DAO"로 저장해 놓은것은 꺼낸다. --> MessageDAO 객체를 꺼낸다.
		// 주의 : key가 다르면 null이 나온다. -> NullPointException이 나온다. + 대소문자를 잘 구분해야한다.
		Beans.get("/message/view.do").setDAO(Beans.getDAO("messageDAO"));
		// Beans에서 key="/message/view.do"로 저장해 놓은것은 꺼낸다. --> MessageViewService 객체를 꺼낸다.
		// Beans에서 key="message.DAO"로 저장해 놓은것은 꺼낸다. --> MessageDAO 객체를 꺼낸다.
		// 주의 : key가 다르면 null이 나온다. -> NullPointException이 나온다. + 대소문자를 잘 구분해야한다.
		Beans.get("/message/delete.do").setDAO(Beans.getDAO("messageDAO"));
		
		
		// 회원 관리 객체를 생성 후 저장 ====================================
		// dao 생성 저장
		Beans.putDAO("memberDAO", new MemberDAO());
		
		// service 생성 저장
		Beans.put("/member/login.do", new MemberLoginService());
		Beans.put("/member/list.do", new MemberListService());
		Beans.put("/member/gradeModify.do", new MemberGradeModifyService());
		System.out.println("Init.init().Beans.get(\"/member/gradeModify.do\") : " 
		+ Beans.get("/member/gradeModify.do"));
		
		Beans.put("/member/view.do", new MemberViewService());
		System.out.println("Init.init().Beans.get(\"/member/view.do\") : " 
				+ Beans.get("/member/view.do"));

		// service에 dao 넣기 - 조립
		Beans.get("/member/login.do").setDAO(Beans.getDAO("memberDAO"));
		Beans.get("/member/list.do").setDAO(Beans.getDAO("memberDAO"));
		System.out.println("Init.init().Beans.get(\"memberDAO\") : " 
				+ Beans.getDAO("memberDAO"));
		Beans.get("/member/gradeModify.do").setDAO(Beans.getDAO("memberDAO"));
		
		System.out.println("Init.init().Beans.get(\"memberDAO\") : " 
				+ Beans.getDAO("memberDAO"));
		Beans.get("/member/view.do").setDAO(Beans.getDAO("memberDAO"));
		
		// 생성 저장이 잘되어 있는지 확인
		System.out.println(Beans.get("/board/list.do"));
		System.out.println(Beans.getDAO("boardDAO"));
		
		// 오라클 드라이버와 필요한 메서드 로딩
		try {
			// class 안에 있는 static 부분이 로딩 되고 static{} 초기화 블록이 실행됨.
			Class.forName("com.webjjang.util.db.DBInfo");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("드라이버 확인하는 처리 중 오류 발생");
		}
	}

}
