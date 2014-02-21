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
import com.ruyicai.code.jc.lq.BasketSFC;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.PublicMethod;

/**
 * 胜分差类
 * 
 * @author Administrator
 * 
 */
public class SfcView extends JcMainView {
	private final int MAX_TEAM = 4;
	BasketSFC basketSfcCode;

	public SfcView(Context context, BetAndGiftPojo betAndGift, Handler handler,
			LinearLayout layout, String type, boolean isdanguan,
			List<String> checkTeam) {
		super(context, betAndGift, handler, layout, type, isdanguan, checkTeam);
		basketSfcCode = new BasketSFC(context);
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

	public void setDifferValue(JSONObject jsonItem, Info itemInfo)
			throws JSONException {
		// 胜分差
		itemInfo.vStrs = new String[12];
		for (int n = 0; n < 12; n++) {
			itemInfo.vStrs[n] = jsonItem.getString("v" + BasketSFC.bqcType[n]);
		}
	}

	@Override
	public int getTeamNum() {
		return MAX_TEAM;
	}

	@Override
	public String getLotno() {
		return Constants.LOTNO_JCLQ_SFC;
	}

	@Override
	public BaseExpandableListAdapter getAdapter() {
		return adapter;
	}

	@Override
	public String getTitle() {
		if (isDanguan) {
			return context.getString(R.string.jclq_sfc_danguan_title)
					.toString();
		} else {
			return context.getString(R.string.jclq_sfc_guoguan_title)
					.toString();
		}
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
		return basketSfcCode.getCode(key, listInfo);
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
						Constants.JC_TOUZHU_TITLE_TEXT_COLOR) + "  ";
				codeStr += info.getAway() + " vs " + info.getHome()+"(主)" + "<br>胜分差：";
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
			
			ViewOnClickListener listener = new ViewOnClickListener(holder, info);
			holder.btnShowDetail.setOnClickListener(listener);
			holder.analysis.setOnClickListener(listener);
			JcCommonMethod.setDividerShowState(childPosition, holder);
			JcCommonMethod.setTeamTime(info, holder);
			JcCommonMethod.setJcLqTeamName(info, holder);
			JcCommonMethod.setBtnText(info, holder);
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
		public ViewOnClickListener(CommonViewHolder.ChildViewHolder holder, Info info) {
			this.holder = holder;
			this.info = info;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.jc_main_list_item_button:
				showDetail();
				break;
				
			case R.id.game_dan:
				setGameDanShowState(info, holder);
				break;
				
			case R.id.game_analysis:
				trunExplain(getEvent(Constants.JCBASKET, info));
				break;
			}
		}
		
		private void showDetail() {
			if (info.onclikNum > 0 || isCheckTeam()) {
				info.setLotno(Constants.LOTNO_JCLQ_SFC);
				info.createDialog(BasketSFC.titleStrs, false,
						info.getAway() + " VS " + info.getHome());
			}
			isNoDan(info, holder.btnDan);
		}
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
		if (isDanguan) {
			return "J00007_0";
		} else {
			return "J00007_1";
		}
	}

	@Override
	public List<double[]> getOdds(List<Info> listInfo) {
		return basketSfcCode.getOddsList(listInfo);
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