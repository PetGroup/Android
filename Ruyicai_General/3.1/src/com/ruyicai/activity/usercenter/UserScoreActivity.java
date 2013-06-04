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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.net.newtransaction.QueryintegrationInterface;
import com.ruyicai.net.newtransaction.ScroechangeInterface;
import com.ruyicai.net.newtransaction.UserScoreDetailQueryInterface;
import com.ruyicai.net.newtransaction.pojo.UserScroeDetailQueryPojo;
import com.ruyicai.util.RWSharedPreferences;
/**
 * ���ֲ�ѯ
 * @author Administrator
 *
 */
public class UserScoreActivity extends Activity {
	private int[] linearId = {R.id.usercenterscroedetail,R.id.usercenterscroechange};
	private String[] titles = {"������ϸ","���ֶһ�"};
	TabHost mTabHost;
	private LayoutInflater mInflater = null;
	private Button returnButton;
	private String jsonString;
	String jsonobject;
	ProgressDialog dialog;
	private final int DIALOG1_KEY = 0;
	private String phonenum,sessionid,userno;
	private LinearLayout scroedetail,scroechange;
	private TextView nowscore,morescroe;
	Button  more;
	String myscore;
	int  scroePages;
	int  scroePgaeIndex=0;
	View tabSpecLinearView;//���б��ListView
    ListView tabSpecListView;//���б��ListView
//    List<Vector> scroelist = new ArrayList<Vector>(); 
    EditText scroeEdit ;
    TextView sroremoney;
    ScroeQueryAdapter adapter;
    List<ScroeDetailQueryInfo> scroeInfos = new ArrayList<ScroeDetailQueryInfo>(); 
    ProgressBar progressbar;
	View view;
    
    /**��������handler*/
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if(dialog !=null){
					dialog.dismiss();
				}
				Toast.makeText(UserScoreActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
				break;
			case 1:
				scroePages=encodejson((String) msg.obj,scroeInfos);
				adapter.notifyDataSetChanged();
				break;
			case 2:
				if(dialog !=null){
					dialog.dismiss();
				}
				scroeEdit.setText("");
				sroremoney.setText("");
				Toast.makeText(UserScoreActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
				getusermessage();
				break;
			case 3:
//				Toast.makeText(UserScoreActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
				nowscore.setText(myscore);
			}
		}
	 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userscoredetail);
		mTabHost = (TabHost) findViewById(R.id.usercenter_tab_host);
		mTabHost.setup();
		mInflater = LayoutInflater.from(this);
		for(int i=0;i<titles.length;i++){
			addTab(i); 
		}
		mTabHost.setOnTabChangedListener(scroeTabChangedListener);
		returnButton = (Button)findViewById(R.id.usercenter_accountdetail_img_return);
		returnButton.setText(R.string.returnlastpage);
		morescroe = (TextView)(findViewById(R.id.userscroe_tophuoqu));
	    initsroreshow();
		
		initReturn();
		nowscore = (TextView)findViewById(R.id.userscroe_topscroe);
		jsonobject = this.getIntent().getStringExtra("scroe");
		myscore = this.getIntent().getStringExtra("myscroe");
		nowscore.setText(myscore);
		scroePages=encodejson(jsonobject,scroeInfos);
		initLinear(scroedetail,linearId[0],initLinearView(scroePgaeIndex,scroeInfos));
	}
    
	protected  void initReturn(){
		returnButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					finish();
			}
		});
	}
	
	protected void initsroreshow() {
		morescroe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ScroesRules(UserScoreActivity.this);
			}
		});
	}
	public void addTab(int index){
		View indicatorTab = mInflater.inflate(R.layout.layout_nav_item, null);
		ImageView img = (ImageView) indicatorTab.findViewById(R.id.layout_nav_item);
		TextView title = (TextView) indicatorTab.findViewById(R.id.layout_nav_icon_title);
		img.setBackgroundResource(R.drawable.tab_buy_selector);
		title.setText(titles[index]);
		TabHost.TabSpec tabSpec = mTabHost.newTabSpec(titles[index]).setIndicator(indicatorTab).setContent(linearId[index]);
		mTabHost.addTab(tabSpec);
	}
	
	/**
	 * ��ʼ���˻���ϸTabHost�Ӳ���
	 * @param pageindex   �����Ӧ��ҳ��
	 * @param allpagenum  �����Ӧ����ҳ��
	 * @param typelist    �����Ӧ��List
	 * @param isfirst     ȷ�������ǲ��ǳ���������TabHost�л���������
	 * @return  ��ʼ��֮���view
	 */
	private View  initLinearView( final int pageindex,final List typelist){
		LayoutInflater inflate = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tabSpecLinearView = (LinearLayout) inflate.inflate(R.layout.usercenter_listview_layout, null);
		tabSpecListView = (ListView) tabSpecLinearView.findViewById(R.id.usercenter_listview_queryinfo);
		initListView(tabSpecListView,scroeInfos);
		return tabSpecLinearView;
	}
	
	/**
	 * ��ʼ��ListView�б�
	 * @param listview
	 * @param list
	 */
	private void initListView(ListView listview,List list){
		LayoutInflater	mInflater = LayoutInflater.from(this);
		view = mInflater.inflate(R.layout.lookmorebtn, null);
	    progressbar=(ProgressBar)view.findViewById(R.id.getmore_progressbar);
	    adapter = new ScroeQueryAdapter(this,list);
	    listview.addFooterView(view);
		listview.setAdapter(adapter);	
        view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				view.setEnabled(false);
				// TODO Auto-generated method stub
	            addmore();

				
			}
		});
	}
	
	 /**
	  * ��ʼ�����ڲ���Adapter��List
	  * @param pageindex
	  * @param giftdatalist
	  * @return
	  */
