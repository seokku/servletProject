package com.webjjang.board.vo;

public class BoardReplyVO {
	
	private Long rno, no;
	private String content, writer, writeDate;
	public Long getRno() {
		return rno;
	}
	public void setRno(Long rno) {
		this.rno = rno;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	@Override
	public String toString() {
		return "BoardReplyVO [rno=" + rno + ", no=" + no + ", content=" + content + ", writer=" + writer
				+ ", writeDate=" + writeDate + "]";
	}
	
	
	
}
