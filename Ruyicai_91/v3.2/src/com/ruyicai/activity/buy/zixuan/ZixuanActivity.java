/**
 * 
 */
package com.ruyicai.activity.buy.zixuan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.TextView.BufferType;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.BuyActivity;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.gift.GiftActivity;
import com.ruyicai.activity.join.JoinStartActivity;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.code.CodeInterface;
import com.ruyicai.custom.drawer.DrawerGallery;
import com.ruyicai.custom.gallery.FlingGallery;
import com.ruyicai.custom.gallery.GalleryOnTouch;
import com.ruyicai.custom.gallery.Lights;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;

/**
 * ��ѡ���ʸ���
 * 
 * @author Administrator
 * 
 */
public abstract class  ZixuanActivity extends Activity implements OnClickListener,SeekBar.OnSeekBarChangeListener,HandlerMsg{

	private TextView mTextSumMoney;
	private EditText editZhuma;
	public SeekBar mSeekBarBeishu, mSeekBarQishu;
	protected TextView mTextBeishu;
	private TextView mTextQishu;
	public int iProgressBeishu = 1, iProgressQishu = 1;
    public int iScreenWidth;
    private CodeInterface code;//ע��ӿ���
    public View view ;
	public Toast toast;
	private boolean toLogin = false;
	ProgressDialog progressdialog;
	public BetAndGiftPojo betAndGift=new BetAndGiftPojo();//Ͷע��Ϣ��
	MyHandler handler = new MyHandler(this);//�Զ���handler
	String phonenum,sessionId,userno;
	public boolean isGift = false;//�Ƿ�����
	public boolean isJoin = false;//�Ƿ����
	public boolean isTouzhu = false;//�Ƿ�Ͷע
	RadioButton  check;
	RadioButton  joinCheck;
	RadioButton  touzhuCheck;
	String codeStr;
	String lotno;
	TextView alertText;
	TextView issueText;
	Button codeInfo;
	AlertDialog touZhuDialog;
	TextView textAlert;
	TextView textZhuma;
    TextView textTitle;
	boolean isTen = false;
	public boolean isplw = false;//�Ƿ�������
	public int SCREENUM = 2;// ��Ļ���ͼ����
	public LinearLayout layoutView;
	public DrawerGallery mGallery;
	public List<BuyViewItem> itemViewArray = new ArrayList<BuyViewItem>();
	public String[] mLabelArray = new String[SCREENUM];
	public AddView addView;
	private LinearLayout layoutBottom;
	protected int MAX_ZHU = 10000;
	protected int ALL_ZHU = 99;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = (LinearLayout) inflate.inflate(R.layout.buy_zixuan_activity_new, null);
		setContentView(view);
		layoutView = (LinearLayout) findViewById(R.id.buy_activity_layout_view);
//		mGallery = (DrawerGallery) findViewById(R.id.buy_zixuan_activity_fling_gallery);
		initBottom();
		
	}
	float startX;
	float startY;