//	  public List<Map<String, Object>> initAdapterlist(int pageindex,List<Vector> giftdatalist){
//		  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
//		  	int listid=pageindex;
//		  for(int j=0;j<giftdatalist.size();j++){
//			for (int i = 0; i < giftdatalist.get(j).size(); i++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("amt", ((ScroeDetailQueryInfo) giftdatalist.get(j).get(i)).getAmt());
//				map.put("time", ((ScroeDetailQueryInfo) giftdatalist.get(j).get(i)).getTime());
//				map.put("type", ((ScroeDetailQueryInfo) giftdatalist.get(j).get(i)).getType());
//				list.add(map);
//			 }
//		  	}
//			return list;
//		  	
//	  }  
	/**
	 * TabHost�л�������
	 */
	TabHost.OnTabChangeListener scroeTabChangedListener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {	
			for(int i=0;i<titles.length;i++){
				if(tabId.equals(titles[0])){
//				initLinear(scroedetail, linearId[0], view);
				}else if(tabId.equals(titles[1])){
				initLinear(scroedetail, linearId[1], initscorechangeview());	
					}
				}
		}
	};
	
	 private void getusermessage(){
			 initPojo();
		    nowscore.setText("��ѯ��...");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				 	jsonString = QueryintegrationInterface.getInstance().queryintegration(phonenum, sessionid, userno);
				 	Message msg = new Message();
				 	try{
				 		JSONObject json = new JSONObject(jsonString);
				 		myscore = json.getString("score");
				 		msg.what=3;
				 		handler.sendMessage(msg);
				 	}catch(JSONException e){
				 		msg.what=0;
				 		handler.sendMessage(msg);
				 	}
				}
			}).start();
		   }
	
   protected View initscorechangeview(){
	   
	 View view =  mInflater.inflate(R.layout.usercenterscorechange, null);
	 scroeEdit = (EditText) view.findViewById(R.id.scroechange);
	 scroeEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(!scroeEdit.getText().toString().equals("")){
				sroremoney.setText(""+Integer.valueOf(scroeEdit.getText().toString())/500);
				}
			}
		});
	 sroremoney = (TextView) view.findViewById(R.id.sroremoney);	 
	 Button   ok = (Button)view.findViewById(R.id.change);
	 ok.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		final String  scroe = scroeEdit.getText().toString();
		
