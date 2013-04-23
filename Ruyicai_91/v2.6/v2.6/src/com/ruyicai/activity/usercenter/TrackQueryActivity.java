/**
 * 
 */
package com.ruyicai.activity.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.net.newtransaction.CancelTrackInterface;
import com.ruyicai.net.newtransaction.GiftQueryInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndWinAndTrackAndGiftQueryPojo;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.ShellRWSharesPreferences;
/**
 * ׷�Ų�
 * @author miao
 */
public class TrackQueryActivity extends Activity {

	private LinearLayout usecenerLinear;
	private Button returnButton;
	private TextView	titleTextView;
	String jsonString;
	final	String BETCODE="betCode",BATCHNUM="batchNum",ORDERTIME="orderTime",ID = "id",
						LOTNO="lotNo",LOTNAME = "lotName",AMOUNT="amount",LASTNUM = "lastNum",BEGINBATCH = "beginBatch"
							,STATE = "state",ERROR_CODE = "error_code",MESSAGE = "message";
	private final int DIALOG1_KEY = 0;
	AlertDialog remindCancleDialog;
	private String phonenum,sessionid,userno;
	List<Vector> windatalist = new ArrayList<Vector>(); 
	Context context = this ;
	ProgressDialog dialog;
	String jsonobject;
	int  allPage;
	int pageindex;
	ListView queryinfolist;
	TextView pagetext;
	boolean isfirst = false,isCancleTrackNet = false;
	 Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				Toast.makeText(TrackQueryActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
				break;
			case 1:
				dialog.dismiss();
				encodejson((String) msg.obj);
				initList();
				break;
			case 2:
				dialog.dismiss();
				Toast.makeText(TrackQueryActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
				cancleTrackError000000();
				break;
			}
		}
	 };
	public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.usercenter_mainlayout);
			returnButton = (Button)findViewById(R.id.layout_usercenter_img_return);
			initReturn();
			titleTextView = (TextView)findViewById(R.id.usercenter_mainlayou_text_title);
			returnButton.setBackgroundResource(R.drawable.returnselecter);
			titleTextView.setText(R.string.usercenter_trackNumberInquiry);
			returnButton.setText(R.string.returnlastpage);
			jsonobject = this.getIntent().getStringExtra("trackjson");
			encodejson(jsonobject);
			isfirst = true;
			initLinear();
	}
	protected  void initReturn(){
		returnButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					finish();
			}
		});
	}
	 private void initPojo(){
			ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this, "addInfo");
			phonenum = shellRW.getUserLoginInfo("phonenum");
			sessionid = shellRW.getUserLoginInfo("sessionid");
			userno = shellRW.getUserLoginInfo("userno");
	}
	private void	trackQueryNet(final int pageindex){
		showDialog(0);
		new Thread(new Runnable() {
			public void run() {
			initPojo();
			BetAndWinAndTrackAndGiftQueryPojo winQueryPojo = new BetAndWinAndTrackAndGiftQueryPojo();
			winQueryPojo.setUserno(userno);
			winQueryPojo.setSessionid(sessionid);
			winQueryPojo.setPhonenum(phonenum);
			winQueryPojo.setPageindex(String.valueOf(pageindex));
			winQueryPojo.setMaxresult("10");
			winQueryPojo.setType("track");
			isCancleTrackNet = false;
			Message msg = new Message();
			jsonString = GiftQueryInterface.getInstance().giftQuery(winQueryPojo);
			try {
				JSONObject aa = new JSONObject(jsonString);
				String errcode = aa.getString(ERROR_CODE);
				String message = aa.getString(MESSAGE);
				if(errcode.equals("0000")){
					msg.what = 1;
					msg.obj = jsonString;
					handler.sendMessage(msg);
				}else{
					msg.what = 0;
					msg.obj = message;
					handler.sendMessage(msg);					
				}
			} catch (Exception e) {
			}
			}
		}).start();
	}
	private void initLinear(){
		usecenerLinear = (LinearLayout)findViewById(R.id.usercenterContent);
		usecenerLinear.addView(initLinearView());
	}
	private  View	initLinearView(){
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (LinearLayout) inflate.inflate(R.layout.usercenter_listview_layout, null);
		queryinfolist = (ListView) view.findViewById(R.id.usercenter_listview_queryinfo);
		pagetext = (TextView)view.findViewById(R.id.usercenter_text_pagetext);
		ImageButton subpagebtn = (ImageButton)view.findViewById(R.id.usercenter_btn_subpage);
		subpagebtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				isfirst = false;
				 pageindex--;
	               if(pageindex<0){
	            	   pageindex = 0;
	   				Toast.makeText(TrackQueryActivity.this,R.string.usercenter_hasgonefirst, Toast.LENGTH_SHORT).show();   
	               }else{
	            	   	List list = initAdapterlist(pageindex);
		        		initListView(queryinfolist,list);
	               }
	             
//	       		pagetext.setText(initPageTextView(pageindex));
			}
		});
		ImageButton addpagebtn = (ImageButton)view.findViewById(R.id.usercenter_btn_addpage);
		addpagebtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			   isfirst = false;
			   if(pageindex > windatalist.size()-1){
				   pageindex = windatalist.size()-1;
			   }
               pageindex++;
	           if(pageindex<allPage){
		        	if(pageindex>windatalist.size()-1){
		        		trackQueryNet(pageindex);
		        	}else{
		        		List list = initAdapterlist(pageindex);
		        		initListView(queryinfolist,list);
		        	}
			   }else{
				   pageindex=allPage-1;
				Toast.makeText(TrackQueryActivity.this, R.string.usercenter_hasgonelast, Toast.LENGTH_SHORT).show();   
			   }
