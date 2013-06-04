package com.ruyicai.net;

import java.util.Random;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.util.Constants;
import com.ruyicai.util.URLEncoder;

public class JrtLot {

	private static String baseUrl = Constants.SERVER_URL; // ������
	// ������
//	private static String messageUrl = Constants.MSG_URL;
	private static String lotServer=Constants.LOT_SERVER;
	public static String versionInfo = "2.3";// ��ǰ�汾��

	/**
	 * �ͻ��˿�����ѯ
	 * @param play_name   ��Ʊ����
	 * @param term_code   ��Ʊ�ں�
	 * @return
	 */
	public static String lotterySelect(String play_name) {
		String action = "twininfoQuery.do";
		String method = "lotterySelect";
		String reValue = "";
		try {
			Random rdm = new Random();
			int transctionId = rdm.nextInt();
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("inputCharset", 2);
			paras.put("version", "v2.0");
			paras.put("language", 1);
			paras.put("transctionid", transctionId);
			// paras.put("mobile_code", "mobile_code");
			paras.put("play_name", play_name);
			para = URLEncoder.encode(paras.toString());

			String re = InternetUtils.GetMethodOpenHttpConnect(baseUrl + action + "?method="+ method + "&jsonString=" + para);
			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {
		}
		return reValue;
	}
	/**
	 * �ͻ��˵�ǰ�ں�����
	 * @param lotNo
	 */
	public static String getLotNo(String lotNo) {
		String action = "touzhu.do";
		String method = "getLotNo";
		String reValue = "";
		String para = "";
		try {
			Random rdm = new Random();
			int transctionId = rdm.nextInt();
			JSONObject paras = new JSONObject();
			paras.put("inputCharset", 2);
			paras.put("version", "v2.0");
			paras.put("language", 1);
			paras.put("transctionid", transctionId);
			paras.put("lotNo", lotNo);
			para = URLEncoder.encode(paras.toString());
			reValue = InternetUtils.GetMethodOpenHttpConnect(baseUrl + action + "?method="
					+ method + "&jsonString=" + para);
		} catch (Exception e) {
			
		}
		return reValue;
	}

	/**
	 * �ͻ�����ʵ�ǰ�ں�����
	 * @param lotNo
	 */
	public static String getZCLotNo(String lotNo) {
		String action = "zcAction.do";
		String method = "getNewIssue";
		String reValue = "";
		String para = "";
		try {
			JSONObject paras = new JSONObject();

			paras.put("lotno", lotNo);
			para = URLEncoder.encode(paras.toString());
			reValue = InternetUtils.GetMethodOpenHttpConnect(baseUrl + action + "?method="
					+ method + "&jsonString=" + para);
		} catch (Exception e) {
			
		}
		return reValue;
	}

	
	/**
	 * ȡ��׷��
	 * @param mobileCode
	 * @param sessionid
	 * @param tsubscribeId
	 * @return
	 */
	public static String cancelTrankingNumber(String mobileCode,
			String sessionid, String tsubscribeId) {
			String action = "lotTCBet.do";
			String method = "trankingNumberUpdate";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();

			paras.put("mobileCode", mobileCode);
			paras.put("tsubscribeId", tsubscribeId);

			para = URLEncoder.encode(paras.toString());

			reValue = InternetUtils.GetMethodOpenHttpConnect(baseUrl + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);

		} catch (Exception e) {
		}
		return reValue;
	}

	/**
	 * ������Ϣ
	 * @param sessionid         �û���¼���id
	 * @param idcardno          ���֤��
	 * @return
	 */
	public static String perfectIfo(String sessionid, String idcardno) {
		String action = "idcardadd.do";
		String method = "add";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("idcardno", idcardno);
			para = URLEncoder.encode(paras.toString());
			String re = InternetUtils.GetMethodOpenHttpConnect(baseUrl + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);

			// �Է�������д���
			JSONObject object;
			object = new JSONObject(re);
			String error_code = object.getString("error_code");

			if (error_code.equals("000000")) {// �޸ĳɹ�
				Toast.makeText(JrtLot.context, "�޸ĳɹ�", Toast.LENGTH_SHORT)
						.show();
			} else if (error_code.equals("000001")) {// ���֤Ϊ�գ�������Ϣ�Ի���
				Toast.makeText(JrtLot.context, "���֤Ϊ��", Toast.LENGTH_SHORT)
						.show();
			} else if (error_code.equals("000002")) {// ԭ�������֤�����������޸ģ��޸���Ϣ�Ի���
				Toast.makeText(JrtLot.context, "ԭ�������֤�����������޸�",
						Toast.LENGTH_SHORT).show();
			} else if (error_code.equals("000003")) {// �޸�ʧ��
				Toast.makeText(JrtLot.context, "�޸�ʧ��", Toast.LENGTH_SHORT)
						.show();
			} else if (error_code.equals("070002")) {// �û�δ��¼
				Toast.makeText(JrtLot.context, "�û�δ��¼", Toast.LENGTH_SHORT)
						.show();
			} else if (error_code.equals("000004")) {// ���֤�ѱ�ʹ��
				Toast.makeText(JrtLot.context, "���֤�����ѱ�ʹ��", Toast.LENGTH_SHORT)
						.show();
			} else if (error_code.equals("000005")) {// ���䲻��18����
				Toast.makeText(context, "���䲻��18����", Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
		}

		return null;
	}

	public static String sessionid;
	public static Context context;

	/**
	 * ������Ϣ�Ի���
	 */
	public static void perfectIfoDialog(String sessionid) {
		JrtLot.sessionid = sessionid;
		LayoutInflater inflater = LayoutInflater.from(context);
		final View textEntryView = inflater.inflate(R.layout.perfect_message_dialog, null);
		final EditText name = (EditText) textEntryView
				.findViewById(R.id.et_perfect_message_name);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.perfect_message_title);
		builder.setView(textEntryView);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						perfectIfo(JrtLot.sessionid, name.getText().toString());

					}

				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
	    public void onClick(DialogInterface dialog, int which) {

					}

				});
		builder.show();
	}

	/**
	 * �޸���Ϣ�Ի���
	 */
	public static void changePerfectIfoDialog(String sessionid) {
		JrtLot.sessionid = sessionid;
		LayoutInflater inflater = LayoutInflater.from(context);
		final View textEntryView = inflater.inflate(
				R.layout.perfect_message_dialog, null);
		final EditText name = (EditText) textEntryView
				.findViewById(R.id.et_perfect_message_name);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.change_message_title);
		builder.setView(textEntryView);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						perfectIfo(JrtLot.sessionid, name.getText().toString());

					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		builder.show();
	}

	/**
	 * DNA��ֵ�˻��󶨲�ѯ����
	 * @param mobile
	 * @param sessionid
	 * @return
	 */
	public static String checkType(String mobile, String sessionid) {
		String action = "dnabind.do";
		String method = "bindInfoFind";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("Mobile", mobile);

			para = URLEncoder.encode(paras.toString());
			reValue = InternetUtils.GetMethodOpenHttpConnect(baseUrl + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);

		} catch (Exception e) {
		}

		return reValue;
	}
	

}
