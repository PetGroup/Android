package com.ruyicai.controller.listerner;

import com.ruyicai.model.PrizeInfoList;

public interface LotteryListener {

	public void updateLatestLotteryList(String lotno);
    public void updateNoticePrizeInfo(String lotno,PrizeInfoList prizeInfoList);
}
