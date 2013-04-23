package com.palmdream.netintface;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ParseXML {

	/**
	 * 
	 * @���ߣ�����
	 * @���ڣ�2011/3/10
	 * @������url
	 * @����ֵ���汾��
	 * @��;�������������ϱ���������µİ汾��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��1.0
	 */
	public static String parseVersionXml(String url) {
		
		String version = "";
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
		try {
			URL u = new URL(url);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(u.openStream());
			NodeList nodeList = document.getElementsByTagName("MODEL");
			for (int i = 0; i < nodeList.getLength(); i ++) {
				if (document.getElementsByTagName("OS").item(i).getFirstChild() != null) {
					String versionVal = document.getElementsByTagName("CURRENT_SOFT_VERSION").item(i).getFirstChild().getNodeValue();
					version += "," + versionVal;				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(version);
		String [] versions = version.split(",");
		return versions[1];
	}
	
}
