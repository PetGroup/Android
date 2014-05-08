package com.ruyicai.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 扎堆数据模型
 * @author wangw
 *
 */
public class RuyiGuessGatherBean implements Parcelable {

	private String gatherName;	//扎堆名称
	private String gatherPwd;	//扎堆密码
	private String gatherDescription;	//扎堆介绍
	private String guessSubjectid;	//竞猜选项ID
	private String peopleNum;	//人数
	private String guessNum;	//竞猜题目数量
	private int score;		//积分
	public String getGatherName() {
		return gatherName;
	}
	public void setGatherName(String gatherName) {
		this.gatherName = gatherName;
	}
	public String getGatherPwd() {
		return gatherPwd;
	}
	public void setGatherPwd(String gatherPwd) {
		this.gatherPwd = gatherPwd;
	}
	public String getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getGuessNum() {
		return guessNum;
	}
	public void setGuessNum(String guessNum) {
		this.guessNum = guessNum;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getGatherDescription() {
		return gatherDescription;
	}
	public void setGatherDescription(String gatherInfo) {
		this.gatherDescription = gatherInfo;
	}
	
	public String getGuessSubjectid() {
		return guessSubjectid;
	}
	public void setGuessSubjectid(String guessSubjectid) {
		this.guessSubjectid = guessSubjectid;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(gatherName);
		dest.writeString(gatherPwd);
		dest.writeString(peopleNum);
		dest.writeString(guessNum);
		dest.writeString(gatherDescription);
		dest.writeString(guessSubjectid);
		dest.writeInt(score);
	}
	
	public static final Parcelable.Creator<RuyiGuessGatherBean> CREATOR = new Parcelable.Creator<RuyiGuessGatherBean>() {

		@Override
		public RuyiGuessGatherBean createFromParcel(Parcel source) {
			RuyiGuessGatherBean bean = new RuyiGuessGatherBean();
			bean.gatherName = source.readString();
			bean.gatherPwd = source.readString();
			bean.peopleNum = source.readString();
			bean.guessNum = source.readString();
			bean.gatherDescription = source.readString();
			bean.guessSubjectid = source.readString();
			bean.score = source.readInt();
 			return bean;
		}

		@Override
		public RuyiGuessGatherBean[] newArray(int size) {
			return new RuyiGuessGatherBean[size];
		}
		
	};
	
}