//	public boolean onTouchEvent(MotionEvent ev) {
//		mGallery.setViewHeight(420);
//		int position = mGallery.getPosition();
//		final int action = ev.getAction();
//		switch (action) {
//		case MotionEvent.ACTION_DOWN:
//		    startX = ev.getX();
//		    startY = ev.getY();
//			break;
//		case MotionEvent.ACTION_MOVE:
//			break;
//		case MotionEvent.ACTION_UP:
//			float absX = Math.abs(ev.getX()-startX);
//			float absY = Math.abs(ev.getY()-startY);
//			if(absX<30&&absY<30){
//				itemViewArray.get(position).ballOnclik.onClik(ev.getX(), ev.getY()+mGallery.getViewScrollY());
//			}
//			break;
//		}
//		return mGallery.onGalleryTouchEvent(ev);
//	}
	/**
	 * ��ʼ��view
	 */
	public abstract void initViewItem();
    /**
    * ���С����ʾ���
    * @param areaNum
    * @param iProgressBeishu
    * @return
    */
   public abstract String textSumMoney(AreaNum areaNum[],int iProgressBeishu);
   /**
    * �ж��Ƿ�����Ͷע����
    */
   public abstract String isTouzhu();
   /**
    * ����ע��
    */
   public abstract int getZhuShu();
   /**
    * ����Ͷע��ʾ����ʾ��Ϣ
    */
   public abstract String getZhuma();
   /**
    * Ͷע������Ϣ
    */
   public abstract void touzhuNet();
  
	/**
	 * ��ʼ���ײ�
	 */
	public void initBottom(){
		mTextSumMoney = (TextView) findViewById(R.id.buy_zixuan_text_sum_money);
		editZhuma = (EditText) findViewById(R.id.buy_zixuan_edit_zhuma);
		mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));
		layoutBottom = (LinearLayout)findViewById(R.id.buy_activity_bottom_layout);
		final TextView textNum = (TextView)findViewById(R.id.buy_zixuan_add_text_num);
		Button add_dialog = (Button) findViewById(R.id.buy_zixuan_img_add_delet);
		addView = new AddView(textNum,this);
		add_dialog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(addView.getSize()>0){
					showAddViewDialog();
				}else{
					 Toast.makeText(ZixuanActivity.this, ZixuanActivity.this.getString(R.string.buy_add_dialog_alert), Toast.LENGTH_SHORT).show();	
				}
			}
		});
		Button add = (Button) findViewById(R.id.buy_zixuan_img_add);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String alertStr = isTouzhu();
				if(alertStr.equals("true")){
					addToCodes();
				}else if(alertStr.equals("false")){
					dialogExcessive();
				}else{
					alertInfo(alertStr);
				}
			}
		
		});
		Button zixuanTouzhu = (Button) findViewById(R.id.buy_zixuan_img_touzhu);
		zixuanTouzhu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				beginTouZhu();
			}
		});
	
	
	}
	/**
	 * ��ѡȡ��Ϣ��ӵ���������
	 */
	private void addToCodes() {
		if(getZhuShu()>MAX_ZHU){
			dialogExcessive();
		}else if(addView.getSize()>=ALL_ZHU){
			Toast.makeText(ZixuanActivity.this,ZixuanActivity.this.getString(R.string.buy_add_view_zhu_alert) , Toast.LENGTH_SHORT).show();
		}else{
			getCodeInfo(addView);
			addView.updateTextNum();
			again();
		}
	}
	
	public void getCodeInfo(AddView addView){
		int zhuShu = getZhuShu();
		CodeInfo codeInfo = addView.initCodeInfo(getAmt(zhuShu), zhuShu);
		AreaNum[] areaNums = itemViewArray.get(0).areaNums;
		codeInfo.setTouZhuCode(code.zhuma(areaNums,iProgressBeishu,0));
		for(AreaNum areaNum:areaNums){
			int[] codes = areaNum.table.getHighlightBallNOs();
			String codeStr = "";
			for (int i = 0; i < codes.length; i++) {
				codeStr += PublicMethod.isTen(codes[i]);
				if (i != codes.length - 1) {
					codeStr += ",";
				}
			}
			codeInfo.addAreaCode(codeStr,areaNum.textColor);
		}   
		addView.addCodeInfo(codeInfo);
	}
	/**
	 * * isTen ��ʾ��ע��С��ʮ���Ƿ����
	 */
	public void setIsTen(boolean isTen){
		this.isTen = isTen;
	}
	/**
	 *  * @param code      ��Ӧ��ע����
	 */
	public void setCode(CodeInterface code){
		 this.code = code;
	}
	/**
	 * ��ʼ������������������
	 * @param view
	 */
    public void initImageView(View view){
		mSeekBarBeishu = (SeekBar) view.findViewById(R.id.buy_zixuan_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mSeekBarQishu = (SeekBar) view.findViewById(R.id.buy_zixuan_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(this);
		mSeekBarQishu.setProgress(iProgressQishu);

		mTextBeishu = (TextView) view.findViewById(R.id.buy_zixuan_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu = (TextView) view.findViewById(R.id.buy_zixuan_text_qishu);
		mTextQishu.setText("" + iProgressQishu);
		/*
		 * ����Ӽ�ͼ�꣬��seekbar�������ã�
		 * 
		 * @param int idFind, ͼ���id View iV, ��ǰ��view final int isAdd, 1��ʾ�� -1��ʾ��
		 * final SeekBar mSeekBar
		 * 
		 * @return void
		 */
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_subtract_beishu, -1,mSeekBarBeishu, true,view);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_add_beishu, 1, mSeekBarBeishu,true,view);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_subtract_qihao, -1,mSeekBarQishu, false,view);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_add_qishu, 1, mSeekBarQishu,false,view);
    }
	/**
	 * Ͷע����
	 */
	public void beginTouZhu() {

		toLogin = false;
		String alertStr = isTouzhu();
		if(alertStr.equals("true")&&addView.getSize()==0){
			if(getZhuShu()>MAX_ZHU){
				dialogExcessive();
			}else if(addView.getSize()>=ALL_ZHU){
				Toast.makeText(ZixuanActivity.this,ZixuanActivity.this.getString(R.string.buy_add_view_zhu_alert) , Toast.LENGTH_SHORT).show();
			}else{
			addToCodes();
			alert();}
		}else if(alertStr.equals("true")&&addView.getSize()>0){
			addToCodes();
			showAddViewDialog();
		}else if(addView.getSize()>0){
			showAddViewDialog();
		}else if(alertStr.equals("false")){
			dialogExcessive();
		}else if(alertStr.equals("no")){
			dialogZhixuan();
		}else{
			alertInfo(alertStr);
		}
		
	}
	private void showAddViewDialog() {
		addView.createDialog(ZixuanActivity.this.getString(R.string.buy_add_dialog_title));
		addView.showDialog();
	}
	public int getAmt(int zhuShu){
		if(betAndGift!=null){
			return zhuShu * betAndGift.getAmt();
		}else{
			return 0;
		}
		
	}

	/**
	 * fqc edit ���һ������ isBeiShu ���жϵ�ǰ�Ǳ����������� ��
	 * 
	 * @param idFind
	 * @param iV
	 * @param isAdd
	 * @param mSeekBar
	 * @param isBeiShu
	 */
	protected void setSeekWhenAddOrSub(int idFind, final int isAdd,final SeekBar mSeekBar, final boolean isBeiShu,View view) {
		ImageButton subtractBeishuBtn = (ImageButton) view.findViewById(idFind);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isBeiShu) {
					if (isAdd == 1) {
						mSeekBar.setProgress(++iProgressBeishu);
					} else {
						mSeekBar.setProgress(--iProgressBeishu);
					}
					iProgressBeishu = mSeekBar.getProgress();
				} else {
					if (isAdd == 1) {
						mSeekBar.setProgress(++iProgressQishu);
					} else {
						mSeekBar.setProgress(--iProgressQishu);
					}
					iProgressQishu = mSeekBar.getProgress();
				}
			}
		});
	}

	/**
	 * seekBar����ļ�����
	 */
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (progress < 1)
		seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();
		switch (seekBar.getId()) {
		case R.id.buy_zixuan_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
//			changeTextSumMoney();
			break;
		case R.id.buy_zixuan_seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			stateCheck();
			break;
		default:
			break;
		}
		alertText.setText(getTouzhuAlert());

	}

	/**
	 * ����ע���ͽ��ķ���
	 * 
	 */
	public void changeTextSumMoney() {     
		String text = textSumMoney(itemViewArray.get(0).areaNums,iProgressBeishu);
		if(toast == null){
			toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		}else{
			toast.setText(text);
			toast.show();
		}

	}





	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		int nBallId = 0; 
		for(int i=0;i<itemViewArray.get(0).areaNums.length;i++){
			AreaNum areaNums1 = itemViewArray.get(0).areaNums[i];
//			AreaNum areaNums2 = itemViewArray.get(1).areaNums[i];
			nBallId = iBallId;
			iBallId = iBallId - areaNums1.areaNum;
			if(iBallId<0){
				areaNums1.table.changeBallState(areaNums1.chosenBallSum, nBallId);
//				areaNums2.table.changeBallState(areaNums2.chosenBallSum, nBallId);
				break;
			}

	     }
	

	}
	/**
	 * ��ʾ��������ע��
	 * 
	 */
	public void showEditText(){
         SpannableStringBuilder builder = new SpannableStringBuilder(); 
		 String zhumas = "";
		 int num = 0;//����С����
		 int length = 0;
			for(int j=0;j<itemViewArray.get(0).areaNums.length;j++){
				BallTable ballTable = itemViewArray.get(0).areaNums[j].table;
				int[] zhuMa = ballTable.getHighlightBallNOs();
				if(j!=0){
				    zhumas +=" + ";
				}
				for (int i = 0; i < ballTable.getHighlightBallNOs().length; i++) {
					if(isTen){
						zhumas += PublicMethod.getZhuMa(zhuMa[i]);
					}else{
						zhumas += zhuMa[i] + "";
					}
					
					if (i != ballTable.getHighlightBallNOs().length - 1){
						zhumas += ",";
					}
				}
				num+=zhuMa.length;
			}	
             if(num==0){
            	 editZhuma.setText("");
             }else{
				builder.append(zhumas);
				String zhuma[]=zhumas.split("\\+");
				  for(int i=0;i<zhuma.length;i++){
					  if(i!=0){
							length += zhuma[i].length()+1;
						  }else{
							length += zhuma[i].length();
						  }
				     if(i!=zhuma.length-1){	
						  builder.setSpan(new ForegroundColorSpan(Color.BLACK), length, length+1, Spanned.SPAN_COMPOSING);  
					  }
					  builder.setSpan(new ForegroundColorSpan(itemViewArray.get(0).areaNums[i].textColor), length-zhuma[i].length(), length, Spanned.SPAN_COMPOSING);  

				  }
			    editZhuma.setText(builder, BufferType.EDITABLE);
             }
	}
	/**
	 * ����ʽͶע���ú���
	 * @param string  ��ʾ����Ϣ
	 * @return
	 */
	public void alert() {
		touzhuNet();//Ͷע���ͺͲ���
		toLogin = true;
		isGift = false;
		isJoin = false;
		isTouzhu = true;
		if(touZhuDialog == null){
			initTouZhuDialog();
		}else{
		    initAlerDialog();
		}
	}
	/**
	 * ��һ������Ͷע��ʾ��
	 */
	public void initTouZhuDialog(){
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v= inflater.inflate(R.layout.alert_dialog_touzhu_new, null);
		touZhuDialog = new AlertDialog.Builder(this).setTitle("��ѡ�����").create();
		touZhuDialog.show();
		initImageView(v);
		if(betAndGift.isZhui()){
			initZhuiJia(v);
		}
		issueText =(TextView) v.findViewById(R.id.alert_dialog_touzhu_textview_qihao);
		alertText =(TextView) v.findViewById(R.id.alert_dialog_touzhu_text_one);
		textZhuma =(TextView) v.findViewById(R.id.alert_dialog_touzhu_text_zhuma);
		textTitle = (TextView) v.findViewById(R.id.alert_dialog_touzhu_text_zhuma_title);
		addView.getCodeList().get(addView.getSize()-1).setTextCodeColor(textZhuma);
		issueText.setText(PublicMethod.toIssue(betAndGift.getLotno())+"��");
		alertText.setText(getTouzhuAlert());
		textTitle.setText("ע�룺"+"����"+addView.getSize()+"��Ͷע");
		Button cancel = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_cancel);
		Button ok = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_ok);
		codeInfo = (Button) v.findViewById(R.id.alert_dialog_touzhu_btn_look_code);
		isCodeText(codeInfo);
		codeInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addView.createCodeInfoDialog();
				addView.showDialog();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toLogin = false;
				touZhuDialog.cancel();
				clearProgress();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RWSharedPreferences pre = new RWSharedPreferences(ZixuanActivity.this, "addInfo");
				sessionId = pre.getStringValue("sessionid");
				phonenum = pre.getStringValue("phonenum");
				userno = pre.getStringValue("userno");
				if (userno.equals("")) {
					toLogin = true;
					Intent intentSession = new Intent(ZixuanActivity.this, UserLogin.class);
					startActivityForResult(intentSession, 0);
				} else {
					touZhu();
				}
			}
		});
		CheckBox checkPrize = (CheckBox) v.findViewById(R.id.alert_dialog_touzhu_check_prize);
		checkPrize.setChecked(true);
		checkPrize.setButtonDrawable(R.drawable.check_on_off);
		checkPrize.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					betAndGift.setPrizeend("1");
				}else{
					betAndGift.setPrizeend("0");
				}
			}
		});
		check = (RadioButton) v.findViewById(R.id.alert_dialog_touzhu_check);
		joinCheck = (RadioButton) v.findViewById(R.id.alert_dialog_join_check);
		touzhuCheck = (RadioButton) v.findViewById(R.id.alert_dialog_touzhu1_check);
		touzhuCheck.setChecked(true);
		textAlert = (TextView) v.findViewById(R.id.alert_dialog_touzhu_text_alert);
		check.setPadding(50, 0, 0, 0);
	    check.setButtonDrawable(R.drawable.check_select);
		// ʵ�ּ�ס���� �� ��ѡ���״̬
	    check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
                    isGift = isChecked;
			}
		});
		joinCheck.setPadding(50, 0, 0, 0);
	    joinCheck.setButtonDrawable(R.drawable.check_select);
		// ʵ�ּ�ס���� �� ��ѡ���״̬
	    joinCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
                    isJoin = isChecked;
			}
		});
		touzhuCheck.setPadding(50, 0, 0, 0);
	    touzhuCheck.setButtonDrawable(R.drawable.check_select);
		// ʵ�ּ�ס���� �� ��ѡ���״̬
	    touzhuCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				    isTouzhu = isChecked;
			}
		});
		stateCheck();
		touZhuDialog.setCancelable(false);
		touZhuDialog.getWindow().setContentView(v);
	}
	/**
	 * Ͷע����
	 */
	private void touZhu() {
		toLogin = false;
		touZhuDialog.cancel();
		initBet();
		// TODO Auto-generated method stub
		if(isGift){
			toActivity(addView.getsharezhuma());
		}else if(isJoin){
			toJoinActivity();
		}else if(isTouzhu){
			touZhuNet();
		}
		clearProgress();
	}
	/**
	 * ��ʾ׷��Ͷע
	 * @param view
	 */
	private void initZhuiJia(View view) {
		LinearLayout toggleLinear = (LinearLayout)view.findViewById(R.id.buy_zixuan_linear_toggle);
		toggleLinear.setVisibility(LinearLayout.VISIBLE);
		ToggleButton zhuijiatouzhu = (ToggleButton)view.findViewById(R.id.dlt_zhuijia);
		zhuijiatouzhu.setOnCheckedChangeListener(new OnCheckedChangeListener() {			

			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if(isChecked){
					betAndGift.setAmt(3);
					betAndGift.setIssuper("0");
				}else{
					betAndGift.setIssuper("");
					betAndGift.setAmt(2);
				}
				addView.setCodeAmt(betAndGift.getAmt());
				alertText.setText(getTouzhuAlert());
			}
		});
	}
	private void isCodeText(Button codeInfo) {
		if(addView.getSize()>1){
			codeInfo.setVisibility(Button.VISIBLE);
		}else{
			codeInfo.setVisibility(Button.GONE);
		}
	}
	/**
	 * ��ձ����������Ľ�����
	 */
	public void clearProgress(){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mSeekBarQishu.setProgress(iProgressQishu);
	}
	/**
	 * �ٴ�������ʾ��
	 */
	public void initAlerDialog(){
		touzhuCheck.setChecked(true);
		clearProgress();
		stateCheck();
		issueText.setText(PublicMethod.toIssue(betAndGift.getLotno())+"��");
		textTitle.setText("ע�룺"+"����"+addView.getSize()+"��Ͷע");
		addView.getCodeList().get(addView.getSize()-1).setTextCodeColor(textZhuma);
		isCodeText(codeInfo);
		alertText.setText(getTouzhuAlert());
		touZhuDialog.show();
	}
    /**
     * �ж������Ƿ����1
     */
    public void stateCheck(){
		if(iProgressQishu>1){
			isGift = false;
			isJoin = false;
			isTouzhu = true;
			check.setVisibility(CheckBox.GONE);
			joinCheck.setVisibility(CheckBox.GONE);
			touzhuCheck.setVisibility(CheckBox.GONE);
			textAlert.setVisibility(TextView.VISIBLE);
		}else{
			check.setVisibility(CheckBox.VISIBLE);
			joinCheck.setVisibility(CheckBox.VISIBLE);
			touzhuCheck.setVisibility(CheckBox.VISIBLE);
			textAlert.setVisibility(TextView.GONE);
		}
    }
	public void toActivity(String zhuma){
		  clearAddView();
		  ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		   try {
		   ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
		            objStream.writeObject(betAndGift);
		  } catch (IOException e) {
		   return;  
		  }
		  
		  Intent intent = new Intent(ZixuanActivity.this, GiftActivity.class);
		  intent.putExtra("info", byteStream.toByteArray());
		  intent.putExtra("zhuma", zhuma);
		  startActivity(intent); 


	}
	public void toJoinActivity(){
		  clearAddView();
		  ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		   try {
		   ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
		            objStream.writeObject(betAndGift);
		  } catch (IOException e) {
		   return;  // should not happen, so donot do error handling
		  }
		  
		  Intent intent = new Intent(ZixuanActivity.this,JoinStartActivity.class);
		  intent.putExtra("info", byteStream.toByteArray());
		  startActivity(intent); 


	}
	
	/**
	 * Ͷע����
	 */
	public void touZhuNet(){
		progressdialog = UserCenterDialog.onCreateDialog(this);
		progressdialog.show();
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				str = BetAndGiftInterface.getInstance().betOrGift(betAndGift);
				try {
					JSONObject obj = new JSONObject(str);		
					String msg = obj.getString("message");
					String error = obj.getString("error_code");
					handler.handleMsg(error,msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				progressdialog.dismiss();
			}

		});
		t.start();
	}
	/**
	 * ��ʼ��Ͷע��Ϣ
	 */
	public void initBet(){
		betAndGift.setIsSellWays("1");
		betAndGift.setAmount(""+addView.getAllAmt()*iProgressBeishu*100);
		betAndGift.setSessionid(sessionId);
		betAndGift.setPhonenum(phonenum);
		betAndGift.setUserno(userno);
		betAndGift.setBettype("bet");// ͶעΪbet,����Ϊgift 
		betAndGift.setLotmulti(""+iProgressBeishu);//lotmulti    ����   Ͷע�ı���
		betAndGift.setBatchnum(""+iProgressQishu);//batchnum    ׷������ Ĭ��Ϊ1����׷�ţ�
		betAndGift.setBet_code(addView.getTouzhuCode(iProgressBeishu,betAndGift.getAmt()*100));
		lotno = PublicMethod.toLotno(betAndGift.getLotno());
		betAndGift.setBatchcode(PublicMethod.toIssue(betAndGift.getLotno()));//�ں�
		
	}

	/**
	 * Ͷע��ʾ���е���Ϣ
	 */
	public String getTouzhuAlert(){
		
		return "ע����"
				+ addView.getAllZhu() + "ע     " 
				+ "��" + 
				+ iProgressQishu * addView.getAllAmt() * iProgressBeishu
				+ "Ԫ"; 
	}
	/**
	 * ��ʾ��1 ��������ѡ�����
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	public void alertInfo(String string) {   
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}

						});
		dialog.show();

	}
	/**
	 * ����Ͷע����1��עʱ�ĶԻ���
	 */
	public void dialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ZixuanActivity.this);
		builder.setTitle(ZixuanActivity.this.getString(R.string.toast_touzhu_title).toString());
		builder.setMessage("����Ͷע���ܴ���"+MAX_ZHU+"ע��");
		// ȷ��
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}

				});
		builder.show();
	}
	// wangyl 7.23 ����3DͶעע������600עʱ�ĶԻ���
	/**
	 * ����3DֱѡͶעע������600עʱ�ĶԻ���
	 */
	public void dialogZhixuan() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ZixuanActivity.this);
		builder.setTitle(getResources().getString(R.string.toast_touzhu_title).toString());
		builder.setMessage("��ѡ�񲻴���600עͶע");
		// ȷ��
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}

				});
		builder.show();
	}
	/**
	 * ���·���
	 * 
	 */
	public void again(){
		if(itemViewArray.get(0).areaNums!=null){
		for(int i=0;i<itemViewArray.get(0).areaNums.length;i++){
            itemViewArray.get(0).areaNums[i].table.clearAllHighlights();	
//            itemViewArray.get(1).areaNums[i].table.clearAllHighlights();	
		}
		showEditText();
		}
	}
	/**
	 * С�򱻵���¼�
	 * 
	 * @param v
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int iBallId = v.getId();
		isBallTable(iBallId);
		showEditText();
		changeTextSumMoney();

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
//		    touZhu();
			break;
		}
	}
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(!toLogin){
			again();
		}
	}

	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	public void errorCode_0000() {
		// TODO Auto-generated method stub
		String codeStr=addView.getsharezhuma();
		clearAddView();
		for(int i=0;i<itemViewArray.get(0).areaNums.length;i++){
             itemViewArray.get(0).areaNums[i].table.clearAllHighlights();
//             itemViewArray.get(1).areaNums[i].table.clearAllHighlights();
	     }
		
		showEditText();
	    PublicMethod.showDialog(ZixuanActivity.this,lotno+codeStr);
	    
	       
	}
	private void clearAddView() {
		addView.clearInfo();
		addView.updateTextNum();
	}

	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}

	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
	/**
	 * �˳���ʾ
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	public void alertExit(String string) {   
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ܰ��ʾ")
				.setMessage(string).setNeutralButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
                                    finish();
							}
						}).setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
		dialog.show();

	}
	/**
	 * ��д�Żؽ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case 4:
			if(addView.getSize()!=0){
				alertExit(getString(R.string.buy_alert_exit));
			}else{
				finish();
			}
			break;
		}
		return false;
	}

}
