package com.ruyicai.util;

import java.text.NumberFormat;

public class StringUtils {
	public static String [][] getImgIdArrByString(String str){
		if(str.endsWith(",")){
			str = str.substring(0, str.length()-1);
		}
		String  [] arrEveryImg  = str.split(",");
		int length = arrEveryImg.length;
		String [] [] arrImgs = new String [length][];
		String [] arrSmallImg = new String [length];
		String [] arrBigImg = new String [length];
		for(int i = 0 ; i < length; i++){
			arrSmallImg[i] = arrEveryImg[i].split("_")[0];
			arrBigImg[i] = arrEveryImg[i].split("_")[1];
		}
		arrImgs[0] = arrSmallImg;
		arrImgs[1] = arrBigImg;
		return arrImgs;
	}
    public static String  getSendtoMinllon(String millon, int num) {
    	try {
        Double tmpmillon = Double.valueOf(millon)*1000;
        NumberFormat mf = NumberFormat.getInstance();
        mf.setGroupingUsed(false);
        return mf.format(tmpmillon).toString().substring(0, num);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "";
    }
 
    /**
     * 判断空
     * @param str
     * @return
     */
    public static boolean isNotEmty(String str){
    	if(str!=null&&!"".equals(str)){
    		return true;
    	}else {
			return false;
		}
    }
}
