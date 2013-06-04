/**
 * 
 */
package com.ruyicai.util;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ����ʱ�߳���
 * 
 * @author Administrator
 * 
 */
public class ClockThread {
	public static String[] times = new String[9];
	public static String[] lotnos = { Constants.LOTNO_SSQ,
			Constants.LOTNO_FC3D, Constants.LOTNO_QLC, Constants.LOTNO_PL3,
			Constants.LOTNO_DLT, Constants.LOTNO_JQC, Constants.LOTNO_RX9,
			Constants.LOTNO_SFC, Constants.LOTNO_LCB };

	public ClockThread() {
		initVaule();
	}

	public void initVaule() {
		for (int i = 0; i < times.length; i++) {
			times[i] = getTime(lotnos[i]);
		}
	}

	public void startThread() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);// ����1��
						for (int i = 0; i < times.length; i++) {
							times[i] = Integer.toString(Integer
									.parseInt(times[i]) - 1);

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public static String getVaule(String lotno) {
		String time = "";
		for (int i = 0; i < times.length; i++) {
			if (lotno.equals(lotnos[i])) {
				time = times[i];
			}
		}
		return time;

	}

	/**
	 * ��ʽ��ʱ��
	 */
	public static String formatTime(String times) {
		if (times != null) {
			if (Integer.parseInt(times) < 0) {
				return "0��00ʱ00��00��";
			} else {
				int time = Integer.parseInt(times);
				int day, hour, minute, second;
				second = time % 60;
				DecimalFormat df=new DecimalFormat("00");
				minute = time / 60;
				if (minute > 60) {
					minute = minute % 60;
				}
				hour = time / 3600;
				if (hour > 24) {
					hour = hour % 24;
				}
				day = time / (3600 * 24);
				return day + "��" + df.format(hour) + "ʱ" + df.format(minute) + "��" + df.format(second) + "��";
			}
		} else {
			return "0��00ʱ00��00��";
		}

	}

	/**
	 * ��õ�ǰʱ��
	 * 
	 * @param type���ֱ��
	 */
	public String getTime(String type) {
		String time = "0";
		// ��ȡ�ںźͽ�ֹʱ��
		JSONObject ssqLotnoInfo = PublicMethod.getCurrentLotnoBatchCode(type);
		if (ssqLotnoInfo != null) {
			// �ɹ���ȡ�����ں���Ϣ
			try {
				time = ssqLotnoInfo.getString("endSecond");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			// û�л�ȡ���ں���Ϣ,����������ȡ��
		}
		return time;
	}
}