//	           pagetext.setText(initPageTextView(pageindex));
			}
		});
		
//		pagetext.setText(initPageTextView(pageindex));
		if(isfirst){
			List list = initAdapterlist(pageindex);
			initListView(queryinfolist,list);
		}
		return view;
	}
	/**
	 * ��ʼ���б�
	 */
	public void initList(){
		List list = initAdapterlist(pageindex);
		initListView(queryinfolist,list);
	}
	private String initPageTextView(int pageindex){
		StringBuffer str = new StringBuffer();
		String zi_gong = getString(R.string.usercenter_gong);
		String zi_ye = getString(R.string.usercenter_page);
		String zi_di = getString(R.string.usercenter_di);
		str.append(zi_di).append(pageindex+1+"").append(zi_ye).append(zi_gong).append(String.valueOf(allPage)).append(zi_ye);
		return str.toString();
	}
	private void	initListView(ListView listview,List list){
		pagetext.setText(initPageTextView(pageindex));
		WinPrizeAdapter adapter = new WinPrizeAdapter(context,list);
		listview.setAdapter(adapter);
		
	}
	private AlertDialog lookDetailDialog(String detail){
		LayoutInflater factory = LayoutInflater.from(this);
		/*�н���ѯ�Ĳ鿴����ʹ������ѯ�Ĳ���*/
		View	view = factory.inflate(R.layout.usercenter_balancequery, null);
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		TextView  title = (TextView)view.findViewById(R.id.usercenter_balancequery_title);
		TextView  remind = (TextView)view.findViewById(R.id.usercenter_remind_text);
		remind.setVisibility(TextView.GONE);
		title.setText(R.string.usercenter_detailsTitle);
		TextView	detailTextView = (TextView)view.findViewById(R.id.balanceinfo);
		detailTextView.setText(detail);
		Button cancleLook = (Button)view.findViewById(R.id.usercenter_balancequery_back);
		cancleLook.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
		dialog.getWindow().setContentView(view);
		return dialog;
			
	}
	private AlertDialog cancleTrackDialog(final String id){
		LayoutInflater factory = LayoutInflater.from(this);
		/*�н���ѯ�Ĳ鿴����ʹ������ѯ�Ĳ���*/
		View	view = factory.inflate(R.layout.usercenter_balancequery, null);
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		TextView  title = (TextView)view.findViewById(R.id.usercenter_balancequery_title);
		TextView  remind = (TextView)view.findViewById(R.id.usercenter_remind_text);
		remind.setVisibility(TextView.GONE);
		title.setText(R.string.cancel_add_num);
		TextView	detailTextView = (TextView)view.findViewById(R.id.balanceinfo);
		detailTextView.setTextSize(18);
		detailTextView.setText(R.string.usercenter_cancleTrackRemind);
		Button cancleLook = (Button)view.findViewById(R.id.usercenter_balancequery_back);
		cancleLook.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		Button okBtn = (Button)view.findViewById(R.id.usercenter_balancequery_ok);
		okBtn.setVisibility(Button.VISIBLE);
		okBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
				cancleTrackNet(id);
			}
		});
		dialog.show();
		dialog.getWindow().setContentView(view);
		return dialog;
	}
	/**
	 * ȡ��׷������
	 * @param tsubscribeid ׷�ż�¼id
	 */
	private void cancleTrackNet(final String tsubscribeNo){
		showDialog(0);
		new Thread(new Runnable() {
			public void run() {
				initPojo();
				Message msg = new Message();
				String cancleTrackBack = CancelTrackInterface.getInstance().canceltrack(userno, sessionid, tsubscribeNo, phonenum);
				try {
					JSONObject cancleTrackReturn = new JSONObject(cancleTrackBack);
					String errorCode = cancleTrackReturn.getString(ERROR_CODE);
					String message = cancleTrackReturn.getString(MESSAGE);
					if(errorCode.equals("0000")){
						msg.what = 2;
						msg.obj = message;
						handler.sendMessage(msg);
					}else{
						msg.what = 0;
						msg.obj = message;
						handler.sendMessage(msg);
					};
				} catch (JSONException e) {
				}
			}
		}).start();
	}
	public List<Map<String, Object>> initAdapterlist(int pageindex){
		  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
			for (int i = 0; i < windatalist.get(pageindex).size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(BETCODE, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getBetCode());
				map.put(AMOUNT, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getAmount());
				map.put(ORDERTIME, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getOrderTime());
				map.put(BATCHNUM, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getBatchNum());
				map.put(STATE, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getState());
				map.put(BEGINBATCH, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getBeginBatch());
				map.put(LOTNAME, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getLotName());
				map.put(LASTNUM, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getLastNums());
				map.put(ID, ((TrackQueryInfo) windatalist.get(pageindex).get(i)).getId());
				list.add(map);
			}
			return list;
	  }  
	
	  public void encodejson(String json) {
		  try {
			  JSONObject  winprizejsonobj = new JSONObject(json);
			  allPage = Integer.parseInt(winprizejsonobj.getString("totalPage"));
			  String winprizejsonstring = winprizejsonobj.getString("result");
			  JSONArray winprizejson = new JSONArray(winprizejsonstring);
			  Vector<TrackQueryInfo> winInfos = new Vector<TrackQueryInfo>(); 
			  for(int i=0;i<winprizejson.length();i++){
				try{
					TrackQueryInfo winPrizeQueryinfo = new TrackQueryInfo();
					winPrizeQueryinfo.setBetCode(winprizejson.getJSONObject(i).getString(BETCODE));
					winPrizeQueryinfo.setAmount(winprizejson.getJSONObject(i).getString(AMOUNT));
					winPrizeQueryinfo.setState(winprizejson.getJSONObject(i).getString(STATE));
					winPrizeQueryinfo.setBatchNum(winprizejson.getJSONObject(i).getString(BATCHNUM));
					winPrizeQueryinfo.setLotName(winprizejson.getJSONObject(i).getString(LOTNAME));
					winPrizeQueryinfo.setOrderTime(winprizejson.getJSONObject(i).getString(ORDERTIME));
					winPrizeQueryinfo.setBeginBatch(winprizejson.getJSONObject(i).getString(BEGINBATCH));
					winPrizeQueryinfo.setLastNums(winprizejson.getJSONObject(i).getString(LASTNUM));
					winPrizeQueryinfo.setId(winprizejson.getJSONObject(i).getString(ID));
					winInfos.add(winPrizeQueryinfo);
				}catch (Exception e) {
				}
			 }
			 windatalist.add(winInfos);
		  	 } catch (JSONException e) {
		  		try {
						JSONObject winprizejson = new JSONObject(json);
		  		} catch (JSONException e1) {
		  		}
	    	}
	  }
	class TrackQueryInfo{
	
		private String lotName;
		private String betCode;
		private String batchNum;
		private String  state;
		private String orderTime;
		private String beginBatch;
		private String amount;
		private String lotMulti;
		private String lastNums;
		private String id;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLastNums() {
			return lastNums;
		}
		public void setLastNums(String lastNums) {
			this.lastNums = lastNums;
		}
		public String getAmount() {
			return amount;
		}
		public String getLotMulti() {
			return lotMulti;
		}
		public void setLotMulti(String lotMulti) {
			this.lotMulti = lotMulti;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getBetCode() {
			return betCode;
		}
		public void setBetCode(String betCode) {
			this.betCode = betCode;
		}
		public String getLotName() {
			return lotName;
		}
		public void setLotName(String lotName) {
			this.lotName = lotName;
		}
		public String getBatchNum() {
			return batchNum;
		}
		public void setBatchNum(String batchNum) {
			this.batchNum = batchNum;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}
		public String getBeginBatch() {
			return beginBatch;
		}
		public void setBeginBatch(String beginBatch) {
			this.beginBatch = beginBatch;
		}
	}
	/**
	 * �н���ѯ��������
	 */
	public class WinPrizeAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater; // �������б���
			private List<Map<String, Object>> mList;
			public WinPrizeAdapter(Context context, List<Map<String, Object>> list) {
				mInflater = LayoutInflater.from(context);
				mList = list;
			}
			public int getCount() {
				return mList.size();
			}
			public Object getItem(int position) {
				return mList.get(position);
			}
			public long getItemId(int position) {
				return position;
			}
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				final String lotName = (String) mList.get(position).get(LOTNAME);
				final String betAmount = (String) mList.get(position).get(AMOUNT);
				final String trackState = (String) mList.get(position).get(STATE);
				final String batchNums = (String) mList.get(position).get(BATCHNUM);
				final String lastNums = (String) mList.get(position).get(LASTNUM);
				final String ordertime = (String) mList.get(position).get(ORDERTIME);
				final String betcode = (String) mList.get(position).get(BETCODE);
				final String beginBatch = (String) mList.get(position).get(BEGINBATCH);
				final String trackId = (String) mList.get(position).get(ID);
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.usercenter_trackquery_listitem,null);
					holder = new ViewHolder();
					holder.lotName = (TextView) convertView.findViewById(R.id.usercenter_trackquery_lotteryname);
					holder.trackState = (TextView)convertView.findViewById(R.id.usercenter_trackquery_trackstate);
					holder.batchNums = (TextView) convertView.findViewById(R.id.usercenter_trackquery_tracknum);
					holder.batchNumed = (TextView) convertView.findViewById(R.id.usercenter_trackquery_tracknumed);
					holder.trackAmount = (TextView)convertView.findViewById(R.id.usercenter_trackquery_money);
					holder.lookdetail = (Button)convertView.findViewById(R.id.usercenter_trackquery_lookdetail);
					holder.cancleTrack = (Button)convertView.findViewById(R.id.usercenter_trackquery_cancle);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.lotName.setText(lotName);
				trackState(holder.trackState, trackState);
				cancleTrackVisible(holder.cancleTrack,trackState);
				holder.trackAmount .setText(PublicMethod.formatMoney(betAmount));
				holder.batchNums .setText(batchNums);
				holder.batchNumed .setText(lastNums);
				holder.lookdetail.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						StringBuffer detailinfo = new StringBuffer();
						detailinfo.append(getString(R.string.usercenter_lotterytypename)).append(lotName).append("\n\n")
