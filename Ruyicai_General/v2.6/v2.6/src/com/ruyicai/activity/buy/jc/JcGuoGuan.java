package com.ruyicai.activity.buy.jc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.Jc.Info;
import com.ruyicai.activity.join.JoinDetailActivity;
import com.ruyicai.activity.join.JoinHallActivity;
import com.ruyicai.activity.join.JoinInfoActivity;
import com.ruyicai.activity.join.JoinInfoActivity.JoinInfoAdapter;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class JcGuoGuan extends JcMain {
	private static final String[] strings = { "2��1", "3��1", "4��1", "5��1","6��1", "7��1", "8��1" };
	private List<Info> list;/* �б�������������Դ */
	private final static String INFO = "INFO";
	private int INDEX = 0;
	private List<String> betcodes = new ArrayList<String>();
	private long iZhuShu ;
	private long iAmt ;
	private String alertStr;
	private String codeType ;
	private final String LOTNO = "J00001";
	private final int MAXAMT = 20000;
	private final int MAXINT = 2000000000;//int ���ֵ

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 initSpinner();
	}
    public void initSpinner(){
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				int position = spinner.getSelectedItemPosition();
				INDEX = position;
				onclikSpinner(INDEX+2);
		
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
    }
    /**
     * 
     * ��ȡע��
     * 
     */
    public String[] getCode(){
    	String codes[]=new String[2];
    	String code = "50"+(INDEX+2)+"@";
    	String codeStr ="ע�룺\n";
		for (int i = 0; i < list.size(); i++) {
			Info info = (Info) list.get(i);
			if (info.onclikNum>0) {
             code +=info.getDay()+"|"+info.getWeek()+"|"+info.getTeamId()+"|";		
             codeStr += info.getHome()+" vs "+info.getAway()+"��";
             if(info.isWin()){
            	 code+="3";
            	 codeStr+="ʤ";
             }
             if(info.isLevel()){	
            	 code+="1";
            	 codeStr+="ƽ";
             }
             if(info.isFail()){
            	 code+="0";
            	 codeStr+="��";
             }
            	 code+="^";
            	 codeStr+="\n";
			}
		}
		codes[0]=code;
		codes[1]=codeStr;
		return codes;
    }
	public void onclikSpinner(int position) {
		betcodes = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Info info = (Info) list.get(i);
			if (info.onclikNum>0) {
				betcodes.add(""+info.onclikNum);
			}
		}
		showEditText();
	}
	@Override
	public void isTouZhuNet() {		
		// TODO Auto-generated method stub
		if(iZhuShu>0){
			if(isAmtDialog()){
				alertInfo("����Ͷע���ܴ���2��","��ܰ��ʾ");
			}else{
				String codes[] =getCode();
				betAndGift.setSellway("0");//1�����ѡ 0������ѡ
				betAndGift.setAmount(""+iAmt*100);
				betAndGift.setLotmulti(""+iZhuShu);
				betAndGift.setLotno(LOTNO);
				betAndGift.setBet_code(codes[0]);
				alert(getAlertStr(), codes[1]);
			}
		}else{
			alertInfo("������ѡ��һע","��ѡ�����");
		}
	}
	/**
	 * �Ƿ񵯳���ܰ��ʾ
	 * @return
	 */
	public boolean isAmtDialog(){
		if(iAmt>MAXAMT||iAmt<0){
			return true;
		}else{
			return false;
		}
	}
	public String getAlertStr(){
		String strs[] = alertStr.split(",");
		String returnStr = "ע����"+iZhuShu+"ע\n"+"������"+iProgressBeishu+"��\n"+"��"+iAmt+"Ԫ";
		return returnStr;
	}
    public void showEditText(){
    	iZhuShu = getAllAmt(betcodes, INDEX+2);
    	long beishu = iProgressBeishu;
		iAmt = iZhuShu*2*beishu;
		alertStr = "��"+iZhuShu+"ע,"+beishu+"��,"+"�ܽ��"+iAmt+"Ԫ";
		showEditText(alertStr);
    }
	@Override
	public void initListView(ListView listview) {
		// TODO Auto-generated method stub
		// ����Դ
		list = getListForJoinInfoAdapter();
		JcInfoAdapter adapter = new JcInfoAdapter(this, list);
		listview.setAdapter(adapter);
		/* �б�ĵ����ı��� */
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                       
			}

		};
		listview.setOnItemClickListener(clickListener);
	}

	/**
	 * ����û������б�������������Դ
	 * 
	 * @return
	 */
	protected List<Info> getListForJoinInfoAdapter() {
		return Jc.infos;
	}

	public void initSpinner(int num,int index) {
		List allcountries = new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			if (i > strings.length - 1) {
				break;
			} else {
				allcountries.add(strings[i]);
			}

		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, allcountries);
		adapter.setDropDownViewResource(R.layout.myspinner_dropdown);
		spinner.setAdapter(adapter);
		if(index<num){
			spinner.setSelection(index,false);
		}else{
			spinner.setSelection(allcountries.size()-1,false);
		}
	}
    /**
     * ������ע��
     * @param betcodes
     * @param select
     * @return
     */
	protected  int getAllAmt(List<String> betcodes, int select) {
		// ��ʼ��ԭʼ����
		int[] a = new int[betcodes.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		// ��������
		int[] b = new int[select];

		List<int[]> list = new ArrayList<int[]>();

		// �������
		combine(a, a.length, select, b, select, list);

		// �������ݶ���
		int resultInt = 0; 
		for (int[] result : list) {
			int itemNum = 1;
			for (int p : result) {
				itemNum *= Integer.parseInt(betcodes.get(p));
			}
			resultInt += itemNum;
		}

		return resultInt;
	}

	/**
	 * ��ϵĵݹ��㷨
	 * 
	 * @param a
	 *            ԭʼ����
	 * @param n
	 *            ԭʼ���ݸ���
	 * @param m
	 *            ѡ�����ݸ���
	 * @param b
	 *            ��ű�ѡ�������
	 * @param M
	 *            ������ѡ�����ݸ���
	 * @param list
	 *            ��ż�����
	 */
	public static void combine(int a[], int n, int m, int b[], final int M,List<int[]> list) {
		for (int i = n; i >= m; i--) {
			b[m - 1] = i - 1;
			if (m > 1)
				combine(a, i - 1, m - 1, b, M, list);
			else {
				int[] result = new int[M];
				for (int j = M - 1; j >= 0; j--) {
					result[j] = a[b[j]];
				}
				list.add(result);
			}
		}
	}

	/**
	 * ���ʵ�������
	 */
	public class JcInfoAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
		private List<Info> mList;

		public JcInfoAdapter(Context context, List<Info> list) {
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
			final Info info = (Info) mList.get(position);
			convertView = mInflater.inflate(R.layout.buy_jc_main_listview_item,
					null);
			final ViewHolder holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_time);
			holder.team = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_team);
			holder.home = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_team_name1);
			holder.away = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_team_name2);
			holder.score = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_team_score);
			holder.timeEnd = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_time_end);
			holder.btn1 = (Button) convertView.findViewById(R.id.jc_main_list_item_button1);
			holder.btn2 = (Button) convertView.findViewById(R.id.jc_main_list_item_button2);
			holder.btn3 = (Button) convertView.findViewById(R.id.jc_main_list_item_button3);
			convertView.setTag(holder);
			holder.time.setText(info.getTime());
			holder.team.setText(info.getTeam());
			holder.home.setText(info.getHome());
			holder.away.setText(info.getAway());
			holder.score.setText(info.getLetPoint());
			holder.timeEnd.setText(info.getTimeEnd());
			holder.btn1.setText("ʤ"+info.getWin());
			holder.btn2.setText("ƽ"+info.getLevel());
			holder.btn3.setText("��"+info.getFail());
			if (info.isWin()) {
				holder.btn1.setBackgroundResource(R.drawable.jc_btn_b);
			} else {
				holder.btn1.setBackgroundResource(R.drawable.jc_btn);
			}
			if (info.isLevel()) {
				holder.btn2.setBackgroundResource(R.drawable.jc_btn_b);
			} else {
				holder.btn2.setBackgroundResource(R.drawable.jc_btn);
			}
			if (info.isFail()) {
				holder.btn3.setBackgroundResource(R.drawable.jc_btn_b);
			} else {
				holder.btn3.setBackgroundResource(R.drawable.jc_btn);
			}
			holder.btn1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					info.setWin(!info.isWin());
					if (info.isWin()) {
						info.onclikNum++;
						holder.btn1.setBackgroundResource(R.drawable.jc_btn_b);
					} else {
						info.onclikNum--;
						holder.btn1.setBackgroundResource(R.drawable.jc_btn);
					}
					onclikBtn();
				}
			});
			holder.btn2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					info.setLevel(!info.isLevel());
					if (info.isLevel()) {
						info.onclikNum++;
						holder.btn2.setBackgroundResource(R.drawable.jc_btn_b);
					} else {
						info.onclikNum--;
						holder.btn2.setBackgroundResource(R.drawable.jc_btn);
					}
					onclikBtn();
				}
			});
			holder.btn3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					info.setFail(!info.isFail());
					if (info.isFail()) {
						info.onclikNum++;
						holder.btn3.setBackgroundResource(R.drawable.jc_btn_b);
					} else {
						info.onclikNum--;
						holder.btn3.setBackgroundResource(R.drawable.jc_btn);
					}
					onclikBtn();
				}
			});
			return convertView;
		}

		public void onclikBtn() {
			int onclikNums = 0;
			betcodes = new ArrayList<String>();
			for (int i = 0; i < mList.size(); i++) {
				Info info = (Info) mList.get(i);
				if (info.onclikNum>0) {
					onclikNums++;
					betcodes.add(""+info.onclikNum);
				}
			}
			if (onclikNums < 9) {
				initSpinner(onclikNums - 1,INDEX);
			}
		   showEditText();
		}

		class ViewHolder {
			TextView time;
			TextView team;
			TextView home;
			TextView away;
			TextView score;
			TextView timeEnd;
			Button btn1;
			Button btn2;
			Button btn3;

		}
	}

}
