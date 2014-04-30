package com.ruyicai.component;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.google.inject.Singleton;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.component.view.SimpleMessageView.OnMessageEditListener;
import com.ruyicai.model.MyMessage;
import com.ruyicai.model.PayLoadBean;
import com.ruyicai.util.DensityUtil;
import com.ruyicai.util.StringUtils;
import com.ruyicai.util.json.JsonUtils;

@Singleton
public class MsgPopMenu {

	private PopupWindow popupWindow;
	private View view;
	private Context context;
	private OnMessageEditListener onMessageEditListener;
	private MyMessage myMessage;
	private int dip2px;

	public void setOnMessageEditListener(OnMessageEditListener onMessageEditListener) {
		this.onMessageEditListener = onMessageEditListener;
	}

	public void showMsgPopMenu(Context context) {
		this.context=context;
		dip2px = DensityUtil.dip2px(context, 46);
		view = LayoutInflater.from(context).inflate(R.layout.msg_popmenu, null);
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}
	
	public void setMessage(final MyMessage myMessage,final String msgBody){
		this.myMessage=myMessage;
		if(getCopyType(myMessage)==0){
			view.findViewById(R.id.PopMenu_copyButton).setVisibility(View.GONE);
		}
		//复制
		view.findViewById(R.id.PopMenu_copyButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
				cmb.setText(msgBody);
			}
		});
		//转发
		view.findViewById(R.id.PopMenu_forwardButton).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
		});
		//删除
		view.findViewById(R.id.PopMenu_deleteButton).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
					ShowAlertDialog();
				}
		});
	}
	private int getCopyType(MyMessage msg){
		if(!StringUtils.isNotEmty(msg.getPayLoad())){
			return 1;
		}
		try {
			PayLoadBean payLoadBean=JsonUtils.resultData(msg.getPayLoad(), PayLoadBean.class);
			if("3".equals(payLoadBean.getType())){
				return 1;
			}else if ("img".equals(payLoadBean.getType())) {
				return 0;
			}
			return 1;
		} catch (Exception e) {
			return 1;
		}
	}
	public void showAsDropDown(final View parent) {
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		int height = view.getMeasuredHeight();
		int[] location = new int[2];
		parent.getLocationInWindow(location);
		int y = location[1];
		if((y-dip2px) <height){
//			view.setBackgroundResource(R.drawable.pop_back_up);
			view.setPadding(0, 30, 0, 0);
			popupWindow.showAsDropDown(parent);
		} else {
			popupWindow.showAsDropDown(parent, 0, -parent.getHeight() - height);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}
	public void dismiss() {
		popupWindow.dismiss();
	}
	private void ShowAlertDialog() {
		AlertDialog.Builder downloadBuilder = new AlertDialog.Builder(context);
		downloadBuilder.setTitle("提示");
		downloadBuilder.setMessage("删除后将不会出现在您的消息记录中");
		downloadBuilder.setPositiveButton("删除",new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				onMessageEditListener.onDelete(myMessage);
			}
		});
		downloadBuilder.setNegativeButton("取消",null);
		downloadBuilder.show();
	}
}
