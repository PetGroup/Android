/**
 * 
 */
package com.ruyicai.activity.more;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.util.PublicMethod;

/**
 * 
 * ��������
 * @author Administrator
 *
 */
public class GongNZY extends Activity{
	// ���������б����ID
	public final static int ID_ZHUCE = 1;/* ע���¼ */
	public final static int ID_MIMA = 2;/* �������� */
	public final static int ID_CHONGZHI = 3;/* ��ֵ*/
	public final static int ID_LINGJIANG = 4;/* �콱���� */
    public final static int ID_TOUZHU = 5;/* Ͷע */
    public final static int ID_HEMAI = 6;//����
    public final static int ID_ZHUIHAO = 7;//׷��
    private List<Map<String, Object>> list;/* �б�������������Դ */
    private TextView text;
    private static final String IICON = "IICON";
    private final static String TITLE = "TITLE"; /* ���� */
    private View views ;
    private boolean isMain = true;
    Activity activity;
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(showView());
		}
	   /**
	    * �����������Ľ���
	    * @return
	    */
	   public View showView(){
		isMain = true;
		LayoutInflater inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		views= (LinearLayout) inflate.inflate(R.layout.ruyihelper_listview, null);
		ListView listview = (ListView) views.findViewById(R.id.ruyihelper_listview_ruyihelper_id);
		Button btnreturn = (Button) views.findViewById(R.id.ruyizhushou_btn_return);
		TextView textView = (TextView) views.findViewById(R.id.ruyipackage_title);
		textView.setText("����ָ��");
		btnreturn.setBackgroundResource(R.drawable.returnselecter);
		btnreturn.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
			    finish();
			}

		});

		// ����Դ
		list = getListForRuyiHelperAdapter();
		HelpCenterAdapter adapter = new HelpCenterAdapter(this,list);
		// ������		
		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				String str = text.getText().toString();
				MoreActivity.iQuitFlag = 20;
				/* ��������֮�淨���� */
				if ("ע���¼".equals(str)) {
					setContentView(showInfoView(ID_ZHUCE));
				}
				/* ��������֮�н��콱 */
				if ("��������".equals(str)) {
					setContentView(showInfoView( ID_MIMA));
				}
				/* ��������֮�������� */
				if ("��ֵ".equals(str)) {
					setContentView(showInfoView(ID_CHONGZHI));
				}
				/* ��������֮�����һ� */
				if ("�콱����".equals(str)) {
					setContentView(showInfoView(ID_LINGJIANG));
				}
				/* ��������֮�ͷ��绰 */
				if ("Ͷע".equals(str)) {
					setContentView(showInfoView(ID_TOUZHU));
				}
				if("����".equals(str)){
					setContentView(showInfoView(ID_HEMAI));
				}if("׷��".equals(str)){
					setContentView(showInfoView(ID_ZHUIHAO));
				}
			}

		};
		listview.setOnItemClickListener(clickListener);
		return views;
	   }
	   /**
	    * �����������б�
	    * @param aInfoFlag
	    * @return
	    */
	   protected View showInfoView(int aInfoFlag) {
		   isMain = false;
			LayoutInflater inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			View view = (LinearLayout) inflate.inflate(R.layout.layout_ruyizhushou, null);
			Button btnreturn = (Button) view.findViewById(R.id.ruyizhushou_btn_return);
			TextView title = (TextView) view.findViewById(R.id.ruyipackage_title);
			btnreturn.setBackgroundResource(R.drawable.returnselecter);
		    btnreturn.setOnClickListener(new Button.OnClickListener() {
		    	
					public void onClick(View v) {
						setContentView(showView());
					}
				});
			WebView webView = (WebView) view.findViewById(R.id.ruyipackage_webview);
			String iFileName = null;
			switch (aInfoFlag) {
			/* ע���¼ */
			case ID_ZHUCE:
				iFileName = "ruyihelper_registlogin.html";
				title.setText("ע���¼");
				break;
			/* �������� */
			case ID_MIMA:
				iFileName = "ruyihelper_MIMA.html";
				title.setText("��������");
				break;
			/* ��ֵ */
			case ID_CHONGZHI:
				iFileName = "ruyihelper_CHONGZHI.html";
				title.setText("��ֵ");
				break;
			/* �콱���� */
			case ID_LINGJIANG:
				iFileName = "ruyihelper_LINGJIANG.html";
				title.setText("�콱����");
				break;
			/* Ͷע */
			case ID_TOUZHU:
				iFileName = "ruyihelper_TOUZHU.html";
				title.setText("Ͷע");
				break;
		    //����
			case ID_HEMAI :
				iFileName = "ruyihelper_HEMAI.html";
				title.setText("����");
				break;
			//׷��
			case ID_ZHUIHAO :
				iFileName = "ruyihelper_ZHUIHAO.html";
				title.setText("׷��");
				break;
			}
			String url = "file:///android_asset/" + iFileName;
			webView.loadUrl(url);
			return view;
		}
		/**
		 *  ������������б�������������Դ
		 * @return
		 */
		protected List<Map<String, Object>> getListForRuyiHelperAdapter() {

			String[] titles = {"ע���¼",
					"��������",
					"��ֵ",
				    "�콱����",
					"Ͷע",
					"����",
					"׷��",
					};
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);

			for (int i = 0; i < titles.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(TITLE, titles[i]);
				list.add(map);
			}

			return list;

		}
		
		/**
		 * �������ĵ�������
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
				String title = (String) mList.get(position).get(TITLE);
				
					convertView = mInflater.inflate(R.layout.ruyihelper_listview_icon_item,null);
					holder = new ViewHolder();
					holder.title = (TextView) convertView.findViewById(R.id.ruyihelper_icon_text);
					holder.icon=(ImageView)convertView.findViewById(R.id.ruyihelper_iicon);
					holder.icon.setBackgroundResource(R.drawable.xiangyou);
//					holder.title.setGravity(Gravity.CENTER);
					holder.title.setText(title);
					convertView.setTag(holder);
					holder = (ViewHolder) convertView.getTag();
				
				return convertView;
			}
			class ViewHolder {
				TextView title;
				ImageView icon;
			}
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
		        	setContentView(showView());
		        }
	           break;
			}
			return false;
		}
}
