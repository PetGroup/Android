package com.ruyicai.code.jc.lq;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.code.jc.JcType;
import com.ruyicai.util.PublicMethod;
/**
 * ��С��
 * @author Administrator
 *
 */
public class BasketDX {
	JcType jcType;
    public BasketDX(Context context){
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
             if(info.isWin()){
            	 code+="2";//С
             }
             if(info.isFail()){
            	 code+="1";//��
             }
            	 code+="^";
			 }
			
		}
		return code;
    }
    /**
     * ��ȡ����List
     */
    
    public List<double[]> getOddsList(List<Info> listInfo){
    	 List<double[]> oddsList = new ArrayList<double[]>() ;/*����ѡ���������б�*/
    	 for (int i = 0; i < listInfo.size(); i++) {
 			Info info = (Info) listInfo.get(i);
 			if (info.onclikNum>0) {
 				try{
 					  double[] aa = new double[2];
 		              if(info.isWin()){
 		            	  aa[0] = Float.parseFloat(info.getSmall());
 		              }
 		              if(info.isFail()){	
 		            	  aa[1] = Float.parseFloat(info.getBig());
 		              }
 		              double[] insertdouble = PublicMethod.getDoubleArrayNoZero(aa);
 		              oddsList.add(insertdouble);
 				}catch(NumberFormatException e){
 					 double[] insertdouble = {1};
 					 oddsList.add(insertdouble);
 				}
 			
 			 }
 			
 		}
    	 return oddsList;
    }
}
