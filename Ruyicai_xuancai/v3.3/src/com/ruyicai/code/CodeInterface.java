/**
 * 
 */
package com.ruyicai.code;

import com.ruyicai.pojo.AreaNum;


/**
 * ����ע�������
 * @author Administrator
 *
 */
public abstract class CodeInterface {
    private static boolean isZHmiss = false;   
    private static String isZHcode;
	public abstract String zhuma(AreaNum areaNums[],int beishu,int type);
	
	public static String getIsZHcode() {
		return isZHcode;
	}

	public void setIsZHcode(String isZHcode) {
		this.isZHcode = isZHcode;
	}
	public static boolean isZHmiss() {
		return isZHmiss;
	}

	public void setZHmiss(boolean isZHmiss) {
		this.isZHmiss = isZHmiss;
	}
	/**
	 * ����С��10��ע��֮ǰ��0�ķ���
	 * @param code ֱ�ӻ�ȡ��С���ϵ���
	 * @return
	 */
	public static StringBuffer formatInteger(int code){
		StringBuffer formatCode = new StringBuffer();
			if(code<10){
				formatCode.append(0).append(code);
			}else{
				formatCode.append(code);
			}
			
		return formatCode;		
	}
	
   public static int[] toInt(String[] codeStrs){
	   int[] codeInts = new int[codeStrs.length];
	   for(int i=0;i<codeStrs.length;i++){
		   codeInts[i] = Integer.parseInt(codeStrs[i]);
	   }
	   return codeInts;
   }


}
