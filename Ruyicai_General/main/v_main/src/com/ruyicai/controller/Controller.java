package com.ruyicai.controller;

import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.FeedBackListInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.net.newtransaction.recharge.RechargeDescribeInterface;
import com.ruyicai.util.ProtocolManager;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class Controller {
    private static final String TAG = "Controller";
    private static Controller sInstance;
    private Context mContext;
    private Context mProviderContext;
    private JSONObject jsonObj;
    protected ProgressDialog dialog;
    
    protected Controller(Context _context) {
        mContext = _context;
//        mProviderContext = _context;
    }

    /**
     * Gets or creates the singleton instance of Controller.
     */
    public static Controller getInstance(Context _context) {
        if (sInstance == null) {
            sInstance = new Controller(_context);
        } else {
        	sInstance.mContext = _context;
        }
        return sInstance;
    }

    /**
     * For testing only:  Inject a different context for provider access.  This will be
     * used internally for access the underlying provider (e.g. getContentResolver().query()).
     * @param providerContext the provider context to be used by this instance
     */
    public void setProviderContext(Context providerContext) {
        mProviderContext = providerContext;
    }
    
	/**
	 * 投注action
	 */
   public void doBettingAction(final MyHandler handler,final BetAndGiftPojo betAndGift) {
	   if (dialog != null && dialog.isShowing()) return;
	   dialog = UserCenterDialog.onCreateDialog(mContext,mContext.getResources().getString(R.string.recommend_network_connection));
	   dialog.show();
	   
		// 加入是否改变切入点判断 陈晨 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";

			@Override
			public void run() {
				str = BetAndGiftInterface.getInstance().betOrGift(betAndGift);
				try {
					JSONObject obj = new JSONObject(str);
					final String msg = obj.getString("message");
					final String error = obj.getString("error_code");
					setRtnJSONObject(obj);
					handler.handleMsg(error, msg);
				} catch (JSONException e) {
					e.printStackTrace();
					// TODO Auto-generated method stub			
				}
				dialog.dismiss();
				//dialog = null;
			}
		});
		t.start();
   }
   /**
    * set return obj
    * @param obj
    */
   public void setRtnJSONObject(JSONObject obj) {
	   this.jsonObj = obj;
   }
   
   /**
    * get return obj
    * @param obj
    */
   public JSONObject getRtnJSONObject() {
	   return this.jsonObj;
   }
   
   
   /**
	 * 查询提现记录详情
	 * 
	 */
	public void queryCashDetail(final MyHandler handler, final String cashdetailId) {
		if (dialog != null && dialog.isShowing()) return;
		dialog = UserCenterDialog.onCreateDialog(mContext, mContext .getResources()
				.getString(R.string.recommend_network_connection));
		dialog.show();
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				str = queryCashNet(cashdetailId);
				try {
					JSONObject obj = new JSONObject(str);
					final String msg = obj.getString("message");
					final String error = obj.getString("error_code");
					setRtnJSONObject(obj);
					handler.handleMsg(error, msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		});
		t.start();
	}

	public String queryCashNet(String cashdetailId) {
		JSONObject jsonProtocol = ProtocolManager.getInstance()
				.getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, "getCash");
			jsonProtocol.put(ProtocolManager.CASHTYPE, "cashDetail");
			jsonProtocol.put(ProtocolManager.CASHDETAILID, cashdetailId);
			return InternetUtils.GetMethodOpenHttpConnectSecurity(
					Constants.LOT_SERVER, jsonProtocol.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取支付宝签名
	 * @return
	 */
	public String getAlipaySign() {
		JSONObject jsonProtocol = ProtocolManager.getInstance()
				.getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, "login");
			jsonProtocol.put(ProtocolManager.REQUESTTYPE, "alipaySign");
			
			String result = InternetUtils.GetMethodOpenHttpConnectSecurity(
					Constants.LOT_SERVER, jsonProtocol.toString());
			JSONObject obj = new JSONObject(result);
			return obj.getString("value");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 查询email的绑定状态
	 * @param lotno
	 * @return
	 */
	public void queryOrderEmail(final Handler handler, final String lotno,
			final String userno) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject jsonProtocol = ProtocolManager.getInstance()
						.getDefaultJsonProtocol();
				try {
					jsonProtocol.put(ProtocolManager.COMMAND, "message");
					jsonProtocol.put(ProtocolManager.REQUESTTYPE,
							"selectOrderEmail");
					jsonProtocol.put(ProtocolManager.LOTNO, lotno);
					jsonProtocol.put(ProtocolManager.USERNO, userno);
					String returnInfo = InternetUtils
							.GetMethodOpenHttpConnectSecurity(
									Constants.LOT_SERVER,
									jsonProtocol.toString());
					JSONObject obj = new JSONObject(returnInfo);
					String error_code = obj.getString("error_code");
					if (error_code.equals("0000")) {
						String result = obj.getString("result");
						Message msg = new Message();
						msg.what = 1;
						msg.obj = result;
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}).start();

	}
	
	/**
	 * 订单邮件开发设置
	 * @param lotno
	 * @return
	 */
	public void setOrderEmail(final Handler handler, final String lotno, final String state, final String userNo) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject jsonProtocol = ProtocolManager.getInstance()
						.getDefaultJsonProtocol();
				try {
					jsonProtocol.put(ProtocolManager.COMMAND, "message");
					jsonProtocol.put(ProtocolManager.REQUESTTYPE, "orderEmail");
					jsonProtocol.put(ProtocolManager.LOTNO, lotno);
					jsonProtocol.put(ProtocolManager.STATE, state);
					jsonProtocol.put(ProtocolManager.USERNO, userNo);
					String result = InternetUtils.GetMethodOpenHttpConnectSecurity(
							Constants.LOT_SERVER, jsonProtocol.toString());
					JSONObject obj = new JSONObject(result);
					String error_code = obj.getString("error_code");
					Message msg = new Message();
					if (error_code.equals("0000")) {
						Bundle data = new Bundle();
						data.putString("state", state);
						data.putString("lotno", lotno);
						msg.what = 2;
						msg.setData(data);
					} else {
						msg.what = 3;
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	
	
	/**
	 * 读取广告墙的显示状态
	 */
	public void readAdWallStateNet() {
		final RWSharedPreferences shellRW = new RWSharedPreferences(
				mContext, ShellRWConstants.ACCOUNT_DISPAY_STATE);
		new Thread(new Runnable() {
			@Override
			public void run() {
				JSONObject jsonObject = RechargeDescribeInterface.getInstance()
						.rechargeDescribe("scoreWallDisplay");
				try {
					if (jsonObject != null) {
						String content = jsonObject.getString("content").toString();
						if ("true".equals(content)) {
							shellRW.putBooleanValue(Constants.ADWALL_DISPLAY_STATE, true);
						} else {
							shellRW.putBooleanValue(Constants.ADWALL_DISPLAY_STATE, false);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * 读取联动优势话费充值的显示状态
	 */
//	public void readUmpayStateNet() {
//		final RWSharedPreferences shellRW = new RWSharedPreferences(mContext,
//				ShellRWConstants.ACCOUNT_DISPAY_STATE);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				JSONObject jsonObject = RechargeDescribeInterface.getInstance()
//						.rechargeDescribe("umpayHfChargeDisplay");
//				try {
//					if (jsonObject != null) {
//						String content = jsonObject.get("content").toString();
//						if ("true".equals(content)) {
//							shellRW.putBooleanValue(
//									Constants.UMPAY_PHONE_DISPLAY_STATE, true);
//						} else {
//							shellRW.putBooleanValue(
//									Constants.UMPAY_PHONE_DISPLAY_STATE, false);
//						}
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
	
	
	public void getFeedbackListNet(final Handler handler, final String userno) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Constants.feedBackData = FeedBackListInterface.getInstance()
						.getFeedbackList("0", "10", userno);
				try {
					Message msg = new Message();
					JSONObject feedjson = new JSONObject(Constants.feedBackData);
					String errorCode = feedjson.getString("error_code");
					// add by yejc 20130411
					if (feedjson.has("result")) {
						Constants.feedBackJSONArray = feedjson
								.getJSONArray("result");
					}
					msg.what = 11;
					msg.obj = Constants.feedBackJSONArray;
					// end
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
