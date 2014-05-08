package com.ruyicai.controller.listerner;

import com.ruyicai.model.ReturnBean;

/**
 * 竞彩Server回调接口
 * @author wangw
 *
 */
public interface GuessListener {

	/**
	 * 创建扎堆回调接口
	 * @param bean
	 */
	public void onCreateGroupCallback(ReturnBean bean);
	
	/**
	 * 获取竞猜题目列表
	 * @param result
	 * @param cmd
	 */
	public void onGetGuessSubjectCallback(String result,int cmd);
}
