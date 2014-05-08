package com.ruyicai.activity.buy.guess;


import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.bean.ItemInfoBean;
import com.ruyicai.activity.buy.guess.util.RuyiGuessConstant;
import com.ruyicai.activity.buy.guess.view.PullRefreshLoadListView;
import com.ruyicai.activity.buy.guess.view.PullRefreshLoadListView.IXListViewListener;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.adapter.CreateGatherSubjectListAdapter;
import com.ruyicai.component.view.TitleBar;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.controller.listerner.GuessListener;
import com.ruyicai.controller.service.GuessService;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.model.RuyiGuessGatherBean;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.json.JsonUtils;

/**
 * 创建扎堆界面
 * @author wangw
 *
 */
public class RuyiGuessCreateGather extends RoboActivity implements GuessListener,IXListViewListener {

	private static final String TYPE = "0";	//获取竞猜题目列表类型，0：获取所有，1获取用户对应的列表
	private static final String STATE = "1";	//state=0代表只获取未结束的竞猜题目
	public final static int RUYI_GUESS_CREATE_GROUP_SUCCESS = 5;	//创建扎堆成功
	public final static int RUYI_GUESS_CREATE_GROUP_FAIL = 4;		//创建扎堆失败
	public final static int RUYI_GUESS_GET_GUESS = 10;		//获取竞猜题目列表
	public final static int RUYI_GUESS_REFRESH_GUESS = 11;	//刷新竞猜题目列表

	@InjectView(R.id.create_gather_btn)
	private Button mCreateBtn;

	@InjectView(R.id.create_gather_layout)
	private View mCreateBtnLayout;

	@InjectView(R.id.ruyi_guest_subject_no_record)
	private TextView mNoSubjecttv;

	@InjectView(R.id.guesssubjectlist)
	private PullRefreshLoadListView mSubjectList;

	@InjectView(R.id.gather_name_txt)
	private EditText mName_txt;

	@InjectView(R.id.gather_pwd_txt)
	private EditText mPwd_txt;

	@InjectView(R.id.gather_info_txt)
	private EditText mInfo_txt;

	private CreateGatherSubjectListAdapter mAdapter;
	private int mPageIndex = 0;	//当前页数

	private String mItemCound = "10";	//每页显示的条数，默认10条

	private ProgressDialog mProDialog;
	private int mTotalPage;	//服务器返回的总页数

	private String mUserNo;

