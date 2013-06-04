package com.ruyicai.activity.buy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.activity.buy.dlt.Dlt;
import com.ruyicai.activity.buy.eleven.Eleven;
import com.ruyicai.activity.buy.fc3d.Fc3d;
import com.ruyicai.activity.buy.jc.lq.LqMainActivity;
import com.ruyicai.activity.buy.jc.zq.ZqMainActivity;
import com.ruyicai.activity.buy.pl3.PL3;
import com.ruyicai.activity.buy.pl5.PL5;
import com.ruyicai.activity.buy.qlc.Qlc;
import com.ruyicai.activity.buy.qxc.QXC;
import com.ruyicai.activity.buy.ssc.Ssc;
import com.ruyicai.activity.buy.ssq.Ssq;
import com.ruyicai.activity.buy.twentytwo.TwentyTwo;
import com.ruyicai.activity.buy.zc.FootballLottery;
import com.ruyicai.activity.expert.ExpertActivity;
import com.ruyicai.activity.info.LotInfoActivity;
import com.ruyicai.activity.join.JoinInfoActivity;
import com.ruyicai.activity.more.ActionActivity;
import com.ruyicai.activity.more.LuckChoose;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.custom.gallery.Lights;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.dialog.MessageDialog;
import com.ruyicai.dialog.ShortcutDialog;
import com.ruyicai.net.newtransaction.PrizeRankInterface;
import com.ruyicai.net.newtransaction.TopNewsInformationInterface;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
/**
 * ���ʴ�������
 * 
 * @author Administrator
 * 
 */
