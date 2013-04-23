package com.ruyicai.activity.buy.jc.lq.view;

import java.security.Identity;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.custom.checkbox.MyCheckBox;
import com.ruyicai.net.newtransaction.QueryJcInfoInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public abstract class LqMainView {
	private EditText editZhuma;
	private Spinner spinner;
	private ListView listView;
	protected Context context;
	private View view;
	private BetAndGiftPojo betAndGift;//Ͷע��Ϣ��
	private String[] spinnerStrs = { "2��1", "3��1", "4��1", "5��1","6��1", "7��1", "8��1" };
	private List<Info> listInfo = new ArrayList<Info>();/* �б�������������Դ */
	private static JSONArray jsonArray = null;
	private int INDEX = 0;
	private List<String> betcodes = new ArrayList<String>();		
    private long iZhuShu ;
	private long iAmt ;
	private final int MAXAMT = 20000;//���Ͷע���
	private String codes[];//ע������
	private long beiShu = 1;
	private Handler handler;
	private final static String ERROR_WIN = "0000";
	private LinearLayout layoutView;
	private BaseAdapter adapter;
	
	public abstract String getLotno();
	public abstract BaseAdapter getAdapter();
	public abstract void initListView(ListView list,Context context,List<Info> listInfo);
	public abstract String[] getCode(int index,List<Info> listInfo);
	
	public LqMainView(Context context,BetAndGiftPojo betAndGift,Handler handler,LinearLayout layout) {
        this.context = context;
        this.betAndGift = betAndGift;
        this.handler = handler;
        layoutView = layout;
        initView();
        getInfoNet();
        
	}
	/**
	 * �������
	 */
	public void clearInfo(){
		jsonArray = null;
	}
	/**
	 * ��ʼ�����
	 */
	public void initView(){
		LayoutInflater factory = LayoutInflater.from(context);
		view = factory.inflate(R.layout.buy_lq_main_view,null);
		spinner = (Spinner) view.findViewById(R.id.buy_jc_main_spinner);
		listView = (ListView) view.findViewById(R.id.buy_jc_main_listview);
		editZhuma = (EditText) view.findViewById(R.id.buy_zixuan_edit_zhuma);
		initSpinner();
		layoutView.addView(getView());
	}
	/**
	 * ������ȡ����
	 */
	public void getInfoNet() {
		if(jsonArray == null){
			infoNet();
		}else{
			initSubView();
		}
	}
	/**
	 * ��ʼ�����б�
	 */
	private void initSubView() {
		setValue(jsonArray);
		initListView(getListView(),context,listInfo);
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
				str = QueryJcInfoInterface.getInstance().queryJcInfo(QueryJcInfoInterface.JCLQ);
				dialog.cancel();
				try {
					JSONObject jsonObj = new JSONObject(str);
					final String msg = jsonObj.getString("message");
					final String error = jsonObj.getString("error_code");
					if(error.equals(ERROR_WIN)){
						jsonArray = jsonObj.getJSONArray("result");
						handler.post(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								initSubView();
							}
						});
					}else{
						handler.post(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
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
	public void setValue(JSONArray jsonArray ) {
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				Info itemInfo = new Info();
				JSONObject jsonItem = jsonArray.getJSONObject(i);
				itemInfo.setTime(jsonItem.getString("dayForamt")+"  "+jsonItem.getString("week"));
				itemInfo.setDay(jsonItem.getString("day"));
				itemInfo.setWeek(jsonItem.getString("weekId"));
				itemInfo.setTeam(jsonItem.getString("league"));
				itemInfo.setTimeEnd(jsonItem.getString("endTime"));
				itemInfo.setTeamId(jsonItem.getString("teamId"));
				itemInfo.setWin(jsonItem.getString("v3"));
				itemInfo.setFail(jsonItem.getString("v0"));
				String teams[] = jsonItem.getString("team").split(":");
				itemInfo.setHome(teams[0]);
				itemInfo.setAway(teams[1]);
				//�÷�ʤ��
				itemInfo.setFail(jsonItem.getString("letVs_v0"));
				itemInfo.setWin(jsonItem.getString("letVs_v3"));
				itemInfo.setLetPoint(jsonItem.getString("letPoint"));
				//��С��
				itemInfo.setBig(jsonItem.getString("g"));
				itemInfo.setSmall(jsonItem.getString("l"));
				itemInfo.setBasePoint(jsonItem.getString("basePoint"));
				//ʤ�ֲ�
				for(int n=0;n<6;n++){
					itemInfo.vStrs[n] = jsonItem.getString("v0"+(n+1));
				}
				for(int m=0;m<6;m++){
					itemInfo.vStrs[m+6] = jsonItem.getString("v1"+(m+1));
				}
				listInfo.add(itemInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
	 * �����б����
	 * @return
	 */
	public ListView getListView(){
		return listView;
	};

	/**
	 * ��ʼ��spinner������
	 */
    public void initSpinner(){
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				int position = spinner.getSelectedItemPosition();
				INDEX = position;
				onclikSpinner();
		
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
    }
	/**
	 * ˢ��spinner
	 */
	public void initSpinner(int num) {
		List allcountries = new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			if (i > spinnerStrs.length - 1) {
				break;
			} else {
				allcountries.add(spinnerStrs[i]);
			}

		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, allcountries);
		adapter.setDropDownViewResource(R.layout.myspinner_dropdown);
		spinner.setAdapter(adapter);
		if(INDEX<num){
			spinner.setSelection(INDEX,false);
		}else{
			spinner.setSelection(allcountries.size()-1,false);
		}
	}
    /**
     * spinner�¼���Ӧ
     * @param position
     */
	public void onclikSpinner() {
		betcodes.clear();
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);
			if (info.onclikNum>0) {
				betcodes.add(""+info.onclikNum);
			}
		}
		showEditText();
	}
	public void pushBetCodes(List<String> list){
		betcodes = list;
	}
	public boolean isTouZhuNet() {		
		// TODO Auto-generated method stub
		if(iZhuShu>0){
			if(isAmtDialog()){
				alertInfo("����Ͷע���ܴ���2��","��ܰ��ʾ");
				return false;
			}else{
				codes =getCode(INDEX+2,listInfo);
				betAndGift.setSellway("0");//1�����ѡ 0������ѡ
				betAndGift.setAmount(""+iAmt*100);
				betAndGift.setLotmulti(""+iZhuShu);
				betAndGift.setLotno(getLotno());
				betAndGift.setBet_code(codes[0]);
				return true;
			}
		}else{
			alertInfo("������ѡ��һע","��ѡ�����");
			return false;
		}
	}
	/**
	 * ��ʾ����Ϣ
	 * @return
	 */
	public String getAlertStr(){
		String returnStr = "ע����"+iZhuShu+"ע   "+"������"+beiShu+"��   "+"��"+iAmt+"Ԫ";
		return returnStr;
	}
	/**
	 * ��ʾ��ע��
	 * @return
	 */
	public String getAlertCode(){
		
		return codes[1];
	}
	/**
	 * �Ƿ񵯳���ܰ��ʾ
	 * @return
	 */
	public boolean isAmtDialog(){
		if(iAmt>MAXAMT||iAmt<0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * �õ�view
	 * 
	 */
	public View getView(){
		return view;
	}
	/**
	 * ��ʾ��������ע��
	 * 
	 */
	public void showEditText(String text){
		editZhuma.setText(text);
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
	public void setBeiShu(int beishu){
		beiShu = beishu;
	}
    public void showEditText(){
    	iZhuShu = PublicMethod.getAllAmt(betcodes, INDEX+2);
		iAmt = iZhuShu*2*beiShu;
		String alertStr = "��"+iZhuShu+"ע,"+beiShu+"��,"+"�ܽ��"+iAmt+"Ԫ";
		showEditText(alertStr);
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
		String fail = "";
		String week = "";
		String teamId = "";
		public int onclikNum = 0;
		boolean isWin = false;
		boolean isLevel = false;
		boolean isFail = false;
		//�÷�ʤ��
		String letWin = "";
		String letFfail = "";
		String letPoint = "";
		//�淨:��С��
		String big = "";
		String small = "";
		String basePoint = "";
		boolean isBig;
		boolean isSmall;
		//�淨:ʤ�ֲ�
		private final static int MAX = 12;
		public String vStrs[] = new String[MAX];
		private String btnStr = "";
		public Dialog	infoDialog = null;//��ʾ��
		public View infoViewType = null;
		private Dialog	dialog = null;//��ʾ��
		private View viewType;
		private int[] checkId ={R.id.lq_sfc_dialog_check01,R.id.lq_sfc_dialog_check02,R.id.lq_sfc_dialog_check03,R.id.lq_sfc_dialog_check04,R.id.lq_sfc_dialog_check05
				                ,R.id.lq_sfc_dialog_check06,R.id.lq_sfc_dialog_check07,R.id.lq_sfc_dialog_check08,R.id.lq_sfc_dialog_check09,R.id.lq_sfc_dialog_check010
				                ,R.id.lq_sfc_dialog_check011,R.id.lq_sfc_dialog_check012};
		public MyCheckBox[] check = new MyCheckBox[MAX]; 
		public String codes[] = {"01","02","03","04","05","06","11","12","13","14","15","16"};
		
		public void onclikBtn() {
			int onclikNums = 0;
			List<String> betcodess = new ArrayList<String>();
			for (int i = 0; i < listInfo.size(); i++) {
				Info info = (Info) listInfo.get(i);
				if (info.onclikNum>0) {
					onclikNums++;
					betcodess.add(""+info.onclikNum);
				}
			}
			pushBetCodes(betcodess);
			if (onclikNums < 9) {
				initSpinner(onclikNums - 1);
			}
		   showEditText();
		}
		/**
		 * ʤ�ֲ����
		 */
		public void createDialog(){
			if(dialog== null){
				adapter = getAdapter();
				LayoutInflater factory = LayoutInflater.from(context);
				viewType = factory.inflate(R.layout.buy_lq_sfc_dialog,null);
				TextView textTitle = (TextView) viewType.findViewById(R.id.layout_main_text_title);
				textTitle.setText(getAway()+" VS "+getHome());
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
			  check[i].setCheckText(""+vStrs[i]);
			  check[i].setPosition(i);
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
					onclikBtn();
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
					onclikBtn();
					adapter.notifyDataSetChanged();
				}
			});
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
		public String getLetFfail() {
			return letFfail;
		}
		public void setLetFfail(String letFfail) {
			this.letFfail = letFfail;
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
