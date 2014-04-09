package com.ruyicai.controller.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.ContactsBean;
import com.ruyicai.model.HttpUser;
import com.ruyicai.net.HttpUrlConnPost;
import com.ruyicai.net.NetType;
import com.ruyicai.util.PublicMethod;

public class HttpService {
	private static String TAG = "HttpService";

	public static String getJsonBygetUserinfo(String username) {
		String result = getJsonfoFromNetBygetUserinfo(username);
		return result;
	}

	public static String updateUserCoordinate(String longitude, String latitude) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_UPDATE_USER_LOCATION_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.LONGITUDE_JSON_KEY_URL, longitude);
			subJson.put(Constants.LATITUDE_JSON_KEY_URL, latitude);
		} catch (JSONException e1) {
			PublicMethod.outLog(TAG, e1.getMessage());
		}
		return HttpUrlConnPost.ConnectionByPost(json.toString());
	}

	public static String getUUIDStr() {
		UUID uuid = UUID.randomUUID();
		String uuidstr = uuid.toString();
		return uuidstr;
	}

	private static String getJsonfoFromNetBygetUserinfo(String username) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_GET_USER_CODE);
			if (!"".equals(username)) {
				JSONObject subJson = new JSONObject();
				json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
				subJson.put(Constants.USERNAME_JSON_KEY_USER, username);
			}
		} catch (JSONException e1) {
			e1.getMessage();
		}
		PublicMethod.outLog(TAG, "------- " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	public static String searchFriendByUserId(String userId) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_GET_USER_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.USERID_JSON_KEY_USER_ID, userId);
		} catch (JSONException e1) {
			e1.getMessage();
		}
		PublicMethod.outLog(TAG, "------- " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	public static String searchFriendByPhoneNumber(String phoneNumber) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_GET_USER_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.USERNAME_JSON_KEY_USER, phoneNumber);
		} catch (JSONException e1) {
			e1.getMessage();
		}
		PublicMethod.outLog(TAG, "------- " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 根据用户昵称查找好友
	 * 
	 * @param nickName
	 * @param pageIndex
	 * @param maxSize
	 * @return
	 */
	public static String searchFriendByNickName(String nickName,
			String pageIndex, String maxSize) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.SEARCH_USER_BY_NICKNAME_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.NICKNAME_JSON_KEY_USER, nickName);
			subJson.put(Constants.PAGE_INDEX, pageIndex);
			subJson.put(Constants.MAX_SIZE, maxSize);
		} catch (JSONException e1) {
			e1.getMessage();
		}
		PublicMethod.outLog(TAG, "------- " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	public static String searchFriendByCharacterName(String charactername,
			String realm) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_GET_USER_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.CHARACTERNAME, charactername);
			subJson.put(Constants.SERVERREALM, realm);
		} catch (JSONException e1) {
			e1.getMessage();
		}
		PublicMethod.outLog(TAG, "------- " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 获得xmpp服务地址
	 * */
	public static String getXmppNotifyServiceInfo() {
		JSONObject json = new JSONObject();
		try {
			json.put(Constants.METHOD_JSON_KEY_URL,
					Constants.REQUEST_XMPP_NOTIFY_SERVICE_INFO_CODE);
			// json.put(Constants.IMEI_JSON_KEY_URL, HttpUser.Imei);
			PublicMethod.outLog(TAG, json.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "143--"+result);
		return result;
	}

	/**
	 * 获得xmpp服务地址
	 * */
	public static String getXmppServiceInfo() {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_XMPP_SERVICE_INFO_CODE);
			PublicMethod.outLog(TAG, json.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "116--"+result);
		return result;
	}

	// 登陆 login FathodMethod
	public static String getLoginfromNet(final String userName, String password) {
		String result = getDateFromNetByLogin(userName, password);
		PublicMethod.outLog(TAG, "login_result" + result);
		return result;
	}

	// "deviceToken":"apntoken" login
	// 用户登录的接口 sonMethod
	private static String getDateFromNetByLogin(String userName, String password) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_LOGIN_CODE);
			JSONObject subJosn = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJosn);
			subJosn.put(Constants.USERNAME_JSON_KEY_USER, userName);
			subJosn.put(Constants.PASSWORD_JSON_KEY_USER, password);
		} catch (JSONException e1) {
			e1.printStackTrace();

		}
		PublicMethod.outLog(TAG, "login: " + "拼接的url是： " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	// 用户是否被使用 isUsernameInuse fatherMehod
	public static String getChectfromNet(final String userName) {
		String result = getDateFromNetByIsUsernameInuse(userName);
		if (result != null) {
			return result;
		}
		return result;
	}

	// 用户是否被使用 isUsernameInuse sonMehod
	private static String getDateFromNetByIsUsernameInuse(String userName) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_CHECK_USER_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.USERNAME_JSON_KEY_USER, userName);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		PublicMethod.outLog(TAG,
				"isUsernameInuse: " + "拼接的url是： " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	// 退出登陆 FatherMethod logout
	public static String Logout() {
		String result = getDateFromNetByLogout();
		PublicMethod.outLog(TAG, "logout_result" + result);
		if (result != null) {
			return result;
		}
		return result;
	}

	// 退出登陆 sonMethod logout
	private static String getDateFromNetByLogout() {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_LOGOUT_CODE);
			JSONObject json2 = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		PublicMethod.outLog(TAG, "logout: " + "拼接的url是： " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 更新用户坐标
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude纬度
	 * @return
	 */
	public synchronized static String updateUserCoordinates(String longitude,
			String latitude) {
		String result = updateUserCoordinate(longitude, latitude);
		PublicMethod.outLog(TAG, "结果集---->>" + result);
		if (result != null) {
			return result;
		}
		return result;
	}

	/**
	 * 获取验证码 getVerificationCode fatherMethod
	 * 
	 * */
	public static String getPetfromgetVerificationCode(final String phone,
			String type) {
		String result = getDateFromNetBygetVerificationCode(phone, type);
		PublicMethod.outLog(TAG, "getVerificationCode_result" + result);
		if (result != null) {
			return result;
		}
		return result;
	}

	/**
	 * 获取验证码 getVerificationCode sonMethod(找回密码时用)
	 * */
	private static String getDateFromNetBygetVerificationCode(String phone,
			String type) {
		JSONObject json = new JSONObject();
		try {
			json.put(Constants.METHOD_JSON_KEY_URL,
					Constants.REQUEST_GET_VERITY_CODE);
			json.put(Constants.CHANNEL_JSON_KEY_URL, HttpUser.channel);
			json.put(Constants.SN, getUUIDStr());
			json.put(Constants.ENCRYPT, "false");
			json.put(Constants.COMPRESSION, "false");
			json.put(Constants.VERSION_JSON_KEY_URL, Constants.SOFTWARE_VERSION);
			json.put(Constants.MAC_JSON_KEY_URL, HttpUser.MAC);
			json.put(Constants.IMEI_JSON_KEY_URL, HttpUser.Imei);
			JSONObject jso2 = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, jso2);
			jso2.put("phoneNum", phone);
			jso2.put("type", type);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 校验验证码 verifyCode fatherMethod d39153a13f70172fd4183521819cefc6bafb2450
	 * */
	public static String getPetfromverifyCode(final String phone, String code) {
		String result = getDateFromNetByverifyCode(phone, code);
		if (result != null) {
			return result;
		}
		return result;
	}

	/**
	 * 校验验证码 verifyCode sonMethod
	 * */
	private static String getDateFromNetByverifyCode(String phone, String code) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_CHECK_VERITY_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.PHONENUMBER_JSON_KEY_USER, phone);
			json2.put(Constants.CODE_JSON_KEY_USER, code);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 注册
	 * */
	public static String getPetfromregister(String gameId, String characterid,
			String nickname, String phonenumber, String password,
			String birthdate, String gender, String email, String code,
			String image) {
		String result = getDateFromNetByregister(gameId, characterid, nickname,
				phonenumber, password, birthdate, gender, email, code, image);
		PublicMethod.outLog(TAG, "register_result" + result);
		if (result != null) {
			return result;
		}
		return result;
	}

	private static String getDateFromNetByregister(String gameId,
			String characterid, String nickname, String phonenumber,
			String password, String birthdate, String gender, String email,
			String code, String image) {
		PublicMethod.outLog(TAG, nickname + " " + password + " " + phonenumber);
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_REGISTER_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.GAMEID, gameId);
			subJson.put(Constants.CHARACTERID, characterid);
			subJson.put(Constants.NICKNAME_JSON_KEY_USER, nickname);
			subJson.put(Constants.PASSWORD_JSON_KEY_USER, password);
			subJson.put(Constants.BIRTHDATE_JSON_KEY_USER, birthdate);
			subJson.put(Constants.GENDER_JSON_KEY_URL, gender);
			subJson.put(Constants.USERNAME_JSON_KEY_USER, phonenumber);
			subJson.put(Constants.EMAIL_JSON_KEY_USER, email);
			subJson.put(Constants.CODE_JSON_KEY_USER, code);
			subJson.put(Constants.IMAEG_JSON_KEY_USER, image);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		PublicMethod.outLog(TAG, "拼接的url是： " + json.toString());
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	// 修改密码 fatherMethod resetPassword
	public static String getPetfromresetPassword(final String password,
			String phonenumber, String code) {
		PublicMethod.outLog(TAG, password + phonenumber);
		String result = getDateFromNetByresetPassword(password, phonenumber,
				code);
		PublicMethod.outLog(TAG, "resetPassword_result" + result);
		if (result != null) {
			return result;
		}
		return result;
	}

	// 修改密码 sonMethod resetPassword
	private static String getDateFromNetByresetPassword(String password,
			String phonenumber, String code) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REVISEPASSWORD_BY_CODE);
			JSONObject subJosn = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJosn);
			subJosn.put("password", password);
			subJosn.put("phoneNum", phonenumber);
			subJosn.put("xcode", code);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String resetUserInfo(String key, String value) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_MODIFY_USER_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(key, value);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 批量修改用户信息
	 * 
	 * @param data
	 *            包含键值的Map集合
	 * @return
	 */
	public static String resetUserInfo(Map<String, String> data) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_MODIFY_USER_CODE);
			JSONObject json2 = new JSONObject();
			for (Map.Entry<String, String> entry : data.entrySet()) {
				json2.put(entry.getKey(), entry.getValue());
			}
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 添加好友
	 * 
	 * @param userId
	 * @param ruteType
	 *            来源
	 * @return
	 */
	public static String addFriend(String userId, String ruteType) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_ADD_FRIEND_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.GAME_FRIEND_USER_ID_JSON_KEY, userId);
			subJson.put(Constants.ROUTE_TYPE, ruteType);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 删除好友
	 * 
	 * @param userId
	 * @param routeTpe
	 * @return
	 */
	public static String deleteFriend(String userId, String routeType) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_DELETE_FRIEND_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.GAME_FRIEND_USER_ID_JSON_KEY, userId);
			subJson.put(Constants.ROUTE_TYPE, routeType);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 添加好友
	 * 
	 * @param userId
	 * @return
	 */
	public static String addFriend(String userId) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_ADD_FRIEND_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.USERID_JSON_KEY_USER_ID, userId);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 获取用户动态
	 * 
	 * @param method
	 * @param userId
	 * @param pageIndex
	 * @param maxSize
	 * @return
	 */
	public static String getUserDynamic(String method, String userId,
			String pageIndex, String maxSize) {
		JSONObject json = null;
		try {
			json = getCommonRequest(method);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.USERID_JSON_KEY_USER_ID, userId);
			json2.put(Constants.PAGEINDEX_JSON_KEY_URL, pageIndex);
			json2.put(Constants.MAXSIZE_JSON_KEY_URL, maxSize);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 查找附近的玩家
	 * 
	 * @param sex
	 *            性别： 0 男 1女不传查找全部
	 * @param pageIndex
	 *            起始页，不传默认为0
	 * @param maxSize
	 *            条数，不传默认20
	 * @return
	 */
	public static String getNearyByPlayer(String sex, String pageIndex,
			String maxSize) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_NEARYBY_PLAYER_INFO_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.SEX_JSON_KEY_USER, sex);
			json2.put(Constants.LONGITUDE_JSON_KEY_URL, HttpUser.longitude);
			json2.put(Constants.LATITUDE_JSON_KEY_URL, HttpUser.latitude);
			json2.put(Constants.PAGEINDEX_JSON_KEY_URL, pageIndex);
			json2.put(Constants.MAXSIZE_JSON_KEY_URL, maxSize);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 查找同服玩家
	 * 
	 * @param sex
	 *            性别： 0 男 1女不传查找全部
	 * @param gameid
	 *            游戏id
	 * @param realm
	 *            服务器名
	 * @param pageIndex
	 *            起始页，不传默认为0
	 * @param maxSize
	 *            条数，不传默认20
	 * @return
	 */
	public static String getPlayersSameServer(String sex, String gameid,
			String realm, String pageIndex, String maxSize) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_PLAYERS_SAME_SERVER_INFO_CODE);
			// json.put(Constants.TOKEN_JSON_KEY_USER, HttpUser.token);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.SEX_JSON_KEY_USER, sex);
			json2.put(Constants.GAMEID, gameid);
			json2.put(Constants.SERVERREALM, realm);
			json2.put(Constants.PAGEINDEX_JSON_KEY_URL, pageIndex);
			json2.put(Constants.MAXSIZE_JSON_KEY_URL, maxSize);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	public static String deleteGameRole(String gameid, String characterid) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REOUEST_DELETE_GAME_ROLE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.GAMEID, gameid);
			json2.put(Constants.CHARACTERID, characterid);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 绑定邮箱角色
	 * 
	 * @param gameid
	 * @param characterid
	 * @return
	 */
	public static String getBindingGameRole(String gameid, String characterid) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_GAME_ROSE_BINDING_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.GAMEID, gameid);
			json2.put(Constants.CHARACTERID, characterid);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 查找游戏角色
	 * 
	 * @param gameid
	 *            游戏id
	 * @param realm
	 *            服务器
	 * @param guild
	 *            公会名
	 * @param classid
	 *            职业id
	 * @return
	 */
	public static String getGameRole(String gameid, String realm, String guild,
			String classid) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_FIND_ROLE_LIST);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.GAMEID, gameid);
			json2.put(Constants.SERVERREALM, realm);
			json2.put(Constants.SERVERGUILD, guild);
			json2.put(Constants.SERVERCLASSID, classid);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 获取游戏角色认证装备
	 * 
	 * @param gameid
	 * @param realm
	 * @param charactername
	 * @return
	 */
	public static String getGameRoleAuthenticateEquip(String gameid,
			String realm, String charactername) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REOUEST_GET_GAME_ROLE_AUTHENTICATE_EQUIP);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.GAMEID, gameid);
			json2.put(Constants.SERVERREALM, realm);
			json2.put(Constants.CHARACTERNAME, charactername);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 游戏角色认证
	 * 
	 * @param gameid
	 * @param realm
	 * @param charactername
	 * @param authitem
	 *            认证装备，将 "角色认证装备"获取的key 以逗号分隔连成字符串传 key,key
	 * @return
	 */
	public static String getGameRoleAuthenticate(String gameid, String realm,
			String charactername, String authitem) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REOUEST_GET_GAME_ROLE_AUTHENTICATE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.GAMEID, gameid);
			json2.put(Constants.SERVERREALM, realm);
			json2.put(Constants.CHARACTERNAME, charactername);
			json2.put(Constants.AUTHITEM, authitem);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 
	 * @param shiptype
	 *            类型， 1：好友 2：关注 3：粉丝
	 * @param sorttype
	 *            sorttype_1 取好友排序方式， 默认按字母索引 值 1 字母索引， 2 距离 3 按头衔等级 4 按坐标更新时间
	 *            sorttype_2 取关注排序方式， 默认按字母索引 值同上 sorttype_3 取粉丝排序方式， 默认按距离，
	 *            目前只有一种距离排序，值只有 2 不传shiptype输出好友，关注和粉丝
	 * @param pageIndex
	 *            页数， 粉丝分页用
	 * @param maxSize
	 *            条数，粉丝分页用
	 * @param latitude
	 *            维度 经度
	 * @param longitude
	 * @return
	 */
	public static String getFriendList(String userId,String shiptype, String sorttype,String pageIndex, String maxSize) {
		return getDateFromNetBygetFriendList(userId,shiptype, sorttype,pageIndex, maxSize);
	}

	/**
	 * 、getFriendList SonMethod 功能：获取好友列表，如果获取当前登录人的好友列表，则不需要传递 params 参数，用
	 * token 即可获取
	 * 
	 * @param userid
	 * @return
	 */
	private static String getDateFromNetBygetFriendList(String userId,String shiptype,
			String sorttype, String pageIndex, String maxSize) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_FRIED_LIST_CODE);
			JSONObject subjson = new JSONObject();
