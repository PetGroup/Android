package com.ruyicai.component.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.model.MyMessage;
import com.ruyicai.model.User;
import com.ruyicai.util.date.MyDate;
@SuppressLint("ViewConstructor")
public class SimpleMessageView extends AbstractMessageView {
	
	TextView msgTextView;
    private Context mContext;
    private MyMessage myMessage;
    private OnMessageEditListener onResendListener;
	public void setOnResendListener(OnMessageEditListener onResendListener) {
		this.onResendListener = onResendListener;
	}
	public SimpleMessageView(Context context,boolean isLeft) {
		super(context);
		mContext = context;
		initView(isLeft);
	}
	private void initView(boolean isLeft) {
		if(isLeft){
			view = LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_txt_left,null); 
		}else{
			view = LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_txt_right,null); 
		}
		this.addView(view);
		initStatusComponent(mContext);
		msgTextView = (TextView) view.findViewById(R.id.Chatting_Item_TxtView_Msg);
	}
	public void setMessage(MyMessage message, User user) {
//		super.setUser(user);
		super.setStatus(message.getStatus());
		super.setTime(MyDate.getDateFromLong(message.getMsgTime()));
		faileImage.setTag(message);
		this.myMessage=message;
	}

	public void setMsgContent(CharSequence msgContent){
		msgTextView.setText(msgContent,BufferType.SPANNABLE);
	}
	public void setMsgDetailBtnListener(OnLongClickListener onLongClickListener){
		msgTextView.setOnLongClickListener(onLongClickListener);
	}
	
	public static interface OnMessageEditListener{
		public void onResend(MyMessage myMessage);
		public void onDelete(MyMessage myMessage);
	}

	@Override
	public void reSendMessage() {
		onResendListener.onResend(myMessage);
	}
}
