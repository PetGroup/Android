package com.ruyicai.activity.buy.guess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.packet.Message.Type;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.view.PullRefreshLoadListView;
import com.ruyicai.adapter.ChattingListViewAdapter;
import com.ruyicai.adapter.ChattingListViewAdapter.OnRefreListView;
import com.ruyicai.adapter.GatherSubjectListAdapter;
import com.ruyicai.adapter.ScoreUsageListAdapter;
import com.ruyicai.component.SlidingView;
import com.ruyicai.component.SlidingView.SlidingViewPageChangeListener;
import com.ruyicai.component.SlidingView.SlidingViewSetCurrentItemListener;
import com.ruyicai.component.view.ChatListView;
import com.ruyicai.component.view.ChatListView.OnRefreshListener;
import com.ruyicai.component.view.TitleBar;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.service.MessageService;
import com.ruyicai.data.db.DbHelper;
import com.ruyicai.model.HttpUser;
import com.ruyicai.model.MessageStatus;
import com.ruyicai.model.MyMessage;
import com.ruyicai.receiver.MsgClientReceiver;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.xmpp.IMessageListerner;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 竞猜扎堆详情页面
 * @author Administrator
 *
 */
public class RuyiGuessGatherInfo extends RoboActivity implements IMessageListerner,
						OnRefreListView ,OnRefreshListener{

	@InjectView(R.id.gather_introduce)
	private TextView mIntroduce;
	
	@InjectView(R.id.gather_gather_name)
	private TextView mGatherName;
	
	@InjectView(R.id.gather_sponsor_name)
	private TextView mSponsorName;
	
	@InjectView(R.id.gather_score)
	private TextView mScore;
	
	@InjectView(R.id.gather_people_num)
	private TextView mPeople;
	
	@InjectView(R.id.gather_head_img)
	private ImageView mHeadImg;
	
	@InjectView(R.id.gather_main_layout)
	private LinearLayout mMainLayout;
	
	@InjectView(R.id.gather_add_subject_layout)
	private View mAddBtnLayout;
	
	@InjectView(R.id.gather_add_subject_btn)
	private Button mAddSubjectBtn;
	
	private LayoutInflater mInflater;
	
	private String[] mTitleGroups = {"大厅", "说两句", "我的积分"};

	private List<View> mSlidingListViews;
	
	private SlidingView mSlidingView;
	
	//我的积分
	private LinearLayout mMyScoreLayout;
	private ListView mScoreUsageList;
	private ScoreUsageListAdapter mScoreUsageListAdapter;
	//说两句
	private LinearLayout mTalkLayout;
	//竞猜大厅
	private View mRuyiGuessListLayout;
	private ListView mGuessSubjectList;
	private GatherSubjectListAdapter mSubjectListAdapter;

	private PullRefreshLoadListView mPullListView;
	@Inject private ChattingListViewAdapter mChatMsgAdapter;
	@Inject private MessageService messageService;
	@Inject private DbHelper dbHelper;
	@Inject private MsgClientReceiver msgClientReceiver;
	private ChatListView mChatList;
	private final static int refreshTitle = 10;
	private final static int refreshAdapter = 11;
	private static final int refreshNotReadMsgNum = 12;//刷新Title
	private static final int PAGE_NUM=10;
	private ChatHandler chatHandler;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_guess_gatherinfo);
		msgClientReceiver.addMessageListener(RuyiGuessGatherInfo.this);
		chatHandler = new ChatHandler();
		initViews();
	}

	private void initViews() {
		initTitleBar();
		mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		initProjectInfo();
		initGuessListView();
		initMyScoreLayout();
		initTalkLayout();
		initSlidingView();
	}
	
	/**
	 * 初始化项目介绍信息
	 */
	private void initProjectInfo(){
		mHeadImg.setImageResource(R.drawable.guess_info_head);
		mGatherName.setText("扎堆的名字");
		mPeople.setText("100人");
		mScore.setText("20000");
		mSponsorName.setText("某某");
		mIntroduce.setText("介绍 ：");
	}
	
	/**
	 * 初始化大厅的listview
	 */
	private void initGuessListView() {
		mRuyiGuessListLayout = mInflater.inflate(R.layout.buy_ruyigather_subject_list, null);
		mGuessSubjectList = (ListView) mRuyiGuessListLayout.findViewById(R.id.gather_subject_listview);
		//设置一个空页脚防止最后一条数据被覆盖
		TextView footer = new TextView(this);
		footer.setHeight(mAddBtnLayout.getLayoutParams().height);
		mGuessSubjectList.addFooterView(footer);
		
		mSubjectListAdapter = new GatherSubjectListAdapter(getLayoutInflater());
		mGuessSubjectList.setAdapter(mSubjectListAdapter);
	}
	
	/**
	 * 初始化我的积分页面
	 */
	private void initMyScoreLayout() {
		mMyScoreLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_myscore_layout, null);
		mScoreUsageList = (ListView) mMyScoreLayout.findViewById(R.id.myscore_usage_list);
		mScoreUsageListAdapter = new ScoreUsageListAdapter(getLayoutInflater());
		mScoreUsageList.setAdapter(mScoreUsageListAdapter);
	}
	
	/**
	 * 初始化聊天室页面
	 */
	private void initTalkLayout(){
		mTalkLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_mysubject_layout, null);
