	package com.ruyicai.activity.buy.high;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.BaseActivity;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.activity.buy.miss.BuyViewItem;
import com.ruyicai.activity.buy.miss.MainViewPagerAdapter;
import com.ruyicai.activity.buy.miss.NumViewItem;
import com.ruyicai.activity.buy.miss.ZHmissViewItem;
import com.ruyicai.activity.buy.miss.ZixuanActivity;
import com.ruyicai.activity.buy.miss.onclik.BaseOnclik;
import com.ruyicai.activity.buy.ssc.Ssc;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.code.CodeInterface;
import com.ruyicai.code.ssc.OneStarCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.custom.jc.button.MyButton;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.SscBalls;
import com.ruyicai.json.miss.DlcMissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.MissJson;
import com.ruyicai.json.miss.SscMissJson;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.MissInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.SensorActivity;

public abstract class ZixuanAndJiXuan extends BaseActivity implements OnCheckedChangeListener,OnClickListener,SeekBar.OnSeekBarChangeListener,HandlerMsg{
	protected int BallResId[] = { R.drawable.grey, R.drawable.red };
	protected LayoutInflater inflater;
	protected LinearLayout buyview;
	protected BallTable   BallTable ;
	protected RadioGroup group;
	protected CodeInterface sscCode = new OneStarCode();
	protected String []  childtype=null;
	protected boolean isbigsmall=false;
	public int iProgressBeishu = 1, iProgressQishu = 1;
	public BetAndGiftPojo betAndGift=new BetAndGiftPojo();//Ͷע��Ϣ��
	MyHandler handler = new MyHandler(this);//�Զ���handler
	public String phonenum,sessionId,userno;
	ProgressDialog progressdialog;
	public String codeStr;
	public String lotno,sellWay = MissConstant.SSC_5X_ZX;
	public String highttype;
	public int type;
	public final static int NULL = 0;
	public final static int ONE = 1;
	public final static int TWO = 2;
	public final static int THREE = 3;
	public final static int FIVE = 5;
	public final static int TWO_ZUXUAN = 6;
	public final static int TWO_HEZHI = 7;
	public final static int FIVE_TONGXUAN = 8;
	public final static int BIG_SMALL = 9;
	public final static String PAGE = "1";
	public final static String MAX = "5";
	public final static int TIME = 5*60000;//��ȡ�ں��߳�ˢ��ʱ�䵥λ�Ƿ�
	private final static String ERROR_WIN = "0000";
	int iZhuShu;
	int zhushuforshouyi;
    Dialog touZhuDialog;
	TextView issueText;
	TextView alertText;
	TextView textZhuma;
	public static String lotnoStr;
	public static boolean isTime = true;
	public static boolean isStart = true;
	public static JSONArray prizeInfos = null;
	public boolean isViewEnd = true;
	public boolean isViewStart = true;
	public static long startTime;
	long endTime ;
	public AddView addView;
	private boolean isJiXuan = false;
	private Button codeInfo;
	private TextView textTitleAlert;
	protected boolean isTen = true;
	protected int MAX_ZHU = 10000;//ÿ�����Ϊ1��ע
	private final int All_ZHU = 99;
	private Context context;
	public int radioId = 0;
	public boolean isMiss = true;//�Ƿ������©ֵ��ѯ
	public boolean isshouyi=false;
	public int hightballs;
	private CheckBox shouyi;
	private final int UP = 30;
	protected boolean isMove = false;
	public Map<Integer,HighItemView> missView = new HashMap<Integer,HighItemView>();
	float startX;
	float startY;
	protected LinearLayout childtypes;
	protected View layoutMain;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		context = this;
		setContentView(R.layout.sscbuyview);
		childtype= new String[]{"ֱѡ","��ѡ"};
	
	}
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
    * ���ػ�ѡͶעע��
    */
   public abstract String getZhuma(Balls ball);
   /**
    * Ͷע������Ϣ
    */
   public abstract void touzhuNet();
   /**
    * ��ѡ��ť��Ӧ�¼�
    * @param checkedId
    */
   public abstract void onCheckAction(int checkedId);
    
	public void init(){
	childtypes = (LinearLayout)findViewById(R.id.sscchildtype);
	childtypes.setVisibility(View.VISIBLE);
	childtypes.removeAllViews();
	buyview=(LinearLayout)findViewById(R.id.buyview);
	inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	group = new RadioGroup(this); 
	group.setOrientation(RadioGroup.HORIZONTAL);
	// ������Ļ���
     for(int i = 0 ; i < childtype.length ; i++){
         RadioButton radio = new RadioButton(this);
         radio.setText(childtype[i]);
         radio.setTextColor(Color.BLACK);
         radio.setId(i);
         radio.setButtonDrawable(R.drawable.radio_select);
		 radio.setPadding(Constants.PADDING-5, 0, 10, 0);
         group.addView(radio);
         group.setOnCheckedChangeListener(this);
        }
    group.check(0);
     //����ѡ��ť����ӵ�������
    childtypes.addView(group);
	
	}
//	public boolean onTouchEvent(MotionEvent ev) {
//		if(isMove){
//			mGallery.setViewHeight(420);
//			int newPosition = mGallery.getPosition();
//			final int action = ev.getAction();
//			switch (action) {
//			case MotionEvent.ACTION_DOWN:
//			    startX = ev.getX();
//			    startY = ev.getY();
//				break;
//			case MotionEvent.ACTION_MOVE:
//				break;
//			case MotionEvent.ACTION_UP:
//				float absX = Math.abs(ev.getX()-startX);
//				float absY = Math.abs(ev.getY()-startY);
//				BaseOnclik baseOnclik = itemViewArray.get(newPosition).ballOnclik;
//				if(absX<UP&&absY<UP&&baseOnclik!=null){
//					baseOnclik.setRadioLayout(buyview);
//					baseOnclik.setLayoutMian(layoutMain);
//					baseOnclik.onClik(ev.getX(), ev.getY()+mGallery.getViewScrollY());
//				}
//				break;
//			}
//			return mGallery.onGalleryTouchEvent(ev);
//		}else{
//			return false;
//		}
//
//	}
	/**
	 * ��ʼ��ѡ��
	 */
	public AreaNum[] initArea() {
		areaNums = new AreaNum[1];
        String title = "��ѡ��Ͷע����" ;
		areaNums[0] = new AreaNum(10,10, 5, BallResId, 0, 0,Color.RED, title);
		return areaNums;
	}
	
	//��ѡ���л�ֱѡ����ѡ
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		radioId = checkedId;
		switch(checkedId){		
		case 0:
			iProgressBeishu = 1;iProgressQishu = 1;
			initArea();
			createView(areaNums, sscCode,type,false,checkedId);
			BallTable=areaNums[0].table;
		break;
		case 1:
			iProgressBeishu = 1;iProgressQishu = 1;
            SscBalls onestar = new SscBalls(1);
			createviewmechine(onestar,checkedId);
		break;	
		}
			
	}
	
	private TextView mTextSumMoney;
	private ImageButton zixuanTouzhu;
	protected EditText editZhuma;
	protected TextView textTitle;
	public SeekBar mSeekBarBeishu, mSeekBarQishu;
	private EditText mTextBeishu, mTextQishu;
    public AreaNum areaNums[];
    public int iScreenWidth;
    protected CodeInterface code;//ע��ӿ���
    protected View view ;
	public Toast toast;
	private boolean toLogin = false;
