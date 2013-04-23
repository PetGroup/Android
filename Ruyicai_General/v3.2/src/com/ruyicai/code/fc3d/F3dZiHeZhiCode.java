/**
 * 
 */
package com.ruyicai.code.fc3d;

import com.ruyicai.activity.buy.fc3d.Fc3d;
import com.ruyicai.code.CodeInterface;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.PublicConst;

/**
 * @author Administrator
 * 
 */
public class F3dZiHeZhiCode extends CodeInterface {
	String CityCode = "1512-";// ���б��
	String DDD_falg = "F47103-";// ���ֱ��
	String typeCode = "";// �淨
	String staticCode = "-01-";
	String endCode = "^";// ������־
	String dateCode = "0";// ����


	String zxHHH = "10";// ֱ��ֵ
	String z3HHH = "11";// ��3ֵ
	String z6HHH = "12";// ��6ֵ
	private int iCurrentButton;

	public int getiCurrentButton() {
		return iCurrentButton;
	}
	public void setiCurrentButton(int iCurrentButton) {
		this.iCurrentButton = iCurrentButton;
	}
	public String zhuma(AreaNum[] areaNums, int beishu,int type) {

		String[] zhuma = null;
		String t_str = "";
		int iZhuShu = 0;
		// �ж��Ǻ�ֱֵѡ ���Ǻ�ֵ��3 ���Ǻ�ֵ��6
		if (iCurrentButton == PublicConst.BUY_FC3D_HEZHI_ZHIXUAN) {
			typeCode = zxHHH;
			 iZhuShu =getFc3dZhixuanHezhiZhushu(areaNums[0].table);
			// �ж���һλ��������λ �³� 20100714
			int[] zhuma_zhixuanhezhi = areaNums[0].table.getHighlightBallNOs();
			if (zhuma_zhixuanhezhi[0]  < 10) {
				String[] str = { "0" + (zhuma_zhixuanhezhi[0]) };
				zhuma = str;
			} else {
				String[] str = { "" + (zhuma_zhixuanhezhi[0]) };

				zhuma = str;
			}
		} else if (iCurrentButton == PublicConst.BUY_FC3D_HEZHI_ZU3) {
			typeCode = z3HHH;
			 iZhuShu =getFc3dZu3HezhiZhushu(areaNums[0].table);
			int[] zhuma_zu3hezhi = areaNums[0].table.getHighlightBallNOs();
			// �ж���һλ��������λ �³� 20100714
			if (zhuma_zu3hezhi[0] < 10) {
				String[] str = { "0" + zhuma_zu3hezhi[0] };
				zhuma = str;
			} else {
				String[] str = { "" + zhuma_zu3hezhi[0] };
				zhuma = str;
			}
		} else if (iCurrentButton == PublicConst.BUY_FC3D_HEZHI_ZU6) {
			typeCode = z6HHH;
			 iZhuShu =getFc3dZu6HezhiZhushu(areaNums[0].table);
			int[] zhuma_zu6hezhi = areaNums[0].table.getHighlightBallNOs();
			// �ж���һλ��������λ �³� 20100714
			if (zhuma_zu6hezhi[0]  < 10) {
				String[] str = { "0" + (zhuma_zu6hezhi[0]) };
				zhuma = str;
			} else {
				String[] str = { "" + (zhuma_zu6hezhi[0]) };
				zhuma = str;
			}	
		}
		// ����ע�� 2010/7/11/ �³�
		String zhushu = "";
		if (iZhuShu < 10) {
			zhushu += "0" + iZhuShu;
		} else if (iZhuShu >= 10) {
			zhushu += "" + iZhuShu;
		}
		String beishu_ = "01";
//		if (beishu < 10) {
//			beishu_ += "0" + beishu;
//		} else if (beishu >= 10) {
//			beishu_ += "" + beishu;
//		}
		t_str += typeCode + beishu_;
		for (int i = 0; i < zhuma.length; i++) {
			t_str += zhuma[i];
		}
		t_str += endCode;
		return t_str;
	}
	/**
	 * ��ø���3Dֱѡ��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getFc3dZhixuanHezhiZhushu(BallTable table) {
		int iZhuShu = 0;
		int[] BallNos = table.getHighlightBallNOs();// ��ѡ��С��ĺ��루���1�����1��
		int[] BallNoZhushus = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69,
				73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1 };// 0~27



	


		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i]) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С������1��ʼ���ʼ�ȥ1
					// ɾ������ cc 20100713
					iZhuShu = BallNoZhushus[j];// *iProgressBeishu;
					// String temp = "��ǰ�淨Ϊ��ֱֵѡ����"
					// +(iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ");
					// mTextSumMoney.setText(temp);
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * ��ø���3D��3��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getFc3dZu3HezhiZhushu(BallTable table) {
		int iZhuShu = 0;
		int[] BallNos = table.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����3��
		int[] BallNoZhushus = { 1, 2, 1, 3, 3, 3, 4, 5, 4, 5, 5, 4, 5, 5, 4, 5,
				5, 4, 5, 4, 3, 3, 3, 1, 2, 1 };// 1~26

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
					// ɾ������ cc 20100713
					iZhuShu = BallNoZhushus[j];// *iProgressBeishu;
					// String temp = "��ǰ�淨Ϊ��ֵ��3����"
					// +(iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ");
					// mTextSumMoney.setText(temp);
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * ��ø���3D��6��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getFc3dZu6HezhiZhushu(BallTable table) {
		int iZhuShu = 0;
		int[] BallNos = table.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����3��
		int[] BallNoZhushus = { 1, 1, 2, 3, 4, 5, 7, 8, 9, 10, 10, 10, 10, 9,
				8, 7, 5, 4, 3, 2, 1, 1 };// 3~24

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 3) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
					// ɾ������ cc 20100713
					iZhuShu = BallNoZhushus[j];// *iProgressBeishu;
					// String temp = "��ǰ�淨Ϊ��ֵ��6����"
					// +(iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ");
					// mTextSumMoney.setText(temp);
				}
			}
		}
		return iZhuShu;
	}

}
