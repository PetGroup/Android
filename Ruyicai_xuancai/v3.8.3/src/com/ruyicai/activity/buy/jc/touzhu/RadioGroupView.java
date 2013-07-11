package com.ruyicai.activity.buy.jc.touzhu;

import java.util.ArrayList;
import java.util.List;

import com.lthj.unipay.plugin.ca;
import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.beijing.BeiJingSingleGameIndentActivity;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.PublicMethod;

import android.R.bool;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * 投注选择项组类
 * 
 * @author Administrator
 * 
 */
public class RadioGroupView {
	private Context context;
	private int checkNum;// 选项组个数
	private final int LineNum = 4;
	public List<RadioButton> radioBtns = new ArrayList<RadioButton>();
	private int radioTextId[] = { R.string.jc_touzhu_radio3_3,
			R.string.jc_touzhu_radio3_4, R.string.jc_touzhu_radio4_4,
			R.string.jc_touzhu_radio4_5, R.string.jc_touzhu_radio4_6,
			R.string.jc_touzhu_radio4_11, R.string.jc_touzhu_radio5_5,
			R.string.jc_touzhu_radio5_6, R.string.jc_touzhu_radio5_10,
			R.string.jc_touzhu_radio5_16, R.string.jc_touzhu_radio5_20,
			R.string.jc_touzhu_radio5_26, R.string.jc_touzhu_radio6_6,
			R.string.jc_touzhu_radio6_7, R.string.jc_touzhu_radio6_15,
			R.string.jc_touzhu_radio6_20, R.string.jc_touzhu_radio6_22,
			R.string.jc_touzhu_radio6_35, R.string.jc_touzhu_radio6_42,
			R.string.jc_touzhu_radio6_50, R.string.jc_touzhu_radio6_57,
			R.string.jc_touzhu_radio7_7, R.string.jc_touzhu_radio7_8,
			R.string.jc_touzhu_radio7_21, R.string.jc_touzhu_radio7_35,
			R.string.jc_touzhu_radio7_120, R.string.jc_touzhu_radio8_8,
			R.string.jc_touzhu_radio8_9, R.string.jc_touzhu_radio8_28,
			R.string.jc_touzhu_radio8_56, R.string.jc_touzhu_radio8_70,
			R.string.jc_touzhu_radio8_247 };

	private int beijingTextId[] = { R.string.jc_touzhu_radio2_3,
			R.string.jc_touzhu_radio3_4, R.string.jc_touzhu_radio3_7,
			R.string.jc_touzhu_radio4_5, R.string.jc_touzhu_radio4_11,
			R.string.jc_touzhu_radio4_15, R.string.jc_touzhu_radio5_6,
			R.string.jc_touzhu_radio5_16, R.string.jc_touzhu_radio5_26,
			R.string.jc_touzhu_radio5_31, R.string.jc_touzhu_radio6_7,
			R.string.jc_touzhu_radio6_22, R.string.jc_touzhu_radio6_42,
			R.string.jc_touzhu_radio6_57, R.string.jc_touzhu_radio6_63 };
	private TouzhuDialog touzhuDialog;
	private int oneAmt = 2;// 单注金额
	private boolean isDan = false;
	private int maxTeam = 8;// 最大串关数，8代表8串一
	private int beijingChuanMaxTeam = 6;
	private int beijingMaxTeam = 15;
	CheckBox checks[] = new CheckBox[7];
	public CheckBox beijingChecks[] = new CheckBox[15];

	public RadioGroupView(Context context, TouzhuDialog touzhuDialog) {
		this.context = context;
		this.touzhuDialog = touzhuDialog;
		setMaxTeam(this.touzhuDialog.getTeamNum());
	}

	public RadioGroupView(Context context) {
		this.context = context;
	}

	public View createView(boolean isDan, int teamNum) {
		this.isDan = isDan;
		if (isDan) {
			return createDanView(teamNum);
		} else {
			return createDuoView(teamNum);
		}
	}

	/**
	 * 设置最大串关数
	 */
	public void setMaxTeam(int teamNum) {
		maxTeam = teamNum;
	}

