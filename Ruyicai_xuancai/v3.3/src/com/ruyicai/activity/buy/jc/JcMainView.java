package com.ruyicai.activity.buy.jc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.activity.buy.jc.oddsprize.JCPrizePermutationandCombination;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.constant.Constants;
import com.ruyicai.custom.checkbox.MyCheckBox;
import com.ruyicai.custom.jc.button.MyButton;
import com.ruyicai.net.newtransaction.QueryJcInfoInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.PublicMethod;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.custom.checkbox.MyCheckBox;
import com.ruyicai.net.newtransaction.QueryJcInfoInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.PublicMethod;

public abstract class JcMainView {
	private ListView listView;   
	protected Context context;
	private View view;
	private BetAndGiftPojo betAndGift;//Ͷע��Ϣ��
	private String[] spinnerStrs = { "2��1", "3��1", "4��1", "5��1","6��1", "7��1", "8��1" };
	protected List<Info> listInfo = new ArrayList<Info>();/* �����б�������������Դ */
	protected List<Info> listInfo1 = new ArrayList<Info>();/* �����б�������������Դ */
	private static JSONArray jsonArray = null;//����
	private static JSONArray jsonArray1 = null;//����
	private int INDEX = 0;
	private List<String> betcodes = new ArrayList<String>();		
    private long iZhuShu ;
	private long iAmt ;
	private final int MAXAMT = 20000;//���Ͷע���
	private Handler handler;
	private final static String ERROR_WIN = "0000";
	private LinearLayout layoutView;
	private BaseAdapter adapter;
	private String jcType;
	private String jcvaluetype;
	private List<List> listWeeks = new ArrayList<List>();//�����ڻ�������
	private final String WEEK_1 = "1";
	private final String WEEK_2 = "2";
	private final String WEEK_3 = "3";
	private final String WEEK_4 = "4";
	private final String WEEK_5 = "5";
	private final String WEEK_6 = "6";
	private final String WEEK_7 = "7";
	private final int MAX_TEAM =10;//����ѡ10������
//	private int checkedNum =0;
	public  boolean isDanguan=false;
	protected static String[] listTeam ;/*����ѡ���б� */
	protected List<String> checkTeam = new ArrayList<String>();/*��ѡ���������б� */
	TextView textTeamNum;
	
