package com.ruyicai.controller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.AsyncTask;
import android.support.v4.util.LruCache;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.data.db.DbHelper;

@Singleton
public class UserService {//implements OnNetworkAvailableListener {
	@Inject DbHelper dbHelper;
	//@Inject PublicMethod publicMethod; 
	//@Inject CommonsharedPreferences commonsharedPreferences;
	//@Inject SayHelloService sayHelloService;
	public UserService() {
	}

	/**
	 * 更新用户激活状态
	 * @param userId
	 * @param active
	 */
	public void updateUserActive(String userId,String active){
		//dbHelper.changeActivite(userId, active);
	}

//	public void addUserInfoUpdateListeners(
//			UserInfoUpdateListener userInfoUpdateListener) {
//		if (userInfoUpdateListeners.contains(userInfoUpdateListener)) {
//			return;
//		}
//		userInfoUpdateListeners.add(userInfoUpdateListener);
//	}
//
//	public void removeUserInfoUpdateListeners(
//			UserInfoUpdateListener userInfoUpdateListener) {
//		if (userInfoUpdateListeners.contains(userInfoUpdateListener)) {
//			userInfoUpdateListeners.remove(userInfoUpdateListener);
//		}
//	}


}
