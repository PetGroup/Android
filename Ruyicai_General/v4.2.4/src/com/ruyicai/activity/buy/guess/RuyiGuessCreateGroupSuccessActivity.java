package com.ruyicai.activity.buy.guess;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.adapter.ShareAdapter;
import com.ruyicai.custom.view.TitleBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 
 * @author win
 *
 */
public class RuyiGuessCreateGroupSuccessActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_ruyiguess_create_group_success);
		initView();
	}
	
	private void initView() {
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText(R.string.buy_ruyi_guess);
		TextView title = (TextView)findViewById(R.id.share_description_text);
		title.setText("您还可以选择分享到:");
		GridView gridView = (GridView)findViewById(R.id.gridview);
		gridView.setNumColumns(4);
		ShareAdapter adapter = new ShareAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
	}

}