	@Inject private GuessService mGuessService;
	private RWSharedPreferences mSharedPreferences;
	private boolean mIsLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_guess_create_gather);
		mGuessService.addListener(this);
		readUserInfo();
		initViews();
	}

	private void readUserInfo() {
		mSharedPreferences = new RWSharedPreferences(RuyiGuessCreateGather.this,
				"addInfo");
		String sessionId = mSharedPreferences.getStringValue(ShellRWConstants.SESSIONID);
		if (!"".equals(sessionId)) {
			mIsLogin = true;
			mUserNo = mSharedPreferences.getStringValue(ShellRWConstants.USERNO);
		}
	}


	private void initViews() {
		mCreateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mIsLogin){
					createGroup();
				}else{
					showToast("请登录后创建");
					gotoActivity(UserLogin.class);
				}
			}
		});
		initTitleBar();
		initSubjectList();
		netWorkGetData(RUYI_GUESS_GET_GUESS);
	}


	/**
	 * 初始化竞猜题目列表
	 */
	private void initSubjectList() {
		mAdapter = new CreateGatherSubjectListAdapter(RuyiGuessCreateGather.this);
		mSubjectList.setOnItemClickListener(listListener);

		//添加一个空页脚，防止创建按钮遮挡数据
		TextView footer = new TextView(this);
		footer.setHeight(mCreateBtnLayout.getLayoutParams().height);
		footer.setVisibility(View.INVISIBLE);
		mSubjectList.addFooterView(footer);
		mSubjectList.setFooterDividersEnabled(false);

		mSubjectList.setAdapter(mAdapter);
		mSubjectList.setPullLoadEnable(true);
		mSubjectList.setXListViewListener(this);

		//默认第一项为勾选
		mAdapter.setSelectItem(0);

	}

	/**
	 * 获取题目列表
	 * @param cmd
	 */
	private void netWorkGetData(int cmd){
		mProDialog = PublicMethod.creageProgressDialog(this);
		mGuessService.getGuessSubjectList(mPageIndex, mItemCound, mUserNo,TYPE,cmd,STATE);
	}

	/**
	 * 监听点击Item事件，改变背景颜色
	 */
	OnItemClickListener listListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			mAdapter.setSelectItem(arg2-mSubjectList.getHeaderViewsCount());
		}
	};


	/**
	 * 初始化上边的标题栏
	 */
	private void initTitleBar() {
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText("创建扎堆");
	}

	/**
	 * 创建扎堆
	 */
	private void createGroup() {
		//判断扎堆名称是否为空
		if(TextUtils.isEmpty(mName_txt.getText())){
			showToast("扎堆名称不能为空");
			return;
		}
		//实例化扎堆数据
		RuyiGuessGatherBean bean = new RuyiGuessGatherBean();
		bean.setGatherName(mName_txt.getText().toString());
		bean.setGatherPwd(mPwd_txt.getText().toString());
		bean.setGatherDescription(mInfo_txt.getText().toString());
		bean.setGuessSubjectid(mAdapter.getSelectedid());
		mGuessService.createGroup(mUserNo, bean);
	}

	private void showToast(String msg){
		Toast.makeText(RuyiGuessCreateGather.this, msg, Toast.LENGTH_SHORT).show();
	}


	private void onLoaded() {
		mSubjectList.stopRefresh();
		mSubjectList.stopLoadMore();
		mSubjectList.setRefreshTime(PublicMethod.dateToStrLong(
				new Date(System.currentTimeMillis())));
	}

	/**
	 * 跳转到指定的Activity
	 * @param cls
	 */
	private void gotoActivity(Class<?> cls){
		Intent intent = new Intent();
		intent.setClass(this, cls);
		startActivity(intent);
		this.finish();
	}

	Handler handler = new Handler(){

		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case RUYI_GUESS_CREATE_GROUP_SUCCESS:	//创建扎堆成功
				gotoActivity(RuyiGuessShareActivity.class);
				break;
			case RUYI_GUESS_CREATE_GROUP_FAIL:		//创建扎堆失败

				showToast("创建扎堆失败！error:"+msg.obj);
				break;
			case RUYI_GUESS_GET_GUESS:		//获取题目列表
			case RUYI_GUESS_REFRESH_GUESS:	//刷新题目列表
				ongetGuess((String) msg.obj,msg.what);
				break;
			}
		}

		//获取竞猜题目列表
		private void ongetGuess(String result,int type) {
			if(TextUtils.isEmpty(result)){
				showToast("网络异常");
				onLoaded();
				PublicMethod.closeProgressDialog(mProDialog);
			}else{
				JSONObject jsonObj;
				try {
					jsonObj = new JSONObject(result);
					String errorCode = jsonObj.getString("error_code");
					mTotalPage = Integer.valueOf(jsonObj.getString("totalPage").trim());
					if (Constants.SUCCESS_CODE.equals(errorCode)) {
						List<ItemInfoBean> list = JsonUtils.getList(jsonObj.getString("result"), ItemInfoBean.class);
						if (type == RUYI_GUESS_REFRESH_GUESS) {
							mPageIndex = 0;
						}

						if (list != null) {
							if(type == RUYI_GUESS_REFRESH_GUESS)
								mAdapter.refreshData(list);
							else
								mAdapter.addData(list);
						}
					} else if ("0047".equals(errorCode)) {
						mNoSubjecttv.setVisibility(View.VISIBLE);
						mSubjectList.setVisibility(View.GONE);
					} else {
						String message = jsonObj.getString("message");
						if (message == null || "null".equals(message) ||"".equals(message)) {
							message = "网络异常";
						}
						showToast(message);
					}
					onLoaded();
				} catch (JSONException e) {
					e.printStackTrace();
				}finally{
					if(type == RUYI_GUESS_REFRESH_GUESS)
						onLoaded();
					PublicMethod.closeProgressDialog(mProDialog);
				}
			}


		}
	};

	@Override
	public void onCreateGroupCallback(ReturnBean bean) {
		PublicMethod.myOutLog("create","code="+bean.getError_code()+" Msg="+bean.getMessage());
		Message msg =handler.obtainMessage();
		if(Constants.SUCCESS_CODE.equals(bean.getError_code())){
			msg.what = RUYI_GUESS_CREATE_GROUP_SUCCESS;
		}else{
			msg.what = RUYI_GUESS_CREATE_GROUP_FAIL;
			msg.obj = bean.getMessage();
		}
		msg.sendToTarget();
	}

	@Override
	public void onGetGuessSubjectCallback(String result,int cmd) {
		Message msg = handler.obtainMessage();
		msg.what = cmd;
		msg.obj = result;
		msg.sendToTarget();
	}


	@Override
	public void onRefresh() {
		if(mAdapter.getCount() > 10)
			mItemCound = Integer.toString(mAdapter.getCount());
		else
			mItemCound = "10";
		netWorkGetData(RUYI_GUESS_REFRESH_GUESS);
	}


	@Override
	public void onLoadMore() {
		mPageIndex ++;
		if(mPageIndex > mTotalPage-1){
			mPageIndex = mTotalPage-1;
			onLoaded();
			showToast(getString(R.string.usercenter_hasgonelast));
		}else {
			netWorkGetData(RUYI_GUESS_GET_GUESS);
		}
	}

}
