package com.ruyicai.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.Message;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.ruyicai.constant.Constants;

import android.content.Context;


public class PullParseXml {
	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isExistFile(String fileName) {
		File file = new File(Constants.SD+fileName);
		if(file.exists()) {
			return true;
		}
		return false;
	} 
	/**
	 * 追加到文件
	 * 
	 * @param xml
	 * @param fileName
	 */
	public static String SaveFile(Context context,String contents, String fileName, boolean append) {
		if (!FileUtils.isSdcardExist()) {
			return null;
		}
		FileOutputStream fileOutputStream = null;
		File file = context.getDir("ruyicai", Context.MODE_PRIVATE);  
		if (file == null) {
			return null;
		}
		String newFilePath=file.getAbsolutePath()+fileName;
		FileUtils.createNewFile(newFilePath);
		try {
			fileOutputStream = new FileOutputStream(newFilePath, append);
			fileOutputStream.write(contents.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(fileOutputStream != null){
					fileOutputStream.flush();
					fileOutputStream.close();
				}
			} catch (IOException e) {
				return null;
			}
		}
		return newFilePath;
	}
	/**
	 * 读取inputstrean
	 * 
	 * @param filename
	 * @return
	 */
	public static InputStream getLastJson(String filename) {
		FileInputStream in;
		try {
			File file = new File(Constants.SD + filename);
			in = new FileInputStream(file);
			return (InputStream)in;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 读取json文件
	 * 
	 * @param filename
	 * @return
	 */
	public static String getStringFromSd(Context context,String filename) {
		FileInputStream in;
		try {
			String filePath = context.getDir("ruyicai", Context.MODE_PRIVATE).getAbsolutePath();  
			File file = new File(filePath+ filename);
			in = new FileInputStream(file);
			return getStreamString(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将一个输入流转化为字符串
	 */
	public static String getStreamString(FileInputStream tInputStream) {
		if (tInputStream != null) {
			try {
				BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = new String("");
				while ((sTempOneLine = tBufferedReader.readLine()) != null) {
					tStringBuffer.append(sTempOneLine);
				}
				return tStringBuffer.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 删除文件
	 */
	public static void deleteFile(String fileName) {
		if (fileName != null && fileName.length() > 0) {
			try {
				File file = new File(Constants.SD+fileName);
				if (file.exists()) {
					file.delete();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
	
	public static List<Message> PullParseXML(InputStream in){
		List<Message> list=null;
		Message message = null;
		//构建XmlPullParserFactory
		try {
			XmlPullParserFactory pullParserFactory=XmlPullParserFactory.newInstance();
			//获取XmlPullParser的实例
			XmlPullParser xmlPullParser=pullParserFactory.newPullParser();
			//设置输入流  xml文件
			xmlPullParser.setInput(in, "UTF-8");
			//开始
			int eventType=xmlPullParser.getEventType();
			try {
				while(eventType!=XmlPullParser.END_DOCUMENT){
					String nodeName=xmlPullParser.getName();
					switch (eventType) {
					//文档开始
					case XmlPullParser.START_DOCUMENT:
						list=new ArrayList<Message>();
						break;
					//开始节点
					case XmlPullParser.START_TAG:
						if("message".equals(nodeName)){
							message= new Message();
							message.setTo(xmlPullParser.getAttributeValue("", "to"));
							message.setFrom(xmlPullParser.getAttributeValue("", "from"));
							message.setMsgtype(xmlPullParser.getAttributeValue("", "msgtype"));
							message.setMsgTime(xmlPullParser.getAttributeValue("", "msgTime"));
							message.setTitle(xmlPullParser.getAttributeValue("", "title"));
							message.setShiptype(xmlPullParser.getAttributeValue("", "shiptype"));
						}else if("body".equals(nodeName)){
							message.setBody(xmlPullParser.nextText());
						}else if ("thread".equals(nodeName)) {
							message.setThread(xmlPullParser.nextText());
						}
						break;
					//结束节点
					case XmlPullParser.END_TAG:
						if("message".equals(nodeName)){
							list.add(message);
							message=null;
						}
						break;
					default:
						break;
					}
					eventType=xmlPullParser.next();
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return list;
	}
	/**
	 * 追加到文件
	 * @param xml
	 * @param fileName
	 */
	public static void PullSaveMsgNum(String xml, String fileName,boolean append){
		if (xml != null) {
			FileOutputStream out;
			try {
				File file = new File(Constants.SD + fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				out = new FileOutputStream(file,append);
				
				out.write(xml.replace("\n", "\r\n").getBytes("utf-8"));
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
