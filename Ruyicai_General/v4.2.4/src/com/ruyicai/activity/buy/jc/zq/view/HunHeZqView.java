package com.ruyicai.activity.buy.jc.zq.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.JcCommonMethod;
import com.ruyicai.activity.buy.jc.JcMainActivity;
import com.ruyicai.activity.buy.jc.JcMainView;
import com.ruyicai.activity.buy.jc.oddsprize.JCPrizePermutationandCombination;
import com.ruyicai.activity.buy.jc.zq.view.BQCView.ViewOnClickListener;
import com.ruyicai.activity.common.CommonViewHolder;
import com.ruyicai.code.jc.zq.FootBF;
import com.ruyicai.code.jc.zq.FootHun;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.PublicMethod;
import com.umeng.analytics.MobclickAgent;

/**
 * 混合投注
 * 
 * @author win
 * 
 */
public class HunHeZqView extends JcMainView {

	protected int CHECK_TEAM = 8;// 最多串几场比赛
	FootHun footHunCode;

	public HunHeZqView(Context context, BetAndGiftPojo betAndGift,
			Handler handler, LinearLayout layout, String type,
			boolean isDanguan, List<String> checkTeam) {
		super(context, betAndGift, handler, layout, type, isDanguan, checkTeam);
		footHunCode = new FootHun(context);
	}

	public boolean isHunHe() {
		return true;
	}

	@Override
	public String getTitle() {
		return context.getString(R.string.jczq_hunhe_guoguan_title).toString();
	}

	public void setDifferValue(JSONObject jsonItem, Info itemInfo)
			throws JSONException {
		itemInfo.setLevel(jsonItem.getString("v1"));
		itemInfo.vStrs = new String[54];
		int rqspf_lenght = 3;
		int spf_lenght = 3;
		int bqc_lenght = 9;
		int zjq_lenght = 8;
		int bf_lenght = 31;
		for (int j = 0; j < bqc_lenght; j++) {
			itemInfo.getVStrs()[j + rqspf_lenght+spf_lenght] = jsonItem.getString("half_v"
					+ FootHun.bqcType[j + rqspf_lenght+spf_lenght]);
		}
		for (int j = 0; j < zjq_lenght; j++) {
			itemInfo.getVStrs()[j + rqspf_lenght + spf_lenght + bqc_lenght] = jsonItem
					.getString("goal_v" + j);
		}
		for (int j = 0; j < bf_lenght; j++) {
			itemInfo.getVStrs()[j + rqspf_lenght + spf_lenght + bqc_lenght + zjq_lenght] = jsonItem
					.getString("score_v"
							+ FootHun.bqcType[j + rqspf_lenght + spf_lenght + bqc_lenght
									+ zjq_lenght]);
		}
		initTitles(itemInfo);
	}

	private void initTitles(final Info info) {
		info.vStrs[0] = info.getWin();
		info.vStrs[1] = info.getLevel();
		info.vStrs[2] = info.getFail();
		
		info.vStrs[3] = info.getLetV3Win();
		info.vStrs[4] = info.getLetV1Level();
		info.vStrs[5] = info.getLetV0Fail();
	}

	/**
	 * 获取单关投注的中奖金额最大金额和最小金额的字符串
	 * 
	 * @return
	 */
	public String getDanPrizeString(int muti) {

		return JCPrizePermutationandCombination.getNewDanGuanPrize(
				getOddsArraysValue(), muti);
	}

	@Override
	public int getTeamNum() {
		return CHECK_TEAM;
	}

	@Override
	public void setTeamNum(int checkTeam) {
		CHECK_TEAM = checkTeam;
	}

	@Override
	public BaseExpandableListAdapter getAdapter() {
		return adapter;
	}

	@Override
	public String getLotno() {
		return Constants.LOTNO_JCZQ_HUN;
	}

	@Override
	public String getTypeTitle() {
		return context.getString(R.string.jczq_dialog_sfc_guoguan_title)
				.toString();
	}

	/**
	 * 
	 * 获取注码
	 * 
	 */
	public String getCode(String key, List<Info> listInfo) {

		return footHunCode.getCode(key, listInfo);
	}

	/**
	 * 
	 * 获取注码
	 * 
	 */
	public String getAlertCode(List<Info> listInfo) {
		String codeStr = "";
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);