	/**
	 * 创建单式机选界面
	 * 
	 * @param number
	 * @return
	 */
	public View createDanView(int teamNum) {
		touzhuDialog.zhuShu = 0;
		touzhuDialog.setAlertText();
		int checkTeam = teamNum;
		if (teamNum > maxTeam) {
			teamNum = maxTeam;
		}
		int danNum = getNum(teamNum, true);
		int danTeamNum = touzhuDialog.getIsDanNum();
		int isDanNum = getDanNum(danTeamNum, true);
		LinearLayout layoutMain = new LinearLayout(context);
		layoutMain.setOrientation(LinearLayout.VERTICAL);
		if (danNum > LineNum) {
			int num = danNum % LineNum;
			int line = danNum / LineNum;
			for (int i = 0; i < line; i++) {
				addLine(layoutMain, i, LineNum, isDanNum, danTeamNum,
						checkTeam, false);
			}
			if (num != 0) {
				addLine(layoutMain, line, num, isDanNum, danTeamNum, checkTeam,
						false);
			}
		} else {
			LinearLayout layoutOne = new LinearLayout(context);
			addLine(layoutMain, 0, danNum, isDanNum, danTeamNum, checkTeam,
					false);
		}

		return layoutMain;
	}

	public View createBeijingDanView(int teamNum) {
		int checkTeam = teamNum;
		if (teamNum > beijingChuanMaxTeam) {
			teamNum = beijingChuanMaxTeam;
		}
		int danNum = getBeijingCheckNum(teamNum);

		LinearLayout layoutMain = new LinearLayout(context);
		layoutMain.setOrientation(LinearLayout.VERTICAL);
		if (danNum > LineNum) {
			int num = danNum % LineNum;
			int line = danNum / LineNum;
			for (int i = 0; i < line; i++) {
				addLine(layoutMain, i, LineNum, 0, 0, teamNum, true);
			}
			if (num != 0) {
				addLine(layoutMain, line, num, 0, 0, teamNum, true);
			}
		} else {
			LinearLayout layoutOne = new LinearLayout(context);
			addLine(layoutMain, 0, danNum, 0, 0, teamNum, true);
		}

		return layoutMain;
	}

	private int getBeijingCheckNum(int teamNum) {
		int num = 0;
		switch (teamNum) {
		case 1:
			num = 0;
			break;
		case 2:
			num = 1;
			break;
		case 3:
			num = 3;
			break;
		case 4:
			num = 6;
			break;
		case 5:
			num = 10;
			break;
		case 6:
			num = 15;
			break;
		}
		return num;
	}

