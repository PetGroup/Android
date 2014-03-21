package com.ruyicai.component.elevenselectfive;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveHistoryLotteryView.Row;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.PrizeInfoInterface;

/**
 * 查询历史开奖信息类
 *
 */
public class ElevenSelectFiveHistoryLottery {
	private static final String TAG = "ElevenSelectFiveHistoryLottery";
	private static final String PROTOCOL_QUERYESUCCESS_FLAG = "0000";

	private static final int GET_PRIZEINFO_ERROR = 0;
	private static final int GET_PRIZEINFO_SUCCESS = 3;

	private ElevenSelectFiveHistoryLotteryView elevenSelectFiveHistoryLotteryView;

	private List<PrizeInfo> prizeInfosList;

	// 获取历史开奖信息-第几页
	private int pageIndex = 1;
	// 获取历史开奖信息-每页显示的条数
	private int maxResult = 10;
	private String lotteryLotNo;

	private Message msg;
	private Bundle bundle;
	
	private Context context;
	private ProgressDialog progressDialog;
	public ElevenSelectFiveHistoryLottery(Context context,ProgressDialog progressDialog,String lotteryLotNo){
		this.context=context;
		this.lotteryLotNo=lotteryLotNo;
		initScreenShow();

		getDataFromInternet();
		this.progressDialog=progressDialog;
	}

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			CharSequence msgString = (CharSequence) msg.getData().get("msg");

			switch (msg.arg2) {
			case GET_PRIZEINFO_ERROR:
				Toast.makeText(context.getApplicationContext(),
						"历史获奖信息获取失败..." + msgString, Toast.LENGTH_LONG).show();
				break;

			case GET_PRIZEINFO_SUCCESS:
				elevenSelectFiveHistoryLotteryView.setPrizeInfos(prizeInfosList);
				if(progressDialog!=null && progressDialog.isShowing()){
					progressDialog.dismiss();
				}
				break;
			}

		}
	};

	private void initScreenShow() {
		elevenSelectFiveHistoryLotteryView = (ElevenSelectFiveHistoryLotteryView)((Activity) context). findViewById(R.id.elevenSelectFiveHistoryLotteryView);
	}

	void getDataFromInternet() {
		prizeInfosList = new ArrayList<PrizeInfo>();

		msg = new Message();
		bundle = new Bundle();

		new Thread(new Runnable() {
			public void run() {
				PrizeInfoInterface prizeInfoInterface = PrizeInfoInterface
						.getInstance();
				JSONObject prizeInfoJsonObject = prizeInfoInterface.getNoticePrizeInfo(
						lotteryLotNo, String.valueOf(pageIndex),
						String.valueOf(maxResult));
				analyzeJsonPrizeInfo(prizeInfoJsonObject);
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private void analyzeJsonPrizeInfo(JSONObject prizeInfoJsonObject) {
		try {
			String messageString = prizeInfoJsonObject.getString("message");
			String errorString = prizeInfoJsonObject.getString("error_code");

			// 网络获取失败
			if (!errorString.equals(PROTOCOL_QUERYESUCCESS_FLAG)) {
				bundle.putString("msg", messageString);
				msg.setData(bundle);

				msg.arg2 = GET_PRIZEINFO_ERROR;
			}
			// 网络获取成功，解析网络
			else {
				JSONArray prizeInfoArray = prizeInfoJsonObject
						.getJSONArray("result");
				int length = prizeInfoArray.length();

				for (int i = 0; i < length; i++) {
					JSONObject jsonObject = (JSONObject) prizeInfoArray.get(i);

					String batchCode = jsonObject.getString("batchCode");
					String winCode = jsonObject.getString("winCode");
					String openTime = jsonObject.getString("openTime");

					PrizeInfo prizeInfo = new PrizeInfo(batchCode, winCode,
							openTime);
					prizeInfosList.add(prizeInfo);
				}

				msg.arg2 = GET_PRIZEINFO_SUCCESS;
			}
		} catch (Exception e) {
			Log.i(TAG, e.toString());
		}
	}

	/**
	 * 历史开奖信息类
	 */
	class PrizeInfo {
		private String batchCode;

		private List<Integer> lotteryCode;
		private static final int WINCODE_SIZE = 5;
		
		public String getBatchCode() {
			return batchCode;
		}

		public void setBatchCode(String batchCode) {
			this.batchCode = batchCode;
		}

		public void setWinCodes(String winCode) {
			for (int i = 0; i < WINCODE_SIZE; i++) {
				String childCode = winCode.substring(i * 2, i * 2 + 2);
				lotteryCode.add(Integer.valueOf(childCode));
			}
		}

		public PrizeInfo(String batchCode, String winCode, String openTime) {
			super();
			this.batchCode = batchCode;

			lotteryCode = new ArrayList<Integer>();
			setWinCodes(winCode);
		}

		/**
		 * 获取指定列的开奖号码值：是开奖号码，返回该列值；不是开奖号码，返回-1
		 */
		public int getWinCodeByColum(int colum) {
			int value;

			if (colum > 0 && colum <= Row.lotteryNum) {
				value = colum;

				if (lotteryCode.contains(value)) {
					return value;
				}
			} 

			return -1;
		}
	}

}
