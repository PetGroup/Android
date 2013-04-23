package com.ruyicai.activity.buy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
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
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView.BufferType;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.activity.buy.jixuan.DanshiJiXuan;
import com.ruyicai.activity.buy.ssc.Ssc;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.ZixuanActivity;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.join.JoinStartActivity;
import com.ruyicai.code.CodeInterface;
import com.ruyicai.code.ssc.OneStarCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.MissConstant;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.SscBalls;
import com.ruyicai.json.miss.dlc.DlcMissJson;
import com.ruyicai.json.miss.ssc.SscMissJson;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.MissInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.SensorActivity;
import com.ruyicai.util.RWSharedPreferences;

public abstract class ZixuanAndJiXuan extends Activity implements OnCheckedChangeListener,OnClickListener,SeekBar.OnSeekBarChangeListener,HandlerMsg{
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
	AlertDialog touZhuDialog;
	TextView issueText;
	TextView alertText;
	TextView textZhuma;
	public View zhixuanview;
	public static String lotnoStr;
	public static boolean isTime = true;
	public static boolean isStart = true;
	public static JSONArray prizeInfos = null;
	public static TextView prizeText = null;
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
	public List<List> missList = new ArrayList<List>();
	public int radioId = 0;
	public boolean isMiss = true;//�Ƿ������©ֵ��ѯ
	public boolean isshouyi=false;
	public int hightballs;
	private CheckBox shouyi;
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
	LinearLayout childtypes= (LinearLayout)findViewById(R.id.sscchildtype);
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
	/**
	 * ��ʼ��ѡ��
	 */
	public void initArea() {
		areaNums = new AreaNum[1];
        String title = "��ѡ��Ͷע����" ;
		areaNums[0] = new AreaNum(10,10, 5, BallResId, 0, 0,Color.RED, title);
		
	}
	
