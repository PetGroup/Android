/**
 * 
 */
package com.ruyicai.interfaces;

import com.ruyicai.pojo.AreaNum;

/**
 * ����Ҫʵ�ֵķ���
 * @author Administrator
 *
 */
public interface BuyImplement {
	  public static final String type="";
	 /**
	    * ���С����ʾ���
	    * @param areaNum
	    * @param iProgressBeishu
	    * @return
	    */
	   public String textSumMoney(AreaNum areaNum[],int iProgressBeishu);
	   /**
	    * �ж��Ƿ�����Ͷע����
	    */
	   public void isTouzhu();
	   /**
	    * Ͷע����
	    */
	   public void touzhuNet();
}
