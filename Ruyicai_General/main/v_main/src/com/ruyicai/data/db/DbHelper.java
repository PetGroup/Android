package com.ruyicai.data.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.model.User;

@Singleton
public class DbHelper {
	private SqlLiteHelper helper;


	private String userid;
	@Inject Context context;
	//@Inject CommonsharedPreferences commonsharedPreferences;
	public  DbHelper() {
	}
	public void switchToUser(String userid){
		if(helper!=null){
			if(userid.equals(this.userid)){
				return;
			}else{
				getHelper().close();
			}
		}
		this.userid=userid;
		helper = new SqlLiteHelper(this, context,userid);
	}
	
	public SqlLiteHelper getHelper() {
		if(helper==null){
			helper = new SqlLiteHelper(this, context,userid);
		}
		return helper;
	}
	public SqlLiteHelper getHelper(Context context) {
		if(helper==null){
			helper = new SqlLiteHelper(this, context,userid);
		}
		return helper;
	}
	

	
	
	/**
	 * 更新消息PayLoad
	 * @param packetId
	 * @param newStatus
	 * @return
	 */
//	public int upDateMsgPayLoad(String packetId,String payLoad) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleMsgPayLoad), payLoad);
//		int a = db.update(context.getString(R.string.MsgInfoTableName),
//				values, context.getString(R.string.MsgInfoTitlePacketId) + "=?", 
//				new String[] { packetId });
//		return a;
//	}

	

	private User createUser(String userId,String nickName){
		User user=new User();
		user.setId(userId);
		user.setNickname(nickName);
		return user;
	}




	/**
	 * 清除所有与我相关信息
	 * @param msgType
	 * @return
	 */
//	public int clearWithMeMsg() {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.withMeMsgListTable),context.getString(R.string.MsgInfoTitleTag)+" = ?", new String[] { "9" });
//		return result;
//	}
}
