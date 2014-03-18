package com.ruyicai.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 */
public class JsonUtils {

	public static <T> T resultData(String key, String jsonString, Class<T> cls) {
		return JSON.parseObject(readjsonString(key, jsonString), cls);
	}

	public static <T> T resultData(String jsonString, Class<T> cls) {
		return JSONObject.parseObject(jsonString, cls);
	}

	public static <T> List<T> getList(String jsonString, Class<T> clszz) {
		return JSONArray.parseArray(jsonString, clszz);
	}

	public static <T> List<T> getList(String jsonString, Class<T> cls, String key) {
		return JSONArray.parseArray(readjsonString(key, jsonString), cls);
	}

	public static String readjsonString(String key, String jsonStringString) {
		return JSONObject.parseObject(jsonStringString).getString(key);
	}
	public static <T> Map<String,List<T>> getKeyMap(String successResult,Class<T> clszz) {
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		try {
			JSONObject userJsonObj = JSONObject.parseObject(successResult);
			Set<String> setString=userJsonObj.keySet();
			for(String key:setString){
				map.put(key, getList(successResult, clszz, key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static <T> List<T> getListFromKey(String successResult,Class<T> clszz) {
		List<T> list=new ArrayList<T>();
		try {
			JSONObject userJsonObj = JSONObject.parseObject(successResult);
			Set<String> setString=userJsonObj.keySet();
			for(String key:setString){
				list.addAll(getList(successResult, clszz, key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static <T> List<T> getListFromKeyMap(String successResult,Class<T> clszz){
		List<T> list=new ArrayList<T>();
		 Map<String,List<T>> map=getKeyMap(successResult,clszz);
		 if(map!=null){
			 for(Map.Entry<String, List<T>> mapSet:map.entrySet()){
				 list.addAll(mapSet.getValue());
			 }
		 }
		 return list;
	}
	
	public static <T> List<T> paserAllFriends(String ectityResult,Class<T> clszz){
		List<T> allListUser=new ArrayList<T>();
		JSONObject jsonObject=JSONObject.parseObject(ectityResult);
		Set<String> key=jsonObject.keySet();
		for(String resultKey:key){
			List<T> listUser=getListFromKeyMap(readjsonString(resultKey, ectityResult), clszz);
			allListUser.addAll(listUser);
		}
		return allListUser;
	}
	public static <T> Map<String, T> getMap(String jsonString, Class<T> clszz, String... keys) {
		Map<String, T> map = new HashMap<String, T>();
		JSONObject jObject = JSONObject.parseObject(jsonString);
		for (String key : keys) {
			map.put(key, jObject.getObject(key, clszz));
		}
		return map;
	}
}
