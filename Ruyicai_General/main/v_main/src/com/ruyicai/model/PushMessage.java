package com.ruyicai.model;

/**
 * 
 * 推送通知body 内容
 * @author anfangshuo
 *
 */
public class PushMessage {

	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	private String pushpage;
	public String getPushpage() {
		return pushpage;
	}
	public void setPushpage(String pushpage) {
		this.pushpage = pushpage;
	}
	private String pushvalue;
	public String getPushvalue() {
		return pushvalue;
	}
	public void setPushvalue(String pushvalue) {
		this.pushvalue = pushvalue;
	}

}
