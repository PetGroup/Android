package com.ruyicai.activity.info;

import java.util.ArrayList;
import java.util.List;

public class ContentList {
	private String content;
	
	private List<String> contentList = new ArrayList<String>(); //����֮�������list,��Ϊ�ַ�����ע��json,��˳��
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		getOneText(content);
	}

	public List<String> getContentList() {
		return contentList;
	}

	public void setContentList(List<String> contentList) {
		this.contentList = contentList;

	}
	
	/**
	 * ����{ ����}��ȡһ���ַ���
	 * @param str
	 */
	void getOneText(String str){
		if(str == null||str.equals("")){
			return ;
		}
		int index = str.indexOf("{");
		if(index==-1){
			//��{������
			contentList.add(str);
			return;
		}
		int _end = str.indexOf("}");
		if(index == 0){
			//�������{��ͷ��
			contentList.add(str.substring(0,_end+1));
			getOneText(str.substring(_end+1));
		}else{
			contentList.add(str.substring(0,index));
			getOneText(str.substring(index));
		}

	}	

}
