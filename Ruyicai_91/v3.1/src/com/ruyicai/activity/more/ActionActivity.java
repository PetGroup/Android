package com.ruyicai.activity.more;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.more.HelpCenter.HelpCenterAdapter;
import com.ruyicai.activity.more.HelpCenter.HelpCenterAdapter.ViewHolder;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.json.action.ContentJson;
import com.ruyicai.json.action.TitleJson;
import com.ruyicai.net.newtransaction.ActionContentInterface;
import com.ruyicai.net.newtransaction.ActionTitleInterface;
import com.ruyicai.util.PublicMethod;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActionActivity extends Activity implements HandlerMsg{
	public static JSONArray jsonArray = null;
	public static List<TitleJson> actions = new ArrayList<TitleJson>();
	private final static String TITLE = "TITLE"; /* ���� */
    private List<Map<String, Object>> list;/* �б�������������Դ */
    private ListView listview;
	private View views ;
    private boolean isMain = true;
    private ProgressDialog progressDialog;//����������
	MyHandler handler = new MyHandler(this);//�Զ���handler
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		openTitleView();
	}
	/**
	 * �򿪻�������
	 */
	public void openTitleView(){
		isMain = true;
		setContentView(showView());
		isTitleNet();
	}

	   /**
	    * ��������ı������
	    * @return
	    */
	 public View showView(){
		LayoutInflater inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		views= (LinearLayout) inflate.inflate(R.layout.action_center_title, null);
		listview = (ListView) views.findViewById(R.id.ruyihelper_listview_ruyihelper_id);
		Button btnreturn = (Button) views.findViewById(R.id.ruyizhushou_btn_return);
		TextView textView = (TextView) views.findViewById(R.id.ruyipackage_title);
		textView.setText(getResources().getString(R.string.action_title));
		btnreturn.setBackgroundResource(R.drawable.returnselecter);
		btnreturn.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
			    finish();
			}

		});

		return views;
	}
	/**
	 * �Ƿ�����
	 * 
	 */
	public void isTitleNet(){
		if(actions.size() == 0){//����
			titleNet();
		}else{//������
			initList();
		}
	}
	/**
	 * ��������
	 */
	public void titleNet(){
		progressDialog = UserCenterDialog.onCreateDialog(this);
		progressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String jsonStr;
				jsonStr = ActionTitleInterface.titleQuery();
				progressDialog.dismiss();
				try {
					JSONObject obj = new JSONObject(jsonStr);
					jsonArray  = new JSONObject(jsonStr).getJSONArray("result");
					splitJsonArray();
					String msg = obj.getString("message");
					String error = obj.getString("error_code");
					handler.handleMsg(error,msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * ����JsonArray����
	 * 
	 */
	public void splitJsonArray(){
		for(int i=0;i<jsonArray.length();i++){
			try {
				TitleJson action = new TitleJson(jsonArray.getJSONObject(i));
				actions.add(action);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * ��ʼ���б�
	 * 
	 */
	public void initList(){
		// ����Դ
		list = getListForRuyiHelperAdapter();
		HelpCenterAdapter adapter = new HelpCenterAdapter(this,list);
		// ������		
		listview.setAdapter(adapter);
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openContentView(position);
			}

		};
		listview.setOnItemClickListener(clickListener);
        listview.setDividerHeight(0);
	}

	/**
	 *  ������������б�������������Դ
	 * @return
	 */
	protected List<Map<String, Object>> getListForRuyiHelperAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);

		for (int i = 0; i < actions.size() ; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, actions.get(i));
			list.add(map);
		}
		return list;

	}
	/**
	 * ����ĵ�������
	 */
	public class HelpCenterAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
		private List<Map<String, Object>> mList;

		public HelpCenterAdapter(Context context, List<Map<String, Object>> list) {
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

		int index;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			index = position;
			ViewHolder holder = null;
			TitleJson titleJson = (TitleJson) mList.get(position).get(TITLE);
			
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.action_center_list_item,null);
				holder = new ViewHolder();
				holder.title1 = (TextView) convertView.findViewById(R.id.action_center_list_item_text_title1);
				holder.title2 = (TextView) convertView.findViewById(R.id.action_center_list_item_text_title2);
				holder.title3 = (TextView) convertView.findViewById(R.id.action_center_list_item_text_title3);
				holder.imgView = (ImageView)convertView.findViewById(R.id.action_center_list_item_img);
				holder.title1.setText(titleJson.getTitle());
				holder.title2.setText(getString(R.string.action_time)+titleJson.getTime());
				holder.title3.setText(getString(R.string.action_content)+titleJson.getIntroduce());
				if(titleJson.getIsEnd()){
					holder.title2.setTextColor(Color.BLACK);
					holder.imgView.setImageResource(R.drawable.action_title_end);
				}
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}
		class ViewHolder {
			TextView title1;
			TextView title2;
			TextView title3;
			ImageView imgView;
		}
	}
	@Override
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		initList();
	}
	@Override
	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
	/******************************************�����*******************************/
	private int index;//�ĸ����б�
	private TextView titleText;
	private TextView contentText;
	/**
	 * �򿪻���ݽ���
	 */
	public void openContentView(int index){
		isMain = false;
		this.index = index;
		setContentView(showContentView());
		isContentNet();
	}
	
	/**
	 * ���б����
	 * 
	 */
	public View showContentView(){
		View view = null;
		LayoutInflater inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		view= (LinearLayout) inflate.inflate(R.layout.action_center_content, null);
		Button btnreturn = (Button) view.findViewById(R.id.ruyizhushou_btn_return);
		TextView textView = (TextView) view.findViewById(R.id.ruyipackage_title);
		titleText = (TextView) view.findViewById(R.id.aciton_content_text_title);
		contentText = (TextView) view.findViewById(R.id.aciton_content_text_content);
		
		textView.setText(getResources().getString(R.string.action_info));
		btnreturn.setBackgroundResource(R.drawable.returnselecter);
		btnreturn.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				openTitleView();
			}

		});
		
		return view;
	}
	/**
	 * ������Ƿ�����
	 * 
	 */
	public void isContentNet(){
		if(actions.get(index).infoJson == null){//����
			contentNet(actions.get(index).getId());
		}else{//������
			initContentView();
		}
	}
	/**
	 * ��������
	 */
	public void contentNet(final String id){
		final Handler handler = new Handler();
		progressDialog = UserCenterDialog.onCreateDialog(this);
		progressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String jsonStr = ActionContentInterface.contentQuery(id);
				progressDialog.dismiss();
				try {
					actions.get(index).infoJson = new ContentJson(new JSONObject(jsonStr));
					handler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							initContentView();
						}
					});
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * ��ʼ������ݽ���
	 * 
	 */
	public void initContentView(){
		titleText.setText(actions.get(index).infoJson.getTitle());
		contentText.setText(actions.get(index).infoJson.getContent());
	}

    /**
     * ��д�Żؽ�
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		   case 4:
	        if(isMain){
	        	finish();
	        }else{
	        	openTitleView();
	        }
           break;
		}
		return false;
	}

}
