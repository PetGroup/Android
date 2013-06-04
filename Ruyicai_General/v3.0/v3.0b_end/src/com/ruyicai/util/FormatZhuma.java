package com.ruyicai.util;
/**
 * ����Ĺ��������������֪ͨ����Ӧ���ֵ�ע���ʽ
 * @author miao
 *
 */
public class FormatZhuma {
	
	
	public static String  formatZhuma(int i,String betCode){
		switch (i) {
		case 0://˫ɫ��
			return formatSSQAndQLCZhuma(betCode);
		case 1://����3d
			return formatFC3DZhuma(betCode);
		case 2://���ֲ�
			return formatSSQAndQLCZhuma(betCode);
		case 3://����͸
			return formatDltZhuma(betCode);
		case 4://���ǲ�
			return formatTCZhuma(betCode);
		case 5://������
			return formatTCZhuma(betCode);
		case 6://������
			return formatTCZhuma(betCode);
		case 7://22ѡ5
			return format225Zhuma( betCode);
		}
		return "";
	}
	/**
	 * �������ʽ��˫ɫ������ֲʵ�ע��
	 * @param betCode
	 * @return
	 */
	private static String  formatSSQAndQLCZhuma(String betCode){
		
		StringBuffer formatCode = new StringBuffer();
		for(int j = 0;j<betCode.length()/2;j++){
			
		    if(j<betCode.length()/2-2){
		    	formatCode.append(betCode.substring(2*j, 2*j+2)).append(",");
		    }else if(j==betCode.length()/2-2){
		    	formatCode.append(betCode.substring(2*j, 2*j+2)).append("|");
		    }else{
		    	formatCode.append(betCode.substring(2*j, 2*j+2));
		    }
			
		}
		return formatCode.toString();
	}
	/**
	 * �������ʽ������͸��ע��
	 * @param betCode
	 * @return
	 */
	private static String formatDltZhuma(String betCode){
		String bbbbbb =  betCode.replace(" ", ",");
		String c = bbbbbb.replace("+", "|");
		return c;
	}
	/**
	 * �������ʽ���������������壬���ǲ�
	 * @param betCode
	 * @return
	 */
	private static String formatTCZhuma(String betCode){
		StringBuffer formatCode = new StringBuffer();
		for(int j = 0;j<betCode.length();j++){
			
		    if(j<betCode.length()-1){
		    	formatCode.append(betCode.substring(j, j+1)).append(",");
		    }else{
		    	formatCode.append(betCode.substring(j, j+1));
		    }
			
		}
		return formatCode.toString();
	}
	/**
	 * �������ʽ��22ѡ5��ע��
	 * @param betCode
	 * @return
	 */
	private static String format225Zhuma(String betCode){
		StringBuffer formatCode = new StringBuffer();
		for(int j = 0;j<betCode.length()/2;j++){
			
		    if(j<betCode.length()/2-1){
		    	formatCode.append(betCode.substring(2*j, 2*j+2)).append(",");
		    }else{
		    	formatCode.append(betCode.substring(2*j, 2*j+2));
		    }
			
		}
		return formatCode.toString();
	}
	/**
	 * �������ʽ������3D��ע��
	 * @param betCode
	 * @return
	 */
	private static String formatFC3DZhuma(String betCode){
		StringBuffer formatCode = new StringBuffer();
		for(int j = 0;j<betCode.length()/2;j++){
			
		    if(j<betCode.length()/2-1){
		    	formatCode.append(betCode.substring(2*j+1, 2*j+2)).append(",");
		    }else{
		    	formatCode.append(betCode.substring(2*j+1, 2*j+2));
		    }
			
		}
		return formatCode.toString();
	}

}
