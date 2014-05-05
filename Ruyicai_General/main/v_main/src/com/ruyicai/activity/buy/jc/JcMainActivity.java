package com.ruyicai.activity.buy.jc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.BuyGameDialog;
import com.ruyicai.activity.buy.jc.score.zq.JcScoreActivity;
import com.ruyicai.activity.buy.jc.touzhu.TouzhuDialog;
import com.ruyicai.activity.buy.jc.zq.adapter.ChampionshipAdapter;
import com.ruyicai.activity.buy.jc.zq.view.BFView;
import com.ruyicai.activity.buy.jc.zq.view.BQCView;
import com.ruyicai.activity.buy.jc.zq.view.HunHeZqView;
import com.ruyicai.activity.buy.jc.zq.view.RQSPFView;
import com.ruyicai.activity.buy.jc.zq.view.SPfView;
import com.ruyicai.activity.buy.jc.zq.view.ZJQView;
import com.ruyicai.activity.buy.ssq.BettingSuccessActivity;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.usercenter.BetQueryActivity;
import com.ruyicai.component.SlidingView;
import com.ruyicai.component.SlidingView.SlidingViewPageChangeListener;
import com.ruyicai.component.SlidingView.SlidingViewSetCurrentItemListener;
import com.ruyicai.component.jc.buton.MyButton;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.controller.Controller;
import com.ruyicai.data.net.GetGYJTeamInfoAsyncTask;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.model.ChampionshipBean;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.CheckUtil;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.umeng.analytics.MobclickAgent;

public class JcMainActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener, HandlerMsg, View.OnClickListener{
	protected static int TYPE = 0;
	protected final static int SF = 1;// 胜负
	protected final static int RQSPF = 6;// 让求胜平负
	protected final static int RF_SF = 2;// 让分胜负
	protected final static int SFC = 3;// 胜分差
	protected final static int DXF = 4;// 大小分
	protected final static int HUN_HE = 5;// 混合
	protected TextView textTitle;
	private EditText mTextBeishu;
	private SeekBar mSeekBarBeishu;
	public String phonenum;
	public String sessionId;
	public String userno;
	private int iProgressBeishu = 1;
	private MyHandler handler = new MyHandler(this);// 自定义handler
	public BetAndGiftPojo betAndGift = new BetAndGiftPojo();// 投注信息类
	private View viewType;
	protected JcMainView lqMainView;
	protected LinearLayout layoutView;
	protected String type = Constants.JCBASKET;
	private TouzhuDialog touzhuDialog;
	protected TextView textTeamNum;
	protected boolean isDanguan = false;
	protected List<RadioButton> radioBtns = new ArrayList<RadioButton>();
	protected Context context;
	protected List<String> checkTeam = new ArrayList<String>();/* 被选择后的赛事列表 */
	private PopupWindow popupwindow;
	private BuyGameDialog gameDialog;
	private Handler gameHandler = new Handler();
	protected Button imgIcon;
	private String lotNo = Constants.LOTNO_JCL;
	MyButton[] myBtns;
	/**add by yejc 20130812 start*/
	private LinearLayout playLayout;
	private LinearLayout playLayersLayout;
	private LinearLayout teamLayersLayout;
	private LinearLayout teamLayersLayoutUp;
	private LinearLayout teamSelectLayout;
	private LinearLayout teamMainLayout;
	private LinearLayout teamSelectGameLayout;
	private ShowHandler showHandler = new ShowHandler();
	private int screenWidth;
	private int[] bgId= {R.drawable.jc_main_team_select_normal, R.drawable.jc_main_team_select_click};
	private int[] paintColor= {Color.BLACK, Color.WHITE};
	private boolean isFirst = true;
	private String[] leagueName = {"NBA", "五大联赛"};
	private String[] championship = {"欧冠冠军", "世界杯冠军"};
	private List<View> listViews; // Tab页面列表
	private ListView europeLeagueListView;
	private ListView worldCupLeagueListView;
	private ChampionshipAdapter europeAdapter;
	private ChampionshipAdapter worldCupAdapter;
	private SlidingView slidingView;
	private boolean isFirstRequestDate = true;
	public boolean isGyjCurrent = false;
//	protected boolean isFromLotteryHall = false;
	protected boolean isFirstGyjRequest = true;
	/**add by yejc 20130812 end*/

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_jc_main_new);
		context = this;
		isGyjCurrent = getIntent().getBooleanExtra(Constants.IS_FROM_LOTTERY_HALL, false);
		screenWidth = PublicMethod.getDisplayWidth(this);
		initView();
		handler.setBetAndGift(betAndGift);
	}

	public void setLotNo(String lotno) {
		lotNo = lotno;
	}

	/**
	 * 创建下拉列表
	 */
	private void createPoPDialog() {
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = (LinearLayout) inflate.inflate(
				R.layout.buy_group_window, null);
		popupwindow = new PopupWindow(popupView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupwindow.setTouchable(true); // 设置PopupWindow可触摸
		popupwindow.setOutsideTouchable(true);
		popupwindow.setFocusable(true);
		popupwindow.update();
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		popupView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				closePopupWindow();
				return false;
			}
		});
		popupwindow.showAsDropDown(imgIcon);
		final LinearLayout layoutGame = (LinearLayout) popupView
				.findViewById(R.id.buy_group_layout1);
		final LinearLayout layoutHosity = (LinearLayout) popupView
				.findViewById(R.id.buy_group_layout2);
		final LinearLayout layoutQuery = (LinearLayout) popupView
				.findViewById(R.id.buy_group_layout4);
		final LinearLayout layoutParentLuck = (LinearLayout) popupView
				.findViewById(R.id.buy_group_one_layout3);
		layoutGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutGame.setBackgroundResource(R.drawable.buy_group_layout_b);
