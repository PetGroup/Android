package com.ruyicai.util;

/**
 * @Title ���÷���
 * @Description: 
 * @Copyright: Copyright (c) 2009
 * @Company: PalmDream
 * @author FanYaJun
 * @version 1.0 20100618
 */

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.telephony.gsm.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.net.transaction.SoftwareUpdateInterface;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.view.OneBallView;

public class PublicMethod {
	// �����
	public static long zuhe(int m, int n) {
		long t_a = 0L;
		long total = 1L;
		int temp = n;
		for (int i = 0; i < m; i++) {
			total = total * temp;
			temp--;
		}
		t_a = total / jiec(m);
		return t_a;
	}

	// ��׳�
	public static long jiec(int a) {
		long t_a = 1L;
		for (long i = 1; i <= a; i++) {
			t_a = t_a * i;
		}
		return t_a;
	}

	// ��ȡ ���������
	static Random random = new Random();

	public static int getRandomByRange(int aFrom, int aTo) {
		return (random.nextInt() >>> 1) % (aTo - aFrom + 1) + aFrom;
	}

	// ���������ײ
	public static boolean checkCollision(int[] aNums, int aTo, int aCheckNum) {
		boolean returnValue = false;
		for (int i = 0; i < aTo; i++) {
			if (aNums[i] == aCheckNum) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	// ��ȡ ��������
	public static int[] getRandomsWithoutCollision(int aNum, int aFrom, int aTo) {
		int[] iReturnNums = new int[aNum];
		for (int i = 0; i < aNum; i++) {
			int iCurrentNum = getRandomByRange(aFrom, aTo);
			while (checkCollision(iReturnNums, i, iCurrentNum)) {
				iCurrentNum = getRandomByRange(aFrom, aTo);
			}
			iReturnNums[i] = iCurrentNum;
		}
		return iReturnNums;
	}

	// ��ȡ��ǰҳ�����Ļ�߶�
	// ����1:Context
	// ����ֵ��int
	public static int getDisplayHeight(Context cx) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}

	// ��ȡ��ǰҳ�����Ļ���
	// ����1:Context
	// ����ֵ��int
	public static int getDisplayWidth(Context cx) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;

		return screenWidth;
	}

	// �޸�����ʽ
	public static String changeMoney(String str) {
		if (str.length() > 2) {
			if (str.substring(str.length() - 2, str.length()).equals("00")) {
				str = str.substring(0, str.length() - 2);
			} else {
				str = str.substring(0, str.length() - 2) + "."
						+ str.substring(str.length() - 2, str.length());
			}
		} else if (str.length() == 2) {
			str = "0" + "." + str;
		} else if (str.length() == 1) {
			str = "0.0" + str;
		}
		return str;
	}

