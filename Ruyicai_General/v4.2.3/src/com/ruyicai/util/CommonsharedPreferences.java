package com.ruyicai.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;

@SuppressLint("CommitPrefEdits")
@Singleton
public class CommonsharedPreferences{
	private SharedPreferences sharedPreferences;
	public SharedPreferences getSharedPreferences() {
		if(sharedPreferences==null){
			sharedPreferences =context.getSharedPreferences(Constants.SHARED_NAME, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
		}
		return sharedPreferences;
	}
	private SharedPreferences.Editor editor;

	@Inject Context context;
	public CommonsharedPreferences() {

	}
	
	public CommonsharedPreferences(Context context) {
		this.context=context;
		sharedPreferences =context.getSharedPreferences(Constants.SHARED_NAME, Context.MODE_PRIVATE);
		editor = getSharedPreferences().edit();
	}

	
	
	public void remove(String key) {
		editor.remove(key);
		editor.commit();
	}
	public void putStringValue(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}
	public void putLongValue(String key, Long value) {
		editor.putLong(key, value);
		editor.commit();
	}
	
	public String getStringValue(String key) {
		return getSharedPreferences().getString(key, "");
	}
	
	public long getLongValue(String key) {
		return getSharedPreferences().getLong(key, 0);
	}
	
	public void putBooleanValue(String key,boolean value){
		editor.putBoolean(key, value);
		editor.commit();
	}
	public void putIntValue(String key,int value){
		editor.putInt(key, value);
		editor.commit();
	}
	public boolean getBooleanValue(String key){
		return getSharedPreferences().getBoolean(key, false);
	}

	
	
	
	public void saveChannel(String channel) {
		editor.putString(Constants.CHANNEL_JSON_KEY_URL, channel);
		editor.commit();
	}
	public String getChannel() {
		String channel = null;
		channel=getSharedPreferences().getString(Constants.CHANNEL_JSON_KEY_URL, "");
		return channel;
	}
	
	
	
	public void saveversionNane(String versionNane) {
		editor.putString(Constants.VERSIONNAME, versionNane);
		editor.commit();
	}
	
	public String getversionNane() {
		String versionNane = null;
		versionNane=getSharedPreferences().getString(Constants.VERSIONNAME, "");
		return versionNane;
	}
	public int getVersionCode() {
		int versionCode = 0;
		versionCode=getSharedPreferences().getInt(Constants.VERSIONCODE, 0);
		return versionCode;
	}

	public void saveFriendsSort(int friendSortType) {
		editor.putInt(Constants.FRIEND_SORT_SHARED_KEY, friendSortType);
		editor.commit();
	}
	public int getFriendsSort(){
		return getSharedPreferences().getInt(Constants.FRIEND_SORT_SHARED_KEY, 1);
	}
	
	
	public void saveAttentionsSort(int attentionSortType) {
		editor.putInt(Constants.ATTENTION_SORT_SHARED_KEY, attentionSortType);
		editor.commit();
	}
	public int getAttentionsSort(){
		return getSharedPreferences().getInt(Constants.ATTENTION_SORT_SHARED_KEY, 1);
	}
	
	
	public void saveFansSort(int fansSortType) {
		editor.putInt(Constants.FAN_SORT_SHARED_KEY, fansSortType);
		editor.commit();
	}
	public int getFansSort(){
		return getSharedPreferences().getInt(Constants.FAN_SORT_SHARED_KEY, 1);
	}
	
	
	
	public void saveNearPeopleScreen(int nearPeopleScreen) {
//		editor.putInt(Constants.NEAR_BY_PEOPLE_SHARED_KEY, nearPeopleScreen);
//		editor.commit();
	}
//	public int getNearPeopleScreen(){
//		return getSharedPreferences().getInt(Constants.NEAR_BY_PEOPLE_SHARED_KEY, 2);
//	}
	
	
	
//	public void saveSameServiceScreen(int nearPeopleScreen) {
//		editor.putInt(Constants.SAME_SERVICE_PEOPLE_SHARED_KEY, nearPeopleScreen);
//		editor.commit();
//	}
//	public int getSameServiceScreen(){
//		return getSharedPreferences().getInt(Constants.SAME_SERVICE_PEOPLE_SHARED_KEY, 2);
//	}
	
	
	public void saveStartImageID(String startImageId) {
		editor.putString(Constants.STARTIMAGE, startImageId);
		editor.commit();
	}
	public String getStartImageID() {
		String id=null;
		id=getSharedPreferences().getString(Constants.STARTIMAGE, "");
		return id;
	}
	public void saveUserInfo(String username,String token,String userid) {
		editor.putString(Constants.USRENAME_SHARED_KEY, username);
		editor.putString(Constants.TOKEN_SHARED_KEY, token);
		editor.putString(Constants.USERID_SHARED_KEY, userid);
		editor.commit();
	}
	public String getToken(){
		String token=null;
		token=getSharedPreferences().getString(Constants.TOKEN_SHARED_KEY, "");
		return token;
	}
	public String getUsername(){
		String username=null;
		username=getSharedPreferences().getString(Constants.USRENAME_SHARED_KEY, "");
		return username;
	}
	public String getUserId(){
		String userid=null;
		userid=getSharedPreferences().getString(Constants.USERID_SHARED_KEY, "");
		return userid;
	}
	
	
	
	
//	public void saveRankingTime(String rankingtime) {
//		editor.putString(Constants.UPDATE_ROLE_TIME_SHARED_KEY, rankingtime);
//		editor.commit();
//	}
//	public String getRankingTime() {
//		return getSharedPreferences().getString(Constants.UPDATE_ROLE_TIME_SHARED_KEY, "");
//	}
	
	
	
	public void saveMillisInfo(String gamelist_millis,String wow_realms_millis,String wow_characterclasses_millis) {
		editor.putString(Constants.GAME_LIST_MILLIS_SHARED_KEY, gamelist_millis);
		editor.putString(Constants.WOW_REALMS_MILLIS_SHARED_KEY, wow_realms_millis);
		editor.putString(Constants.WOW_CHARACTER_CLASSES_MILLIS_SHARED_KEY, wow_characterclasses_millis);
		editor.commit();
	}
	
	public String getGameListMillis() {
		String birthdate = null;
		birthdate=getSharedPreferences().getString(Constants.GAME_LIST_MILLIS_SHARED_KEY, "");
		return birthdate;
	}
	public String getRealmsMillis() {
		String birthdate = null;
		birthdate=getSharedPreferences().getString(Constants.WOW_REALMS_MILLIS_SHARED_KEY, "");
		return birthdate;
	}
	public String getCharcTerClassesMillis() {
		String birthdate = null;
		birthdate=getSharedPreferences().getString(Constants.WOW_CHARACTER_CLASSES_MILLIS_SHARED_KEY, "");
		return birthdate;
	}
	
	
	public void saveRegisterNeedMsg(String registerNeedMsg) {
		editor.putString(Constants.REGISTER_NEED_MSG_SHARED_KEY, registerNeedMsg);
		editor.commit();
	}
	public String getRegisterNeedMsg() {
		String registerNeedMsg = null;
		registerNeedMsg=getSharedPreferences().getString(Constants.REGISTER_NEED_MSG_SHARED_KEY, "");
		return registerNeedMsg;
	}
	public void saveRankName(String rankName) {
		editor.putString("rankName", rankName);
		editor.commit();
	}
	public String getRankName() {
		String rankName = null;
		rankName=getSharedPreferences().getString("rankName", "");
		return rankName;
	}
	
	public void saveDbOldVersion(int oldVersion) {
		editor.putInt("oldVersion", oldVersion);
		editor.commit();
	}
	public int getDbOldVersion() {
		return getSharedPreferences().getInt("oldVersion", 0);
	}
	
	
	
	public void saveUpdateInfo(String clientUpdate,String clientMustUpdate,String clientUpdateUrl) {
		editor.putString(Constants.CLIENT_UPDATE_SHARED_KEY, clientUpdate);
		editor.putString(Constants.CLIENT_MUST_UPDATE_SHARED_KEY, clientMustUpdate);
		editor.putString(Constants.CLIENT_UPDATE_URL_SHARED_KEY, clientUpdateUrl);
		editor.commit();
	}
	public String getClientUpdate() {
		String clientUpdate = null;
		clientUpdate=getSharedPreferences().getString(Constants.CLIENT_UPDATE_SHARED_KEY, "");
		return clientUpdate;
	}
	public String getClientMustUpdate() {
		String clientMustUpdate = null;
		clientMustUpdate=getSharedPreferences().getString(Constants.CLIENT_MUST_UPDATE_SHARED_KEY, "");
		return clientMustUpdate;
	}
	public String getClientUpdateUrl() {
		String clientUpdateUrl = null;
		clientUpdateUrl=getSharedPreferences().getString(Constants.CLIENT_UPDATE_URL_SHARED_KEY, "");
		return clientUpdateUrl;
	}
}