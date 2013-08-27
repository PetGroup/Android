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
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.JcMainActivity;
import com.ruyicai.activity.buy.jc.JcMainView;
import com.ruyicai.activity.buy.jc.explain.lq.JcLqExplainActivity;
import com.ruyicai.activity.buy.jc.oddsprize.JCPrizePermutationandCombination;
import com.ruyicai.code.jc.lq.BasketHun;
import com.ruyicai.code.jc.lq.BasketSFC;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.PublicMethod;

/**
 * 混合投注
 * 
 * @author win
 * 
 */
public class HunHeLqView extends JcMainView {
	private int MAX_TEAM = 8; // 最多串几场比赛
	JcInfoAdapter adapter;
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
	public BaseAdapter getAdapter() {
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
						} else if (position >= 2 && position <= 4) { // 让分胜负
							second++;
							if (second == 1) {
								codeStr += "<br>让分胜负：";
							}
						} else if (position >= 5 && position <= 7) { // 大小分
							third++;
							if (third == 1) {
								codeStr += "<br>大小分：";
							}
						} else if (position >= 8 && position <= 19) { // 胜分差
							fourth++;
							if (fourth == 1) {
								codeStr += "<br>胜分差：";
							}
						}
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
	public void initListView(ListView listview, Context context,
			List<List> listInfo) {
		adapter = new JcInfoAdapter(context, listInfo);
		adapter.notifyDataSetChanged();
		listview.setAdapter(adapter);
	}

	/**
	 * 竞彩的适配器
	 */
	public class JcInfoAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // 扩充主列表布局
		private List<List> mList;

		public JcInfoAdapter(Context context, List<List> list) {
			mInflater = LayoutInflater.from(context);
			mList = list;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ArrayList<Info> list = (ArrayList<Info>) mList.get(position);
			convertView = mInflater.inflate(
					R.layout.buy_jc_main_view_list_item, null);
			final ViewHolder holder = new ViewHolder();
			holder.btn = (Button) convertView
					.findViewById(R.id.buy_jc_main_view_list_item_btn);
			holder.layout = (LinearLayout) convertView
					.findViewById(R.id.buy_jc_main_view_list_item_linearLayout);
			holder.btn.setBackgroundResource(R.drawable.buy_jc_item_btn_close);

			if (list.size() == 0) {
				holder.btn.setVisibility(Button.GONE);
			} else {
				isOpen(list, holder);
				holder.btn.setText(list.get(0).getTime() + "  " + list.size()
						+ context.getString(R.string.jc_main_btn_text));
				holder.btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						list.get(0).isOpen = !list.get(0).isOpen;
						isOpen(list, holder);
					}
				});
				for (int i = 0; i < list.size(); i++) {
					holder.layout.addView(addView(list.get(i), position, i));
				}
			}

			return convertView;
		}

		private void isOpen(final ArrayList<Info> list, final ViewHolder holder) {
			if (list.get(0).isOpen) {
				holder.layout.setVisibility(LinearLayout.VISIBLE);
				holder.btn.setBackgroundResource(R.drawable.buy_jc_item_btn_open);
			} else {
				holder.layout.setVisibility(LinearLayout.GONE);
				holder.btn.setBackgroundResource(R.drawable.buy_jc_item_btn_close);
			}
		}

		// add by yejc 20130402
		private View addView(final Info info, final int position, final int index) {
			View convertView = mInflater.inflate(
					R.layout.buy_jc_main_listview_item_others, null);
			TextView gameName = (TextView) convertView
					.findViewById(R.id.game_name);
			TextView gameDate = (TextView) convertView
					.findViewById(R.id.game_date);

			final TextView homeTeam = (TextView) convertView
					.findViewById(R.id.home_team_name);
			final TextView guestTeam = (TextView) convertView
					.findViewById(R.id.guest_team_name);

			TextView btn = (Button) convertView
					.findViewById(R.id.jc_main_list_item_button);

			TextView analysis = (TextView) convertView
					.findViewById(R.id.game_analysis);
			final Button btnDan = (Button) convertView
					.findViewById(R.id.game_dan);

			gameName.setText(info.getTeam());
			String date = getWeek(info.getWeeks()) + " " + info.getTeamId()
					+ "\n" + PublicMethod.getEndTime(info.getTimeEnd()) + " " + "(截)";
			gameDate.setText(date);
			homeTeam.setText(info.getAway() + "(客)");
			guestTeam.setText(info.getHome() + "(主)");
			info.setLq(true);
			if (!info.getBtnStr().equals("")) {
				btn.setText(info.getBtnStr());
			}
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (info.onclikNum > 0 || isCheckTeam()) {
						info.createDialog(BasketHun.titleStrs, false,
								info.getAway() + " VS " + info.getHome());
						/**add by yejc 20130801 start*/
						mPosition = position;
						mIndex = index;
						/**add by yejc 20130801 end*/
					}
					isNoDan(info, btnDan);
				}
			});

			if (isDanguan || isHunHe()) {
				btnDan.setVisibility(Button.GONE);
			} else {
				btnDan.setVisibility(Button.VISIBLE);
				btnDan.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (info.isDan()) {
							info.setDan(false);
							btnDan.setBackgroundResource(R.drawable.jc_btn);
						} else if (info.onclikNum > 0 && isDanCheckTeam()
								&& isDanCheck()) {
							info.setDan(true);
							btnDan.setBackgroundResource(R.drawable.jc_btn_b);
						}
					}
				});
			}
			analysis.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					trunExplain(getEvent(Constants.JCBASKET, info));
				}
			});
			return convertView;
		}
		// end

		class ViewHolder {
			Button btn;
			LinearLayout layout;
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