	/**
	 * 加载每一行的单选按钮
	 * 
	 * @param layoutMain
	 * @param line
	 * @param lineNum
	 */
	private void addLine(LinearLayout layoutMain, int line, int lineNum,
			int isDanNum, int danTeamNum, int teamNum, final boolean isBeijing) {
		LinearLayout layoutOne = new LinearLayout(context);
		int id = 0;
		int last = getNum(teamNum - 1, true);
		boolean isCheck = isLastCheck(teamNum, danTeamNum);
		for (int j = 0; j < lineNum; j++) {
			id = line * this.LineNum + j;
			RadioButton radio = new RadioButton(context);
			if (id < isDanNum) {
				radio.setEnabled(false);
				radio.setTextColor(Color.GRAY);
				radio.setButtonDrawable(R.drawable.radio_select);
			} else if (!isCheck && id >= last) {
				radio.setEnabled(false);
				radio.setTextColor(Color.GRAY);
				radio.setButtonDrawable(R.drawable.radio_select);
			} else {
				radio.setTextColor(Color.BLACK);
				radio.setButtonDrawable(R.drawable.radio_select);
			}
			if (isBeijing) {
				radio.setText(beijingTextId[id]);
			} else {
				radio.setText(radioTextId[id]);
			}

			int width = PublicMethod.getDisplayWidth(context);
			if (width == 720) {
				radio.setTextSize(PublicMethod.getPxInt(7, context));
			} else if (width == 640) {
				radio.setTextSize(PublicMethod.getPxInt(7, context));
			} else if (width == 240) {
				radio.setTextSize(PublicMethod.getPxInt(20, context));
			} else if (width == 320) {
				radio.setTextSize(PublicMethod.getPxInt(15, context));
			} else if (width == 800) {
				radio.setTextSize(PublicMethod.getPxInt(8, context));
			} else {
				radio.setTextSize(PublicMethod.getPxInt(10, context));
			}
			if (Constants.SCREEN_HEIGHT == 854) {
				radio.setTextSize(PublicMethod.getPxInt(8, context));
			}
			radio.setId(id);
			radio.setPadding(PublicMethod.getPxInt(20, context), 0, 0, 0);
			int withPx = PublicMethod.getPxInt(75, context);// 将dip换算成px
			radio.setLayoutParams(new LayoutParams(withPx,
					LayoutParams.WRAP_CONTENT));
			radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						if (isBeijing) {
							((BeiJingSingleGameIndentActivity) context).bettingNum = getBeijingRadioZhu(buttonView
									.getText().toString());
							((BeiJingSingleGameIndentActivity) context)
									.setBettingInformationShow();
						} else {
							touzhuDialog.zhuShu = getRadioZhu(buttonView
									.getText().toString());
							touzhuDialog.setAlertText();
							setRadioPrize(buttonView.getText().toString());
						}
						clearRadio(buttonView);

					}
				}

			});
			radioBtns.add(radio);
			layoutOne.addView(radio);
		}
		layoutMain.addView(layoutOne);
	}

	/**
	 * 清空单选按钮
	 * 
	 * @param buttonView
	 */
	private void clearRadio(CompoundButton buttonView) {
		for (RadioButton radio : radioBtns) {
			if (radio.isChecked() && radio.getId() != buttonView.getId()) {
				radio.setChecked(false);
			}
		}
	}

	/**
	 * 创建多选界面
	 * 
	 * @param visiableNum
	 * @return
	 */
	public View createDuoView(int teamNum) {
		touzhuDialog.zhuShu = 0;
		touzhuDialog.setAlertText();
		int teamCheck = teamNum;
		if (teamNum > maxTeam) {
			teamNum = maxTeam;
		}
		int num = getNum(teamNum, false);
		int danTeamNum = touzhuDialog.getIsDanNum();
		int danNum = getDanNum(danTeamNum, false);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.buy_jc_touzhu_group_duo, null);
		int[] checkId = { R.id.checkBox1, R.id.checkBox2, R.id.checkBox3,
				R.id.checkBox4, R.id.checkBox5, R.id.checkBox6, R.id.checkBox7 };
		for (int i = 0; i < checkId.length; i++) {
			checks[i] = (CheckBox) v.findViewById(checkId[i]);
			checks[i].setId(i);
			if (i >= num) {
				checks[i].setVisibility(CheckBox.GONE);
			} else if (i < danNum) {
				checks[i].setEnabled(false);
				checks[i].setTextColor(Color.GRAY);
			} else {
				checks[i]
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								int checknum = buttonView.getId();
								
								if (isChecked) {
									touzhuDialog.freedomMaxprize += touzhuDialog
											.getFreedomMaxPrize(checknum + 2);
									touzhuDialog.zhuShu += touzhuDialog
											.getZhushu(buttonView.getId() + 2);
									
									/**add by yejc 20130509 start*/
//									if (touzhuDialog.zhuShu > 10000 | touzhuDialog.getAllAmt() > 200000) {
//										if (isChecked) {
//											buttonView.setChecked(false);
//										}
//										touzhuDialog.freedomMaxprize -= touzhuDialog
//												.getFreedomMaxPrize(checknum + 2);
//										touzhuDialog.zhuShu -= touzhuDialog
//												.getZhushu(buttonView.getId() + 2);
//										if (touzhuDialog.zhuShu > 10000) {
//											Toast.makeText(context, "倍数不能大于10000注", Toast.LENGTH_LONG).show();
//										} else if (touzhuDialog.getAllAmt() > 200000) {
//											Toast.makeText(context, "单笔投注金额不能大于200000", Toast.LENGTH_LONG).show();
//										}
//									} 
									/**add by yejc 20130509 end*/
									
								} else {
									touzhuDialog.freedomMaxprize -= touzhuDialog
											.getFreedomMaxPrize(checknum + 2);
									touzhuDialog.zhuShu -= touzhuDialog
											.getZhushu(buttonView.getId() + 2);
								}
								int mixPrize = isMixChecked();
								if (mixPrize == 0) {
									touzhuDialog.freedomMixprize = 0;
								} else {
									touzhuDialog.freedomMixprize = touzhuDialog
											.getFreedomMixPrize(mixPrize);
								}
								touzhuDialog.setAlertText();
								touzhuDialog.setPrizeText();
							}
						});
			}
		}
		if (!isLastCheck(teamCheck, danTeamNum)) {
			checks[num - 1].setEnabled(false);
			checks[num - 1].setTextColor(Color.GRAY);
		}
		return v;
	}

	public View createBeijingDuoView(int teamNum) {

		int teamCheck = teamNum;
		if (teamNum > beijingMaxTeam) {
			teamNum = beijingMaxTeam;
		}
		int num = getBeijingRadioNum(teamNum);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.beijing_touzhu_group_duo, null);
		int[] checkId = { R.id.checkBoxDan, R.id.checkBox1, R.id.checkBox2,
				R.id.checkBox3, R.id.checkBox4, R.id.checkBox5, R.id.checkBox6,
				R.id.checkBox7, R.id.checkBox8, R.id.checkBox9,
				R.id.checkBox10, R.id.checkBox11, R.id.checkBox12,
				R.id.checkBox13, R.id.checkBox14 };
		for (int i = 0; i < checkId.length; i++) {
			beijingChecks[i] = (CheckBox) v.findViewById(checkId[i]);
			beijingChecks[i].setId(i);
			/*Add by pengcx 20130516 start*/
			beijingChecks[i].setTextSize(15.0f);
			/*Add by pengcx 20130516 end*/
			if (i >= num) {
				beijingChecks[i].setVisibility(CheckBox.GONE);
			} else if (i < 0) {
				beijingChecks[i].setEnabled(false);
				beijingChecks[i].setTextColor(Color.GRAY);
			} else {
				beijingChecks[i]
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								int checknum = buttonView.getId();
								if (isChecked) {
//									Log.i("111", "增加的注数:" +((BeiJingSingleGameIndentActivity) context)
//											.getBettingNum(buttonView.getId() + 1));
									((BeiJingSingleGameIndentActivity) context).bettingNum += ((BeiJingSingleGameIndentActivity) context)
											.getBettingNum(buttonView.getId() + 1);
								} else {
//									Log.i("111", "减少的注数:" +((BeiJingSingleGameIndentActivity) context)
//											.getBettingNum(buttonView.getId() + 1));
									((BeiJingSingleGameIndentActivity) context).bettingNum -= ((BeiJingSingleGameIndentActivity) context)
											.getBettingNum(buttonView.getId() + 1);
								}
								
//								Log.i("111","结果注数:"+((BeiJingSingleGameIndentActivity) context).bettingNum);
								((BeiJingSingleGameIndentActivity) context)
										.setBettingInformationShow();
							}
						});
			}
		}
		if (!isLastCheck(teamCheck, 0)) {
			beijingChecks[num - 1].setEnabled(false);
			beijingChecks[num - 1].setTextColor(Color.GRAY);
		}
		return v;
	}

	private int getBeijingRadioNum(int teamNum) {
		int num = 0;
		switch (teamNum) {
		case 1:
			num = 1;
			break;
		case 2:
			num = 2;
			break;
		case 3:
			num = 3;
			break;
		case 4:
			num = 4;
			break;
		case 5:
			num = 5;
			break;
		case 6:
			num = 6;
			break;
		case 7:
			num = 7;
			break;
		case 8:
			num = 8;
			break;
		case 9:
			num = 9;
			break;
		case 10:
			num = 10;
			break;
		case 11:
			num = 11;
			break;
		case 12:
			num = 12;
			break;
		case 13:
			num = 13;
			break;
		case 14:
			num = 14;
			break;
		case 15:
			num = 15;
			break;
		}
		return num;
	}

	/**
	 * 最后一个多选按钮是否可以选
	 * 
	 * @return
	 */
	public boolean isLastCheck(int teamNum, int danTeamNum) {
		if (danTeamNum > 0 && teamNum <= maxTeam) {
			return false;
		} else {
			return true;
		}
	}

	public int isMixChecked() {
		for (int i = 0; i < checks.length; i++) {
			if (checks[i].isChecked()) {
				return i + 2;
			}
		}
		return 0;
	}

	/**
	 * 根据选则的球队计算单选按钮数
	 * 
	 * @param num
	 * @return
	 */
	public int getNum(int teamNum, boolean isDan) {
		// isDan=true是多串过关
		int num = 0;
		switch (teamNum) {
		case 2:
			if (isDan) {
				num = 0;
			} else {
				num = 1;
			}
			break;
		case 3:
			if (isDan) {
				num = 2;
			} else {
				num = 2;
			}
			break;
		case 4:
			if (isDan) {
				num = 6;
			} else {
				num = 3;
			}
			break;
		case 5:
			if (isDan) {
				num = 12;
			} else {
				num = 4;
			}
			break;
		case 6:
			if (isDan) {
				num = 21;
			} else {
				num = 5;
			}
			break;
		case 7:
			if (isDan) {
				num = 26;
			} else {
				num = 6;
			}
			break;
		case 8:
			if (isDan) {
				num = 32;
			} else {
				num = 7;
			}
			break;
		default:
			if (isDan) {
				num = 32;
			} else {
				num = 7;
			}
		}
		return num;
	}

	/**
	 * 根据设胆数隐藏按钮数
	 * 
	 * @param num
	 * @return
	 */
	public int getDanNum(int teamNum, boolean isDan) {
		// isDan=true是多串过关
		int num = 0;
		switch (teamNum) {
		case 2:
			if (isDan) {
				num = 0;
			} else {
				num = 1;
			}
			break;
		case 3:
			if (isDan) {
				num = 2;
			} else {
				num = 2;
			}
			break;
		case 4:
			if (isDan) {
				num = 6;
			} else {
				num = 3;
			}
			break;
		case 5:
			if (isDan) {
				num = 12;
			} else {
				num = 4;
			}
			break;
		case 6:
			if (isDan) {
				num = 21;
			} else {
				num = 5;
			}
			break;
		case 7:
			if (isDan) {
				num = 26;
			} else {
				num = 6;
			}
			break;
		case 8:
			if (isDan) {
				num = 32;
			} else {
				num = 7;
			}
		case 9:
			if (isDan) {
				num = 32;
			} else {
				num = 7;
			}
			break;
		}
		return num;
	}

	/**
	 * 获得投注时的注码
	 * 
	 * @return
	 */
	public String getBetCode() {
		if (isDan) {
			return getRadioCode();
		} else {
			return getCheckCode();
		}
	}

	/**
	 * 获得多选注码
	 * 
	 * @param check
	 * @return
	 */
	private String getCheckCode() {
		String betCode = "";
		for (CheckBox check : checks) {
			if (check.isChecked()) {
				betCode += touzhuDialog.getBetCode(check.getText().toString())
						+ "_" + PublicMethod.isTen(touzhuDialog.getBeishu())
						+ "_" + oneAmt * 100 + "_"
						+ touzhuDialog.getZhushu(check.getId() + 2) * oneAmt
						* 100 + "!";
			}
		}
		return betCode.substring(0, betCode.length() - 1);
	}

	/**
	 * 获得单选注码
	 * 
	 * @param check
	 * @return
	 */
	private String getRadioCode() {
		String betCode = "";
		for (RadioButton radio : radioBtns) {
			if (radio.isChecked()) {
				betCode += touzhuDialog.getBetCode(radio.getText().toString())
						+ "_" + PublicMethod.isTen(touzhuDialog.getBeishu())
						+ "_" + oneAmt * 100 + "_"
						+ getRadioZhu(radio.getText().toString()) * oneAmt
						* 100 + "!";
			}
		}
		return betCode.substring(0, betCode.length() - 1);
	}

	public long getBeijingRadioZhu(String radioText) {
		long zhuShu = 0;

		if (radioText.equals(getString(R.string.jc_touzhu_radio2_3))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(2, 1)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(2, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio3_4))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(3, 2)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(3, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio3_7))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(3, 1)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(3, 2)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(3, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_5))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(4, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(4, 4);

		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_11))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(4, 2)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(4, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(4, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_15))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(4, 1)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(4, 2)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(4, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(4, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_6))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(5, 4)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_16))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(5, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 4)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_26))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(5, 2)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 4)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_31))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(5, 1)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 2)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 4)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(5, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_7))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(6, 5)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 6);

		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_22))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(6, 4)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 5)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_42))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(6, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 4)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 5)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_57))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(6, 2)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 4)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 5)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_63))) {
			zhuShu = ((BeiJingSingleGameIndentActivity) context)
					.getChuanBettingNum(6, 1)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 2)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 3)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 4)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 5)
					+ ((BeiJingSingleGameIndentActivity) context)
							.getChuanBettingNum(6, 6);
		}

		return zhuShu;
	}

	private int getRadioZhu(String radioText) {
		int zhuShu = 0;

		if (radioText.equals(getString(R.string.jc_touzhu_radio3_3))) {
			zhuShu = touzhuDialog.getDuoZhushu(3, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio3_4))) {
			zhuShu = touzhuDialog.getDuoZhushu(3, 2)
					+ touzhuDialog.getDuoZhushu(3, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_4))) {
			zhuShu = touzhuDialog.getDuoZhushu(4, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_5))) {
			zhuShu = touzhuDialog.getDuoZhushu(4, 3)
					+ touzhuDialog.getDuoZhushu(4, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_6))) {
			zhuShu = touzhuDialog.getDuoZhushu(4, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_11))) {
			zhuShu = touzhuDialog.getDuoZhushu(4, 2)
					+ touzhuDialog.getDuoZhushu(4, 3)
					+ touzhuDialog.getDuoZhushu(4, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_5))) {
			zhuShu = touzhuDialog.getDuoZhushu(5, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_6))) {
			zhuShu = touzhuDialog.getDuoZhushu(5, 4)
					+ touzhuDialog.getDuoZhushu(5, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_10))) {
			zhuShu = touzhuDialog.getDuoZhushu(5, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_16))) {
			zhuShu = touzhuDialog.getDuoZhushu(5, 3)
					+ touzhuDialog.getDuoZhushu(5, 4)
					+ touzhuDialog.getDuoZhushu(5, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_20))) {
			zhuShu = touzhuDialog.getDuoZhushu(5, 2)
					+ touzhuDialog.getDuoZhushu(5, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_26))) {
			zhuShu = touzhuDialog.getDuoZhushu(5, 2)
					+ touzhuDialog.getDuoZhushu(5, 3)
					+ touzhuDialog.getDuoZhushu(5, 4)
					+ touzhuDialog.getDuoZhushu(5, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_6))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_7))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 5)
					+ touzhuDialog.getDuoZhushu(6, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_15))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_20))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_22))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 4)
					+ touzhuDialog.getDuoZhushu(6, 5)
					+ touzhuDialog.getDuoZhushu(6, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_35))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 2)
					+ touzhuDialog.getDuoZhushu(6, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_42))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 3)
					+ touzhuDialog.getDuoZhushu(6, 4)
					+ touzhuDialog.getDuoZhushu(6, 5)
					+ touzhuDialog.getDuoZhushu(6, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_50))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 2)
					+ touzhuDialog.getDuoZhushu(6, 3)
					+ touzhuDialog.getDuoZhushu(6, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_57))) {
			zhuShu = touzhuDialog.getDuoZhushu(6, 2)
					+ touzhuDialog.getDuoZhushu(6, 3)
					+ touzhuDialog.getDuoZhushu(6, 4)
					+ touzhuDialog.getDuoZhushu(6, 5)
					+ touzhuDialog.getDuoZhushu(6, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_7))) {
			zhuShu = touzhuDialog.getDuoZhushu(7, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_8))) {
			zhuShu = touzhuDialog.getDuoZhushu(7, 6)
					+ touzhuDialog.getDuoZhushu(7, 7);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_21))) {
			zhuShu = touzhuDialog.getDuoZhushu(7, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_35))) {
			zhuShu = touzhuDialog.getDuoZhushu(7, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_120))) {
			zhuShu = touzhuDialog.getDuoZhushu(7, 2)
					+ touzhuDialog.getDuoZhushu(7, 3)
					+ touzhuDialog.getDuoZhushu(7, 4)
					+ touzhuDialog.getDuoZhushu(7, 5)
					+ touzhuDialog.getDuoZhushu(7, 6)
					+ touzhuDialog.getDuoZhushu(7, 7);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_8))) {
			zhuShu = touzhuDialog.getDuoZhushu(8, 7);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_9))) {
			zhuShu = touzhuDialog.getDuoZhushu(8, 7)
					+ touzhuDialog.getDuoZhushu(8, 8);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_28))) {
			zhuShu = touzhuDialog.getDuoZhushu(8, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_56))) {
			zhuShu = touzhuDialog.getDuoZhushu(8, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_70))) {
			zhuShu = touzhuDialog.getDuoZhushu(8, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_247))) {
			zhuShu = touzhuDialog.getDuoZhushu(8, 2)
					+ touzhuDialog.getDuoZhushu(8, 3)
					+ touzhuDialog.getDuoZhushu(8, 4)
					+ touzhuDialog.getDuoZhushu(8, 5)
					+ touzhuDialog.getDuoZhushu(8, 6)
					+ touzhuDialog.getDuoZhushu(8, 7)
					+ touzhuDialog.getDuoZhushu(8, 8);
		}
		return zhuShu;
	}

	private void setRadioPrize(String radioText) {
		if (radioText.equals(getString(R.string.jc_touzhu_radio3_3))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					3, 2);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					3, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio3_4))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					3, 2) + touzhuDialog.getFreedomDuoMaxPrize(3, 3);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					3, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_4))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					4, 3);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					4, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_5))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					4, 3) + touzhuDialog.getFreedomDuoMaxPrize(4, 4);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					4, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_6))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					4, 2);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					4, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio4_11))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					4, 2)
					+ touzhuDialog.getFreedomDuoMaxPrize(4, 3)
					+ touzhuDialog.getFreedomDuoMaxPrize(4, 4);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					4, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_5))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					5, 4);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					5, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_6))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					5, 4) + touzhuDialog.getFreedomDuoMaxPrize(5, 5);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					5, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_10))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					5, 2);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					5, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_16))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					5, 3)
					+ touzhuDialog.getFreedomDuoMaxPrize(5, 4)
					+ touzhuDialog.getFreedomDuoMaxPrize(5, 5);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					5, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_20))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					5, 2) + touzhuDialog.getFreedomDuoMaxPrize(5, 3);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					5, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio5_26))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					5, 2)
					+ touzhuDialog.getFreedomDuoMaxPrize(5, 3)
					+ touzhuDialog.getFreedomDuoMaxPrize(5, 4)
					+ touzhuDialog.getFreedomDuoMaxPrize(5, 5);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					5, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_6))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 5);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_7))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 5) + touzhuDialog.getFreedomDuoMaxPrize(6, 6);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_15))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 2);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_20))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 3);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_22))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 4)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 5)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 6);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_35))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 2) + touzhuDialog.getFreedomDuoMaxPrize(6, 3);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_42))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 3)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 4)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 5)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 6);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 3);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_50))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 2)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 3)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 4);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio6_57))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					6, 2)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 3)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 4)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 5)
					+ touzhuDialog.getFreedomDuoMaxPrize(6, 6);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					6, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_7))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					7, 6);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					7, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_8))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					7, 6) + touzhuDialog.getFreedomDuoMaxPrize(7, 7);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					7, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_21))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					7, 5);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					7, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_35))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					7, 4);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					7, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio7_120))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					7, 2)
					+ touzhuDialog.getFreedomDuoMaxPrize(7, 3)
					+ touzhuDialog.getFreedomDuoMaxPrize(7, 4)
					+ touzhuDialog.getFreedomDuoMaxPrize(7, 5)
					+ touzhuDialog.getFreedomDuoMaxPrize(7, 6)
					+ touzhuDialog.getFreedomDuoMaxPrize(7, 7);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					7, 2);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_8))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					8, 7);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					8, 7);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_9))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					8, 7) + touzhuDialog.getFreedomDuoMaxPrize(8, 8);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					8, 7);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_28))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					8, 6);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					8, 6);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_56))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					8, 5);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					8, 5);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_70))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					8, 4);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					8, 4);
		} else if (radioText.equals(getString(R.string.jc_touzhu_radio8_247))) {
			touzhuDialog.freedomMaxprize = touzhuDialog.getFreedomDuoMaxPrize(
					8, 2)
					+ touzhuDialog.getFreedomDuoMaxPrize(8, 3)
					+ touzhuDialog.getFreedomDuoMaxPrize(8, 4)
					+ touzhuDialog.getFreedomDuoMaxPrize(8, 5)
					+ touzhuDialog.getFreedomDuoMaxPrize(8, 6)
					+ touzhuDialog.getFreedomDuoMaxPrize(8, 7)
					+ touzhuDialog.getFreedomDuoMaxPrize(8, 8);
			touzhuDialog.freedomMixprize = touzhuDialog.getFreedomDuoMixPrize(
					8, 2);
		}
		touzhuDialog.setPrizeText();
	}

	private String getString(int id) {
		return context.getString(id);
	}
}
