package com.ruyicai.activity.notice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 * 
 */
public class NoticeBallView extends View {
	private Paint p = null;
	private Context context;
	/** 开奖走势图的列数 */
	private int row;
	/** 开奖走势的行数 */
	private int line;
	/** 开奖走势的起始值 */
	private int startNum;
	List<RowInfo> list;
	List<RowInfo> blueList;
	private int with;
	private int height;
	/** 小球的宽度 */
	private int WITH = 25;
	/** 期号和开奖号码的宽度 */
	private int FIRST_WITH = 350;
	/** 开奖号码的宽度 */
	private int SECOND_WITH = 210;
	/** 缩放比率 */
	private float release = (float) 1.4;
	/** 彩种的类型 */
	private String iGameType;
	/** 是否是红球走势 */
	private boolean isRed;

	Bitmap bitWhite;
	Bitmap bitGrey;

	Bitmap bitNoticeRed;
	Bitmap bitNoticeTopRed;
	Bitmap bitNoticeTopBlue;

	Bitmap bitNoticeBlue;
	Bitmap bitNoticeYellow;

	Bitmap bitLeftRed;
	Bitmap bitFirstLeftRed;
	Bitmap bitLeftWhite;
	Bitmap bitRedBall;
	Bitmap bitBlueBall;
	Bitmap bitBlackBall;
	Bitmap bitNmk3Ico;
	Bitmap downSelectButton;

	// 快三小球 by afs 2014年3月26日10:41:04
	Bitmap bitRedBall_ks;// single
	Bitmap bitBlueBall_ks;// double
	Bitmap bitGreenBall_ks;// three same

	// 记录了选号小球的位置集合
	ArrayList<BallPosition> ballsList = new ArrayList<NoticeBallView.BallPosition>();
	// 选中小球的位置集合
	ArrayList<BallPosition> ballsChcekOne = new ArrayList<NoticeBallView.BallPosition>();
	ArrayList<BallPosition> ballsChcekTwo = new ArrayList<NoticeBallView.BallPosition>();
	ArrayList<BallPosition> selectButtonList = new ArrayList<NoticeBallView.BallPosition>();
	/** 小于10的数字是否显示2位 */
	boolean isTen = true;

	float startX;
	float startY;
	Toast toast;
	TextView textCodeOne;
	/** add by pengcx 20130809 start */
	TextView textCodeTow;

	TextView hundredPart;
	TextView decadePart;
	TextView unitPart;
	TextView hundredTwoPart;
	TextView decadeTwoPart;
	TextView unitTwoPart;
	boolean isBeforeThree;
	private int[] hPBallNums=new int[3];

	public boolean getIsBeforThree() {
		return isBeforeThree;
	}

	public void setIsBeforThree(boolean isBeforThree) {
		this.isBeforeThree = isBeforThree;
	}

	public TextView getHundredPart() {
		return hundredPart;
	}

	public void setHundredPart(TextView hundredPart) {
		this.hundredPart = hundredPart;
	}

	public TextView getDecadePart() {
		return decadePart;
	}

	public void setDecadePart(TextView decadePart) {
		this.decadePart = decadePart;
	}

	public TextView getUnitPart() {
		return unitPart;
	}

	public void setUnitPart(TextView unitPart) {
		this.unitPart = unitPart;
	}

	public TextView getTextCodeTow() {
		return textCodeTow;
	}

	public void setTextCodeTow(TextView textCodeTow) {
		this.textCodeTow = textCodeTow;
	}

	public TextView getHundredTwoPart() {
		return hundredTwoPart;
	}

	public void setHundredTwoPart(TextView hundredTwoPart) {
		this.hundredTwoPart = hundredTwoPart;
	}

	public TextView getDecadeTwoPart() {
		return decadeTwoPart;
	}

	public void setDecadeTwoPart(TextView decadeTwoPart) {
		this.decadeTwoPart = decadeTwoPart;
	}

	public TextView getUnitTwoPart() {
		return unitTwoPart;
	}

	public void setUnitTwoPart(TextView unitTwoPart) {
		this.unitTwoPart = unitTwoPart;
	}

	/** add by pengcx 20130809 end */
	private boolean isFirstDraw = true;

	public boolean isFirstDraw() {
		return isFirstDraw;
	}

	public void setFirstDraw(boolean isFirstDraw) {
		this.isFirstDraw = isFirstDraw;
	}

	/** add by pengcx 20130808 start */
	/** 是否是选号栏 */
	private boolean isSelectedBar;

	/** add by pengcx 20130808 end */

