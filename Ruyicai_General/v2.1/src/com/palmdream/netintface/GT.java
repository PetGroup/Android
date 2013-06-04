package com.palmdream.netintface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;

import com.palmdream.RuyicaiAndroid.PublicMethod;



public class GT {
	static Random rnd = new Random();
    public static int getRandEx(int r1,int r2){
		
		  return  (rnd.nextInt()>>>1)%(r2-r1+1)+r1;			
	   }
	/**
	 * ��������
	 * @param t
	 * @return
	 */
	public static int[] sort(int t[]){
		int t_s[] = t;
		int t_a = 0;
		for (int i = 0; i < t_s.length; i++) {
			for (int j = i+1; j < t_s.length; j++) {
				if(t_s[i] >  t_s[j]){
					t_a = t_s[i];
					t_s[i] = t_s[j];
					t_s[j] = t_a;
				}
			}
		}
		return t_s;
	}
	
	/**
	 * ������ע��
	 * @param type  ����   0 ���� ˫ɫ��   1 ���� ����3D 2 ���� ���ֲ�
	 * @param iNote �������ע���ע��
	 * @return
	 */
	public static int[][] getBetCode(int type,int iNote){
		int betCode[][] = new int[1][7];
		
		switch(type){
		case 0://˫ɫ��
			betCode = new int[iNote][7];
			for(int i = 0; i < iNote; i++){
				 for(int j = 0; j < 6; j++){
					 betCode[i][j] = GT.getRandEx(1, 32);
					 for(int m = 0; m < j; m++){
						 if(betCode[i][j] == betCode[i][m]){
							j --;
							break;
						 }
					 }
				 }
				 betCode[i][6] = GT.getRandEx(1, 16);
				 betCode[i] = sort(betCode[i]);
			}
			break;
		case 1://����3D
			betCode = new int[iNote][3];
			for(int i = 0; i < iNote; i++){
				 for(int j = 0; j < 3; j++){
					 betCode[i][j] = GT.getRandEx(0, 9);
					 for(int m = 0; m < j; m++){
						 if(betCode[i][j] == betCode[i][m]){
							j --;
							break;
						 }
					 }
				 }
			}
			break;
		case 2://���ֲ�
			betCode = new int[iNote][7];
			for(int i = 0; i < iNote; i++){
				 for(int j = 0; j < 7; j++){
					 betCode[i][j] = GT.getRandEx(1, 30);
					 for(int m = 0; m < j; m++){
						 if(betCode[i][j] == betCode[i][m]){
							j --;
							break;
						 }
					 }
				 }
				 betCode[i] = sort(betCode[i]);
			}
			break;
		}
		
		
		return betCode;
	} 
	
	
	public static String strAreaCode = "1512";
	private static String strLotteryType = "F47104";
	
	/**
	 * ��Ͷע�������ַ���
	 * @param type  ��Ʊ����   0 ���� ˫ɫ��;   1 ���� ����3D; 2 ���� ���ֲ�
	 * @param maxNum ���Ͷע��
	 * @param playType ��Ʊ�淨
	 * @param mul ����Ͷע��ı���
	 * @param betCode ����Ͷע��
	 */
	public static String betCodeToString(int type,int maxNum,String playType,int mul,int[][] betCode){
		String codeTemp = "";
		String strBetCode = "";
		switch(type){
		case 0://˫ɫ��
			strLotteryType = "F47104";
			if(playType.equals("00")){
				for(int i = 0;i < betCode.length;i++){
					codeTemp += playType;
					
					if(mul < 10){
						codeTemp += "0" + mul;
					}else{
						codeTemp += mul;
					}
					
					for(int j = 0;j <6;j++){
						if( betCode[i][j] < 10){
							codeTemp += "0" + betCode[i][j];
						}else{
							codeTemp += betCode[i][j];
						}
					}
					codeTemp += "~";
					if( betCode[i][6] < 10){
						codeTemp += "0" + betCode[i][6];
					}else{
						codeTemp += betCode[i][6];
					}
					codeTemp += "^";
				}
			}
			
			break;
		case 1://����3D
			strLotteryType = "F47103";
			if(playType.equals("00")){
				for(int i = 0;i < betCode.length;i++){
					codeTemp += playType;
					
					if(mul < 10){
						codeTemp += "0" + mul;
					}else{
						codeTemp += mul;
					}
					
					for(int j = 0;j < 3;j++){
						if( betCode[i][j] < 10){
							codeTemp += "0" + betCode[i][j];
						}else{
							codeTemp += betCode[i][j];
						}
					}
					
					codeTemp += "^";
				}
			}
			break;
		case 2://���ֲ�
			strLotteryType = "F47102";
			if(playType.equals("00")){
				for(int i = 0;i < betCode.length;i++){
					codeTemp += playType;
					
					if(mul < 10){
						codeTemp += "0" + mul;
					}else{
						codeTemp += mul;
					}
					
					for(int j = 0;j < betCode[i].length;j++){
						if( betCode[i][j] < 10){
							codeTemp += "0" + betCode[i][j];
						}else{
							codeTemp += betCode[i][j];
						}
					}
					codeTemp +=  "^";
				}
			}
			
			break;
		}
		
		strBetCode = strAreaCode + "-" + strLotteryType + "-" + playType + "-";
		if(maxNum < 10){
			strBetCode += "0" + maxNum + "-";
		}else{
			strBetCode += maxNum +"-";
		}
		strBetCode += codeTemp;
		
		return strBetCode;
	}
	
