package com.ruyicai.controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author wangw
 * @param <T>
 *
 */

public class BasicService<T> {

	/**存放回调接口的集合*/
	protected List<T> mListeners = new ArrayList<T>();

	/**线程池，默认2个*/
	protected ExecutorService mServiceThreadPool = Executors.newFixedThreadPool(2);
	
	/**
	 * 添加监听回调接口
	 * @param listener
	 */
	public void addListener(T listener){
		if(mListeners.contains(listener))
			return;
		mListeners.add(listener);
	}
	
	/**
	 * 移除监听回调接口
	 * @param listener
	 */
	public void removeListener(T listener){
		if(mListeners.contains(listener)){
			mListeners.remove(listener);
		}
	}
	
	
	
}