//			subjson.put(Constants.GAME_SHIPTYPE_JSON_KEY, shiptype);
//			subjson.put(Constants.USERID_JSON_KEY_USER_ID, userId);
//			subjson.put(Constants.GAME_SORTTYPE_JSON_KEY, sorttype);
//			subjson.put(Constants.GAME_PAGEINDEX_JSON_KEY, pageIndex);
//			subjson.put(Constants.GAME_MAXSIZE_JSON_KEY, maxSize);
//			subjson.put(Constants.GAME_LATITUDE_JSON_KEY, HttpUser.latitude);
//			subjson.put(Constants.GAME_LONGITUDE_JSON_KEY, HttpUser.longitude);
			json.put(Constants.PARAMS_JSON_KEY_URL, subjson);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG,
				"getDateFromNetBygetFriendList-->>" + json.toString());
		return result;
	}

	/**
	 * 获取用户所有游戏角色列表
	 * 
	 * @param userid用户id
	 * @return
	 */
	public static String getUserRoleListData(String userid) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_USER_ROLE_LIST_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.USERID_JSON_KEY_USER_ID, userid);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 头衔列表
	 * 
	 * @param userid
	 *            用户id
	 * @param hide
	 *            0是显示， 1是隐藏， 如果不传两种都返回
	 * @return
	 */
	public static String getUserTitleListData(String userid, String hide) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_USER_TITLE_LIST_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.USERID_JSON_KEY_USER_ID, userid);
			json2.put(Constants.HIDE, hide);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 头衔排行
	 * 
	 * @param gameid
	 *            游戏id
	 * @param ranktype
	 *            排行类型，三个值：1，2，3， 1是好友， 2是全服， 3是全国， 大头衔也就是 基本头衔有这个字段值是多个这间用逗号分隔的
	 * @param characterid
	 *            角色id
	 * @param rankvaltype
	 *            排行值类型， 战斗力还是坐骑等
	 * @param pageIndex
	 *            起始页， 如果传 -1 我就认为是取角色id附近的排名， 会返回这个角色的排行，
	 *            如果这个角色的排名就是前几名会返回rank排名为1的，这时pageIndex其实 就相当于为0第一页时的返回数据，
	 *            所以前端要判断取角色排名的时侯是否返 回了rank = 1， 如果有下一页应该是传pageIndex = 1 而不是
	 *            pageIndex = 0
	 * @param maxSize
	 *            记录数
	 * @param realm
	 *            服务器， 全服排行用这个
	 * @param classid
	 *            职业id， 全服和全国用这个， 不传默认全职业
	 * @return
	 */
	public static String getUserTitleRankingData(String gameid,String ranktype, String characterid, String rankvaltype,String pageIndex, String maxSize, String realm, String classid) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_USER_TITLE_RANKING_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.GAMEID, gameid);
			json2.put(Constants.RANKTYPE, ranktype);
			json2.put(Constants.CHARACTERID, characterid);
			json2.put(Constants.RANKVALTYPE, rankvaltype);
			json2.put(Constants.PAGEINDEX_JSON_KEY_URL, pageIndex);
			json2.put(Constants.MAXSIZE_JSON_KEY_URL, maxSize);
			json2.put(Constants.SERVERREALM, realm);
			json2.put(Constants.SERVERCLASSID, classid);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 修改角色头衔排序
	 * 
	 * @param characterid
	 *            角色id
	 * @param conditionType
	 *            modsort 修改排序和显示:
	 * @param titleid
	 *            头衔id用逗号连接起来，1,2,5,9, 排序顺序就是对应的1，2，3，4
	 * @param hide
	 *            隐藏显示 1,0,1,0 0 是显示， 1是隐藏
	 * @return
	 */
	public static String reviseUserHonorStatus(String characterid,
			String conditionType, String titleid, String hide) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_REVISE_USER_HONOR_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.CHARACTERID, characterid);
			json2.put(Constants.CRICLE_CONDITIONTYPE, conditionType);