//		mTalkLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_myscore_layout, null);
	}
	
	/**
	 * 初始化侧滑控件
	 */
	private void initSlidingView() {	
		mSlidingListViews = new ArrayList<View>();
		mSlidingListViews.add(mRuyiGuessListLayout);
		mSlidingListViews.add(mTalkLayout);
		mSlidingListViews.add(mMyScoreLayout);
		
		mSlidingView = new SlidingView(this, mTitleGroups, mSlidingListViews, 
				mMainLayout, 17, getResources().getColor(R.color.red));
		setSlidingViewListener();
		mSlidingView.setTabHeight(40);
		mSlidingView.resetCorsorViewValue(PublicMethod.getDisplayWidth(this)/3, 0, R.drawable.jc_gyj_tab_bg);
	}
	
	/**
	 * 初始化上边的标题栏
	 */
	private void initTitleBar() {
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText("如意竞猜");
	}
	
	/**
	 * 添加SlindingView事件回调
	 */
	private void setSlidingViewListener(){
		mSlidingView.addSlidingViewPageChangeListener(new SlidingViewPageChangeListener() {

			@Override
			public void SlidingViewPageChange(int index) {
				if (index == 1) {
					addTalkLayout();
				}
			}
		});
		
		mSlidingView.addSlidingViewSetCurrentItemListener(new SlidingViewSetCurrentItemListener() {

			@Override
			public void SlidingViewSetCurrentItem(int index) {
				if(index != 0){
					mAddBtnLayout.setVisibility(View.GONE);
				}else{
					mAddBtnLayout.setVisibility(View.VISIBLE);
				}
				if (index == 1) {
					addTalkLayout();
				}
			}
		});
	}
	
	private void addTalkLayout() {
		mAddBtnLayout.setVisibility(View.GONE);
		if (mTalkLayout != null && mTalkLayout.getChildCount() == 0) {
			View view  = mInflater.inflate(R.layout.buy_ruyiguess_talk_layout, null);
			mChatList = (ChatListView) view.findViewById(R.id.chatList);
			Button sendBtn = (Button)view.findViewById(R.id.buy_ruyiguess_send_msg_btn);
			final EditText contentET = (EditText)view.findViewById(R.id.buy_ruyiguess_send_content_et);
			sendBtn.setOnClickListener(new ImageView.OnClickListener() {
				@Override
				public void onClick(View v) {
					String content = contentET.getText().toString().trim();
					if (TextUtils.isEmpty(content)) {
						PublicMethod.showMessage(RuyiGuessGatherInfo.this, "内容不能为空");
						return;
					}
					if (HttpUser.userId == null) {
						PublicMethod.showMessage(RuyiGuessGatherInfo.this, "请先登录！");
						return;
					}
					MyMessage myMessage = messageService.createGroupMessage("g10001",HttpUser.userId, content);
					addMessageToAdapter(myMessage);
					notifyListView(myMessage);
					mChatMsgAdapter.notifyDataSetChanged();
					messageService.beforeSendMessage(myMessage);
					Intent intent = new Intent(Constants.SERVER_MSG_RECIVER_ACTION);
					intent.putExtra("sendMsg", myMessage);
					sendBroadcast(intent);
					contentET.setText("");
				}
			});
			initListViewAdapter();
			mChatList.setAdapter(mChatMsgAdapter);
			mTalkLayout.addView(view);
		}
	}
	
	/**
	 * @param myMessage
	 */
	private void addMessageToAdapter(MyMessage myMessage){
//		dbHelper.saveMsg(myMessage);
//		dbHelper.changeAllMsgToisReadByMsgType(myMessage.getToWho(),"0");
//		notifyListView(myMessage);
	}
	
	private void notifyListView(MyMessage myMessage) {
		mChatMsgAdapter.addMessage(myMessage);
		mChatList.setSelection(mChatMsgAdapter.getListData().size());
	}
	
	public class ChatHandler extends Handler {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
//			case refreshTitle:
//				refreshUserData();
//				break;
			case refreshAdapter:
				MyMessage myMessage=(MyMessage)msg.obj;
				mChatMsgAdapter.userReadOneMsg(myMessage);
				String newUserId = myMessage.getFrom();
//				dbHelper.changeMsgToisReadByPacketId(newUserId, "0");
				notifyListView(myMessage);
				break;
//			case refreshNotReadMsgNum:
//				int notReadMsgNum=msg.arg1;
//				showNotReadMsgNum(notReadMsgNum);
//				break;
			}
