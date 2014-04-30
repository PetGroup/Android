package com.ruyicai.component.view;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.MessageStatus;
import com.ruyicai.model.MyMessage;
import com.ruyicai.model.User;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.date.MyDate;

public abstract class AbstractMessageView extends LinearLayout{

	protected ProgressBar progressBar;// 进度条
	protected ImageView faileImage;// 失败红点
	protected TextView userReadTxt;// 消息状态
	protected TextView txtTime;// 时间
	protected ImageView imgView_Icon;// 头像
	protected View view;

	public AbstractMessageView(Context context) {
		super(context);
	}
	public AbstractMessageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	protected void initStatusComponent(final Context mContext) {
		progressBar = (ProgressBar) view.findViewById(R.id.Chatting_Item_Process_bar);
		faileImage = (ImageView) view.findViewById(R.id.FaileImage);
		userReadTxt = (TextView) view.findViewById(R.id.userReadTxt);
		txtTime = (TextView) view.findViewById(R.id.Chatting_Item_TxtTime);
		imgView_Icon = (ImageView) view.findViewById(R.id.Chatting_Item_ImgView_Icon);
		
		imgView_Icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String userId = ((User) imgView_Icon.getTag()).getId();
				if (userId != null && !"".equals(userId)) {
					if (userId.startsWith("sys")|| Constants.SYSTEM_ID.equals(userId)) {
						return;
					}
				}
//				Intent intent = new Intent(mContext,FriendInfoDetailActivity.class);
//				intent.putExtra(FriendInfoDetailActivity.USER_ID, userId);
//				mContext.startActivity(intent);
			}
		});
//		faileImage.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				final BaseDialog baseDialog = new BaseDialog();
//				baseDialog.showDialog(mContext);
//				baseDialog.setOkListner(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						baseDialog.cancelDialog();
//						reSendMessage();
//					}
//				});
//				baseDialog.createDialog();
//			}
//		});
	}
	/**
	 * 设置消息内容和用户信息
	 * @param myMessage
	 * @param user
	 */
	public abstract void setMessage(MyMessage myMessage, User user);

	/**
	 * 设置消息内容
	 * @param msgContentStr
	 */
	public abstract void setMsgContent(CharSequence msgContentStr);

	/**
	 * 对消息的操作
	 * @param onLongClickListener
	 */
	public abstract void setMsgDetailBtnListener(OnLongClickListener onLongClickListener);
	
	/**
	 * 重发消息
	 */
	public abstract void reSendMessage();
	/**
	 * 设置消息发送状态
	 * 
	 * @param statusToSet
	 */
	public void setStatus(MessageStatus statusToSet) {
		MessageStatus status = (MessageStatus) progressBar.getTag();
		if (status != null && status.getValue() == statusToSet.getValue()) {
			return;
		}
		if (statusToSet.getValue() == MessageStatus.Sending.getValue()) {
			progressBar.setVisibility(View.VISIBLE);
			faileImage.setVisibility(View.INVISIBLE);
			userReadTxt.setText("");
		} else if (statusToSet.getValue() == MessageStatus.SendFail.getValue()) {
			faileImage.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			userReadTxt.setText("");
		} else if (statusToSet.getValue() == MessageStatus.ServerReceivd.getValue()) {
			progressBar.setVisibility(View.GONE);
			faileImage.setVisibility(View.INVISIBLE);
			userReadTxt.setText("");
		} else if (statusToSet.getValue() == MessageStatus.UserReceived.getValue()) {
			userReadTxt.setText("送达");
			progressBar.setVisibility(View.GONE);
			faileImage.setVisibility(View.INVISIBLE);
		} else if (statusToSet.getValue() == MessageStatus.UserRead.getValue()) {
			userReadTxt.setText("已读");
			progressBar.setVisibility(View.GONE);
			faileImage.setVisibility(View.INVISIBLE);
		}/*else if (statusToSet.getValue() == MessageStatus.UploadFaile.getValue()) {
			faileImage.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			userReadTxt.setText("");
		}*/else {
			progressBar.setVisibility(View.GONE);
			faileImage.setVisibility(View.INVISIBLE);
			userReadTxt.setText("");
		}
		progressBar.setTag(statusToSet);
	}

	/**
	 * 设置消息时间
	 * 
	 * @param msgTime
	 */
	public void setTime(Date msgTime) {
		txtTime.setText(getDateString(msgTime));
	}

	/**
	 * 设置用户信息
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		User tag = (User) imgView_Icon.getTag();
		setImgIfNeed(user, tag);
		imgView_Icon.setTag(user);
	}

	/**
	 * 设置图片
	 * 
	 * @param user
	 * @param tag
	 */
	private void setImgIfNeed(User user, User tag) {
		if (tag != null && tag.getHeadIcon().equals(user.getHeadIcon())) {
			return;
		}
//		if (Constants.SYSTEM_ID.equals(user.getId())) {
//			imgView_Icon.setImageResource(R.drawable.announcement);
//			return;
//		}
//		if (!"".equals(user.getHeadIcon())) {
//			BitmapXUtil.display(this.getContext(), imgView_Icon,
//					user.getHeadIcon(),
//					PublicMethod.getDefaultPictureResId(user.getGender()), 10);
//		} else {
//			BitmapXUtil.display(this.getContext(), imgView_Icon,
//					PublicMethod.getDefaultPictureResId(user.getGender()), 10);
//		}
	}

	/**
	 * 计算时间
	 * @param date
	 * @return
	 */
	protected String getDateString(Date date) {
		if (date == null) {
			return "时间为空";
		}
		/** 当前时间 年月日 **/
		Date currentDate = new Date(System.currentTimeMillis());
		/** 两个时间差的天数 **/
		long ApartDays = MyDate.getDatetimeIntervalUsingDay(currentDate, date);
		int isYeater = MyDate.isYeaterday(date, currentDate);
		/** 得到消息的小时 包含上下午 **/
		String hourExtraInfo = MyDate.GetHourExtraInfo(date);
		/** 当前时间 年 **/
		int yearCurrentDate = currentDate.getYear();
		/** 消息时间 年 **/
		int yearMsgDate = date.getYear();
		/** 周几 **/
		String indexOfWeek = MyDate.getIndexOfWeek(date);
		if (ApartDays == 0) {// 今天
			if (isYeater == 0) {
				return "昨天" + hourExtraInfo;
			}
			return hourExtraInfo;
		} else if (ApartDays == 1) {// 昨天
			return ("昨天" + " " + hourExtraInfo);
		} else if (ApartDays == 2) {// 前天
			return (indexOfWeek + " " + hourExtraInfo);
		} else if (yearCurrentDate == (yearMsgDate)) {// 今年
			return (MyDate.AddMDChina(date) + " " + hourExtraInfo);
		} else {// 今年之前
			return (MyDate.AddYMDChina(date) + " " + hourExtraInfo);
		}
	}
}