//				if (gameDialog == null) {
					if (isGyjCurrent) {
						gameDialog = new BuyGameDialog(context, Constants.LOTNO_JCZQ_GJ, gameHandler);
					} else {
						gameDialog = new BuyGameDialog(context, Constants.LOTNO_JCZ, gameHandler);
					}
//				}
				gameDialog.showDialog();
				closePopupWindow();
				MobclickAgent.onEvent(context, "wanfajieshao");
			}
		});
		layoutQuery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RWSharedPreferences shellRW = new RWSharedPreferences(context,
						"addInfo");
				String userno = shellRW.getStringValue(ShellRWConstants.USERNO);
				if (userno == null || userno.equals("")) {
					Intent intentSession = new Intent(context, UserLogin.class);
					startActivity(intentSession);
				} else {
					Intent intent = new Intent(context, BetQueryActivity.class);
					if (isGyjCurrent) {
						intent.putExtra("lotno", Constants.LOTNO_JCZQ_GJ);
					} else {
						intent.putExtra("lotno", Constants.LOTNO_JCZ);
					}
					
					startActivity(intent);
				}
				closePopupWindow();
				MobclickAgent.onEvent(context, "touzhuchaxun");
			}

		});
		layoutHosity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutHosity
						.setBackgroundResource(R.drawable.buy_group_layout_b);
				turnHosity();
				closePopupWindow();
				MobclickAgent.onEvent(context, "lishikaijiang");
			}

		});
		layoutParentLuck.setVisibility(LinearLayout.GONE);
	}
	
	private void closePopupWindow() {
		if(popupwindow != null && popupwindow.isShowing()){
			popupwindow.dismiss();
		}
	}

	public void turnHosity() {

	}

	public void isTeamBtn() {
		Button teamBtn = (Button) findViewById(R.id.buy_lq_main_btn_team);
		teamBtn.setOnClickListener(this);
	}

	public int getTeamNum() {
		return lqMainView.getTeamNum();
	}

	public BetAndGiftPojo getBetAndGiftPojo() {
		return betAndGift;
	}

	public int getIprogressBeiShu() {
		return iProgressBeishu;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 赛事选择窗口
	 */
	public void createTeamDialog() {
		LinearLayout layoutMain = (LinearLayout)findViewById(R.id.jc_linear_check_all);
		if (JcMainView.listTeam != null) {
			myBtns = new MyButton[JcMainView.listTeam.length];
		} else {
			myBtns = new MyButton[0];
		}
		if (JcMainView.listTeam != null && JcMainView.listTeam.length > 0
				&& !"".equals(JcMainView.listTeam[0].trim())) {
			addLayout(layoutMain, myBtns);
		}
		Button all = (Button)findViewById(R.id.all_check);
		Button clear = (Button)findViewById(R.id.clear_check);
		Button fiveLeague = (Button)findViewById(R.id.ok);
		if (Constants.LOTNO_JCL.equals(lotNo)) {
			fiveLeague.setText(leagueName[0]);
		} else {
			fiveLeague.setText(leagueName[1]);
		}
		all.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				for (MyButton btn : myBtns) {
					btn.setOnClick(true);
					btn.switchBg();
				}
				MobclickAgent.onEvent(context, "saishixuanze_quanxuan");
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				for (MyButton btn : myBtns) {
					setMyButtonState(btn, !btn.isOnClick());
				}
				MobclickAgent.onEvent(context, "saishixuanze_fanxuan");
			}
		});
		fiveLeague.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Constants.LOTNO_JCL.equals(lotNo)) {
					for (MyButton btn : myBtns) {
						setMyButtonState(btn, leagueName[0].equals(btn.getBtnText()));
					}
					MobclickAgent.onEvent(context, "saishixuanze_NBA");
				} else {
					for (MyButton btn : myBtns) {
						setMyButtonState(btn, PublicMethod.isFiveLeague(btn.getBtnText()));
					}
					MobclickAgent.onEvent(context, "saishixuanze_wudaliansai");
				}
			}
		});
	}
	
	private void setMyButtonState(MyButton btn, boolean flag) {
		if (flag) {
			btn.setOnClick(true);
		} else {
			btn.setOnClick(false);
		}
		btn.switchBg();
	}

	private void addLayout(LinearLayout layoutMain, MyButton[] myBtns) {
		int length = 0;
		if (JcMainView.listTeam != null) {
			length = JcMainView.listTeam.length;
		}
		int lineNum = 4;// 每行个数
		int lastNum = length % lineNum;// 最后一行个数
		int line = 1;// 行数
		if (length >= lineNum) {
			line = length / lineNum;
			for (int i = 0; i < line; i++) {
				LinearLayout layoutOne = addLine(lineNum, i, myBtns, lineNum);
				layoutMain.addView(layoutOne);
			}
			if (lastNum > 0) {
				LinearLayout layoutOne = addLine(lastNum, line, myBtns, lineNum);
				layoutMain.addView(layoutOne);
			}
		} else {
			LinearLayout layoutOne = addLine(length, 0, myBtns, lineNum);
			layoutMain.addView(layoutOne);
		}
	}

	/**
	 * @param lastNum
	 * @param line 当前行数，从0开始
	 * @param myBtns
	 * @return
	 */
	private LinearLayout addLine(int lastNum, int line, MyButton[] myBtns,
			int lineNum) {
		LinearLayout layoutOne = new LinearLayout(context);
		for (int j = 0; j < lastNum; j++) {
			final MyButton btn = new MyButton(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((screenWidth-PublicMethod.getPxInt(50, context))/4, 
					PublicMethod.getPxInt(42, context));
			if (j == 0) {
				params.setMargins(0,
						PublicMethod.getPxInt(10, context), 0, 0);
			} else {
				params.setMargins(PublicMethod.getPxInt(10, context),
						PublicMethod.getPxInt(10, context), 0, 0);
			}
			
			btn.setLayoutParams(params);
			myBtns[line * lineNum + j] = btn;
			btn.setBtnText(JcMainView.listTeam[line * lineNum + j]);
			/**add by yejc 20130812 start*/
			btn.initBg(bgId);
			btn.setPaintColorArray(paintColor);
			/**add by yejc 20130812 start*/
			btn.onAction();
			
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					btn.onAction();
					MobclickAgent.onEvent(context, "saishixuanze_xuanzemougesaishi");
				}
			});
			layoutOne.addView(btn);
		}
		return layoutOne;
	}

	/**
	 * 初始化倍数和期数进度条
	 * 
	 * @param view
	 */
	public void initImageView(View view) {
		mTextBeishu = (EditText) view.findViewById(R.id.buy_zixuan_text_beishu);
		mSeekBarBeishu = (SeekBar) view
				.findViewById(R.id.buy_jc_zixuan_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mTextBeishu.setText("" + iProgressBeishu);
		PublicMethod.setEditOnclick(mTextBeishu, mSeekBarBeishu, new Handler());
		/*
		 * 点击加减图标，对seekbar进行设置：
		 * 
		 * @param int idFind, 图标的id View iV, 当前的view final int isAdd, 1表示加 -1表示减
		 * final SeekBar mSeekBar
		 * 
		 * @return void
		 */
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_subtract_beishu, -1,
				mSeekBarBeishu, true, view);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_add_beishu, 1, mSeekBarBeishu,
				true, view);
	}

	public void setScoreBtn() {
		Button btnScore = (Button) findViewById(R.id.buy_lq_main_btn_score);
		btnScore.setVisibility(Button.VISIBLE);
		btnScore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(JcMainActivity.this,
						JcScoreActivity.class);
				startActivity(intent);
				MobclickAgent.onEvent(context, "jishibifen");
			}
		});
	}

	/**
	 * 初始化组建
	 */
	public void initView() {
		teamMainLayout = (LinearLayout)findViewById(R.id.buy_jc_main_layout);
		playLayersLayout = (LinearLayout)findViewById(R.id.jc_main_team_layout_layers_middle);
		teamLayersLayout = (LinearLayout)findViewById(R.id.jc_main_team_layout_layers_down);
		teamLayersLayoutUp = (LinearLayout)findViewById(R.id.jc_main_team_layout_layers_up);
		playLayout = (LinearLayout)findViewById(R.id.jc_play_select);
		teamSelectLayout = (LinearLayout)findViewById(R.id.jc_main_team_select);
		teamSelectGameLayout = (LinearLayout)findViewById(R.id.jc_team_select_layout);
		viewType = (LinearLayout)findViewById(R.id.buy_jc_play_select_layout);
		layoutView = (LinearLayout) findViewById(R.id.buy_lq_mian_layout);
		textTitle = (TextView) findViewById(R.id.layout_main_text_title);
		textTeamNum = (TextView) findViewById(R.id.buy_jc_main_text_team_num);
		playLayout.setOnClickListener(this);
		playLayersLayout.setOnClickListener(this);
		teamLayersLayout.setOnClickListener(this);
		teamLayersLayoutUp.setOnClickListener(this);
		ImageButton zixuanTouzhu = (ImageButton) findViewById(R.id.buy_zixuan_img_touzhu);
		zixuanTouzhu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isGyjCurrent) {
					if (slidingView != null ) {
						ChampionshipAdapter adapter = getGyjAdapter(slidingView.getViewPagerCurrentItem());
						if (adapter != null) {
							gyjBeginTouZhu(adapter.getSelectTeamMap().size());
						}
					}
					MobclickAgent.onEvent(context, "gyj_lijitouzhu");
				} else {
					if (lqMainView.isCorrectDanCount()) {
						beginTouZhu();
					}
					MobclickAgent.onEvent(context, "lijitouzhu");
				}
			}
		});
		ImageButton again = (ImageButton) findViewById(R.id.buy_zixuan_img_again);
		again.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isGyjCurrent) {
					if (slidingView != null ) {
						ChampionshipAdapter adapter = getGyjAdapter(slidingView.getViewPagerCurrentItem());
						if (adapter != null && adapter.getSelectTeamMap().size() > 0) {
							//确定要清空X场比赛吗？万一中奖怎么办呢？三思啊！！！
							createDialog("确定要清空"+adapter.getSelectTeamMap().size()+"场比赛吗？万一中奖怎么办呢？三思啊！！！");
						}
					}
					MobclickAgent.onEvent(context, "gyj_chongxuan");
				} else {
					lqMainView.clearChecked();
					lqMainView.initTeamNum(textTeamNum);
					MobclickAgent.onEvent(context, "chongxuan");
				}
			}
		});
		imgIcon = (Button) findViewById(R.id.layout_main_img_return);
		imgIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createPoPDialog();
				MobclickAgent.onEvent(context, "popwindow_menu");
			}
		});
	}
	
	/**
	 * 玩法切换弹出框
	 */
	public void createDialog() {
		viewType.setVisibility(View.VISIBLE);
		teamLayersLayoutUp.setVisibility(View.VISIBLE);
		PublicMethod.setLayoutHeight(45, teamLayersLayoutUp, context);
//		viewType.startAnimation(AnimationUtils.loadAnimation(this, 
//        		R.anim.jc_top_menu_window_enter));
		playLayersLayout.setVisibility(View.VISIBLE);
		TextView text1 = (TextView) viewType
				.findViewById(R.id.buy_lq_main_dialog_type1);
		TextView text2 = (TextView) viewType
				.findViewById(R.id.buy_lq_main_dialog_type2);
		TextView text3 = (TextView) viewType
				.findViewById(R.id.buy_lq_main_dialog_type3);
		TextView text4 = (TextView) viewType
				.findViewById(R.id.buy_lq_main_dialog_type4);
		if (type.equals(Constants.JCBASKET)) {
			text1.setText(getString(R.string.jclq_dialog_sf_guoguan_title)
					.toString());
			text2.setText(getString(R.string.jclq_dialog_rf_guoguan_title)
					.toString());
			text3.setText(getString(R.string.jclq_dialog_sfc_guoguan_title)
					.toString());
			text4.setText(getString(R.string.jclq_dialog_dxf_guoguan_title)
					.toString());
		} else {
			text1.setText(getString(R.string.jczq_dialog_sf_guoguan_title)
					.toString());
			text2.setText(getString(R.string.jczq_dialog_rf_guoguan_title)
					.toString());
			text3.setText(getString(R.string.jczq_dialog_sfc_guoguan_title)
					.toString());
			text4.setText(getString(R.string.jczq_dialog_dxf_guoguan_title)
					.toString());
			
			TextView text5 = (TextView) viewType
					.findViewById(R.id.buy_lq_main_dialog_new_type1);
			text5.setVisibility(View.VISIBLE);
		}
		initRadioGroup(viewType);
	}

	private void initRadioGroup(final View view) {
		RadioButton radio0 = (RadioButton) view.findViewById(R.id.radio0);
		RadioButton radio1 = (RadioButton) view.findViewById(R.id.radio1);
		RadioButton radio2 = (RadioButton) view.findViewById(R.id.radio2);
		RadioButton radio3 = (RadioButton) view.findViewById(R.id.radio3);
		RadioButton radio4 = (RadioButton) view.findViewById(R.id.radio4);
		RadioButton radio5 = (RadioButton) view.findViewById(R.id.radio5);
		RadioButton radio6 = (RadioButton) view.findViewById(R.id.radio6);
		RadioButton radio7 = (RadioButton) view.findViewById(R.id.radio7);
		RadioButton radio8 = (RadioButton) view.findViewById(R.id.radio8);
		radioBtns.add(radio0);
		radioBtns.add(radio1);
		radioBtns.add(radio2);
		radioBtns.add(radio3);
		radioBtns.add(radio4);
		radioBtns.add(radio5);
		radioBtns.add(radio6);
		radioBtns.add(radio7);
		radioBtns.add(radio8);
		
		if (type.equals(Constants.JCFOOT)) {
			RadioButton radio9 = (RadioButton) view.findViewById(R.id.radio_new0);
			RadioButton radio10 = (RadioButton) view.findViewById(R.id.radio_new4);
			radio9.setVisibility(View.VISIBLE);
			radio10.setVisibility(View.VISIBLE);
			radioBtns.add(radio9);
			radioBtns.add(radio10);
			/*****是否需要从后台传回标示来控制显示*******/
			RWSharedPreferences shellRW = new RWSharedPreferences(context,
					ShellRWConstants.CAIZHONGSETTING);
			if (!CheckUtil.isTickedClosed(Constants.GYJLABELCLOSED, shellRW)
					&& Constants.CAIZHONG_OPEN.equals(shellRW.getStringValue(Constants.GYJLABEL))) {
				LinearLayout gyjLayout = (LinearLayout) view
						.findViewById(R.id.buy_jczq_gyj_layout);
				RadioButton radio11 = (RadioButton) view
						.findViewById(R.id.radio11);
				gyjLayout.setVisibility(View.VISIBLE);
				radioBtns.add(radio11);
				if (isGyjCurrent && isFirstGyjRequest) {
					isFirstGyjRequest = false;
					radio11.setChecked(true);
					radio0.setChecked(false);
				}
			}
		}
		for (RadioButton radio : radioBtns) {
			radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						isGyjCurrent = false;
						teamMainLayout.setPadding(0, PublicMethod.getPxInt(85, context), 0, 0);
						teamSelectGameLayout.setVisibility(View.VISIBLE);
						switch (buttonView.getId()) {
						case R.id.radio0:
							isDanguan = false;
							createView(SF, isDanguan);
							break;
						case R.id.radio1:
							isDanguan = false;
							createView(RF_SF, isDanguan);
							break;
						case R.id.radio2:
							isDanguan = false;
							createView(SFC, isDanguan);
							break;
						case R.id.radio3:
							isDanguan = false;
							createView(DXF, isDanguan);
							break;
						case R.id.radio4:
							isDanguan = true;
							createView(SF, isDanguan);
							break;
						case R.id.radio5:
							isDanguan = true;
							createView(RF_SF, isDanguan);
							break;
						case R.id.radio6:
							isDanguan = true;
							createView(SFC, isDanguan);
							break;
						case R.id.radio7:
							isDanguan = true;
							createView(DXF, isDanguan);
							break;
						case R.id.radio8:
							isDanguan = false;
							createView(HUN_HE, isDanguan);
							break;
							
						case R.id.radio_new0:
							isDanguan = false;
							createView(RQSPF, isDanguan);
							break;
							
						case R.id.radio_new4:
							isDanguan = true;
							createView(RQSPF, isDanguan);
							break;
							
						case R.id.radio11:
							showChampionshipLayout();
							break;
						}
						clearRadio(buttonView);
						showHandler.sendEmptyMessageDelayed(1, 500);
						MobclickAgent.onEvent(context, "jcwanfaxuanze");
					}
				}
			});
		}
	}
	
	/**
	 * 显示冠亚军界面
	 */
	@SuppressLint("ResourceAsColor")
	protected void showChampionshipLayout() {
		isGyjCurrent = true;
		if (listViews == null) {
			listViews = new ArrayList<View>();
			LayoutInflater mInflater = getLayoutInflater();
			europeLeagueListView = (ListView)mInflater.inflate(R.layout.jc_zq_league_listview, null);
			worldCupLeagueListView = (ListView)mInflater.inflate(R.layout.jc_zq_league_listview, null);
			listViews.add(europeLeagueListView);
			listViews.add(worldCupLeagueListView);
		}
		textTitle.setText("猜冠军");
		layoutView.removeAllViews();
		teamSelectGameLayout.setVisibility(View.GONE);
		teamMainLayout.setPadding(0, PublicMethod.getPxInt(45, context), 0, 0);
		if (slidingView == null) {
			slidingView = new SlidingView(context, championship, listViews,
					layoutView, 17,getResources().getColor(R.color.red));
			setViewPagerListener();
			setTeamNum(0);
			GetGYJTeamInfoAsyncTask.getInstance(context, GyjTeamInfoHandler).getEuropeInfo();
		} else {
			layoutView.addView(slidingView.getMainView());
			setTeamNumShowState(slidingView.getViewPagerCurrentItem());
			clearGyjAdapter(0);
			clearGyjAdapter(1);
		}
		slidingView.setTabBackgroundColor(R.color.jc_gyj_tab_bg);
		slidingView.setTabHeight(40);
		slidingView.resetCorsorViewValue(screenWidth/2, 0, R.drawable.jc_gyj_tab_bg);
	}
	
	/**
	 * 给viewpager设置监听
	 */
	private void setViewPagerListener(){
		slidingView.addSlidingViewPageChangeListener(new SlidingViewPageChangeListener() {

			@Override
			public void SlidingViewPageChange(int arg0) {
				getData(arg0);
				setTeamNumShowState(arg0);
//				setEndTime(arg0);
				MobclickAgent.onEvent(context, "gyjzhujiemianhuadong");
			}
		});
		
		slidingView.addSlidingViewSetCurrentItemListener(new SlidingViewSetCurrentItemListener() {

			@Override
			public void SlidingViewSetCurrentItem(int index) {
				getData(index);
				setTeamNumShowState(index);
//				setEndTime(index);
				MobclickAgent.onEvent(context, "gyjzhujiemiandianjiedaohang");
			}
		});
	}
	
	/**
	 * 设置选择的球队的数量
	 * @param index
	 */
	private void setTeamNumShowState(int index) {
		ChampionshipAdapter adapter = getGyjAdapter(index);
		if (adapter != null) {
			setTeamNum(adapter.getSelectTeamMap().size());
		} else {
			setTeamNum(0);
		}
	}
	
	private ChampionshipAdapter getGyjAdapter(int index) {
		if (index == 0) {
			return europeAdapter;
		} else {
			return worldCupAdapter;
		}
	}
	
	/**
	 * 获得选择的球队数
	 * @return
	 */
	public int getGyjTeamNum() {
		ChampionshipAdapter adapter = getGyjAdapter(slidingView.getViewPagerCurrentItem());
		if (adapter != null) {
			return adapter.getSelectTeamMap().size();
		}
		return 0;
	}
	
	/**
	 * 获得注码格式
	 * @return
	 */
	public String getCode() {
		ChampionshipAdapter adapter = getGyjAdapter(slidingView.getViewPagerCurrentItem());
		if (adapter != null) {
			return adapter.getCode();
		}
		return "";
	}
	
	/**
	 * 获得方案内容
	 * @return
	 */
	public String getAlertCode() {
		ChampionshipAdapter adapter = getGyjAdapter(slidingView.getViewPagerCurrentItem());
		if (adapter != null) {
			return adapter.getAlertCode();
		}
		return "";
	}
	
	/**
	 * 获得预计奖金
	 * @return
	 */
	public float getGyjPrize() {
		ChampionshipAdapter adapter = getGyjAdapter(slidingView.getViewPagerCurrentItem());
		if (adapter != null) {
			return adapter.getGyjPrize();
		}
		return 0f;
	}
	
	public void setTeamNum(int index) {
		textTeamNum.setText("已选择" + index + "支球队");
	}
	
	/**
	 * 获取对阵数据
	 * @param index
	 */
	private void getData(int index) {
		if (index == 1) {
			if (isFirstRequestDate) {
				GetGYJTeamInfoAsyncTask.getInstance(context, GyjTeamInfoHandler).getworldCupInfo();
				isFirstRequestDate = false;
			}
		}
	}

	private void clearRadio(CompoundButton buttonView) {
		for (RadioButton radio : radioBtns) {
			if (radio.isChecked() && radio.getId() != buttonView.getId()) {
				radio.setChecked(false);
			}
		}
	}

	/**
	 * 根据玩法按钮创建界面
	 */
	protected void createView(int type, boolean isdanguan) {
		switch (type) {
		case SF:
			SPfView.isRQSPF = false;
			lqMainView = new SPfView(this, betAndGift, new Handler(),
					layoutView, this.type, isdanguan, checkTeam);
			TYPE = type;
			break;
		case RQSPF:
			SPfView.isRQSPF = true;
			lqMainView = new RQSPFView(this, betAndGift, new Handler(),
					layoutView, this.type, isdanguan, checkTeam);
			TYPE = type;
			break;
		case RF_SF:
			lqMainView = new ZJQView(this, betAndGift, new Handler(),
					layoutView, this.type, isdanguan, checkTeam);
			TYPE = RF_SF;
			break;
		case SFC:
			lqMainView = new BFView(this, betAndGift, new Handler(),
					layoutView, this.type, isdanguan, checkTeam);
			TYPE = SFC;
			break;
		case DXF:
			lqMainView = new BQCView(this, betAndGift, new Handler(),
					layoutView, this.type, isdanguan, checkTeam);
			TYPE = DXF;
			break;
		case HUN_HE:
			lqMainView = new HunHeZqView(this, betAndGift, new Handler(),
					layoutView, this.type, isdanguan, checkTeam);
			TYPE = HUN_HE;
			break;
		}
		lqMainView.initTeamNum(textTeamNum);
		textTitle.setText(lqMainView.getTitle());

	}

	/**
	 * 投注方法
	 */
	public void beginTouZhu() {
		if (lqMainView.isTouZhuNet()) {
			if (touzhuDialog != null && touzhuDialog.isShowing()) {
				return;
			} else {
				touzhuDialog = new TouzhuDialog(this, lqMainView);
				touzhuDialog.alert();
			}
		}
	}
	
	private void gyjBeginTouZhu(int index) {
		if (index < 1) {
			PublicMethod.createDialog("请至少选择一支球队进行投注", "", context);
		} else {
			touzhuDialog = new TouzhuDialog(this, lqMainView);
			touzhuDialog.alert();
		}
	}

	/**
	 * fqc edit 添加一个参数 isBeiShu 来判断当前是倍数还是期数 。
	 * @param idFind
	 * @param iV
	 * @param isAdd
	 * @param mSeekBar
	 * @param isBeiShu
	 */
	protected void setSeekWhenAddOrSub(int idFind, final int isAdd,
			final SeekBar mSeekBar, final boolean isBeiShu, View view) {
		ImageButton subtractBeishuBtn = (ImageButton) view.findViewById(idFind);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isBeiShu) {
					if (isAdd == 1) {
						mSeekBar.setProgress(++iProgressBeishu);
					} else {
						mSeekBar.setProgress(--iProgressBeishu);
					}
					iProgressBeishu = mSeekBar.getProgress();
				}
			}
		});
	}

	/**
	 * 初始化投注信息
	 */
	public void initBet() {
		betAndGift.setSessionid(sessionId);
		betAndGift.setPhonenum(phonenum);
		betAndGift.setUserno(userno);
		betAndGift.setBettype("bet");// 投注为bet,赠彩为gift
	}

	public void toJoinActivity() {
		createView(TYPE, isDanguan);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
			objStream.writeObject(betAndGift);
		} catch (IOException e) {
			return; // should not happen, so donot do error handling
		}
		Intent intent = new Intent(JcMainActivity.this,
				JoinStartActivityjc.class);
		intent.putExtra("info", byteStream.toByteArray());
		startActivity(intent);
	}

	/**
	 * 投注联网
	 */
	public void touZhuNet() {
		if (isGyjCurrent) {
//			GetGYJTeamInfoAsyncTask task = GetGYJTeamInfoAsyncTask.getInstance(context, GyjTeamInfoHandler);
			if (slidingView != null) {
//				String batchCode = task.getIssue(slidingView.getViewPagerCurrentItem());
				if (slidingView.getViewPagerCurrentItem() == 0) {
					betAndGift.setBatchcode(GetGYJTeamInfoAsyncTask.EUROPE_ISSUE);
				} else {
					betAndGift.setBatchcode(GetGYJTeamInfoAsyncTask.WORLDCUP_ISSUE);
				}
			}
		}
		Controller.getInstance(JcMainActivity.this).doBettingAction(handler, betAndGift);
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();
		switch (seekBar.getId()) {
		case R.id.buy_jc_zixuan_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			if (touzhuDialog != null) {
				if (isGyjCurrent) {
					touzhuDialog.setGyjAlertText();
					touzhuDialog.setGyjPrizeText();
				} else {
					touzhuDialog.setAlertText();
					touzhuDialog.setPrizeText();
				}
			}
			break;
		}
	}

	public void onDestroy() {
		super.onDestroy();
		lqMainView.clearInfo();
	}

	public void errorCode_0000() {
		if (isGyjCurrent) {
			if (slidingView != null) {
				clearGyjAdapter(slidingView.getViewPagerCurrentItem());
			}
		} else {
			createView(TYPE, isDanguan);
		}
		/**modify by pengcx 20130605 start*/
		Intent intent = new Intent(this, BettingSuccessActivity.class);
		intent.putExtra("page", BettingSuccessActivity.BETTING);
		intent.putExtra("lotno", betAndGift.getLotno());
		intent.putExtra("amount", betAndGift.getAmount());
		startActivity(intent);
		/**modify by pengcx 20130605 end*/
	}

	public void errorCode_000000() {
	}

	public Context getContext() {
		return this;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	/**
	 * 重写放回建
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case 4:
			if (viewType.getVisibility() == View.VISIBLE) {
				setLayoutGone();
			} else if (teamSelectLayout.getVisibility() == View.VISIBLE){
				teamSelectLayout.setVisibility(View.GONE);
				teamLayersLayout.setVisibility(View.GONE);
				teamLayersLayoutUp.setVisibility(View.GONE);
			} else {
				lqMainView.clearInfo();
				finish();
			}
			break;
		}
		MobclickAgent.onEvent(context, "onKeyDown");
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);// BY贺思明 2012-7-24
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);// BY贺思明 2012-7-24
	}
	
	
	class ShowHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				setLayoutGone();
				break;
			}
		}
	}
	
	private void showSelectedTeam() {
		checkTeam.clear();
		for (MyButton btn : myBtns) {
			if (btn.isOnClick()) {
				checkTeam.add(btn.getBtnText());
			}
		}
		if (checkTeam.size() != 0 || myBtns.length == 0) {
			teamSelectLayout.setVisibility(View.GONE);
			teamLayersLayout.setVisibility(View.GONE);
			teamLayersLayoutUp.setVisibility(View.GONE);
			lqMainView.updateList(checkTeam);
		} else {
			Toast.makeText(context, "请至少选择一个赛事!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.jc_main_team_layout_layers_middle:
			setLayoutGone();
			MobclickAgent.onEvent(context, "jc_main_team_layout_layers_middle");
			break;

		case R.id.jc_main_team_layout_layers_down:
			showSelectedTeam();
			MobclickAgent.onEvent(context, "jc_main_team_layout_layers_down");
			break;
			
		case R.id.jc_main_team_layout_layers_up:
			if (teamSelectLayout.getVisibility() == View.VISIBLE) {
				showSelectedTeam();
			} else {
				setLayoutGone();
			}
			MobclickAgent.onEvent(context, "jc_main_team_layout_layers_up");
			break;
			
		case R.id.buy_lq_main_btn_team:
			if (teamSelectLayout.getVisibility() == View.GONE
					|| teamSelectLayout.getVisibility() == View.INVISIBLE) {
				if (isFirst) {
					createTeamDialog();
					isFirst = false;
				}
				teamSelectLayout.setVisibility(View.VISIBLE);
				teamLayersLayout.setVisibility(View.VISIBLE);
				teamLayersLayoutUp.setVisibility(View.VISIBLE);
				PublicMethod.setLayoutHeight(85, teamLayersLayoutUp, context);
//				teamSelectLayout.startAnimation(AnimationUtils.loadAnimation(
//						context, R.anim.jc_top_menu_window_enter));
			} else {
				showSelectedTeam();
			}
			MobclickAgent.onEvent(context, "saishixuanze");
			break;
			
		case R.id.jc_play_select:
			createDialog();
			MobclickAgent.onEvent(context, "wanfaxuanze");
			break;
		}
	}
	
	private void setLayoutGone() {
		viewType.setVisibility(View.GONE);
		playLayersLayout.setVisibility(View.GONE);
		teamLayersLayoutUp.setVisibility(View.GONE);
	}
	
	Handler GyjTeamInfoHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			List<ChampionshipBean> list = (List<ChampionshipBean>)msg.obj;
			switch (msg.what) {
			case 0:
				europeAdapter= new ChampionshipAdapter(list, context, false);
				setEndTime(0, europeLeagueListView);
				europeLeagueListView.setAdapter(europeAdapter);
				break;

			case 1:
				worldCupAdapter= new ChampionshipAdapter(list, context, true);
				setEndTime(1, worldCupLeagueListView);
				worldCupLeagueListView.setAdapter(worldCupAdapter);
				break;
			}
		}
	};
	
	/**
	 * 设置结束时间
	 * @param index
	 */
	private void setEndTime(int index, ListView listview) {
		if (listview != null && listview.getHeaderViewsCount() == 0) {
			String endTime = "";
			Map<String, String> map = GetGYJTeamInfoAsyncTask.getInstance(context, GyjTeamInfoHandler).getInfoMap();
			if (map != null) {
				if (index == 0) {
					endTime = map.get(GetGYJTeamInfoAsyncTask.EUROPE_ISSUE);
				} else {
					endTime = map.get(GetGYJTeamInfoAsyncTask.WORLDCUP_ISSUE);
				}
			}
			LayoutInflater inflater = getLayoutInflater();
			TextView endTimeTV = (TextView)inflater.inflate(R.layout.buy_jcgyj_textview, null);
			endTimeTV.setText("截止时间:"+endTime);
			listview.addHeaderView(endTimeTV);
//			GetGYJTeamInfoAsyncTask task = GetGYJTeamInfoAsyncTask.getInstance(context, GyjTeamInfoHandler);
//			String[] endTime = task.getEndTime();
//			if (endTime != null && endTime.length > 1) {
//				LayoutInflater inflater = getLayoutInflater();
//				TextView endTimeTV = (TextView)inflater.inflate(R.layout.buy_jcgyj_textview, null);
//				endTimeTV.setText("截止时间:"+endTime[index]);
//				listview.addHeaderView(endTimeTV);
//			}
		}
	}
	
	public void clearGyjAdapter(int index) {
		ChampionshipAdapter adapter = getGyjAdapter(index);
		if (adapter != null) {
			adapter.getSelectTeamMap().clear();
			adapter.notifyDataSetChanged();
			setTeamNum(0);
		}
	}
	
	public void createDialog(String string) {
		Builder dialog = new AlertDialog.Builder(context).setMessage(string)
				.setPositiveButton("废话，删掉！", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						clearGyjAdapter(slidingView.getViewPagerCurrentItem());
					}
				}).setNegativeButton("算了，我手抖~", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		dialog.show();
	}
	
}