//			json2.put(Constants.TITLEID, titleid);
			json2.put(Constants.HIDE, hide);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	// feedback
	// 功能：用户反馈
	// 输入：{"feedback":"","Email":""}
	// 输出：{"success":true,"entity":"OK"}

	public static String feedback(String msg, String imsi) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.FEEDBACK_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.PHONENUMBER_JSON_KEY_USER, HttpUser.Username);
			json2.put(Constants.MSG_JSON_KEY_URL, msg);
//			json2.put(Constants.IMSI_KEY, imsi);
			JSONObject json3 = new JSONObject();
			json3.put("Imei", HttpUser.Imei);
			json3.put("Platform", "android");
			json3.put("Machine", android.os.Build.MODEL);
			json3.put("ContactWay", "");
			json2.put("detail", json3);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	/**
	 * 舉報msg消息 detail设备详情 imsi设备标识 type类型 user为举报用户， content为举报内容 id
	 * user为举报的用户id, content为举报的messageid
	 * 
	 * @param msg
	 * @param imsi
	 * @param type
	 * @param id
	 * @return
	 */
	public static String report(String msg, String imsi, String type, String id) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REPORT_CODE);
			JSONObject json2 = new JSONObject();
			json2.put(Constants.MSG_JSON_KEY_URL, msg);
