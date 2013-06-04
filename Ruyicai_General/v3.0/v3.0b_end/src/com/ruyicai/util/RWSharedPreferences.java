package com.ruyicai.util;

import android.content.Context;
import android.content.SharedPreferences;

//��RWSharedPreferences���������ü�ֵ�Ե���ʽ���û��������Ϣ�������������������´�ʹ��ʱ�Զ���ʾ����
public class RWSharedPreferences {
	SharedPreferences sp; // ����һ��SharedPreferences�Ķ���
	SharedPreferences.Editor editor; // ͨ�� SharedPreferences.Editor�Ķ������޸�����

	Context context;

	/**
	 * ��ʼ����Ϣ
	 * 
	 * @param c
	 * @param name
	 *            ����
	 */
	public RWSharedPreferences(Context c, String name) {
		context = c;
		sp = context.getSharedPreferences(name, 0);
		editor = sp.edit();
	}

	/**
	 * ��value��ֵ��key��������
	 * @param key       ��
	 * @param value     ֵ
	 */
	public void putStringValue(String key, String value) {
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * ͨ��key���Եõ���Ӧ��valueֵ������value��ֵ
	 * @param key   ��
	 * @return ����key��Ӧ��ֵ
	 */
	public String getStringValue(String key) {
		return sp.getString(key, null);
	}
	/**
	 * booleanֵ��ֵ��Ӧ
	 * @param key       ��
	 * @param value     ֵ
	 */
	public void putBooleanValue(String key, boolean isOn) {
		editor = sp.edit();
		editor.putBoolean(key, isOn);
		editor.commit();
	}
	/**
	 * booleanֵ��ֵ��Ӧ
	 * @param key       ��
	 * @param value     ֵ
	 */
	public boolean getBooleanValue(String key) {
	  return sp.getBoolean(key, false);
	}
}
