package com.ruyicai.activity.buy.jc.zq.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
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

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.jc.JcMainActivity;
import com.ruyicai.activity.buy.jc.JcMainView;
import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.code.jc.zq.FootBQC;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;

/**
 * 半全场
 * 
 * @author Administrator
 * 
 */
public class BQCView extends JcMainView {
	private final int MAX_TEAM = 4;
	JcInfoAdapter adapter;
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
		// TODO Auto-generated method stub
		return MAX_TEAM;
	}

	@Override
	public String getLotno() {
		// TODO Auto-generated method stub
		return Constants.LOTNO_JCZQ_BQC;
	}

	@Override
	public BaseAdapter getAdapter() {
		// TODO Auto-generated method stub
		return adapter;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
				codeStr += info.getHome() + " vs " + info.getAway() + "：";
				for (int j = 0; j < info.check.length; j++) {
					if (info.check[j].isChecked()) {
						codeStr += info.check[j].getChcekTitle() + " ";
					}
				}
				if (info.isDan()) {
					codeStr += "(胆)";
				}
				codeStr += "\n\n";
			}

		}
		return codeStr;
	}

	/**
	 * 初始化列表
	 */
	public void initListView(ListView listview, Context context,
			List<List> listInfo) {
		// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final ArrayList<Info> list = (ArrayList<Info>) mList.get(position);
			convertView = mInflater.inflate(
					R.layout.buy_jc_main_view_list_item, null);
			final ViewHolder holder = new ViewHolder();
			holder.btn = (Button) convertView
					.findViewById(R.id.buy_jc_main_view_list_item_btn);
			holder.layout = (LinearLayout) convertView
					.findViewById(R.id.buy_jc_main_view_list_item_linearLayout);
			holder.btn.setBackgroundResource(R.drawable.buy_jc_btn_close);
			if (list.size() == 0) {
				holder.btn.setVisibility(Button.GONE);
			} else {
				isOpen(list, holder);
				holder.btn.setText(list.get(0).getTime() + "  " + list.size()
						+ context.getString(R.string.jc_main_btn_text));
				holder.btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						list.get(0).isOpen = !list.get(0).isOpen;
						isOpen(list, holder);
					}
				});
				for (Info info : list) {
					holder.layout.addView(addView(info)/*addLayout(info)*/);
				}
			}

			return convertView;
		}

		private void isOpen(final ArrayList<Info> list, final ViewHolder holder) {
			if (list.get(0).isOpen) {
				holder.layout.setVisibility(LinearLayout.VISIBLE);
				holder.btn.setBackgroundResource(R.drawable.buy_jc_btn_open);
			} else {
				holder.layout.setVisibility(LinearLayout.GONE);
				holder.btn.setBackgroundResource(R.drawable.buy_jc_btn_close);
			}
		}
		
		// add by yejc 20130402
		private View addView(final Info info) {
			View convertView = mInflater.inflate(
					R.layout.buy_jc_main_listview_item_others, null);
			TextView gameName = (TextView) convertView
					.findViewById(R.id.game_name);
			TextView gameDate = (TextView) convertView
					.findViewById(R.id.game_date);

			final TextView homeTeam = (TextView) convertView
					.findViewById(R.id.home_team_name);
//			homeTeam.getPaint().setFakeBoldText(true);
			final TextView textVS = (TextView) convertView
					.findViewById(R.id.game_vs);
			if (!"".equals(info.getLetPoint()) && !"0".equals(info.getLetPoint())) {
				textVS.setText(info.getLetPoint());
			}
			final TextView guestTeam = (TextView) convertView
					.findViewById(R.id.guest_team_name);
//			guestTeam.getPaint().setFakeBoldText(true);

			TextView btn = (Button) convertView
					.findViewById(R.id.jc_main_list_item_button);

			TextView analysis = (TextView) convertView
					.findViewById(R.id.game_analysis);
			final Button btnDan = (Button) convertView
					.findViewById(R.id.game_dan);

			gameName.setText(info.getTeam());
			String date = getWeek(info.getWeeks()) + " " + info.getTeamId() + "\n"
					+ getEndTime(info.getTimeEnd())+" " + "(截)";
			gameDate.setText(date);
			homeTeam.setText(info.getHome());

			gameName.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (context instanceof JcMainActivity) {
						JcMainActivity activity = (JcMainActivity) context;
						activity.createTeamDialog();
					}
				}
			});

			// textVS.setText(info.getWin());
			guestTeam.setText(info.getAway());

			if (!info.getBtnStr().equals("")) {
				btn.setText(info.getBtnStr());
			}
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (info.onclikNum > 0 || isCheckTeam()) {
						info.createDialog(FootBQC.titleStrs, false,
								info.getHome() + " VS " + info.getAway());
					}
					isNoDan(info, btnDan);
				}
			});
			if (isDanguan) {
				btnDan.setVisibility(Button.GONE);
			} else {
				btnDan.setVisibility(Button.VISIBLE);
				btnDan.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub
					trunExplain(getEvent(Constants.JCFOOT, info),
							info.getHome(), info.getAway());
				}
			});
			return convertView;
		}
		// end

		private View addLayout(final Info info) {
			View convertView;
			convertView = mInflater.inflate(R.layout.buy_jc_main_listview_item,
					null);
			LinearLayout layout1 = (LinearLayout) convertView
					.findViewById(R.id.lq_sf_layout_bottom1);
			LinearLayout layout2 = (LinearLayout) convertView
					.findViewById(R.id.lq_sf_layout_bottom2);
			layout1.setVisibility(LinearLayout.GONE);
			layout2.setVisibility(LinearLayout.VISIBLE);
			TextView time = (TextView) convertView
					.findViewById(R.id.jc_main_list_item_text_time);
			TextView team = (TextView) convertView
					.findViewById(R.id.jc_main_list_item_text_team);
			TextView home = (TextView) convertView
					.findViewById(R.id.jc_main_list_item_text_team_name1);
			TextView away = (TextView) convertView
					.findViewById(R.id.jc_main_list_item_text_team_name2);
			TextView timeEnd = (TextView) convertView
					.findViewById(R.id.jc_main_list_item_text_time_end);
			TextView score = (TextView) convertView
					.findViewById(R.id.jc_main_list_item_text_team_score);
			TextView btn = (Button) convertView
					.findViewById(R.id.jc_main_list_item_button);
			final Button btnDan = (Button) convertView
					.findViewById(R.id.jc_main_list_item_btn_dan);
			final Button btnXi = (Button) convertView
					.findViewById(R.id.buy_jc_main_list_item_btn_xi);

			score.setVisibility(TextView.GONE);
			time.setText(info.getTime() + "  "
					+ context.getString(R.string.jc_main_team_id_title)
					+ info.getTeamId());
			team.setText(info.getTeam());
			home.setText(info.getHome() + "(主)");
			away.setText(info.getAway() + "(客)");
			timeEnd.setText(info.getTimeEnd());

			if (!info.getBtnStr().equals("")) {
				btn.setText(info.getBtnStr());
			}
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (info.onclikNum > 0 || isCheckTeam()) {
						info.createDialog(FootBQC.titleStrs, false,
								info.getHome() + " VS " + info.getAway());
					}
					isNoDan(info, btnDan);
				}
			});
			if (isDanguan) {
				btnDan.setVisibility(Button.GONE);
			} else {
				btnDan.setVisibility(Button.VISIBLE);
				btnDan.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
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
			btnXi.setVisibility(Button.VISIBLE);
			btnXi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					trunExplain(getEvent(Constants.JCFOOT, info),
							info.getHome(), info.getAway());
				}
			});
			return convertView;
		}

		class ViewHolder {
			Button btn;
			LinearLayout layout;

		}
	}

	@Override
	public String getPlayType() {
		// TODO Auto-generated method stub
		if (isDanguan) {
			return "J00004_0";
		} else {
			return "J00004_1";
		}
	}

	@Override
	public List<double[]> getOdds(List<Info> listInfo) {
		// TODO Auto-generated method stub
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
