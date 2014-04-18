package com.ruyicai.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.ruyicai.model.HttpUser;


public class HttpUrlConnPost {

	/**
	 * 连接服务器
	 * 
	 * @param strJson
	 * @return ？后面的Json字符串
	 */
	public static String ConnectionByPost(String strJson) {
		URL url = null;
		HttpURLConnection urlConn = null;
		InputStreamReader inputReader = null;
		BufferedReader buffer = null;
		StringBuffer sb = new StringBuffer();
		String inputLine = null;
		DataOutputStream out = null;
		try {
			url = new URL(HttpUser.URL_POST);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setConnectTimeout(5 * 1000);
			urlConn.setReadTimeout(30 * 1000);
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setInstanceFollowRedirects(false);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConn.connect();
			out = new DataOutputStream(urlConn.getOutputStream());
			String content = "body="
					+ URLEncoder.encode(strJson.toString(), "UTF-8");
			out.writeBytes(content);
			out.flush();
			if (urlConn.getResponseCode() == 200) {
				inputReader = new InputStreamReader(urlConn.getInputStream());
				buffer = new BufferedReader(inputReader);
				while ((inputLine = buffer.readLine()) != null) {
					sb.append(inputLine);
				}
			} else {
				inputLine = "获取服务响应失败!";
				sb.append(inputLine);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return sb.toString();
		} finally {
			try {
				if (inputReader != null) {
					inputReader.close();
					urlConn.disconnect();
				} else {
				}
			} catch (IOException e) {
			}
		}
	}
}
