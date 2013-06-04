/**
 * 
 */
package com.ruyicai.activity.notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.NoticeInterface;
import com.ruyicai.net.newtransaction.NoticeWinInterface;

/**
 * �������б�
 * @author Administrator
 *
 */
public class NoticeActivityGroup extends BuyActivityGroup{

	public static int LOTNO=2;
	public static String ISSUE;//��ǰ��
	public final static int ID_SUB_SHUANGSEQIU_LISTVIEW = 2;
	public final static int ID_SUB_FUCAI3D_LISTVIEW = 3;
	public final static int ID_SUB_QILECAI_LISTVIEW = 4;
	public final static int ID_SUB_PAILIESAN_LISTVIEW = 5; // zlm 8.9 ������ӣ�������
	public final static int ID_SUB_DLC_LISTVIEW = 6; // zlm 8.9
	public final static int ID_SUB_SHISHICAI_LISTVIEW = 7; // zlm 8.9
	public final static int ID_SUB_SFC_LISTVIEW = 8; // zlm 8.9
	public final static int ID_SUB_RXJ_LISTVIEW = 9; // zlm 8.9
	public final static int ID_SUB_LCB_LISTVIEW = 10; // zlm 8.9
	public final static int ID_SUB_JQC_LISTVIEW = 11; // zlm 8.9
	public final static int ID_SUB_DLT_LISTVIEW = 12; // zlm 8.9
	public final static int ID_SUB_PL5_LISTVIEW = 13;
	public final static int ID_SUB_QXC_LISTVIEW = 14;
	public final static int ID_SUB_YDJ_LISTVIEW = 15;
	public final static int ID_SUB_TWENTY_LISTVIEW = 16;//22
	public final static String PRIZE = "���¿���";
	public final static String PRIZE_INFO = "���¿�������";
	public final static int SIZE = 16;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        isIssue(false);
        initView(LOTNO);
	}
	/**
	 * �������������б������б�֮�����ת
	 * @param listViewID
	 *            �б�ID
	 */
	private void initView(int listViewID) {
		switch (listViewID) {
		case NoticeActivityGroup.ID_SUB_SHUANGSEQIU_LISTVIEW:
			String[] topTitles1 ={PRIZE_INFO,"˫ɫ�򿪽�����","˫ɫ�򿪽�����","˫ɫ�򿪽�����"};
			String[] titles1 ={PRIZE,"��������","��������","��������"};
			Class[] allId1 ={NewNoticeInfoActivity.class,NoticeRedBallActivity.class,NoticeBlueBallActivity.class,NoticeInfoActivity.class};
			init(titles1, topTitles1, allId1,SIZE);
			
			break;
		case NoticeActivityGroup.ID_SUB_FUCAI3D_LISTVIEW:
			String[] topTitles2 ={PRIZE_INFO,"����3D��������","����3D��������"};
			String[] titles2 ={PRIZE,"��������","��������"};
			Class[] allId2 ={NewNoticeInfoActivity.class,NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titles2, topTitles2, allId2);
			break;
		case NoticeActivityGroup.ID_SUB_QILECAI_LISTVIEW:
			String[] topTitles3 ={PRIZE_INFO,"���ֲʿ�������","���ֲʿ�������","���ֲʿ�������"};
			String[] titles3 ={PRIZE,"��������","��������","��������"};
			Class[] allId3 ={NewNoticeInfoActivity.class,NoticeRedBallActivity.class,NoticeBlueBallActivity.class,NoticeInfoActivity.class};
			init(titles3, topTitles3, allId3,SIZE);
			break;
		case NoticeActivityGroup.ID_SUB_PAILIESAN_LISTVIEW:
			String[] topTitles4 ={PRIZE_INFO,"��������������","��������������"};
			String[] titles4 ={PRIZE,"��������","��������"};
			Class[] allId4 ={NewNoticeInfoActivity.class,NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titles4, topTitles4, allId4);
			break;
		case NoticeActivityGroup.ID_SUB_DLT_LISTVIEW:
			String[] topTitles5 ={PRIZE_INFO,"����͸��������","����͸��������","����͸��������"};
			String[] titles5 ={PRIZE,"��������","��������","��������"};
			Class[] allId5 ={NewNoticeInfoActivity.class,NoticeRedBallActivity.class,NoticeBlueBallActivity.class,NoticeInfoActivity.class};
			init(titles5, topTitles5, allId5,SIZE);
			break;
		case NoticeActivityGroup.ID_SUB_SHISHICAI_LISTVIEW:
			String[] topTitles6 ={"ʱʱ�ʿ�������"};
			String[] titles6 ={"��������"};
			Class[] allId6 ={NoticeInfoActivity.class};
			init(titles6, topTitles6, allId6);
			break;
		case NoticeActivityGroup.ID_SUB_DLC_LISTVIEW:
			String[] topTitles7 ={"11ѡ5��������","11ѡ5��������"};
			String[] titles7 ={"�����ֲ�","��������"};
			Class[] allId7 ={NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titles7, topTitles7, allId7);
			break;
		case NoticeActivityGroup.ID_SUB_YDJ_LISTVIEW:
			String[] topTitles15 ={"11�˶�𿪽�����","11�˶�𿪽�����"};
			String[] titles15 ={"�����ֲ�","��������"};
			Class[] allId15 ={NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titles15, topTitles15, allId15);
			break;
		case NoticeActivityGroup.ID_SUB_TWENTY_LISTVIEW:
			String[] topTitles16 ={PRIZE_INFO,"22ѡ5�����ֲ���������","22ѡ5��������"};
			String[] titles16 ={PRIZE,"�����ֲ�","��������"};
			Class[] allId16 ={NewNoticeInfoActivity.class,NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titles16, topTitles16, allId16);
			break;
		case NoticeActivityGroup.ID_SUB_SFC_LISTVIEW:
			String[] topTitles8 ={"ʤ���ʿ�������"};
			String[] titles8 ={"��������"};
			Class[] allId8 ={NoticeInfoActivity.class};
			init(titles8, topTitles8, allId8);
			break;
		case NoticeActivityGroup.ID_SUB_RXJ_LISTVIEW:
			String[] topTitles9 ={"��ѡ�ſ�������"};
			String[] titles9 ={"��������"};
			Class[] allId9 ={NoticeInfoActivity.class};
			init(titles9, topTitles9, allId9);
			break;
		case NoticeActivityGroup.ID_SUB_LCB_LISTVIEW:
			String[] topTitles10 ={"�����뿪������"};
			String[] titles10 ={"��������"};
			Class[] allId10 ={NoticeInfoActivity.class};
			init(titles10, topTitles10, allId10);
			break;
		case NoticeActivityGroup.ID_SUB_JQC_LISTVIEW:
			String[] topTitles11 ={"����ʿ�������"};
			String[] titles11 ={"��������"};
			Class[] allId11 ={NoticeInfoActivity.class};
			init(titles11, topTitles11, allId11);
			break;
		case NoticeActivityGroup.ID_SUB_PL5_LISTVIEW:
			String[] topTitlespl5 ={PRIZE_INFO,"�����忪������","�����忪������"};
			String[] titlespl5 ={PRIZE,"��������","��������"};
			Class[] allIdpl5 ={NewNoticeInfoActivity.class,NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titlespl5, topTitlespl5, allIdpl5);
			break;
		case NoticeActivityGroup.ID_SUB_QXC_LISTVIEW:
			String[] topTitlesqxc ={PRIZE_INFO,"���ǲʿ�������","���ǲʿ�������"};
			String[] titlesqxc ={PRIZE,"��������","��������"};
			Class[] allIdqxc ={NewNoticeInfoActivity.class,NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titlesqxc, topTitlesqxc, allIdqxc);
			break;
		}
	}

	/**
	 * ����ȡ���Ŀ�����Ϣ�ŵ���������
	 */
	public static void analysisLotteryNoticeJsonObject(JSONObject jobject){
		
		//˫ɫ����Ϣ��ȡ
		try {
		    JSONArray ssqArray = jobject.getJSONArray("ssq");
		    if(ssqArray!=null&&ssqArray.length()>0){
		    	NoticeMainActivity.isFirstNotice = false;
				Constants.ssqNoticeList.clear();
				for (int i = 0; i < ssqArray.length(); i++) {
					JSONObject _ssq = (JSONObject) ssqArray.get(i);
					Constants.ssqNoticeList.add(_ssq);
				}
			}
		}catch(Exception e){
			//��ȡ˫ɫ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.ssqNoticeList==null||Constants.ssqNoticeList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00000000000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.ssqNoticeList.add(tempObj);
			}
		}
		try {
			JSONArray fc3dArray = jobject.getJSONArray("ddd");
			if(fc3dArray!=null&&fc3dArray.length()>0){
				Constants.fc3dList.clear();
				for (int i = 0; i < fc3dArray.length(); i++) {
					JSONObject _fc3d = (JSONObject) fc3dArray.get(i);
					Constants.fc3dList.add(_fc3d);
				}
			}
		}catch(Exception e){
			//��ȡ����3D���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.fc3dList==null||Constants.fc3dList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.fc3dList.add(tempObj);
			}
		}
		
		try {
			JSONArray qlcArray = jobject.getJSONArray("qlc");
			if(qlcArray!=null&&qlcArray.length()>0){
				Constants.qlcList.clear();
				for (int i = 0; i < qlcArray.length(); i++) {
					JSONObject _qlc = (JSONObject) qlcArray.get(i);
					Constants.qlcList.add(_qlc);
				}
			}
		}catch(Exception e){
			//��ȡ���ֲ����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.qlcList==null||Constants.qlcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "0000000000000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.qlcList.add(tempObj);
			}
		}
		
		try {
			JSONArray pl3Array = jobject.getJSONArray("pl3");
			if(pl3Array!=null&&pl3Array.length()>0){
			Constants.pl3List.clear();
				for (int i = 0; i < pl3Array.length(); i++) {
					JSONObject _pl3 = (JSONObject) pl3Array.get(i);
					Constants.pl3List.add(_pl3);
				}
			}
		}catch(Exception e){
			//��ȡ���������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.pl3List==null||Constants.pl3List.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.pl3List.add(tempObj);
			}
		}
		
		try {
			JSONArray dltArray = jobject.getJSONArray("dlt");
			if(dltArray!=null&&dltArray.length()>0){
				Constants.dltList.clear();
				for (int i = 0; i < dltArray.length(); i++) {
					JSONObject _dlt = (JSONObject) dltArray.get(i);
					Constants.dltList.add(_dlt);
				}
			}
		}catch(Exception e){
			//��ȡ����͸���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.dltList==null||Constants.dltList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00 00 00 00 00+00 0000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.dltList.add(tempObj);
			}
		}
		
		try {
			JSONArray sscArray = jobject.getJSONArray("ssc");
			if(sscArray!=null&&sscArray.length()>0){
				Constants.sscList.clear();
				for (int i = 0; i < sscArray.length(); i++) {
					JSONObject _ssc = (JSONObject) sscArray.get(i);
					Constants.sscList.add(_ssc);
				}
			}
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.sscList==null||Constants.sscList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "0000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.sscList.add(tempObj);
			}
		}
		
		try {
			JSONArray dlcArray = jobject.getJSONArray("11-5");
			if(dlcArray!=null&&dlcArray.length()>0){
				Constants.dlcList.clear();
				for (int i = 0; i < dlcArray.length(); i++) {
					JSONObject _dlc = (JSONObject) dlcArray.get(i);
					Constants.dlcList.add(_dlc);
				}
			}
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.dlcList==null||Constants.dlcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "0000000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.dlcList.add(tempObj);
			}
		}
		

		try {
			JSONArray ydjArray = jobject.getJSONArray("11-ydj");
			if(ydjArray!=null&&ydjArray.length()>0){
				Constants.ydjList.clear();
				for (int i = 0; i < ydjArray.length(); i++) {
					JSONObject _ydj = (JSONObject) ydjArray.get(i);
					Constants.ydjList.add(_ydj);
				}
			}
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.ydjList==null||Constants.ydjList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "0000000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.ydjList.add(tempObj);
			}
		}
		try {
			JSONArray twentyArray = jobject.getJSONArray("22-5");
			if(twentyArray!=null&&twentyArray.length()>0){
				Constants.twentylist.clear();
				for (int i = 0; i < twentyArray.length(); i++) {
					JSONObject twenty = (JSONObject) twentyArray.get(i);
					Constants.twentylist.add(twenty);
				}
			}
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.twentylist==null||Constants.twentylist.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "0000000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.twentylist.add(tempObj);
			}
		}
		try {
			JSONArray sfcArray = jobject.getJSONArray("sfc");
			if(sfcArray!=null&&sfcArray.length()>0){
				Constants.sfcList.clear();
				for (int i = 0; i < sfcArray.length(); i++) {
					JSONObject _sfc = (JSONObject) sfcArray.get(i);
					Constants.sfcList.add(_sfc);
				}
			}
		}catch(Exception e){
			//��ȡʤ�������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.sfcList==null||Constants.sfcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.sfcList.add(tempObj);
			}
		}
		
		try {
			JSONArray rx9Array = jobject.getJSONArray("rx9");
			if(rx9Array!=null&&rx9Array.length()>0){
				Constants.rx9List.clear();
				for (int i = 0; i < rx9Array.length(); i++) {
					JSONObject _rx9 = (JSONObject) rx9Array.get(i);
					Constants.rx9List.add(_rx9);
				}
			}
		}catch(Exception e){
			//��ȡ��ѡ9���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.rx9List==null||Constants.rx9List.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.rx9List.add(tempObj);
			}
		}
			
		try {
			JSONArray half6Array = jobject.getJSONArray("6cb");
			if(half6Array!=null&&half6Array.length()>0){
				Constants.half6List.clear();
				for (int i = 0; i < half6Array.length(); i++) {
					JSONObject _6cb = (JSONObject) half6Array.get(i);
					Constants.half6List.add(_6cb);
				}
			}
		}catch(Exception e){
			//��ȡ6�������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.half6List==null||Constants.half6List.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.half6List.add(tempObj);
			}
		}
		
		try {
			JSONArray jqcArray = jobject.getJSONArray("jqc");
			if(jqcArray!=null&&jqcArray.length()>0){
				Constants.jqcList.clear();
				for (int i = 0; i < jqcArray.length(); i++) {
					JSONObject _jqc = (JSONObject)jqcArray.get(i);
					Constants.jqcList.add(_jqc);
				}
			}
		}catch(Exception e){
			//��ȡ��������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.jqcList==null||Constants.jqcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.jqcList.add(tempObj);
			}
		}
		//������
		try {
			JSONArray pl5Array = jobject.getJSONArray("pl5");
			if(pl5Array!=null&&pl5Array.length()>0){
				Constants.pl5List.clear();
				for (int i = 0; i < pl5Array.length(); i++) {
					JSONObject _pl5 = (JSONObject)pl5Array.get(i);
					Constants.pl5List.add(_pl5);
				}
			}
		}catch(Exception e){
			//��ȡ��������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.pl5List==null||Constants.pl5List.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.pl5List.add(tempObj);
			}
		}
		//���ǲ�
		try {
			JSONArray qxcArray = jobject.getJSONArray("qxc");
			if(qxcArray!=null&&qxcArray.length()>0){
				Constants.qxcList.clear();
				for (int i = 0; i < qxcArray.length(); i++) {
					JSONObject _qxc = (JSONObject)qxcArray.get(i);
					Constants.qxcList.add(_qxc);
				}
			}
		}catch(Exception e){
			//��ȡ��������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.qxcList==null||Constants.qxcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "0000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.qxcList.add(tempObj);
			}
		}
	}
}
