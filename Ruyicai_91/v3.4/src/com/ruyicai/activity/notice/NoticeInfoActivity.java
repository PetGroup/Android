package com.ruyicai.activity.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.notice.LotnoDetail.DLTDetailView;
import com.ruyicai.activity.notice.LotnoDetail.FC3DetailView;
import com.ruyicai.activity.notice.LotnoDetail.PL3DetailView;
import com.ruyicai.activity.notice.LotnoDetail.PL5DetailView;
import com.ruyicai.activity.notice.LotnoDetail.QLCDetailView;
import com.ruyicai.activity.notice.LotnoDetail.QXCDetailView;
import com.ruyicai.activity.notice.LotnoDetail.SsqDetailView;
import com.ruyicai.activity.notice.LotnoDetail.TwentyDetailView;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.NoticeInterface;
import com.ruyicai.net.newtransaction.PrizeInfoInterface;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 *
 */// implements OnScrollListener
public class NoticeInfoActivity extends Activity{
	public final static String LOTTERYTYPE = "LOTTERYTYPE";
	public final static String WINNINGNUM = "WINNINGNUM";
	public final static String DATE = "DATA";
	public final static String ISSUE = "ISSUE";
	public final static String FINALDATE = "FINALDATE";
	public final static String MONEYSUM = "MONEYSUM";
    private Handler handler = new Handler();  
	public ProgressDialog progress;
	String Lotno,LotLalel;
	int lotType = -1;
	ProgressBar progressbar;//列表获取更多的progressbar
	LayoutInflater mInflater;
	private final int LISTSSQ = 0,LIST3D = 1,LISTQLC = 2,LISTPL3 = 3,LISTDLT = 4,LISTSSC = 5
					,LIST115 = 6,LISTSFC = 7,LISTRX9 = 8,LISTLCB = 9,LISTJQC = 10
					,LISTPL5 = 11,LISTQXC = 12,LISTYDJ = 13,LISTTWENTY = 14;
	List<Map<String,Object>> adpterlist = new ArrayList<Map<String, Object>>();; // zlm 8.9 添加排列三、超级大乐透
	TextView noticePrizesTitle;
	BaseAdapter adapter;
	View addMoreView;
	Button reBtn;
	ListView listview;
	String[] str = new String[] { LOTTERYTYPE, WINNINGNUM, DATE, ISSUE,FINALDATE, MONEYSUM };
	int pageindex = 1;
	int totalItem = 24;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progress = UserCenterDialog.onCreateDialog(this);
		setContentView(R.layout.notice_prizes_single_specific_main);
		noticeAllNet();
	}
	public void noticeAllNet() {
//		if (NoticeMainActivity.isFirstNotice) {
			progress.show();
			final Handler handler = new Handler();
			new Thread(new Runnable() {
				@Override
				public void run() {
					getLotno(NoticeActivityGroup.LOTNO);
					 JSONObject prizemore = getJSONByLotno(Lotno, "50");
						try {
							final String msg = prizemore.getString("message");
							final String code = prizemore.getString("error_code");
							progress.dismiss();
							if(code.equals("0000")){
								JsonToString(prizemore);
								handler.post(new Runnable() {
									@Override
									public void run() {
										initList();
									}
								 });
							}else{
								handler.post(new Runnable() {
									@Override
									public void run() {
										initList();
										Toast.makeText(NoticeInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
									}
								 });
							}
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			
				}

			}).start();
	}
	private static JSONObject getJSONByLotno(String lotnoString,String maxresultString) {
		JSONObject jsonObjectByLotno = PrizeInfoInterface.getInstance()
				.getNoticePrizeInfo(lotnoString, "1", maxresultString);
		return jsonObjectByLotno;
	}
	private void initList(){
		noticePrizesTitle = (TextView) findViewById(R.id.notice_prizes_single_specific_title_id);
		listview = (ListView) findViewById(R.id.notice_prizes_single_specific_listview);
		mInflater =  LayoutInflater.from(this);
		addMoreView = mInflater.inflate(R.layout.lookmorebtn, null);
		progressbar=(ProgressBar)addMoreView.findViewById(R.id.getmore_progressbar);
	    listview.addFooterView(addMoreView);
		
		
		noticePrizesTitle.setTextSize(20);
		reBtn = (Button) findViewById(R.id.notice_prizes_single_specific_main_returnID);
		reBtn.setBackgroundResource(R.drawable.returnselecter);
		adapter = getAdapter(Lotno);
		listview.setAdapter(adapter);
		addMoreView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				addMoreView.setEnabled(false);
	            getMore();
			}
		});
	}
	public BaseAdapter getAdapter(String lotno){
		if(lotno.equals(Constants.LOTNO_SSC)||lotno.equals(Constants.LOTNO_11_5)||
				lotno.equals(Constants.LOTNO_eleven)||lotno.equals(Constants.LOTNO_GD115)){
			return new HightSubEfficientAdapter(this, str,adpterlist);
		}else{
			if (lotno.equals(Constants.LOTNO_SFC)||lotno.equals(Constants.LOTNO_RX9)||
					lotno.equals(Constants.LOTNO_JQC)||lotno.equals(Constants.LOTNO_LCB)) {
				return new SubEfficientAdapter(this, str,adpterlist,false);
		}
			return new SubEfficientAdapter(this, str,adpterlist,true);
		}
	}

	/**
	 * 获取lotno
	 */
	private void getLotno(int listViewID) {
		switch (listViewID) {
		case NoticeActivityGroup.ID_SUB_SHUANGSEQIU_LISTVIEW:
			Lotno = Constants.LOTNO_SSQ;
			lotType = LISTSSQ;//双色球
			break;
		case NoticeActivityGroup.ID_SUB_FUCAI3D_LISTVIEW:
			Lotno = Constants.LOTNO_FC3D;
			lotType = LIST3D;//福彩3D
			break;
		case NoticeActivityGroup.ID_SUB_QILECAI_LISTVIEW:
			Lotno = Constants.LOTNO_QLC;
			lotType = LISTQLC;//七乐彩
			break;
		case NoticeActivityGroup.ID_SUB_PAILIESAN_LISTVIEW:
			Lotno = Constants.LOTNO_PL3;
			lotType = LISTPL3;//排列3
			break;
		case NoticeActivityGroup.ID_SUB_PL5_LISTVIEW:
			Lotno = Constants.LOTNO_PL5;
			lotType = LISTPL5;//排列五
			break;
		case NoticeActivityGroup.ID_SUB_QXC_LISTVIEW:
			Lotno = Constants.LOTNO_QXC;
			lotType = LISTQXC;//七星彩
			break;
		case NoticeActivityGroup.ID_SUB_DLT_LISTVIEW:
			Lotno = Constants.LOTNO_DLT;
			lotType = LISTDLT;//大乐透
			break;
		case NoticeActivityGroup.ID_SUB_SHISHICAI_LISTVIEW:
			Lotno = Constants.LOTNO_SSC;
			lotType = LISTSSC;//时时彩
			break;
		case NoticeActivityGroup.ID_SUB_DLC_LISTVIEW:
			Lotno = Constants.LOTNO_11_5;
			lotType = LIST115;//江西11-5
			break;
		case NoticeActivityGroup.ID_SUB_YDJ_LISTVIEW:
			Lotno = Constants.LOTNO_eleven;
			lotType = LISTYDJ;//11运夺金
			break;
		case NoticeActivityGroup.ID_SUB_TWENTY_LISTVIEW:
			Lotno = Constants.LOTNO_22_5;
			lotType = LISTTWENTY;//22选5
			break;
		case NoticeActivityGroup.ID_SUB_SFC_LISTVIEW:
			Lotno = Constants.LOTNO_SFC;//胜负彩
			lotType = -1;
			break;
		case NoticeActivityGroup.ID_SUB_RXJ_LISTVIEW:
			Lotno = Constants.LOTNO_RX9;//任选9
			lotType = -1;
			break;
		case NoticeActivityGroup.ID_SUB_LCB_LISTVIEW:
			Lotno = Constants.LOTNO_LCB;//六场半
			lotType = -1;
			break;
		case NoticeActivityGroup.ID_SUB_JQC_LISTVIEW:
			Lotno = Constants.LOTNO_JQC;//进球彩
			lotType = -1;
			break;
		case NoticeActivityGroup.ID_SUB_GD115_LISTVIEW:
			Lotno = Constants.LOTNO_GD115;//广东11-5
			lotType = -1;
			break;
		}
	}

	 /**
		 * 子列表适配器
		 */
		public  class HightSubEfficientAdapter extends BaseAdapter {
			int count = 0;
			private LayoutInflater mInflater; // 扩充主列表布局
			private List<Map<String, Object>> mList;
			private String[] mIndex;
			private Activity context;
			public HightSubEfficientAdapter(Activity context, String[] index,List<Map<String, Object>> list) {
				this.context = context;
				this.mInflater = LayoutInflater.from(context);
				this.mList = list;
				this.mIndex = index;
				
			}
			@Override
			public int getCount() {
				count = mList.size();
				return count;
			}

			@Override
			public Object getItem(int position) {
				return mList.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}
			public  final int[] colors = new int[] { 0x3000000, 0x300010ff };
			// 设置主列表布局中的详细内容
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				final String iNumbers = (String) mList.get(position).get(mIndex[1]);
				String iDate = (String) mList.get(position).get(mIndex[2]);
				final String iIssueNo = (String) mList.get(position).get(mIndex[3]);
				if(convertView == null){
					convertView = mInflater.inflate(R.layout.win_lot_high_frequency_item, null);
					holder = new ViewHolder();
					holder.date = (TextView) convertView.findViewById(R.id.notice_prizes_single_specific_noticedDate_id);
					holder.issue = (TextView) convertView.findViewById(R.id.notice_prizes_single_specific_issue_id);
					holder.imgView = (ImageView) convertView.findViewById(R.id.notice_prizes_single_specific_img);
					holder.imgView.setVisibility(ImageView.GONE);
					convertView.setTag(holder);
				}else{
					holder = (ViewHolder) convertView.getTag();
				}
				holder.date.setText(iNumbers);
				holder.date.setTextColor(Color.RED);
				holder.issue.setText("第"+iIssueNo+"期");		
				return convertView;
			}

			 class ViewHolder {
				TextView name;
				TextView date;
				TextView issue;
				ImageView imgView;
			}
		}
	/**
	 * 子列表适配器
	 */
	public  class SubEfficientAdapter extends BaseAdapter {
		int count = 0;
		private LayoutInflater mInflater; // 扩充主列表布局
		private List<Map<String, Object>> mList;
		private String[] mIndex;
		private Activity context;
		private boolean isImg = true;
		public SubEfficientAdapter(Activity context, String[] index,
				List<Map<String, Object>> list, boolean isImg) {
			this.context = context;
			this.mInflater = LayoutInflater.from(context);
			this.mList = list;
			this.mIndex = index;
			this.isImg = isImg;
			
		}
		@Override
		public int getCount() {
			count = mList.size();
			return count;
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public  final int[] colors = new int[] { 0x3000000, 0x300010ff };

		// 设置主列表布局中的详细内容
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			String iGameType = (String) mList.get(position).get(mIndex[0]);
			final String iNumbers = (String) mList.get(position).get(mIndex[1]);
			String iDate = (String) mList.get(position).get(mIndex[2]);
			final String iIssueNo = (String) mList.get(position).get(mIndex[3]);
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.notice_prizes_single_specific_layout, null);
				holder = new ViewHolder();
				holder.numbers = (LinearLayout) convertView.findViewById(R.id.notice_pirzes_single_specific_ball_linearlayout);
				holder.date = (TextView) convertView.findViewById(R.id.notice_prizes_single_specific_noticedDate_id);
				holder.issue = (TextView) convertView.findViewById(R.id.notice_prizes_single_specific_issue_id);
				holder.imgView = (ImageView) convertView.findViewById(R.id.notice_prizes_single_specific_img);
				if(isImg){
					holder.imgView.setVisibility(ImageView.VISIBLE);
				}else{
					holder.imgView.setVisibility(ImageView.GONE);
				}
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			PrizeBallLinearLayout linear = new PrizeBallLinearLayout(context);
			linear.Lotname = Lotno;
			linear.Batchcode = iNumbers;
			linear.linear = holder.numbers;
			linear.removeAllBalls();
			linear.initLinear();
			
			holder.date.setText(iDate);
			holder.issue.setText("第"+iIssueNo+"期");
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(isImg){
						switch (lotType){
						case LISTSSQ:
							SsqDetailView ssqDetailView = new SsqDetailView(context,Constants.LOTNO_SSQ,iIssueNo,progress,new Handler(),true);
							break;
						case LIST3D:
							FC3DetailView fc3DetailView = new FC3DetailView(context,Constants.LOTNO_FC3D,iIssueNo,progress,new Handler(),true);
							break;

						case LISTQLC:
							QLCDetailView qlcDetailView = new QLCDetailView(context,Constants.LOTNO_QLC,iIssueNo,progress,new Handler(),true);
							break;
						case LISTDLT:
							DLTDetailView dltDetailView = new DLTDetailView(context,Constants.LOTNO_DLT,iIssueNo,progress,new Handler(),true);
							break;
						case LISTPL3:
							PL3DetailView pl3DetailView = new PL3DetailView(context,Constants.LOTNO_PL3,iIssueNo,progress,new Handler(),true);
							break;
						case LISTPL5:
							PL5DetailView pl5DetailView = new PL5DetailView(context,Constants.LOTNO_PL5,iIssueNo,progress,new Handler(),true);
							break;
						case LISTQXC:
							QXCDetailView qxcDetailView = new QXCDetailView(context,Constants.LOTNO_QXC,iIssueNo,progress,new Handler(),true);
							break;
						case LISTTWENTY:
							TwentyDetailView twentview = new TwentyDetailView(context, Constants.LOTNO_22_5, iIssueNo, progress,new Handler(),true);
					
						}
					}

				}
			});
			return convertView;
		}

		 class ViewHolder {
			LinearLayout numbers;
			TextView name;
			TextView date;
			TextView issue;
			ImageView imgView;
		}
	}
	protected void onStart() {
		super.onStart();
	}	
	protected void onResume() {
		super.onResume();
	}
	protected void onPause() {
		super.onPause();
	}
	protected void onStop() {
		super.onStop();
	}
	private void netting(){
		progressbar.setVisibility(ProgressBar.VISIBLE);
		final Handler tHandler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
		totalItem =  adapter.getCount();
		pageindex ++;
		final JSONObject prizemore = PrizeInfoInterface.getInstance().getNoticePrizeInfo(Lotno, pageindex+"", "50");
		try {
			final String msg = prizemore.getString("message");
			final String code = prizemore.getString("error_code");
			if(code.equals("0000")){
				JsonToString(prizemore);
				tHandler.post(new Runnable() {
					@Override
					public void run() {
						progressbar.setVisibility(ProgressBar.INVISIBLE);
						addMoreView.setEnabled(true);
						adapter.notifyDataSetChanged();
					}
				 });
			}else{
				tHandler.post(new Runnable() {
					@Override
					public void run() {
						progressbar.setVisibility(ProgressBar.INVISIBLE);
						addMoreView.setEnabled(true);
						Toast.makeText(NoticeInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				 });
			}
		}catch (JSONException e) {
				// TODO: handle exception
		}
	   }

	
	 }).start();
		
	}
	private void JsonToString(JSONObject prizemore)
			throws JSONException {
		JSONArray prizeArray = prizemore.getJSONArray("result");
		for (int i = 0; i < prizeArray.length(); i++) {
			JSONObject prizeJson = (JSONObject) prizeArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, LotLalel);
			map.put(WINNINGNUM, prizeJson.getString("winCode"));
			map.put(DATE, "开奖日期： " + prizeJson.getString("openTime"));
			map.put(ISSUE,  prizeJson.getString("batchCode") );
			adpterlist.add(map);
		}
	}
	  private void getMore(){
			netting();
			
	  }
}
