package com.ruyicai.dialog;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.util.RWSharedPreferences;

import android.app.Activity;
import android.widget.Toast;

/**
 * ע���Ի���
 * @author Administrator
 *
 */
public class LogOutDialog extends BaseDialog{
	static MyDialogListener dialogListener;
	private RWSharedPreferences shellRW;
    public static LogOutDialog dialog = null;
	public LogOutDialog(Activity activity, String title, String message) {
		super(activity, title, message);
		// TODO Auto-generated constructor stub
	}
    public static LogOutDialog createDialog(Activity activity){
//    	if(dialog == null){
    		dialog = new LogOutDialog(activity, activity.getString(R.string.log_out_title), activity.getString(R.string.log_out_content));
    		dialog.showDialog();
    		dialog.createMyDialog();
//    	}else{
//    		dialog.showDialog();
//    	}
    	return dialog;
    }
    /**
     * ��ť�Ļص�����
     */
    public void setOnClik(MyDialogListener dialogListener){
    	this.dialogListener = dialogListener;
    }
	/**
	 * ����ϴεĵ�¼��Ϣ
	 */
	public void clearLastLoginInfo() {
		shellRW = new RWSharedPreferences(activity, "addInfo");
		String userno = shellRW.getStringValue("userno");
		if(userno.equals("")||userno == null){
			Toast.makeText(activity,activity.getString(R.string.log_out_toast_no_login), Toast.LENGTH_SHORT).show();
		}else{
			shellRW.putStringValue("sessionid", "");
			shellRW.putStringValue("userno", "");
			shellRW.putStringValue("password", "");
			shellRW.putBooleanValue(ShellRWConstants.AUTO_LOGIN, false);
			shellRW.putStringValue(ShellRWConstants.RANDOMNUMBER, "");
			
			Toast.makeText(activity,activity.getString(R.string.log_out_toast_msg), Toast.LENGTH_SHORT).show();
		}
	
	}
	@Override
	public void onOkButton() {
		// TODO Auto-generated method stub
		dialogListener.onOkClick();
	}

	@Override
	public void onCancelButton() {
		// TODO Auto-generated method stub
	}

}
