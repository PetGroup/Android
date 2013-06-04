package com.palmdream.RuyicaiAndroid;

/**
 * @Title ���÷���
 * @Description: 
 * @Copyright: Copyright (c) 2009
 * @Company: PalmDream
 * @author FanYaJun
 * @version 1.0 20100618
 */

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.gsm.SmsManager;
import android.util.DisplayMetrics;

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
		String str = "";
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		return screenHeight;
		/*
		 * str += "The absolute width:" + String.valueOf(screenWidth) +
		 * "pixels\n"; str += "The absolute heightin:" +
		 * String.valueOf(screenHeight) + "pixels\n"; str +=
		 * "The logical density of the display.:" + String.valueOf(density) +
		 * "\n"; str += "X dimension :" + String.valueOf(xdpi) +
		 * "pixels per inch\n"; str += "Y dimension :" + String.valueOf(ydpi) +
		 * "pixels per inch\n"; return str;
		 */
	}

	// ��ȡ��ǰҳ�����Ļ���
	// ����1:Context
	// ����ֵ��int
	public static int getDisplayWidth(Context cx) {
		String str = "";
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		return screenWidth;
		/*
		 * str += "The absolute width:" + String.valueOf(screenWidth) +
		 * "pixels\n"; str += "The absolute heightin:" +
		 * String.valueOf(screenHeight) + "pixels\n"; str +=
		 * "The logical density of the display.:" + String.valueOf(density) +
		 * "\n"; str += "X dimension :" + String.valueOf(xdpi) +
		 * "pixels per inch\n"; str += "Y dimension :" + String.valueOf(ydpi) +
		 * "pixels per inch\n"; return str;
		 */
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
		// if (ifagent.equals("1")) {
		// PendingIntent pi = PendingIntent.getActivity(context, 0,new
		// Intent(context, SMS.class), 0);
		try {
			SmsManager sms = SmsManager.getDefault();
			List<String> iContents = sms.divideMessage(message);
			for (int i = 0; i < iContents.size(); i++)
				sms.sendTextMessage(phoneNumber, null, iContents.get(i), null,
						null);
			// sms.sendMultipartTextMessage(destinationAddress, scAddress,
			// parts, sentIntents, deliveryIntents)endTextMessage(phoneNumber,
			// null, message, null, null);
		} catch (IllegalArgumentException e) {
			PublicMethod.myOutput("--------" + e.getMessage() + " "
					+ e.toString());
			return false;
			// return;
		}
		return true;
		// }
	}

	// �������
	public static void openUrlByString(Context cx, String a) {
		Uri myUri = Uri.parse(a);
		Intent returnIt = new Intent(Intent.ACTION_VIEW, myUri);
		// Toast.makeText(Beauty.this,
		// "Settings not saved",Toast.LENGTH_SHORT).show();
		cx.startActivity(returnIt);

	}

	/**
	 * �����Ϣ
	 */
	public static void myOutput(String strContent) {
		boolean iFlag = true;
//		boolean iFlag = false;
		if (iFlag) {
			System.out.println(strContent);
		}
		return;
	}

}