	// ������
	public static boolean sendSMS(String phoneNumber, String message) {
		try {
			SmsManager sms = SmsManager.getDefault();
			List<String> iContents = sms.divideMessage(message);
			for (int i = 0; i < iContents.size(); i++)
				sms.sendTextMessage(phoneNumber, null, iContents.get(i), null,
						null);

		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	/**
	 * �����Ϣ
	 */
	public static void myOutput(String strContent) {
		boolean iFlag = true;
		// boolean iFlag = false;
		if (iFlag) {
//			Log.d("tag", strContent);
		}
		return;
	}

	/**
	 * ����listView�ļ��
	 * 
	 * @param listview
	 */
	public static void setmydividerHeight(ListView listview) {
		listview.setDivider(new ColorDrawable(Color.GRAY));
		listview.setDividerHeight(1);

	}


	// ��õ�ǰ�ںͽ�ֹʱ��
	public static String[] getLotno(String string, Context context) {
		String error_code;
		String batchcode = "";
		String endTime = "";
		String str[] = new String[2];
		// ShellRWSharesPreferences
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				context, "addInfo");
		String notice = shellRW.getUserLoginInfo(string);
		// �ж�ȡֵ�Ƿ�Ϊ�� cc 2010/7/9
		if (notice != null && !notice.equals("")) {
			try {
				JSONObject obj = new JSONObject(notice);
				error_code = obj.getString("error_code");
				if (error_code.equals("0000")) {
					batchcode = obj.getString("batchCode");
					endTime = obj.getString("end_time");
					str[0] = batchcode;
					str[1] = endTime;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	// ����
	public static int[] orderby(int[] nums, String str) {
		// �Ӵ�С��
		if (str.equalsIgnoreCase("cba")) {
			for (int i = 0; i < nums.length; i++) {
				for (int j = i + 1; j < nums.length; j++) {
					if (nums[i] < nums[j]) {
						int tem = nums[i];
						nums[i] = nums[j];
						nums[j] = tem;
					}
				}
			}
		}
		// ��С������
		else if (str.equalsIgnoreCase("abc")) {
			for (int i = 0; i < nums.length; i++) {
				for (int j = i + 1; j < nums.length; j++) {
					if (nums[i] > nums[j]) {
						int tem = nums[i];
						nums[i] = nums[j];
						nums[j] = tem;
					}
				}
			}
		}
		return nums;
	}

	/**
	 * ��ע��1ת�����ַ�01�ķ���
	 */
	public static String getZhuMa(int num) {
		String str="";

		if (num < 10) {
			str = "0" + num;
		} else {
			str = "" + num;
		}
		return str;

	}

	/**
	 * ������ѡTable
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
	public static BallTable makeBallTableJiXuan(BallTable iBallTable,
			int aFieldWidth, int[] aResId,
			int[] iTotalRandoms, Context context) {
		PublicMethod.recycleBallTable(iBallTable);//�ͷ�С����Դ
		// int iBallNum = aBallNum;
		int iBallNum = iTotalRandoms.length;
		int iFieldWidth = aFieldWidth;
		int scrollBarWidth = 6;
		int viewNumPerLine = 8;
		int iBallViewWidth = (iFieldWidth - scrollBarWidth)/viewNumPerLine-2;
		
		int lineNum = iBallNum / viewNumPerLine;
		int lastLineViewNum = iBallNum % viewNumPerLine;
		int margin = (iFieldWidth - scrollBarWidth - (iBallViewWidth + 2)
				* viewNumPerLine) / 2;
		int iBallViewNo = 0;
		for (int row = 0; row < lineNum; row++) {
			TableRow tableRow = new TableRow(iBallTable.getContext());
			for (int col = 0; col < viewNumPerLine; col++) {
				int num = iTotalRandoms[(row * viewNumPerLine) + col] + 1;
				String iStrTemp = "" + num;
				OneBallView tempBallView = new OneBallView(iBallTable
						.getContext());
				tempBallView.setId(iBallTable.getStartId() + iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,
						aResId);
				// tempBallView.setOnClickListener(this);
				tempBallView.changeBallColor();
				iBallTable.addBallView(tempBallView);

				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin + 1, 1, 1, 1);
				} else if (col == viewNumPerLine - 1) {
					lp.setMargins(1, 1, margin + scrollBarWidth + 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				iBallViewNo++;
			}
			// �½���TableRow��ӵ�TableLayout
			iBallTable.tableLayout
					.addView(tableRow, new TableLayout.LayoutParams(
							PublicConst.FP, PublicConst.WC));
		}
		if (lastLineViewNum > 0) {
			TableRow tableRow = new TableRow(context);
			for (int col = 0; col < lastLineViewNum; col++) {
				int num = iTotalRandoms[iBallViewNo] + 1;
				String iStrTemp = "" + num;
				OneBallView tempBallView = new OneBallView(iBallTable
						.getContext());
				tempBallView.setId(iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,
						aResId);
				// tempBallView.setOnClickListener(this);
				tempBallView.changeBallColor();
				iBallTable.addBallView(tempBallView);
				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin + 1, 1, 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				iBallViewNo++;
			}
			// �½���TableRow��ӵ�TableLayout
			iBallTable.tableLayout
					.addView(tableRow, new TableLayout.LayoutParams(
							PublicConst.FP, PublicConst.WC));
		}

		return iBallTable;
	}

	public static void showedittextnumber(EditText editText,
			BallTable balltable, String string) {
		String red_zhuma_string = "  ";
		int[] redZhuMa = balltable.getHighlightBallNOs();
		for (int i = 0; i < balltable.getHighlightBallNOs().length; i++) {
			red_zhuma_string += PublicMethod.getZhuMa(redZhuMa[i]);
			if (i != balltable.getHighlightBallNOs().length - 1)
				red_zhuma_string = red_zhuma_string + ",";
		}

		if (red_zhuma_string.equals("  ")) {
			editText.setText("");
			editText.setHint(string);
		} else {
			editText.setText(red_zhuma_string);
		}

	}

	/**
	 * ��������
	 * @param t
	 * @return
	 */
	public static int[] sort(int t[]) {
		int t_s[] = t;
		int temp;
		for (int i = 0; i < t_s.length; i++) {
			for (int j = i + 1; j < t_s.length; j++) {
				if (t_s[i] > t_s[j]) {
					temp = t_s[i];
					t_s[i] = t_s[j];
					t_s[j] = temp;
				}
			}
		}
		return t_s;
	}

	/**
	 * ��ѡ��ʾ��1 ��������ѡ�����
	 * 
	 * @param string       ��ʾ����Ϣ
	 * @return
	 */

	public static void alertJiXuan(String string, Context context) {
		Builder dialog = new AlertDialog.Builder(context).setTitle("��ѡ�����")
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
	 * ��ȡ��ǰ�ں���Ϣ
	 */
	public static JSONObject getCurrentLotnoBatchCode(String lotno) {
		try {
			return Constants.currentLotnoInfo.getJSONObject(lotno);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ����BallTable
	 * 
	 * @param LinearLayout aParentView ��һ��Layout
	 * @param int aLayoutId ��ǰBallTable��LayoutId
	 * @param int aFieldWidth BallTable����Ŀ�ȣ�����Ļ��ȣ�
	 * @param int aBallNum С�����
	 * @param int aBallViewWidth С����ͼ�Ŀ�ȣ�ͼƬ��ȣ�
	 * @param int[] aResId С��ͼƬId
	 * @param int aIdStart С��Id��ʼ��ֵ
	 * @return BallTable
	 */
	public static BallTable makeBallTable(LinearLayout aParentView,
			int aLayoutId, int aFieldWidth, int aBallNum,
			int[] aResId, int aIdStart, int aBallViewText, Context context,
			OnClickListener onclick) {
		BallTable iBallTable = new BallTable(aParentView, aLayoutId, aIdStart);
		// BallTable iBallTable=new BallTable(aLayoutId,aIdStart);
		int iBallNum = aBallNum;
	
		int viewNumPerLine = 8;	//����û��С��ĸ���Ϊ7		
		int iFieldWidth = aFieldWidth;
		int scrollBarWidth = 6;
		int iBallViewWidth = (iFieldWidth - scrollBarWidth)/viewNumPerLine-2;
		int lineNum = iBallNum / viewNumPerLine;
		int lastLineViewNum = iBallNum % viewNumPerLine;
		int margin = (iFieldWidth - scrollBarWidth - (iBallViewWidth + 2)
				* viewNumPerLine) / 2;
		int iBallViewNo = 0;
		for (int row = 0; row < lineNum; row++) {
			TableRow tableRow = new TableRow(aParentView.getContext());
			
			for (int col = 0; col <viewNumPerLine; col++) {
				String iStrTemp = "";
				if (aBallViewText == 0) {
					iStrTemp = "" + (iBallViewNo);// С���0��ʼ
				} else if (aBallViewText == 1) {
					iStrTemp = "" + (iBallViewNo + 1);// С���1��ʼ
				} else if (aBallViewText == 3) {
					iStrTemp = "" + (iBallViewNo + 3);// С���3��ʼ
				}
				OneBallView tempBallView = new OneBallView(aParentView.getContext());
				tempBallView.setId(aIdStart + iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,aResId);
				tempBallView.setOnClickListener(onclick);
				iBallTable.addBallView(tempBallView);

				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin , 1, 1, 1);
				} else if (col == viewNumPerLine - 1) {
					lp.setMargins(1, 1, margin + scrollBarWidth + 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				iBallViewNo++;
			//	Log.v("iBallViewNo", iBallViewNo+"")
			}
			// �½���TableRow��ӵ�TableLayout
			iBallTable.tableLayout
					.addView(tableRow, new TableLayout.LayoutParams(
							PublicConst.FP, PublicConst.WC));
		}
		if (lastLineViewNum > 0) {
			TableRow tableRow = new TableRow(context);
			for (int col = 0; col < lastLineViewNum; col++) {
				String iStrTemp = "";
				// PublicMethod.myOutput("-----------"+iBallViewNo);
				if(aBallViewText == 0){
				iStrTemp = "" + (iBallViewNo);
				}else if (aBallViewText == 1) {
					iStrTemp = "" + (iBallViewNo + 1);// С���1��ʼ
				} else if (aBallViewText == 3) {
					iStrTemp = "" + (iBallViewNo + 3);// С���3��ʼ
				}
				OneBallView tempBallView = new OneBallView(aParentView
						.getContext());
				tempBallView.setId(aIdStart + iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,
						aResId);
				tempBallView.setOnClickListener(onclick);
				iBallTable.addBallView(tempBallView);
				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin , 1, 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				iBallViewNo++;
			}
			// �½���TableRow��ӵ�TableLayout
			iBallTable.tableLayout
					.addView(tableRow, new TableLayout.LayoutParams(
							PublicConst.FP, PublicConst.WC));
		}
		return iBallTable;
	}
	

	public static Bitmap getBitmapFromRes(int aResId, Context context) {
		Resources r = context.getResources();
		InputStream is = r.openRawResource(aResId);
		BitmapDrawable bmpDraw = new BitmapDrawable(is);

		return bmpDraw.getBitmap();
	}

	public static Drawable getDrawFormRes(int aResId, Context context,
			int width, int height) {
		// ����ͼƬ
		Bitmap bitmap = getBitmapFromRes(aResId, context);
		int widthImg = bitmap.getWidth();
		int heightImg = bitmap.getHeight();

		float sw = ((float) width) / widthImg;
		float sh = ((float) height) / heightImg;
		Matrix matrix = new Matrix();
		matrix.postScale(sw, sh);
		Bitmap onBitmapM = Bitmap.createBitmap(bitmap, 0, 0, widthImg,
				heightImg, matrix, true);
		Drawable drawable = new BitmapDrawable(onBitmapM);
		return drawable;

	}
	
	
	public static void recycleBallTable(BallTable balltable){
		if(balltable != null && balltable.getBallViews()!= null){
			for (Iterator iterator = balltable.getBallViews().iterator(); iterator.hasNext();) {
				OneBallView ball = (OneBallView) iterator.next();
				ball.recycleBitmaps();
				ball = null;
			}
		}
		balltable = null;
	}
	/**
	 * ������ȡ�ں�
	 */
	public static void getIssue(final String lotno,final TextView term,final TextView time,final Handler updateIssueHandler){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					term.setText("�ںŻ�ȡ��....");
					JSONObject softupdateObj = new JSONObject(SoftwareUpdateInterface.getInstance().softwareupdate(null));
					Constants.currentLotnoInfo = softupdateObj.getJSONObject("currentBatchCode");// ��ȡ������ں���Ϣ
					JSONObject temp_obj = Constants.currentLotnoInfo.getJSONObject(lotno);
					final String issueStr = temp_obj.getString("batchCode");
					final String timeStr = temp_obj.getString("endTime");
					updateIssueHandler.post(new Runnable() {
						public void run() {
							term.setText("��" + issueStr + "��");
							time.setText("��ֹʱ�䣺" + timeStr);
						}
					});// ��ȡ�ɹ�
				} catch (Exception e) {
					e.printStackTrace();
					updateIssueHandler.post(new Runnable() {
						public void run() {
							term.setText("�ںŻ�ȡʧ��");
						}
					});// ��ȡ�ɹ�
				}
			}
		});
		t.start();
	}
	/**
	 * ����ַ
	 * @param cx ���ص�context
	 * @param a  ��ַ�ַ���
	 */
	public static void openUrlByString(Context cx, String a) {
		Uri myUri = Uri.parse(a);
		Intent returnIt = new Intent(Intent.ACTION_VIEW, myUri);
		cx.startActivity(returnIt);
	}
	public static BallTable makeBallTableJiXuanSSC(BallTable iBallTable,
			int aFieldWidth, int[] aResId,
			int[] iTotalRandoms, Context context) {

		// int iBallNum = aBallNum;
		int iBallNum = iTotalRandoms.length;
		int iFieldWidth = aFieldWidth;
		int scrollBarWidth = 6;
		int viewNumPerLine = 8;
		int iBallViewWidth = (iFieldWidth - scrollBarWidth)/viewNumPerLine-2;
		
		int lineNum = iBallNum / viewNumPerLine;
		int lastLineViewNum = iBallNum % viewNumPerLine;
		int margin = (iFieldWidth - scrollBarWidth - (iBallViewWidth + 2)
				* viewNumPerLine) / 2;
		int iBallViewNo = 0;
		for (int row = 0; row < lineNum; row++) {
			TableRow tableRow = new TableRow(iBallTable.getContext());
			for (int col = 0; col < viewNumPerLine; col++) {
				int num = iTotalRandoms[(row * viewNumPerLine) + col];
				String iStrTemp = "" + num;
				OneBallView tempBallView = new OneBallView(iBallTable
						.getContext());
				tempBallView.setId(iBallTable.getStartId() + iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,
						aResId);
				// tempBallView.setOnClickListener(this);
				tempBallView.changeBallColor();
				iBallTable.addBallView(tempBallView);

				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin + 1, 1, 1, 1);
				} else if (col == viewNumPerLine - 1) {
					lp.setMargins(1, 1, margin + scrollBarWidth + 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				iBallViewNo++;
			}
			// �½���TableRow��ӵ�TableLayout
			iBallTable.tableLayout
					.addView(tableRow, new TableLayout.LayoutParams(
							PublicConst.FP, PublicConst.WC));
		}
		if (lastLineViewNum > 0) {
			TableRow tableRow = new TableRow(context);
			for (int col = 0; col < lastLineViewNum; col++) {
				int num = iTotalRandoms[iBallViewNo];
				String iStrTemp = "" + num;
				OneBallView tempBallView = new OneBallView(iBallTable
						.getContext());
				tempBallView.setId(iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,
						aResId);
				// tempBallView.setOnClickListener(this);
				tempBallView.changeBallColor();
				iBallTable.addBallView(tempBallView);
				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin + 1, 1, 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				iBallViewNo++;
			}
			// �½���TableRow��ӵ�TableLayout
			iBallTable.tableLayout.addView(tableRow, new TableLayout.LayoutParams(PublicConst.FP, PublicConst.WC));
		}

		return iBallTable;   
	}
   //Ͷע�ɹ�����ʾtoast
	public static void showDialog(Context context){
		   LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   View view = inflate.inflate(R.layout.touzhu_succe,null);
		   ImageView image = (ImageView) view.findViewById(R.id.touzhu_succe_img);
           image.setImageResource(R.drawable.succee);
//		   Toast toast = new Toast(context);
//		   toast.setGravity(Gravity.CENTER, 0, 0);
//		   toast.setDuration(Toast.LENGTH_LONG);
//		   toast.setView(view);
//		   toast.show();
		   
		   Builder dialog = new AlertDialog.Builder(context).setTitle("��ʾ")
			.setView(view).setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {

						}

					});
	        dialog.show();
	}
}
