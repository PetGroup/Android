/**
 * 
 */
package com.ruyicai.activity.join;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.QueryJoinInfoInterface;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 *
 */
public class JoinInfoActivity extends Activity implements HandlerMsg{
	private List<Map<String, Object>>list1 =new ArrayList<Map<String,Object>>(); /* �б�������������Դ */
	private List<Map<String, Object>>list2 =new ArrayList<Map<String,Object>>();
	private List<Map<String, Object>>list3 =new ArrayList<Map<String,Object>>();
	private final static String INFO = "INFO"; 
	public final static String ID = "id"; 
	private String issue = "",lotno = "";
	private ViewInfo[] viewInfos = new ViewInfo[3];
	private JoinInfoAdapter[] adapter =new JoinInfoAdapter[3];
	private int topIndex = 0;

	String orderBy,orderDir;
	Button imgUp,imgDown;
	Button progress,allAtm,atm;//����ť
	CheckBox  check;//����������
	private ProgressDialog progressdialog;
	MyHandler handler = new MyHandler(this);//�Զ���handler
	Handler handlerTwo = new Handler();
	JSONObject json;
	public static boolean isRefresh = false;
	ListView listview;
	View view;
	ProgressBar progressbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.join_info_check);
		isRefresh = false;
		initViewInfos();
		getInfo();
		init();	
		buttonOnclik();
		joinInfokNet(orderBy,orderDir);
	}
	public void initViewInfos(){
		for(int i=0;i<3;i++){
			 viewInfos[i] = new ViewInfo(); 
		}
	}
	/**
	 * ����һ�����ȡ��Ϣ
	 */
	public void getInfo(){
		Intent intent = getIntent();
		if(intent!=null){
			lotno = intent.getStringExtra(JoinHallActivity.LOTNO);
			issue = intent.getStringExtra(JoinHallActivity.ISSUE);
		}
	}
	/**
	 * ��ʼ�����
	 */
	public void init() {
		TextView title = (TextView) findViewById(R.id.join_text_title);
		Button imgRetrun = (Button) findViewById(R.id.join_img_return);
		title.setText("�������");
		title.append("-"+PublicMethod.toLotno(lotno));
		imgRetrun.setBackgroundResource(R.drawable.returnselecter);
		// ImageView�ķ����¼�
		imgRetrun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		LinearLayout top = (LinearLayout) findViewById(R.id.join_info_check_linear_top);
		top.setVisibility(LinearLayout.VISIBLE);
		progress = (Button) findViewById(R.id.join_info_btn_progress);
		progress.setBackgroundResource(R.drawable.join_info_btn_selecter);
		allAtm = (Button) findViewById(R.id.join_info_btn_all_atm);
		allAtm.setBackgroundResource(R.drawable.join_info_btn_selecter);
		atm = (Button) findViewById(R.id.join_info_btn_atm);
		atm.setBackgroundResource(R.drawable.join_info_btn_selecter);
		check = (CheckBox) findViewById(R.id.jion_info_check);
	    check.setButtonDrawable(R.drawable.join_info_check_select);
	    listview = (ListView) findViewById(R.id.join_listview);
	    LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    view = mInflater.inflate(R.layout.lookmorebtn,null);
	    progressbar=(ProgressBar)view.findViewById(R.id.getmore_progressbar);
		listview.addFooterView(view);
     	// ����Դ
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				progressbar.setVisibility(ProgressBar.VISIBLE);
				view.setEnabled(false);
				// TODO Auto-generated method stub
	            addmore();	                    	
	             			
			}
		});


	}

	
	
	private void addmore(){
	
	   viewInfos[topIndex].newPage++;
       if(viewInfos[topIndex].newPage<viewInfos[topIndex].allPage){   
        	joinInfokNet(orderBy,orderDir);
	   }else{
		viewInfos[topIndex].newPage=viewInfos[topIndex].allPage-1;
		 view.setEnabled(true);
		 progressbar.setVisibility(view.INVISIBLE);
		 Toast.makeText(JoinInfoActivity.this, "����βҳ", Toast.LENGTH_SHORT).show();   
	   }
	}
	/**
	 * ������ť�¼�
	 */
	public void buttonOnclik(){
		progress.setBackgroundResource(R.drawable.join_info_btn_b);
		orderBy = QueryJoinInfoInterface.PROGRESS;
		orderDir = QueryJoinInfoInterface.DESC;
		progress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progress.setBackgroundResource(R.drawable.join_info_btn_b);
				allAtm.setBackgroundResource(R.drawable.join_info_btn);
				atm.setBackgroundResource(R.drawable.join_info_btn);
				orderBy = QueryJoinInfoInterface.PROGRESS;
				topIndex = 0;
				initOrder();
			}
		});

		allAtm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progress.setBackgroundResource(R.drawable.join_info_btn);
				allAtm.setBackgroundResource(R.drawable.join_info_btn_b);
				atm.setBackgroundResource(R.drawable.join_info_btn);
				orderBy = QueryJoinInfoInterface.TOTALAMT;
				topIndex = 1;
				initOrder();
			}
		});
		atm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progress.setBackgroundResource(R.drawable.join_info_btn);
				allAtm.setBackgroundResource(R.drawable.join_info_btn);
				atm.setBackgroundResource(R.drawable.join_info_btn_b);
				orderBy = QueryJoinInfoInterface.MINAMT;
				topIndex = 2;
				initOrder();
			}
		});
		// ʵ�ּ�ס���� �� ��ѡ���״̬
	    check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
                         if(isChecked){
                         	 orderDir = QueryJoinInfoInterface.ASC;
                         }else{
                        	 orderDir = QueryJoinInfoInterface.DESC;
                         }
                         if(viewInfos[topIndex].ischeck != isChecked){
                        	  viewInfos[topIndex].ischeck = isChecked;
                              onCheck();
                         }
                       
					}
				});
	}
	/**
	 * �����������¼�
	 */
	public void onCheck(){
		viewInfos[topIndex].newPage = 0;
		viewInfos[topIndex].allPage = 0;
		viewInfos[topIndex].listdata.clear();
		joinInfokNet(orderBy,orderDir);
		view.setEnabled(true);
	}
	public void initOrder(){
	    check.setChecked(viewInfos[topIndex].ischeck);	
		if(viewInfos[topIndex].newPage>viewInfos[topIndex].allPage-1){
    		joinInfokNet(orderBy,orderDir);
    		view.setEnabled(true);
    	}else{
    		initList();
    	}
	}
	/**
	 * ������ѯ
	 */
	
	private void netting(final String orderBy,final String orderDi){
		String str = "00";
		str = QueryJoinInfoInterface.queryLotJoinInfo(lotno,issue,orderBy,orderDir,""+viewInfos[topIndex].newPage,JoinHallActivity.PAGENUM);
		handlerTwo.post(new Runnable() {
			@Override
			public void run() {
				progressbar.setVisibility(view.INVISIBLE);
 			    view.setEnabled(true);
			
			}
		});
		try {
			json = new JSONObject(str);
			String msg = json.getString("message");
			String error = json.getString("error_code");
			if(!error.equals("0000")){
			
			}
			handler.handleMsg(error,msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(viewInfos[topIndex].newPage==0){
			progressdialog.dismiss();
		}
		
}
		
	
	public void joinInfokNet(final String orderBy,final String orderDir){
		if(viewInfos[topIndex].newPage==0){
			showDialog(0);	
		}
         // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
//			String str = "00";
			@Override
			public void run() {
			netting(orderBy, orderDir);
			}
		});
		t.start();
	}
	/**
	 * ��ʼ���б�����
	 */
    public void setValue(){
		try{
			Vector<Info> checkInfos = new Vector<Info>();
			JSONArray array = json.getJSONArray("result");
			viewInfos[topIndex].allPage = Integer.parseInt(json.getString("totalPage"));
			for(int i=0;i<array.length();i++){
				JSONObject obj = array.getJSONObject(i);
				Info info = new Info();
                info.setAllAtm(obj.getString("totalAmt"));
                info.setAtm(obj.getString("buyAmt"));
                info.setName(obj.getString("starter"));
                info.setProgress(obj.getString("progress"));
                info.setId(obj.getString("caseLotId"));
                JSONObject displayIcon = obj.getJSONObject("displayIcon");
                try{
                    info.setCup(displayIcon.getString("cup"));
                }catch(Exception e){
                    
                }
                try{
                	info.setDiamond(displayIcon.getString("diamond"));
                }catch(Exception e){
                    
                }
                try{
                	info.setStarNum(displayIcon.getString("goldStar"));
                }catch(Exception e){
                    
                }
                try{
                	info.setCrown(displayIcon.getString("crown"));
                }catch(Exception e){
                    
                }
                info.setSafe(obj.getString("safeRate"));
            	checkInfos.add(info);
            	viewInfos[topIndex].listdata.add(info);
			}
//			ViewPage page = new ViewPage();
//			page.setCheckInfos(checkInfos);
//			if(viewInfos[topIndex].listPages.size()>viewInfos[topIndex].newPage){
//				viewInfos[topIndex].listPages.remove(viewInfos[topIndex].newPage);
//				viewInfos[topIndex].listPages.add(viewInfos[topIndex].newPage, page);
//			}else{
//				viewInfos[topIndex].listPages.add(page);
//			}
			
		}catch(Exception e){
			
		}
    }
	/**
	 * ��ʼ���б�
	 */
	public void initList() {

		if(viewInfos[topIndex].newPage==0){
				adapter[topIndex] = new  JoinInfoAdapter(this, viewInfos[topIndex].listdata);  	
			    listview.setAdapter(adapter[topIndex]);		
	      }
		   else{
			adapter[topIndex].notifyDataSetChanged();
		 }
	
		/* �б�ĵ����ı��� */
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Info info = (Info) viewInfos[topIndex].listdata.get(position);
				if(info.getAtm().equals(info.getAllAtm())){
					Toast.makeText(JoinInfoActivity.this, "�÷����Ѿ���Ա,����ѡ������������", Toast.LENGTH_SHORT).show();  
				}else{
					Intent intent = new Intent(JoinInfoActivity.this, JoinDetailActivity.class);
					intent.putExtra(ID, info.getId());
			        intent.putExtra(JoinHallActivity.LOTNO, lotno);
			        intent.putExtra(JoinHallActivity.ISSUE, issue);
					startActivity(intent);
				}
			}

		};
		listview.setOnItemClickListener(clickListener);
	}
    public String getTextPage(int newPage,int allPage){
    	if((newPage+1)>allPage){
    		return "��"+allPage+"ҳ"+"  ��"+allPage+"ҳ";
    	}else{
    		return "��"+(newPage+1)+"ҳ"+"  ��"+allPage+"ҳ";
    	}
    }

	/**
	 * ����û������б�������������Դ
	 * 
	 * @return
	 */
