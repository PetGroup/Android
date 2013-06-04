/**
 * 
 */
package com.ruyicai.jixuan;

import java.util.Vector;


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
	/**Ͷע��ʾ��ע���ʽ����������,����3d,�������������壬���ǲʣ�ʱʱ�ʻ�ѡ
	 */
	public String getShowZhuma(int index) {
		int num[] = getBalls(index);
	//	Log.v("===========","****************");
		String str = "";
		for (int i = 0; i < num.length; i++) {
			if (i != num.length - 1)
				str += (num[i] + 1) + ",";
			else
				str += (num[i] + 1);
		}
		return str;
	}
	/**Ͷע��ʾ��ע���ʽ����������,����ʱʱ�ʻ�ѡ
	 */
	public String getSpecialShowZhuma(int index) {
		int num[] = getBalls(index);
	//	Log.v("===========","****************");
		String str = "";
		for (int i = 0; i < num.length; i++) {
			if (i != num.length - 1)
				str += num[i] + ",";
			else
				str += num[i] ;
		}
		return str;
	}

}