	public abstract String getPlayType();
	public abstract String getTitle();
	public abstract String getTypeTitle();
	public abstract String getLotno();
	public abstract BaseAdapter getAdapter();
	public abstract void initListView(ListView list,Context context,List<List> listInfo);
	public abstract String getCode(String key,List<Info> listInfo);
	public abstract  List<double[]>  getOdds(List<Info> listInfo);
	public abstract String getAlertCode(List<Info> listInfo);
	public abstract int getTeamNum();

	
	public JcMainView(Context context,BetAndGiftPojo betAndGift,Handler handler,LinearLayout layout,String type,boolean isDanguan,List<String> checkTeam) {
        this.context = context;
        this.betAndGift = betAndGift;
        this.handler = handler;
        this.isDanguan=isDanguan;
        this.checkTeam = checkTeam;
        layoutView = layout;
        if(isDanguan){
        	jcvaluetype="0";
        }else{
        	jcvaluetype="1";
        }
        initListWeeks();
        setType(type);
        initView();
        getInfoNet();
        
	}
	private void initListWeeks() {
		listWeeks.clear();
	}
    public void updateList(List<String> checkTeam){
    	initListWeeks();
    	if(isDanguan){
    	listInfo1.clear();
    	}else{
    	listInfo.clear();
    	}
    	this.checkTeam = checkTeam;
    	getInfoNet();
    }
	/**
	 * ���ò�Ʊ���
	 */
	public void setType(String type){
		jcType = type;
	}
	/**
	 * �������
	 */
	public void clearInfo(){
		jsonArray = null;
		jsonArray1=null;
	}
	/**
	 * ��ʼ�����
	 */
	public void initView(){
		LayoutInflater factory = LayoutInflater.from(context);
		view = factory.inflate(R.layout.buy_jc_main_view_new,null);
		listView = (ListView) view.findViewById(R.id.buy_jc_main_listview);
		layoutView.addView(getView());
	}
	/**
	 * ������ȡ����
	 */
	public void getInfoNet() {
		if(isDanguan){
			if(jsonArray1==null) {
				infoNet();		
			}else{
				initSubView();
			}
		}else{
		if(jsonArray == null){
			infoNet();
		}else{
			initSubView();
		 }
		}
	}
	/**
	 * ��ʼ�����б�
	 */
	private void initSubView() {
		if(isDanguan){
			setValue(jsonArray1);	
		}else{
			setValue(jsonArray);
		}
		initListView(getListView(),context,listWeeks);
		layoutView.removeAllViews();
		layoutView.addView(getView());
	}
	private void infoNet() {
		final ProgressDialog dialog = UserCenterDialog.onCreateDialog(context);
		dialog.show();
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				str = QueryJcInfoInterface.getInstance().queryJcInfo(jcType,jcvaluetype);
				try {
					JSONObject jsonObj = new JSONObject(str);
					final String msg = jsonObj.getString("message");
					final String error = jsonObj.getString("error_code");
					if(error.equals(ERROR_WIN)){
						listTeam = jsonObj.getString("leagues").split(";");
						if(isDanguan){
						jsonArray1 = jsonObj.getJSONArray("result");	
						}else{
						jsonArray = jsonObj.getJSONArray("result");
						}
						handler.post(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								initSubView();
								dialog.cancel();
							}
						});
					}else{
						handler.post(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								dialog.cancel();
								Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	public void setValue(JSONArray jsonArray) {
		try {
			for(int i=0;i<jsonArray.length();i++){
				List<Info> list = addDateInfo(jsonArray.getJSONArray(i));
				if(list.size()>0){
					listWeeks.add(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private List<Info> addDateInfo(JSONArray jsonArray)throws JSONException {
		List<Info> listInfos = new ArrayList<Info>();
		for(int i=0;i<jsonArray.length();i++){
			JSONObject jsonItem = jsonArray.getJSONObject(i);
			if(checkTeam.size()>0){
				 String league = jsonItem.getString("league");
				 for(int j=0;j<checkTeam.size();j++){
					 if(checkTeam.get(j).equals(league)){
						 if(addInfo(jsonItem)!=null)
						 listInfos.add(addInfo(jsonItem));
					 }
				 }
			}else{
				 if(addInfo(jsonItem)!=null)
				 listInfos.add(addInfo(jsonItem));
			}
		}
		return listInfos;
	}
	public Info addInfo(JSONObject jsonItem) throws JSONException{
		Info itemInfo = new Info();
		itemInfo.setTime(jsonItem.getString("dayForamt")+"  "+jsonItem.getString("week"));
		itemInfo.setDay(jsonItem.getString("day"));
		itemInfo.setWeek(jsonItem.getString("weekId"));
		itemInfo.setTeam(jsonItem.getString("league"));
		itemInfo.setTimeEnd(jsonItem.getString("endTime"));
		itemInfo.setTeamId(jsonItem.getString("teamId"));
		itemInfo.setWin(jsonItem.getString("v3"));
		itemInfo.setFail(jsonItem.getString("v0"));
		String teams[] = jsonItem.getString("team").split(":");
		String[] unsupportStr = jsonItem.getString("unsupport").split(",");
		itemInfo.setHome(teams[0]);
		itemInfo.setAway(teams[1]);
		itemInfo.setLetPoint(jsonItem.getString("letPoint"));
		setDifferValue(jsonItem,itemInfo);
		for(String str:unsupportStr){
			if(getPlayType().equals(str)){
				 return null;
			}
		}
        if(isDanguan){
        	listInfo1.add(itemInfo);
        }else{
	      	listInfo.add(itemInfo);
        }
        return itemInfo;
	}
	public void setDifferValue(JSONObject jsonItem,Info itemInfo) throws JSONException{
		
	}

	/**
	 * �����б����
	 * @return
	 */
	public ListView getListView(){
		return listView;
	};
    /**
     * ��ʼ��ÿ�ӱ�ѡ�еİ�ť��������
     * @param position
     */
	public void initOnclikNums() {
		betcodes.clear();
		List<Info> list;
		if(isDanguan){
			list=listInfo1;
		}else{
			list=listInfo;
		}
		for (int i = 0; i < list.size(); i++) {
			Info info = (Info) list.get(i);
			if (info.onclikNum>0) {
				betcodes.add(""+info.onclikNum);
			}
		}
	}
	/**
	 * ��ʼ����ѡ����ٳ�����
	 */
	public void initTeamNum(TextView textTeamNum) {
		this.textTeamNum = textTeamNum;
		setTeamNum();
		
	}
	public void setTeamNum(){
		textTeamNum.setText("��ѡ����"+initCheckedNum()+"������");
	}
	public boolean isTouZhuNet() {		
		// TODO Auto-generated method stub
		int checkedNum = initCheckedNum();
		if(isDanguan){
			if(checkedNum>=1){//����ѡ������
				betAndGift.setSellway("0");//1�����ѡ 0������ѡ
				betAndGift.setLotno(getLotno());
				initOnclikNums();
				return true;
			}else{
				alertInfo("������ѡ��һ������","��ѡ�����");
				return false;
			}
		}else{
	       	if(checkedNum>=2){//����ѡ������
			betAndGift.setSellway("0");//1�����ѡ 0������ѡ
			betAndGift.setLotno(getLotno());
			initOnclikNums();
			return true;
		}else{
			alertInfo("������ѡ����������","��ѡ�����");
			return false;
		}
	}
	}
	/**
	 * 	��ȡѡ�е������
	 * @return
	 */
	public int initCheckedNum(){
		int checkedNum = 0;
		List<Info> list;
		if(isDanguan){
			list=listInfo1;
		}else{
			list=listInfo;
		}
		for(Info info:list){
			if(info.onclikNum>0){
				checkedNum++;
			}
		}
		return checkedNum;
	}
	/**
	 * ���ɹ��ؼ���ע��
	 * @param select
	 * @return
	 */
	public int getZhushu(int select){
		return PublicMethod.getAllAmt(betcodes, select);
	}
	/**
	 * �മ���ؼ���ע��
	 * @param select
	 * @return
	 */
	public int getDuoZhushu(int teamNum,int select){
		return PublicMethod.getDouZhushu(teamNum, betcodes, select);
	}
	/**
	 * ���ؼ���ע��
	 * @param select
	 * @return
	 */
	public int getDanZhushu(){
		return PublicMethod.getDanAAmt(betcodes);
	}
	/**
	 * ��ȡ����Ͷע���н������������С�����ַ���
	 * @return
	 */
	public String getDanPrizeString(int muti){
		
		return JCPrizePermutationandCombination.getDanGuanPrize(getOddsArraysValue(), muti);
	}
	/**
	 * ��ȡ���ɹ��ص��н��������ֵ����Сֵ
	 * @return
	 */
	public String getFreedomPrizeString(int select,int muti){
		
//		StringBuffer aa = new StringBuffer();
//		String mixValue = PublicMethod.formatStringToTwoPoint(mixArrays[0]*2* muti);
//		String maxValue = PublicMethod.formatStringToTwoPoint(maxPrize*2* muti);
//		aa.append("��С�н���").append(mixValue).append("Ԫ��").append("����н���").append(maxValue).append("Ԫ");
	    	return "" ;
	    }
	/**
	 * ��ȡ�മ���ص��н��������ֵ����Сֵ
	 * @return
	 */
    public String getDuoPrizeString(){
	    	return "";
	    }
	/**
	 * �õ�view
	 * 
	 */
	public View getView(){
		return view;
	}
    public String getBetCode(String key){
    	List<Info> list;
		if(isDanguan){
			list=listInfo1;
		}else{
			list=listInfo;
		}
    	return getCode(key, list);
    }
    
    public List<double[]> getOddsArraysValue(){
    	List<Info> list;
		if(isDanguan){
			list=listInfo1;
		}else{
			list=listInfo;
		}
    	return getOdds(list);
    }
    
    public double getFreedomMaxPrize(int select){
    	return JCPrizePermutationandCombination.getFreedomGuoGuanMaxPrize(getOddsArraysValue(), select);
    }
    public double getDuoMaxPrize(int teamNum,int select){
    	return JCPrizePermutationandCombination.getDuoMaxPrize(teamNum,getOddsArraysValue(), select);
    }
    public double getDuoMixPrize(int teamNum,int select){
    	return JCPrizePermutationandCombination.getDuoMixPrize(teamNum,getOddsArraysValue(), select);
    }
    /**
     * ��ȡ��С�н����
     * @param select ��С���м���
     * @return
     */
    public double getFreedomMixPrize(int select){
    	return JCPrizePermutationandCombination.FreedomGuoGuanMixPrize(getOddsArraysValue(), select);
    }
	/**
	 * ��ʾ��1 ��������ѡ�����
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	public void alertInfo(String string,String title) {   
		Builder dialog = new AlertDialog.Builder(context).setTitle(title)
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}

						});
		dialog.show();

	}
    public String getAlertMsg(){
    	List<Info> list;
		if(isDanguan){
			list=listInfo1;
		}else{
			list=listInfo;
		}
    	return getAlertCode(list);
    }
    /**
     * ���ѡ��״̬
     */
    public void clearChecked(){
    	if(isDanguan){
    	listInfo1.clear();
        initListWeeks();
        setValue(jsonArray1);
        getAdapter().notifyDataSetChanged();	
    	}else{
    	listInfo.clear();
    	initListWeeks();
    	setValue(jsonArray);
    	getAdapter().notifyDataSetChanged();
    	}
    }
    /**
     * ������ѡ��10������
     */
    public boolean isCheckTeam(){
		int teamNum = initCheckedNum();
		if(teamNum<MAX_TEAM){
    		return true;
		}else{
			Toast.makeText(context, "��������ѡ��10����������Ͷע��", Toast.LENGTH_SHORT).show();
			return false;
		}
    }
	/**
	 * �����ڲ���
	 * 
	 * @author Administrator
	 */
	public class Info {
		String time = "";
		String day = "";
		String team = "";
		String home = "";
		String away = "";
		String score = "";
		String timeEnd = "";
		String win = "";
		String level = "";
		String fail = "";
		String week = "";
		String teamId = "";
		public int onclikNum = 0;
		boolean isWin = false;
		boolean isLevel = false;
		boolean isFail = false;
		//�÷�ʤ��
		String letWin = "";
		String letFail = "";
		String letPoint = "";
		//�淨:��С��
		String big = "";
		String small = "";
		String basePoint = "";
		boolean isBig;
		boolean isSmall;
		//�淨:ʤ�ֲ�
		private  int MAX = 12;
		public String vStrs[];
		private String btnStr = "";
		public Dialog	infoDialog = null;//��ʾ��
		public View infoViewType = null;
		private Dialog	dialog = null;//��ʾ��
		private View viewType;
		private int[] checkId ={R.id.lq_sfc_dialog_check01,R.id.lq_sfc_dialog_check02,R.id.lq_sfc_dialog_check03,R.id.lq_sfc_dialog_check04,R.id.lq_sfc_dialog_check05
				                ,R.id.lq_sfc_dialog_check06,R.id.lq_sfc_dialog_check07,R.id.lq_sfc_dialog_check08,R.id.lq_sfc_dialog_check09,R.id.lq_sfc_dialog_check010
				                ,R.id.lq_sfc_dialog_check011,R.id.lq_sfc_dialog_check012,R.id.lq_sfc_dialog_check013,R.id.lq_sfc_dialog_check014,R.id.lq_sfc_dialog_check015
				                ,R.id.lq_sfc_dialog_check016,R.id.lq_sfc_dialog_check017,R.id.lq_sfc_dialog_check018,R.id.lq_sfc_dialog_check019,R.id.lq_sfc_dialog_check020
				                ,R.id.lq_sfc_dialog_check021,R.id.lq_sfc_dialog_check022,R.id.lq_sfc_dialog_check023,R.id.lq_sfc_dialog_check024,R.id.lq_sfc_dialog_check025
				                ,R.id.lq_sfc_dialog_check026,R.id.lq_sfc_dialog_check027,R.id.lq_sfc_dialog_check028,R.id.lq_sfc_dialog_check029,R.id.lq_sfc_dialog_check030
				                ,R.id.lq_sfc_dialog_check031};
		public MyCheckBox[] check ; 
		public boolean isOpen = false;
		public String titles[];
		
		public String[] getVStrs(){
			return vStrs;
		}
		public void setMax(int max){
			this.MAX = max;
		}
		/**
		 * ��ʼ��������ѡ�����
		 * @param length
		 */
        public void initCheckTitles(String titles[]){
        	this.titles = titles;
        	MAX = titles.length;
        	check = new MyCheckBox[MAX];
        }
		/**
		 * ʤ�ֲ����
		 */
		public void createDialog(String titles[],boolean isVisable,String title){
			if(dialog== null){
				initCheckTitles(titles);
				adapter = getAdapter();
				LayoutInflater factory = LayoutInflater.from(context);
				viewType = factory.inflate(R.layout.buy_lq_sfc_dialog,null);
				LinearLayout layout1 = (LinearLayout) viewType.findViewById(R.id.jc_check_dialog_layout2);
				LinearLayout layout2 = (LinearLayout) viewType.findViewById(R.id.jc_check_dialog_layout3);
				if(isVisable){
					layout1.setVisibility(LinearLayout.VISIBLE);
					layout2.setVisibility(LinearLayout.VISIBLE);
				}
				TextView textTitle = (TextView) viewType.findViewById(R.id.layout_main_text_title);
				textTitle.setText(title);
				initDialogView();
				onClikOk();
				onClikCanCel();
				dialog = new AlertDialog.Builder(context).create();
				dialog.setCancelable(false);
				infoDialog = dialog;
				infoViewType = viewType;
			}
		    dialog.show();
		    dialog.getWindow().setContentView(viewType);
		}

		private void initDialogView(){
			for(int i=0;i<MAX;i++){
			      check[i] = (MyCheckBox) viewType.findViewById(checkId[i]);
				  check[i].setVisibility(CheckBox.VISIBLE);
				  check[i].setCheckText(""+vStrs[i]);
				  check[i].setPosition(i);  
				  check[i].setCheckTitle(titles[i]);
			}
		}
		private void onClikCanCel() {
			Button cancel = (Button) viewType.findViewById(R.id.canel);
			cancel.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialog.cancel();
					for(int i=0;i<check.length;i++){
						if(check[i].getChecked()){
							check[i].setChecked(false);
						}
						setBtnStr("");
						onclikNum = 0;
					}
					setTeamNum();
					adapter.notifyDataSetChanged();
				}
			});
		}
		private void onClikOk() {
			Button ok = (Button) viewType.findViewById(R.id.ok);
			ok.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialog.cancel();
					btnStr = "";
					onclikNum = 0;
					for(int i=0;i<check.length;i++){
						if(check[i].getChecked()){
							btnStr += check[i].getChcekTitle()+"  ";
							onclikNum++;
						}
					}
					setTeamNum();
					adapter.notifyDataSetChanged();
				}
			});
		}
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public boolean isBig() {
			return isBig;
		}
		public void setBig(boolean isBig) {
			this.isBig = isBig;
		}
		public boolean isSmall() {
			return isSmall;
		}
		public void setSmall(boolean isSmall) {
			this.isSmall = isSmall;
		}
		public String getBtnStr() {
			return btnStr;
		}
		public void setBtnStr(String btnStr) {
			this.btnStr = btnStr;
		}
		public String getLetWin() {
			return letWin;
		}
		public void setLetWin(String letWin) {
			this.letWin = letWin;
		}
		public String getLetFail() {
			return letFail;
		}
		public void setLetFail(String letFail) {
			this.letFail = letFail;
		}
		public String getBig() {
			return big;
		}
		public void setBig(String big) {
			this.big = big;
		}
		public String getSmall() {
			return small;
		}
		public void setSmall(String small) {
			this.small = small;
		}
		public String getBasePoint() {
			return basePoint;
		}
		public void setBasePoint(String basePoint) {
			this.basePoint = basePoint;
		}
		public String getTeamId() {
			return teamId;
		}
		public void setTeamId(String teamId) {
			this.teamId = teamId;
		}
		public String getLetPoint() {
			return letPoint;
		}
		public void setLetPoint(String letPoint) {
			this.letPoint = letPoint;
		}
		public String getWeek() {
			return week;
		}
		public void setWeek(String week) {
			this.week = week;
		}
		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public int getOnclikNum() {
			return onclikNum;
		}

		public void setOnclikNum(int onclikNum) {
			this.onclikNum = onclikNum;
		}
		public boolean isWin() {
			return isWin;
		}

		public void setWin(boolean isWin) {
			this.isWin = isWin;
		}

		public boolean isLevel() {
			return isLevel;
		}

		public void setLevel(boolean isLevel) {
			this.isLevel = isLevel;
		}

		public boolean isFail() {
			return isFail;
		}

		public void setFail(boolean isFail) {
			this.isFail = isFail;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getTeam() {
			return team;
		}

		public void setTeam(String team) {
			this.team = team;
		}

		public String getHome() {
			return home;
		}

		public void setHome(String home) {
			this.home = home;
		}

		public String getAway() {
			return away;
		}

		public void setAway(String away) {
			this.away = away;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getTimeEnd() {
			return timeEnd;
		}

		public void setTimeEnd(String timeEnd) {
			this.timeEnd = timeEnd;
		}

		public String getWin() {
			return win;
		}

		public void setWin(String win) {
			this.win = win;
		}

		public String getFail() {
			return fail;
		}

		public void setFail(String fail) {
			this.fail = fail;
		}
	}
}
