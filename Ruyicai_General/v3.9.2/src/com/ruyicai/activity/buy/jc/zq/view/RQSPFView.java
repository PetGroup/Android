package com.ruyicai.activity.buy.jc.zq.view;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.widget.LinearLayout;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;

public class RQSPFView extends SPfView {

	public RQSPFView(Context context, BetAndGiftPojo betAndGift,
			Handler handler, LinearLayout layout, String type,
			boolean isdanguan, List<String> checkTeam) {
		super(context, betAndGift, handler, layout, type, isdanguan, checkTeam);
	}

	@Override
	public String getLotno() {
		return Constants.LOTNO_JCZQ_RQSPF;
	}

	@Override
	public String getTitle() {
		if (isDanguan) {
			return context.getString(R.string.jczq_rqspf_danguan_title)
					.toString();
		} else {
			return context.getString(R.string.jczq_rqspf_guoguan_title)
					.toString();
		}
	}

	public String getAlertCode(List<Info> listInfo) {
		String codeStr = "";
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);

			if (info.onclikNum > 0) {
				codeStr += info.getWeeks() + "  " + info.getTeamId() + "  ";
				codeStr += info.getHome() + " vs " + info.getAway()
						+ "\n让球胜平负:";
				if (info.isWin()) {
					codeStr += "胜";
				}
				if (info.isLevel()) {
					codeStr += "平";
				}
				if (info.isFail()) {
					codeStr += "负";
				}
				if (info.isDan()) {
					codeStr += "(胆)";
				}
				codeStr += "\n";
			}

		}
		return codeStr;
	}

	/**
	 * 获取倍率
	 */
	@Override
	public List<double[]> getOdds(List<Info> listInfo) {
		return footSpfCode.getOddsList(listInfo, isRQSPF);
	}
	
	@Override
	public String getPlayType() {
		if (isDanguan) {
			return "J00013_0";
		} else {
			return "J00013_1";
		}
	}
}
