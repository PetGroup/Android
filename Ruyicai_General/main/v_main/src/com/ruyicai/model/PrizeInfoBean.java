package com.ruyicai.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.ruyicai.component.elevenselectfive.ElevenSelectFiveHistoryLotteryView.Row;

/**
 * 历史开奖信息类
 */
public class PrizeInfoBean implements Parcelable {
	private String batchCode;
    private String openTime;
    private String winCode;
	private List<Integer> lotteryCode;
	private static final int WINCODE_SIZE = 5;
	
	public String getWinCode() {
		return winCode;
	}

	public void setWinCode(String winCode) {
		this.winCode = winCode;
	
		setWinCodes(winCode);
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public void setWinCodes(String winCode) {
		lotteryCode = new ArrayList<Integer>();
		for (int i = 0; i < WINCODE_SIZE; i++) {
			String childCode = winCode.substring(i * 2, i * 2 + 2);
			lotteryCode.add(Integer.valueOf(childCode));
		}
	}

//	public PrizeInfo(String batchCode, String winCode, String openTime) {
//		super();
//		this.batchCode = batchCode;
//
//		lotteryCode = new ArrayList<Integer>();
//		setWinCodes(winCode);
//	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public static int getWincodeSize() {
		return WINCODE_SIZE;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
    			dest.writeString(batchCode);
    			dest.writeString(openTime);
    			dest.writeString(winCode);
    			dest.writeList(lotteryCode);
	}
	public static final Parcelable.Creator<PrizeInfoBean> CREATOR = new Parcelable.Creator<PrizeInfoBean>() {
        public PrizeInfoBean createFromParcel(Parcel in) {
        	PrizeInfoBean bean=new PrizeInfoBean();
        	bean.batchCode=in.readString();
        	bean.openTime=in.readString();
        	bean.winCode=in.readString();
        	bean.lotteryCode = in.readArrayList(this.getClass().getClassLoader());
            return bean;
        }
        @Override
        public PrizeInfoBean[] newArray(int size) {
            return new PrizeInfoBean[size];
        }
    };    
}
