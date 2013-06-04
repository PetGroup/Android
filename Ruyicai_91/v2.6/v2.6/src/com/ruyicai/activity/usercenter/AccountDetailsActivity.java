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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.net.newtransaction.AccountDetailQueryInterface;
import com.ruyicai.net.newtransaction.pojo.AccountDetailQueryPojo;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.ShellRWSharesPreferences;
/**
 * �˻���ϸ
 * @author miao
 */
public class AccountDetailsActivity extends Activity{
	private int pageallindex=0,pagewithdrawindex=0,pagepayindex=0,pagesendprizeindex=0,pagechargeindex=0;
	private int[] linearId = {R.id.usercenterAccountdetailContent_all,R.id.usercenterAccountdetailContent_cz,R.id.usercenterAccountdetailContent_zf,
			R.id.usercenterAccountdetailContent_pj,R.id.usercenterAccountdetailContent_tx};
	private String[] titles = {"ȫ��","��ֵ","֧��","�ɽ�","����"};
	private LinearLayout linearall,linearcharge,linearpay,linearsendprize,linearwithdraw;
	private LayoutInflater mInflater = null;
	private Button returnButton;
	private String jsonString;
	ProgressDialog dialog;
	private final	String  AMT="amt",MEMO="memo",PLATTIME="platTime",TTRANSACTIONTYPE = "ttransactionType",TEXTCOLOR = "textcolor";
	private final int DIALOG1_KEY = 0;
	private String phonenum,sessionid,userno;
	List<Vector> alldatalist = new ArrayList<Vector>(); 
	List<Vector> paydatalist = new ArrayList<Vector>(); 
	List<Vector> sendprizesdatalist = new ArrayList<Vector>(); 
	List<Vector> withdrawdatalist = new ArrayList<Vector>(); 
	List<Vector> chargedatalist = new ArrayList<Vector>(); 
	String jsonobject;
	TabHost mTabHost;
	int  allPages,withdrawPages,chargePages,payPages,sendPrizePages;
	boolean isAllFirst=true,isChargeFirst=true,isSendPrizeFirst=true,isWithdrawFirst=true,isPayFirst=true;
	View tabSpecLinearView;//���б��ListView
	 ListView tabSpecListView;//���б��ListView
	 TextView pagetext;
	 ImageButton subpagebtn;
	 ImageButton addpagebtn;
	int type = 0;
	/**��������handler*/
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				Toast.makeText(AccountDetailsActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
				break;
			case 1:
				dialog.dismiss();
				allPages=encodejson((String) msg.obj,alldatalist);
				initLinear(linearall,linearId[0],initLinearView(pageallindex,allPages,alldatalist));
				break;
			case 2:
				dialog.dismiss();
				chargePages = encodejson((String) msg.obj,chargedatalist);
				initLinear(linearcharge,linearId[1],initLinearView(pagechargeindex,chargePages,chargedatalist));
				break;
			case 3:
				dialog.dismiss();
				payPages = encodejson((String) msg.obj,paydatalist);
				initLinear(linearpay,linearId[2],initLinearView(pagepayindex,payPages,paydatalist));
				break;
			case 4:
				dialog.dismiss();
				sendPrizePages = encodejson((String) msg.obj,sendprizesdatalist);
				initLinear(linearsendprize,linearId[3],initLinearView(pagesendprizeindex,sendPrizePages,sendprizesdatalist));
				break;
			case 5:
				dialog.dismiss();
				withdrawPages = encodejson((String) msg.obj,withdrawdatalist);
				initLinear(linearwithdraw,linearId[4],initLinearView(pagewithdrawindex,withdrawPages,withdrawdatalist));
				isWithdrawFirst = false;
				break;
			}
		}
	 };
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenter_accountdetail_layout);
		mTabHost = (TabHost) findViewById(R.id.usercenter_tab_host);
		mTabHost.setup();
		mInflater = LayoutInflater.from(this);
		for(int i=0;i<titles.length;i++){
			addTab(i); 
		}
		mTabHost.setOnTabChangedListener(accountTabChangedListener);
		returnButton = (Button)findViewById(R.id.usercenter_accountdetail_img_return);
		returnButton.setText(R.string.returnlastpage);
		initReturn();
		jsonobject = this.getIntent().getStringExtra("allaccountjson");
		allPages=encodejson(jsonobject,alldatalist);
		initLinear(linearall,linearId[0],initLinearView(pageallindex,allPages,alldatalist));
	}
	/**
	 * TabHost�л�������
	 */
	TabHost.OnTabChangeListener accountTabChangedListener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {	
			for(int i=0;i<titles.length;i++){
				if(tabId.equals(titles[0])){
					type = 0;
					initLinear(linearall,linearId[0],initLinearView(pageallindex,allPages,alldatalist));
				}else if(tabId.equals(titles[1])){
					type = 1;
					if(isChargeFirst){
						getAccountDataNet(0,1);
						isChargeFirst = false;
						break;
					}else{
						if(chargedatalist.size()!=0){
							initLinear(linearcharge,linearId[1],initLinearView(pagechargeindex,chargePages,chargedatalist));  
						}
					}
				}else if(tabId.equals(titles[2])){
					type = 2;
					if(isPayFirst){
						getAccountDataNet(0,2);
						isPayFirst = false;
						break;
					}else{
						if(paydatalist.size()!=0){
						  initLinear(linearpay,linearId[2],initLinearView(pagepayindex,payPages,paydatalist));
						}
					}
				}else if(tabId.equals(titles[3])){
					type = 3;
					if(isSendPrizeFirst){
						isSendPrizeFirst = false;
						getAccountDataNet(0,3);
						break;
					}else{
						if(sendprizesdatalist.size()!=0){
						initLinear(linearsendprize,linearId[3],initLinearView(pagesendprizeindex,sendPrizePages,sendprizesdatalist)); 
						}
					}
				}else if(tabId.equals(titles[4])){
					type = 4;
					if(isWithdrawFirst){
						getAccountDataNet(0,4);
						isWithdrawFirst = false;
						break;
					}else{
						if(withdrawdatalist.size()!=0){
						 initLinear(linearwithdraw,linearId[4],initLinearView(pagewithdrawindex,withdrawPages,withdrawdatalist));
						}
					}
				}
			}
		}
	};
	/**
	 * ��ShellRWSharesPreferences�л�ȡphonenum ��sessionid ��userno
	 */
	 private void initPojo(){
			ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this, "addInfo");
			phonenum = shellRW.getUserLoginInfo("phonenum");
			sessionid = shellRW.getUserLoginInfo("sessionid");
			userno = shellRW.getUserLoginInfo("userno");
	}
	 /**
	  * �˻���ϸ��ѯ����
	  * @param pageindexgift  ҳ��
	  * @param type	�˻���ϸ��ѯ����
	  */
	 private void getAccountDataNet(final int pageindexgift,final int type) {
		 showDialog(0);
			new Thread(new Runnable() {
				public void run() {
				initPojo();
				AccountDetailQueryPojo accountQueryPojo = new AccountDetailQueryPojo();
				accountQueryPojo.setUserno(userno);
				accountQueryPojo.setSessionid(sessionid);
				accountQueryPojo.setPhonenum(phonenum);
				accountQueryPojo.setPageindex(String.valueOf(pageindexgift));
				accountQueryPojo.setMaxresult("10");
				accountQueryPojo.setTransactiontype(String.valueOf(type));
				accountQueryPojo.setType("new");
				
				Message msg = new Message();
				jsonString = AccountDetailQueryInterface.getInstance().accountDetailQuery(accountQueryPojo);
					try {
						JSONObject aa = new JSONObject(jsonString);
						String errcode = aa.getString("error_code");
						String message = aa.getString("message");
						if(errcode.equals("0000")){
							setNewPage(pageindexgift);
							msg.what = type+1;
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
	 /**
	  * ��ʼ�����ڲ���Adapter��List
	  * @param pageindex
	  * @param giftdatalist
	  * @return
	  */
	  public List<Map<String, Object>> initAdapterlist(int pageindex,List<Vector> giftdatalist){
		  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		  	int listid=pageindex;
			for (int i = 0; i < giftdatalist.get(listid).size(); i++) {
				String type = (String)((AccountDetailQueryInfo) giftdatalist.get(listid).get(i)).getTtransactionType();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(AMT, ((AccountDetailQueryInfo) giftdatalist.get(listid).get(i)).getAmt());
				map.put(PLATTIME, ((AccountDetailQueryInfo) giftdatalist.get(listid).get(i)).getPlatTime());
				map.put(MEMO, ((AccountDetailQueryInfo) giftdatalist.get(listid).get(i)).getMemo());
				map.put(TTRANSACTIONTYPE, type);
				map.put(TEXTCOLOR,amountTextColor(type));
				list.add(map);
			}
			return list;
	  }  
	 /**
	  * ����������ȡ���ַ���
	  * @param json ������ȡ���ַ���
	  * @param list ��Ӧ�������͵�list����
	  */
	 public int encodejson(String json,List<Vector> list) {
	      int typeAllPage=0;
		  try {
			  JSONObject  winprizejsonobj = new JSONObject(json);
			  typeAllPage = Integer.parseInt(winprizejsonobj.getString("totalPage"));
			  String winprizejsonstring = winprizejsonobj.getString("result");
			  JSONArray winprizejson = new JSONArray(winprizejsonstring);
			  Vector<AccountDetailQueryInfo> accountInfos = new Vector<AccountDetailQueryInfo>(); 
			  for(int i=0;i<winprizejson.length();i++){
				try{
					AccountDetailQueryInfo accountDetailInfo = new AccountDetailQueryInfo();
					accountDetailInfo.setAmt(winprizejson.getJSONObject(i).getString(AMT));
					accountDetailInfo.setMemo(winprizejson.getJSONObject(i).getString(MEMO));
					accountDetailInfo.setPlatTime(winprizejson.getJSONObject(i).getString(PLATTIME));
					accountDetailInfo.setTtransactionType(winprizejson.getJSONObject(i).getString(TTRANSACTIONTYPE));
					accountInfos.add(accountDetailInfo);
				}catch (Exception e) {
				}
			 }
			 list.add(accountInfos);
		  	 } catch (JSONException e) {
		  		   try {
						JSONObject winprizejson = new JSONObject(json);
		  		        } catch (JSONException e1) {
		  		        }
		    }
		  	 return typeAllPage;
	     }
	 /**
	  * ΪTabHost���TabSpec
	  * @param index
	  */
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
	/**
	 * ��ʼ���˻���ϸ�����ҳ����Ϣ
	 * @param pageindex  ҳ��
	 * @param typeAllPages ��ҳ��
	 * @return ҳ����Ϣ��String��
	 */
	private String initPageTextView(int pageindex,int typeAllPages){
		StringBuffer str = new StringBuffer();
		String zi_gong = getString(R.string.usercenter_gong);
		String zi_ye = getString(R.string.usercenter_page);
		String zi_di = getString(R.string.usercenter_di);
		str.append(zi_di).append((pageindex+1)+"").append(zi_ye).append("  ").append(zi_gong)
		   .append(String.valueOf(typeAllPages)).append(zi_ye);
		return str.toString();
	}
	/**
	 * ��ʼ���˻���ϸTabHost�Ӳ���
	 * @param pageindex   �����Ӧ��ҳ��
	 * @param allpagenum  �����Ӧ����ҳ��
	 * @param typelist    �����Ӧ��List
	 * @param isfirst     ȷ�������ǲ��ǳ���������TabHost�л���������
	 * @return  ��ʼ��֮���view
	 */
	private View  initLinearView( final int pageindex,final int allpagenum,final List typelist){
		setAllPage(allpagenum);
		setList(typelist);
		LayoutInflater inflate = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tabSpecLinearView = (LinearLayout) inflate.inflate(R.layout.usercenter_listview_layout, null);
		tabSpecListView = (ListView) tabSpecLinearView.findViewById(R.id.usercenter_listview_queryinfo);
		pagetext = (TextView)tabSpecLinearView.findViewById(R.id.usercenter_text_pagetext);
		subpagebtn = (ImageButton)tabSpecLinearView.findViewById(R.id.usercenter_btn_subpage);
		addpagebtn = (ImageButton)tabSpecLinearView.findViewById(R.id.usercenter_btn_addpage);
		subpagebtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				subLisener();
			}
		});
		addpagebtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addLisener();
			}
		});
		pagetext.setText(initPageTextView(pageindex,allpagenum));
		List list = initAdapterlist(pageindex,typelist);
		initListView(tabSpecListView,list);
		return tabSpecLinearView;
	}
	/**
	 * �����ǰ��ť���õķ���
	 */
	private  void subLisener(){
		int pageindex = getNewPage();
		int allpagenum = getAllPage();
		List typelist = getList();
		pageindex--;
        if(pageindex<0){
        	pageindex = 0;
        	setNewPage(pageindex);
			Toast.makeText(AccountDetailsActivity.this,R.string.usercenter_hasgonefirst, Toast.LENGTH_SHORT).show();   
        }else{
        	setNewPage(pageindex);
     	   	List list = initAdapterlist(pageindex,typelist);
     	   	initListView(tabSpecListView,list);
        }
	}
	/**
	 * ������ť���õķ���
	 */
	private  void addLisener(){
		 int pageIndex = getNewPage();
		 int allpagenum = getAllPage();
		 List typelist = getList();
		 if( pageIndex > typelist.size()){
			pageIndex = typelist.size();
		 }
		 pageIndex++;
         if(pageIndex>allpagenum-1){
			   pageIndex=allpagenum-1;
			   setNewPage(pageIndex);
			   Toast.makeText(AccountDetailsActivity.this, R.string.usercenter_hasgonelast, Toast.LENGTH_SHORT).show();  
		   }else{
	        	if(pageIndex<typelist.size()){
	        		setNewPage(pageIndex);
	        		List list = initAdapterlist(pageIndex,typelist);
	        		initListView(tabSpecListView,list);
	        	}else{
	        		getAccountDataNet(pageIndex,type);
	        	}
		   }
    	
//         pagetext.setText(initPageTextView(pageIndex,allpagenum));
	}
	private void setNewPage(int page){
		switch(type){
		case 0:
			pageallindex = page;
			break;
		case 1:
			pagechargeindex = page;
			break;
		case 2:
			pagepayindex = page;
			break;
		case 3:
			pagesendprizeindex = page;
			break;
		case 4:
			pagewithdrawindex = page;
			break;
		}
	}
	private int getNewPage(){
		int page=0;
		switch(type){
		case 0:
			page = pageallindex ;
			break;
		case 1:
			page = pagechargeindex ;
			break;
		case 2:
			page = pagepayindex ;
			break;
		case 3:
			page = pagesendprizeindex ;
			break;
		case 4:
			page = pagewithdrawindex ;
			break;
		}
		return page;
	}
	private void setAllPage(int page){
	
		switch(type){
		case 0:
			allPages = page;
			break;
		case 1:
			chargePages = page;
			break;
		case 2:
			payPages = page;
			break;
		case 3:
			sendPrizePages = page;
			break;
		case 4:
			withdrawPages = page;
			break;
		}
	}
	private int getAllPage(){
		int page=0;
		switch(type){
		case 0:
			page = allPages;
			break;
		case 1:
			page = chargePages ;
			break;
		case 2:
			page = payPages ;
			break;
		case 3:
			page = sendPrizePages ;
			break;
		case 4:
			page = withdrawPages;
			break;
		}
		return page;
	}
	private void setList(List list){
		switch(type){
		case 0:
			alldatalist = list;
			break;
		case 1:
			chargedatalist = list;
			break;
		case 2:
			paydatalist = list;
			break;
		case 3:
			sendprizesdatalist = list;
			break;
		case 4:
			withdrawdatalist = list;
			break;
		}
	}
	private List getList(){
		List list = null;
		switch(type){
		case 0:
			list = alldatalist;
			break;
		case 1:
			list = chargedatalist;
			break;
		case 2:
			list = paydatalist;
			break;
		case 3:
			list = sendprizesdatalist;
			break;
		case 4:
			list = withdrawdatalist;
			break;
		}
		return list;
	}

	/**
	 * ��ʼ��ListView�б�
	 * @param listview
	 * @param list
	 */
	private void initListView(ListView listview,List list){
		int pageIndex = getNewPage();
		int allpagenum = getAllPage();
		pagetext.setText(initPageTextView(pageIndex,allpagenum));
		AccountQueryAdapter adapter = new AccountQueryAdapter(this,list);
		listview.setAdapter(adapter);
	}
	protected  void initReturn(){
		returnButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					finish();
			}
		});
	}
	/**
	 * �ڲ���pojo
	 */
    class AccountDetailQueryInfo{
		  private String platTime="";//�䶯ʱ��
		  private String memo="";//�˻���Ŀ��ϸ����
		  private String amt="";//�䶯���
		  private String  ttransactionType = "";//�˻�����
		  public String getTtransactionType() {
			return ttransactionType;
		  }
		  public void setTtransactionType(String ttransactionType) {
			this.ttransactionType = ttransactionType;
		  }
		  public String getPlatTime() {
			  return platTime;
		  }
		  public void setPlatTime(String platTime) {
			  this.platTime = platTime;
		  }
		  public String getMemo() {
			  return memo;
		  }
		  public void setMemo(String memo) {
			  this.memo = memo;
		  }
		  public String getAmt() {
			  return amt;
		  }	
		  public void setAmt(String amt) {
			  this.amt = amt;
		  }
     }
    /**
	 * �˻����Ĳ�ѯ��������
	 */
	public class AccountQueryAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater; // �������б���
			private List<Map<String, Object>> mList;
			public AccountQueryAdapter(Context context, List<Map<String, Object>> list) {
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
				 String accountMemo = (String) mList.get(position).get(MEMO);
			     String accountPlattime = (String) mList.get(position).get(PLATTIME);
				 String amt = (String) mList.get(position).get(AMT);
				 String amtType = (String) mList.get(position).get(TTRANSACTIONTYPE);
				 int amtColor = (Integer)mList.get(position).get(TEXTCOLOR);
				 String accountAmt = PublicMethod.formatAccountMoney(amt);
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.usercenter_accountdetails_listitem,null);
					holder = new ViewHolder();
					holder.memo = (TextView) convertView.findViewById(R.id.user_center_account_detail_trading_mode_id);
					holder.platTime= (TextView) convertView.findViewById(R.id.user_center_account_detail_trading_date_id);
					holder.amt = (TextView) convertView.findViewById(R.id.user_center_account_detail_yu_e_id);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.amt.setTextColor(amtColor);
				holder.memo.setText(accountMemo);
				holder.platTime.setText(accountPlattime);
				holder.amt.setText(accountAmt);
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
	 * �����û��˻��Ľ�������ttransactionType��������������ɫ
	 * @param tView TextView
	 * @param type  ttransactionType
	 */
	private int amountTextColor(String type){
		int Colortype = 1;
		int textColor = 0xff333333;
		try{
			Colortype = Integer.parseInt(type);
		}catch(Exception e){
		}
		switch (Colortype) {
		case 12:
		case 13:
		case 14:
		case 6:
		case 23:
			textColor =	0xffcc0000;//�н�Ϊ��ɫ
			break;
		case 9:
		case 2:
		case 3:
		case 10:
			textColor = 0xff4a4aff;//��ֵΪ��ɫ
			break;
		case 5:
			textColor = 0xff006600;//����Ϊ��ɫ
			return textColor;
		default:
			break;
		}
		return textColor;
	}
}
