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
public class ChangJWT extends Activity{
	// ���������б����ID
	public final static int ID_WENTI1= 1;
	public final static int ID_WENTI2 = 2;
	public final static int ID_WENTI3 = 3;
	public final static int ID_WENTI4 = 4;
    public final static int ID_WENTI5 = 5;
    public final static int ID_WENTI6 = 6;
    public final static int ID_WENTI7 = 7;
    public final static int ID_WENTI8 = 8;
    public final static int ID_WENTI9 = 9;
    public final static int ID_WENTI10 = 10;
    public final static int ID_WENTI11 = 11;
    public final static int ID_WENTI12 = 12;
    public final static int ID_WENTI13 = 13;
    public final static int ID_WENTI14 = 14;
    public final static int ID_WENTI15 = 15;
    public final static int ID_WENTI16 = 16;
    public final static int ID_WENTI17 = 17;

    private List<Map<String, Object>> list;/* �б�������������Դ */
    private TextView text;
    private static final String IICON = "IICON";
    private final static String TITLE = "TITLE"; /* ���� */
    private View views ;
    private boolean isMain = true;
	private String[] titles = {"ʹ������ʹ��ʶ�����Щ���ƣ�",
			"ʹ�ÿͻ��˹����Ʊ��ȫ��",
			"��¼ʱ������������ô�죿",
		    "ΪʲôҪ��д��ʵ���������֤�ţ�",
			"��ֵδ��ʱ������ô�죿",
			"��������֧��֧����Щ���У�",
			"��θ����󶨵��ֻ��ţ�",
			"���ֺ����ܵ��ˣ�",
			"��β鿴�����н����飿",	
			"�����ֵĿ���ʱ����ʲôʱ��",	
			"ʲô���ǳƣ�",	
			"�����������ע�����˻���",	
			"����޸ĵ�½���룿",	
			"���֤�Ų�С���������ô�죿",	
			"��α����˻����ʽ�ȫ��",	
			"��������Щ����",	
			"���򷽰��н��󽱽���η��䣿",	
			};
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
		textView.setText("��������");
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
				if (titles[0].equals(str)) {
					setContentView(showInfoView(ID_WENTI1));
				}else if (titles[1].equals(str)) {
					setContentView(showInfoView(ID_WENTI2));
				}else if (titles[2].equals(str)) {
					setContentView(showInfoView(ID_WENTI3));
				}else if (titles[3].equals(str)) {
					setContentView(showInfoView(ID_WENTI4));
				}else if (titles[4].equals(str)) {
					setContentView(showInfoView(ID_WENTI5));
				}else if (titles[5].equals(str)) {
					setContentView(showInfoView(ID_WENTI6));
				}else if (titles[6].equals(str)) {
					setContentView(showInfoView(ID_WENTI7));
				}else if (titles[7].equals(str)) {
					setContentView(showInfoView(ID_WENTI8));
				}else if (titles[8].equals(str)) {
					setContentView(showInfoView(ID_WENTI9));
				}else if (titles[9].equals(str)) {
					setContentView(showInfoView(ID_WENTI10));
				}else if (titles[10].equals(str)) {
					setContentView(showInfoView(ID_WENTI11));
				}else if (titles[11].equals(str)) {
					setContentView(showInfoView(ID_WENTI12));
				}else if (titles[12].equals(str)) {
					setContentView(showInfoView(ID_WENTI13));
				}else if (titles[13].equals(str)) {
					setContentView(showInfoView(ID_WENTI14));
				}else if (titles[14].equals(str)) {
					setContentView(showInfoView(ID_WENTI15));
				}else if (titles[15].equals(str)) {
					setContentView(showInfoView(ID_WENTI16));
				}else if (titles[16].equals(str)) {
					setContentView(showInfoView(ID_WENTI17));
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
			case ID_WENTI1:
				iFileName = "ruyihelper_WENTI1.html";
				title.setText("��������");
				break;
			case ID_WENTI2:
				iFileName = "ruyihelper_WENTI2.html";
				title.setText("��������");
				break;
			case ID_WENTI3:
				iFileName = "ruyihelper_WENTI3.html";
				title.setText("��������");
				break;
			case ID_WENTI4:
				iFileName = "ruyihelper_WENTI4.html";
				title.setText("��������");
				break;
			case ID_WENTI5:
				iFileName = "ruyihelper_WENTI5.html";
				title.setText("��������");
				break;
			case ID_WENTI6:
				iFileName = "unionpaysupportbanklist.html";
				title.setText("��������");
				break;
			case ID_WENTI7 :
				iFileName = "ruyihelper_WENTI6.html";
				title.setText("��������");
				break;
			case ID_WENTI8 :
				iFileName = "ruyihelper_WENTI7.html";
				title.setText("��������");
				break;
			case ID_WENTI9 :
				iFileName = "ruyihelper_WENTI8.html";
				title.setText("��������");
				break;
			case ID_WENTI10 :
				iFileName = "ruyihelper_WENTI9.html";
				title.setText("��������");
				break;
			case ID_WENTI11 :
				iFileName = "ruyihelper_WENTI10.html";
				title.setText("��������");
				break;
			case ID_WENTI12 :
				iFileName = "ruyihelper_WENTI11.html";
				title.setText("��������");
				break;
			case ID_WENTI13 :
				iFileName = "ruyihelper_WENTI12.html";
				title.setText("��������");
				break;
			case ID_WENTI14 :
				iFileName = "ruyihelper_WENTI13.html";
				title.setText("��������");
				break;
			case ID_WENTI15 :
				iFileName = "ruyihelper_WENTI14.html";
				title.setText("��������");
				break;
			case ID_WENTI16 :
				iFileName = "ruyihelper_WENTI15.html";
				title.setText("��������");
				break;
			case ID_WENTI17 :
				iFileName = "ruyihelper_WENTI16.html";
				title.setText("��������");
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
//					holder.title.setGravity(Gravity.CENTER);
					holder.title.setText(title);
					holder.icon.setBackgroundResource(R.drawable.xiangyou);
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