	/**
	 * ��������ַ�ת����*
	 * @param str
	 * @return
	 */
	public static String returnPassword(String str){
		String pass = "";
		int length = str.length();
		StringBuffer login_Pas_num=new StringBuffer("");
		
		for(int i = 0; i < length; i++){
			pass = login_Pas_num.append('*').toString();
		}
		
		
		return pass;
		
	}
	
	public static String[] splitBetCode(String pns){
		PublicMethod.myOutput("?????????????????????????");
		String[] tmp = null;
		try{
		Vector vector = new Vector();
		int sIndex =0;
		int eIndex = 0;
		
		String tempS =null;
		boolean flag = false;
		for(int i=0;i<pns.length();i++){
			if(pns.charAt(i) =='^'){
				flag = true;
				eIndex = i;
				tempS = pns.substring(sIndex,eIndex);
				if(!tempS.equals("")){
					vector.addElement(tempS);
				}
				sIndex = eIndex+1;
			}
			if(flag)
			if(i == pns.length()-1){
				tempS = pns.substring(sIndex,i+1);
				if(!tempS.equals("")){
					vector.addElement(tempS);
				}
			}
		}
		if(!flag){
				tempS = pns;
				if(!tempS.equals("")){
					vector.addElement(tempS);
				}
			}
		tmp = new String[vector.size()];
		for (int i = 0; i < vector.size(); i++) {
			tmp[i] = (String)vector.elementAt(i);
		}
	
		}catch(Exception e){e.printStackTrace();}
		return tmp;
	}
	
