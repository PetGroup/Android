/**
 * 
 */
package com.ruyicai.jixuan;

import java.util.Vector;

import android.util.Log;

import com.ruyicai.util.PublicMethod;

/**
 * ��ѡС��ĳ�����
 * 
 * @author Administrator
 * 
 */
public abstract class Balls {
	
	 Vector num = new Vector<int[]>();//ע������
	 Vector colors = new Vector<String>();//��ɫ����Ĭ��Ϊ��ɫ

	/**
	 * ��ֵ
	 * 
	 */
	public abstract void init();


    /**
     * ��������
     * @return
     */
	public abstract Balls createBalls();
	/**
	 * ���Ͷעע��
	 * @param balls
	 * @param beishu
	 * @return
	 */
	public abstract String getZhuma(Vector<Balls> balls,int beishu);
	
    /**
     * ��������
     * @param index
     * @return
     */
	public int[] getBalls(int index) {
		return (int[]) num.get(index);

	}
   /**
    * ������������
    * @return
    */
    public Vector<int[]> getVZhuma(){
		return num;
    }
    /**
     * ������������
     * @return
     */
    public Vector<String> getVColor(){
		return colors;
    }
    public void add(int[] num,String color ){
    	this.num.add(num);
    	if(color==null){
    		this.colors.add("red");
    	}else{
    		this.colors.add(color);
    	}
    
    }
    /**ע���ʽ�������,���ڴ���͸��˫ɫ�����ֲʻ�ѡ
	 */
	public String getZhuma(int index) {
		int num[] = getBalls(index);
		String str = "";
		for (int i = 0; i < num.length; i++) {
			if (i != num.length - 1)
				str += PublicMethod.getZhuMa(num[i] + 1) + ",";
			else
				str += PublicMethod.getZhuMa(num[i] + 1);
		}
		return str;

	}
	/**Ͷע��ʾ��ע���ʽ�������
	 */
	public String getTenShowZhuma(int index) {
		int num[] = getBalls(index);
		String str = "";
		for (int i = 0; i < num.length; i++) {
			str += PublicMethod.isTen(num[i]);
			if (i != num.length - 1)
				str += ",";
		}
		return str;
	}
	/**Ͷע��ʾ��ע���ʽ����������
	 */
	public String getShowZhuma(int index) {
		int num[] = getBalls(index);
		String str = "";
		for (int i = 0; i < num.length; i++) {
			str += (num[i])+"";
			if (i != num.length - 1)
				str += ",";
		}
		return str;
	}
	/**Ͷע��ʾ��ע���ʽ����������,����ʱʱ�ʻ�ѡ
	 */
	public String getSpecialShowZhuma(int index) {
		int num[] = getBalls(index);
		String str = "";
		for (int i = 0; i < num.length; i++) {
			str += num[i];
			if (i != num.length - 1)
				str += ",";
		}
		return str;
	}
	/**Ͷע��ʾ��ע���ʽ�������,����11-5��ѡ�ʻ�ѡ
	 */
	public String getTenSpecialShowZhuma(int index) {
		int num[] = getBalls(index);
		String str = "";
		for (int i = 0; i < num.length; i++) {
			str += PublicMethod.getZhuMa(num[i]);
			if (i != num.length - 1)
				str += ",";
		}
		return str;
	}

}
