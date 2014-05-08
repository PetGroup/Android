package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.activity.buy.guess.util.RuyiGuessConstant;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.RuyiGuessGatherBean;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;

/***
 * 
 * @author yejc
 *
 */
public class RuyiGuessInterface {
	private String COMMAND = "quiz";
	private String REQUESTTYPE = "list";
	private String DETAIL_REQUESTTYPE = "detail";
	private String SEND_REQUESTTYPE = "participate";
	/**
	 * 创建扎堆请求命令
	 */
	private String COMMAND_GROUPQUIZ ="groupQuiz";
	
	private static RuyiGuessInterface instance;

	private RuyiGuessInterface() {}

	public synchronized static RuyiGuessInterface getInstance() {
		if (instance == null) {
			instance = new RuyiGuessInterface();
		}
		return instance;
	}
	
	/***
	 * 获取如意竞猜题目列表
	 * @param userno
	 * @param pageIndex
	 * @param maxResult
	 * @return
	 */
	public String getRuyiGuessList(String pageIndex, String maxResult, 
			String userno, String type) {
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance()
					.getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.REQUESTTYPE, REQUESTTYPE);
			jsonProtocol.put(ProtocolManager.PAGEINDEX, pageIndex);
			jsonProtocol.put(ProtocolManager.MAXRESULT, maxResult);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.TYPE, type);
			
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(
					Constants.LOT_SERVER, jsonProtocol.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/***
	 * 获取如意竞猜未结束的题目列表
	 * @param userno
	 * @param pageIndex
	 * @param maxResult
	 * @return
	 */
	public String getRuyiGuessSubjectList(String pageIndex, String maxResult, 
			String userno, String type,String state) {
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance()
					.getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.REQUESTTYPE, REQUESTTYPE);
			jsonProtocol.put(ProtocolManager.PAGEINDEX, pageIndex);
			jsonProtocol.put(ProtocolManager.MAXRESULT, maxResult);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.TYPE, type);
			jsonProtocol.put(RuyiGuessConstant.GUESSSUBJECT_STATE, state);
			
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(
					Constants.LOT_SERVER, jsonProtocol.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/***
	 * 获取如意竞猜详情列表
	 * @param userno
	 * @param pageIndex
	 * @param maxResult
	 * @return
	 */
	public String getRuyiGuessDetailList(String pageIndex, String maxResult, String userno,
			String id, String type) {
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance()
					.getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.REQUESTTYPE, DETAIL_REQUESTTYPE);
			jsonProtocol.put(ProtocolManager.PAGEINDEX, pageIndex);
			jsonProtocol.put(ProtocolManager.MAXRESULT, maxResult);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put("id", id);
			jsonProtocol.put(ProtocolManager.TYPE, type);
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(
					Constants.LOT_SERVER, jsonProtocol.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/***
	 * 获取如意竞猜详情列表
	 * @param userno
	 * @param pageIndex
	 * @param maxResult
	 * @return
	 */
	public String sendDateToService(String userno,
			String id, String info) {
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance()
					.getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.REQUESTTYPE, SEND_REQUESTTYPE);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put("id", id);
			jsonProtocol.put("info", info);
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(
					Constants.LOT_SERVER, jsonProtocol.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/***
	 * 获取如意竞猜详情列表
	 * @param userno
	 * @param pageIndex
	 * @param maxResult
	 * @return
	 */
	public String getRuyiGuessImageList() {
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance()
					.getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, "image");
			jsonProtocol.put(ProtocolManager.REQUESTTYPE, REQUESTTYPE);
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(
					Constants.LOT_SERVER, jsonProtocol.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/***
	 * 发送赞/踩状态
	 * @param type
	 * @param userno
	 * @param id
	 * @return
	 */
	public String sendPraiseOrThredState(String type, String userno, String id) {
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance()
					.getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.REQUESTTYPE, type);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put("id", id);
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(
					Constants.LOT_SERVER, jsonProtocol.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 发送创建扎堆请求
	 * @param type
	 * @param userno
	 * @param bean
	 * @return
	 */
	public String sendCreateGroup(String type,String userno,RuyiGuessGatherBean bean){
		String result = "";
		JSONObject data = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			data.put(ProtocolManager.COMMAND, COMMAND_GROUPQUIZ);
			data.put(ProtocolManager.REQUESTTYPE, type);
			data.put(ProtocolManager.USERNO, userno);
			data.put(RuyiGuessConstant.NAME, bean.getGatherName());//扎堆名称
			data.put(RuyiGuessConstant.DESCRIPTION, bean.getGatherDescription());
			data.put(RuyiGuessConstant.PASSWORD, bean.getGatherPwd());
			data.put(RuyiGuessConstant.GUESSSUBJECT_ID, bean.getGatherDescription());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		result = InternetUtils.GetMethodOpenHttpConnectSecurity(
				Constants.LOT_SERVER, data.toString());
		return result;
	}

}
