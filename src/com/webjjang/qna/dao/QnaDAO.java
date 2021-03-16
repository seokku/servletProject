package com.webjjang.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.qna.vo.QnaVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.db.DBInfo;
import com.webjjang.util.db.DBSQL;

public class QnaDAO {
	
	
	// 필요한 객체 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 1. qna list
	public List<QnaVO> list(PageObject pageObject) throws Exception {

		List<QnaVO> list = null;
		
		try {
			// 1.2.
			con = DBInfo.getConnection();
			// 3.4.
			pstmt = con.prepareStatement(DBSQL.QNA_LIST);
			pstmt.setLong(1, pageObject.getStartRow());
			pstmt.setLong(2, pageObject.getEndRow());
			// 5.
			rs = pstmt.executeQuery();
			// 6.
			if(rs !=null) {
				while(rs.next()) {
					if(list == null) list = new ArrayList<>();
					QnaVO vo = new QnaVO();
					// 번호, 제목, 작성자이름(작성자ID), 작성일, 조회수, 들여쓰기
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setName(rs.getString("name"));
					vo.setId(rs.getString("id"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setHit(rs.getLong("hit"));
					vo.setLevNo(rs.getLong("levNo"));
					System.out.println("QnaDAO.list().vo : " + vo);
					list.add(vo);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("질문 답변 리스트 DB 처리 중 오류");
		} finally {
			DBInfo.close(con, pstmt, rs);
		}
		
		return list;
	}
	
	
	// 1-1. getTotalRow - 전체 데이터 갯수 가져오기
		public Long getTotalRow() throws Exception {
			Long result = 0L;
			
			try {
				// 1.2.
				con = DBInfo.getConnection();
				// 3.4.
				pstmt = con.prepareStatement(DBSQL.QNA_GET_TOTALROW);
				// 5.
				rs = pstmt.executeQuery();
				// 6.
				if(rs !=null && rs.next()) {
						result = rs.getLong(1); // 첫번째있는 데이터를 꺼내자.
					}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new Exception("공지사항 전체 데이터 가져오기 DB 처리 중 오류");
			} finally {
				DBInfo.close(con, pstmt, rs);
			}
			
			return result;
		} // end of getTotalRow
		
		// 1-2.질문 답변 보기 - view
		public QnaVO view(Long no) throws Exception {
			QnaVO vo= null;
			
			try {
				// 1.2.
				con = DBInfo.getConnection();
				// 3.4.
				pstmt = con.prepareStatement(DBSQL.QNA_VIEW);
				pstmt.setLong(1, no);
				// 5.
				rs = pstmt.executeQuery();
				// 6.
				if(rs !=null && rs.next()) {
					vo = new QnaVO();
					// no, q.title, q.content, m.name, q.id, q.writeDate, q.hit
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setContent(rs.getString("content"));
					vo.setName(rs.getString("name"));
					vo.setId(rs.getString("id"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setHit(rs.getLong("hit"));
					// 글보기 할때는 관련글번호, 순서번호, 들여쓰기 정보를 필요로 하지 않아서 버린다.
					// 답변 쓰기 할때는 이러한 정보가 필요하다
					vo.setRefNo(rs.getLong("refNo"));
					vo.setOrdNo(rs.getLong("ordNo"));
					vo.setLevNo(rs.getLong("levNo"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new Exception("질문 답변 보기  DB 처리 중 오류");
			} finally {
				DBInfo.close(con, pstmt, rs);
			}
			
			return vo;
		} // end of getTotalRow
		
		// 2. 조회수 1증가
		public int increase(Long no)  throws Exception {
			int result = 0;
			
			try {
				// 1.2.
				con = DBInfo.getConnection();
				// 3.4.
				pstmt = con.prepareStatement(DBSQL.QNA_INCREASE);
				pstmt.setLong(1, no);
				// 5.
				result = pstmt.executeUpdate();
				// 6.
				System.out.println("조회수 1증가 완료");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new Exception("질문 답변 보기의 조회수 1증가 DB 처리 중 오류");
			} finally {
				DBInfo.close(con, pstmt);
			}
			
			return result;
		} // end of increase
		
	// 3-1. 질문하기 - question : 제목, 내용 -> 사용자 입력 / 아이디 -> session의 로그인 정보 : JSP에서 수집
		public int question(QnaVO vo) throws Exception {
			int result = 0;
			
			try {
				// 1.2.
				con = DBInfo.getConnection();
				// 3.4.
				pstmt = con.prepareStatement(DBSQL.QNA_QUESTION);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setString(3, vo.getId());
				// 5.
				result = pstmt.executeUpdate();
				// 6.
				System.out.println("QnaDAO.question() - 질문 등록 완료루레이후~");
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new Exception("질문 등록 중 DB 처리 중 오류");
			} finally {
				DBInfo.close(con, pstmt);
			}
			
			return result;
		} // end of getTotalRow
		
		// 3-2. 답변하기 - answer : 제목, 내용 -> 사용자 입력 / 아이디 -> session의 로그인 정보 : JSP에서 수집
		// 관련글번호, 순서번호, 들여쓰기, 부모글번호  -> 전달받는다.
		public int answer(QnaVO vo) throws Exception {
			int result = 0;
			
			try {
				// 1.2.
				con = DBInfo.getConnection();
				// 3.4.
				pstmt = con.prepareStatement(DBSQL.QNA_ANSWER);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setString(3, vo.getId());
				pstmt.setLong(4, vo.getRefNo());
				pstmt.setLong(5, vo.getOrdNo());
				pstmt.setLong(6, vo.getLevNo());
				pstmt.setLong(7, vo.getParentNo());
				// 5.
				result = pstmt.executeUpdate();
				// 6.
				System.out.println("QnaDAO.answer() - 답변 등록 완료루레이후~");
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new Exception("질문 답변 중 DB 처리 중 오류");
			} finally {
				DBInfo.close(con, pstmt);
			}
			
			return result;
		} // end of getTotalRow
		// 3-3. 답변하기 순서번호 1증가
				public int ordNoIncrease(QnaVO vo)  throws Exception {
					int result = 0;
					
					try {
						// 1.2.
						con = DBInfo.getConnection();
						// 3.4.
						pstmt = con.prepareStatement(DBSQL.QNA_ANSWER_INCREASE_ORDNO);
						pstmt.setLong(1, vo.getRefNo());
						pstmt.setLong(2, vo.getOrdNo());
						// 5.
						result = pstmt.executeUpdate();
						// 6.
						System.out.println("답변하기 순서번호 1증가 완료");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						throw new Exception("질문 답변 답변하기 순서번호 1증가 DB 처리 중 오류");
					} finally {
						DBInfo.close(con, pstmt);
					}
					
					return result;
				} // end of increase
		// 5. 질문답변 글삭제======================================================
		public int delete(long no) throws Exception {
			int result = 0;
			try {
				// 1. 확인 2. 연결
				con = DBInfo.getConnection();
				// 3. sql . 4.실행&데이터셋팅
				pstmt = con.prepareStatement(DBSQL.QNA_DELETE);
				pstmt.setLong(1, no);
				// 5. 실행
				result = pstmt.executeUpdate();
				// 6. 출력
				if(result == 1)
					System.out.println("질문답변 글 삭제에 성공하셨습니다.");
				else
					System.out.println("삭제하려는 글의 정보를 확인하세요.");
				
			}catch (Exception e) {
				// TODO: handle exception
				// 개발자를 위한 예외출력(500) -> 콘솔
				e.printStackTrace();
				// 사용자를 위한 예외처리. -> jsp까지 전달된다.
				throw new Exception("질문답변 삭제 DB 처리 중 오류 발생.");
			} finally {
				DBInfo.close(con, pstmt);
			}
			return result;
		}
		
		
		
}
