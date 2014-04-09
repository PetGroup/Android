package com.ruyicai.model;

import java.util.List;

import com.google.inject.spi.Message;

public class HttpUser {
	public static boolean isLogin=false;
	public static String userId;
	public static String startImageID;
	public static String versionNane;
	public static String clineVersionNane;
	public static String Username;
	public static String Nicname;
	public static String token;
	public static String channel;
	public static String Imei;
	public static String MAC;
	public static String latitude;
	public static String longitude;
	public static String city;
	public static String userNameFromGreet;//存放打招呼人的名字
	public static String userNameFromMessage;//发消息人的名字
	public static String newFriend;//添加的新朋友ac
	public static String ImageName;
	public static String petid;
//	public static GameRoseInfo gameRoseInformation;
	public static String sn;
	public static List<Message> myMessageList = null;
	public static String gamelist_millis;
	public static String wow_realms_millis;
	public static String wow_characterclasses_millis;
//	public static List<GameMaps> gameMapsList;
	public static int taskNumber = 0;
	public static boolean isGestureShow = false;
	public static String URl_ALl = "";
	public static String URL_HTML5 = "";//魔女榜Html5
	public static String URL_DOWN_PICTURE="";
	public static String URL_POST ="";
	public static String URL_UPLOAD_IMG = "";//上传图片
	public static String URL_DELETE_IMG = "";//删除图片
}
