package com.ruyicai.model;

public class PayLoadBean {
	private String msg;
	private String messageid;
	private String type;
	private String thumb;
	private String title;
	private String zanNum;
	private String commentNum;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getZanNum() {
		return zanNum;
	}
	public void setZanNum(String zanNum) {
		this.zanNum = zanNum;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	@Override
	public String toString() {
		return "PayLoadBean [msg=" + msg + ", messageid=" + messageid
				+ ", type=" + type + ", thumb=" + thumb + ", title=" + title
				+ ", zanNum=" + zanNum + ", commentNum=" + commentNum + "]";
	}
	
}
