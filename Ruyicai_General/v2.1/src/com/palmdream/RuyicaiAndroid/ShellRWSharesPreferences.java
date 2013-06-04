package com.palmdream.RuyicaiAndroid;

import android.content.Context;

//�����������ǰ�RWSharedPreferences��ʵ�����������û���Ϣ���б���,�������ظ�ʹ������ࡣ
class ShellRWSharesPreferences extends RWSharedPreferences {
	Context context;
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
		// TODO Auto-generated constructor stub
		context = c;
		sp = new RWSharedPreferences(context, "LotteryUserInfo");
	}

	// �õ��û���Ҫ�������ϢvalueName
	/**
	 * ��value��ֵ��key��������
	 * 
	 * @param aKeyName
	 *            ��
	 * @param aKeyValue
	 *            ֵ
	 */
	public void setUserLoginInfo(String aKeyName, String aKeyValue) {
		// Log.d("tag","ok");
		// �ж�valueName��ֵ�Ƿ�͡�username����ͬ������ͬ�򷵻�
		if (aKeyName.equals(null) || aKeyValue.equals(null)) {
		} else {
			sp.putPreferencesValue(aKeyName, aKeyValue);// ��Ҫ������û���Ϣ����Ӧ��key��Ӧ����

			// Log.d("tag","ok");
		}

	}

	// ͨ���õ�key���Ϳ����ҵ���Ӧ��valueֵ��Ȼ�󷵻�value��ֵ
	/**
	 * ͨ��key���Եõ���Ӧ��valueֵ������value��ֵ
	 * 
	 * @param aKeyName
	 *            ��
	 * @return ����key��Ӧ��ֵ
	 */
	public String getUserLoginInfo(String aKeyName) {
		if (aKeyName.equals(null)) {
			return null;
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
}