//			messageReadService.readMessage(null);
		}
	}
	
	private void initListViewAdapter() {
		mChatMsgAdapter.setOnRefreListView(this);
		mChatMsgAdapter.setIXListViewListener(this);
		mChatMsgAdapter.initData(getData());
//		mChatMsgAdapter.loadMoreMsg(userId, pageIndex);
//		chatList.setSelection(mChatMsgAdapter.getListData().size());
//		mChatMsgAdapter.notifyDataSetChanged();
	}
	
	
	private List<MyMessage> getData() {
		List<MyMessage> list = new ArrayList<MyMessage>();
		for (int i = 0; i < 10; i++) {
			MyMessage msg = new MyMessage();
			msg.setBody("您好！"+i);
			msg.setFrom("sdfdfsd");
			msg.setId("000000");
			msg.setMsgTag("aaaaaa");
			msg.setMsgTime("2014");
			msg.setPayLoad("dsfdsfsdf");
			msg.setToWho("dsfdsfdsff");
			msg.setMsgtype("5465513");
			msg.setReceiveTime(new Date());
			msg.setStatus(MessageStatus.Sending);
			msg.setType(Type.chat);
			list.add(msg);
		}
		return list;
	}
	
	private MyMessage getMessage(String str) {
		MyMessage msg = new MyMessage();
		msg.setBody(str);
		msg.setFrom(HttpUser.userId);
		msg.setId(HttpUser.userId);
		msg.setMsgTag("aaaaaa");
		msg.setMsgTime("2014");
		msg.setPayLoad("dsfdsfsdf");
		msg.setToWho("dsfdsfdsff");
		msg.setMsgtype("5465513");
		msg.setReceiveTime(new Date());
		msg.setStatus(MessageStatus.Sending);
		msg.setType(Type.chat);
		return msg;
	}

	@Override
	public void onMessage(MyMessage message) {
		if (message.getType().equals(Type.chat)
				|| message.getType().equals(Type.normal)) {
			String newUserId = message.getFrom().substring(0,
					message.getFrom().indexOf("@"));
			if (!HttpUser.userId.equals(newUserId)) {
				// searchNotReadMsgNum();
				return;
			}
			if (!PublicMethod.isNormalChat(message.getMsgtype())) {
				return;
			}
			message.setStatus(MessageStatus.UserRead);
			message.setFrom(newUserId);
			// dbHelper.changeMsgToisReadByPacketId(newUserId, "0");
			// searchNotReadMsgNum();
			 notifyRefreshAdapter(message);
		}
	}
	
	/**{需要发Handler刷新，不然刷新不了}
	 * 刷新Adapter
	 */
    private void notifyRefreshAdapter(MyMessage myMessage) {	
		android.os.Message ms = chatHandler.obtainMessage();
		ms.what = refreshAdapter;
		ms.obj=myMessage;
		ms.sendToTarget();
    }

	@Override
	public void onRefresh() {
		int pageCurr=mChatMsgAdapter.getCount();
		mChatMsgAdapter.loadMoreMsg(HttpUser.userId, pageCurr);
	}

	@Override
	public void refreListView(int status) {
		if(status==0){
			onLoad();
			mChatList.stopRefre();
			return;
		}
		mChatList.setSelection(PAGE_NUM);
		onLoad();
	}
	
	private void onLoad() {
		mChatList.onRefreshComplete();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		msgClientReceiver.removeMessageListener(RuyiGuessGatherInfo.this);
	}
	

}
