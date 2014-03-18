package com.ruyicai.activity.account;

import org.json.JSONException;
import org.json.JSONObject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.RechargeMoneyTextWatcher;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.RechargeInterface;
import com.ruyicai.net.newtransaction.pojo.RechargePojo;
import com.ruyicai.net.newtransaction.recharge.RechargeDescribeInterface;
import com.ruyicai.util.CheckUtil;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.umeng.analytics.MobclickAgent;
import com.umpay.quickpay.UmpayActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 联动优势充值
 * 
 * @author win
 * 
 */
public class UmPayActivity extends Activity implements HandlerMsg {
	public ProgressDialog progressdialog;
	private final String YINTYPE = "0903";
	private Button secureOk;
	private EditText accountnum;
	private EditText payerNameEdit = null;
	private EditText payerIdEdit = null;
	private WebView alipay_content = null;
	private String sessionId = "";
	private String userno = "";
	private String message = "";
	private MyHandler handler = new MyHandler(this);
	private TextView accountTitleTextView = null;
	private final int REQUESTCODE = 1;
	private String orderId = "";
	private boolean isUmPay = false;
	private String payerName = "";
	private String payerId = "";
	private RWSharedPreferences shellUserInfo;
	public static final int RESULTCODE_UMPAY = 88888;// 联动优势返回码
    public static final String RET_CANCEL = "1001";// 返回结果1001表示用户取消
    public static final String RET_PAYPARAMSERROR = "1002";// 返回结果1002表示传入参数有误
    public static final String RET_SUCCESS = "0000";// 返回结果0000表示支付成功

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_alipay_secure_recharge_dialog);
		shellUserInfo = new RWSharedPreferences(this, "addInfo");
		initTextViewContent();
		initView();
	}
	
	private void initView() {
		payerNameEdit = (EditText)findViewById(R.id.payer_name);
		payerIdEdit = (EditText)findViewById(R.id.payer_identity_id);
		payerNameEdit.setVisibility(View.VISIBLE);
		payerIdEdit.setVisibility(View.VISIBLE);
		String name = shellUserInfo.getStringValue("name");
		String certid = shellUserInfo.getStringValue("certid");
		if (!"".equals(name) && !"".equals(certid)) {
			payerNameEdit.setText(name);
			payerIdEdit.setText(certid);
			payerNameEdit.setEnabled(false);
			payerIdEdit.setEnabled(false);
		}
		accountTitleTextView = (TextView) findViewById(R.id.accountTitle_text);
		accountTitleTextView.setText(R.string.umpay_recharge);

		secureOk = (Button) findViewById(R.id.alipay_secure_ok);
		accountnum = (EditText) findViewById(R.id.alipay_secure_recharge_value);
		/**add by yejc 20130802 start*/
		accountnum.addTextChangedListener(new RechargeMoneyTextWatcher(accountnum));
		/**add by yejc 20130802 end*/
		secureOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MobclickAgent.onEvent(UmPayActivity.this, "chongzhi ");
				payerName = payerNameEdit.getText().toString();
				payerId = payerIdEdit.getText().toString();
				if (TextUtils.isEmpty(payerName)) {
					Toast.makeText(UmPayActivity.this, R.string.payer_name_no_empty, Toast.LENGTH_SHORT).show();
				} else if (!CheckUtil.isValidName(payerName)) {
					Toast.makeText(UmPayActivity.this, R.string.input_name_error, Toast.LENGTH_SHORT).show();
				} else if (TextUtils.isEmpty(payerId)) {
					Toast.makeText(UmPayActivity.this, R.string.payer_identity_id_no_empty, Toast.LENGTH_SHORT).show();
				} else if (!CheckUtil.isValidCard(payerId)) {
					Toast.makeText(UmPayActivity.this, R.string.payer_identity_id_error, Toast.LENGTH_SHORT).show();
				} else {
					beginUmpayRecharge(v);
				}
			}
		});
		PublicMethod.setTextViewContent(this); //add by yejc 20130718
	}
	
	private void initTextViewContent() {
		alipay_content = (WebView) findViewById(R.id.alipay_content);
		new Thread(new Runnable() {
			@Override
			public void run() {
				JSONObject jsonObject = getJSONByLotno();
				try {
					final String conten = jsonObject.get("content").toString();
					handler.post(new Runnable() {
						public void run() {
							alipay_content.loadDataWithBaseURL("", conten, "text/html", "UTF-8", "");
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private static JSONObject getJSONByLotno() {
		JSONObject jsonObjectByLotno = RechargeDescribeInterface.getInstance()
				.rechargeDescribe("umpayYjChargeDescriptionHtml");
		return jsonObjectByLotno;
	}

	// 联动优势充值
	private void beginUmpayRecharge(View Vi) {
		String umPayRechargeValue = accountnum.getText().toString();
		RWSharedPreferences pre = new RWSharedPreferences(UmPayActivity.this,
				"addInfo");
		String sessionIdStr = pre.getStringValue("sessionid");
		if (sessionIdStr.equals("")) {
			Intent intentSession = new Intent(UmPayActivity.this,
					UserLogin.class);
			startActivity(intentSession);
		} else {
			if (!PublicMethod.isRecharge(umPayRechargeValue, this)) {
				RechargePojo rechargepojo = new RechargePojo();
				rechargepojo.setAmount(umPayRechargeValue);
				rechargepojo.setRechargetype("11");
				rechargepojo.setCardtype(YINTYPE);
				rechargepojo.setCertid(payerId);
				rechargepojo.setName(payerName);
				/**add by yejc 20130527 start*/
				if (isUmPay) {
					rechargepojo.setBankId("ump003");
				} else {
					rechargepojo.setBankId("ump001");
				}
				/**add by yejc 20130527 end*/
				recharge(rechargepojo);
			}
		}
	}

	// 充值
	private void recharge(final RechargePojo rechargepojo) {
		RWSharedPreferences pre = new RWSharedPreferences(UmPayActivity.this,
				"addInfo");
		sessionId = pre.getStringValue(ShellRWConstants.SESSIONID);
		userno = pre.getStringValue(ShellRWConstants.USERNO);
		ConnectivityManager ConnMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = ConnMgr.getActiveNetworkInfo();
		if (info.getExtraInfo() != null
				&& info.getExtraInfo().equalsIgnoreCase("3gwap")) {
			Toast.makeText(this, "提醒：检测到您的接入点为3gwap，可能无法正确充值,请切换到3gnet！",
					Toast.LENGTH_LONG).show();
		}
		progressdialog = UserCenterDialog.onCreateDialog(UmPayActivity.this);
		progressdialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String error_code = "00";
				message = "";
				try {
					rechargepojo.setSessionid(sessionId);
					rechargepojo.setUserno(userno);
					String re = RechargeInterface.recharge(
							rechargepojo);
					JSONObject obj = new JSONObject(re);
					error_code = obj.getString("error_code");
					message = obj.getString("message");
					if (error_code.equals("0000")) {
						orderId = obj.getString("orderId");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				handler.handleMsg(error_code, message);
				progressdialog.dismiss();
			}
		}).start();
	}

	@Override
	public void errorCode_0000() {
		turnUMPayView();
	}

	@Override
	public void errorCode_000000() {
	}

	@Override
	public Context getContext() {
		return this;
	}

	/**
	 * 银联充值跳转到插件
	 */
	public void turnUMPayView() {
		Intent intent = new Intent(this, UmpayActivity.class);
		intent.putExtra("tradeNo", orderId);//订单号
		intent.putExtra("merCustId", "");//用户编号
        intent.putExtra("gateId", "");//银行代码
        intent.putExtra("iseditable","0" );//姓名与身份证是否修改
        intent.putExtra("holderName", payerName);//姓名
        intent.putExtra("identityCode", payerId);//身份证号
        startActivityForResult(intent, REQUESTCODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE && resultCode == RESULTCODE_UMPAY
				&& data != null) {
            String retCode = data.getStringExtra("umpResultCode");
            String retMsg = data.getStringExtra("umpResultMessage");
            if (RET_CANCEL.equals(retCode)) {
                Toast.makeText(this, retMsg, Toast.LENGTH_LONG).show();
            } else if (RET_PAYPARAMSERROR.equals(retCode)) {
                Toast.makeText(this, retMsg, Toast.LENGTH_LONG).show();
            } else if (RET_PAYPARAMSERROR.equals(retCode)) {
                Toast.makeText(this, retMsg, Toast.LENGTH_LONG).show();
            }
        }
	}
}