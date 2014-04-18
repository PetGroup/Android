package com.ruyicai.util;

import java.util.Map;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//类RWSharedPreferences的作用是用键值对的形式将用户输入的信息保存下来，并可以在下次使用时自动显示出来
public class RWSharedPreferences {
	SharedPreferences sp; // 定义一个SharedPreferences的对象
	SharedPreferences.Editor editor; // 通过 SharedPreferences.Editor的对象来修改数据

	Context context;

	/**
	 * 初始化信息
	 * 
	 * @param c
	 * @param name
	 *            名称
	 */
	public RWSharedPreferences(Context c, String name) {
		context = c;
		sp = context.getSharedPreferences(name, 0);
		editor = sp.edit();
	}

	/**
	 * 把value的值与key关联起来
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void putStringValue(String key, String value) {
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 通过key可以得到对应的value值，返回value的值
	 * 
	 * @param key
	 *            键
	 * @return 返回key对应的值
	 */
	public String getStringValue(String key) {
		return sp.getString(key, "");
	}

	/**
	 * boolean值键值对应
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void putBooleanValue(String key, boolean isOn) {
		editor = sp.edit();
		editor.putBoolean(key, isOn);
		editor.commit();
	}

	/**
	 * boolean值键值对应
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public boolean getBooleanValue(String key) {
		return sp.getBoolean(key, false);
	}

	/**
	 * boolean值键值对应
	 * 
	 * @param isValue
	 *            默认值
	 */
	public boolean getBooleanValue(String key, boolean isValue) {
		return sp.getBoolean(key, isValue);
	}

	/**
	 * 获取所以kek值集合
	 */
	public Map<String, ?> getAllKey() {
		return sp.getAll();
	}

	/**
	 * 删除key值
	 */
	public void removeKey(String key) {
		editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}
	
	public void putIntValue(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}
	
	public int getIntValue(String key) {
		return sp.getInt(key, 0);
	}
	
	private final String PREFERENCES_NAME = "com_weibo_sdk_android";

    private final String KEY_UID           = "uid";
    private final String KEY_ACCESS_TOKEN  = "access_token";
    private final String KEY_EXPIRES_IN    = "expires_in";
    
    /**
     * 保存 Token 对象到 SharedPreferences。
     * 
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }
        
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.commit();
    }

    /**
     * 从 SharedPreferences 读取 Token 信息。
     * 
     * @param context 应用程序上下文环境
     * 
     * @return 返回 Token 对象
     */
    public Oauth2AccessToken readAccessToken(Context context) {
        if (null == context) {
            return null;
        }
        
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        token.setUid(pref.getString(KEY_UID, ""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
        return token;
    }

    /**
     * 清空 SharedPreferences 中 Token信息。
     * 
     * @param context 应用程序上下文环境
     */
    public void clear(Context context) {
        if (null == context) {
            return;
        }
        
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
