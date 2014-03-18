package com.ruyicai.activity.buy.jc.touzhu;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ApplicationAddview;
import com.ruyicai.activity.buy.jc.JcMainActivity;
import com.ruyicai.activity.buy.jc.JcMainView;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.umeng.analytics.MobclickAgent;

/**
 * 投注类
 * 
 * @author Administrator
 * 
 */
public class TouzhuDialog {
	JcMainActivity context;
	RadioGroupView viewGroup;
	Dialog dialog = null;
	String alertMsg;
	int teamNum = 0;
	int oneAmt = 2;
	private JcMainView jcMainView;
	public double freedomMaxprize = 0;
	public double freedomMixprize = 0;
	public int zhuShu = 0;
	public boolean isRadio = false;// false是自由过关,true是多串过关
	String returnStr = "";

	/** add by pengcx 20130703 start */
	private TextView lotoTypeTextView;
	private TextView gameNumTextView;
	private TextView betNumTextView;
	private TextView moneyTextView;
	private TextView predictMoneyTextView;
	private TextView schemeTextView;
	private TextView schemeDetailTextView;
	private ImageView upDownImageView;
	private RelativeLayout schemeRelativeLayout;
	private LinearLayout schemeDetailLinearLayout;

	/** add by pengcx 20130703 end */

	public TouzhuDialog(JcMainActivity context, JcMainView jcMainView) {
		this.context = context;
		this.jcMainView = jcMainView;
		viewGroup = new RadioGroupView(context.getContext(), this);
	}

	public int getTeamNum() {
		if (context.isGyjCurrent) {
			return context.getGyjTeamNum();
		} else {
			return context.getTeamNum();
		}
	}

	/**
	 * 初始化一些信息
	 */
	private void initInfo() {
		this.alertMsg = jcMainView.getAlertMsg();
		this.teamNum = jcMainView.initCheckedNum();
		isRadio = false;
	}
	
	/**
	 * 冠亚军
	 */
	private void initGyjInfo() {
		this.alertMsg = context.getAlertCode();
		this.teamNum = context.getGyjTeamNum();
		isRadio = false;
	}

