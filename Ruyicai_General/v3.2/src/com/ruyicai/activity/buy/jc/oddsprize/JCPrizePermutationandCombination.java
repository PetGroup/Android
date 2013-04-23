package com.ruyicai.activity.buy.jc.oddsprize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruyicai.util.PublicMethod;

import android.util.Log;

/**
 * �����㽱�����������
 * @author win
 *
 */
public class JCPrizePermutationandCombination {
	
	
	/**
	 * ����һ������ÿ����������ѡ��������������ʵ�����
	 * @param list ����ÿ��������ѡ���ʵ�list�б�
	 * @return
	 */
    private static	double[] getEachGameMaxOdds(List<double[]> list){
    	double[] maxArrays = new double[list.size()];
    	for(int i = 0;i<list.size();i++){
			double[] sonDouble = list.get(i);
			Arrays.sort(sonDouble);
			maxArrays[i]= sonDouble[sonDouble.length-1];
		}
    	
		return maxArrays;
	}
    /**
     * ��ȡ��С�н����
     * @param list ����ÿ�����������������ʵ�list�б�
     * @param num  ��С�н����н�����
     * @return ��С�н����
     */
    public static double FreedomGuoGuanMixPrize(List<double[]> list,int num){
    	double mixPrize = 1;
    	double[] mixArrays = new double[list.size()];
    	for(int i = 0;i<list.size();i++){
			double[] sonDouble = list.get(i);
			Arrays.sort(sonDouble);
			mixArrays[i] = sonDouble[0]; 
		}
    	Arrays.sort(mixArrays);
    	for(int i = 0;i<num;i++){
    		mixPrize *= mixArrays[i];
    	}
    	return mixPrize*2;//2Ϊÿע��Ԫ
    }
    /**
     * ��ȡ�മ��С�н����
     * @param list ����ÿ�����������������ʵ�list�б�
     * @param num  ��С�н����н�����
     * @return ��С�н����
     */
    public static double getDuoMixPrize(int teamNum,List<double[]> list,int num){
    	double mixPrize = 1;
    	double[] mixArrays = new double[list.size()];
    	for(int i = 0;i<list.size();i++){
			double[] sonDouble = list.get(i);
			Arrays.sort(sonDouble);
			mixArrays[i] = sonDouble[0]; 
		}
    	Arrays.sort(mixArrays);
    	double[] mixs = new double[num];
    	for(int i = 0;i<num;i++){
    		mixs[i] = i;
    		mixPrize *= mixArrays[i];
    	}
    	return mixPrize*2*getDouMixNum(teamNum,mixArrays,mixs);//2Ϊÿע��Ԫ
    }
    /**
     * ���ؾ��ʶമ������С����
     * �����������ֳɼ���
     * odds���г��ε�����
     * mix ��С����������
     */
	public  static double getDouMixNum(int teamNum,double[] odds,double[] mixs) {
		// ��ʼ��ԭʼ����
		int[] a = new int[odds.length];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		// ��������
		int[] b = new int[teamNum];

		List<int[]> list = new ArrayList<int[]>();

		// �������
		PublicMethod.combine(a, a.length, teamNum, b, teamNum, list);
		int resultInt = 0; 
		for (int[] result : list) {
			int num =0;
			for (int n=0;n<result.length;n++) {
				int index = result[n];
				for(double mix:mixs){
					if(index == mix){
						num++;
						break;
					}
				}
			}
			if(num==mixs.length){
				resultInt++;
			}
		}
		return resultInt;
	}
	/**
	 * ���ʵ���Ͷע�������
	 * @param list
	 * @param muti
	 * @return
	 */
	public static String getDanGuanPrize(List<double[]> list,int muti){
		double[] mixArrays = new double[list.size()];
		double maxPrize = 0;
		for(int i = 0;i<list.size();i++){
			double[] sonDouble = list.get(i);
			Arrays.sort(sonDouble);
			Log.v("mixvalue",""+sonDouble[0]);
			mixArrays[i] = sonDouble[0];
			maxPrize += sonDouble[sonDouble.length-1];
		}
		Arrays.sort(mixArrays);
		StringBuffer aa = new StringBuffer();
		String mixValue = PublicMethod.formatStringToTwoPoint(mixArrays[0]*2* muti);
		String maxValue = PublicMethod.formatStringToTwoPoint(maxPrize*2* muti);
		aa.append("Ԥ���н���").append(mixValue).append("Ԫ").append("~").append(maxValue).append("Ԫ");
		return aa+"";
	}
	
	/**
	 * ��ȡ���ɹ��ص������
	 * @param list �����б�
	 * @param k  k��1
	 * @return
	 */
	public static double getFreedomGuoGuanMaxPrize(List<double[]> list,int k){
		double[] odds = getEachGameMaxOdds(list);
		return select(odds,k)*2;
	}
	/**
	 * ��ȡ���ɹ��ص������
	 * @param list �����б�
	 * @param k  k��1
	 * @return
	 */
	public static double getDuoMaxPrize(int teamNum,List<double[]> list,int k){
		double[] odds = getEachGameMaxOdds(list);
		return getDouMaxAmt(teamNum,odds,k)*2;
	}
    /**
     * ���ؾ��ʶമ���������
     * @param teamNum �മ����3*3 teamNum = 3
     * @param select 2*1 select=2
     * @return
     * �����������ֳɼ���
     */
	public  static double getDouMaxAmt(int teamNum,double[] odds, int select) {
		// ��ʼ��ԭʼ����
		int[] a = new int[odds.length];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		// ��������
		int[] b = new int[teamNum];

		List<int[]> list = new ArrayList<int[]>();

		// �������
		PublicMethod.combine(a, a.length, teamNum, b, teamNum, list);
		double resultInt = 0; 
		for (int[] result : list) {
			double[] betcode = new double[result.length];
			for (int n=0;n<result.length;n++) {
				betcode[n] = odds[result[n]];
			}
			resultInt += select(betcode,select);
		}
		return resultInt;
	}
	
	private static  double select(double[] odds,int k) {
		double prizeAmt = 0;
		double[] hascombine = new double[k];
		List<Double> prizeList1 = new ArrayList<Double>();
		List<Double> prizeList  = subselect(0, 1, hascombine, k,odds,prizeList1);
		for(int i=0;i<prizeList.size();i++){
			prizeAmt += prizeList.get(i);
		}
		return prizeAmt;
	}
	private static List<Double> subselect(int head, int index, double[] r, int k, double[] odds,List<Double> prizeList) {
		
	
		for (int i = head; i < odds.length + index - k; i++) {
			if (index < k) {
				r[index - 1] = odds[i];
				subselect(i + 1, index + 1, r, k,odds,prizeList);
			} else if (index == k) {
				r[index - 1] = odds[i];
				double eachZhuPrize = 1;//ÿע��Ʊ���н����{���˵Ļ�}
				for(int j=0;j<r.length;j++){
					eachZhuPrize *= r[j];
				}
				prizeList.add(eachZhuPrize);
				subselect(i + 1, index + 1, r, k,odds,prizeList);
			} 

		}
		return prizeList;
	}

}