	public boolean onTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			startX = ev.getX();
			startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			float absX = Math.abs(ev.getX() - startX);
			float absY = Math.abs(ev.getY() - startY);
			if (absX < 30 && absY < 30) {
				// 获取手指点击的位置
				float x = ev.getX();
				float y = ev.getY();

				if ((textCodeOne != null && textCodeTow != null && !(iGameType
						.equalsIgnoreCase("qlc") && !isRed))
						|| ((hundredPart != null && hundredTwoPart != null
								&& decadePart != null && decadeTwoPart != null
								&& unitPart != null && unitTwoPart != null))
						&& (iGameType.equalsIgnoreCase("fc3d")
								|| iGameType.equals("pl3") || (iGameType
								.equals("gd11-5") || iGameType.equals("11-5") || iGameType
									.equals("11-ydj")) && isBeforeThree)) {
					for (int i = 0; i < ballsList.size(); i++) {
						// 获取绘制小球的位置
						BallPosition ball = ballsList.get(i);
						float ballX = ball.getLeft();
						float ballY = ball.getTop();
						float width = ball.getWidth();
						/** modify by pengcx 20130809 start */
						int selectedRow = ball.getSelectRow();

						// 点中某个小球
						if ((x > ballX && x < ballX + width)
								&& (y > ballY && y < ballY + width)) {
							if (selectedRow == 0) {
								boolean isBall = isBallChecked(ballX, ballY,
										selectedRow);
								if (isBall) {
									if ((iGameType.equals("gd11-5")
											|| iGameType.equals("11-5") || iGameType
												.equals("11-ydj"))
											&& isBeforeThree) {
										int method = ((NoticeBallActivity) context).oneSelectButtonSpinner
												.getSelectedItemPosition();
										int part = ball.getPart();
										if (method == 0) {
											if (part == 0) {
												ballsChcekOne.add(ball);
											}
										} else if (method == 1 || method == 3) {
											if (part == 0 || part == 1) {
												if (method == 3) {
													if (!isRepeat(
															ball.getNum(),
															ballsChcekOne)) {
														ballsChcekOne.add(ball);
													}
												} else {
													ballsChcekOne.add(ball);
												}

											}
										} else if (method == 4) {
											if (!isRepeat(ball.getNum(),
													ballsChcekOne)) {
												ballsChcekOne.add(ball);
											}
										} else {
											ballsChcekOne.add(ball);
										}
									} else {
										ballsChcekOne.add(ball);
									}

								}
							} else {
								boolean isBall = isBallChecked(ballX, ballY,
										selectedRow);
								if (isBall) {
									if ((iGameType.equals("gd11-5")
											|| iGameType.equals("11-5") || iGameType
												.equals("11-ydj"))
											&& isBeforeThree) {
										int method = ((NoticeBallActivity) context).twoSelectButtonSpinner
												.getSelectedItemPosition();
										int part = ball.getPart();
										if (method == 0) {
											if (part == 0) {
												ballsChcekTwo.add(ball);
											}
										} else if (method == 1 || method == 3) {
											if (part == 0 || part == 1) {
												if (method == 3) {
													if (!isRepeat(
															ball.getNum(),
															ballsChcekTwo)) {
														ballsChcekTwo.add(ball);
													}
												} else {
													ballsChcekTwo.add(ball);
												}
											}
										} else if (method == 4) {
											if (!isRepeat(ball.getNum(),
													ballsChcekTwo)) {
												ballsChcekTwo.add(ball);
											}
										} else {
											ballsChcekTwo.add(ball);
										}
									} else {
										ballsChcekTwo.add(ball);
									}
								}
							}

							invalidate();
							break;
						}
						/** modify by pengcx 20130809 end */
					}
					if (iGameType.equalsIgnoreCase("11-5")
							|| iGameType.equalsIgnoreCase("gd11-5")
							|| ((iGameType.equals("gd11-5")
									|| iGameType.equals("11-5") || iGameType
										.equals("11-ydj")) && isBeforeThree)) {
						for (int i = 0; i < selectButtonList.size(); i++) {
							BallPosition ball = selectButtonList.get(i);
							float ballX = ball.getLeft();
							float ballY = ball.getTop();
							float width = ball.getWidth();
							float height = ball.getHeight();
							int selectedRow = ball.getSelectRow();
							int part = ball.getPart();

							// 点中某个小球
							if ((x > ballX && x < ballX + width)
									&& (y > ballY && y < ballY + height)) {
								if ((iGameType.equals("gd11-5")
										|| iGameType.equals("11-5") || iGameType
											.equals("11-ydj")) && isBeforeThree) {
									if (selectedRow == 2) {
										((NoticeBallActivity) context).oneSelectButtonSpinner
												.performClick();
									} else if (selectedRow == 3) {
										((NoticeBallActivity) context).twoSelectButtonSpinner
												.performClick();
									}
								} else {
									if (selectedRow == 0) {
										((NoticeBallActivity) context).oneSelectButtonSpinner
												.performClick();
									} else if (selectedRow == 1) {
										((NoticeBallActivity) context).twoSelectButtonSpinner
												.performClick();
									}
								}

							}
						}
					}
				}
			}
			break;
		}
		return true;
	}

	private boolean isRepeat(String num, ArrayList<BallPosition> ballsChcekOne2) {
		boolean isRepeat = false;
		for (int i = 0; i < ballsChcekOne2.size(); i++) {
			if (num.equals(ballsChcekOne2.get(i).getNum())) {
				isRepeat = true;
			}
		}
		return isRepeat;
	}

	public TextView getTextCode() {
		return textCodeOne;
	}

	public void setTextCode(TextView textCode) {
		this.textCodeOne = textCode;
	}

	/**
	 * 小球是否可以被选中
	 * 
	 * @param ballX
	 *            小球左坐标
	 * @param ballY
	 *            小球上坐标
	 * @return
	 */
	private boolean isBallChecked(float ballX, float ballY, int selectRow) {
		boolean isOnclick = true;
		/** modify by pengcx 20130809 start */
		if (selectRow == 0) {
			for (int j = 0; j < ballsChcekOne.size(); j++) {
				BallPosition selectedBall = ballsChcekOne.get(j);
				if (selectedBall.getLeft() == ballX
						&& selectedBall.getTop() == ballY
						&& selectedBall.getSelectRow() == selectRow) {
					ballsChcekOne.remove(j);
					isOnclick = false;
				}
			}
		} else {
			for (int j = 0; j < ballsChcekTwo.size(); j++) {
				BallPosition selectedBall = ballsChcekTwo.get(j);
				if (selectedBall.getLeft() == ballX
						&& selectedBall.getTop() == ballY
						&& selectedBall.getSelectRow() == selectRow) {
					ballsChcekTwo.remove(j);
					isOnclick = false;
				}
			}

		}
		/** modify by pengcx 20130809 end */

		return isOnclick;
	}

	/**
	 * @param context
	 */
	public NoticeBallView(Context context) {
		super(context);
		this.context = context;
	}

	public NoticeBallView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

	}

	public NoticeBallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}

	/**
	 * 
	 * @param line行数
	 * @param row列数
	 * @param startStr启始值
	 * @param list
	 *            彩种信息的数组
	 */

	int ballsStartLefPostion = 0;

	public void initNoticeBall(int line, int row, int startNum,
			List<JSONObject> list, boolean isRed, String lotno, double release) {
		iGameType = lotno;
		this.isRed = isRed;
		this.line = line;
		this.row = row;
		this.startNum = startNum;
		this.list = initRowInfo(list, isRed);
		this.blueList = initRowInfo(list, false);
		this.release = (float) release;
		WITH = (int) (WITH * release);
		if (lotno.equals("gd-10")) {
			if (isRed) {
				FIRST_WITH = 1;
			} else {
				FIRST_WITH = (int) (FIRST_WITH * release);
				SECOND_WITH = (int) (SECOND_WITH * release); // add by yejc
				// 20130805
				setFistWith();
			}
		} else {
			if (isRed) {
				FIRST_WITH = (int) (FIRST_WITH * release);
				setFistWith();
			} else {
				FIRST_WITH = 1;
			}
		}

		if (lotno.equals("fc3d")
				|| lotno.equals("pl3")
				|| ((iGameType.equals("gd11-5") || iGameType.equals("11-5") || iGameType
						.equals("11-ydj")) && isBeforeThree)) {
			with = 3 * row * WITH + FIRST_WITH;
		} else if (lotno.equals("pl5") || lotno.equals("ssc")) {
			with = 5 * row * WITH + FIRST_WITH;
		} else if (lotno.equals("qxc")) {
			with = 7 * row * WITH + FIRST_WITH;
		} else {
			with = row * WITH + FIRST_WITH;
		}
		/** modify by pengcx 20130808 start */
		if (!isSelectedBar) {
			height = (line + 1) * WITH;
		} else {
			height = line * WITH;
		}
		// 内蒙快三多出来一行
		if (lotno.equals("nmk3") || iGameType.equals("jlk3")||iGameType.equals("happy-poker")) {
			height += WITH;
		}

		// he zhi and kuo du ,add with
		if (isAddHeZhiAndKuoDu()) {
			with += SECOND_WITH / 2;
		}

		ballsStartLefPostion = GetHeTotalWidthWithZhiAndKuoDu();

		/** modify by pengcx 20130808 end */
		setPaint();
		initImage();

	}

	/*
	 * 判断是否增加和值和跨度
	 */
	public Boolean isAddHeZhiAndKuoDu() {
		// 增加和值，跨度走势
		if (iGameType.equals("fc3d") || iGameType.equals("pl3")
				|| iGameType.equals("pl5") || iGameType.equals("ssc")
				|| (iGameType.equals("gd-10") && !isRed)
				|| iGameType.equals("cq-11-5") || iGameType.equals("gd11-5")
				|| iGameType.equals("11-5") || iGameType.equals("11-ydj")) {
			return true;
		}
		return false;
	}

	public void setFistWith() {
		if (Constants.SCREEN_WIDTH == 240) {
			FIRST_WITH = PublicMethod.getPxInt(200, context);
			SECOND_WITH = PublicMethod.getPxInt(100, context);
		} else if (Constants.SCREEN_WIDTH == 320) {
			FIRST_WITH = PublicMethod.getPxInt(200, context);
			SECOND_WITH = PublicMethod.getPxInt(100, context);
		} else if (Constants.SCREEN_WIDTH == 480) {
			return;
		} else if (Constants.SCREEN_WIDTH == 540) {
			FIRST_WITH = PublicMethod.getPxInt(330, context);
		} else {
			return;
		}

	}

	public void initImage() {
		bitWhite = getBitmapFromRes(R.drawable.notice_center_wite, WITH, WITH);
		bitGrey = getBitmapFromRes(R.drawable.notice_center_grey, WITH, WITH);

		bitNoticeRed = getBitmapFromRes(R.drawable.notice_top_red, WITH, WITH);

		bitNoticeTopRed = getBitmapFromRes(R.drawable.notice_top_red,
				ballsStartLefPostion, WITH);
		bitNoticeTopBlue = getBitmapFromRes(R.drawable.notice_top_blue,
				ballsStartLefPostion, WITH);

		bitNoticeBlue = getBitmapFromRes(R.drawable.notice_top_blue, WITH, WITH);
		bitNoticeYellow = getBitmapFromRes(R.drawable.notice_top_yellow, WITH,
				WITH);

		bitLeftRed = getBitmapFromRes(R.drawable.notice_left_red, WITH, WITH);
		bitFirstLeftRed = getBitmapFromRes(R.drawable.notice_left_red,
				ballsStartLefPostion, WITH);
		bitLeftWhite = getBitmapFromRes(R.drawable.notice_left_wite, WITH, WITH);
		bitRedBall = getBitmapFromRes(R.drawable.notice_ball_red, WITH, WITH);
		bitBlueBall = getBitmapFromRes(R.drawable.notice_ball_blue, WITH, WITH);
		bitBlackBall = getBitmapFromRes(R.drawable.notice_ball_black, WITH,
				WITH);
		downSelectButton = getBitmapFromRes(R.drawable.down_select_button,
				(int) (SECOND_WITH / 1.7), WITH);
		bitNmk3Ico = getBitmapFromRes(R.drawable.nmk3_jiaoico,
				(int) (WITH / 1.8), (int) (WITH / 1.8));
		if(iGameType.equals("happy-poker")){
			bitRedBall_ks = getBitmapFromRes(R.drawable.notice_ball_red_hp,
					WITH );
			bitBlueBall_ks = getBitmapFromRes(R.drawable.notice_ball_green_hp,
					WITH );
			bitGreenBall_ks = getBitmapFromRes(R.drawable.notice_ball_blue_hp,
					WITH );
		}else{
			// 初始化，快三（ks）小球背景
			bitRedBall_ks = getBitmapFromRes(R.drawable.notice_ball_red_ks,
					WITH - 2);
			bitBlueBall_ks = getBitmapFromRes(R.drawable.notice_ball_blue_ks,
					WITH - 2);
			bitGreenBall_ks = getBitmapFromRes(R.drawable.notice_ball_green_ks,
					WITH - 2);
		}
	}

	/**
	 * 解析开奖信息
	 * 
	 * @param _list
	 *            开奖走势Json集合
	 * @param isRed
	 *            是否是控球走势标识
	 * @return 开奖信息集合
	 */
	public List<RowInfo> initRowInfo(List<JSONObject> _list, boolean isRed) {
		List<RowInfo> list = new ArrayList<RowInfo>();
		if (_list != null) {
			for (int i = 0; i < _list.size(); i++) {
				RowInfo info = new RowInfo();
				try {
					info.setIssue(_list.get(i).getString("batchCode"));
					info.setBallNum(parseStr(_list.get(i).getString("winCode"),
							isRed));
					if (iGameType.equals("fc3d")) {
						info.setTrycode(parseStrtrycode(_list.get(i).getString(
								"tryCode")));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				list.add(info);
			}
		} else {
			for (int i = 0; i < line; i++) {
				RowInfo info = new RowInfo();
				info.setIssue("");
				info.setBallNum(new int[] {});
				list.add(info);
			}
			isSelectedBar = true;
		}
		return list;
	}

	/*
	 * 设置画笔
	 * 
	 * @return void
	 */
	private void setPaint() {
		if (p == null) {
			p = new Paint();
			float textSize;
			if (release > 1) {
				textSize = 22;
			} else if (release > 0.8) {
				textSize = 16
						* release
						* (Float.valueOf(Constants.SCREEN_WIDTH) / Float
								.valueOf(480));
			} else if (release > 0.6) {
				textSize = 20
						* release
						* (Float.valueOf(Constants.SCREEN_WIDTH) / Float
								.valueOf(480));
			} else {
				textSize = 27
						* release
						* (Float.valueOf(Constants.SCREEN_WIDTH) / Float
								.valueOf(480));
			}

			p.setTextSize(textSize);
		}

	}

	public int[] parseStr(String iNumbers, boolean isRed) {
		if (iGameType.equalsIgnoreCase("gd11-5")) {
			int[] allNums = new int[5];
			for (int i = 0; i < 5; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i * 2,
						i * 2 + 2));
			}
			return allNums;
		}
		if (iGameType.equalsIgnoreCase("gd-10")) {
			int[] allNums = new int[8];
			int[] redNums = new int[2];
			int index = 0;
			for (int i = 0; i < 8; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i * 2,
						i * 2 + 2));
				if (allNums[i] == 19 || allNums[i] == 20) {
					redNums[index] = allNums[i];
					index++;
				}
			}
			if (isRed) {
				return redNums;
			} else {
				return allNums;
			}
		}
		if (iGameType.equalsIgnoreCase("ssq")) {
			// zlm 7.28 代码修改：添加号码排序
			isTen = true;
			int i2;
			String iShowNumber;
			int[] ssqInt01 = new int[6];
			int[] ssqInt02 = new int[1];
			for (i2 = 0; i2 < 6; i2++) {
				iShowNumber = iNumbers.substring(i2 * 2, i2 * 2 + 2);
				ssqInt01[i2] = Integer.valueOf(iShowNumber);
			}

			ssqInt01 = PublicMethod.sort(ssqInt01);
			if (isRed) {
				return ssqInt01;
			} else {
				iShowNumber = iNumbers.substring(12, 14);
				ssqInt02[0] = Integer.valueOf(iShowNumber);
				return ssqInt02;
			}

		} else if (iGameType.equalsIgnoreCase("fc3d")) {
			isTen = false;
			// zlm 7.30 代码修改：修改福彩3D号码
			int[] allNums = new int[3];
			for (int i = 0; i < 3; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i * 2,
						i * 2 + 2));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("qlc")) {
			// zlm 7.28 代码修改：添加号码排序
			int i1, i2, i3;
			String iShowNumber;

			int[] ssqInt01 = new int[7];
			int[] ssqInt02 = new int[1];

			for (i2 = 0; i2 < 7; i2++) {
				iShowNumber = iNumbers.substring(i2 * 2, i2 * 2 + 2);
				ssqInt01[i2] = Integer.valueOf(iShowNumber);
			}

			ssqInt01 = PublicMethod.sort(ssqInt01);
			// zlm 8.3 代码修改 ：添加七乐彩蓝球
			if (isRed) {
				return ssqInt01;
			} else {
				iShowNumber = iNumbers.substring(14, 16);
				ssqInt02[0] = Integer.valueOf(iShowNumber);
				return ssqInt02;
			}

		} else if (iGameType.equalsIgnoreCase("pl3")) {
			isTen = false;
			int[] allNums = new int[3];
			for (int i = 0; i < 3; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i, i + 1));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("pl5")) {
			isTen = false;
			int[] allNums = new int[5];
			for (int i = 0; i < 5; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i, i + 1));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("qxc")) {
			isTen = false;
			int[] allNums = new int[7];
			for (int i = 0; i < 7; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i, i + 1));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("cjdlt")) {

			int i1, i2;
			String iShowNumber;
			int[] cjdltInt01 = new int[5];
			int[] cjdltInt02 = new int[2];

			for (i2 = 0; i2 < 5; i2++) {
				iShowNumber = iNumbers.substring(i2 * 3, i2 * 3 + 2);
				cjdltInt01[i2] = Integer.valueOf(iShowNumber);
			}

			cjdltInt01 = PublicMethod.sort(cjdltInt01);
			for (i2 = 0; i2 < 2; i2++) {
				iShowNumber = iNumbers.substring(i2 * 3 + 15, i2 * 3 + 17);
				cjdltInt02[i2] = Integer.valueOf(iShowNumber);
			}
			cjdltInt02 = PublicMethod.sort(cjdltInt02);
			if (isRed) {
				return cjdltInt01;
			} else {
				return cjdltInt02;
			}

		} else if (iGameType.equalsIgnoreCase("ssc")) {
			isTen = false;
			int[] allNums = new int[5];
			for (int i = 0; i < 5; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i, i + 1));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("11-5")
				|| iGameType.equalsIgnoreCase("11-ydj")
				|| iGameType.equals("cq-11-5")) {

			int[] allNums = new int[5];
			for (int i = 0; i < 5; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i * 2,
						i * 2 + 2));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("22-5")) {

			int[] allNums = new int[5];
			for (int i = 0; i < 5; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i * 2,
						i * 2 + 2));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("sfc")) {

			int[] allNums = new int[14];
			for (int i = 0; i < 14; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i, i + 1));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("rxj")) {
			int[] allNums = new int[14];
			for (int i = 0; i < 14; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i, i + 1));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("lcb")) {
			int[] allNums = new int[12];
			for (int i = 0; i < 12; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i, i + 1));
			}
			return allNums;
		} else if (iGameType.equalsIgnoreCase("jqc")) {
			int[] allNums = new int[8];
			for (int i = 0; i < 8; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i, i + 1));
			}
			return allNums;
		}
		/** modify by pengcx 20130808 start */
		else if (iGameType.equalsIgnoreCase("nmk3")) {
			isTen = false;
			int[] allNums = new int[3];
			for (int i = 0; i < 3; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i * 2 + 1,
						i * 2 + 2));
			}
			return allNums;
		}
		/** modify by pengcx 20130808 end */
		else if (iGameType.equalsIgnoreCase("jlk3")) {
			isTen = false;
			int[] allNums = new int[3];
			for (int i = 0; i < 3; i++) {
				allNums[i] = Integer.valueOf(iNumbers.substring(i * 2 + 1,
						i * 2 + 2));
			}
			return allNums;
		}
		else if (iGameType.equalsIgnoreCase("happy-poker")) {
			isTen = false;
			int[] allNums = new int[3];
			for (int i = 0; i < 3; i++) {
				if(i==0){
					hPBallNums[i]=Integer.valueOf(iNumbers.substring(0,3));
					allNums[i] = Integer.valueOf(iNumbers.substring(0,3).substring(1));
				}else{
					hPBallNums[i]=Integer.valueOf(iNumbers.substring(i * 4 ,
							i * 4 + 3));
					allNums[i] = Integer.valueOf(iNumbers.substring(i * 4 ,
							i * 4 + 3).substring(1));
				}
			}
			return allNums;
		}
		return null;
	}
	
	public int[] parseStrtrycode(String iNumbers) {
		isTen = false;
		// zlm 7.30 代码修改：修改福彩3D号码
		if (iNumbers.equals("")) {
			return null;

		} else {
			int[] allNums = new int[3];
			for (int i = 0; i < 3; i++) {
				if (iNumbers.length() > 6) {
					allNums[i] = Integer.valueOf(iNumbers.substring(i * 3,
							i * 3 + 2));
				} else {
					allNums[i] = Integer.valueOf(iNumbers.substring(i * 2,
							i * 2 + 2));
				}
			}
			return allNums;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(with, height);
	}

	/**
	 * 画布
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.drawColor(Color.TRANSPARENT);
		p.setColor(Color.parseColor("#444444"));
		p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
		p.setAntiAlias(true);
		/** modify by pengcx 20130808 start */
		if (!isSelectedBar
				|| iGameType.equals("fc3d")
				|| iGameType.equals("pl3")
				|| ((iGameType.equals("gd11-5") || iGameType.equals("11-5") || iGameType
						.equals("11-ydj")) && isBeforeThree)) {
			// 绘制标题栏
			onDrawTop(canvas);
		}
		/** modify by pengcx 20130808 end */

		if (list != null && list.get(0).getBallNum() != null) {
			// 期号和开奖号码
			if (iGameType.equals("gd-10")) {
				if (isRed) {
					p.setColor(Color.GRAY);
					canvas.drawLine(FIRST_WITH, 0, FIRST_WITH, getHeight(), p);
				} else {
					onDrawLeft(canvas);
				}
			} else {
				if (isRed) {
					onDrawLeft(canvas);
				} else {
					p.setColor(Color.GRAY);
					canvas.drawLine(FIRST_WITH, 0, FIRST_WITH, getHeight(), p);
				}
			}
			if (iGameType.equals("fc3d")
					|| iGameType.equals("pl3")
					|| iGameType.equals("pl5")
					|| iGameType.equals("qxc")
					|| iGameType.equals("ssc")
					|| ((iGameType.equals("gd11-5") || iGameType.equals("11-5") || iGameType
							.equals("11-ydj")) && isBeforeThree)) {
				int ballNum = 3;
				if (iGameType.equals("pl5") || iGameType.equals("ssc")) {
					ballNum = 5;
				} else if (iGameType.equals("qxc")) {
					ballNum = 7;
				}
				onDrawCenterPL3(canvas, ballNum);
			} else {
				onDrawCenter(canvas);
			}
			// 画和值
			onDrawHeZhiAndKuaDu(canvas);
		}

		if (iGameType.equals("fc3d")
				|| iGameType.equals("pl3")
				|| ((iGameType.equals("gd11-5") || iGameType.equals("11-5") || iGameType
						.equals("11-ydj")) && isBeforeThree)) {
			if (hundredPart != null && decadePart != null && unitPart != null) {
				rankList(ballsChcekOne);
				String hundredPartStr = "";
				String decadePartStr = "";
				String unitPartStr = "";

				for (int i = 0; i < ballsChcekOne.size(); i++) {
					if (((iGameType.equals("gd11-5")
							|| iGameType.equals("11-5") || iGameType
								.equals("11-ydj")) && isBeforeThree)) {

						if (ballsChcekOne.get(i).getSelectRow() == 0) {
							if (ballsChcekOne.get(i).getPart() == 0) {
								hundredPartStr += PublicMethod
										.isTen(Integer.valueOf(ballsChcekOne
												.get(i).getNum()))
										+ ",";
							} else if (ballsChcekOne.get(i).getPart() == 1) {
								decadePartStr += PublicMethod
										.isTen(Integer.valueOf(ballsChcekOne
												.get(i).getNum()))
										+ ",";
							} else if (ballsChcekOne.get(i).getPart() == 2) {
								unitPartStr += PublicMethod
										.isTen(Integer.valueOf(ballsChcekOne
												.get(i).getNum()))
										+ ",";
							}
						}
					} else {
						if (ballsChcekOne.get(i).getSelectRow() == 0) {
							if (ballsChcekOne.get(i).getPart() == 0) {
								hundredPartStr += Integer.valueOf(ballsChcekOne
										.get(i).getNum()) + ",";
							} else if (ballsChcekOne.get(i).getPart() == 1) {
								decadePartStr += Integer.valueOf(ballsChcekOne
										.get(i).getNum()) + ",";
							} else if (ballsChcekOne.get(i).getPart() == 2) {
								unitPartStr += Integer.valueOf(ballsChcekOne
										.get(i).getNum()) + ",";
							}
						}
					}
					onDrawBallOnclik(canvas, ballsChcekOne.get(i).getLeft(),
							ballsChcekOne.get(i).getTop(), ballsChcekOne.get(i)
									.getNum());
				}
				if (!hundredPartStr.equals("")) {
					hundredPartStr = hundredPartStr.substring(0,
							hundredPartStr.length() - 1);
					hundredPart.setTextColor(Color.RED);
					hundredPart.setText(hundredPartStr + "|");
				} else {
					hundredPart.setText("");
				}
				if (!decadePartStr.equals("")) {
					decadePartStr = decadePartStr.substring(0,
							decadePartStr.length() - 1);
					decadePart.setTextColor(Color.RED);
					decadePart.setText(decadePartStr + "|");
				} else {
					decadePart.setText("");
				}
				if (!unitPartStr.equals("")) {
					unitPartStr = unitPartStr.substring(0,
							unitPartStr.length() - 1);
					unitPart.setTextColor(Color.RED);
					unitPart.setText(unitPartStr);
				} else {
					unitPart.setText("");
				}

				if (hundredPartStr.equals("") && decadePartStr.equals("")
						&& unitPartStr.equals("")) {
					hundredPart.setText("请点击选号一小球选取号码");
				}
			}

			if (hundredTwoPart != null && decadeTwoPart != null
					&& unitTwoPart != null) {
				rankList(ballsChcekTwo);
				String hundredPartStr = "";
				String decadePartStr = "";
				String unitPartStr = "";

				for (int i = 0; i < ballsChcekTwo.size(); i++) {

					if (((iGameType.equals("gd11-5")
							|| iGameType.equals("11-5") || iGameType
								.equals("11-ydj")) && isBeforeThree)) {

						if (ballsChcekTwo.get(i).getSelectRow() == 1) {
							if (ballsChcekTwo.get(i).getPart() == 0) {
								hundredPartStr += PublicMethod
										.isTen(Integer.valueOf(ballsChcekTwo
												.get(i).getNum()))
										+ ",";
							} else if (ballsChcekTwo.get(i).getPart() == 1) {
								decadePartStr += PublicMethod
										.isTen(Integer.valueOf(ballsChcekTwo
												.get(i).getNum()))
										+ ",";
							} else if (ballsChcekTwo.get(i).getPart() == 2) {
								unitPartStr += PublicMethod
										.isTen(Integer.valueOf(ballsChcekTwo
												.get(i).getNum()))
										+ ",";
							}
						}
					} else {
						if (ballsChcekTwo.get(i).getSelectRow() == 1) {
							if (ballsChcekTwo.get(i).getPart() == 0) {
								hundredPartStr += Integer.valueOf(ballsChcekTwo
										.get(i).getNum()) + ",";
							} else if (ballsChcekTwo.get(i).getPart() == 1) {
								decadePartStr += Integer.valueOf(ballsChcekTwo
										.get(i).getNum()) + ",";
							} else if (ballsChcekTwo.get(i).getPart() == 2) {
								unitPartStr += Integer.valueOf(ballsChcekTwo
										.get(i).getNum()) + ",";
							}
						}
					}
					onDrawBallOnclik(canvas, ballsChcekTwo.get(i).getLeft(),
							ballsChcekTwo.get(i).getTop(), ballsChcekTwo.get(i)
									.getNum());
				}
				if (!hundredPartStr.equals("")) {
					hundredPartStr = hundredPartStr.substring(0,
							hundredPartStr.length() - 1);
					hundredTwoPart.setTextColor(Color.RED);
					hundredTwoPart.setText(hundredPartStr + "|");
				} else {
					hundredTwoPart.setText("");
				}
				if (!decadePartStr.equals("")) {
					decadePartStr = decadePartStr.substring(0,
							decadePartStr.length() - 1);
					decadeTwoPart.setTextColor(Color.RED);
					decadeTwoPart.setText(decadePartStr + "|");
				} else {
					decadeTwoPart.setText("");
				}
				if (!unitPartStr.equals("")) {
					unitPartStr = unitPartStr.substring(0,
							unitPartStr.length() - 1);
					unitTwoPart.setTextColor(Color.RED);
					unitTwoPart.setText(unitPartStr);
				} else {
					unitTwoPart.setText("");
				}

				if (hundredPartStr.equals("") && decadePartStr.equals("")
						&& unitPartStr.equals("")) {
					hundredTwoPart.setText("请点击选号二小球选取号码");
				}
			}
		} else {
			/** modify by pengcx 20130809 start */
			if (textCodeOne != null) {
				String codeStr = "";
				rankList(ballsChcekOne);
				for (int i = 0; i < ballsChcekOne.size(); i++) {
					onDrawBallOnclik(canvas, ballsChcekOne.get(i).getLeft(),
							ballsChcekOne.get(i).getTop(), ballsChcekOne.get(i)
									.getNum());
					if (ballsChcekOne.get(i).getSelectRow() == 0) {
						if (i != 0 && i != ballsChcekOne.size()) {
							codeStr += ",";
						}
						codeStr += ballsChcekOne.get(i).getNum();

					}
				}
				if (isRed) {
					if (codeStr.equals("")) {
						textCodeOne.setText("请点击选号一小球选取号码");
					} else {
						textCodeOne.setText(codeStr);
					}
				} else if (!iGameType.equalsIgnoreCase("qlc")) {
					PublicMethod.setTextColor(textCodeOne, "|" + codeStr, 0, 1,
							Color.BLACK);
				}
			}

			if (textCodeTow != null) {
				String codeStr = "";
				rankList(ballsChcekTwo);
				for (int i = 0; i < ballsChcekTwo.size(); i++) {
					onDrawBallOnclik(canvas, ballsChcekTwo.get(i).getLeft(),
							ballsChcekTwo.get(i).getTop(), ballsChcekTwo.get(i)
									.getNum());

					if (ballsChcekTwo.get(i).getSelectRow() == 1) {
						if (i != 0 && i != ballsChcekTwo.size()) {
							codeStr += ",";
						}
						codeStr += ballsChcekTwo.get(i).getNum();
					}
				}
				if (isRed) {
					if (codeStr.equals("")) {
						textCodeTow.setText("请点击选号二小球选取号码");
					} else {
						textCodeTow.setText(codeStr);
					}
				} else if (!iGameType.equalsIgnoreCase("qlc")) {
					PublicMethod.setTextColor(textCodeTow, "|" + codeStr, 0, 1,
							Color.BLACK);
				}
			}
			/** modify by pengcx 20130809 end */
		}
		isFirstDraw = false;

		// nmk3 by afs 增加快三走势图提示 ，走势图下边
		if (iGameType.equals("nmk3") || iGameType.equals("jlk3")|| iGameType.equals("happy-poker")) {
			// height +=100;
			p.setColor(Color.BLACK);

			int bmpPromptHeight = height - WITH;
	
			float startX = 65 * release;

			//获取字体宽度
			float tWidth = p.measureText("六字体的宽度") * release;// 字体的宽度
			float bitBallWidth = bitRedBall_ks.getWidth();
			float tHeight = bmpPromptHeight+ (bitRedBall_ks.getHeight()) - (bitRedBall_ks.getHeight())/3;
		

			canvas.drawBitmap(bitRedBall_ks, startX, bmpPromptHeight, null);
			startX += bitBallWidth;

			canvas.drawText("代表单号", startX, tHeight, p);
			startX += tWidth;

			canvas.drawBitmap(bitBlueBall_ks, startX, bmpPromptHeight, null);
			startX += bitBallWidth;

			canvas.drawText("代表二同号", startX, tHeight, p);
			startX += tWidth;

			canvas.drawBitmap(bitGreenBall_ks, startX, bmpPromptHeight, null);
			startX += bitBallWidth;

			canvas.drawText("代表三同号", startX, tHeight, p);
		}

		canvas.restore();
	}

	/**
	 * 冒泡排序
	 * 
	 * @param myArray
	 * @return
	 */
	public List<BallPosition> rankList(List<BallPosition> rankInt) {
		if (iGameType.equals("fc3d") || iGameType.equals("pl3")) {
			int onePart = 0;
			int twoPart = 0;
			int threePart = 0;
			List<BallPosition> tempList = new ArrayList<NoticeBallView.BallPosition>();
			for (int i = 0; i < rankInt.size(); i++) {
				if (rankInt.get(i).getPart() == 0) {
					tempList.add(onePart, rankInt.get(i));
					onePart++;
				} else if (rankInt.get(i).getPart() == 1) {
					tempList.add(onePart + twoPart, rankInt.get(i));
					twoPart++;
				} else if (rankInt.get(i).getPart() == 2) {
					tempList.add(onePart + twoPart + threePart, rankInt.get(i));
					threePart++;
				}
			}

			BallPosition[] balls = (BallPosition[]) tempList
					.toArray(new BallPosition[rankInt.size()]);
			if (onePart > 0) {
				Arrays.sort(balls, 0, onePart, new BallComparator());
			}
			if (twoPart > 0) {
				Arrays.sort(balls, onePart, onePart + twoPart,
						new BallComparator());
			}
			if (threePart > 0) {
				Arrays.sort(balls, onePart + twoPart, balls.length,
						new BallComparator());
			}

			tempList = Arrays.asList(balls);
			for (int i = 0; i < tempList.size(); i++) {
				rankInt.set(i, tempList.get(i));
			}

		} else {
			// 取长度最长的词组 -- 冒泡法
			for (int j = 1; j < rankInt.size(); j++) {
				for (int i = 0; i < rankInt.size() - 1; i++) {
					// 如果 myArray[i] > myArray[i+1] ，则 myArray[i] 上浮一位
					String numStr = rankInt.get(i).getNum();
					String nextStr = rankInt.get(i + 1).getNum();
					if (Integer.parseInt(numStr) > Integer.parseInt(nextStr)) {
						BallPosition temp = rankInt.get(i);
						rankInt.set(i, rankInt.get(i + 1));
						rankInt.set(i + 1, temp);
					}
				}
			}
		}

		return rankInt;
	}

	class BallComparator implements Comparator<BallPosition> {

		@Override
		public int compare(BallPosition lhs, BallPosition rhs) {
			int one = Integer.valueOf(lhs.getNum());
			int two = Integer.valueOf(rhs.getNum());

			int com = one - two;
			if (com > 0) {
				return 1;
			} else if (com < 0) {
				return -1;
			} else {
				return 0;
			}

		}

	}

	/**
	 * 绘制顶部标题
	 */
	public void onDrawTop(Canvas canvas) {
		// 绘制红色的背景图片 图片是一竖条根据宽度来拉伸
		if (isRed) {
			canvas.drawBitmap(bitNoticeTopRed, 0, 0, null);
		} else {
			canvas.drawBitmap(bitNoticeTopBlue, 0, 0, null);
		}
		p.setColor(Color.WHITE);
		// 设置高 WITH是25 release 是1.4？
		int height = (int) (WITH - (6 * release));
		// 设置宽 WITH是25 release 是1.4？
		int with = (int) (WITH / 2 - (10 * release));
		if (isRed && iGameType.equals("fc3d")) {
			if (!isSelectedBar) {
				canvas.drawText("期号", with, height, p);
				canvas.drawText("开奖号码", FIRST_WITH - SECOND_WITH, height, p);
				canvas.drawText("试机号", FIRST_WITH - SECOND_WITH / 2 + 20,
						height, p);
			}
		} else {
			if (isRed || (iGameType.equals("gd-10") && !isRed)) {
				if (!isSelectedBar) {
					canvas.drawText("期号", with, height, p);
					canvas.drawText("开奖号码", FIRST_WITH - SECOND_WITH, height, p);
				}
			}
		}

		if ((isAddHeZhiAndKuoDu() && !isSelectedBar)) {
			int x1 = 0, x2 = 0;
			if (Constants.SCREEN_WIDTH == 240 || Constants.SCREEN_WIDTH == 320) {
				x1 = FIRST_WITH + 2;
				x2 = FIRST_WITH + SECOND_WITH / 4 + 2;
			} else if (Constants.SCREEN_WIDTH == 540) {
				// toLeft = PublicMethod.getPxInt(28, context);
				x1 = FIRST_WITH + 10;
				x2 = FIRST_WITH + SECOND_WITH / 4 + 10;
			} else {
				// with = WITH / 2 - 10;
				x1 = FIRST_WITH + 10;
				x2 = FIRST_WITH + SECOND_WITH / 4 + 10;
			}
			canvas.drawText("和值", x1, height, p);
			canvas.drawText("跨度", x2, height, p);
		}

		if (iGameType.equals("fc3d")
				|| iGameType.equals("pl3")
				|| iGameType.equals("pl5")
				|| iGameType.equals("qxc")
				|| iGameType.equals("ssc")
				|| ((iGameType.equals("gd11-5") || iGameType.equals("11-5") || iGameType
						.equals("11-ydj")) && isBeforeThree)) {
			int ballNum = 3;
			// 和值，跨度

			if (iGameType.equals("pl5") || iGameType.equals("ssc")) {
				ballNum = 5;
			} else if (iGameType.equals("qxc")) {
				ballNum = 7;
			}

			for (int j = 0; j < ballNum; j++) {
				for (int i = 0; i < row; i++) {
					if (j % 2 == 0) {

						canvas.drawBitmap(bitNoticeRed, ballsStartLefPostion
								+ i * WITH + (row * WITH * j), 0, null);
					} else if (j % 2 == 1) {
						canvas.drawBitmap(bitNoticeBlue, ballsStartLefPostion
								+ i * WITH + (row * WITH * j), 0, null);
					}
					if (!isSelectedBar) {
						int num = i + startNum;
						if (((iGameType.equals("gd11-5")
								|| iGameType.equals("11-5") || iGameType
									.equals("11-ydj")) && isBeforeThree)) {
							canvas.drawText(PublicMethod.isTen(num),
									(ballsStartLefPostion + i * WITH + with)
											+ (row * WITH * j), height, p);
						} else {
							canvas.drawText("" + num, (ballsStartLefPostion + i
									* WITH + with)
									+ (row * WITH * j) + 4, height, p);
						}

					}
				}

				if ((iGameType.equals("fc3d") || iGameType.equals("pl3"))
						&& isSelectedBar) {
					if (j == 0) {
						canvas.drawText("百位", (ballsStartLefPostion
								+ ((row - 2) / 2.0f) * WITH + with)
								+ (row * WITH * j) + 10, height, p);
					} else if (j == 1) {
						canvas.drawText("十位", (ballsStartLefPostion
								+ ((row - 2) / 2.0f) * WITH + with)
								+ (row * WITH * j) + 10, height, p);
					} else if (j == 2) {
						canvas.drawText("个位", (ballsStartLefPostion
								+ ((row - 2) / 2.0f) * WITH + with)
								+ (row * WITH * j) + 10, height, p);
					}

				} else if ((iGameType.equals("gd11-5")
						|| iGameType.equals("11-5") || iGameType
							.equals("11-ydj")) && isSelectedBar) {
					if (j == 0) {
						canvas.drawText("一位走势", (ballsStartLefPostion
								+ ((row - 2) / 2.0f) * WITH + with)
								+ (row * WITH * j) + 10, height, p);
					} else if (j == 1) {
						canvas.drawText("二位走势", (ballsStartLefPostion
								+ ((row - 2) / 2.0f) * WITH + with)
								+ (row * WITH * j) + 10, height, p);
					} else if (j == 2) {
						canvas.drawText("三位走势", (ballsStartLefPostion
								+ ((row - 2) / 2.0f) * WITH + with)
								+ (row * WITH * j) + 10, height, p);
					}
				}
			}
		} else {
			for (int i = 0; i < row; i++) {
				if (isRed) {
					canvas.drawBitmap(bitNoticeRed, ballsStartLefPostion + i
							* WITH, 0, null);
				} else {
					canvas.drawBitmap(bitNoticeBlue, ballsStartLefPostion + i
							* WITH, 0, null);
				}
				int num = i + startNum;
				/** modify by pengcx 20130808 start */
				if (isTen) {
					canvas.drawText(PublicMethod.isTen(num),
							ballsStartLefPostion + i * WITH + with, height, p);
				} else {
					if(iGameType.equals("happy-poker")){
						if(num==1){
							canvas.drawText("A" , ballsStartLefPostion + i * WITH
									+ with + 4, height, p);
						}else if(num==11){
							canvas.drawText("J" , ballsStartLefPostion + i * WITH
									+ with + 9, height, p);
						}else if(num==12){
							canvas.drawText("Q" , ballsStartLefPostion + i * WITH
									+ with + 4, height, p);
						}else if(num==13){
							canvas.drawText("K" , ballsStartLefPostion + i * WITH
									+ with + 4, height, p);
						}else {
							canvas.drawText("" + num, ballsStartLefPostion + i * WITH
									+ with + 4, height, p);
						}
					}else{
						canvas.drawText("" + num, ballsStartLefPostion + i * WITH
								+ with + 4, height, p);
					}
				}
				/** modify by pengcx 20130808 end */
			}
		}
	}

	/**
	 * 绘制 跨度 和值
	 * 
	 * @param canvas
	 */
	public void onDrawHeZhiAndKuaDu(Canvas canvas) {
		// 没有和值和跨度，就返回
		if (!isAddHeZhiAndKuoDu()) {
			return;
		}
		p.setColor(Color.BLACK);
		int height = (int) (WITH - (6 * release));
		int x1 = 0;
		int x2 = 0;

		if (Constants.SCREEN_WIDTH == 240 || Constants.SCREEN_WIDTH == 320) {
			x1 = FIRST_WITH + 5;
			x2 = FIRST_WITH + SECOND_WITH / 4 + 5;
		} else if (Constants.SCREEN_WIDTH == 540) {
			// toLeft = PublicMethod.getPxInt(28, context);
			x1 = FIRST_WITH + 15;
			x2 = FIRST_WITH + SECOND_WITH / 4 + 20;
		} else {
			// with = WITH / 2 - 10;
			x1 = FIRST_WITH + 15;
			x2 = FIRST_WITH + SECOND_WITH / 4 + 20;
		}

		for (int i = 0; i < line; i++) {

			int[] ballNumbers = list.get(i).getBallNum();
			if (ballNumbers.length != 0) {

				Arrays.sort(ballNumbers);

				String heZhi = sumArrays(ballNumbers) + "";
				String kuaDu = (ballNumbers[ballNumbers.length - 1] - ballNumbers[0])
						+ "";

				int isSingleNumber1 = heZhi.length() > 1 ? 0 : 5;
				int isSingleNumber2 = kuaDu.length() > 1 ? 0 : 5;

				canvas.drawText(heZhi, isSingleNumber1 + x1, WITH + i * WITH
						+ height, p);
				canvas.drawText(kuaDu, isSingleNumber2 + x2, WITH + i * WITH
						+ height, p);
			}
		}
	}

	private int sumArrays(int[] array) {
		int sum = 0;
		int i = 0;
		while (i < array.length) {
			sum += array[i];
			i++;
		}
		return sum;
	}

	/**
	 * 绘制左边
	 */
	public void onDrawLeft(Canvas canvas) {
		int toLeft = PublicMethod.getPxInt(20, context);
		int height = (int) (WITH - (6 * release));
		int with = 0;
		for (int i = 0; i < line; i++) {
			if (!isSelectedBar) {
				p.setColor(Color.GRAY);
				if (Constants.SCREEN_WIDTH == 240
						|| Constants.SCREEN_WIDTH == 320) {
					with = WITH / 2 - 5;
				} else if (Constants.SCREEN_WIDTH == 540) {
					toLeft = PublicMethod.getPxInt(28, context);
				} else {
					with = WITH / 2 - 10;
				}

				// 绘制背景图片
				if (i % 2 == 0) {
					canvas.drawBitmap(bitLeftWhite, 0, WITH + i * WITH, null);
				} else {
					canvas.drawBitmap(bitFirstLeftRed, 0, WITH + i * WITH, null);
				}

				// 绘制期号文字
				canvas.drawText(list.get(i).getIssue(), with, WITH + i * WITH
						+ height, p);

				// 绘制开奖号码
				if (isRed) {
					p.setColor(Color.RED);
				} else {
					p.setColor(Color.BLUE);
				}
				String isNumber = "";
				if (list.get(i).getBallNum() != null) {
					for (int j = 0; j < list.get(i).getBallNum().length; j++) {
						int sum = list.get(i).getBallNum().length + 1;
						int textInt = list.get(i).getBallNum()[j];
						if (iGameType.equals("gd-10")
								&& (textInt == 19 || textInt == 20)) {
							p.setColor(Color.RED);
						} else {
							p.setColor(Color.BLUE);
						}
						if (isTen) {
							isNumber = PublicMethod.isTen(list.get(i)
									.getBallNum()[j]);
						} else {
							isNumber = Integer.toString(list.get(i)
									.getBallNum()[j]);
						}
						if(iGameType.equals("happy-poker")){
							String temp=String.valueOf(hPBallNums[j]);
							canvas.drawBitmap(
									getBitmapFromRes(PublicMethod.setHappyPokerLotteryBg(temp), WITH+15,WITH-2),
									FIRST_WITH - SECOND_WITH - toLeft
											+ SECOND_WITH / sum * j + 4, WITH
											+ i * WITH + (int) (WITH - (23 * release)) , null);
						}else{
							if (j != list.get(i).getBallNum().length - 1) {
								isNumber += ",";
							}
							canvas.drawText(isNumber, FIRST_WITH - SECOND_WITH
									- toLeft + SECOND_WITH / sum * j + 4, WITH + i
									* WITH + height, p);
						}
						// 篮球
						if (j == list.get(i).getBallNum().length - 1
								&& iGameType.equals("cjdlt")) {
							p.setColor(0xFF073AAC);
							canvas.drawText(
									","
											+ PublicMethod.isTen(blueList
													.get(i).getBallNum()[1]),
									FIRST_WITH - SECOND_WITH - toLeft
											+ SECOND_WITH / sum * (j + 2) + 4,
									WITH + i * WITH + height, p);
							p.setColor(0xFFDE0201);
						}

						if (j == list.get(i).getBallNum().length - 1
								&& (iGameType.equals("ssq")
										|| iGameType.equals("qlc") || iGameType
											.equals("cjdlt"))) {
							p.setColor(0xFF073AAC);
							canvas.drawText(
									","
											+ PublicMethod.isTen(blueList
													.get(i).getBallNum()[0]),
									FIRST_WITH - SECOND_WITH - toLeft
											+ SECOND_WITH / sum * (j + 1) + 4,
									WITH + i * WITH + height, p);
							p.setColor(0xFFDE0201);
						}

					}
				}

				if (iGameType.equals("fc3d")) {
					if (list.get(i).getTrycode() != null) {
						for (int j = 0; j < list.get(i).getTrycode().length; j++) {
							int sum = (list.get(i).getTrycode().length + 1) * 2;
							p.setColor(Color.rgb(128, 64, 0));
							if (isTen) {
								isNumber = PublicMethod.isTen(list.get(i)
										.getTrycode()[j]);
							} else {
								isNumber = Integer.toString(list.get(i)
										.getTrycode()[j]);
							}
							if (j != list.get(i).getBallNum().length - 1) {
								isNumber += ",";
							}
							canvas.drawText(isNumber, FIRST_WITH - SECOND_WITH
									/ 2 + toLeft / 2 + (SECOND_WITH / sum) * j,
									WITH + i * WITH + height, p);
						}
					}
				}
			} else {
				if (iGameType.equals("fc3d")
						|| iGameType.equals("pl3")
						|| ((iGameType.equals("gd11-5")
								|| iGameType.equals("11-5") || iGameType
									.equals("11-ydj")) && isBeforeThree)) {
					// 绘制背景图片
					if (i % 2 == 0) {
						canvas.drawBitmap(bitLeftWhite, 0, WITH + i * WITH,
								null);
					} else {
						canvas.drawBitmap(bitFirstLeftRed, 0, WITH + i * WITH,
								null);
					}

					p.setColor(Color.GRAY);
					if (i == 0) {
						canvas.drawText("选号一", with, WITH + i * WITH + height,
								p);
					} else if (i == 1) {
						canvas.drawText("选号二", with, WITH + i * WITH + height,
								p);
					}

					if (((iGameType.equals("gd11-5")
							|| iGameType.equals("11-5") || iGameType
								.equals("11-ydj")) && isBeforeThree)) {
						String selectText = "";
						if (i == 1) {
							switch (((NoticeBallActivity) context).oneSelectButtonSpinner
									.getSelectedItemPosition()) {
							case 0:
								selectText = "前一直选";
								break;
							case 1:
								selectText = "前二直选";
								break;
							case 2:
								selectText = "前三直选";
								break;
							case 3:
								selectText = "前二组选";
								break;

							case 4:
								selectText = "前三组选";
								break;
							}
							canvas.drawBitmap(downSelectButton, FIRST_WITH
									- SECOND_WITH - 20, i * WITH, null);
							canvas.drawText(selectText, FIRST_WITH
									- SECOND_WITH - 15, i * WITH + height, p);
							if (isFirstDraw) {
								if (selectButtonList.size() < 2) {
									BallPosition ball = new BallPosition(
											FIRST_WITH - SECOND_WITH - 20, i
													* WITH, SECOND_WITH / 2,
											WITH, i + 1, "3");
									selectButtonList.add(ball);
								}

							}
						} else if (i == 2) {
							switch (((NoticeBallActivity) context).twoSelectButtonSpinner
									.getSelectedItemPosition()) {
							case 0:
								selectText = "前一直选";
								break;

							case 1:
								selectText = "前二直选";
								break;
							case 2:
								selectText = "前三直选";
								break;
							case 3:
								selectText = "前二组选";
								break;

							case 4:
								selectText = "前三组选";
								break;
							}
							canvas.drawBitmap(downSelectButton, FIRST_WITH
									- SECOND_WITH - 20, i * WITH, null);
							canvas.drawText(selectText, FIRST_WITH
									- SECOND_WITH - 15, i * WITH + height, p);
							if (isFirstDraw) {
								if (selectButtonList.size() < 2) {
									BallPosition ball = new BallPosition(
											FIRST_WITH - SECOND_WITH - 20, i
													* WITH, SECOND_WITH / 2,
											WITH, i + 1, "3");
									selectButtonList.add(ball);
								}
							}
						}

					}
				} else {
					// 绘制背景图片
					if (i % 2 == 0) {
						canvas.drawBitmap(bitLeftWhite, 0, i * WITH, null);

					} else {
						canvas.drawBitmap(bitFirstLeftRed, 0, i * WITH, null);

					}
					if (i == 0) {
						canvas.drawText("选号一", with, i * WITH + height, p);
					} else if (i == 1) {
						canvas.drawText("选号二", with, i * WITH + height, p);
					} else {
						canvas.drawText("平均遗漏值", with, i * WITH + height, p);
					}

					if (iGameType.equals("gd11-5") || iGameType.equals("11-5")) {
						canvas.drawBitmap(downSelectButton, FIRST_WITH
								- SECOND_WITH - 30, i * WITH, null);
						String selectText = "";
						if (i == 0) {
							switch (((NoticeBallActivity) context).oneSelectButtonSpinner
									.getSelectedItemPosition()) {
							case 0:
								selectText = "任选二";
								break;

							case 1:
								selectText = "任选三";
								break;
							case 2:
								selectText = "任选四";
								break;
							case 3:
								selectText = "任选五";
								break;

							case 4:
								selectText = "任选六";
								break;
							case 5:
								selectText = "任选七";
								break;
							case 6:
								selectText = "任选八";
								break;
							}
							canvas.drawText(selectText, FIRST_WITH
									- SECOND_WITH - 15, i * WITH + height, p);
							if (isFirstDraw) {
								if (selectButtonList.size() < 2) {
									BallPosition ball = new BallPosition(
											FIRST_WITH - SECOND_WITH - 20, i
													* WITH, SECOND_WITH / 2,
											WITH, i, "5");
									selectButtonList.add(ball);
								}

							}

						} else if (i == 1) {
							switch (((NoticeBallActivity) context).twoSelectButtonSpinner
									.getSelectedItemPosition()) {
							case 0:
								selectText = "任选二";
								break;

							case 1:
								selectText = "任选三";
								break;
							case 2:
								selectText = "任选四";
								break;
							case 3:
								selectText = "任选五";
								break;

							case 4:
								selectText = "任选六";
								break;
							case 5:
								selectText = "任选七";
								break;
							case 6:
								selectText = "任选八";
								break;
							}
							canvas.drawText(selectText, FIRST_WITH
									- SECOND_WITH - 15, i * WITH + height, p);
							if (isFirstDraw) {
								if (selectButtonList.size() < 2) {
									BallPosition ball = new BallPosition(
											FIRST_WITH - SECOND_WITH - 20, i
													* WITH, SECOND_WITH / 2,
											WITH, i, "5");
									selectButtonList.add(ball);
								}

							}
						}
					}
				}
			}
		}

		// 画竖线分隔符
		p.setColor(Color.GRAY);

		int centerLine = getHeight();

		// 快三 走势图多出来一行提示 把竖线拿掉
		if (iGameType.equals("nmk3") || iGameType.equals("jlk3")|| iGameType.equals("happy-poker")) {
			centerLine -= WITH;
		}

		canvas.drawLine(FIRST_WITH - SECOND_WITH - toLeft, 0, FIRST_WITH
				- SECOND_WITH - toLeft, centerLine, p);

		if (iGameType.equals("fc3d")) {
			int xStart = FIRST_WITH - SECOND_WITH / 2 - toLeft / 4;
			canvas.drawLine(xStart, 0, xStart, getHeight(), p);
		}

		if (isAddHeZhiAndKuoDu()) {
			int xStart2 = FIRST_WITH + SECOND_WITH / 4;
			// 和值和跨度中线
			canvas.drawLine(xStart2, 0, xStart2, getHeight(), p);
		}

		canvas.drawLine(FIRST_WITH, 0, FIRST_WITH, centerLine, p);
	}

	/**
	 * 绘制表格
	 */
	public void onDrawTable(Canvas canvas, int x, int y) {
		// 和值，跨度
		int ballsStartLefPostion = GetHeTotalWidthWithZhiAndKuoDu();
		// 3.6.2需求多画2行
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < row; j++) {
				if (!isSelectedBar) {
					if (iGameType.equals("ssq") && isRed && j == 11) {
						p.setColor(Color.GRAY);
						p.setStrokeWidth(1);
						canvas.drawLine(ballsStartLefPostion + j * WITH + x, 0,
								ballsStartLefPostion + j * WITH + x,
								getHeight(), p);
					}
					if (iGameType.equals("ssq") && isRed && j == 22) {
						p.setColor(Color.GRAY);
						p.setStrokeWidth(1);
						canvas.drawLine(ballsStartLefPostion + j * WITH + x, 0,
								ballsStartLefPostion + j * WITH + x,
								getHeight(), p);
					}
					if (i % 2 == 0) {
						if (j % 2 == 0) {
							canvas.drawBitmap(bitWhite, ballsStartLefPostion
									+ j * WITH + x, WITH + i * WITH + y, null);
						} else {
							canvas.drawBitmap(bitGrey, ballsStartLefPostion + j
									* WITH + x, WITH + i * WITH + y, null);
						}
					} else {
						if (j % 2 == 0) {
							canvas.drawBitmap(bitGrey, ballsStartLefPostion + j
									* WITH + x, WITH + i * WITH + y, null);
						} else {
							canvas.drawBitmap(bitWhite, ballsStartLefPostion
									+ j * WITH + x, WITH + i * WITH + y, null);
						}
					}
				} else {
					if (iGameType.equals("fc3d")
							|| iGameType.equals("pl3")
							|| ((iGameType.equals("gd11-5")
									|| iGameType.equals("11-5") || iGameType
										.equals("11-ydj")) && isBeforeThree)) {
						if (i % 2 == 0) {
							if (j % 2 == 0) {
								canvas.drawBitmap(bitWhite,
										ballsStartLefPostion + j * WITH + x,
										WITH + i * WITH + y, null);
							} else {
								canvas.drawBitmap(bitGrey, ballsStartLefPostion
										+ j * WITH + x, WITH + i * WITH + y,
										null);
							}
						} else {
							if (j % 2 == 0) {
								canvas.drawBitmap(bitGrey, ballsStartLefPostion
										+ j * WITH + x, WITH + i * WITH + y,
										null);
							} else {
								canvas.drawBitmap(bitWhite,
										ballsStartLefPostion + j * WITH + x,
										WITH + i * WITH + y, null);
							}
						}
					} else {
						if (i % 2 == 0) {
							if (j % 2 == 0) {
								canvas.drawBitmap(bitWhite,
										ballsStartLefPostion + j * WITH + x, i
												* WITH + y, null);
							} else {
								canvas.drawBitmap(bitGrey, ballsStartLefPostion
										+ j * WITH + x, i * WITH + y, null);
							}
						} else {
							if (j % 2 == 0) {
								canvas.drawBitmap(bitGrey, ballsStartLefPostion
										+ j * WITH + x, i * WITH + y, null);
							} else {
								canvas.drawBitmap(bitWhite,
										ballsStartLefPostion + j * WITH + x, i
												* WITH + y, null);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 绘制小球
	 * 
	 * @param canvas
	 */
	public void onDrawCenter(Canvas canvas) {
		onDrawTable(canvas, 0, 0);
		drawLine(canvas);
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < row; j++) {
				int balls[] = list.get(i).getBallNum();
				int num = j + startNum;
				int height = (int) (WITH - (8 * release));
				int with = (int) (WITH / 2 - (8 * release));
				int repeat = 0;
				/** modify by pengcx 20130808 start */
				if (!isSelectedBar) {
					for (int n = 0; n < balls.length; n++) {
						if (num == balls[n]) {
							repeat++;
							if (isRed) {
								if (iGameType.equals("nmk3")
										|| iGameType.equals("jlk3")
										||iGameType.equals("happy-poker")) {
									switch (repeat) {
									case 1:
										canvas.drawBitmap(
												bitRedBall_ks,
												ballsStartLefPostion + j * WITH,
												WITH + i * WITH, null);
										break;
									case 2:
										canvas.drawBitmap(
												bitBlueBall_ks,
												ballsStartLefPostion + j * WITH,
												WITH + i * WITH, null);
										break;
									case 3:
										canvas.drawBitmap(
												bitGreenBall_ks,
												ballsStartLefPostion + j * WITH,
												WITH + i * WITH, null);
										break;
									}
								} else {
									canvas.drawBitmap(bitRedBall,
											ballsStartLefPostion + j * WITH,
											WITH + i * WITH, null);
								}
							} else {
								canvas.drawBitmap(bitBlueBall,
										ballsStartLefPostion + j * WITH, WITH
												+ i * WITH, null);
							}

							p.setColor(Color.WHITE);
							/** modify by pengcx 20130808 start */
							if(iGameType.equals("happy-poker")){
								canvas.drawText("" + PublicMethod.isTen(balls[n]),
										ballsStartLefPostion + j * WITH + with, 
										WITH + i * WITH + height,p);
							}else if (isTen) {
								canvas.drawText(
										"" + PublicMethod.isTen(balls[n]),
										ballsStartLefPostion + j * WITH + with,
										WITH + i * WITH + height, p);
							} else {
								canvas.drawText("" + balls[n],
										ballsStartLefPostion + j * WITH + with
												+ 4, WITH + i * WITH + height,
										p);
							}
							/** modify by pengcx 20130808 end */

							// 判断内蒙快三中的重复选号，并显示重复值
							/*
							 * if (iGameType.equals("nmk3") && repeat > 1) {
							 * Paint paint = new Paint();
							 * paint.setColor(Color.BLACK);
							 * paint.setTextSize((float) (p.getTextSize() /
							 * 1.2));
							 * 
							 * canvas.drawBitmap(bitNmk3Ico, FIRST_WITH + j WITH
							 * + (float) (WITH / 1.5), WITH + i WITH - (float)
							 * (WITH / 3.8), null); canvas.drawText("" + repeat,
							 * FIRST_WITH + j WITH + with + (float) (WITH /
							 * 1.6), WITH + i * WITH + height - (float) (WITH /
							 * 2.1), paint); }
							 */

						}
					}
				} else {
					if (i == 0 || i == 1) {
						canvas.drawBitmap(bitBlackBall, ballsStartLefPostion
								+ j * WITH, i * WITH, null);
						if (isFirstDraw) {
							BallPosition ball = new BallPosition(
									ballsStartLefPostion + j * WITH, i * WITH,
									WITH, PublicMethod.isTen(j + 1), i);
							ballsList.add(ball);
						}
					}

					if (i == 2) {
						p.setColor(Color.BLACK);
					} else {
						p.setColor(Color.WHITE);
					}

					if (i == 2) {
						if (isRed && NoticeBallActivity.missRed != null
								&& NoticeBallActivity.missRed.size() > 0) {
							canvas.drawText(NoticeBallActivity.missRed.get(j),
									ballsStartLefPostion + j * WITH + with, i
											* WITH + height, p);
						} else if (!isRed
								&& NoticeBallActivity.missBlue != null
								&& NoticeBallActivity.missBlue.size() > 0) {
							canvas.drawText(NoticeBallActivity.missBlue.get(j),
									ballsStartLefPostion + j * WITH + with, i
											* WITH + height, p);
						}
					} else {
						if (isTen) {
							canvas.drawText("" + PublicMethod.isTen(j + 1),
									ballsStartLefPostion + j * WITH + with, i
											* WITH + height, p);
						} else {
							canvas.drawText("" + (j + 1), ballsStartLefPostion
									+ j * WITH + with + 4, i * WITH + height, p);
						}
					}

				}
				/** modify by pengcx 20130808 end */
			}
		}
	}

	public void onDrawBallOnclik(Canvas canvas, float f, float g, String num) {
		int height = (int) (WITH - (8 * release));
		int with = (int) (WITH / 2 - (8 * release));
		p.setColor(Color.WHITE);
		/** modify by pengcx 20130808 start */
		if (isRed) {
			canvas.drawBitmap(bitRedBall, (int) f, (int) g, null);
		} else {
			canvas.drawBitmap(bitBlueBall, (int) f, (int) g, null);
		}
		/** modify by pengcx 20130808 end */
		if (iGameType.equals("fc3d") || iGameType.equals("pl3")) {
			isTen = false;
		}
		if (!isTen) {
			canvas.drawText(Integer.valueOf(num) + "", f + with + 4,
					g + height, p);
		} else {
			canvas.drawText(PublicMethod.isTen(Integer.valueOf(num)), f + with,
					g + height, p);
		}

	}

	class BallPosition {
		float left = 0;
		float top = 0;
		float width = 0;

		/** add by pengcx 20130809 start */
		int selectRow;
		int part;
		float height = 0;

		public int getSelectRow() {
			return selectRow;
		}

		public void setSelectRow(int selectRow) {
			this.selectRow = selectRow;
		}

		public int getPart() {
			return part;
		}

		public void setPart(int part) {
			this.part = part;
		}

		public float getHeight() {
			return height;
		}

		public void setHeight(float height) {
			this.height = height;
		}

		/** add by pengcx 20130809 end */
		String num;

		/** modify by pengcx 20130809 start */
		public BallPosition(float left, float top, float width, String num,
				int selectRow) {
			this.left = left;
			this.top = top;
			this.width = width;
			this.num = num;
			this.selectRow = selectRow;
		}

		public BallPosition(float left, float top, float width, float height,
				int selectRow, String num) {
			super();
			this.left = left;
			this.top = top;
			this.width = width;
			this.selectRow = selectRow;
			this.height = height;
			this.num = num;
		}

		public BallPosition(int left, int top, int width, String num,
				int selectRow, int part) {
			this.left = left;
			this.top = top;
			this.width = width;
			this.num = num;
			this.selectRow = selectRow;
			this.part = part;
		}

		/** modify by pengcx 20130809 end */

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public float getLeft() {
			return left;
		}

		public void setLeft(float left) {
			this.left = left;
		}

		public float getTop() {
			return top;
		}

		public void setTop(float top) {
			this.top = top;
		}

		public float getWidth() {
			return width;
		}

		public void setWidth(float width) {
			this.width = width;
		}
	}

	/**
	 * 绘制连线
	 * 
	 * @param canvas
	 */
	public void drawLine(Canvas canvas) {
		p.setColor(Color.GRAY);
		p.setStrokeWidth(3);
		boolean isDraw = false;
		int blueX = 0;
		int blueY = 0;
		int blueStartX = 0;
		int blueStartY = 0;
		int blueNum = 0;
		onDrawTable(canvas, 0, 0);

		for (int i = 0; i < line; i++) {
			for (int j = 0; j < row; j++) {
				int balls[] = list.get(i).getBallNum();
				int num = j + startNum;
				for (int n = 0; n < balls.length; n++) {
					if (num == balls[n]) {
						if (!isRed && !iGameType.equals("gd-10")) {
							if (isDraw) {
								blueStartX = ballsStartLefPostion + j * WITH
										+ WITH / 2;
								blueStartY = WITH + i * WITH + WITH / 2;
								canvas.drawLine(blueStartX, blueStartY, blueX,
										blueY, p);
								blueX = blueStartX;
								blueY = blueStartY;
								blueNum = num;
							} else {
								isDraw = true;
								blueNum = num;
								blueX = ballsStartLefPostion + j * WITH + WITH
										/ 2;
								blueY = WITH + i * WITH + WITH / 2;
							}

						}
					}
				}
			}
		}

	}

	/**
	 * 绘制福彩3d连线
	 * 
	 * @param canvas
	 */
	public void drawLine(Canvas canvas, int x, int y, int m) {
		p.setColor(Color.GRAY);
		p.setStrokeWidth(3);
		boolean isDraw = false;
		int blueX = 0;
		int blueY = 0;
		int blueStartX = 0;
		int blueStartY = 0;
		int blueNum = 0;
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < row; j++) {
				int balls[] = list.get(i).getBallNum();
				int num = j + startNum;
				if (num == balls[m]) {
					if (isDraw) {
						blueStartX = ballsStartLefPostion + j * WITH + WITH / 2;
						blueStartY = WITH + i * WITH + WITH / 2;
						canvas.drawLine(blueStartX + x, blueStartY + y, blueX
								+ x, blueY + y, p);
						blueX = blueStartX;
						blueY = blueStartY;
						blueNum = num;
					} else {
						isDraw = true;
						blueNum = num;
						blueX = ballsStartLefPostion + j * WITH + WITH / 2;
						blueY = WITH + i * WITH + WITH / 2;
					}
				}
			}
		}

	}

	/**
	 * 和值阔度的总宽度
	 * 
	 * @return
	 */
	private int GetHeTotalWidthWithZhiAndKuoDu() {
		return isAddHeZhiAndKuoDu() ? FIRST_WITH + SECOND_WITH / 2 : FIRST_WITH;
	}

	/**
	 * 福彩3d小球
	 */
	public void onDrawCenterPL3(Canvas canvas, int ballNum) {
		/** modify by pengcx 20130812 start */
		for (int m = 0; m < ballNum; m++) {
			onDrawTable(canvas, m * row * WITH, 0);
			if (!isSelectedBar) {
				drawLine(canvas, m * row * WITH, 0, m);
			}
			int height = (int) (WITH - (8 * release));
			int with = (int) (WITH / 2 - (8 * release));
			for (int i = 0; i < line; i++) {
				for (int j = 0; j < row; j++) {
					if (!isSelectedBar) {
						int ballColor = 0;
						if (m % 2 == 0) {
							ballColor = R.drawable.notice_ball_red;
						} else {
							ballColor = R.drawable.notice_ball_blue;
						}
						int balls[] = list.get(i).getBallNum();
						int num = j + startNum;
						if (num == balls[m]) {
							canvas.drawBitmap(bitRedBall,
									(ballsStartLefPostion + j * WITH)
											+ (row * WITH * m),
									WITH + i * WITH, null);
							p.setColor(Color.WHITE);
							if (((iGameType.equals("gd11-5")
									|| iGameType.equals("11-5") || iGameType
										.equals("11-ydj")) && isBeforeThree)) {
								canvas.drawText(
										PublicMethod.isTen(balls[m]),
										(ballsStartLefPostion + j * WITH + with)
												+ (row * WITH * m), WITH + i
												* WITH + height, p);
							} else {
								canvas.drawText(
										"" + balls[m],
										(ballsStartLefPostion + j * WITH + with)
												+ (row * WITH * m) + 4, WITH
												+ i * WITH + height, p);
							}

						}
					} else {
						if (i == 0 || i == 1) {
							canvas.drawBitmap(bitBlackBall,
									(ballsStartLefPostion + j * WITH)
											+ (row * WITH * m),
									WITH + i * WITH, null);
							p.setColor(Color.WHITE);
							if (((iGameType.equals("gd11-5")
									|| iGameType.equals("11-5") || iGameType
										.equals("11-ydj")) && isBeforeThree)) {
								canvas.drawText(
										PublicMethod.isTen(j + startNum),
										(ballsStartLefPostion + j * WITH + with)
												+ (row * WITH * m), WITH + i
												* WITH + height, p);
							} else {
								canvas.drawText("" + j, (ballsStartLefPostion
										+ j * WITH + with)
										+ (row * WITH * m) + 4, WITH + i * WITH
										+ height, p);
							}

							if (isFirstDraw) {
								BallPosition ball = new BallPosition(
										(ballsStartLefPostion + j * WITH)
												+ (row * WITH * m), WITH + i
												* WITH, WITH,
										PublicMethod.isTen(j + startNum), i, m);
								ballsList.add(ball);
							}
						}
					}
				}
			}
		}
		/** modify by pengcx 20130812 end */
	}

	public void resetSelect() {
		ballsChcekOne.clear();
		ballsChcekTwo.clear();
	}

	/**
	 * 
	 * @param aResId
	 *            图片
	 * @param square
	 *            　正方形的宽度或高度
	 * @return
	 */
	protected Bitmap getBitmapFromRes(int aResId, int square) {
		return getBitmapFromRes(aResId, square, square);
	}

	/**
	 * 获取图片
	 */
	protected Bitmap getBitmapFromRes(int aResId, int _width, int _height) {
		Resources res = this.getContext().getResources();
		InputStream is = res.openRawResource(aResId);
		Bitmap bitmap = new BitmapDrawable(is).getBitmap();
		int width = 0;
		int height = 0;
		float sw;
		float sh;
		Matrix matrix;
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		sw = ((float) _width) / width;
		sh = ((float) _height) / height;
		matrix = new Matrix();
		matrix.postScale(sw, sh);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return bitmap;
	}

	/**
	 * 行开奖信息对象
	 * 
	 */
	public class RowInfo {
		/** 期号 */
		private String issue = "";
		/** 开奖号码 */
		private int ballNum[];
		private int trycode[];

		public int[] getTrycode() {
			return trycode;
		}

		public void setTrycode(int trycode[]) {
			this.trycode = trycode;
		}

		public String getIssue() {
			return issue;
		}

		public void setIssue(String issue) {
			this.issue = issue;
		}

		public int[] getBallNum() {
			return ballNum;
		}

		public void setBallNum(int[] ballNum) {
			this.ballNum = ballNum;
		}

		public RowInfo() {

		}
	}
	
}
