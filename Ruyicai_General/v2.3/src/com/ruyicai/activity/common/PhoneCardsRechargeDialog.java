package com.ruyicai.activity.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.RuyicaiAndroid.R;

public class PhoneCardsRechargeDialog extends Activity {
	/** Called when the activity is first created. */
	private Spinner mySpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_cards_recharge_dialog);
		// list.add("�ƶ���ֵ��");
		// list.add("��ͨ��ֵ��");
		mySpinner = (Spinner) findViewById(R.id.phone_card_spinner);
		// Ϊ�����б���һ����������������õ���ǰ�涨���list
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.recharge_card_type,
				android.R.layout.simple_spinner_item);
		// adapter = new
		// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
		// Ϊ���������������б�����ʱ�Ĳ˵���ʽ
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ����������ӵ������б���
		mySpinner.setAdapter(adapter);
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// ��������򡣡���

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// û���κεĴ����¼�ʱ

			}

		});
	}
}