//							.append(getString(R.string.usercenter_winningCheck_lotteryMultiple)).append(lotMulti).append("\n\n")//�����Ȳ���ʾ
							.append(getString(R.string.usercenter_trackbatchnums)).append(batchNums).append("\n\n")
							.append(getString(R.string.usercenter_trackedbatchnums)).append(lastNums).append("\n\n")
							.append(getString(R.string.usercenter_startbatchnums)).append(beginBatch).append("\n\n")
							.append(getString(R.string.usercenter_alltrackmoney)).append(PublicMethod.formatMoney(betAmount)).append("\n\n")
							.append(getString(R.string.usercenter_lottery_time)).append(ordertime).append("\n\n")
							.append(getString(R.string.usercenter_trackquery_nowstate)).append(formatTrackState(trackState)).append("\n\n")
							.append(getString(R.string.usercenter_betcode)).append("\n")
							.append(betcode);
						lookDetailDialog(""+detailinfo).show();
					}
				});
				holder.cancleTrack.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						cancleTrackDialog(trackId);
					}
				});
				return convertView;
			}
			class ViewHolder {
				TextView lotName;
				TextView trackState;
				TextView batchNums;
				TextView batchNumed;
				TextView trackAmount;
				Button   cancleTrack;
				Button   lookdetail;
			}
	}
	
	protected Dialog onCreateDialog(int id) {
	   dialog = new ProgressDialog(this);
       switch (id) {
           case DIALOG1_KEY: {
        	   dialog.setTitle(R.string.usercenter_netDialogTitle);
               dialog.setMessage(getString(R.string.usercenter_netDialogRemind));
               dialog.setIndeterminate(true);
               dialog.setCancelable(true);
               return dialog;
           }
       }
       return dialog;
   }
	/**
	 * ��ʽ��state������state��������ɫ��ֵ��TextView
	 * @param text   TextView
	 * @param state 
	 */
	private void trackState(TextView text,String state){
		int stateInt = 0;
		stateInt = Integer.parseInt(state);
		int StringId = 0;
		switch (stateInt) {
		case 0:
			StringId = R.string.usercenter_str_running;
			text.setTextColor(0xff006600);
			break;
		case 2:
			StringId = R.string.usercenter_str_hasCancled;
			text.setTextColor(0xffcc0000);
			break;
		case 3:
			StringId =  R.string.usercenter_str_hasClosed;
			text.setTextColor(0xffd4d4d4);
			break;
		}
		text.setText(StringId);
	}
	private void cancleTrackVisible(Button btn,String state){
		int stateInt = 0;
		stateInt = Integer.parseInt(state);
		switch (stateInt) {
		case 0:
			btn.setBackgroundResource(R.drawable.usercenter_cancletrack_selector);
			btn.setEnabled(true);
			break;
		case 2:
			btn.setEnabled(false);
			btn.setBackgroundResource(R.drawable.btn_qxzq_stop);
			break;
		case 3:
			btn.setEnabled(false);
			btn.setBackgroundResource(R.drawable.btn_qxzq_stop);
			break;
		}
	}
	/**
	 * ��ʽ��state������state��������ɫ��ֵ��TextView
	 * @param text   TextView
	 * @param state 
	 */
	private String formatTrackState(String state){
		String formatState = "";
		int stateInt = 0;
		stateInt = Integer.parseInt(state);
		switch (stateInt) {
		case 0:
			formatState =  this.getString(R.string.usercenter_str_runningNoParentheses);
			break;
		case 2:
			formatState = getString(R.string.usercenter_str_hasCancledNoParentheses);
			break;
		case 3:
			formatState =  getString(R.string.usercenter_str_hasClosedNoParentheses);
			break;
		}
		return formatState;
	}
	/**
	 * ���ȡ��׷�ŷ��ص��ǡ�000000��ʱ�Ĵ�����
	 */
	private void cancleTrackError000000(){
		windatalist.clear();
		isCancleTrackNet = true;
		pageindex = 0;
		trackQueryNet(0);
	}
}
