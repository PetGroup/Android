package com.ruyicai.activity.buy.zc;

import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.zc.pojo.TeamInfo;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
/**
 * ��ʸ���
 * @author miao
 */
public abstract class FootBallLotteryFather extends Activity implements OnClickListener,SeekBar.OnSeekBarChangeListener{
	String	batchCode;
    public int iScreenWidth;
    public SeekBar mSeekBarBeishu;
    public int iProgressBeishu = 1;
    protected TextView mTextBeishu;
    String sessionid,phonenum,userno;
    private boolean toLogin = false;
    private BuyImplement buyImplement;//ͶעҪʵ�ֵķ���
    BetAndGiftPojo betPojo = new BetAndGiftPojo();
	public boolean isGift = false;//�Ƿ�����
	public boolean isJoin = false;//�Ƿ����
	public boolean isTouzhu = false;
	Button layout_football_issue;
	TextView layout_football_time;
	/**
	 * ���Ͷע��ť
	 */
	ImageButton sfc_btn_touzhu;
	ListView footBallList;
	String qihaoxinxi[] = new String[4];// ����ںţ���ֹʱ�䣬����
	Vector<TeamInfo> teamInfos = new Vector<TeamInfo>();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iScreenWidth = PublicMethod.getDisplayWidth(this);
		setContentView(R.layout.buy_footballlottery_layout);
		layout_football_issue = (Button)findViewById(R.id.layout_football_issue);
		layout_football_time = (TextView)findViewById(R.id.layout_football_time);
		footBallList = (ListView)findViewById(R.id.buy_footballlottery_list);
		createVeiw();
	}
	public	void initBatchCode(final String lotteryType){
		JSONObject footballLotnoInfo = PublicMethod.getCurrentLotnoBatchCode(lotteryType);
		if (footballLotnoInfo != null) {
			// �ɹ���ȡ�����ں���Ϣ
			try {
				batchCode = footballLotnoInfo.getString("batchCode");
				qihaoxinxi[0] = batchCode;
				qihaoxinxi[1] = footballLotnoInfo.getString("endTime");
				qihaoxinxi[2] = lotteryType;

			} catch (JSONException e) {
				qihaoxinxi[0] = "";
				qihaoxinxi[1] = "";
				qihaoxinxi[2] = lotteryType;
			}
		}	
	}
	/**
	 * ��ʼ����������ͽ�ֹʱ����Ϣ
	 */
	abstract void initBatchCodeView();
	/**
	 * Ͷע����
	 */
	public void beginTouZhu() {
		RWSharedPreferences pre = new RWSharedPreferences(
				FootBallLotteryFather.this, "addInfo");
		sessionid = pre.getStringValue("sessionid");
		phonenum = pre.getStringValue("phonenum");
		userno = pre.getStringValue("userno");
		if (sessionid.equals("")) {
			toLogin = true;
			Intent intentSession = new Intent(FootBallLotteryFather.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {
			toLogin = false;
			buyImplement.isTouzhu();
			
		}

	}
	/**
	 *��������ҳ��
	 */
	public void createVeiw() {
		mSeekBarBeishu = (SeekBar) findViewById(R.id.buy_footballlottery_seekbar_muti);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		iProgressBeishu = 1;
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mTextBeishu = (TextView) findViewById(R.id.buy_footballlottery_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		setSeekWhenAddOrSub(R.id.buy_footballlottery_img_subtract_beishu,  -1,mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.buy_footballlottery_img_add_beishu,  1,mSeekBarBeishu, true);

		sfc_btn_touzhu = (ImageButton) findViewById(R.id.buy_footballlottery_img_touzhu);
		sfc_btn_touzhu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				beginTouZhu(); // 1��ʾ��ǰΪ��ʽ
			}
		});
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
	protected void setSeekWhenAddOrSub(int idFind, final int isAdd,final SeekBar mSeekBar, final boolean isBeiShu) {
		ImageButton subtractBeishuBtn = (ImageButton) findViewById(idFind);
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
				} else {}
			}
		});
	}
	/**
	 * <b>������ʽ�������б���ѡ����ķ���</b>
	 * @param linear  ���б��Ӧ��LinearLayout
	 * @param position	���б��λ��
	 */
	public void setFootballListItemBackground(LinearLayout linear,int position){
		if(position%2==0){
			Drawable drawable = getResources().getDrawable(R.drawable.list_ou);
			linear.setBackgroundDrawable(drawable);
		}else{
			Drawable drawable = getResources().getDrawable(R.drawable.list_bg_white);
			linear.setBackgroundDrawable(drawable);
		}
	}
	/**
	 * ������ʽ����BallTable
	 * @param LinearLayout  aParentView ��һ��Layout
	 * @param int aLayoutId ��ǰBallTable��LayoutId
	 * @param int aFieldWidth BallTable����Ŀ�ȣ�����Ļ��ȣ�
	 * @param int aBallNum С�����
	 * @param int aBallViewWidth С����ͼ�Ŀ�ȣ�ͼƬ��ȣ�
	 * @param int[] aResId С��ͼƬId
	 * @param int aIdStart С��Id��ʼ��ֵ
	 * @return BallTable
	 */
	 BallTable makeBallTable(LinearLayout aParentView, int aLayoutId,
			int aFieldWidth, int aBallNum, int[] aResId,int aIdStart) {

		BallTable iBallTable = new BallTable(aParentView, aLayoutId, aIdStart);
		// BallTable iBallTable=new BallTable(aLayoutId,aIdStart);
		int iBallNum = aBallNum;
		int iFieldWidth = aFieldWidth;
		/** �������Ŀ�� */
		int scrollBarWidth = 6;
		/** ÿһ�е�С������ */
		int viewNumPerLine = 4;
		int iBallViewWidth = (iFieldWidth - scrollBarWidth)/viewNumPerLine-2;
		/** �е����� */
		int lineNum = iBallNum / viewNumPerLine;
		/** ���һ�е�view����Ŀ */
		int lastLineViewNum = iBallNum % viewNumPerLine;
		/** �հ׵Ĵ�С */
		int margin = (iFieldWidth - scrollBarWidth - (iBallViewWidth + 2)* viewNumPerLine) / 2;
		int iBallViewNo = 0;

		for (int row = 0; row < lineNum; row++) {
			TableRow tableRow = new TableRow(aParentView.getContext());
			for (int col = 0; col < viewNumPerLine; col++) {
				/** ������ʾ������ */
				String iStrTemp = "" + (iBallViewNo + 1);
				if (iStrTemp.equals("1") ) {
					iStrTemp = "0";
				} else if (iStrTemp.equals("2")) {
					iStrTemp = "1";
				} else if (iStrTemp.equals("3") ) {
					iStrTemp = "2";
				} else {
					iStrTemp = "3+";
				}
				/** ʵ����һ��BallView���� */
				OneBallView tempBallView = new OneBallView(aParentView
						.getContext());
				/** Ϊ���tempView����һ��Id */
				tempBallView.setId(aIdStart + iBallViewNo);
				/** �����С���ʼ������ */
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,aResId);

				/** Ϊ��ʼ����С�����ü��� */
				tempBallView.setOnClickListener(this);
				/*** ��С��tempView��ӵ�Table�� */
				iBallTable.addBallView(tempBallView);

				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					/** ����TableRow�ĸ�����Ŀհ����� */
					lp.setMargins(margin + 1, 1, 1, 1);
				} else if (col == viewNumPerLine - 1) {
					lp.setMargins(1, 1, margin + scrollBarWidth + 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				/** iBallViewNo������ѭ������С������� */
				iBallViewNo++;
			}
			/** �½���TableRow��ӵ�TableLayout */
			iBallTable.tableLayout.addView(tableRow, new TableLayout.LayoutParams(PublicConst.FP, PublicConst.WC));
		}
		return iBallTable;
	}
	/**
	 * �������������BallTable
	 * @param LinearLayout aParentView ��һ��Layout
	 * @param int aLayoutId ��ǰBallTable��LayoutId
	 * @param int aFieldWidth BallTable����Ŀ�ȣ�����Ļ��ȣ�
	 * @param int aBallNum С�����
	 * @param int aBallViewWidth С����ͼ�Ŀ�ȣ�ͼƬ��ȣ�
	 * @param int[] aResId С��ͼƬId
	 * @param int aIdStart С��Id��ʼ��ֵ
	 * @return BallTable
	 */
	BallTable makeBallTable(LinearLayout aParentView, int aLayoutId,
			int aFieldWidth, int[] aResId,int aIdStart) {

		BallTable iBallTable = new BallTable(aParentView, aLayoutId, aIdStart);

		int iBallViewWidth = aFieldWidth/3-2;
		int iFieldWidth = aFieldWidth;
		/** �������Ŀ�� */
		int scrollBarWidth = 6;
		/** ÿһ�е�С������ */
		int viewNumPerLine = 3;
		/** �е����� */
		int lineNum = 1;
	
		/** �հ׵Ĵ�С */
		int margin = (iFieldWidth - scrollBarWidth - (iBallViewWidth + 2)* viewNumPerLine) / 2;
		int iBallViewNo = 0;

		for (int row = 0; row < lineNum; row++) {
			TableRow tableRow = new TableRow(aParentView.getContext());
			for (int col = 0; col < viewNumPerLine; col++) {
				/** ������ʾ������ */
				String iStrTemp = "" + (iBallViewNo + 1);
				if (iStrTemp.equals("1")) {
					iStrTemp = "3";
				} else if (iStrTemp.equals("2")) {
					iStrTemp = "1";
				} else {
					iStrTemp = "0";
				}
				/** ʵ����һ��BallView���� */
				OneBallView tempBallView = new OneBallView(aParentView.getContext());
				/** Ϊ���tempView����һ��Id */
				tempBallView.setId(aIdStart + iBallViewNo);
				/** �����С���ʼ������ */
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,	aResId);

				/** Ϊ��ʼ����С�����ü��� */
				tempBallView.setOnClickListener(this);
				/*** ��С��tempView��ӵ�Table�� */
				iBallTable.addBallView(tempBallView);

				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					/** ����TableRow�ĸ�����Ŀհ����� */
					lp.setMargins(margin + 1, 1, 1, 1);
				} else if (col == viewNumPerLine - 1) {
					lp.setMargins(1, 1, margin + scrollBarWidth + 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				/** iBallViewNo������ѭ������С������� */
				iBallViewNo++;
			}
			/** �½���TableRow��ӵ�TableLayout */
			iBallTable.tableLayout.addView(tableRow, new TableLayout.LayoutParams(PublicConst.FP, PublicConst.WC));
		}
	
		return iBallTable;
	}
	 /**
	 * ����Ͷע����2��Ԫʱ�ĶԻ���
	 */
	protected void DialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(FootBallLotteryFather.this);
		builder.setTitle("Ͷעʧ��");
		builder.setMessage("����Ͷע���ܴ���2��Ԫ");
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
	 * ��ʾ��Ϣ
	 */
	public void changeTextSumMoney(int iZhuShu) {

		StringBuffer touzhuAlert = new StringBuffer();
		
		if (iZhuShu != 0) {
			touzhuAlert.append("��").append(iZhuShu).append("ע����").append((iZhuShu * 2)).append("Ԫ");
			Toast.makeText(FootBallLotteryFather.this, touzhuAlert.toString(), Toast.LENGTH_SHORT).toString();
		} else {
			Toast.makeText(FootBallLotteryFather.this, getResources().getString(R.string.please_choose_number), Toast.LENGTH_SHORT).toString();
		}
	}
	/**
	 * С�򱻵���¼�
	 * 
	 * @param v
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	String formatBatchCode(String batchCode){
		return "��"+batchCode+"��";
	}
	String formatEndtime(String endTime){
		return "��ֹʱ�䣺"+endTime;
	}
	
	

}
