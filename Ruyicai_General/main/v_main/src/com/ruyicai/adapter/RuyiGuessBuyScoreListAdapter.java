package com.ruyicai.adapter;

import java.util.List;
import java.util.Map;
import com.palmdream.RuyicaiAndroid.R;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 购买积分列表适配器
 *
 */
public class RuyiGuessBuyScoreListAdapter extends BaseAdapter {

	private LayoutInflater mInflater; // 扩充主列表布局
	private int[] mStringResIds = {R.string.balance_recharge_score,R.string.umpay_recharge, 
			R.string.yin_bank_cards_recharge,R.string.zhfb_cards_secure_recharge,
			R.string.zhfb_cards_recharge,R.string.umpay_phone_recharge,
			R.string.bank_cards_recharge,R.string.account_chongzhi,
			R.string.phone_cards_recharge};
	
	private int[] mDrawableResIds = {R.drawable.ruyi_guess_balance_recharge,R.drawable.recharge_umpay,
			R.drawable.recharge_bank,R.drawable.recharge_alipay_safe,
			R.drawable.recharge_alipay,R.drawable.recharge_phone_umpay,
			R.drawable.recharge_phone,R.drawable.account_chongzhi,
			R.drawable.recharge_phonebank};
	
	private int[] mDescriptionResIds = {R.string.account_balance_alert,R.string.account_umplay_alert,
			R.string.account_yinlian_alert,R.string.account_zfb_secure,
			R.string.account_zfb_alert,R.string.account_umplay_phone_alert,
			R.string.account_card_alert,R.string.account_chongzhi_alert,
			R.string.account_phone_alert};

	public RuyiGuessBuyScoreListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return mStringResIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder = null;
//		String title = (String) mList.get(position).get(TITLE);
//		Integer iconid = (Integer) mList.get(position).get(PICTURE);
//		String alertStr = (String) mList.get(position).get(ISHANDINGFREE);
//		if (convertView == null) {
//			convertView = mInflater.inflate(R.layout.account_listviw_item,
//					null);
//			holder = new ViewHolder();
//			holder.title = (TextView) convertView
//					.findViewById(R.id.account_recharge_listview_text);
//			holder.isfreeHanding = (TextView) convertView
//					.findViewById(R.id.ishandingfree);
//			holder.lefticon = (ImageView) convertView
//					.findViewById(R.id.account_recharge_type);
//			holder.layout = (LinearLayout) convertView
//					.findViewById(R.id.account_recharge_listview_linerlayout);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		SpannableStringBuilder builder1 = new SpannableStringBuilder();
//		String str1 = title;
//		builder1.append(str1);
//		String alertStr1 = "";
//		
//		if (str1.equals(getString(R.string.zhfb_cards_recharge)) 
//				|| str1.equals(getString(R.string.zhfb_cards_secure_recharge))
//				|| str1.equals(getString(R.string.bank_cards_recharge))
//				|| str1.equals(getString(R.string.umpay_recharge))) {
//			alertStr1 = getString(R.string.freeHanding);
//		} else if (str1.equals(getString(R.string.yin_bank_cards_recharge))){
//			alertStr1 = getString(R.string.recommend_the_use_of);
//		} else if (str1.equals(getString(R.string.account_chongzhi))) {
//			alertStr1 = getString(R.string.account_chongzhi_good);
//		}
//		
//		if (str1.equals(getString(R.string.get_free_gold_title))
//				|| str1.equals(getString(R.string.account_exchange_gold))) {
//			holder.layout.setBackgroundResource(R.drawable.get_free_gold_background);
//		} else {
//			holder.layout.setBackgroundColor(getResources().getColor(R.color.white));
//		}
//		if (!alertStr1.equals("")) {
//			builder1.append(alertStr1);
//			builder1.setSpan(new ForegroundColorSpan(Color.RED),
//					builder1.length() - alertStr1.length(),
//					builder1.length(), Spanned.SPAN_COMPOSING);
//		}
//		holder.title.setText(builder1);
//		holder.lefticon.setBackgroundResource(iconid);
//		SpannableStringBuilder builder = new SpannableStringBuilder();
//		String str = alertStr;
//		builder.append(str);
//		holder.isfreeHanding.setHint(builder);
		return convertView;
	}

	class ViewHolder {
		TextView title;
		ImageView lefticon;
		TextView isfreeHanding;
		LinearLayout layout;
	}

}
