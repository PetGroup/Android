package com.ruyicai.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ReturnBean implements Parcelable{
	private static final long serialVersionUID = -8704930601795903613L;
	private String error_code = "";
	private String message = "";
	private String result = "";
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(error_code);
		dest.writeString(message);
		dest.writeString(result);
	}
	
	public static final Parcelable.Creator<ReturnBean> CREATOR = new Parcelable.Creator<ReturnBean>() {

		@Override
		public ReturnBean createFromParcel(Parcel source) {
			ReturnBean bean = new ReturnBean();
			bean.error_code = source.readString();
			bean.message = source.readString();
			bean.result = source.readString();
			return bean;
		}

		@Override
		public ReturnBean[] newArray(int size) {
			return new ReturnBean[size];
		}
		
	};
	
}
