/**
 * 
 */
package com.ruyicai.jixuan;

import java.util.Vector;

import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 *
 */
public class GdelevenQxBalls  extends Balls{
    int i=0;
	public GdelevenQxBalls(int i){
		this.i=i;
		init();
	}
	@Override
	public Balls createBalls() {
		// TODO Auto-generated method stub
		return new DlcQxBalls(i);
	}

	@Override
	public void init() {
		int onenum[]= new int[i];
		// TODO Auto-generated method stub
		for(int j=0;j<i;j++){
			onenum = PublicMethod.getRandomsWithoutCollision(i, 1, 11);
	    }
		add(onenum,null);

}
	
	public String getZhuma(int index) {
		int num[] = getBalls(index);
		String str = "";
		for (int i = 0; i < num.length; i++) {
			if (i != num.length - 1)
				str += PublicMethod.getZhuMa(num[i] ) + ",";
			else
				str += PublicMethod.getZhuMa(num[i] );
		}
		return str;

	}
	/* (non-Javadoc)
	 * @see com.ruyicai.jixuan.Balls#getZhuma(java.util.Vector, int)
	 */
	@Override
	public String getZhuma(Vector<Balls> balls, int beishu) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getZhuma(Balls balls, String type){
		String str="";
		if(type.equals("Q2")){
		str+="S|Q2|";
		str+=DlcRxBalls.getstrjixuan(balls, ";S|Q2|");
		}else if(type.equals("Q3")){
			str+="S|Q3|";
			str+=DlcRxBalls.getstrjixuan(balls, ";S|Q3|");	
		}else if(type.equals("Z2")){
			str+="S|Z2|";
			str+=DlcRxBalls.getstrjixuan(balls, ";S|Z2|");
        }else if(type.equals("Z3")){
			str+="S|Z3|";
			str+=DlcRxBalls.getstrjixuan(balls, ";S|Z3|");
		}
		return str;
	}
}