//	public DrawerGallery mGallery;
//	public List<BuyViewItem> itemViewArray ;
	protected int lineNum = 3;//�����©ÿ�а�ť��
	protected int textSize = 1; 
	
	protected ViewPager mGallery;
	// ������Ҫ���һ�������ͼȺ���б�����
	public List<BuyViewItem> itemViewArray;
	
	
	/**
	 * �����ɻ���ֱѡҳ��
	 * @param areaNum
	 * @param code
	 * @param type
	 * @param isTen
	 * id ��ǰview��id
	 */
	public void createViewNew(AreaNum areaNum[],CodeInterface code,int type,boolean isTen,int id) {
	   sensor.stopAction();
	   isJiXuan = false;
	   isMove = true;
	   setNewPosition(0);
	   this.code = code;
	   buyview.removeAllViews();
	   if(missView.get(id)==null){
		   inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   View zhixuanview = inflater.inflate(R.layout.ssczhixuan_new, null);
		   initZixuanView(areaNum,zhixuanview);
		   initViewItemNew(areaNum,isTen,zhixuanview);
		   initBotm(zhixuanview);
		   missView.put(id,new HighItemView(zhixuanview,areaNum,addView,itemViewArray));
		   missView.get(id).setmGallery(mGallery);
		   refreshView(type, id);
	   }else{
		   mGallery.setCurrentItem(0);
		   refreshView(type, id);
	   }
	   
	}
	/**
	 * �������ɻ���ֱѡҳ��
	 * @param areaNum
	 * @param code
	 * @param type
	 * @param isTen
	 */
	public void createView(AreaNum areaNum[],CodeInterface code,int type,boolean isTen,int id) {
	   sensor.stopAction();
	   isJiXuan = false;
	   isMove = false;
	   this.code = code;
	   buyview.removeAllViews();
	   if(missView.get(id)==null){
		   inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   View zhixuanview = inflater.inflate(R.layout.ssczhixuan, null);
		   initZixuanView(areaNum,zhixuanview);
		   initViewItem(areaNum,isTen,zhixuanview);	
		   initBotm(zhixuanview);
		   missView.put(id,new HighItemView(zhixuanview,areaNum,addView,null));
		   refreshView(type, id);
	   }else{
		   refreshView(type, id);
	   }
	}
	/**
	 * ��������ҳ��
	 * @param areaNum
	 * @param code
	 * @param type
	 * @param isTen
	 */
	public void createViewDanTuo(AreaNum areaNum[],CodeInterface code,int type,boolean isTen,int id) {
		   sensor.stopAction();
		   isJiXuan = false;
		   isMove = false;
		   this.code = code;
		   buyview.removeAllViews();
		   if(missView.get(id)==null){
			   inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			   View zhixuanview = inflater.inflate(R.layout.ssczhixuan, null);
			   initZixuanView(areaNum,zhixuanview);
			   initViewItemDan(areaNum,isTen,zhixuanview);
			   initBotm(zhixuanview);
			   missView.put(id,new HighItemView(zhixuanview, areaNum,addView,null));
			   missView.get(id).setMissList(missView.get(0).getMissList());
			   refreshView(type, id);
		   }else{
			   refreshView(type, id);
			   missView.get(id).setMissList(missView.get(0).getMissList());
		   }
		   initMissText(missView.get(id).getAreaNum(),true,id);
	}
	private AddView initAddView(View zhixuanview,boolean isZixuan) {
		final TextView textNum = (TextView)zhixuanview.findViewById(R.id.buy_zixuan_add_text_num);
		return new AddView(textNum,this,isZixuan);
	}
	private void refreshView(int type, int id) {
		areaNums = missView.get(id).getAreaNum();
		addView = missView.get(id).getAddView();
		itemViewArray = missView.get(id).getItemViewArray();
		if(missView.get(id).getmGallery()!=null){
			mGallery = missView.get(id).getmGallery();
		}
		this.type = type;
		showEditTitle(type);
		buyview.addView(missView.get(id).getView());
	}
	private void initMissText(AreaNum areaNums[],boolean isDanTuo,int id) {
		List<List> missList = missView.get(id).getMissList();
		for(int i=0;i<areaNums.length;i++){
				int index = 0; 
				if(highttype.equals("SSC")){
					index = areaNums.length-1-i;
				}else if(highttype.equals("DLC")){
					index = i;
				}
				if(missList.size()>0&&missList.size()>index&&!isDanTuo){
					PublicMethod.setMissText(areaNums[i].table.textList, missList.get(index));
				}else if(missList.size()>0){
					PublicMethod.setMissText(areaNums[i].table.textList, missList.get(0));
				}
		}
	}
		
	private void initViewItemDan(AreaNum areaNums[],boolean isTen,View zhixuanview) {
		iScreenWidth = PublicMethod.getDisplayWidth(this);
		int tableLayoutIds[]={R.id.buy_zixuan_table_one,R.id.buy_zixuan_table_two,R.id.buy_zixuan_table_third,R.id.buy_zixuan_table_four,R.id.buy_zixuan_table_five};
		int textViewIds[]={R.id.buy_zixuan_text_one,R.id.buy_zixuan_text_two,R.id.buy_zixuan_text_third,R.id.buy_zixuan_text_four,R.id.buy_zixuan_text_five};
		int linearViewIds[]={R.id.buy_zixuan_linear_one,R.id.buy_zixuan_linear_two,R.id.buy_zixuan_linear_third,R.id.buy_zixuan_linear_four,R.id.buy_zixuan_linear_five};
		//��ʼ��ѡ��
		for(int i=0;i<areaNums.length;i++){
			areaNums[i].initView(tableLayoutIds[i],textViewIds[i],linearViewIds[i],zhixuanview);
			AreaNum info = areaNums[i];
			if(i!=0){
				info.aIdStart=areaNums[i-1].areaNum+areaNums[i-1].aIdStart;
			}
			areaNums[i].table =  makeBallTable(areaNums[i].tableLayout, iScreenWidth, info.areaNum,info.ballResId, info.aIdStart, info.aBallViewText, this, this,isTen,null);
			areaNums[i].init();
		}
	}
	/**
	 * ��ʼ����ѡ�ײ�
	 * @param type
	 */
	private void initBotm(View zhixuanview) {
		Button add_dialog = (Button) zhixuanview.findViewById(R.id.buy_zixuan_img_add_delet);
		final TextView textNum = (TextView)zhixuanview.findViewById(R.id.buy_zixuan_add_text_num);
		addView = new AddView(textNum,this,false);
		add_dialog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(addView.getSize()>0){
					showAddViewDialog();
				}else{
					Toast.makeText(ZixuanAndJiXuan.this, ZixuanAndJiXuan.this.getString(R.string.buy_add_dialog_alert), Toast.LENGTH_SHORT).show();	
				}
			}
		});
		Button add = (Button) zhixuanview.findViewById(R.id.buy_zixuan_img_add);
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
		Button zixuanTouzhu = (Button) zhixuanview.findViewById(R.id.buy_zixuan_img_touzhu);
		zixuanTouzhu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				beginTouZhu();
			}
		});
	}
	private void initViewItem(AreaNum[] areaNums,boolean isTen,View zhixuanview){
		    iScreenWidth = PublicMethod.getDisplayWidth(this);
			int tableLayoutIds[]={R.id.buy_zixuan_table_one,R.id.buy_zixuan_table_two,R.id.buy_zixuan_table_third,R.id.buy_zixuan_table_four,R.id.buy_zixuan_table_five};
			int textViewIds[]={R.id.buy_zixuan_text_one,R.id.buy_zixuan_text_two,R.id.buy_zixuan_text_third,R.id.buy_zixuan_text_four,R.id.buy_zixuan_text_five};
			int linearViewIds[]={R.id.buy_zixuan_linear_one,R.id.buy_zixuan_linear_two,R.id.buy_zixuan_linear_third,R.id.buy_zixuan_linear_four,R.id.buy_zixuan_linear_five};
			//��ʼ��ѡ��
			for(int i=0;i<areaNums.length;i++){
	        	areaNums[i].initView(tableLayoutIds[i],textViewIds[i],linearViewIds[i],zhixuanview);
				AreaNum info = areaNums[i];
				if(i!=0){
					info.aIdStart=areaNums[i-1].areaNum+areaNums[i-1].aIdStart;
				}
				areaNums[i].table =  makeBallTable(areaNums[i].tableLayout, iScreenWidth, info.areaNum,info.ballResId, info.aIdStart, info.aBallViewText, this, this,isTen,null);
				areaNums[i].init();
			}
	}

	/**
	 * ��ʼ��ѡ������
	 */
	public void initViewItemNew(AreaNum[] areaNum,boolean isTen,View zhixuanview) {
	   mGallery = (ViewPager) zhixuanview.findViewById(R.id.buy_zixuan_viewpager);
	   mGallery.removeAllViews();	
	   NumViewItem numView = new NumViewItem(this,areaNum,null);
	   ZHmissViewItem zhView = new ZHmissViewItem(this,null,lineNum,textSize);
	   itemViewArray = new ArrayList<BuyViewItem>();
	   itemViewArray.add(numView);
	   itemViewArray.add(zhView);
		// ���� ViewPager �� Adapter
	   MainViewPagerAdapter MianAdapter = new MainViewPagerAdapter(null);
	   View view = numView.createView();
	   numView.rightBtn(view);
	   numView.rightBtnBG(R.drawable.buy_zh_miss_btn);
	   MianAdapter.addView(view);
	   MianAdapter.addView(zhView.createView());
	   mGallery.setAdapter(MianAdapter);
		// ���õ�һ��ʾҳ��
	   mGallery.setCurrentItem(0);
	   mGallery.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Log.e("arg0",""+arg0);
                setNewPosition(arg0);
				// activity��1��2������2�����غ���ô˷���
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// ��1��2��������1����ǰ����
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});//���ü�����
	}
	/**
	 * ��ʼ������
	 */
	public void initGallery() {

	}
	private List<String> test(){
		List<String> missList = new ArrayList<String>();
		for(int i=0;i<10;i++){
			missList.add(""+i*20);
		}
		return missList;
	}
	private void initZixuanView(AreaNum[] areaNum,View zhixuanview) {
		mTextSumMoney = (TextView)zhixuanview. findViewById(R.id.buy_zixuan_text_sum_money);
		editZhuma = (EditText) zhixuanview.findViewById(R.id.buy_zixuan_edit_zhuma);
		textTitle = (TextView) zhixuanview.findViewById(R.id.buy_zixuan_text_title);
		mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));
	}
	

	/**
	 * ��ѡȡ��Ϣ��ӵ���������
	 */
	private void addToCodes() {
		if(getZhuShu()>MAX_ZHU){
			dialogExcessive();
		}else if(addView.getSize()>=All_ZHU){
			Toast.makeText(ZixuanAndJiXuan.this,ZixuanAndJiXuan.this.getString(R.string.buy_add_view_zhu_alert) , Toast.LENGTH_SHORT).show();
		}else{
			if(type == BIG_SMALL){
				getCodeInfoDX(addView);
			}else{
				if(isMove&&itemViewArray.get(newPosition).isZHmiss){
					getZCodeInfo(addView);
				}else{
					getCodeInfo(addView);
				}
			}
			addView.updateTextNum();
			again();
		}
	}
	private void showAddViewDialog() {
		addView.createDialog(ZixuanAndJiXuan.this.getString(R.string.buy_add_dialog_title));
		addView.showDialog();
	}
	public void getCodeInfo(AddView addView){
		int zhuShu = getZhuShu();
		CodeInfo codeInfo = addView.initCodeInfo(getAmt(zhuShu), zhuShu);
		code.setZHmiss(false);
		codeInfo.setTouZhuCode(getZhuma());
		for(AreaNum areaNum:areaNums){
			int[] codes = areaNum.table.getHighlightBallNOs();
			hightballs=codes.length;
			String codeStr = "";
			for (int i = 0; i < codes.length; i++) {
				codeStr += codes[i];
				if (i != codes.length - 1) {
					codeStr += ",";
				}
			}
			codeInfo.addAreaCode(codeStr,areaNum.textColor);
		}   
		addView.addCodeInfo(codeInfo);
	}
	/**
	 * �����©��ӵ�ѡ����
	 * @param addView
	 */
	public void getZCodeInfo(AddView addView){
		List<MyButton> missBtnList = itemViewArray.get(newPosition).missBtnList;
		for(int i=0;i<missBtnList.size();i++){
			MyButton myBtn = missBtnList.get(i);
			if(myBtn.isOnClick()){
				int zhuShu = 1;
				CodeInfo codeInfo = addView.initCodeInfo(getAmt(zhuShu), zhuShu);
				String codeStr = myBtn.getBtnText();
				code.setZHmiss(true);
				code.setIsZHcode(codeStr);
				codeInfo.setTouZhuCode(getZhuma());
				String[] alertStr = codeStr.split("\\,");
				for(String str:alertStr){
					codeInfo.addAreaCode(str,Color.RED);
				}
				addView.addCodeInfo(codeInfo);
			}
		}
	}
	/**
	 * ���������©ѡ�а�ť��
	 * @return
	 */
	public int getClickNum() {
		int onClickNum = 0;
		List<MyButton> missBtnList = itemViewArray.get(newPosition).missBtnList;
		for(int i=0;i<missBtnList.size();i++){
			MyButton myBtn = missBtnList.get(i);
			if(myBtn.isOnClick()){
				onClickNum++;
			}
		}
		return onClickNum;
	}
	/**
	 * ��Ӵ�С
	 * @param addView
	 */
	public void getCodeInfoDX(AddView addView){
		int zhuShu = getZhuShu();
		CodeInfo codeInfo = addView.initCodeInfo(getAmt(zhuShu), zhuShu);
		codeInfo.setTouZhuCode(getZhuma());
		for(AreaNum areaNum:areaNums){
			String[] codes = areaNum.table.getHighlightStr();
			String codeStr = "";
			for (int i = 0; i < codes.length; i++) {
				codeStr += codes[i];
				if (i != codes.length - 1) {
					codeStr += ",";
				}
			}
			codeInfo.addAreaCode(codeStr,areaNum.textColor);
		}   
		addView.addCodeInfo(codeInfo);
	}
	public int getAmt(int zhuShu){
		if(betAndGift!=null){
			return zhuShu * betAndGift.getAmt();
		}else{
			return 0;
		}
		
	}
	public void showEditTitle(int type){
		textTitle.setTextSize(11);
		switch(type){
		case NULL:
			textTitle.setText("��ѡ:");
			textTitle.setTextSize(15);
			break;
		case ONE:
			textTitle.setText("�Ӹ�λѡ��1���������룬ѡ���뿪���Ÿ�λһ�¼��н�10Ԫ");
			break;
		case TWO:
			textTitle.setText("�Ӻ���λ��ѡ1���������룬ѡ���뿪���ź���λ��λһ�¼��н�100Ԫ");
			break;
		case THREE:
			textTitle.setText("����λ��ѡ1���������룬ѡ���뿪���ź���λ��λһ�¼��н�1000Ԫ");
			break;
		case FIVE:
			textTitle.setText("����λ��ѡ1���������룬ѡ���뿪���Ű�λһ�¼��н�10��Ԫ");
			break;
		case TWO_ZUXUAN:
			textTitle.setText("����ѡ��2�����룬�����������λ������ѡ���м��н�50Ԫ");
			break;
		case TWO_HEZHI:
			textTitle.setText("�������������λ���������֮��,��������Ϊ���ӽ���100Ԫ���Ƕ��ӽ���50Ԫ");
			break;
		case FIVE_TONGXUAN:
			textTitle.setText("����ǧ���١�ʮ����λ��ѡ1���������룬��ע���Ϊ2Ԫ����ѡ�����뿪������һһ��Ӧ����Ϊ2��Ԫ����ѡ����ǰ��λ�����λ�뿪������һһ��Ӧ������Ϊ200Ԫ��" +
					"ǰ��λ���ߺ��λ�뿪������һһ��Ӧ������Ϊ20Ԫ");
			break;
		case BIG_SMALL:
			textTitle.setText("��ʮ����λ���д�С��˫Ͷע����ѡ��С��˫�뿪������ʮ����λ��С��˫һ�¼���4Ԫ");
			break;
		}
		
	}
	//������ѡҳ��

	private Spinner jixuanZhu;
	private LinearLayout zhumaView;
	private SsqSensor sensor = new SsqSensor(this);
	private boolean isOnclik = true;
	public Vector<Balls> balls = new Vector();
	protected Balls ballOne;
	public Toast toastjixuan;
	
	public void createviewmechine(Balls balles,int id){
	   isJiXuan = true;
	   isMove = false;
	   buyview.removeAllViews();
	   if(missView.get(id)==null){
		   View jixuanview = inflater.inflate(R.layout.sscmechine, null);
		   initJiXuanView(balles,jixuanview);
		   missView.put(id,new HighItemView(jixuanview,null,addView,null));
	   }else{
		   initJiXuanView(balles,missView.get(id).getView());
	   }
	}
	private void initJiXuanView(Balls balles,View jixuanview) {
		   buyview.addView(jixuanview);
		   sensor.startAction();
		   this.ballOne = balles;
		   zhumaView = (LinearLayout)jixuanview. findViewById(R.id.buy_danshi_jixuan_linear_zhuma);
		   zhumaView.removeAllViews();
		   toastjixuan = Toast.makeText(this, "����ҡ���ֻ�������ѡ�ţ�", Toast.LENGTH_SHORT);
		   toastjixuan.show();
		   balls = new Vector<Balls>();
				// ��ʼ��spinner
				jixuanZhu = (Spinner) jixuanview.findViewById(R.id.buy_danshi_jixuan_spinner);
				if(isbigsmall){
				TextView textview = (TextView)jixuanview.findViewById(R.id.TextView01);
				textview.setText("ע��:һע");
				jixuanZhu.setVisibility(View.GONE);	
				jixuanZhu.setSelection(0);
				}else{
				jixuanZhu.setSelection(4);
				jixuanZhu.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						int position = jixuanZhu.getSelectedItemPosition();
						if (isOnclik) {
							zhumaView.removeAllViews();
							balls = new Vector();
							for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
								Balls ball = ballOne.createBalls();
								balls.add(ball);
							}
							createTable(zhumaView);
						} else {
							isOnclik = true;
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});
			}
   
			int index = jixuanZhu.getSelectedItemPosition() + 1;
			for (int i = 0; i < index; i++) {
				Balls ball = ballOne.createBalls();
				balls.add(ball);
			}
			createTable(zhumaView);
			sensor.onVibrator();// ��
			Button zixuanTouzhu = (Button)jixuanview. findViewById(R.id.buy_danshi_jixuan_img_touzhu);
			zixuanTouzhu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu();
				}
			});
			final TextView textNum = (TextView)findViewById(R.id.buy_zixuan_add_text_num);
			Button add_dialog = (Button) findViewById(R.id.buy_zixuan_img_add_delet);
			addView = new AddView(textNum,this,false);
			add_dialog.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(addView.getSize()>0){
						showAddViewDialog();
					}else{
						 Toast.makeText(ZixuanAndJiXuan.this, ZixuanAndJiXuan.this.getString(R.string.buy_add_dialog_alert), Toast.LENGTH_SHORT).show();	
					}
				}
			});
			Button add = (Button) findViewById(R.id.buy_zixuan_img_add);
			add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
		           addJxToCodes();
				}
			
			});
	}
	/**
	 * ��ѡȡ��Ϣ��ӵ���������
	 */
	private void addJxToCodes() {
			if(addView.getSize()+balls.size()-1>=All_ZHU){
				Toast.makeText(ZixuanAndJiXuan.this,ZixuanAndJiXuan.this.getString(R.string.buy_add_view_zhu_alert) , Toast.LENGTH_SHORT).show();
			}else{
				if(type == BIG_SMALL){
					getJxCodeInfoDX(addView);
				}else{
					getJxCodeInfo(addView);
				}
				addView.updateTextNum();
				againJx();
			}
	}
	
	public void getJxCodeInfo(AddView addView){
		for(int i=0;i<balls.size();i++){
			Balls ball = balls.get(i);
			String codeStr = getAddZhuma(i);
			CodeInfo codeInfo = addView.initCodeInfo(betAndGift.getAmt(), 1);
			codeInfo.addAreaCode(codeStr,Color.BLACK);
			codeInfo.setTouZhuCode(getZhuma(ball));
			addView.addCodeInfo(codeInfo);
		}   
		addView.setIsJXcode(ballOne.getZhuma(balls, iProgressBeishu));
	}
	public void getJxCodeInfoDX(AddView addView){
		for(int i=0;i<balls.size();i++){
			Balls ball = balls.get(i);
			String codeStr = getAddZhumaDX(i);
			CodeInfo codeInfo = addView.initCodeInfo(betAndGift.getAmt(), 1);
			codeInfo.addAreaCode(codeStr,Color.BLACK);
			codeInfo.setTouZhuCode(getZhuma(ball));
			addView.addCodeInfo(codeInfo);
		}   
		addView.setIsJXcode(ballOne.getZhuma(balls, iProgressBeishu));
	}
	/**
	 * ����ѡ�񷽷�
	 */
	private void againJx() {
		zhumaView.removeAllViews();
		balls = new Vector();
		for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
			Balls ball = ballOne.createBalls();
			balls.add(ball);
		}
		createTable(zhumaView);
	}
	/**
	 * ��������ʾע��
	 * @return
	 */
	private String getAddZhuma(int index){
		String zhumaString = "";
			for(int j=0;j<balls.get(index).getVZhuma().size();j++){
				if(isTen){
					zhumaString += balls.get(index).getTenShowZhuma(j);
				}else{
					zhumaString += balls.get(index).getShowZhuma(j);
				}
				
				if(j!=balls.get(index).getVZhuma().size()-1){
					zhumaString += "|";
				}
			}
		return zhumaString ;	
	}
	/**
	 * ��������ʾע��
	 * @return
	 */
	private String getAddZhumaDX(int index){
		String zhumaString = "";
			for(int j=0;j<balls.get(index).getVZhuma().size();j++){
				 String str =balls.get(index).getTenShowZhuma(j);
				if(str.equals("00")){
					zhumaString += "��";
				}else if(str.equals("01")){
					zhumaString += "С";
				}else if(str.equals("02")){
					zhumaString += "��";
				}else if(str.equals("03")){
					zhumaString += "˫";
				}
				if(j!=balls.get(index).getVZhuma().size()-1){
					zhumaString += "|";
				}
			}
		return zhumaString ;	
	}
	//��ѡ����С��
	public void createTable(LinearLayout layout) {
		for (int i = 0; i < balls.size(); i++) {
			final int index = i;
			int iScreenWidth = PublicMethod.getDisplayWidth(this);
			LinearLayout lines = new LinearLayout(layout.getContext());
			for(int j=0;j<balls.get(i).getVZhuma().size();j++){
			    String color = (String) balls.get(i).getVColor().get(j);
			    TableLayout table;
			    if(isbigsmall){
				table = PublicMethod.makeBallTableJiXuanbigsmall(null,iScreenWidth,BallResId, balls.get(i).getBalls(j), this); 	
			    }else
			    {
				table = PublicMethod.makeBallTableJiXuan(null,iScreenWidth,BallResId, balls.get(i).getBalls(j), this);
			    }
				lines.addView(table);
			}	
			ImageButton delet = new ImageButton(lines.getContext());
			delet.setBackgroundResource(R.drawable.shanchu);
			delet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (balls.size() > 1) {
						zhumaView.removeAllViews();
						balls.remove(index);
						isOnclik = false;
						jixuanZhu.setSelection(balls.size() - 1);
						createTable(zhumaView);
					} else {
						Toast.makeText(ZixuanAndJiXuan.this, getResources().getText(R.string.zhixuan_jixuan_toast),Toast.LENGTH_SHORT).show();

					}
   
				}
			});
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			param.setMargins(10, 5, 0, 0);
			lines.addView(delet, param);
			lines.setGravity(Gravity.CENTER_HORIZONTAL);
            if(i%2==0){
				lines.setBackgroundResource(R.drawable.jixuan_list_bg);
			}
            lines.setPadding(0, 3, 0, 0);
			layout.addView(lines);
			
		}

	}
	/**
	 * Ͷע����
	 */
	public void beginTouZhu() {
            if(isJiXuan){
            	touZhuJX();
            }else{
            	touZhuZX();
            }
	}
	public void touZhuJX(){
		
		if (balls.size() == 0) {
			alertInfo("������ѡ��1ע��Ʊ");
		} else {
			if(addView.getSize()==0){
				addJxToCodes();
				alertJX(); 
			}else{
				showAddViewDialog();
			}
			
		}
	}
	public void touZhuZX(){
		String alertStr = isTouzhu();
		if(alertStr.equals("true")&&addView.getSize()==0){
			addToCodes();
			alertZX();
		}else if(alertStr.equals("true")&&addView.getSize()>0){
			addToCodes();
			showAddViewDialog();
		}else if(addView.getSize()>0){
			showAddViewDialog();
		}else if(alertStr.equals("false")){
			dialogExcessive();
		}else{
			alertInfo(alertStr);
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
	public void setSeekWhenAddOrSub(int idFind, final int isAdd,final SeekBar mSeekBar, final boolean isBeiShu,View view) {
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
		String text = textSumMoney(areaNums,iProgressBeishu);
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
	 * ����BallTable
	 * 
	 * @param LinearLayout
	 *            aParentView ��һ��Layout
	 * @param int aLayoutId ��ǰBallTable��LayoutId
	 * @param int aFieldWidth BallTable����Ŀ�ȣ�����Ļ��ȣ�
	 * @param int aBallNum С�����
	 * @param int aBallViewWidth С����ͼ�Ŀ�ȣ�ͼƬ��ȣ�
	 * @param int[] aResId С��ͼƬId
	 * @param int aIdStart С��Id��ʼ��ֵ
	 * @return BallTable
	 */
	public BallTable makeBallTable(TableLayout tableLayout, int aFieldWidth,int aBallNum, int[] aResId, int aIdStart, int aBallViewText,
			Context context, OnClickListener onclick,boolean isTen,List<String> missValues) {
		TableLayout tabble = tableLayout;
		BallTable iBallTable = new BallTable(aIdStart,context);
		int iBallNum = aBallNum;
		int viewNumPerLine = 8; // ʱʱ������ÿ��С��ĸ���Ϊ10
		if(isTen){
			 viewNumPerLine = 10;
		}
		
		int iFieldWidth = aFieldWidth;
		int scrollBarWidth = 6;
		int iBallViewWidth = (iFieldWidth - scrollBarWidth) / viewNumPerLine - 2;
		int lineNum = iBallNum / viewNumPerLine;
		int lastLineViewNum = iBallNum % viewNumPerLine;
		int margin = (iFieldWidth - scrollBarWidth - (iBallViewWidth + 2)
				* viewNumPerLine) / 2;
		int iBallViewNo = 0;
		int[] rankInt = null;
		if(missValues!=null){
			rankInt = rankList(missValues);
		}
		for (int row = 0; row < lineNum; row++) {
			TableRow tableRow = new TableRow(context);
			TableRow tableRowText = new TableRow(context);

			for (int col = 0; col < viewNumPerLine; col++) {
				String iStrTemp = setTemp(aBallViewText, iBallViewNo,col);
				OneBallView tempBallView = new OneBallView(context);
				tempBallView.setId(aIdStart + iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,aResId);
				tempBallView.setOnClickListener(onclick);
				iBallTable.addBallView(tempBallView);

				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin, 1, 1, 1);
				} else if (col == viewNumPerLine - 1) {
					lp.setMargins(1, 1, margin + scrollBarWidth + 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				TextView textView = new TextView(context);
				if(missValues!=null){
					String missValue = missValues.get(iBallViewNo);
					textView.setText(missValue);
					if(rankInt[0]== Integer.parseInt(missValue)||rankInt[1]== Integer.parseInt(missValue)){
						textView.setTextColor(Color.RED);
					}
				}else{
					textView.setText("0");
				}
				iBallViewNo++;
				textView.setGravity(Gravity.CENTER);
				tableRowText.addView(textView, lp);
				iBallTable.textList.add(textView);
			}
			tabble.addView(tableRow, new TableLayout.LayoutParams(PublicConst.FP, PublicConst.WC));
			tabble.addView(tableRowText, new TableLayout.LayoutParams(PublicConst.FP, PublicConst.WC));
		}
		if (lastLineViewNum > 0) {
			TableRow tableRow = new TableRow(context);
			TableRow tableRowText = new TableRow(context);
			for (int col = 0; col < lastLineViewNum; col++) {
				String iStrTemp = setTemp(aBallViewText, iBallViewNo,col);
				OneBallView tempBallView = new OneBallView(context);
				tempBallView.setId(aIdStart + iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,aResId);
				tempBallView.setOnClickListener(onclick);
				iBallTable.addBallView(tempBallView);
				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin, 1, 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				TextView textView = new TextView(context);
				if(missValues!=null){
					String missValue = missValues.get(iBallViewNo);
					textView.setText(missValue);
					if(rankInt[0] == Integer.parseInt(missValue)||rankInt[1] == Integer.parseInt(missValue)){
						textView.setTextColor(Color.RED);
					}
				}else{
					textView.setText("0");
				}
				iBallViewNo++;
				textView.setGravity(Gravity.CENTER);
				tableRowText.addView(textView, lp);
				iBallTable.textList.add(textView);
			}
			// �½���TableRow��ӵ�TableLayout
			tabble.addView(tableRow, new TableLayout.LayoutParams(PublicConst.FP, PublicConst.WC));
			tabble.addView(tableRowText, new TableLayout.LayoutParams(PublicConst.FP, PublicConst.WC));
		}
		return iBallTable;
	}
	public String setTemp(int aBallViewText, int iBallViewNo,int col) {
		String iStrTemp = "";
		if (aBallViewText == 0) {
			iStrTemp = "" + (iBallViewNo);// С���0��ʼ
		} else if (aBallViewText == 1) {
			iStrTemp = "" + (iBallViewNo + 1);// С���1��ʼ
		} else if (aBallViewText == 3) {
			iStrTemp = "" + (iBallViewNo + 3);// С���3��ʼ
		}
		return iStrTemp;
	}
	public int[] rankList(List<String> myArray){
		int[] rankInt = new int[myArray.size()] ;
		for(int n=0;n<myArray.size();n++){
			rankInt[n] = Integer.parseInt(myArray.get(n));
		}
        // ȡ������Ĵ��� -- ð�ݷ�
        for (int j = 1; j < rankInt.length;j++)
        {
            for (int i = 0; i < rankInt.length - 1; i++)
            {
                 // ��� myArray[i] > myArray[i+1] ���� myArray[i] �ϸ�һλ
                if (rankInt[i] < rankInt[i + 1])
                {
                    int temp = rankInt[i];
                    rankInt[i] = rankInt[i + 1];
                    rankInt[i + 1] = temp;
                }
            }
        }
		return rankInt;
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
	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		int nBallId = 0; 
		if(!isMove){
			for(int i=0;i<areaNums.length;i++){
				nBallId = iBallId;
				iBallId = iBallId - areaNums[i].areaNum;
				if(iBallId<0){
					  areaNums[i].table.changeBallState(areaNums[i].chosenBallSum, nBallId);
					  break;
				}

		     }
		}else{
			AreaNum areaNums[] = itemViewArray.get(0).areaNums;
			for(int i=0;i<areaNums.length;i++){
				nBallId = iBallId;
				AreaNum areaNum = areaNums[i];
				iBallId = iBallId - areaNum.areaNum;
				if(iBallId<0){
					areaNum.table.changeBallState(areaNum.chosenBallSum, nBallId);
					break;
				}
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
			for(int j=0;j<areaNums.length;j++){
				BallTable ballTable = areaNums[j].table;
				int[] zhuMa = ballTable.getHighlightBallNOs();
				if(j!=0){
				    zhumas +=" + ";
				}
				for (int i = 0; i < ballTable.getHighlightBallNOs().length; i++) {
					if(highttype.equals("SSC")){
					zhumas += (zhuMa[i])+"";
					}else if(highttype.equals("DLC")){
					zhumas += PublicMethod.getZhuMa(zhuMa[i]);
					}
					if (i != ballTable.getHighlightBallNOs().length - 1){
						zhumas += ",";
					}
				}
				num+=zhuMa.length;
			}
             if(num==0){
            	 editZhuma.setText("");
            	 showEditTitle(this.type);
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
					  builder.setSpan(new ForegroundColorSpan(areaNums[i].textColor), length-zhuma[i].length(), length, Spanned.SPAN_COMPOSING);  

				  }
			    editZhuma.setText(builder, BufferType.EDITABLE);
			    showEditTitle(NULL);
             }
	}
	

	
	public void alertJX() {
		sensor.stopAction();
		if(touZhuDialog == null){
			initTouZhuDialog(getTouzhuAlert());
		}else{
		    initAlerDialog(getTouzhuAlert());
		}
		touZhuDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				sensor.startAction();
			}
		});

	}
	public void alertZX() {
		if(touZhuDialog == null){
			initTouZhuDialog(getTouzhuAlert());
		}else{
		    initAlerDialog(getTouzhuAlert());
		}
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
		mTextBeishu = (EditText) view.findViewById(R.id.buy_zixuan_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu = (EditText) view.findViewById(R.id.buy_zixuan_text_qishu);
		mTextQishu.setText("" + iProgressQishu);
		setProgressMax(2000);//���ñ������������ֵ
		PublicMethod.setEditOnclick(mTextBeishu,mSeekBarBeishu,new Handler());
		PublicMethod.setEditOnclick(mTextQishu,mSeekBarQishu,new Handler());
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
	 * ��һ������Ͷע��ʾ��
	 */
	public void initTouZhuDialog(String alert){
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v= inflater.inflate(R.layout.alert_dialog_touzhu_new, null);
		RadioGroup group =(RadioGroup) v.findViewById(R.id.RadioGroup01);
		group.setVisibility(RadioGroup.GONE);
		touZhuDialog = new Dialog(this,R.style.MyDialog);
		issueText =(TextView) v.findViewById(R.id.alert_dialog_touzhu_textview_qihao);
		shouyi=(CheckBox)v.findViewById(R.id.checkboxzhuihao);
		shouyi.setChecked(false);
		if(addView.getSize()<=1){
			shouyi.setVisibility(view.VISIBLE);
			}else{
			shouyi.setVisibility(view.GONE);	
		}
		alertText =(TextView) v.findViewById(R.id.alert_dialog_touzhu_text_one);
		textZhuma =(TextView) v.findViewById(R.id.alert_dialog_touzhu_text_zhuma);
		textTitleAlert = (TextView) v.findViewById(R.id.alert_dialog_touzhu_text_zhuma_title);
		issueText.setText(getBatchCode());
		alertText.setText(alert);
		textTitleAlert.setText("ע�룺"+"����"+addView.getSize()+"��Ͷע");
		addView.getCodeList().get(addView.getSize()-1).setTextCodeColor(textZhuma);
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
		initImageView(v);
		CheckBox checkPrize = (CheckBox) v.findViewById(R.id.alert_dialog_touzhu_check_prize);
		checkPrize.setChecked(true);
		checkPrize.setButtonDrawable(R.drawable.check_on_off);
		checkPrize.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					betAndGift.setPrizeend("1");
				}else{
					betAndGift.setPrizeend("0");
				}
			}
		});
		shouyi.setChecked(false);
		shouyi.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				isshouyi=isChecked;
			}
		});
		Button cancel = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_cancel);
		Button ok = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_ok);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				touZhuDialog.cancel();
				clearProgress();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RWSharedPreferences pre = new RWSharedPreferences(ZixuanAndJiXuan.this, "addInfo");
				sessionId = pre.getStringValue("sessionid");
				phonenum = pre.getStringValue("phonenum");
				userno = pre.getStringValue("userno");
				if (sessionId.equals("")) {
					toLogin = true;
					Intent intentSession = new Intent(ZixuanAndJiXuan.this, UserLogin.class);
					startActivityForResult(intentSession, 0);
				} else {
					toLogin = false;				
					touZhu();
				}
			}
	
		});
		
		touZhuDialog.setCancelable(false);
		touZhuDialog.setContentView(v);
		touZhuDialog.show();
	}
	/**
	 * �û�Ͷע
	 */
	private void touZhu() {
		initBet();
		touZhuDialog.cancel();
		if(isshouyi){
			toRevenueActivity();
		}else{
		touZhuNet();
		clearProgress();
		}
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
	public void initAlerDialog(String alert){
		clearProgress();
		issueText.setText(getBatchCode());
		alertText.setText(alert);
		textTitleAlert.setText("ע�룺"+"����"+addView.getSize()+"��Ͷע");
		addView.getCodeList().get(addView.getSize()-1).setTextCodeColor(textZhuma);
		isCodeText(codeInfo);
//		isshouyi = false;
		shouyi.setChecked(false);
		if(addView.getSize()<=1){
			shouyi.setVisibility(view.VISIBLE);
			}else{
			shouyi.setVisibility(view.GONE);	
		}
		touZhuDialog.show();
	}
	/**
	* ��ǰע��
	*/
	public void setZhuShu(int zhushu){
		iZhuShu = zhushu;
	}
	/**
	 * ��ѡͶע��ʾ���е���Ϣ
	 */
	public String getTouzhuAlert(){
	return "ע����" + addView.getAllZhu() + "ע    " + "��" + iProgressQishu * addView.getAllAmt()*iProgressBeishu+ "Ԫ"; 
	}

	/**
	 * ���ص�ǰ����
	 * @return
	 */
	public String getBatchCode(){
		String batchCode = "";
		if(highttype.equals("SSC")){
			batchCode = Ssc.batchCode;
		 }else if(highttype.equals("DLC")){
			batchCode = Dlc.batchCode;
		 }
		return batchCode += "��" ;
	}
	   /**
	    * ��ѡ���ѿ�ע��
	    * @return
	    */
	   public String getJxAlertZhuma(){
			String zhumaString = "";
			for (int i = 0; i < balls.size(); i++) {
				for(int j=0;j<balls.get(i).getVZhuma().size();j++){
					if(highttype.equals("SSC")){
						zhumaString += balls.get(i).getSpecialShowZhuma(j);
					 }else if(highttype.equals("DLC")){
						 zhumaString += balls.get(i).getTenSpecialShowZhuma(j);
					 }
					
					if(j!=balls.get(i).getVZhuma().size()-1){
						zhumaString += ",";
					}
				}
				if (i != balls.size() - 1) {
					zhumaString += "\n";
				}
			}
			codeStr = "ע�룺\n"+zhumaString;
			 return codeStr;	 
	   }

	public String getStrZhuMa(int balls[]) {
		String str = "";
		for (int i = 0; i < balls.length; i++) {
			str += (balls[i]);
			if (i != (balls.length - 1))
				str += ",";
		}
		return str;

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
							public void onClick(DialogInterface dialog,int which) {
							}
						});
		dialog.show();

	}
	/**
	 * ����Ͷע����1��עʱ�ĶԻ���
	 */
	public void dialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ZixuanAndJiXuan.this);
		builder.setTitle(ZixuanAndJiXuan.this.getString(R.string.toast_touzhu_title).toString());
		builder.setMessage("����Ͷע���ܴ���1��ע��");
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
		if(areaNums!=null){
			if(!isMove){
				for(int i=0;i<areaNums.length;i++){
		            areaNums[i].table.clearAllHighlights();					
				}
			}else if(itemViewArray.get(newPosition).isZHmiss){
				 againZH(newPosition);
			}else{
				for(int i=0;i<itemViewArray.get(0).areaNums.length;i++){
		            itemViewArray.get(0).areaNums[i].table.clearAllHighlights();	
				}
			}
		showEditText();
		}
	}
	public void again(int position){
		if(itemViewArray.get(position).isZHmiss){
			 againZH(position);
		}else{
			for(int i=0;i<itemViewArray.get(0).areaNums.length;i++){
	            itemViewArray.get(0).areaNums[i].table.clearAllHighlights();	
			}
			showEditText();
		}
	}
	private void againZH(int position) {
		List<MyButton> missBtnList = itemViewArray.get(position).missBtnList;
		if(missBtnList!=null){
			for(int i=0;i<missBtnList.size();i++){
				MyButton myBtn = missBtnList.get(i);
				if(myBtn.isOnClick()){
					myBtn.onAction();
				}
			}
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			break;
		}
	}
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		again();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(!toLogin){
			sensor.stopAction();
		}
	}
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isTime = true;
		isStart = false;
		isViewEnd = false;
		prizeInfos = null;
		lotnoStr = "";
	}
	
	/**
	 * ���³�ʼ����ѡ����
	 */
	public void againView(){
	    sensor.startAction();
	    sensor.onVibrator();// ��
	    toastjixuan.show();
	    jixuanZhu.setSelection(4);
		zhumaView.removeAllViews();
		balls = new Vector();
		for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
			Balls ball = ballOne.createBalls();
			balls.add(ball);
		}
		createTable(zhumaView);
	}
	/**
	 * ʵ���𶯵���
	 * @author Administrator
	 *
	 */
	class SsqSensor extends SensorActivity {

		public SsqSensor(Context context) {
			getContext(context);
		}

		@Override
		public void action() {
				zhumaView.removeAllViews();
				balls = new Vector<Balls>();
				for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
					Balls ball = ballOne.createBalls();
					balls.add(ball);
				}
				createTable(zhumaView);
		}
	}
	/**
	 * ����������ʾ��
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			// progressdialog.setTitle("Indeterminate");
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			progressdialog.setCancelable(true);
			return progressdialog;
		}
		}
		return null;
	}
	/**
	 * ��ʼ��Ͷע��Ϣ
	 */
	public void initBet(){
		betAndGift.setSessionid(sessionId);
		betAndGift.setPhonenum(phonenum);
		betAndGift.setUserno(userno);
		betAndGift.setBettype("bet");// ͶעΪbet,����Ϊgift 
		betAndGift.setLotmulti(""+iProgressBeishu);//lotmulti    ����   Ͷע�ı���
		betAndGift.setBatchnum(""+iProgressQishu);//batchnum    ׷������ Ĭ��Ϊ1����׷�ţ�
		touzhuNet();
		betAndGift.setAmount(""+addView.getAllAmt()*iProgressBeishu*100);
		betAndGift.setIsSellWays("1");
		betAndGift.setBet_code(addView.getTouzhuCode(iProgressBeishu,betAndGift.getAmt()*100));
		
	}
	
	
	public void toRevenueActivity(){
		  ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		   try {
		   ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
		            objStream.writeObject(betAndGift);
		  } catch (IOException e) {
		   return;  // should not happen, so donot do error handling
		  }
		  
		  Intent intent = new Intent(ZixuanAndJiXuan.this,High_Frequencyrevenue_Recovery.class);
		  intent.putExtra("info", byteStream.toByteArray());
		  intent.putExtra("zhuma", textZhuma.getText().toString());
		  intent.putExtra("lotno", lotnoStr);		
		  intent.putExtra("lingtball", hightballs);	
		  intent.putExtra("zhushu", addView.getAllZhu());	
		  startActivity(intent); 
		  clearArea();
		  clearProgress();
	}
	/**
	 * Ͷע����
	 */
	public void touZhuNet(){
		lotno = PublicMethod.toLotno(betAndGift.getLotno());
		betAndGift.setBatchcode(PublicMethod.toIssue(betAndGift.getLotno()));
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
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
	public void isMissNet(MissJson missJson,String sellWay,boolean isZHMiss){
		if(isZHMiss&&missView.get(radioId).isZMissNet){
			missView.get(radioId).isZMissNet = false;
			List<String> zMissList = missView.get(radioId).getZHMissList();
			if(!isJiXuan && (zMissList==null||zMissList.size()==0)){
				getMissNet(missJson,sellWay,isZHMiss);
			}
		}else if(missView.get(radioId).isMissNet){
			missView.get(radioId).isMissNet = false;
			List<List> missList = missView.get(radioId).getMissList();
			if(!isJiXuan && (missList==null||missList.size()==0)){
				getMissNet(missJson,sellWay,isZHMiss);
			}
		}
	}
	/**
	 * ��ѯ��©ֵ����
	 */
	public void getMissNet(final MissJson missJson,final String sellWay,final boolean isZHMiss){
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			int id = radioId;
			@Override
			public void run() {
				str = MissInterface.getInstance().betMiss(lotnoStr, sellWay);
				try {
					JSONObject obj = new JSONObject(str);		
					String msg = obj.getString("message");
					String error = obj.getString("error_code");
					missJson.jsonToList(sellWay,obj.getJSONObject("result"));
					missError(error,msg,missJson,isZHMiss,id);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
		t.start();
	}
	private void missError(String error,final String msg,final MissJson missJson,final boolean isZHMiss,final int id) throws JSONException {
		if(error.equals(ERROR_WIN)){
			
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(isZHMiss){
						missView.get(id).setZHMissList(missJson.zMissList);
						updateMissView(missJson);
					}else{
						missView.get(id).setMissList(missJson.missList);
						initMissText(missView.get(id).getAreaNum(),false,id);
					}
					
				}
			});
		}else{
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	/**
	 * ˢ����©ֵ����
	 * @param missList
	 */
	private void updateMissView(MissJson missJson) {
		   if(itemViewArray !=null){
			   itemViewArray.get(1).updateView(missJson);
		   }
	}
   
	/**
	 * ��ʽ����������
	 */
	public String formtPrizeInfo(String info){
		if(info.length()>5){
			return info;
		}else{
			String returnStr = "";
			int start = 0;
			while(start < info.length()){
				returnStr += info.substring(start,start+=1);
				if(start != info.length()){
					returnStr += ",";
				}
			}
			return returnStr;
		}
	
	}
	/**
	 * ���ñ������������ֵ
	 * @param max
	 */
	public void setProgressMax(int max){
		mSeekBarBeishu.setMax(max);
		mSeekBarQishu.setMax(max);
	}
	@Override
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String code= addView.getsharezhuma();
		clearArea();
		for(int i=0;i<areaNums.length;i++){
             areaNums[i].table.clearAllHighlights();
	     }
		editZhuma.setText("");
		PublicMethod.showDialog(ZixuanAndJiXuan.this,lotno+code);
	}
	public void clearArea(){
		if(addView != null){
			addView.clearInfo();
			addView.updateTextNum();
		}
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
