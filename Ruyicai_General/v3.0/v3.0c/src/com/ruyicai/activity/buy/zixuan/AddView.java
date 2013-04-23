package com.ruyicai.activity.buy.zixuan;

import java.util.ArrayList;
import java.util.List;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.jixuan.DanshiJiXuan;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.test.IsolatedContext;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.BufferType;

/**
 * ��������
 * 
 * @author Administrator
 * 
 */
public class AddView {
	private List<CodeInfo> codeList = new ArrayList<CodeInfo>();
	private List<CodeInfo> codesharelist = new ArrayList<CodeInfo>();
	private Context context;
	private String title;
	private AlertDialog dialog;
	private View view ;
	private ListView listView;
	private TextView textNum ;
	private AddListAdapter listAdapter;
	private TextView infoText;
	ZixuanActivity zXActivity;
	DanshiJiXuan jXActivity;
	ZixuanAndJiXuan zJActivity;
	private boolean isZiXuan = true;
	private String isJXcode = "";
	private final int MAX_ZHU = 10000;//�����ܳ���2��
	public AddView(TextView textNum,ZixuanActivity zixuan) {
		this.context = zixuan.getContext();
		this.zXActivity = zixuan;
		this.textNum = textNum;
		updateTextNum();
		
	}
	public AddView(TextView textNum,DanshiJiXuan jXActivity,boolean isZiXuan) {
		this.context = jXActivity.getContext();
		this.jXActivity = jXActivity;
		this.textNum = textNum;
		this.isZiXuan = isZiXuan;
		updateTextNum();
		
	}
	public AddView(TextView textNum,ZixuanAndJiXuan zJActivity,boolean isZiXuan) {
		this.isZiXuan = isZiXuan;
		this.zJActivity = zJActivity;
		this.context = zJActivity.getContext();
		this.textNum = textNum;
		updateTextNum();
		
	}
	/**
	 * ˢ�º���������
	 */
	public void updateTextNum(){
		textNum.setText(""+codeList.size());
	}
	public void createDialog(String titleStr) {
		LayoutInflater factory = LayoutInflater.from(context);
		dialog = new AlertDialog.Builder(context).create();
		dialog.setCancelable(false);
		view = factory.inflate(R.layout.buy_add_dialog,null);
		TextView title = (TextView) view.findViewById(R.id.zfb_text_title);
		infoText = (TextView) view.findViewById(R.id.buy_add_dialog_text_info);
		title.setText(titleStr);
		initListView(true);
		Button xuanhao = (Button) view.findViewById(R.id.ok);
		Button touzhu = (Button) view.findViewById(R.id.canel);
		xuanhao.setText(context.getString(R.string.buy_add_dialog_xuanhao));
		touzhu.setText(context.getString(R.string.buy_add_dialog_touzhu));
		xuanhao.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				updateTextNum();
			}
		});
		touzhu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				updateTextNum();
				if(codeList.size()>0){
					if(zJActivity  != null){
						if(isZiXuan){
							isSscZiXuan();
						}else{
							isSscJiXuan();
						}
					}else{
						if(isZiXuan){
							isZiXuan();
						}else{
							isJiXuan();
						}
					}
					
				}else{
					Toast.makeText(context, context.getString(R.string.buy_add_dialog_toast_msg), Toast.LENGTH_SHORT).show();
				}
			}
		});
		updateInfoText();
	}
	private void isZiXuan() {
		RWSharedPreferences pre = new RWSharedPreferences(zXActivity, "addInfo");
		zXActivity.phonenum = pre.getStringValue("phonenum");
		zXActivity.userno = pre.getStringValue("userno");
		if(!zXActivity.userno.equals("")){
			zXActivity.alert();
		}else{
			Intent intentSession = new Intent(zXActivity, UserLogin.class);
			zXActivity.startActivityForResult(intentSession, 0);
		}
	}
	private void isJiXuan(){
		RWSharedPreferences pre = new RWSharedPreferences(jXActivity, "addInfo");
		jXActivity.phonenum = pre.getStringValue("phonenum");
		jXActivity.userno = pre.getStringValue("userno");
		if(!jXActivity.userno.equals("")){
			jXActivity.alert_jixuan();
		}else{
			Intent intentSession = new Intent(jXActivity, UserLogin.class);
			jXActivity.startActivityForResult(intentSession, 0);
		}
	}
	private void isSscZiXuan(){
		RWSharedPreferences pre = new RWSharedPreferences(zJActivity, "addInfo");
		zJActivity.phonenum = pre.getStringValue("phonenum");
		zJActivity.userno = pre.getStringValue("userno");
		if(!zJActivity.userno.equals("")){
			zJActivity.alertZX();
		}else{
			Intent intentSession = new Intent(zXActivity, UserLogin.class);
			zXActivity.startActivityForResult(intentSession, 0);
		}
	}
	private void isSscJiXuan(){
		RWSharedPreferences pre = new RWSharedPreferences(zJActivity, "addInfo");
		zJActivity.phonenum = pre.getStringValue("phonenum");
		zJActivity.userno = pre.getStringValue("userno");
		if(!zJActivity.userno.equals("")){
			zJActivity.alertJX();
		}else{
			Intent intentSession = new Intent(zJActivity, UserLogin.class);
			zJActivity.startActivityForResult(intentSession, 0);
		}
	}
	/**
	 * ��ѡͶעע�봮
	 * @return
	 */
	public String getIsJXcode() {
		return isJXcode;
	}
	public void setIsJXcode(String isJXcode) {
		this.isJXcode += isJXcode;
	}
	public void createCodeInfoDialog(){
		LayoutInflater factory = LayoutInflater.from(context);
		dialog = new AlertDialog.Builder(context).create();
		dialog.setCancelable(false);
		view = factory.inflate(R.layout.buy_add_dialog,null);
		TextView title = (TextView) view.findViewById(R.id.zfb_text_title);
		infoText = (TextView) view.findViewById(R.id.buy_add_dialog_text_info);
		title.setText(context.getString(R.string.buy_add_dialog_title));
		initListView(false);
		updateInfoText();
		Button xuanhao = (Button) view.findViewById(R.id.ok);
		Button touzhu = (Button) view.findViewById(R.id.canel);
		touzhu.setVisibility(Button.GONE);
		xuanhao.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
			}
		});
	}

	/**
	 * ÿ��Ͷע��ע������¸�ֵ
	 */
	public void setCodeAmt(int amt){
		for(CodeInfo codeInfo:codeList){
			codeInfo.setAmt(amt*codeInfo.zhuShu);
		}
	}
	/**
	 * ƴװͶעʱ��ע�봮
	 */
	public String getTouzhuCode(int beishu,int amt){
		String code = "";
		for(int i=0;i<codeList.size();i++){
			CodeInfo codeInfo = codeList.get(i);
			code += codeInfo.getTouZhuCode(beishu,amt);
			if(i!=codeList.size()-1){
				code += "!";
			}
		}
		return code;
	}
	
	//��ʾ��ע��(���������)
	public String getsharezhuma(){
		StringBuffer zhuma = new StringBuffer();
		for(int i=0;i<codesharelist.size();i++){
			for(int j=0;j<codesharelist.get(i).getCodes().size();j++){
		        	String code = codesharelist.get(i).getCodes().get(j);
		        	zhuma.append(code);				
			}
			if(i!=codeList.size()-1){
				zhuma.append(";");
			}
		}
		return zhuma.toString();
	}
	private void updateInfoText() {
		infoText.setText("��ѡ��"+getAllZhu()+"ע��"+"�ܽ��"+getAllAmt()+"Ԫ");
	}
	public int getAllZhu(){
		int allZhu = 0;
		for(CodeInfo codeInfo:codeList){
			allZhu += codeInfo.getZhuShu();
		}
		return allZhu;
	}
	public int getAllAmt(){
		int allAmt = 0;
		for(CodeInfo codeInfo:codeList){
			allAmt += codeInfo.getAmt();
		}
		return allAmt;
	}
	private void initListView(boolean isDelet) {
		listView = (ListView) view.findViewById(R.id.buy_add_dialog_list); 
		listAdapter = new AddListAdapter(context,codeList,isDelet);
		listView.setAdapter(listAdapter);
	}
	public void updateListView(){
		listAdapter.notifyDataSetChanged();
	}
	public int getSize(){
		return codeList.size();
	}
	public CodeInfo initCodeInfo(int amt,int zhuShu){
		return new CodeInfo(amt, zhuShu);
	}
	public void addCodeInfo(CodeInfo codeInfo){
		codeList.add(codeInfo);
	}
	
	public List<CodeInfo> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<CodeInfo> codeList) {
		this.codeList = codeList;
	}
	public void clearInfo(){
		codeList.clear();
		codesharelist.clear();
	}
	public void addshareCodeInfo(CodeInfo codeInfo){
		codesharelist.add(codeInfo);
	}
	public int getshareSize(){
		return codesharelist.size();
	}
	public List<CodeInfo> getshareCodeList() {
		return codesharelist;
	}
	public void setCodeshareList(List<CodeInfo> codesharelist) {
		this.codesharelist = codeList;
	}
	public void showDialog(){
		dialog.show();
		dialog.getWindow().setContentView(view);
	}
	class AddListAdapter extends BaseAdapter{
		private boolean isDelet = true;
		private LayoutInflater mInflater; // �������б���
		private List<CodeInfo> codeInfos = new ArrayList<CodeInfo>();
        public AddListAdapter(Context context,List<CodeInfo> codeInfos,boolean isDelet){
        	mInflater = LayoutInflater.from(context);
        	this.codeInfos = codeInfos;
        	this.isDelet = isDelet;
        }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return codeInfos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return codeInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}  

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			CodeInfo codeInfo = codeInfos.get(position);
			convertView = mInflater.inflate(R.layout.buy_add_dialog_list_item,null);
			TextView textNum =  (TextView) convertView.findViewById(R.id.buy_add_list_item_text_num);
			TextView textCode =  (TextView) convertView.findViewById(R.id.buy_add_list_item_text_code);
			TextView textZhuShu =  (TextView) convertView.findViewById(R.id.buy_add_list_item_text_zhushu);
			TextView textAmt =  (TextView) convertView.findViewById(R.id.buy_add_list_item_text_amt);
			Button btnDelet = (Button)convertView.findViewById(R.id.buy_add_dialog_delet);
			btnDelet.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
						codeInfos.remove(position);
						updateListView();
						updateInfoText();
				}
			});
			textNum.setText(""+(position+1));
	        codeInfo.setTextCodeColor(textCode);
	        textZhuShu.setText(codeInfo.zhuShu+"ע");
	        textAmt.setText(codeInfo.amt+"Ԫ");
	        if(isDelet){
	        	btnDelet.setVisibility(Button.VISIBLE);
	        }else{
	        	btnDelet.setVisibility(Button.GONE);
	        }
			return convertView;
		}

	}
	public class CodeInfo{
		private String touZhuCode;//Ͷעʱ��ע�룬������̨��
		List<String> codes = new ArrayList<String>();
		List<Integer> colors = new ArrayList<Integer>();
		int amt;
		int zhuShu;
		
		public CodeInfo(int amt,int zhuShu){
			this.amt = amt;
			this.zhuShu =zhuShu;
		}
		/**
		 * 
		 * @param beishu
		 * @param amt ��ע���
		 * @return
		 */
		public String getTouZhuCode(int beishu,int amt) {
			return touZhuCode+"_"+PublicMethod.isTen(beishu)+"_"+amt+"_"+zhuShu*amt;
		}
		public String getTouZhuCode() {
			return touZhuCode;
		}
		public void setTouZhuCode(String touZhuCode) {
			this.touZhuCode = touZhuCode;
		}
		public int getAmt() {
			return amt;
		}
		public void setAmt(int amt) {
			this.amt = amt;
		}
		public int getZhuShu() {
			return zhuShu;
		}
		public void setZhuShu(int zhuShu) {
			this.zhuShu = zhuShu;
		}
		public void addAreaCode(String code,int color){
			codes.add(code);
			colors.add(color);
		}
		public List<String> getCodes(){
			return codes;
		}
		public List<Integer> getColors(){
			return colors;
		}
		public void setTextCodeColor(TextView textCode) {
			SpannableStringBuilder builder = new SpannableStringBuilder(); 
			int upLength = 0;
	        for(int i=0;i<getCodes().size();i++){
	        	String code = getCodes().get(i);
	            builder.append(code);
		        builder.setSpan(new ForegroundColorSpan(getColors().get(i)), upLength, code.length()+upLength, Spanned.SPAN_COMPOSING); 
		        if(i!=getCodes().size()-1){
		        	builder.append("|");
		        }
		        upLength = builder.length();
	        }
	        textCode.setText(builder, BufferType.EDITABLE);
		}
	}
}