public class BuyActivity extends Activity implements OnClickListener {
	String messagetitle;
	String messagedetail;
	String messageerrorcode;
	String userno,phonenum,sessionid;
	private String messageidflag = null;
	private JSONObject obj;
	private int SCREENMAX = 9;// ��Ļ���ͼ����
	private int SCREENUM = 4;// ��Ļ�����
	private int SCREEALL = 0;// ��Ļ��ͼ����
	private int PRIZERANKSCREEN = 1;//�¼��Ъ�����
	private int HEIGHT = 0;
	ProgressDialog progressdialog;
	String newstitle="";
	String newscontent="";
	private int top = 20;
	private List<String> mLabelArray = new ArrayList<String>();
	private int[] imageViews = { R.drawable.ico_buy, R.drawable.ico_double,R.drawable.ico_super,
			R.drawable.ico_3d, R.drawable.ico_timec,R.drawable.ico_115,R.drawable.ico_basketball,
			R.drawable.icon_jc, R.drawable.ico_eleven,R.drawable.ico_expert,
			R.drawable.ico_three,R.drawable.ico_seven, R.drawable.twentyfive,R.drawable.icon_pl5,
			R.drawable.icon_qxc ,R.drawable.ico_goalin,};
	private String[] imageTitle = { "�������", "˫ɫ��", "����͸", "����3D", "ʱʱ��",
			"11ѡ5", "��������", "��������", "11�˶��","ר�Ҽ���", "������", "���ֲ�","22ѡ5","������","���ǲ�","���"};
	private final Class[] cla = { JoinInfoActivity.class, Ssq.class,Dlt.class,
			Fc3d.class, Ssc.class,Dlc.class,LqMainActivity.class,  ZqMainActivity.class,Eleven.class,ExpertActivity.class,PL3.class,
			Qlc.class,TwentyTwo.class,PL5.class, QXC.class,FootballLottery.class};
	private int[] imgViewsId = { R.id.mainpage_hemai_sign,
			R.id.mainpage_ssq_sign, R.id.mainpage_fc3d_sign,
			R.id.mainpage_11x5_sign, R.id.mainpage_dlt_sign,
			R.id.mainpage_ssc_sign, R.id.mainpage_qlc_sign,
			R.id.mainpage_pl3_sign, R.id.mainpage_zucai_sign ,
			R.id.mainpage_10_sign, R.id.mainpage_11_sign,
			R.id.mainpage_12_sign,R.id.mainpage_13_sign,
			R.id.mainpage_14_sign,R.id.mainpage_15_sign};
	private int[] textViewId = { R.id.mainpage_hemai_text,
			R.id.mainpage_ssq_text, R.id.mainpage_fc3d_text,
			R.id.mainpage_11x5_text, R.id.mainpage_dlt_text,
			R.id.mainpage_ssc_text, R.id.mainpage_qlc_text,
			R.id.mainpage_pl3_text, R.id.mainpage_zucai_text,
			R.id.mainpage_10_text, R.id.mainpage_11_text,
			R.id.mainpage_12_text, R.id.mainpage_13_text,
			R.id.mainpage_14_text, R.id.mainpage_15_text
			};
	private String[] imageTitleFirst = { "�������", "˫ɫ��", "����͸", "����3D", "ʱʱ��",
			"11ѡ5", "��������", "��������", "11�˶��"};
	private String[] imageTitleSecond = { "ר�Ҽ���", "������", "���ֲ�","22ѡ5","������","���ǲ�","���"};
	private final Class[] claFirst = { JoinInfoActivity.class, Ssq.class,Dlt.class,
			Fc3d.class, Ssc.class,Dlc.class,LqMainActivity.class,  ZqMainActivity.class,Eleven.class};
	private final Class[] claSecond = {ExpertActivity.class,PL3.class,
			Qlc.class,TwentyTwo.class,PL5.class, QXC.class,FootballLottery.class};
	private int[] imageViewsFirst = { R.drawable.ico_buy, R.drawable.ico_double,R.drawable.ico_super,
			R.drawable.ico_3d, R.drawable.ico_timec,R.drawable.ico_115,R.drawable.ico_basketball,
			R.drawable.icon_jc, R.drawable.ico_eleven};
	private int[] imageViewsSecond = { R.drawable.ico_expert,
			R.drawable.ico_three,R.drawable.ico_seven, R.drawable.twentyfive,R.drawable.icon_pl5,
			R.drawable.icon_qxc ,R.drawable.ico_goalin};
	
	
	
	
	// ����ViewPagerΪ��ҳ��ʾ�ؼ���PagerTitleStrip������ʾ��ǰҳ��ı���
	private ViewPager viewPagerContainer;
	// ������Ҫ���һ�������ͼȺ���б�����
	private List<View> viewsBufList;
	private Lights lights;
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressdialog.dismiss();
				MessageDialog newsdialog = new MessageDialog(BuyActivity.this,newstitle,newscontent);
				newsdialog.showDialog();
				newsdialog.createMyDialog();
				break;
			case 2:
				progressdialog.dismiss();
				break;
			case 4:
				MessageDialog dialog = new MessageDialog(BuyActivity.this,messagetitle,messagedetail);
				dialog.showDialog();
				dialog.createMyDialog();
				break;
			}
		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.buy_activity);
		progressdialog = UserCenterDialog.onCreateDialog(this);
		HEIGHT = getWindowManager().getDefaultDisplay().getHeight();//��Ļ�ĸ߶�
		viewPagerContainer = (ViewPager) findViewById(R.id.viewpager);
		initNumber();
		initLights();
		initGallery();
		initImgView();
		initRollingText();
		isShortcut();
	}
	/**
	 * ���ݲ�ͬ�ֻ��ֱ��ʣ���ʼ����Ļͼ������
	 */
	public void initNumber() {
		SCREEALL = imageViews.length;
		switch (HEIGHT) {
		case 320:
			SCREENMAX = 6;
			top = 10;
			break;
		case 480:
			SCREENMAX = 9;
			top = 5;
			break;
		case 854:
			SCREENMAX = 9;
			top = 30;
			break;
		case 800:
			SCREENMAX = 9;
			top = 25;
			break;
		default:
			SCREENMAX = 9;
			top = 50;
			break;
		}
		if(SCREEALL%SCREENMAX==0){
			SCREENUM =  SCREEALL/SCREENMAX;
		}else{
			SCREENUM =  SCREEALL/SCREENMAX+1;
		}
		SCREENUM += PRIZERANKSCREEN;//������Ļ�������¼ӵķǹ���ҳ������� 
		for (int i = 0; i < SCREENUM; i++) {
			mLabelArray.add("" + i);
		}
	}
	/**
	 * ��ʼ������
	 */
	public void initGallery() {
		// �����Ҫ���һ���Ч������ͼ������������
		viewsBufList = new ArrayList<View>();
		viewsBufList.add(new GalleryViewItem(getApplicationContext(), 0));
		viewsBufList.add(new GalleryViewItem(getApplicationContext(), 1));
		viewsBufList.add(new GalleryViewItem(getApplicationContext(), 2));
		// ���� ViewPager �� Adapter
		viewPagerContainer.setAdapter(new MainViewPagerAdapter());
		// ���õ�һ��ʾҳ���� ��2��View
		viewPagerContainer.setCurrentItem(1);
		// ���� ViewPagerҳ���л���������
		viewPagerContainer.setOnPageChangeListener(viewPagerPageChangeListener);
	}
	/**
	 * ��ʼ����Ļ��
	 */
	public void initLights() {
		lights = new Lights(this);
		lights.setIsAnim(false);
		LinearLayout layout = (LinearLayout) findViewById(R.id.buy_activity_light_layout);
		int[] images = { R.drawable.buy_radio, R.drawable.buy_radio_b };
		lights.createViews(SCREENUM, images, layout);
		lights.isDefault(1);// 20120709 tangzhihua Ĭ����ʾ�ڶ�ҳ
	}
	/**
	 * ��ʼ��imageView��ť
	 */
	public void initImgView() {
		int[] buttons = { R.id.mainpage_usercenter, R.id.mainpage_luck_sign,
				R.id.mainpage_help };
		for (int j = 0; j < buttons.length; j++) {
			Button button = (Button) findViewById(buttons[j]);
			button.setBackgroundResource(R.drawable.join_info_btn_selecter);
			button.setOnClickListener(this);
		}
	}
	/**
	 * ��������
	 */
	public void informationNet(){
		progressdialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String jsonStr;
				jsonStr = TopNewsInformationInterface.informationQuery();
				try {
					Message msg = new Message();
					JSONObject obj = new JSONObject(jsonStr);
					newstitle = obj.getString("title");
					newscontent = obj.getString("content");
					msg.what=1;
					handler.sendMessage(msg);
					
				} catch (JSONException e) {
					Message msg = new Message();
					msg.what=2;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * ��ֹ�����Ϣ�ַ��� ��ʽΪ���Ÿ���
	 */
	public String[] splitStr(String str, int with) {
		String strss[] = str.split(",");
		if (strss.length == 0) {
			int indexs = str.length() / with + 1;
			String strs[] = new String[indexs];
			for (int i = 0; i < indexs; i++) {
				if (i == indexs - 1) {
					strs[i] = str.substring(i * with, str.length());
				} else {
					strs[i] = str.substring(i * with, with * (i + 1));
				}
			}
			return strs;
		}
		return strss;
	}

	public View addTextByText(String text) {
		TextView tv = new TextView(this);
		tv.setText(text);
		tv.setGravity(1);
		tv.setTextSize(15);
		tv.setTextColor(Color.BLACK);
		return tv;
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onResume() {
		super.onResume();
		Constants.MEMUTYPE = 0;
	}

	protected void onPause() {
		super.onPause();
	}

	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mainpage_usercenter:
			tendToUserCenter();
			break;
		case R.id.mainpage_luck_sign:
			tendToLuckCenter();
			break;
		case R.id.mainpage_help:
			tendToActionCenter();
			break;
		}
	}
	 /**
	  * ��Ʊ��Ѷ
	  */
	public void tendToUserCenter() {
	        Intent intent = new Intent(BuyActivity.this, LotInfoActivity.class);
		    startActivity(intent);
	}

	public void tendToActionCenter() {
		Intent intent = new Intent(BuyActivity.this, ActionActivity.class);
		startActivity(intent);
	}
	
	public void tendToLuckCenter() {
		Intent intent = new Intent(BuyActivity.this, LuckChoose.class);
		startActivity(intent);
	}

	private void dialogMsg() {
		RWSharedPreferences shellRW = new RWSharedPreferences(BuyActivity.this, "UserMessage");
		messageidflag = shellRW.getStringValue(ShellRWConstants.ID);
		if (!PublicConst.MESSAGE.equals("")) {
			try {
				obj = new JSONObject(PublicConst.MESSAGE);
				String id = obj.getString("id");
				messagetitle = obj.getString("title");
				messagedetail = obj.getString("message");
				if (messageidflag == null) {
					shellRW.putStringValue("id", id);
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				} else if (!messageidflag.equals(id)) {
					shellRW.putStringValue(ShellRWConstants.ID, id);
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				}

			} catch (JSONException e) {

			}
		}
	}

	/**
	 * ��д�Żؽ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case 4:
			ExitDialogFactory.createExitDialog(this);
			break;
		}
		return false;
	}
	
	private class GalleryViewItem extends TableLayout {
		/*
		 *�н������е�һЩ����
		 */
		private final String NAME = "name";
		private final String PRIZEAMT = "prizeAmt";
		TabHost prizeRankTab ;
		String[] prizerankTitles = {getString(R.string.total),getString(R.string.month),getString(R.string.week)};
		ListView monthView,weekView,totalView;
		int[] listViewId = {R.id.prizeRank_total,R.id.prizeRank_month,R.id.prizeRank_week};
		View view ;//��View
		LayoutInflater mInflater = null;
		final Handler handler2 = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Constants.prizeRankJSON = msg.obj.toString();
					prizeRankTab.setCurrentTab(0);
					break;
				}
			}   

		};
		
		/**
		 * ��ʼ������
		 * @param rankType  �н���������
		 */
		private List<Map<String,Object>> initPrizeRankListData(String rankType){
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			try{
				JSONObject aa = new JSONObject(Constants.prizeRankJSON );
				String rankStr  = aa.getString(rankType);
				JSONArray  prizeRankArray = new JSONArray(rankStr);
				
				for(int i = 0;i<prizeRankArray.length();i++ ){
					try{
						Map<String, Object> map = new HashMap<String, Object>();
						map.put(NAME,prizeRankArray.getJSONObject(i).getString("name"));
						map.put(PRIZEAMT,prizeRankArray.getJSONObject(i).getString("prizeAmt"));
						list.add(map);
					}catch(Exception e){
					}
				}
			
			}catch (Exception e) {
			}
			
			return list;
		}
		
		private void getPrizeRankData(){
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = new Message();
					String prizeRankData = PrizeRankInterface.getInstance().prizeRankQuery();
					if(prizeRankData!= ""&&prizeRankData!=null){
							msg.what = 0;
							msg.obj = prizeRankData;
							handler2.sendMessage(msg);
					};
				}
			}).start();
		}
		public GalleryViewItem(Context context, int position) {
			super(context);
			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(position == 0){
				view = (LinearLayout) inflate.inflate( R.layout.buy_activity_btn, null);
				prizeRankTab = (TabHost)view.findViewById(R.id.buyactivity_tab_host);
				prizeRankTab.setVisibility(TabHost.VISIBLE);
				initPrizeRank();
				if(Constants.prizeRankJSON  == ""||	Constants.prizeRankJSON  == null){
					getPrizeRankData();   
				}
			}else{
				view = (LinearLayout) inflate.inflate( R.layout.buy_activity_btn1, null);
				prizeRankTab = (TabHost)view.findViewById(R.id.buyactivity_tab_host);
				initBtn(view, position);
			}
			this.addView(view);
		}
		/**
		 * ��ʼ���н����е�һЩ������TAB��
		 * @param view
		 */
		private void initPrizeRank() {
			prizeRankTab.setup();
			mInflater = LayoutInflater.from(BuyActivity.this);
			for(int i=0;i<prizerankTitles.length;i++){
					addTab(i); 
			}
			prizeRankTab.setOnTabChangedListener(accountTabChangedListener);
			prizeRankTab.setCurrentTab(1);
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
			title.setText(prizerankTitles[index]);
			TabHost.TabSpec tabSpec = prizeRankTab.newTabSpec(prizerankTitles[index]).setIndicator(indicatorTab).setContent(listViewId[index]);
			prizeRankTab.addTab(tabSpec);
		}
	
		TabHost.OnTabChangeListener accountTabChangedListener = new TabHost.OnTabChangeListener() {
			public void onTabChanged(String tabId) {	
				for(int i=0;i<prizerankTitles.length;i++){
					if(tabId.equals(prizerankTitles[0])){
						initPrizeList(totalView,listViewId[0],initPrizeRankListData(Constants.TOTAL));
					}else if(tabId.equals(prizerankTitles[1])){
						initPrizeList(monthView,listViewId[1],initPrizeRankListData(Constants.MONTH));
					}else if(tabId.equals(prizerankTitles[2])){
						initPrizeList(weekView,listViewId[2],initPrizeRankListData(Constants.WEEK));
					}
				}
			}
		};
		/**
		 * ��ʼ���н����н���
		 * @param listview  listView
		 * @param listviewid listView��ID
		 * @param list    �н����е�����  
		 */
		protected void initPrizeList( ListView listview,int listviewid,List<Map<String,Object>> list) {
			listview = (ListView)view.findViewById(listviewid);
			switch (HEIGHT) {
			case 320:
				listview.setPadding(0, 0, 0, 130);
				break;
			case 480:
				listview.setPadding(0, 0, 0, 110);
				break;
			case 800:
				listview.setPadding(0, 0, 0, 54);
				break;
			default:
				listview.setPadding(0, 0, 0, 0);
				break;
			}
			listview.setSelector(R.color.transparent);
			BuyActivityAdapter adapter = new BuyActivityAdapter(BuyActivity.this,list);
			listview.setSelected(false);
			listview.setAdapter(adapter);
			if(listview.isFocused()){
				listview.setItemsCanFocus(false);
			}else{
				listview.setItemsCanFocus(true);
			}
		}
		/**
		 * �н�����ListViewAdapter
		 * @author miao
		 */
		private class BuyActivityAdapter extends BaseAdapter{
			
			LayoutInflater mAdapterInflater = null;
			List<Map<String,Object>> mList = null;
			
			public  BuyActivityAdapter(Context context, List<Map<String,Object>> list) {
				mAdapterInflater = LayoutInflater.from(context);
				mList = list;
			}

			public int getCount() {
				return mList.size();
			}

			@Override
			public Object getItem(int position) {
				return mList.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position,  View convertView, ViewGroup parent) {
				final String prizeName =  (String) mList.get(position).get(NAME);
				final String prizeAmt = (String) mList.get(position).get(PRIZEAMT);
				PrizeViewHolder holder = new PrizeViewHolder();
				if (convertView == null) {
					convertView = mAdapterInflater.inflate(R.layout.prizerank_listitem,null);
					holder = new PrizeViewHolder();
					holder.prizeId = (TextView) convertView.findViewById(R.id.prizeRank_id);
					holder.prizerName= (TextView) convertView.findViewById(R.id.prizeRank_name);
					holder.prizeNumber = (TextView) convertView.findViewById(R.id.prizeRank_number);
				} else {
					holder = (PrizeViewHolder) convertView.getTag();
				}
				int rank =  position+1;
				setPrizeRankIDBg(holder.prizeId,rank);
				holder.prizeId.setText(rank+"");
				holder.prizerName.setText(prizeName);
				String prizeAmtHtml = "<font color=\"#ff0000\"><B>"+PublicMethod.toYuan(prizeAmt)+"</B></font>"+"Ԫ";//��Html��ʽ������ɫ
				holder.prizeNumber.setText(Html.fromHtml(prizeAmtHtml));
				convertView.setTag(holder);
				return convertView;
			}
			class PrizeViewHolder {
				TextView prizeId;
				TextView prizerName;
				TextView prizeNumber;
			}
			private void setPrizeRankIDBg(TextView btn,int ID){
				switch (ID) {
				case 1:
					btn.setBackgroundResource(R.drawable.prizerank_1);
					break;
				case 2:
					btn.setBackgroundResource(R.drawable.prizerank_2);
					break;

				case 3:
					btn.setBackgroundResource(R.drawable.prizerank_3);
					break;

				default:
					btn.setBackgroundResource(R.drawable.prizerank_other);
					break;
				}
			}
		}
		public void initBtn(View view, int position) {
			int length = SCREENMAX;
			int imgpostion = position -1;//�趨��ʼ��img��
			if(position<SCREENUM){
				if(SCREENUM>position+1){
					length = SCREENMAX;
				}else{
					length = SCREEALL - SCREENMAX * (imgpostion);
				}
			}
			RelativeLayout top1 = (RelativeLayout) view .findViewById(R.id.layout1_top);
			RelativeLayout top2 = (RelativeLayout) view .findViewById(R.id.layout2_top);
			RelativeLayout top3 = (RelativeLayout) view .findViewById(R.id.layout3_top);
			top1.setPadding(0, top, 0, 0);
			top2.setPadding(0, top, 0, 0);
			top3.setPadding(0, top, 0, 0);
			for (int i = 0; i < length; i++) {
				final int index = i + SCREENMAX * (imgpostion);
				ImageView imgView = (ImageView) view .findViewById(imgViewsId[i]);
				imgView.setVisibility(ImageView.VISIBLE);
				imgView.setImageResource(imageViews[index]);
				imgView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(BuyActivity.this, cla[index]);
						startActivity(intent);
					}
				});
				TextView textTitle = (TextView) view.findViewById(textViewId[i]);
				textTitle.setVisibility(TextView.VISIBLE);
				textTitle.setText(imageTitle[index]);
			}
		}
	}
	  /**
     * �״���������Ƿ񴴽���ݷ�ʽ
     * 
     */
	public void isShortcut(){
		RWSharedPreferences shellRW = new RWSharedPreferences(this, "addInfo");
		boolean isShortcut = shellRW.getBooleanValue("isShortcut");
		if(!isShortcut){//�����false�Ļ������״�����
		  shellRW.putBooleanValue("isShortcut", true);
		  ShortcutDialog dialog = new ShortcutDialog(this, getString(R.string.shortcut_dialog_title), getString(R.string.shortcut_dialog_message));
		  dialog.showDialog();
		  dialog.createMyDialog();
		}else{
		  dialogMsg();
		}
	}
	/**
	 * ����һ��activity���ص�ǰactivityִ�еķ���
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
       switch(resultCode){
       case RESULT_OK:
    	   tendToUserCenter();
    	   break;
       }
	}
	private ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			// activity��1��2������2�����غ���ô˷���
			lights.isLight(arg0);// ������ʵ��ViewPager�ؼ�����ʱ, �·��ĵ�ǰҳ���־ͼ��
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// ��1��2��������1����ǰ����
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	private class MainViewPagerAdapter extends PagerAdapter {
		public MainViewPagerAdapter() {
		}
		@Override
		public int getCount() {
			/*
			 * �����ṩ��ViewPager����ͼ����. һ�����ǻ��ViewȺ�Ȳ���һ��List<View>�л���,
			 * Ȼ��������ͷ������List<View>.size()����.
			 */
			return viewsBufList.size();
		}
		@Override
		public void startUpdate(ViewGroup container) {
			// Log.d(TAG, "PagerAdapter:startUpdate");
		}
		@Override
		public int getItemPosition(Object object) {
			// Log.d(TAG, "PagerAdapter:getItemPosition");
			return POSITION_UNCHANGED;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewsBufList.get(position));
		}
		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewsBufList.get(position), 0);
			return viewsBufList.get(position);
		}
		@Override
		public void finishUpdate(ViewGroup container) {
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}
		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}
		@Override
		public Parcelable saveState() {
			return null;
		}
		@Override
		public void notifyDataSetChanged() {
		}
		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
		}
	};
	private View creatGridView(Context context  , int[] imageViews , String[] imageTitle , final Class[] cla){
		 // ��ʼ������  
		GridView gridView = new GridView(context) ;
		gridView.setPadding(0,top, 0, 0);
		//���ñ���͸��
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		        gridView.setNumColumns(3);
		        // ��������ƥ����  
		        gridView.setAdapter(getAdapter(imageViews , imageTitle));  
		        // ֻ����ʾ��û�õģ�������������ӵ���itemʱ�ļ����¼�  
		       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
		            @Override  
		           public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {  
		            	Intent intent = new Intent(BuyActivity.this, cla[index]);
						startActivity(intent);
		            }  
		        });
			return gridView;  
		}
	 /** 
	    *  ��ȡ����ƥ���� 
	    */  
	   private ListAdapter getAdapter( int[] imageViews , String[] imageTitle) {  
	       // ��list�������ÿһ��item��Ӧ�����ֺ�ͼƬ  
	       List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();  
	       for (int i = 0; i < imageTitle.length; i++) {  
	           HashMap<String, Object> map = new HashMap<String, Object>();  
	           map.put("itemText", imageTitle[i]);  
	            map.put("itemImage", imageViews[i]);  
	          list.add(map);  
	        }  
	       SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.buy_gridview_item, new String[] { "itemText", "itemImage" },  
	                new int[] { R.id.imageText, R.id.imageView });  
	        return simpleAdapter;  
	    }  
		public void initRollingText() {
			ViewFlipper mFlipper = ((ViewFlipper) this .findViewById(R.id.notice_other_flipper));
			String str[] = splitStr(Constants.NEWS, 23);
			for (int i = 0; i < str.length; i++) {
				mFlipper.addView(addTextByText(str[i]));
			}
			mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_up_in));
			mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_up_out));
			mFlipper.startFlipping();
			mFlipper.setOnClickListener(filterclick);
		}
		 OnClickListener  filterclick = new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
			          informationNet();
				}
			};
}