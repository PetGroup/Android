package com.ruyicai.controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.listerner.LotteryListener;
import com.ruyicai.model.PrizeInfoBean;
import com.ruyicai.model.PrizeInfoList;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.net.newtransaction.PrizeInfoInterface;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;

@Singleton
public class LotteryService {

	private final List<LotteryListener> lotteryListeners = new ArrayList<LotteryListener>();
	private ExecutorService lotteryServiceThreadPool = Executors
			.newFixedThreadPool(2);
	// 获取历史开奖信息-第几页
	private int pageIndex = 1;
	// 获取历史开奖信息-每页显示的条数
	private int maxResult = 10;

	/**
	 * 获得指定采种最近10期开奖金号码列表
	 * 
	 * @param lotteryLotNo
	 */
	public void getNoticePrizeInfoList(final String lotteryLotNo) {
		final PrizeInfoList prizeInfoList = new PrizeInfoList();
		Thread prizeInfothread = new Thread(new Runnable() {
			public void run() {

				String result = PrizeInfoInterface.getInstance()
						.getNoticePrizeDetil(lotteryLotNo,
								String.valueOf(pageIndex),
								String.valueOf(maxResult));
				try {
					ReturnBean returnBean = JsonUtils.resultData(result,
							ReturnBean.class);
					prizeInfoList.setReturnBean(returnBean);
					if (returnBean != null
							&& !Constants.SUCCESS_CODE.equals(returnBean
									.getError_code())) {
						updateNoticePrizeInfo(lotteryLotNo, prizeInfoList);
						return;
					}
					ArrayList<PrizeInfoBean> subPrizeInfoList = (ArrayList<PrizeInfoBean>) JsonUtils.getList(
							returnBean.getResult(), PrizeInfoBean.class);
					prizeInfoList.setPrizeInfoList(subPrizeInfoList);
					updateNoticePrizeInfo(lotteryLotNo, prizeInfoList);
				} catch (Exception e) {
					PublicMethod.outLog("LotteryService", e.getMessage());
				}
			}
		});
		lotteryServiceThreadPool.execute(prizeInfothread);
	}

	/**
	 * 历史开奖更新
	 * 
	 * @param lotno
	 * @param prizeInfoList
	 */
	public void updateNoticePrizeInfo(String lotno, PrizeInfoList prizeInfoList) {
		for (LotteryListener lotteryListener : lotteryListeners) {
			lotteryListener.updateNoticePrizeInfo(lotno, prizeInfoList);
		}
	}

	/**
	 * 最新开奖号码更新
	 * 
	 * @param lotno
	 */
	public void updateLatestLotteryList(String lotno) {
		for (LotteryListener lotteryListener : lotteryListeners) {
			lotteryListener.updateLatestLotteryList(lotno);
		}
	}

	public void addLotteryListeners(LotteryListener LotteryListener) {
		if (lotteryListeners.contains(LotteryListener)) {
			return;
		}
		lotteryListeners.add(LotteryListener);
	}

	public void removeLotteryListeners(LotteryListener LotteryListener) {
		if (lotteryListeners.contains(LotteryListener)) {
			lotteryListeners.remove(LotteryListener);
		}
	}

}
