package com.ruyicai.util;

import java.util.Random;

import android.R.integer;
import android.util.Log;


public class RandomMessage {
	
	/**
	 * �����ȡ�ȴ���ʾ��
	 * @return
	 */
	public String getWaitingMessage(){
		Random r = new Random();
		String numTemp = String.valueOf(r.nextFloat());
		int num = Integer.parseInt(numTemp.substring(5,6));
		if(num==0){
			return "���һ�,�ҹ�ȥ��Ү...";
		}else if(num==1){
			return "�������Ų���,������������,�ȴ�����������...";
		}else if(num==2){
			return "ʹ�ÿͻ��˻����Ը����ĺ������Ͳ�Ʊ...";
		}else if(num==3){
			return "�ǵ���,���ǵ���,���ǵ���...";
		}else if(num==4){
			return "�д󽱺���԰Ѳ��ֽ����������Ҫ����...";
		}else if(num==5){
			return "������������������н�����...";
		}else if(num==6){
			return "�о�һ�²�Ʊ��Ѷ,�����о�ϲŶ...";
		}else if(num==7){
			return "91��Ʊ�����д�...500��Ӵ˲�����...";
		}else if(num==8){
			return "91��Ʊ�������еĹ���ר��...";
		}else if(num==9){
			return "91��Ʊ�����д�...500��Ӵ˲�����...";
		}else if(num==10){
			return "91��Ʊ�����д�...500��Ӵ˲�����...";
		}
		
		return "";
	}

	
}