	/**
	 * 投注调用函数
	 * 
	 * @param string
	 *            显示框信息
	 * @return
	 */
	public void alert() {
		if (context.isGyjCurrent) {
			initGyjInfo();
		} else {
			initInfo();
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.alert_dialog_jc_touzhu, null);
		dialog = new Dialog(context, R.style.MyDialog);
		/** add by pengcx 20130703 start */
		lotoTypeTextView = (TextView) v
				.findViewById(R.id.alert_dialog_jc_lotnotype);
		gameNumTextView = (TextView) v
				.findViewById(R.id.alert_dialog_jc_gamenum);
		betNumTextView = (TextView) v.findViewById(R.id.alert_dialog_jc_betnum);
		moneyTextView = (TextView) v.findViewById(R.id.alert_dialog_jc_money);
		predictMoneyTextView = (TextView) v
				.findViewById(R.id.alert_dialog_jc_predictmoney);
		schemeTextView = (TextView) v
				.findViewById(R.id.alert_dialog_touzhu_alert_scheme);
		schemeTextView.setText(Html.fromHtml(alertMsg));
		schemeDetailTextView = (TextView) v
				.findViewById(R.id.alert_dialog_touzhu_alert_textview_schemedetail);
		upDownImageView = (ImageView) v.findViewById(R.id.alert_dialog_touzhu_updown);
		schemeRelativeLayout = (RelativeLayout) v
				.findViewById(R.id.alert_dialog_touzhu_linear_qihao_beishu);
		schemeDetailLinearLayout = (LinearLayout) v
				.findViewById(R.id.alert_dialog_touzhu_alert_schemedetail);
		
		schemeDetailTextView.setText(Html.fromHtml(alertMsg));
		schemeRelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/**modify by pengcx 20130805 start*/
				int visibility = schemeDetailLinearLayout.getVisibility();
				if (visibility == View.VISIBLE) {
					schemeDetailLinearLayout.setVisibility(View.GONE);
					schemeTextView.setVisibility(View.VISIBLE);
					upDownImageView.setImageResource(R.drawable.down_icon);
					MobclickAgent.onEvent(context, "jctouzhu_dialog_fangan_xianshi");
				} else {
					schemeDetailLinearLayout.setVisibility(View.VISIBLE);
					upDownImageView.setImageResource(R.drawable.up_icon);
					schemeTextView.setVisibility(View.INVISIBLE);
					MobclickAgent.onEvent(context, "jctouzhu_dialog_fangan_yincang");
				}
				/**modify by pengcx 20130805 end*/
			}
		});
		
		/** add by pengcx 20130703 end */

		context.initImageView(v);
		if (context.isGyjCurrent) {
			lotoTypeTextView.setText("竞彩足球冠亚军");
			setGyjAlertText();
			setGyjPrizeText();
		} else {
			lotoTypeTextView.setText(PublicMethod.toLotno(jcMainView.getLotno()));
			setAlertText();
			setPrizeText();
		}
		initBtn(dialog, v);
		dialog.setContentView(v);
		dialog.show();
	}

	/**
	 * 初始化投注框里的组件
	 * 
	 * @param dialog
	 * @param cancel
	 * @param ok
	 * @param infoBtn
	 * @param zhuma
	 */
	private void initBtn(final Dialog dialog, View v) {
		TextView labe = (TextView) v
				.findViewById(R.id.alert_dialog_touzhu_text_fangshi);
		LinearLayout btnlayout = (LinearLayout) v
				.findViewById(R.id.buttonlayout);
		Button cancel = (Button) v
				.findViewById(R.id.alert_dialog_touzhu_button_cancel);
		Button ok = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_ok);
		final Button zyBtn = (Button) v.findViewById(R.id.jc_alert_btn_ziyou);
		final Button dcBtn = (Button) v
				.findViewById(R.id.jc_alert_btn_duochuan);
		final LinearLayout layout = (LinearLayout) v
				.findViewById(R.id.alert_dialog_jc_layout_group);
		if (context.isGyjCurrent) {
			LinearLayout guoGuanlayout = (LinearLayout)v.findViewById(R.id.alert_dialog_guoguan_fangshi);
			guoGuanlayout.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
			ok.setVisibility(View.GONE);
			Button gyjOk = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_gyj_ok);
			gyjOk.setVisibility(View.VISIBLE);
			gyjOk.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					initUserInfo();
					if (context.userno == null || context.userno.equals("")) {
						Intent intentSession = new Intent(context, UserLogin.class);
						context.startActivityForResult(intentSession, 0);
					} else {
						gyjBeginTouzhu();
					}
					MobclickAgent.onEvent(context, "jcgyjtouzhu_dialog_Ok");
				}
			});
		} else {
			if (jcMainView.isHunHe()) {
				zyBtn.setVisibility(View.GONE);
				dcBtn.setVisibility(View.GONE);
			}
			if (jcMainView.isDanguan) {
				labe.setText("过关方式：单关");
				zhuShu = jcMainView.getDanZhushu();
				btnlayout.setVisibility(View.INVISIBLE);
				setPrizeText();
				setAlertText();
			} else {
				btnlayout.setVisibility(View.VISIBLE);
				labe.setText("过关方式：");
				onclikBtn(layout, zyBtn, dcBtn);
				zyBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						isRadio = false;
						onclikBtn(layout, zyBtn, dcBtn);
						MobclickAgent.onEvent(context, "jctouzhu_dialog_ziyouguoguan");
					}
				});
				dcBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						isRadio = true;
						onclikBtn(layout, zyBtn, dcBtn);
						MobclickAgent.onEvent(context, "jctouzhu_dialog_duochuanguoguan");
					}
				});
			}
			final String title = "投注详情";
			cancel.setOnClickListener(touzhuOrhemaiListener);
			ok.setOnClickListener(touzhuOrhemaiListener);
		}
	}

	private void initUserInfo() {
		RWSharedPreferences pre = new RWSharedPreferences(context,
				"addInfo");
		context.sessionId = pre.getStringValue("sessionid");
		context.phonenum = pre.getStringValue("phonenum");
		context.userno = pre.getStringValue("userno");
	}
	OnClickListener touzhuOrhemaiListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			initUserInfo();
			if (context.userno == null || context.userno.equals("")) {
				Intent intentSession = new Intent(context, UserLogin.class);
				context.startActivityForResult(intentSession, 0);
			} else if (zhuShu == 0) {
				Toast.makeText(context, "请选择过关方式", Toast.LENGTH_SHORT).show();
			} else {
				if (zhuShu > 100000) {
					alertInfo(
							context.getString(R.string.jc_main_touzhu_alert_text_content_zhushu),
							context.getString(R.string.jc_main_touzhu_alert_text_title));
				} else {
					switch (v.getId()) {
					case R.id.alert_dialog_touzhu_button_cancel:
						initBet();
						context.toJoinActivity();
						MobclickAgent.onEvent(context, "jctouzhu_dialog_cancel");
						break;
					case R.id.alert_dialog_touzhu_button_ok:
						beginTouzhu();
						MobclickAgent.onEvent(context, "jctouzhu_dialog_ok");
						break;
					}
					dialog.cancel();
				}
			}

		}
	};

	/**
	 * 提示框1 用来提醒选球规则
	 * 
	 * @param string
	 *            显示框信息
	 * @return
	 */
	public void alertInfo(String string, String title) {
		Builder dialog = new AlertDialog.Builder(context).setTitle(title)
				.setMessage(string)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		dialog.show();
	}

	/**
	 * 自由和多串过关按钮事件
	 * 
	 * @param layout
	 * @param zyBtn
	 * @param dcBtn
	 */
	private void onclikBtn(final LinearLayout layout, Button zyBtn, Button dcBtn) {
		freedomMaxprize = 0;
		freedomMixprize = 0;
		setPrizeText();
		if (isRadio) {
			dcBtn.setBackgroundResource(R.drawable.jc_alert_right_radio_b);
			zyBtn.setBackgroundResource(R.drawable.jc_alert_left_radio);
		} else {
			dcBtn.setBackgroundResource(R.drawable.jc_alert_right_radio);
			zyBtn.setBackgroundResource(R.drawable.jc_alert_left_radio_b);
		}
		layout.removeAllViews();
		layout.addView(viewGroup.createView(isRadio, teamNum));
	}

	private void initBet() {
		context.initBet();
		context.getBetAndGiftPojo().setAmount("" + getAllAmt() * 100);
		context.getBetAndGiftPojo().setLotmulti("" + getBeishu());
		/** add by pengcx 20130609 start */
		context.getBetAndGiftPojo().setPredictMoney(returnStr);
		/** add by pengcx 20130609 end */
		if (jcMainView.isDanguan) {
			context.getBetAndGiftPojo().setBet_code(
					getBetCode(context.getString(R.string.jc_touzhu_DAN)) + "_"
							+ PublicMethod.isTen(getBeishu()) + "_" + oneAmt
							* 100 + "_" + jcMainView.getDanZhushu() * oneAmt
							* 100 + "!");
		} else {
			context.getBetAndGiftPojo().setBet_code(viewGroup.getBetCode());
		}
		context.getBetAndGiftPojo().setIsSellWays("1");
	}

	private void beginTouzhu() {
		initBet();
		ApplicationAddview app = (ApplicationAddview) context
				.getApplicationContext();
		app.setPojo(context.betAndGift);
		context.touZhuNet();
	}
	
	private void gyjBeginTouzhu() {
		StringBuffer buffer = new StringBuffer();
		context.initBet();
		context.getBetAndGiftPojo().setAmount("" + teamNum * getBeishu() * 2 * 100);
		context.getBetAndGiftPojo().setLotmulti("" + getBeishu());
		context.getBetAndGiftPojo().setPredictMoney(returnStr);
		buffer.append(context.getCode());
		buffer.append("_");
		buffer.append(PublicMethod.isTen(getBeishu()));
		buffer.append("_");
		buffer.append(oneAmt * 100);
		buffer.append("_");
		buffer.append(teamNum * oneAmt * 100);
		buffer.append("!");
		context.getBetAndGiftPojo().setBet_code(buffer.toString());
		context.getBetAndGiftPojo().setIsSellWays("1");
		context.getBetAndGiftPojo().setSellway("0");
		context.getBetAndGiftPojo().setLotno(Constants.LOTNO_JCZQ_GJ);
		ApplicationAddview app = (ApplicationAddview) context
				.getApplicationContext();
		app.setPojo(context.betAndGift);
		context.touZhuNet();
		if (dialog != null) {
			dialog.cancel();
		}
	}

	/**
	 * 中奖范围
	 */
	public void setPrizeText() {
		if (jcMainView.isDanguan) {
			returnStr = jcMainView.getDanPrizeString(getBeishu());
		} else {
			if (!isRadio) {
				returnStr = getFreedomGuoGuanPrize(context.getIprogressBeiShu());
			} else {
				returnStr = getFreedomGuoGuanPrize(context.getIprogressBeiShu());
			}
		}
		/** add by pengcx 20130703 start */
		predictMoneyTextView.setText(returnStr);
		/** add by pengcx 20130703 end */
	}
	
	/**
	 * 中奖范围
	 */
	public void setGyjPrizeText() {
		returnStr = String.valueOf(PublicMethod.formatStringToTwoPoint(context.getGyjPrize() * getBeishu() *2))+"元";
		predictMoneyTextView.setText(returnStr);
	}

	public String getFreedomGuoGuanPrize(int muti) {
		double max = freedomMaxprize * muti;
		double mix = freedomMixprize * muti;
		StringBuffer result = new StringBuffer();
		/** mofidy by pengcx 20130703 start */
		result.append(PublicMethod.formatStringToTwoPoint(mix)).append("元~")
				.append(PublicMethod.formatStringToTwoPoint(max)).append("元 ");
		/** mofidy by pengcx 20130703 end */
		return result + "";
	}

	public double getFreedomDuoMixPrize(int teamNum, int select) {
		return jcMainView.getDuoMixPrize(teamNum, select);
	}

	public double getFreedomDuoMaxPrize(int teamNum, int select) {
		return jcMainView.getDuoMaxPrize(teamNum, select);
	}

	public double getFreedomMaxPrize(int select) {
		if (jcMainView.isHunHe()) {
			return jcMainView.getFreedomHunMaxPrize(select);
		} else {
			return jcMainView.getFreedomMaxPrize(select);
		}

	}

	public double getFreedomMixPrize(int select) {
		return jcMainView.getFreedomMixPrize(select);
	}

	/**
	 * 提示信息
	 */
	public void setAlertText() {
		/** add by pengcx 20130703 start */
		betNumTextView.setText("共" + zhuShu + "注");
		moneyTextView.setText("共" + getAllAmt() + "元");
		gameNumTextView.setText("共" + teamNum + "场");
		/** add by pengcx 20130703 end */
	}
	
	/**
	 * 提示信息
	 */
	public void setGyjAlertText() {
		int num = context.getGyjTeamNum();
		betNumTextView.setText("共" + num + "注");
		moneyTextView.setText("共" + getBeishu() * num * 2 + "元");
		gameNumTextView.setText("已选择" + num + "支球队");
	}
	
	

	/**
	 * 获取设胆个数
	 * 
	 * @return
	 */
	public int getIsDanNum() {
		return jcMainView.getIsDanNum();
	}

	public int getZhushu(int select) {
		return jcMainView.getZhushu(select);
	}

	public int getBeishu() {
		return context.getIprogressBeiShu();
	}

	public int getAllAmt() {
		return zhuShu * getBeishu() * 2;
	}

	public String getBetCode(String type) {
		return jcMainView.getBetCode(type);
	}

	public List<double[]> getOddsArraysValue() {
		return jcMainView.getOddsArraysValue();
	}

	/**
	 * 多串过关计算注数
	 * 
	 * @param select
	 * @return
	 */
	public int getDuoZhushu(int teamNum, int select) {
		return jcMainView.getDuoZhushu(teamNum, select);
	}
	
	/**add by yejc 20130801 start*/
	public boolean isShowing() {
		return dialog.isShowing();
	}
	/**add by yejc 20130801 end*/
}
