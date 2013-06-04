package com.ruyicai.util.wap;

import com.ruyicai.util.PublicMethod;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class NetworkUtils{

	//cmwapת��cmnet
	public static boolean cmwapToCmnet(Context context)
	        {
	            
	            return new UpdateAPN(context).updateApn();
	        }
	//�Ƿ���cmwap��������

	public static boolean isCmwap(Context context) {
	                
	                if (context == null) {
	                        return false;
	                }
	                ConnectivityManager cm = (ConnectivityManager) context
	                                .getSystemService(Context.CONNECTIVITY_SERVICE);
	                if (cm == null) {
	                        return false;
	                }
	                NetworkInfo info = cm.getActiveNetworkInfo();
	                if (info == null) {
	                        return false;
	                }
	                String extraInfo = info.getExtraInfo();
//	                Constant.debug("extraInfo ---> " + extraInfo);
	//�����࣬�ж��Ƿ�Ϊ�ռ�null
	                if (TextUtils.isEmpty(extraInfo) || (extraInfo.length() < 3)) {
	                        return false;
	                }
	                if (extraInfo.toLowerCase().indexOf("wap") > 0) {
	                        return true;
	                }
	                // return extraInfo.regionMatches(true, extraInfo.length() - 3, "wap",
	                // 0, 3);
	                return false;
	        }
	/**
	     * �Ƿ���cmnet��������
	     * 
	     * @return ture�ǣ�false��
	     */
	    public static boolean isCmnet(Context context)
	    {
	      
	        ConnectivityManager cm = (ConnectivityManager) context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);
	        if (cm == null) {
	            return false;
	        }
	        NetworkInfo info = cm.getActiveNetworkInfo();
	        if (info == null) {
	            return false;
	        }
	        String extraInfo = info.getExtraInfo();
	        if (TextUtils.isEmpty(extraInfo) || (extraInfo.length() < 3)) {
	            return false;
	        }
	        if (extraInfo.toLowerCase().indexOf("net") > 0) {
	            return true;
	        }
	        return false;
	    }
		public static boolean isNetworkAvailable(Context context) {
			boolean isOk = false;
			long startTime = System.currentTimeMillis();
	        int count = 0;
	        while (!NetworkUtils.isCmnet(context))
	        {
	            // cmwap�л���cmnet��Ҫ���4���ʱ�䣬ֻ���л���ȥ���Ž���
	            if (count >= 10)
	            {
	                break;
	            }
	            try
	            {
	                Thread.sleep(1000);
	            }
	            catch (Exception e)
	            {
	            }
	            count++;
	        }
	        long endTime = System.currentTimeMillis();
	        PublicMethod.myOutLog("wap====",("�л��������л�����ʱ��Ϊ��" + ((endTime - startTime) / 1000.0) + "���л�ѭ���������������10����û���л��ɹ�����" + count));
	        if (NetworkUtils.isCmnet(context))
	        {
	        	PublicMethod.myOutLog("wap====",("�л��������������ӷ�ʽΪcmnet"));
	        	isOk = true;
	        }
	        return isOk;
		}
}
