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
   

	public abstract String zhuma(AreaNum areaNums[],int beishu,int type);

//	public abstract String zhuma(AreaNum areaNums[],int beishu);
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


}