	//��ѡ���л�ֱѡ����ѡ
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(checkedId){		
		case 0:
			iProgressBeishu = 1;iProgressQishu = 1;
			initArea();
			createView(areaNums, sscCode,type,false);
			BallTable=areaNums[0].table;
		break;
		case 1:
			iProgressBeishu = 1;iProgressQishu = 1;
            SscBalls onestar = new SscBalls(1);
			createviewmechine(onestar);
		break;	
		}
			
	}
	
	private TextView mTextSumMoney;
	private ImageButton zixuanTouzhu;
	protected EditText editZhuma;
	protected TextView textTitle;
	public SeekBar mSeekBarBeishu, mSeekBarQishu;
	private TextView mTextBeishu, mTextQishu;
    public AreaNum areaNums[];
    public int iScreenWidth;
    private CodeInterface code;//ע��ӿ���
    protected View view ;
	public Toast toast;
	private boolean toLogin = false;
	
	//����ֱѡҳ��
	public void createView(AreaNum areaNum[],CodeInterface code,int type,boolean isTen) {
	   isJiXuan = false;
	   buyview.removeAllViews();
	   zhixuanview = inflater.inflate(R.layout.ssczhixuan, null);
	   prizeText = (TextView) zhixuanview.findViewById(R.id.buy_zixuan_text_prize_num_title);
	   if(areaNums!=null){
		   for(int i=0;i<areaNums.length;i++){
			   if(areaNums[i].tableLayout!=null){
					areaNums[i].tableLayout.removeAllViews();
					areaNums[i].textTitle.setText("");
			   }
			}
		}
	    areaNums = areaNum;
	    this.code = code;
		iScreenWidth = PublicMethod.getDisplayWidth(this);
		mTextSumMoney = (TextView)zhixuanview. findViewById(R.id.buy_zixuan_text_sum_money);
		editZhuma = (EditText) zhixuanview.findViewById(R.id.buy_zixuan_edit_zhuma);
		textTitle = (TextView) zhixuanview.findViewById(R.id.buy_zixuan_text_title);
		mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));
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
			if(missList==null||missList.size()==0){
				areaNums[i].table =  makeBallTable(areaNums[i].tableLayout, iScreenWidth, info.areaNum,info.ballResId, info.aIdStart, info.aBallViewText, this, this,isTen,null);
			}else{
				int index = 0; 
				if(highttype.equals("SSC")){
					index = areaNums.length-1-i;
				}else if(highttype.equals("DLC")){
					index = i;
				}
				areaNums[i].table =  makeBallTable(areaNums[i].tableLayout, iScreenWidth, info.areaNum,info.ballResId, info.aIdStart, info.aBallViewText, this, this,isTen,missList.get(index));
			}

			areaNums[i].init();
		}
		final TextView textNum = (TextView)zhixuanview.findViewById(R.id.buy_zixuan_add_text_num);
		Button add_dialog = (Button) zhixuanview.findViewById(R.id.buy_zixuan_img_add_delet);
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
	   this.type = type;
	   showEditTitle(type);
	   buyview.addView(zhixuanview);
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
				getCodeInfo(addView);
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
		codeInfo.setTouZhuCode(getZhuma());
		for(AreaNum areaNum:areaNums){
			int[] codes = areaNum.table.getHighlightBallNOs();
			hightballs=codes.length;
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
	
	public void createviewmechine(Balls balles){
	   isJiXuan = true;
	   buyview.removeAllViews();
	   View jixuanview = inflater.inflate(R.layout.sscmechine, null);
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
	private void beginTouZhu() {
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
	private void setSeekWhenAddOrSub(int idFind, final int isAdd,
			final SeekBar mSeekBar, final boolean isBeiShu,View view) {
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
				iBallViewNo++;
				TextView textView = new TextView(context);
				if(missValues!=null){
					String missValue = missValues.get(col);
					textView.setText(missValue);
					if(rankInt[0]== Integer.parseInt(missValue)||rankInt[1]== Integer.parseInt(missValue)){
						textView.setTextColor(Color.RED);
					}
				}
				textView.setGravity(Gravity.CENTER);
				tableRowText.addView(textView, lp);
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
				}
				textView.setGravity(Gravity.CENTER);
				iBallViewNo++;
				tableRowText.addView(textView, lp);
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
		showEditText(highttype);
		changeTextSumMoney();

	}
	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		int nBallId = 0; 
		for(int i=0;i<areaNums.length;i++){
			nBallId = iBallId;
			iBallId = iBallId - areaNums[i].areaNum;
			if(iBallId<0){
				  areaNums[i].table.changeBallState(areaNums[i].chosenBallSum, nBallId);
				  break;
			}

	     }

	}
	
	/**
	 * ��ʾ��������ע��
	 * 
	 */
	public void showEditText(String type){
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
					if(type.equals("SSC")){
					zhumas += (zhuMa[i])+"";
					}else if(type.equals("DLC")){
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
		mTextBeishu = (TextView) view.findViewById(R.id.buy_zixuan_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu = (TextView) view.findViewById(R.id.buy_zixuan_text_qishu);
		mTextQishu.setText("" + iProgressQishu);
		setProgressMax(2000);//���ñ������������ֵ
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
		touZhuDialog = new AlertDialog.Builder(this).setTitle("��ѡ�����").create();
		touZhuDialog.show();
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
		touZhuDialog.getWindow().setContentView(v);
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
		for(int i=0;i<areaNums.length;i++){
            areaNums[i].table.clearAllHighlights();					
		}
		showEditText(highttype);
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
	public void isMissNet(){
		if(!isJiXuan && missList.size()==0){
			getMissNet();
		}
	}
	/**
	 * ��ѯ��©ֵ����
	 */
	public void getMissNet(){
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				str = MissInterface.getInstance().betMiss(lotnoStr, sellWay);
				try {
					JSONObject obj = new JSONObject(str);		
					String msg = obj.getString("message");
					String error = obj.getString("error_code");
					missError(error,msg,obj);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
		t.start();
	}
	private void missError(String error,final String msg,JSONObject obj) throws JSONException {
		if(error.equals(ERROR_WIN)){
			if(highttype.equals("SSC")){
				SscMissJson sscJson = new SscMissJson();
				missList = sscJson.jsonToList(sellWay,obj.getJSONObject("result"));
			}else if(highttype.equals("DLC")){
				DlcMissJson dlcJson = new DlcMissJson();
				missList = dlcJson.jsonToList(sellWay,obj.getJSONObject("result"));
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					onCheckAction(radioId);
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
