package com.webjjang.image.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.image.vo.ImageVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.db.DBInfo;
import com.webjjang.util.db.DBSQL;

public class ImageDAO {
	
	// 필요한 객체 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 1. 이미지 리스트
	public List<ImageVO> list(PageObject pageObject) throws Exception{
		
		// 넘어오는 데이터 확인
		System.out.println("ImageDAO.list().pageObject : " + pageObject);
		
		
		List<ImageVO> list = null;
		
		try {
			//1. 드라이버 확인 + 2. 연결
			con = DBInfo.getConnection();
			System.out.println("ImageDAO.list().con : " + con);
			//3. sql -> DBSQL + 4. 실행객체 + 데이터 셋팅
			System.out.println("ImageDAO.list().DBSQL.IMAGE_LIST : " + DBSQL.IMAGE_LIST);
			pstmt = con.prepareStatement(DBSQL.IMAGE_LIST);
			System.out.println("ImageDAO.list().pstmt : " + pstmt);
			pstmt.setLong(1, pageObject.getStartRow()); // 시작 번호
			pstmt.setLong(2, pageObject.getEndRow()); // 끝 번호
			// 5. 실행
			rs = pstmt.executeQuery();
			System.out.println("ImageDAO.list().rs : " + rs);
			// 6. 표시 -> 데이터담기
			if(rs != null) {
				while(rs.next()) {
					if(list == null) list = new ArrayList<ImageVO>();
					ImageVO vo = new ImageVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setName(rs.getString("name"));
					vo.setId(rs.getString("id"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setFileName(rs.getString("fileName"));
					list.add(vo);
//					System.out.println("ImageDAO.list().while().vo : " + vo);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			// 개발자를 위해서 오류를 콘솔에 출력한다.
			e.printStackTrace();
			// 사용자를 위한 오류 처리
			throw new Exception("이미지 리스트 실행 중 DB 처리 오류");
		} finally {
			// 7. 닫기
			DBInfo.close(con, pstmt, rs);
		}
		System.out.println("ImageDAO.list().list : " + list);
		return list;
	}
	
	// 1-1. 이미지 전체 데이터 갯수
	public long getTotalRow() throws Exception{
	System.out.println("ImageDAO.getTotalRow()");
	long result = 0;
	
	try {
		// 1.2.
		con = DBInfo.getConnection();
		System.out.println("ImageDAO.getTotalRo().con : " + con);
		// 3.4.
		// 쿼리 확인
		System.out.println("ImageDAO.getTotalRo().DBSQL.IMAGE_GET_TOTALROW : " + DBSQL.IMAGE_GET_TOTALROW);
		pstmt = con.prepareStatement(DBSQL.IMAGE_GET_TOTALROW);
		System.out.println("ImageDAO.getTotalRo().pstmt : " + pstmt);
		// 5.
		// rs는 출력해 볼 수 있다. 그러나 rs.next() 출력하면 데이터를 한개 넘기게 된다.
		rs = pstmt.executeQuery();
		System.out.println("ImageDAO.getTotalRo().rs : " + rs);
		// 6.
		if(rs != null && rs.next()) {
			result = rs.getLong(1);
			System.out.println("ImageDAO.getTotalRo().result : " + result);
		}
	} catch(Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		throw new Exception("이미지 데이터 전체 갯수를 가져오는 DB 처리 중 오류");
	} finally {
		DBInfo.close(con, pstmt, rs);
	}
	System.out.println("ImageDAO.getTotalRo().result : " + result);
	return result;
}
	
	// 2. 이미지 보기
	public ImageVO view(Long no) throws Exception{
		System.out.println("ImageDAO.getTotalRow()");
		ImageVO vo = null;
		
		try {
			// 1.2.
			con = DBInfo.getConnection();
			System.out.println("ImageDAO.view().con : " + con);
			// 3.4.
			// 쿼리 확인
			System.out.println("ImageDAO.view().DBSQL.IMAGE_VIEW : " + DBSQL.IMAGE_VIEW);
			pstmt = con.prepareStatement(DBSQL.IMAGE_VIEW);
			pstmt.setLong(1, no);
			System.out.println("ImageDAO.view().pstmt : " + pstmt);
			// 5.
			// rs는 출력해 볼 수 있다. 그러나 rs.next() 출력하면 데이터를 한개 넘기게 된다.
			rs = pstmt.executeQuery();
			System.out.println("ImageDAO.view().rs : " + rs);
			// 6.
			if(rs != null && rs.next()) {
				vo = new ImageVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("id"));
				vo.setWriteDate(rs.getString("writeDate"));
				vo.setFileName(rs.getString("fileName"));
				System.out.println("ImageDAO.view().vo : " + vo);
			}
		} catch(Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("이미지 게시판 이미지 보기 DB 처리 중 오류");
		} finally {
			DBInfo.close(con, pstmt, rs);
		}
		System.out.println("ImageDAO.view().vo : " + vo);
		return vo;
	}

	// 3. 이미지 등록
	public int write(ImageVO vo) throws Exception {
		int result = 0;
		try {
			// 1.2.
			con = DBInfo.getConnection();
			// 3.4.
			pstmt = con.prepareStatement(DBSQL.IMAGE_WRITE);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getId());
			pstmt.setString(4, vo.getFileName());
			// 5.
			result = pstmt.executeUpdate();
			// 6.
			System.out.println("ImageDAO.write() - 이미지 등록 완료 ");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("이미지 등록 DB 처리 중 오류");
		} finally {
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	
	// 4. 이미지 파일 정보 수정 - 번호 , 파일명
	public int updateFile(ImageVO vo) throws Exception {
		int result = 0;
		try {
			// 1.2.
			con = DBInfo.getConnection();
			// 3.4.
			pstmt = con.prepareStatement(DBSQL.IMAGE_UPDATE_FILE);
			pstmt.setString(1, vo.getFileName());
			pstmt.setLong(2, vo.getNo());
			// 5.
			result = pstmt.executeUpdate();
			// 6.
			System.out.println("ImageDAO.write() - 이미지 파일 수정 완료 ");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("이미지 파일 수정 DB 처리 중 오류");
		} finally {
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	
	// 5. 이미지 게시판 삭제
	public int delete(Long no) throws Exception {
		int result = 0;
		try {
			// 1.2.
			con = DBInfo.getConnection();
			// 3.4.
			pstmt = con.prepareStatement(DBSQL.IMAGE_DELETE);
			pstmt.setLong(1, no);
			// 5.
			result = pstmt.executeUpdate();
			// 6.
			System.out.println("ImageDAO.delete() - 이미지 게시판 삭제 완료 ");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("이미지 게시판 삭제 수정 DB 처리 중 오류");
		} finally {
			DBInfo.close(con, pstmt);
		}
		return result;
	}

}
