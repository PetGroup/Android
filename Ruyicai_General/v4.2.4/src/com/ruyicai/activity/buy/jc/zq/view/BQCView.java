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
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.JcCommonMethod;
import com.ruyicai.activity.buy.jc.JcMainView;
import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.activity.common.CommonViewHolder;
import com.ruyicai.code.jc.zq.FootBQC;
import com.ruyicai.constant.Constants;
import com.ruyicai.custom.checkbox.MyCheckBox;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.PublicMethod;
import com.umeng.analytics.MobclickAgent;

/**
 * 半全场
 * 
 * @author Administrator
 * 
 */
public class BQCView extends JcMainView {
	private final int MAX_TEAM = 4;
	FootBQC footbqcCode;

	public BQCView(Context context, BetAndGiftPojo betAndGift, Handler handler,
			LinearLayout layout, String type, boolean isdanguan,
			List<String> checkTeam) {
		super(context, betAndGift, handler, layout, type, isdanguan, checkTeam);
		footbqcCode = new FootBQC(context);
		
	}

	public void setDifferValue(JSONObject jsonItem, Info itemInfo)
			throws JSONException {
		itemInfo.vStrs = new String[9];
		for (int j = 0; j < 9; j++) {
			itemInfo.getVStrs()[j] = jsonItem.getString("half_v"
					+ FootBQC.bqcType[j]);
		}
	}

	@Override
	public int getTeamNum() {
		return MAX_TEAM;
	}

	@Override
	public String getLotno() {
		return Constants.LOTNO_JCZQ_BQC;
	}

	@Override
	public BaseExpandableListAdapter getAdapter() {
		return adapter;
	}

	@Override
	public String getTitle() {
		if (isDanguan) {
			return context.getString(R.string.jczq_dxf_danguan_title)
					.toString();
		} else {
			return context.getString(R.string.jczq_dxf_guoguan_title)
					.toString();
		}
	}

	@Override
	public String getTypeTitle() {
		return context.getString(R.string.jczq_dialog_dxf_guoguan_title)
				.toString();
	}

	/**
	 * 
	 * 获取注码
	 * 
	 */
	public String getCode(String key, List<Info> listInfo) {
		return footbqcCode.getCode(key, listInfo);
	}

	/**
	 * 
	 * 获取投注框提示注码
	 * 
	 */
	public String getAlertCode(List<Info> listInfo) {
		String codeStr = "";
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);
			if (info.onclikNum > 0) {
				codeStr += PublicMethod.stringToHtml(info.getWeeks() + " " + info.getTeamId(), 
						Constants.JC_TOUZHU_TITLE_TEXT_COLOR) + " ";
				codeStr += info.getHome() + " vs " + info.getAway() + "<br>半全场：";
				for (int j = 0; j < info.check.length; j++) {
					if (info.check[j].isChecked()) {
						codeStr += PublicMethod.stringToHtml(info.check[j].getChcekTitle(), Constants.JC_TOUZHU_TEXT_COLOR) + "  ";
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
			info.detailBtn = holder.btnShowDetail;
			ViewOnClickListener listener = new ViewOnClickListener(holder, info, childPosition);
			holder.btnShowDetail.setOnClickListener(listener);
			holder.analysis.setOnClickListener(listener);
			JcCommonMethod.setDividerShowState(childPosition, holder);
			JcCommonMethod.setTeamTime(info, holder);
			JcCommonMethod.setJcZqTeamName(info, holder);
			JcCommonMethod.setBtnText(info, holder);
			setDetailLayoutState(holder, childPosition, info, FootBQC.titleStrs);
			setDanShowState(info, holder);
			if (isDanguan || isHunHe()) {
				holder.btnDan.setVisibility(Button.GONE);
			} else {
				holder.btnDan.setVisibility(Button.VISIBLE);
				holder.btnDan.setOnClickListener(listener);
			}
			
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
	}
	
	public class ViewOnClickListener implements View.OnClickListener {
		private CommonViewHolder.ChildViewHolder holder;
		private Info info;
		private int childPosition;
		public ViewOnClickListener(CommonViewHolder.ChildViewHolder holder, 
				Info info, int childPosition) {
			this.holder = holder;
			this.info = info;
			this.childPosition = childPosition;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.jc_main_list_item_button:
				showDetail();
				MobclickAgent.onEvent(context, "jczqbanquanchang_duizhenxiangqing");
				break;
				
			case R.id.game_dan:
				setGameDanShowState(info, holder);
				MobclickAgent.onEvent(context, "jczqbanquanchang_dan");
				break;
				
			case R.id.game_analysis:
				trunExplain(getEvent(Constants.JCFOOT, info),
						info.getHome(), info.getAway());
				MobclickAgent.onEvent(context, "jczqbanquanchang_fenxi");
				break;
			}
		}
		
		private void showDetail() {
			if (info.detailView == null) {
				info.detailView = mFactory
						.inflate(R.layout.buy_jc_zq_bqc_layout, null);
				info.setJqsLayout(FootBQC.titleStrs, info.detailView, new MyHandler(info));
			}
			info.setExpanded(!info.isExpanded());
			setDetailLayoutState(holder, childPosition, info, FootBQC.titleStrs);
			isNoDan(info, holder.btnDan);
		}
	}
	

	@Override
	public String getPlayType() {
		if (isDanguan) {
			return "J00004_0";
		} else {
			return "J00004_1";
		}
	}

	@Override
	public List<double[]> getOdds(List<Info> listInfo) {
		return footbqcCode.getOddsList(listInfo);
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