//		final String  money = sroremoney.getText().toString();
		if(scroe.equals("")){
			
			Toast.makeText(UserScoreActivity.this, "�������ֲ���Ϊ��", Toast.LENGTH_SHORT).show();
		}else if((Integer.valueOf(scroe)%500!=0)){
			
			Toast.makeText(UserScoreActivity.this, "������500�ı���", Toast.LENGTH_SHORT).show();
			
		}else if(Integer.valueOf(scroe)>Integer.valueOf(myscore)){
			Toast.makeText(UserScoreActivity.this, "���Ļ��ֲ���", Toast.LENGTH_SHORT).show();
		}else{
			 initPojo();
		     showDialog(0);
		    new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				// TODO Auto-generated method stub
				UserScroeDetailQueryPojo scroeDetailPojo = new UserScroeDetailQueryPojo();
				scroeDetailPojo.setUserno(userno);
				scroeDetailPojo.setSessionid(sessionid);
				scroeDetailPojo.setPhonenum(phonenum);
				scroeDetailPojo.setType("transScore2Money");
				scroeDetailPojo.setScroe(scroe);
				Message msg = new Message();
				jsonString = ScroechangeInterface.getInstance().scroeDetailQuery(scroeDetailPojo);
				try {
					JSONObject aa = new JSONObject(jsonString);
					String errcode = aa.getString("error_code");
					String message = aa.getString("message");
					if(errcode.equals("0000")){
						msg.what = 2;
						msg.obj = message;
						handler.sendMessage(msg);		
					}else{
						msg.what = 0;
						msg.obj = message;
						handler.sendMessage(msg);	
					}
					}catch(JSONException e){
						Toast.makeText(UserScoreActivity.this, "�����쳣", Toast.LENGTH_SHORT).show();
		               	}
		     	}
			}).start();
		}
		}
	});
	 return  view;
	}
	/**
	 * ��ʼ��LinearLayout,ΪTabHost�µ�LinearLayout���View
	 * @param linear  Ҫ��ʼ����LinearLayout
	 * @param linearid LinearLayout��Ӧ��Id
	 * @param view    Ҫ��ӵ�View
	 */
	private void initLinear(LinearLayout linear,int linearid,View view) {
		linear = (LinearLayout)findViewById(linearid);
		linear.removeAllViews();
		linear.addView(view);
	}
	 public int encodejson(String json,List<ScroeDetailQueryInfo> list) {
	      int typeAllPage=0;
		  try {
			  JSONObject  scorejsonobj = new JSONObject(json);
			  typeAllPage = Integer.parseInt(scorejsonobj.getString("totalPage"));
			  String scroedetailjsonstring = scorejsonobj.getString("result");
			  JSONArray scroedetail = new JSONArray(scroedetailjsonstring);
			  for(int i=0;i<scroedetail.length();i++){
				try{
					ScroeDetailQueryInfo scoreDetailInfo = new ScroeDetailQueryInfo();
					scoreDetailInfo.setAmt(scroedetail.getJSONObject(i).getString("score"));
					scoreDetailInfo.setType(scroedetail.getJSONObject(i).getString("scoreSource"));
					scoreDetailInfo.setTime(scroedetail.getJSONObject(i).getString("createTime"));
					scoreDetailInfo.setBlsign(scroedetail.getJSONObject(i).getString("blsign"));
					scroeInfos.add(scoreDetailInfo);
				}catch (Exception e) {
				}
			 }
		  	 } catch (JSONException e) {
		  		   try {
						JSONObject winprizejson = new JSONObject(json);
		  		        } catch (JSONException e1) {
		  		        }
		    }
	        
		  	 return typeAllPage;
	     }
	 
		/**
		 * ������ť���õķ���
		 */
		private  void addmore(){
			
			 scroePgaeIndex++;
	         if(scroePgaeIndex>scroePages-1){
	        	 scroePgaeIndex=scroePages-1;
	        	 progressbar.setVisibility(view.INVISIBLE);
	        	 tabSpecListView.removeFooterView(view);
				 Toast.makeText(UserScoreActivity.this, R.string.usercenter_hasgonelast, Toast.LENGTH_SHORT).show();  
			   }else{  	    
		         netting();
			   }
	      
		}
	 
		/**
		 * ��ShellRWSharesPreferences�л�ȡphonenum ��sessionid ��userno
		 */
		 private void initPojo(){
				RWSharedPreferences shellRW = new RWSharedPreferences(this, "addInfo");
				phonenum = shellRW.getStringValue("phonenum");
				sessionid = shellRW.getStringValue("sessionid");
				userno = shellRW.getStringValue("userno");
		}
		 
		 
		 
		 public void netting(){
		  progressbar.setVisibility(ProgressBar.VISIBLE);
		  final Handler tHandler = new Handler();
		  new Thread(new Runnable() {
					@Override
			 public void run() {
			    initPojo();
				UserScroeDetailQueryPojo scroeDetailPojo = new UserScroeDetailQueryPojo();
				scroeDetailPojo.setUserno(userno);
				scroeDetailPojo.setSessionid(sessionid);
				scroeDetailPojo.setPhonenum(phonenum);
				scroeDetailPojo.setPageindex(String.valueOf(scroePgaeIndex));
				scroeDetailPojo.setMaxresult("10");
				scroeDetailPojo.setType("scoreDetail");
		
				Message msg = new Message();
				jsonString = UserScoreDetailQueryInterface.getInstance().scroeDetailQuery(scroeDetailPojo);
				tHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressbar.setVisibility(ProgressBar.GONE);
						view.setEnabled(true);
					}
				 });
				try {
					JSONObject aa = new JSONObject(jsonString);
					String errcode = aa.getString("error_code");
					String message = aa.getString("message");
					if(errcode.equals("0047")){
					
						msg.what = 0;
						msg.obj = message;
						handler.sendMessage(msg);					
				}else if(errcode.equals("0000")){
						msg.what = 1;
						msg.obj = jsonString;
						handler.sendMessage(msg);
				}else if(errcode.equals("9999")){
					
					   msg.what = 0;
					   msg.obj = message;
					   handler.sendMessage(msg);					
				}
				} catch (Exception e) {
				
				}
			}
		}).start();
				
      }
		 /**
		  * ������ϸ����
		  * @param pageindexgift  ҳ��
		  * @param type	�˻���ϸ��ѯ����
		  */
		 private void getScroeDataNet(final int pageindexgift) {
//			 showDialog(0);
				new Thread(new Runnable() {
					public void run() {
                     netting();		
					}
				}).start();
		}
	 /**
		 * �˻����Ĳ�ѯ��������
		 */
		public class ScroeQueryAdapter extends BaseAdapter {
			  
			private LayoutInflater mInflater; // �������б���
				private List<ScroeDetailQueryInfo> mList;
				public ScroeQueryAdapter(Context context, List<ScroeDetailQueryInfo> list) {
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
					 String accountMemo = (String) mList.get(position).getType();
				     String accountPlattime = (String) mList.get(position).getTime();
					 String amt = (String) mList.get(position).getAmt();
					 String blsign = (String) mList.get(position).getBlsign();
					if (convertView == null) {
						convertView = mInflater.inflate(R.layout.userscroe_listitem,null);
						holder = new ViewHolder();
						holder.memo = (TextView) convertView.findViewById(R.id.user_center_account_detail_trading_mode_id);
						holder.platTime= (TextView) convertView.findViewById(R.id.user_center_account_detail_trading_date_id);
						holder.amt = (TextView) convertView.findViewById(R.id.user_center_account_detail_yu_e_id);
					} else {
						holder = (ViewHolder) convertView.getTag();
					}
//					holder.amt.setTextColor("");
					holder.memo.setText(accountMemo);
					holder.platTime.setText(accountPlattime);
					if(blsign.equals("-1")){
						holder.amt.setTextColor(Color.rgb(33, 66, 33));
						holder.amt.setText("-"+amt);	
					}else if(blsign.equals("1")){
						holder.amt.setTextColor(Color.RED);
						holder.amt.setText("+"+amt);
					}
					convertView.setTag(holder);
					return convertView;
				}
				class ViewHolder {
					TextView amt;//�˻��䶯���
					TextView memo;//�˻��䶯��ʾ
					TextView platTime;//�˻��䶯ʱ��
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
	 * �ڲ���pojo
	 */
    class ScroeDetailQueryInfo{
		  private String type="";//��ȡ��������
		  private String time="";//��ȡ����ʱ��
		  private String amt="";//����
		  private String blsign="";
		  public String getBlsign() {
			return blsign;
		}
		public void setBlsign(String blsign) {
			this.blsign = blsign;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getAmt() {
			return amt;
		}
		public void setAmt(String amt) {
			this.amt = amt;
		}
		
		  
     }
}
