package com.ruyicai.net.newtransaction.pojo;
/**<b>
 * Ͷע��ѯ<br>
 * �н���ѯ <br>
 * ׷�Ų�ѯ<br>
 * ���Ͳ�ѯ��pojo��</b><br>
 * userno	           �û����	��¼�ɹ����ص��û������00000001<br>
 * pageindex	��ǰҳ<br>
 * maxresult	ÿҳ��������<br>
 * lotno		����	����<br>
 * sessionid	sessionid<br>	
 * batchcode	�ں�	�ں�<br>
 * type			��ѯ����	type  �н���ѯΪwin<br>
 * command		����	command:QueryLot<br>
 * @author miao
 */
public class BetAndWinAndTrackAndGiftQueryPojo {
	private String userno = "";
	private String pageindex = "";
	private String maxresult = "";
	private String lotno = "";
	private String type = "";
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPageindex() {
		return pageindex;
	}

	public void setPageindex(String pageindex) {
		this.pageindex = pageindex;
	}

	public String getMaxresult() {
		return maxresult;
	}

	public void setMaxresult(String maxresult) {
		this.maxresult = maxresult;
	}

	private String sessionid = "";
	private String batchcode = "";
	private String phonenum = "";

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getLotno() {
		return lotno;
	}

	public void setLotno(String lotno) {
		this.lotno = lotno;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

}
