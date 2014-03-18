package com.ruyicai.activity.buy.jc.lq.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.JcCommonMethod;
import com.ruyicai.activity.buy.jc.JcMainView;
import com.ruyicai.activity.buy.jc.explain.lq.JcLqExplainActivity;
import com.ruyicai.activity.buy.jc.oddsprize.JCPrizePermutationandCombination;
import com.ruyicai.activity.common.CommonViewHolder;
import com.ruyicai.code.jc.lq.BasketHun;
import com.ruyicai.code.jc.lq.BasketSFC;
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
public class HunHeLqView extends JcMainView {
	private int MAX_TEAM = 8; // 最多串几场比赛
	BasketHun basketHun;

	public HunHeLqView(Context context, BetAndGiftPojo betAndGift,
			Handler handler, LinearLayout layout, String type,
			boolean isDanguan, List<String> checkTeam) {
		super(context, betAndGift, handler, layout, type, isDanguan, checkTeam);
		basketHun = new BasketHun(context);
	}

	public void setDifferValue(JSONObject jsonItem, Info itemInfo)
			throws JSONException {
		// 大小分
		itemInfo.setBig(jsonItem.getString("g"));
		itemInfo.setSmall(jsonItem.getString("l"));
		itemInfo.setBasePoint(jsonItem.getString("basePoint"));
		// 让分胜负
		itemInfo.setLetFail(jsonItem.getString("letVs_v0"));
		itemInfo.setLetWin(jsonItem.getString("letVs_v3"));
		itemInfo.setLetPoint(jsonItem.getString("letPoint"));

		itemInfo.vStrs = new String[20];
		int before_lenght = 8;
		int sfc_lenght = 12;
		for (int i = 0; i < before_lenght; i++) {
			itemInfo.vStrs[i] = "";
		}
		for (int n = 0; n < sfc_lenght; n++) {
			itemInfo.vStrs[n + before_lenght] = jsonItem.getString("v"
					+ BasketSFC.bqcType[n]);
		}
		initTitles(itemInfo);
	}

	private void initTitles(final Info info) {
		/**modify by pengcx 20130801 start*/
		info.vStrs[0] = info.getFail();
		info.vStrs[1] = info.getWin();
		info.vStrs[2] = info.getLetFail();
		info.vStrs[3] = info.getLetPoint();
		info.vStrs[4] = info.getLetWin();
		info.vStrs[5] = info.getBig();
		info.vStrs[6] = info.getBasePoint();
		info.vStrs[7] = info.getSmall();
		/**modify by pengcx 20130801 end*/
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
		return MAX_TEAM;
	}
	
	@Override
	public void setTeamNum(int checkTeam) {
		MAX_TEAM = checkTeam;
	}

	@Override
	public String getLotno() {
		return Constants.LOTNO_JCLQ_HUN;
	}

	@Override
	public BaseExpandableListAdapter getAdapter() {
		return adapter;
	}

	@Override
	public String getTitle() {
		return context.getString(R.string.jclq_hunhe_guoguan_title).toString();
	}

	@Override
	public String getTypeTitle() {
		return context.getString(R.string.jclq_dialog_sfc_guoguan_title)
				.toString();
	}

	/**
	 * 
	 * 获取注码
	 * 
	 */
	public String getCode(String key, List<Info> listInfo) {
		return basketHun.getCode(key, listInfo);
	}

	/**
	 * 
	 * 获取投注框提示注码
	 * 
	 */
	public String getAlertCode(List<Info> listInfo) {
		String codeStr = "";
		String spStr = "";
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);
			int first = 0;
			int second = 0;
			int third = 0;
			int fourth = 0;
			if (info.onclikNum > 0) {
				codeStr += PublicMethod.stringToHtml(info.getWeeks() + " " + info.getTeamId(), 
						Constants.JC_TOUZHU_TITLE_TEXT_COLOR) + "  ";
				codeStr += info.getAway() + " vs " + info.getHome() + "(主)";
				for (int j = 0; j < info.check.length; j++) {
					if (info.check[j].isChecked()) {
						int position = info.check[j].getPosition(); 
						if (position >= 0 && position <= 1) { // 胜负
							first++;
							if (first == 1) {
								codeStr += "<br>胜负：";
							}
							spStr = "SP";
						} else if (position >= 2 && position <= 4) { // 让分胜负
							second++;
							if (second == 1) {
								codeStr += "<br>让分胜负：";
							}
							spStr = "SP";
						} else if (position >= 5 && position <= 7) { // 大小分
							third++;
							if (third == 1) {
								codeStr += "<br>大小分：";
							}
							spStr = "SP";
						} else if (position >= 8 && position <= 19) { // 胜分差
							fourth++;
							if (fourth == 1) {
								codeStr += "<br>胜分差：";
							}
							spStr = "|";
						}
						codeStr += PublicMethod.stringToHtml(info.check[j].getChcekTitle()+spStr+info.check[j].getCheckText(), Constants.JC_TOUZHU_TEXT_COLOR) + "  ";
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
		adapter.notifyDataSetChanged();
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
			holder.btnDan.setVisibility(Button.GONE);
			JcCommonMethod.setDividerShowState(childPosition, holder);
			JcCommonMethod.setTeamTime(info, holder);
			JcCommonMethod.setJcLqTeamName(info, holder);
			JcCommonMethod.setBtnText(info, holder);
			info.setLq(true);
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
				MobclickAgent.onEvent(context, "jclahunhe_duizhenxingqing");
				break;
				
			case R.id.game_analysis:
				trunExplain(getEvent(Constants.JCBASKET, info));
				MobclickAgent.onEvent(context, "jclqhunhe_fenxibutton");
				break;
			}
		}
		
		private void showDetail() {
			if (info.onclikNum > 0 || isCheckTeam()) {
				info.createDialog(BasketHun.titleStrs, false,
						info.getAway() + " VS " + info.getHome());
				mPosition = groupPosition;
				mIndex = childPosition;
			}
		}
	}

	public boolean isHunHe() {
		return true;
	}

	/**
	 * 跳转到分析界面
	 */
	public void trunExplain(String event) {
		JcLqExplainActivity.Lq_TYPE = JcLqExplainActivity.LQ_SFC;
		Intent intent = new Intent(context, JcLqExplainActivity.class);
		intent.putExtra("event", event);
		context.startActivity(intent);
	}

	@Override
	public String getPlayType() {
		/**close by yejc 20130801 start*/
//		if (isDanguan) {
//			return "J00007_0";
//		} else {
//			return "J00007_1";
//		}
		return "playtype"; //这里返回只要不为""并且不与其他玩法重复即可
		/**close by yejc 20130801 end*/
	}
	

	@Override
	public List<double[]> getOdds(List<Info> listInfo) {
		return basketHun.getOddsList(listInfo);
	}

	public List<double[]> getMinOdds(List<Info> listInfo) {
		return basketHun.getMinOddsList(listInfo);
	}

	/**
	 * 最多可以设胆7场比赛
	 */
	public boolean isDanCheckTeam() {
		int teamNum = initDanCheckedNum();
		if (teamNum < MAX_TEAM - 1) {
			return true;
		} else {
			Toast.makeText(context, "您最多可以选择" + (MAX_TEAM - 1) + "场比赛进行设胆！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

}