//	protected void getListForJoinInfoAdapter() {
//
//
////		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
//        if(viewInfos[topIndex].listPages.size()!=0){
//        	for(int j=0; j< viewInfos[topIndex].listPages.size();j++){
//        	for (int i = 0; i < viewInfos[topIndex].listPages.get(j).getCheckInfos().size(); i++) {
//    			Map<String, Object> map = new HashMap<String, Object>();
//    			Info info = (Info) viewInfos[topIndex].listPages.get(j).getCheckInfos().get(i);
//    			map.put(INFO, info);
//    			if(topIndex==0){
//    				list1.add(map);
//    			}else if(topIndex==1){
//    				list2.add(map);
//    			}else if(topIndex==2){
//    				list3.add(map);
//    			}
//    			
//    		}
//        }
//        }
////		return list;
//
//	}

	/**
	 * �û����ĵ�������
	 */
	public class JoinInfoAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
		private List<Info> mList;

		public  JoinInfoAdapter(Context context, List<Info> list) {
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
			Info info = (Info) mList.get(position);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.join_info_listview_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.join_info_item_text_name);
				holder.starNum = (LinearLayout) convertView.findViewById(R.id.join_info_item_linear_star);
				holder.progress = (TextView) convertView.findViewById(R.id.join_info_item_text_progress);
				holder.allAtm = (TextView) convertView.findViewById(R.id.join_info_item_text_all_amt);
				holder.atm = (TextView) convertView.findViewById(R.id.join_info_item_text_amt);
				holder.safe = (TextView) convertView.findViewById(R.id.join_info_item_safe);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText("������:"+info.getName());
			holder.progress.setText(info.getProgress());
			holder.allAtm.setText("��"+info.getAllAtm());
			holder.atm.setText("��"+info.getAtm());
			holder.safe.setText(info.getSafe());
			PublicMethod.createStar(holder.starNum,info.getCrown(),info.getCup(),info.getDiamond(),
					                info.getStarNum(),JoinInfoActivity.this);
			return convertView;
		}

		class ViewHolder {
			TextView name;
			LinearLayout starNum;
			TextView progress;
			TextView allAtm;
			TextView atm;
			TextView safe;
		}
	}

	/**
	 *  �������ӿ�
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			return progressdialog;
		}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#errorCode_0000()
	 */
	@Override
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		setValue();
		initList();
	}
	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#errorCode_000000()
	 */
	@Override
	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#getContext()
	 */
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
	/**
	 * �����ѯ�ڲ����ŵ�ǰ����ÿһ���б���Ϣ
	 * @author Administrator
	 */
	class Info{
		String name;
		String progress;
		String allAtm;
		String atm;
		String id;
		String safe = "";
		String crown = "0";//�ʹ�
		String cup = "0";//����
		String diamond = "0";//��ʯ
		String starNum = "0";//��
		public String getSafe() {
			return safe;
		}
		public void setSafe(String safe) {
			this.safe = "+"+safe+"%";
		}
		public String getCrown() {
			return crown;
		}
		public void setCrown(String crown) {
			this.crown = crown;
		}
		public String getCup() {
			return cup;
		}
		public void setCup(String cup) {
			this.cup = cup;
		}
		public String getDiamond() {
			return diamond;
		}
		public void setDiamond(String diamond) {
			this.diamond = diamond;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStarNum() {
			return starNum;
		}
		public void setStarNum(String starNum) {
			this.starNum = starNum;
		}
		public String getProgress() {
			return progress;
		}
		public void setProgress(String progress) {
			this.progress = progress+"%";
		}
		public String getAllAtm() {
			return allAtm;
		}
		public void setAllAtm(String allAtm) {
			
			this.allAtm = Integer.toString(Integer.parseInt(allAtm)/100);
		}
		public String getAtm() {
			return atm;
		}
		public void setAtm(String atm) {
			this.atm = Integer.toString(Integer.parseInt(atm)/100);
		}

		public Info(){

		}
		
	}
//	/**
//	 * �����ѯ�ڲ����ŵ�ǰҳ��Ϣ
//	 * @author Administrator
//	 */
//	class ViewPage{
//	 public	Vector<Info> checkInfos = new Vector<Info>();
//	 public boolean isRefresh = false;//��ǰҳ�Ƿ���Ҫ���¸���
//
//	 public Vector<Info> getCheckInfos() {
//			return checkInfos;
//	 }
//	 public void setCheckInfos(Vector<Info> checkInfos) {
//			this.checkInfos = checkInfos;
//	 }
//	 public boolean isRefresh() {
//		return isRefresh;
//	 }
//	 public void setRefresh(boolean isRefresh) {
//		this.isRefresh = isRefresh;
//	 }
//	} 
	/**
	 * �����ѯ�ڲ����ŵ�ǰ������Ϣ
	 * @author Administrator
	 */
	class ViewInfo{
		public boolean ischeck = false;//true������false�ǽ���
		public int allPage = 0;
		public int newPage = 0;//��ǰҳ��0��ʼ
//		public List<ViewPage> listPages = new ArrayList();
		public List<Info> listdata = new ArrayList<Info>();
		public boolean isRefresh = false;
		public ViewInfo(){
			
		}
		public boolean isRefresh() {
			return isRefresh;
		}
		public void setRefresh(boolean isRefresh) {
			this.isRefresh = isRefresh;
		}
		public int getAllPage() {
			return allPage;
		}
		public void setAllPage(int allPage) {
			this.allPage = allPage;
		}
		public int getNewPage() {
			return newPage;
		}
		public void setNewPage(int newPage) {
			this.newPage = newPage;
		}
	}
	/**
	 * �Ƿ���¸��µ�ǰҳ
	 */
	public void ifRefreshPage(){
     
    	  if(viewInfos[topIndex].isRefresh()){
    		  viewInfos[topIndex].setRefresh(false);
    		  joinInfokNet(orderBy,orderDir);
    	  }else{
    		  initList();
    	  }
	}
	/**
	 * ��Ҫ���¸��µĽ���
	 */
	public void initViewPageInfo(){
		 viewInfos[topIndex].setRefresh(true);
		
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isRefresh){
			initViewPageInfo();
			ifRefreshPage();
		}
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
}
