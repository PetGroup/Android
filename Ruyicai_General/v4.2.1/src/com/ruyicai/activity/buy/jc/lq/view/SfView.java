package com.ruyicai.activity.buy.jc.lq.view;

import java.util.ArrayList;
import java.util.List;
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
import android.widget.TextView;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.JcCommonMethod;
import com.ruyicai.activity.buy.jc.JcMainView;
import com.ruyicai.activity.buy.jc.explain.lq.JcLqExplainActivity;
import com.ruyicai.activity.common.CommonViewHolder;
import com.ruyicai.code.jc.lq.BasketSF;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.PublicMethod;

/**
 * 胜负类
 * 
 * @author Administrator
 * 
 */
public class SfView extends JcMainView {
	private final int MAX_TEAM = 8;
	protected BasketSF basketSf;
	protected JcInfoExpandableListAdapter adapter;
	protected static final int B_SF = 0;
	public static final int B_RF = 1;
	public static final int B_DX = 2;

	public SfView(Context context, BetAndGiftPojo betAndGift, Handler handler,
			LinearLayout layout, String type, boolean isdanguan,
			List<String> checkTeam) {
		super(context, betAndGift, handler, layout, type, isdanguan, checkTeam);
		basketSf = new BasketSF(context);
	}

	@Override
	public String getLotno() {
		return Constants.LOTNO_JCLQ;
	}

	@Override
	public BaseExpandableListAdapter getAdapter() {
		return adapter;
	}

	@Override
	public String getTitle() {
		if (isDanguan) {
			return context.getString(R.string.jclq_sf_danguan_title).toString();
		} else {
			return context.getString(R.string.jclq_sf_guoguan_title).toString();
		}
	}

	@Override
	public String getTypeTitle() {
		return context.getString(R.string.jclq_dialog_sf_guoguan_title)
				.toString();
	}

	@Override
	public String getCode(String key, List<Info> listInfo) {
		return basketSf.getCode(key, listInfo);
	}

