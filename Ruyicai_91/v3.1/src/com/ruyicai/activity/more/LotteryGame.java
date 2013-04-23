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

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.util.PublicMethod;

/**
 * 
 * ��������
 * @author Administrator
 *
 */
public class LotteryGame extends Activity{
	// ���������б����ID
	public final static int ID_SHUANGSEQIU = 1;/* �淨���� */
	public final static int ID_FC3D = 2;/* �������� */
	public final static int ID_QLC = 3;/* �н��콱 */
	public final static int ID_DLT = 4;/* �����һ� */
    public final static int ID_PL3 = 5;/* �ͷ��绰 */
    public final static int ID_PL5 = 6;//���ڱ����
    public final static int ID_QXC = 7;
    public final static int ID_22_5 = 8;
    public final static int ID_SSC = 9;
    public final static int ID_11_5 = 10;
    public final static int ID_11DJ = 11;
    public final static int ID_ZC = 12;
    public final static int ID_JCZQ = 13;
    public final static int ID_JCLQ = 14;
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
		textView.setText("��Ʊ�淨");
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
				if ("˫ɫ���淨����".equals(str)) {
					setContentView(showInfoView(ID_SHUANGSEQIU));
				}
				if ("����3D�淨����".equals(str)) {
					setContentView(showInfoView(ID_FC3D));
				}
				if ("���ֲ��淨����".equals(str)) {
					setContentView(showInfoView(ID_QLC));
				}
				if ("����͸�淨����".equals(str)) {
					setContentView(showInfoView(ID_DLT));
				}
				if ("�������淨����".equals(str)) {
					setContentView(showInfoView(ID_PL3));
				}
				if ("�������淨����".equals(str)) {
					setContentView(showInfoView(ID_PL5));
				}if ("���ǲ��淨����".equals(str)) {
					setContentView(showInfoView(ID_QXC));
				}if ("22ѡ5�淨����".equals(str)) {
					setContentView(showInfoView(ID_22_5));
				}if ("ʱʱ���淨����".equals(str)) {
					setContentView(showInfoView(ID_SSC));
				}if ("11ѡ5�淨����".equals(str)) {
					setContentView(showInfoView(ID_11_5));
				}if ("ʮһ�˶���淨����".equals(str)) {
					setContentView(showInfoView(ID_11DJ));
				}if ("����淨����".equals(str)) {
					setContentView(showInfoView(ID_ZC));
				}if ("���������淨����".equals(str)) {
					setContentView(showInfoView(ID_JCZQ));
				}if ("���������淨����".equals(str)) {
					setContentView(showInfoView(ID_JCLQ));
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
			/* �淨���� */
			case ID_SHUANGSEQIU:
				iFileName = "ruyihelper_SHUANGSEQIU.html";
				title.setText("˫ɫ���淨����");
				break;
			/* �н��콱 */
			case ID_FC3D:
				iFileName = "ruyihelper_FC3D.html";
				title.setText("����3D�淨����");
				break;
			/* �������� */
			case ID_QLC:
				iFileName = "ruyihelper_QLC.html";
				title.setText("���ֲ��淨����");
				break;
			/* �����һ� */
			case ID_DLT:
				iFileName = "ruyihelper_DLT.html";
				title.setText("����͸�淨����");
				break;
			/* �ͷ��绰 */
			case ID_PL3:
				iFileName = "ruyihelper_PL3.html";
				title.setText("����3�淨����");
				break;
			case ID_PL5 :
				iFileName = "ruyihelper_PL5.html";
				title.setText("�������淨����");
				break;
			case ID_QXC :
				iFileName = "ruyihelper_QXC.html";
				title.setText("���ǲ��淨����");
				break;
			case ID_22_5 :
				iFileName = "ruyihelper_22_5.html";
				title.setText("22ѡ5�淨����");
				break;
			case ID_SSC :
				iFileName = "ruyihelper_SSC.html";
				title.setText("ʱʱ���淨����");
				break;
			case ID_11_5 :
				iFileName = "ruyihelper_11_5.html";
				title.setText("11ѡ5�淨����");
				break;
			case ID_11DJ :
				iFileName = "ruyihelper_11DJ.html";
				title.setText("ʮһ�˶���淨����");
				break;
			case ID_ZC :
				iFileName = "ruyihelper_ZC.html";
				title.setText("����淨����");
				break;
			case ID_JCZQ :
				iFileName = "ruyihelper_JCZQ.html";
				title.setText("���������淨����");
				break;
			case ID_JCLQ :
				iFileName = "ruyihelper_JCLQ.html";
				title.setText("���������淨����");
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

			String[] titles = {"˫ɫ���淨����",
					"����3D�淨����",
					"���ֲ��淨����",
				    "����͸�淨����",
					"�������淨����",
					"�������淨����",
					"���ǲ��淨����",
					"22ѡ5�淨����",
					"ʱʱ���淨����",
					"11ѡ5�淨����",
					"ʮһ�˶���淨����",
					"����淨����",
					"���������淨����",
					"���������淨����"
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
