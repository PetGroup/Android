package com.ruyicai.controller.service;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;
import com.ruyicai.activity.buy.guess.util.RuyiGuessConstant;
import com.ruyicai.controller.listerner.GuessListener;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.model.RuyiGuessGatherBean;
import com.ruyicai.net.newtransaction.RuyiGuessInterface;
import com.ruyicai.util.json.JsonUtils;

/**
 * 竞彩Server
 * @author wangw
 *
 */

@Singleton
public class GuessService extends BasicService<GuessListener> {

	
	/**
	 * 创建扎堆
	 * @param userno
	 * @param bean
	 */
	public void createGroup(final String userno,final RuyiGuessGatherBean bean){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String result = RuyiGuessInterface.getInstance().sendCreateGroup(RuyiGuessConstant.CREATE, userno, bean);
				ReturnBean bean = JsonUtils.resultData(result, ReturnBean.class);
				onCreateGroup(bean);
			}
		});
		mServiceThreadPool.execute(thread);
	}
	
	/**
	 * 通知回调接口
	 * @param bean
	 */
	private void onCreateGroup(ReturnBean bean){
		for (GuessListener listener : mListeners) {
			listener.onCreateGroupCallback(bean);
		}
	}
	
	/**
	 * 获取竞猜题目列表
	 * @param pageIndex
	 * @param maxResult
	 * @param userno
	 * @param type
	 * @param cmd
	 * @param state
	 */
	public void getGuessSubjectList(final int pageIndex, 
			final String maxResult,final String userno,final String type,final int cmd,final String state){
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String result = RuyiGuessInterface.getInstance().getRuyiGuessSubjectList(Integer.toString(pageIndex), 
						maxResult, userno, type,state);
				onGetGuessSubjectList(result,cmd);
			}
		});
		mServiceThreadPool.execute(thread);
	}
	
	/**
	 * 获取竞猜列表回调接口
	 * @param result
	 */
	private void onGetGuessSubjectList(String result,int cmd){
		for (GuessListener listener : mListeners) {
			listener.onGetGuessSubjectCallback(result,cmd);
		}
	}
	
	
}
