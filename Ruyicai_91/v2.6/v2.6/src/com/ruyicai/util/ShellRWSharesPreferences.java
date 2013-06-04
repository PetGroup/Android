package com.ruyicai.util;

import android.content.Context;

//�����������ǰ�RWSharedPreferences��ʵ�����������û���Ϣ���б���,�������ظ�ʹ������ࡣ
public class ShellRWSharesPreferences extends RWSharedPreferences {
	//Context context;
	boolean isSame;
	RWSharedPreferences sp; // ����һ��RWSharedPreferences�Ķ���

	/**
	 * ��ʼ����Ϣ
	 * 
	 * @param c
	 * @param name
	 *            ����
	 */
	public ShellRWSharesPreferences(Context c, String name) {
		super(c, name);
		//context = c;
		sp = new RWSharedPreferences(context, "LotteryUserInfo");
	}

	/**
	 * �õ��û���Ҫ�������ϢvalueName
	 * ��value��ֵ��key��������
	 * @param aKeyName         ��
	 * @param aKeyValue        ֵ
	 */
	public void setUserLoginInfo(String aKeyName, String aKeyValue) {

		// �ж�valueName��ֵ�Ƿ�͡�username����ͬ������ͬ�򷵻�
		if (aKeyName.equals(null) || aKeyValue.equals(null)) {
		} else {
			sp.putPreferencesValue(aKeyName, aKeyValue);// ��Ҫ������û���Ϣ����Ӧ��key��Ӧ����
		}

	}

	/**
	 * ͨ��key���Եõ���Ӧ��valueֵ������value��ֵ
	 * @param aKeyName   ��
	 * @return ����key��Ӧ��ֵ
	 */
	public String getUserLoginInfo(String aKeyName) {
		if (aKeyName == null||aKeyName.equals(null)) {
			return "";
		} else {
			return sp.getPreferencesValue(aKeyName);
		}
		// Log.d("tag","ok");
	}

	// ͨ��yn���ж��Ƿ�����
	/**
	 * �ж��Ƿ�����
	 * 
	 * @param yn
	 */
	public String connectNetYOrN(boolean yn) {
		String changBooleanToString = new String();
		if (yn) {
			return changBooleanToString.valueOf(yn);
		}
		return "";
	}
	//
	public void setBoolean(String key,boolean isOn){
		if(!key.equals(null)){
			sp.setPreferencesBoolean(key, isOn);
		}
	}
	public boolean getBoolean(String key){
		if(!key.equals(null)){
			return sp.getPreferencesBoolean(key);
		}
		return false;
	}
}