	@Override
	public String getAlertCode(List<Info> listInfo) {
		String codeStr = "";
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);
			if (info.onclikNum > 0) {
				codeStr += PublicMethod.stringToHtml(info.getWeeks() + " " + info.getTeamId(), 
						Constants.JC_TOUZHU_TITLE_TEXT_COLOR) + "  ";
				if (Constants.LOTNO_JCLQ_RF.equals(getLotno())) {
					if (!"".equals(info.getLetPoint())) {
						codeStr += info.getAway() + " (" +info.getLetPoint() + ") " + info.getHome() + "(主)"+"<br>让分胜负：";
					} else {
						codeStr += info.getAway() + " vs " + info.getHome() + "(主)"+"<br>胜负：";
					}
					if (info.isWin()) {
						codeStr += PublicMethod.stringToHtml("主胜SP"+info.getLetWin(), Constants.JC_TOUZHU_TEXT_COLOR) + "  ";
					}
					if (info.isFail()) {
						codeStr += PublicMethod.stringToHtml("主负SP"+info.getLetFail(), Constants.JC_TOUZHU_TEXT_COLOR) + "  ";
					}
				} else {
					codeStr += info.getAway() + " vs " + info.getHome() + "(主)"+"<br>胜负：";
					if (info.isWin()) {
						codeStr += PublicMethod.stringToHtml("主胜SP"+info.getWin(), Constants.JC_TOUZHU_TEXT_COLOR) + "  ";
					}
					if (info.isFail()) {
						codeStr += PublicMethod.stringToHtml("主负SP"+info.getFail(), Constants.JC_TOUZHU_TEXT_COLOR) + "  ";
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

	@Override
	public int getTeamNum() {
		return MAX_TEAM;
	}

	/**
	 * 初始化列表
	 */
	public void initListView(ExpandableListView listview, Context context,
			List<List> listInfo) {
		adapter = new JcInfoExpandableListAdapter(context, listInfo, B_SF);
		listview.setAdapter(adapter);
	}

	/**
	 * 竞彩的适配器
	 */
	public class JcInfoExpandableListAdapter extends BaseExpandableListAdapter {
		private LayoutInflater mInflater; // 扩充主列表布局
		private List<List> mList;
		private int type;
		
		public JcInfoExpandableListAdapter(Context context, List<List> list, int type) {
			mInflater = LayoutInflater.from(context);
			mList = list;
			this.type = type;
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
			ArrayList<Info> list = (ArrayList<Info>) mList.get(groupPosition);
			final Info info = list.get(childPosition);
			if (convertView == null) {
				holder = new CommonViewHolder.ChildViewHolder();
				convertView = mInflater.inflate(
						R.layout.buy_jc_main_listview_item_other, null);
				holder = JcCommonMethod.initChildViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (CommonViewHolder.ChildViewHolder) convertView.getTag();
			}
			ViewOnClickListener listener = new ViewOnClickListener(holder, info, type);
			holder.guestLayout.setOnClickListener(listener);
			holder.homeLayout.setOnClickListener(listener);
			holder.analysis.setOnClickListener(listener);
			
			JcCommonMethod.setDividerShowState(childPosition, holder);
			JcCommonMethod.setTeamTime(info, holder);
			JcCommonMethod.setJcLqTeamName(info, holder);
			setOddsShowState(holder, info);
			setDanShowState(info, holder);
			setBackground(holder.guestLayout, holder.guestTeam, holder.guestOdds, info.isWin(), type);
			setBackground(holder.homeLayout, holder.homeTeam, holder.homeOdds, info.isFail(), type);
			holder.vsLayout.setBackgroundResource(android.R.color.transparent);
			if (isDanguan) {
				holder.btnDan.setVisibility(Button.GONE);
			} else {
				holder.btnDan.setVisibility(Button.VISIBLE);
				holder.btnDan.setOnClickListener(listener);
			}
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		
		private void setOddsShowState(CommonViewHolder.ChildViewHolder holder, Info info) {
			if(type == B_DX) {
				holder.homeOdds.setText("大" + info.getBig());
				holder.guestOdds.setText("小" + info.getSmall());
				holder.textOdds.setText(info.getBasePoint());
				holder.textOdds.setTextColor(red);
				holder.textVS.setVisibility(View.GONE);
			} else if(type == B_RF) {
				holder.guestOdds.setText(info.getLetWin());
				holder.homeOdds.setText(info.getLetFail());
				String letPoint = info.getLetPoint();
				if (letPoint != null && letPoint.length()>0) {
					holder.textOdds.setText(letPoint);
					setOddsColor(holder.textOdds);
				}
				holder.textVS.setVisibility(View.GONE);
			} else {
				holder.guestOdds.setText(info.getWin());
				holder.homeOdds.setText(info.getFail());
				holder.textVS.setBackgroundResource(android.R.color.transparent);
				holder.textVS.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT,
						LinearLayout.LayoutParams.FILL_PARENT));
				holder.textOdds.setVisibility(View.GONE);
			}
		}
	}
	
	public class ViewOnClickListener implements View.OnClickListener {
		private CommonViewHolder.ChildViewHolder holder;
		private Info info;
		private int type;
		public ViewOnClickListener(CommonViewHolder.ChildViewHolder holder, Info info, int type) {
			this.holder = holder;
			this.info = info;
			this.type = type;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_layout:
				setHomeLayoutShowState();
				break;

			case R.id.guest_layout:
				setGuestLayoutShowState();
				break;
				
			case R.id.game_dan:
				setGameDanShowState(info, holder);
				break;
				
			case R.id.game_analysis:
				trunExplain(getEvent(Constants.JCBASKET, info));
				break;
			}
		}
		
		private void setGuestLayoutShowState() {
			if (info.onclikNum > 0 || isCheckTeam()) {
				info.setWin(!info.isWin());
				if (info.isWin()) {
					info.onclikNum++;
				} else {
					info.onclikNum--;
				}
				setBackground(holder.guestLayout, holder.guestTeam, holder.guestOdds, info.isWin(), type);
				isNoDan(info, holder.btnDan);
				setTeamNum();
			}
		}
		
		private void setHomeLayoutShowState() {
			if (info.onclikNum > 0 || isCheckTeam()) {
				info.setFail(!info.isFail());
				if (info.isFail()) {
					info.onclikNum++;
				} else {
					info.onclikNum--;
				}
				setBackground(holder.homeLayout, holder.homeTeam, holder.homeOdds, info.isFail(), type);
				isNoDan(info, holder.btnDan);
				setTeamNum();
			}
		}
	}
	
	private void setBackground(LinearLayout layout, TextView team,
			TextView odds, boolean flag, int type) {
		if (flag) {
			layout.setBackgroundResource(R.drawable.team_name_bj_yellow);
			team.setBackgroundResource(R.drawable.team_name_bj_top_yellow);
			team.setTextColor(white);
			odds.setTextColor(white);
		} else {
			layout.setBackgroundResource(android.R.color.transparent);
			team.setBackgroundResource(android.R.color.transparent);
			team.setTextColor(black);
			if (type == B_DX) {
				odds.setTextColor(red);
			} else if (type == B_RF){
				setOddsColor(odds);
			} else {
				odds.setTextColor(oddsColor);
			}
		}
	}

	/**
	 * 跳转到分析界面
	 */
	public void trunExplain(String event) {
		JcLqExplainActivity.Lq_TYPE = JcLqExplainActivity.LQ_SF;
		Intent intent = new Intent(context, JcLqExplainActivity.class);
		intent.putExtra("event", event);
		context.startActivity(intent);
	}

	@Override
	public String getPlayType() {
		if (isDanguan) {
			return "J00005_0";
		} else {
			return "J00005_1";
		}
	}

	@Override
	public List<double[]> getOdds(List<Info> listInfo) {
		return basketSf.getOddsList(listInfo, B_SF);
	}
	
	private void setOddsColor(TextView tv) {
		String text = tv.getText().toString();
		if (text != null && text.length() != 0) {
			if ("-".equals(text.substring(0, 1))) {
				tv.setTextColor(red);
			} else if ("+".equals(text.substring(0, 1))) {
				tv.setTextColor(green);
			} else {
				tv.setTextColor(oddsColor);
			}
		}
	}

}