			if (info.onclikNum > 0) {
				int first = 0;
				int second = 0;
				int third = 0;
				int fourth = 0;
				int fifth = 0;
				codeStr += PublicMethod.stringToHtml(info.getWeeks() + " " + info.getTeamId(), 
						Constants.JC_TOUZHU_TITLE_TEXT_COLOR) + " ";
				codeStr += (info.getHome() + " vs " + info.getAway());
				for (int j = 0; j < info.check.length; j++) {
					if (info.check[j].isChecked()) {
						/**add by yejc 20130730 start*/
						int position = info.check[j].getPosition(); 
						String title = info.check[j].getChcekTitle();
						if (position >= 0 && position <= 2) { // 胜平负
							first++;
							if (first == 1) {
								codeStr += "<br>胜平负：";
							}
						} else if (position >= 3 && position <= 5) { // 让球胜平负
							second++;
							if (second == 1) {
								codeStr += "<br>让球胜平负：";
							}
							title = "让" + title;
						} else if (position >= 6 && position <= 14) { // 半全场
							third++;
							if (third == 1) {
								codeStr += "<br>半全场：";
							}
						} else if (position >= 15 && position <= 22) { // 总进球
							fourth++;
							if (fourth == 1) {
								codeStr += "<br>总进球：";
							}
						} else if (position >= 23 && position <= 53) { // 比分
							fifth++;
							if (fifth == 1) {
								codeStr += "<br>比分：";
							}
						}
						codeStr += PublicMethod.stringToHtml(title, Constants.JC_TOUZHU_TEXT_COLOR) + "  ";
						/**add by yejc 20130730 end*/
					}
				}
				if (info.isDan()) {
					codeStr += PublicMethod.stringToHtml("(胆)", Constants.JC_TOUZHU_TEXT_COLOR) + "  ";
				}
				codeStr += "<br>";
			}
		}
		return codeStr;
	}

	/**
	 * 初始化列表
	 */
	public void initListView(ExpandableListView listview, Context context,
			List<List> listInfo) {
		adapter = new JcInfoExpandableListAdapter(context, listInfo);
		listview.setAdapter(adapter);
	}
	
	public class JcInfoExpandableListAdapter extends BaseExpandableListAdapter {
		private LayoutInflater mInflater; // 扩充主列表布局
		private List<List> mList;
		public JcInfoExpandableListAdapter(Context context, List<List> list) {
			mInflater = LayoutInflater.from(context);
			mList = list;
		}

		@Override
		public int getGroupCount() {
			if (mList == null) {
				return 0;
			}
			return mList.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			ArrayList<Info> list = (ArrayList<Info>) mList.get(groupPosition);
			if (list == null) {
				return 0;
			}
			return list.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return mList.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			ArrayList<Info> list = (ArrayList<Info>) mList.get(groupPosition);
			if (list == null) {
				return null;
			}
			return list.get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			return getConvertView(groupPosition, isExpanded, convertView, mList, mInflater);
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			CommonViewHolder.ChildViewHolder holder = null;
			final ArrayList<Info> list = (ArrayList<Info>) mList.get(groupPosition);
			final Info info = list.get(childPosition);
			if (convertView == null) {
				holder = new CommonViewHolder.ChildViewHolder();
				convertView = mInflater.inflate(
						R.layout.buy_jc_main_listview_item_others, null);
				holder = JcCommonMethod.initChildViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (CommonViewHolder.ChildViewHolder) convertView.getTag();
			}
			ViewOnClickListener listener = new ViewOnClickListener(info, groupPosition, childPosition);
			holder.btnShowDetail.setOnClickListener(listener);
			holder.analysis.setOnClickListener(listener);
			JcCommonMethod.setDividerShowState(childPosition, holder);
			JcCommonMethod.setTeamTime(info, holder);
			JcCommonMethod.setJcZqTeamName(info, holder);
			JcCommonMethod.setBtnText(info, holder);
			holder.btnDan.setVisibility(Button.GONE);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
	}
	
	public class ViewOnClickListener implements View.OnClickListener {
		private Info info;
		private int groupPosition;
		private int childPosition;
		public ViewOnClickListener(Info info, int groupPosition, int childPosition) {
			this.info = info;
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.jc_main_list_item_button:
				showDetail();
				MobclickAgent.onEvent(context, "jczqhunhe_duizhenxiangqing");
				break;
				
			case R.id.game_analysis:
				trunExplain(getEvent(Constants.JCFOOT, info),
						info.getHome(), info.getAway());
				MobclickAgent.onEvent(context, "jczqhunhe_fenxi");
				break;
			}
		}
		
		private void showDetail() {
			if (info.onclikNum > 0 || isCheckTeam()) {
				info.setHunheZQ(true);
				info.createDialog(FootHun.titleStrs, true,
						info.getHome() + " VS " + info.getAway());
				mPosition = groupPosition;
				mIndex = childPosition;
				View view = info.getViewType();
				TextView tv = (TextView)view.findViewById(R.id.lq_rqspf_dialog_textview);
				tv.setText("主"+info.getLetPoint());
			}
		}
	}

	@Override
	public String getPlayType() {
		return "playtype"; //这里返回只要不为""并且不与其他玩法重复即可
	}

	@Override
	public List<double[]> getOdds(List<Info> listInfo) {
		return footHunCode.getOddsList(listInfo);
	}

	public List<double[]> getMinOdds(List<Info> listInfo) {
		return footHunCode.getMinOddsList(listInfo);
	}

	/**
	 * 最多可以设胆7场比赛
	 */
	public boolean isDanCheckTeam() {
		return false;
	}

}