	public static String[] splitBetCodeTC(String pns){
		PublicMethod.myOutput("?????????????????????????");
		String[] tmp = null;
		try{
		Vector vector = new Vector();
		int sIndex =0;
		int eIndex = 0;
		
		String tempS =null;
		boolean flag = false;
		for(int i=0;i<pns.length();i++){
			if(pns.charAt(i) ==';'){
				flag = true;
				eIndex = i;
				tempS = pns.substring(sIndex,eIndex);
				if(!tempS.equals("")){
					vector.addElement(tempS);
				}
				sIndex = eIndex+1;
			}
			if(flag)
			if(i == pns.length()-1){
				tempS = pns.substring(sIndex,i+1);
				if(!tempS.equals("")){
					vector.addElement(tempS);
				}
			}
		}
		if(!flag){
				tempS = pns;
				if(!tempS.equals("")){
					vector.addElement(tempS);
				}
			}
		tmp = new String[vector.size()];
		for (int i = 0; i < vector.size(); i++) {
			tmp[i] = (String)vector.elementAt(i);
		}
	
		}catch(Exception e){e.printStackTrace();}
		return tmp;
	}
	public static String makeString(String strLotteryType,int wayCode,String str){
		String tmp = "";
		if(strLotteryType.equals("F47104")){
			if(wayCode == 00){//��ʽ
				for(int i = 0; i < str.length(); ){
					if(str.charAt(i) == '~'){
						i++;
					}
					if(i < str.length() - 2){
						tmp += str.substring(i, i+2) + " ";
					}else if(i == str.length() - 2){
						tmp += " | " + str.substring(i, i+2);
					}
					PublicMethod.myOutput("--------temp"+tmp);
					i += 2;
				}
			}else if(wayCode == 40 || wayCode == 50){//����
				for(int i = 0; i < str.length(); ){
					if(i < str.length() - 2){
						tmp += str.substring(i, i+2) + ",";
					}else if(i == str.length() - 2){
						tmp += str.substring(i, i+2);
					}
					
					i += 2;
					
				}
			}else{
				for(int i = 0; i < str.length(); ){
					if(i < str.length() - 2){
						tmp += str.substring(i, i+2) + ",";
					}else if(i == str.length() - 2){
						tmp += str.substring(i, i+2);
					}
					
					i += 2;
					
				}
			}
			
			
			
		}else if(strLotteryType.equals("F47103")){
			
			for(int i = 0; i < str.length(); ){
				if(i < str.length() - 2){
					tmp += str.substring(i, i+2) + ",";
				}else if(i == str.length() - 2){
					tmp += str.substring(i, i+2);
				}
				
				i += 2;
				
			}
			
		}else if(strLotteryType.equals("F47102")){
			for(int i = 0; i < str.length(); ){
				if(i < str.length() - 2){
					tmp += str.substring(i, i+2) + ",";
				}else if(i == str.length() - 2){
					tmp += str.substring(i, i+2);
				}
				
				i += 2;
				
			}
		}else if(strLotteryType.equals("T01001")){
			int iStr=0;
			for(int i = 0; i < str.length();i++ ){
				if(str.charAt(i) == '-'){
					iStr=i;
				}
				
			}
			tmp=str.substring(0,iStr)+"|"+str.substring(iStr+1);
			System.out.println("-----tmp------"+tmp);
				
		}else if(strLotteryType.equals("T01002")){
			tmp=str.substring(2);
		}
		
		return tmp;
	}
	 public static String encodingString(String oldstring, String oldEncoding, String newEncoding) throws UnsupportedEncodingException{
		   
		 OutputStreamWriter outputStreamWriter = null;
	     PublicMethod.myOutput(oldstring);
	     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(oldstring.getBytes(oldEncoding));
	     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	     InputStreamReader inputStreamRead = null;
	     
	     char cbuf[] = new char[1024];
	     int  retVal = 0;
	     try {
	    	 inputStreamRead = new InputStreamReader(byteArrayInputStream , oldEncoding);
	    	 outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, newEncoding);
	    	 while((retVal = inputStreamRead.read(cbuf)) != -1){
	    		 outputStreamWriter.write(cbuf, 0, retVal);
	    	 }
	      
	
		  } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }finally{
			   try {
			    inputStreamRead.close();
			    outputStreamWriter.close();
			   } catch (Exception e) {
			    // TODO: handle exception
			   };
		  }
	
		  String temp = null;
		  try {
		   temp = new String(byteArrayOutputStream.toByteArray(), newEncoding);
		  } catch (UnsupportedEncodingException e) {
		   e.printStackTrace();
		  }
		  PublicMethod.myOutput("temp"+temp);
		     return temp;
		  }
		
	 /**
	  * ����������������͸����ѡ��Ͷע��ʽ
	  * @param type
	  * @param betCode
	  * @return
	  */
		public static String betCodeToStringTC(int type,int[][] betCode){
			String strBetCode = "";
			switch(type){
			case 3://������
				   
					for(int i = 0;i < betCode.length-1;i++){
						strBetCode+="1|";
						for(int j = 0;j < betCode[i].length-1;j++){
							strBetCode+=betCode[i][j]+",";
						}
						strBetCode+=betCode[i][betCode[i].length-1];
						strBetCode+=";";
					}
					strBetCode+="1|";
					for(int j = 0;j < betCode[betCode.length-1].length-1;j++){
						strBetCode+=betCode[betCode.length-1][j]+",";
				   }
				   strBetCode+=betCode[betCode.length-1][betCode[betCode.length-1].length-1];
				break;
		case 4://����͸
				for(int i = 0;i < betCode.length-1;i++){
					for(int j = 0;j < betCode[i].length-3;j++){
						if(betCode[i][j]<10){
							strBetCode+="0"+betCode[i][j]+" ";
						}else{
						strBetCode+=betCode[i][j]+" ";
						}
					}
					if(betCode[i][betCode[i].length-3]<10){
						strBetCode+="0"+betCode[i][betCode[i].length-3]+"-";
					}else{
					   strBetCode+=betCode[i][betCode[i].length-3]+"-";
					}
					if(betCode[i][betCode[i].length-2]<10){
						strBetCode+="0"+betCode[i][betCode[i].length-2]+" ";
					}else{
					strBetCode+=betCode[i][betCode[i].length-2]+" ";
					}
					if(betCode[i][betCode[i].length-1]<10){
						strBetCode+="0"+betCode[i][betCode[i].length-1];
					}else{
					strBetCode+=betCode[i][betCode[i].length-1];
					}
					strBetCode+=";";
			   }
				for(int j = 0;j < betCode[betCode.length-1].length-3;j++){
					if(betCode[betCode.length-1][j]<10){
						strBetCode+="0"+betCode[betCode.length-1][j]+" ";
					}else{
					strBetCode+=betCode[betCode.length-1][j]+" ";
					}
				}
				if(betCode[betCode.length-1][betCode[betCode.length-1].length-3]<10){
					strBetCode+="0"+betCode[betCode.length-1][betCode[betCode.length-1].length-3]+"-";
				}else{
				  strBetCode+=betCode[betCode.length-1][betCode[betCode.length-1].length-3]+"-";
				}
				if(betCode[betCode.length-1][betCode[betCode.length-1].length-2]<10){
					strBetCode+="0"+betCode[betCode.length-1][betCode[betCode.length-1].length-2]+" ";
				}else{
				strBetCode+=betCode[betCode.length-1][betCode[betCode.length-1].length-2]+" ";
				}
				if(betCode[betCode.length-1][betCode[betCode.length-1].length-1]<10){
					strBetCode+="0"+betCode[betCode.length-1][betCode[betCode.length-1].length-1];
				}else{
			        strBetCode+=betCode[betCode.length-1][betCode[betCode.length-1].length-1];
				}
			break;
		}
			return strBetCode;
		}

}

