package com.ruyicai.code.jc.lq;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.code.jc.JcType;
import com.ruyicai.util.PublicMethod;

public class BasketSFC {
	private static final String BFC_01 ="01";
	private static final String BFC_02 ="02";
	private static final String BFC_03 ="03";
	private static final String BFC_04 ="04";
	private static final String BFC_05 ="05";
	private static final String BFC_06 ="06";
	private static final String BFC_11 ="11";
	private static final String BFC_12 ="12";
	private static final String BFC_13 ="13";
	private static final String BFC_14 ="14";
	private static final String BFC_15 ="15";
	private static final String BFC_16 ="16";
	public static String bqcType[] = {BFC_01,BFC_02,BFC_03,BFC_04,BFC_05,BFC_06,BFC_11,BFC_12,BFC_13,BFC_14,BFC_15,BFC_16};
	public static String titleStrs[] = {"��ʤ1-5��","��ʤ6-10��","��ʤ11-15��","��ʤ16-20��","��ʤ21-25��","��ʤ26������",
		                                 "����1-5��","����6-10��","����11-15��","����16-20��","����21-25��","����26������"}; 
	JcType jcType;
    public BasketSFC(Context context){
    	jcType = new JcType(context);
    }
	 /**
     * 
     * ��ȡע��
     * 
     */
    public String getCode(String key,List<Info> listInfo){
    	String code = jcType.getValues(key)+"@";
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);
			if (info.onclikNum>0) {
             code +=info.getDay()+"|"+info.getWeek()+"|"+info.getTeamId()+"|";		
         	for(int j=0;j<info.check.length;j++){
				if(info.check[j].isChecked()){
	            	 code+= bqcType[j];
				}
			}
            	 code+="^";
			 }
			
		}
		return code;
    }

	/**
	 * ��ȡ����List
	 */

	public List<double[]> getOddsList(List<Info> listInfo) {
		List<double[]> oddsList = new ArrayList<double[]>();/* ����ѡ���������б� */
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);
			if (info.onclikNum > 0) {
				try {
					double[] aa = new double[info.check.length];
					for (int j = 0; j < info.check.length; j++) {
						if (info.check[j].isChecked()) {
							aa[j] = Double.parseDouble(info.getVStrs()[j]);
						}
					}
					double[] insertdouble = PublicMethod.getDoubleArrayNoZero(aa);
					oddsList.add(insertdouble);
				} catch (NumberFormatException e) {
					double[] result = { 1 };
					oddsList.add(result);
				}
			}

		}
		return oddsList;
	}
}