//			json2.put(Constants.IMSI_KEY, imsi);
			json2.put("id", id);
			json2.put("type", type);
			JSONObject json3 = new JSONObject();
			json3.put("Imei", HttpUser.Imei);
			json3.put("Platform", "android");
			json3.put("Machine", android.os.Build.MODEL);
			json3.put("ContactWay", "");
			json2.put("detail", json3);
			json.put(Constants.PARAMS_JSON_KEY_URL, json2);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "json.toString()" + json.toString());
		return result;
	}

	// ------------------------------以下是用户动态--------------------------------

	public static JSONObject getCommonRequest(String method) {
		JSONObject json = new JSONObject();
		try {
			json.put(Constants.METHOD_JSON_KEY_URL, method);
			json.put(Constants.CHANNEL_JSON_KEY_URL, HttpUser.channel);
			json.put(Constants.TOKEN_JSON_KEY_URL, HttpUser.token);
			json.put(Constants.SN, getUUIDStr());
			json.put(Constants.ENCRYPT, "false");
			json.put(Constants.COMPRESSION, "false");
			json.put(Constants.VERSION_JSON_KEY_URL, HttpUser.clineVersionNane);
			json.put(Constants.MAC_JSON_KEY_URL, HttpUser.MAC);
			json.put(Constants.IMEI_JSON_KEY_URL, HttpUser.Imei);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	// 获取短信验证码
	public static String getDateFromNetVerifyCode(String phoneNum) {
		JSONObject json = null;
		try {
			json = getCommonRequest(Constants.REQUEST_GET_VERITY_CODE);
			JSONObject jso2 = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, jso2);
			jso2.put(Constants.USERNAME_JSON_KEY_USER, phoneNum);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	// 获取魔兽用户
	public static String getRoseInfoFromNet(String gameId, String gamerealm,
			String gameName) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_GAME_ROSE_INFO_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.GAMEID, gameId);
			subJson.put(Constants.GAMEREALM, gamerealm);
			subJson.put(Constants.GAMENAME, gameName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	// 陌游Open接口
	public static String getInformateFromNetByOpen() {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_OPEN_CODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	// 陌游新的Open接口
	public static String getInformateFromNetByOpenNews(String gamelist_millis,
			String wow_realms_millis, String wow_characterclasses_millis,
			String version) {
		JSONObject json = new JSONObject();
		try {
			json.put(Constants.METHOD_JSON_KEY_URL,Constants.REQUEST_OPEN_CODE_NEW);
			json.put(Constants.CHANNEL_JSON_KEY_URL, HttpUser.channel);
			json.put(Constants.TOKEN_JSON_KEY_URL, "");
			json.put(Constants.SN, getUUIDStr());
			json.put(Constants.ENCRYPT, "false");
			json.put(Constants.COMPRESSION, "false");
			json.put(Constants.VERSION_JSON_KEY_URL, version);
			json.put(Constants.MAC_JSON_KEY_URL, HttpUser.MAC);
			json.put(Constants.IMEI_JSON_KEY_URL, HttpUser.Imei);
			JSONObject subJson = new JSONObject();
			subJson.put("token", HttpUser.token);
			subJson.put("gamelist_millis", gamelist_millis);
			subJson.put("wow_realms_millis", wow_realms_millis);
			subJson.put("wow_characterclasses_millis",wow_characterclasses_millis);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			PublicMethod.outLog(TAG, "open接口请求参数-->>" + json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG, "open接口返回结果-->>" + result);
		return result;
	}

	/**
	 * 获取 我的动态 的接口 userid 用户id pageIndex 页数 maxSize 条数
	 */

	public static String getMyDynamic(String userId, String pageIndex,
			String maxSize) {
		String result = getDateFromNetByGetMyDynamic(userId, pageIndex, maxSize);
		if (result != null) {
			return result;
		}
		return result;
	}

	private static String getDateFromNetByGetMyDynamic(String userId,
			String pageIndex, String maxSize) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_USER_DYNAMIC_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.USERID_JSON_KEY_USER_ID, userId);
			subJson.put(Constants.PAGEINDEX_JSON_KEY_URL, pageIndex);
			subJson.put(Constants.MAXSIZE_JSON_KEY_URL, maxSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 我的动态 -----详情 ----- 的接口 messageid
	 */
	public static String getMyDynamicDetail(String messageid) {
		String result = getDateFromNetByGetMyDynamicDetail(messageid);
		if (result != null) {
			return result;
		}
		return result;
	}

	private static String getDateFromNetByGetMyDynamicDetail(String messageid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_USER_DYNAMIC_DETAIL_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.MESSAGEID_JSON_KEY_URL, messageid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 134 发布动态消息 type 消息类型 3 发表， 4 赞， 5 评论 title 标题 发表文章时需要 msg 消息内容 发表消息，文章，
	 * 评论时需要 img 发表消息和文章的配图， 多个图用逗号隔开 urlLink 链接 messageid 赞和评论时需要
	 */

	public static String publishDynamicMessage(String type, String title,
			String msg, String img, String urlLink, String messageid) {
		return getDateFromNetByGetPublishDynamicMessage(type, title,msg, img, urlLink, messageid);
	}

	private static String getDateFromNetByGetPublishDynamicMessage(String type,
			String title, String msg, String img, String urlLink,
			String messageid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.PUBLISH_DYNAMIC_MESSAGE_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			if (!type.equals("")) {
				subJson.put(Constants.TYPE_JSON_KEY_URL, type);
			}
			if (!title.equals("")) {
				subJson.put(Constants.TITLE_JSON_KEY_URL, title);
			}
			if (!msg.equals("")) {
				subJson.put(Constants.MSG_JSON_KEY_URL, msg);
			}
			if (!img.equals("")) {
				subJson.put(Constants.IMG_JSON_KEY_URL, img);
			}
			if (!urlLink.equals("")) {
				subJson.put(Constants.URLLINK_JSON_KEY_URL, urlLink);
			}
			if (!messageid.equals("")) {
				subJson.put(Constants.MESSAGEID_JSON_KEY_URL, messageid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 获取好友动态 的接口 messageid
	 */
	public static String getFriendDynamicList(String userId, String pageIndex,
			String maxSize) {
		String result = getDateFromNetByGetFriendDynamicList(userId, pageIndex,
				maxSize);
		return result;
	}

	private static String getDateFromNetByGetFriendDynamicList(String userId,
			String pageIndex, String maxSize) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.GET_FRIEND_DYNAMIC_MESSAGE_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.USERID_JSON_KEY_USER_ID, userId);
			subJson.put(Constants.PAGEINDEX_JSON_KEY_URL, pageIndex);
			subJson.put(Constants.MAXSIZE_JSON_KEY_URL, maxSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * messageid 消息id， 如果消息是赞或者评论， 后台判断返回目标消息评论 pageIndex 页码 maxSize 条数
	 * messageid
	 */
	public static String getDynamicPingLunList(String messageid,
			String pageIndex, String maxSize) {
		String result = getDateFromNetBygetDynamicPingLunList(messageid,
				pageIndex, maxSize);
		if (result != null) {
			return result;
		}
		return result;
	}

	private static String getDateFromNetBygetDynamicPingLunList(
			String messageid, String pageIndex, String maxSize) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.GET_DYNAMIC_PINGLUN_LIST_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.MESSAGEID_JSON_KEY_URL, messageid);
			subJson.put(Constants.PAGEINDEX_JSON_KEY_URL, pageIndex);
			subJson.put(Constants.MAXSIZE_JSON_KEY_URL, maxSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}
	
	/**
	 * 修改好友别名
	 */
	public static String getUpdateFriendNickNameData(String frienduserid,
			String friendalias) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_MODIFY_FRIED_INFO_CODE);
			JSONObject subJson = new JSONObject();
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
			subJson.put(Constants.GAME_FRIEND_USER_ID_JSON_KEY, frienduserid);
//			subJson.put(Constants.GAME_FRIDNE_DALIAS_JSON_KEY, friendalias);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		PublicMethod.outLog(TAG,
				"getUpdateFriendNickNameData" + json.toString() + "---"
						+ result);
		return result;
	}

	/**
	 * 检查网络
	 * */
	public static boolean CheckNet(Context context) {
		if (NetType.CheckNetwork(context) != true
				&& NetType.CheckNetWIFI(context) != true) {
			return false;
		}
		return true;
	}

	public static String CheckTokenFail() {
		JSONObject json3 = new JSONObject();
		try {
			json3.put(Constants.METHOD_JSON_KEY_URL,
					Constants.REQUEST_USER_TOKEN_FAIL);
			json3.put(Constants.CHANNEL_JSON_KEY_URL, HttpUser.channel);
			json3.put(Constants.TOKEN_JSON_KEY_URL, "");
			json3.put(Constants.SN, getUUIDStr());
			json3.put(Constants.ENCRYPT, "false");
			json3.put(Constants.COMPRESSION, "false");
			json3.put(Constants.VERSION_JSON_KEY_URL,
					Constants.SOFTWARE_VERSION);
			json3.put(Constants.MAC_JSON_KEY_URL, HttpUser.MAC);
			json3.put(Constants.IMEI_JSON_KEY_URL, HttpUser.Imei);

			JSONObject json4 = new JSONObject();
			json3.put(Constants.PARAMS_JSON_KEY_URL, json4);
			json4.put(Constants.TOKEN_JSON_KEY_URL, HttpUser.token);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json3.toString());
		PublicMethod.outLog(TAG, "CheckTokenFail" + json3.toString() + "---"
				+ result);
		return result;
	}

	/**
	 * 144 分享动态的接口 messageid 转发的动态id touserid 转给用户的userid， 多个用户之间用逗号分隔
	 * 
	 * @return
	 */

	public static String shareDynamic(String messageid, String touserid) {
		String result = getDateFromNetByShareDynamic(messageid, touserid);
		if (result != null) {
			return result;
		}
		return result;
	}

	private static String getDateFromNetByShareDynamic(String messageid,
			String touserid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_SHARE_DYNAMIC);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.MESSAGEID_JSON_KEY_URL, messageid);
			subJson.put(Constants.TOUSERID, touserid);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 广播动态的接口 145 转发动态到好友和粉丝聊天信息 messageid 转发的动态id
	 */

	public static String broadCastDynamic(String messageid) {
		String result = getDateFromNetBybroadCastDynamic(messageid);
		if (result != null) {
			return result;
		}
		return result;
	}

	private static String getDateFromNetBybroadCastDynamic(String messageid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.BROAD_CAST_DYNAMIC);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.MESSAGEID_JSON_KEY_URL, messageid);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 角色详情
	 * 
	 * @param gameid
	 *            游戏id
	 * @param characterid
	 *            游戏角色id
	 * @return
	 */
	public static String getRoleInfo(String gameid, String characterid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.REQUEST_ROLE_INFO);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.GAMEID, gameid);
			subJson.put(Constants.CHARACTERID, characterid);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 更新游戏角色排名
	 * 
	 * @param gameid
	 *            游戏id
	 * @param characterid
	 *            游戏角色id
	 * 
	 * @return
	 */
	public static String getUpdateRoleInfo(String gameid, String characterid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.UPDATE_REQUEST_ROLE_INFO);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.GAMEID, gameid);
			subJson.put(Constants.CHARACTERID, characterid);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 更新排名查询系统状态 gameid游戏id characterid角色id 输出排名内容
	 * json里存在systemstate，值为ok为系统可接受159来直接更新用户排名,
	 * 值为busy为系统忙，已把更新请求放入更新队列等待更新，index为角色在更新列表的位置
	 * ，index如果为0为当前正在更新角色排名，time为预计更新时间
	 * (单位为毫秒，前端自行转换)，如返回没有systemstate时，返回更新后的结果(同159输出一样)
	 * 
	 * @return
	 */
	public static String getUpdateRoleStatusInfo(String gameid,
			String characterid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.UPDATE_REQUEST_STATUS_ROLE_INFO);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.GAMEID, gameid);
			subJson.put(Constants.CHARACTERID, characterid);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 许愿池
	 * 
	 * @param gameid
	 *            游戏id
	 * @param characterid
	 *            游戏角色id
	 * @return
	 */
	public static String getMeetInfo(String gameid, String characterid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.NEW_REQUEST_MEET);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.GAMEID, gameid);
			subJson.put(Constants.CHARACTERID, characterid);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}
	/**
	 * 许愿池
	 * 
	 * @param gameid
	 *            游戏id
	 * @param characterid
	 *            游戏角色id
	 * @return
	 */
	public static String getMeetInfo(String gameid, String characterid,String testIndex) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.NEW_REQUEST_MEET);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.GAMEID, gameid);
			subJson.put(Constants.CHARACTERID, characterid);
			subJson.put("testIndex", testIndex);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 
	 * @param gameId
	 *            gameid 游戏id
	 * @param characterId
	 *            characterid 角色id
	 * @param rouserId
	 *            touserid 招招呼人的userid
	 * @param roll
	 *            roll 许愿池接口返回的roll点
	 * @return
	 */
	public static String getMeetSayHello(String gameId, String characterId,
			String rouserId, String index, String sayHelloType) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.NEW_MEET_SAY_HELLO_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.GAMEID, gameId);
			subJson.put(Constants.CHARACTERID, characterId);
			subJson.put(Constants.ROUSER_ID, rouserId);
			subJson.put(Constants.INDEX, index);
			subJson.put(Constants.SAYHELLOTYPE, sayHelloType);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 激活码
	 * 
	 * @param invitationCode
	 *            激活码
	 * @return
	 */
	public static String getActivate(String invitationCode) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.ACTIVATE_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.INVITATION_CODE, invitationCode);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 妹子认证
	 * 
	 * @param img
	 *            妹子认证的图片id
	 * @return
	 */
	public static String getAuthentication(String img) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.AUTHENTICATION_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.IMG, img);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**
	 * 添加第一次打招呼153 touserid打招呼人的userid
	 * 
	 * @param touserid
	 * @return
	 */
	public static String getSayHelloFirst(String touserid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.SAY_HELLO_FIRST_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.TOUSERID, touserid);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	/**154
	 * 打过招呼的列表 touserid招招呼人的userid, 可不传   输出打过招呼人的userid列表
	 * 有传touserid的话只返回这个touserid，返回空说明没有打过招呼或者状态已变化为好友或关注
	 * 
	 * @param touserid
	 * @return
	 */
	public static String getSayHelloList(String touserid) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.SAY_HELLO_LIST_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.TOUSERID, touserid);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}

	public static Map<String, Object> getCommonRequest2(String method) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Constants.METHOD_JSON_KEY_URL, method);
		params.put(Constants.CHANNEL_JSON_KEY_URL, HttpUser.channel);
		params.put(Constants.TOKEN_JSON_KEY_URL, HttpUser.token);
		params.put(Constants.SN, getUUIDStr());
		params.put(Constants.ENCRYPT, "false");
		params.put(Constants.COMPRESSION, "false");
		params.put(Constants.VERSION_JSON_KEY_URL, HttpUser.clineVersionNane);
		params.put(Constants.MAC_JSON_KEY_URL, HttpUser.MAC);
		params.put(Constants.IMEI_JSON_KEY_URL, HttpUser.Imei);
		return params;
	}

	/**
	 * 上传通讯录
	 * 
	 * @param contacts
	 * @return
	 */
	public static String getContact(List<ContactsBean> contacts) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Constants.CONTACTS, contacts);
		Map<String, Object> method = getCommonRequest2(Constants.CONTACTS_CODE);
		method.put(Constants.PARAMS_JSON_KEY_URL, params);
		String result = HttpUrlConnPost.ConnectionByPost(JSON
				.toJSONString(method));
		return result;
	}

	/**
	 * 导入游戏内好友
	 * 
	 * @param gameId游戏id
	 * @param characterid角色id
	 * @return
	 */
	public static String getGameFriend(String gameId, String characterid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Constants.GAMEID, gameId);
		params.put(Constants.CHARACTERID, characterid);
		Map<String, Object> method = getCommonRequest2(Constants.GAME_FRIEND_CODE);
		method.put(Constants.PARAMS_JSON_KEY_URL, params);
		String result = HttpUrlConnPost.ConnectionByPost(JSON
				.toJSONString(method));
		return result;
	}
	/**
	 * 校验兑换码
	 * 
	 * @param cdkey兑换码
	 * @param characterid角色id
	 * @return
	 */
	public static String checkCDKey(String characterId, String exchangeCode) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.CHECK_CD_KEY_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.EXCHANGECODE, exchangeCode);
			subJson.put(Constants.CHARACTERIDBIG, characterId);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}
	/**
	 * 使用兑换码
	 * 
	 * @param cdkey兑换码
	 * @param characterid角色id
	 * @return
	 */
	public static String usedCDKey(String characterId, String exchangeCode) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.USED_CD_KEY_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.EXCHANGECODE, exchangeCode);
			subJson.put(Constants.CHARACTERIDBIG, characterId);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}
	/**
	 * 删除动态
	 * @param messageId 动态Id
	 * @return
	 */
	public static String deleteDynamic(String messageId) {
		JSONObject json = new JSONObject();
		try {
			json = getCommonRequest(Constants.DELETE_CODE_KEY_CODE);
			JSONObject subJson = new JSONObject();
			subJson.put(Constants.MESSAGE_ID, messageId);
			json.put(Constants.PARAMS_JSON_KEY_URL, subJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = HttpUrlConnPost.ConnectionByPost(json.toString());
		return result;
	}
}
