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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.net.newtransaction.WinQueryInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.net.newtransaction.pojo.BetAndWinAndTrackAndGiftQueryPojo;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
/**
 * �н���ѯ����
 * @author Administrator
 *
 */
public class WinPrizeActivity extends Activity {
	private BetAndGiftPojo betPojo = new BetAndGiftPojo();
	private LinearLayout usecenerLinear;
	private Button returnButton;
	private TextView	titleTextView;
	String jsonString;
	ListView queryinfolist;
	final	String  BATCHCODE="batchCode",BETCODE="betCode",CASHTIME="cashTime",LOTNAME = "lotName",
	             LOTNO="lotNo",WINAMOUNT="winAmount",SELLTIME = "sellTime";
	private final int DIALOG1_KEY = 0;
	private String phonenum,sessionid,userno;
	List<WinPrizeQueryInfo> windatalist = new ArrayList<WinPrizeQueryInfo>(); 
	Context context = this ;
	ProgressDialog dialog;
	String jsonobject;
	int  allPage;
	int pageindex;
	boolean isfirst = false;
	WinPrizeAdapter adapter;
	private View view;
	private ProgressBar progressbar;
	 Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if(dialog !=null){
					dialog.dismiss();
				}
				Toast.makeText(WinPrizeActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
				break;
			case 1:
				encodejson((String) msg.obj);
                adapter.notifyDataSetChanged();
				break;
			}
		}
	 };
	public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.usercenter_mainlayoutold);
			
			returnButton = (Button)findViewById(R.id.layout_usercenter_img_return);
			titleTextView = (TextView)findViewById(R.id.usercenter_mainlayou_text_title);
			returnButton.setBackgroundResource(R.drawable.returnselecter);
			titleTextView.setText(R.string.usercenter_winningCheck);
			returnButton.setText(R.string.returnlastpage);
			returnButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					finish();
				}
			});
			jsonobject = this.getIntent().getStringExtra("winjson");
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
			RWSharedPreferences shellRW = new RWSharedPreferences(this, "addInfo");
			phonenum = shellRW.getStringValue("phonenum");
			sessionid = shellRW.getStringValue("sessionid");
			userno = shellRW.getStringValue("userno");
			
	}
	 
	private void netting(final int pageindex){
		progressbar.setVisibility(ProgressBar.VISIBLE);
		final Handler tHandler = new Handler();
		new Thread(new Runnable() {
			@Override
		public void run() {
		initPojo();
		BetAndWinAndTrackAndGiftQueryPojo winQueryPojo = new BetAndWinAndTrackAndGiftQueryPojo();
		winQueryPojo.setUserno(userno);
		winQueryPojo.setSessionid(sessionid);
		winQueryPojo.setPhonenum(phonenum);
		winQueryPojo.setPageindex(String.valueOf(pageindex));
		winQueryPojo.setMaxresult("10");
		winQueryPojo.setType("win");
		
			Message msg = new Message();
			jsonString = WinQueryInterface.getInstance().winQuery(winQueryPojo);
			tHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					progressbar.setVisibility(ProgressBar.INVISIBLE);
					view.setEnabled(true);
				}
			 });
			try {
				JSONObject aa = new JSONObject(jsonString);
				String errcode = aa.getString("error_code");
				String message = aa.getString("message");
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
	private void	getWinDataNet(final int pageindex){
		showDialog(0);
		new Thread(new Runnable() {
			public void run() {
		    netting(pageindex);
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
		initListView(queryinfolist, windatalist);
		return view;
	}

	private void initListView(ListView listview,List <WinPrizeQueryInfo>list){
		adapter = new WinPrizeAdapter(this, windatalist);
		LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    view = mInflater.inflate(R.layout.lookmorebtn,null);
	    progressbar=(ProgressBar)view.findViewById(R.id.getmore_progressbar);
		listview.addFooterView(view);
     	// ����Դ
		listview.setAdapter(adapter);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				progressbar.setVisibility(ProgressBar.VISIBLE);
				view.setEnabled(false);
	            addmore();
				
			}
		});
		
	}
	private void addmore(){
		isfirst = false;
		
        pageindex++;
        if(pageindex<allPage){
	       netting(pageindex);
		 }else{
	        pageindex=allPage-1;
	        progressbar.setVisibility(view.INVISIBLE);
			queryinfolist.removeFooterView(view);
			Toast.makeText(WinPrizeActivity.this,R.string.usercenter_hasgonelast, Toast.LENGTH_SHORT).show();   
		 }
 
	}
	public AlertDialog lookDetailDialog(String detail){
		LayoutInflater factory = LayoutInflater.from(this);
		/*�н���ѯ�Ĳ鿴����ʹ������ѯ�Ĳ���*/
		View	view = factory.inflate(R.layout.usercenter_balancequery, null);
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		TextView  title = (TextView)view.findViewById(R.id.usercenter_balancequery_title);
		title.setText(R.string.usercenter_detailsTitle);
		TextView	detailTextView = (TextView)view.findViewById(R.id.balanceinfo);
		detailTextView.setText(Html.fromHtml(PublicMethod.repleaceNtoBR(detail)));
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

	
	  public void encodejson(String json) {
		  
		  try {
			  JSONObject  winprizejsonobj = new JSONObject(json);
			  allPage = Integer.parseInt(winprizejsonobj.getString("totalPage"));
			  String maxpage = "";
			  String winprizejsonstring = winprizejsonobj.getString("result");
				
			  JSONArray winprizejson = new JSONArray(winprizejsonstring);
			  for(int i=0;i<winprizejson.length();i++){
				  try{
						WinPrizeQueryInfo winPrizeQueryinfo = new WinPrizeQueryInfo();
						winPrizeQueryinfo.setBatchCode(winprizejson.getJSONObject(i).getString(BATCHCODE));
						winPrizeQueryinfo.setBetCode(winprizejson.getJSONObject(i).getString(BETCODE));
						winPrizeQueryinfo.setCashTime(winprizejson.getJSONObject(i).getString(CASHTIME));
						winPrizeQueryinfo.setLotNo(winprizejson.getJSONObject(i).getString(LOTNO));
						winPrizeQueryinfo.setLotName(winprizejson.getJSONObject(i).getString(LOTNAME));
						winPrizeQueryinfo.setWinAmount(winprizejson.getJSONObject(i).getString(WINAMOUNT));
						winPrizeQueryinfo.setSellTime(winprizejson.getJSONObject(i).getString(SELLTIME));
						windatalist.add(winPrizeQueryinfo);
				  }catch (Exception e) {
				  }
			  }
			 
			  } catch (JSONException e) {
			  		try {
						JSONObject winprizejson = new JSONObject(json);
			  		} catch (JSONException e1) {
			  		}
			  }
	  }
	class WinPrizeQueryInfo{
		private String winAmount;
		private String batchCode;
		private String lotNo;
		private String lotName;
		private String betCode;
		private String cashTime;
		private String sellTime;
		private String bet_code;
		public String getLotName() {
			return lotName;
		}
		public void setLotName(String lotName) {
			this.lotName = lotName;
		}
	
		public String getBet_code() {
			return bet_code;
		}
		public void setBet_code(String betCode) {
			bet_code = betCode;
		}
		public String getSellTime() {
			return sellTime;
		}
		public void setSellTime(String sellTime) {
			this.sellTime = sellTime;
		}
		public String getWinAmount() {
			return winAmount;
		}
		public void setWinAmount(String winAmount) {
			this.winAmount = winAmount;
		}
		public String getBatchCode() {
			return batchCode;
		}
		public void setBatchCode(String batchCode) {
			this.batchCode = batchCode;
		}
		public String getLotNo() {
			return lotNo;
		}
		public void setLotNo(String lotNo) {
			this.lotNo = lotNo;
		}
		public String getBetCode() {
			return betCode;
		}
		public void setBetCode(String betCode) {
			this.betCode = betCode;
		}
		public String getCashTime() {
			return cashTime;
		}
		public void setCashTime(String cashTime) {
			this.cashTime = cashTime;
		}
	}
	/**
	 * �н���ѯ��������
	 */
	public class WinPrizeAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater; // �������б���
			private List<WinPrizeQueryInfo> mList;
			public WinPrizeAdapter(Context context, List<WinPrizeQueryInfo> list) {
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
				final String lotno = (String) mList.get(position).getLotNo();
				final String lotName = (String) mList.get(position).getLotName();
				final 	String prizeqihao = (String) mList.get(position).getBatchCode();
				String winAmount = (String) mList.get(position).getWinAmount();
				final String prizemoney = PublicMethod.formatMoney(winAmount);
				final String cashtime = (String) mList.get(position).getCashTime();
				final String sellTime = (String) mList.get(position).getSellTime();
				final String betcode = (String) mList.get(position).getBetCode();
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.usercenter_listitem_winprize_query,null);
					holder = new ViewHolder();
					holder.lotteryname = (TextView) convertView.findViewById(R.id.usercenter_winprize_lotteryname);
					holder.prizeqihao = (TextView) convertView.findViewById(R.id.usercenter_winprize_prizeqihao);
					holder.prizemoney = (TextView) convertView.findViewById(R.id.usercenter_winprize_paymoney);
					holder.paytime = (TextView) convertView.findViewById(R.id.usercenter_winprize_prizemoney);
					holder.lookdetail = (Button)convertView.findViewById(R.id.usercenter_winprize_querydetail);
					holder.buyagain = (Button)convertView.findViewById(R.id.usercenter_winprize_buyagain);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				String prizeString = getString(R.string.usercenter_prizeMoney);//�н������
				holder.lotteryname.setText(lotName);
				if(lotno.equals("J00001")){
					holder.prizeqihao.setVisibility(TextView.GONE);
				}else{
					holder.prizeqihao.setText("(�ں�:"+prizeqihao+")");
				}
				holder.paytime .setText(sellTime);
				holder.prizemoney.setText(Html.fromHtml(prizeString+"<font color=\"red\">"+prizemoney+"</font>"));
				holder.buyagain.setVisibility(Button.GONE);
				holder.lookdetail.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						StringBuffer detailinfo = new StringBuffer();
						if(lotno.equals("J00001")){
							detailinfo.append(getString(R.string.usercenter_lotterytypename)).append(lotName).append("<br>")
							.append(getString(R.string.usercenter_bettingTime)).append(sellTime).append("<br>")
							.append(getString(R.string.usercenter_endTime)).append(cashtime).append("<br>")
							.append(getString(R.string.usercenter_prizeMoney)).append("<font color=\"red\">"+prizemoney+"</font>").append("<br>")
							.append(getString(R.string.usercenter_betcode)).append("<br>")
							.append(betcode);
							
						}else{
							detailinfo.append(getString(R.string.usercenter_lotterytypename)).append(lotName).append("<br>")
							.append(getString(R.string.usercenter_lotteryIssue)).append(prizeqihao).append("<br>")
							.append(getString(R.string.usercenter_bettingTime)).append(sellTime).append("<br>")
							.append(getString(R.string.usercenter_endTime)).append(cashtime).append("<br>")
							.append(getString(R.string.usercenter_prizeMoney)).append("<font color=\"red\">"+prizemoney+"</font>").append("<br>")
							.append(getString(R.string.usercenter_betcode)).append("<br>")
							.append(betcode);
						}
						
						lookDetailDialog(""+detailinfo);
					}
				});
				return convertView;
			}
			class ViewHolder {
				TextView lotteryname;
				TextView prizeqihao;
				TextView paytime;//����ʱ��
				TextView prizemoney;
				Button   lookdetail;
				Button  buyagain;
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
}
