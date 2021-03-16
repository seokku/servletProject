package com.webjjang.message.vo;

public class MessageVO {
	
	  private long no;
	  private String content;
	  private String sender;
	  private String senderName;
	  private String sendDate;
	  private String accepter;
	  private String accepterName;
	  private String acceptDate;
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getAccepter() {
		return accepter;
	}
	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	public String getAccepterName() {
		return accepterName;
	}
	public void setAccepterName(String accepterName) {
		this.accepterName = accepterName;
	}
	public String getAcceptDate() {
		return acceptDate;
	}
	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}
	@Override
	public String toString() {
		return "MessageVO [no=" + no + ", content=" + content + ", sender=" + sender + ", senderName=" + senderName
				+ ", sendDate=" + sendDate + ", accepter=" + accepter + ", accepterName=" + accepterName
				+ ", acceptDate=" + acceptDate + "]";
	}

	  
}
