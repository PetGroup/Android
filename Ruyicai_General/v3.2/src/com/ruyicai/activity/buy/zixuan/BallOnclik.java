package com.ruyicai.activity.buy.zixuan;

import java.util.Vector;

import android.util.Log;
import android.view.View;

import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.pojo.OneBallView;

public class BallOnclik {
	 private ZixuanActivity zixuan;
	 private AreaNum areaNums[]; 
	 private float[] areaNumTopY;//ÿ��ѡ�������Ķ�Ӧ��yֵ 
	 private float[] areaNumBotY;//ÿ��ѡ���ײ��Ķ�Ӧ��yֵ 	
	 private BallTable ballTable;
	 private float ballWith;
	 private float tableTopInt;
	 private float textHeight;
 
	 public BallOnclik(ZixuanActivity zixuan,AreaNum areaNums[]){
		 areaNumTopY = new float[areaNums.length];
		 areaNumBotY = new float[areaNums.length];
		 this.zixuan = zixuan;
		 this.areaNums = areaNums;
		 ballTable = areaNums[0].table;
	 }
	 private void initInfo() {
		 if(ballWith==0){
			 if(ballTable.textView != null){
				 textHeight = ballTable.textView.getHeight();
			 }
			 tableTopInt = areaNums[0].tableLayout.getTop();
			 ballWith = ((View) ballTable.getBallViews().get(0)).getHeight();
			 initAreaNumY();
		 }
	 }
	 public void onClik(float x,float y){
			 initInfo(); 
	        int iBallId = getIBallId(x,y);
	        if(iBallId!=-1){
	        	zixuan.isBallTable(iBallId);
	    		zixuan.showEditText();
	    		zixuan.changeTextSumMoney(); 
	        }
	 }
	
	 public int getIBallId(float x,float y){
		 int id = 0;
		 int yIndex[] = getHIndex(y);
		 int xIndex = getLIndex(x);
		 int index = yIndex[1]*ballTable.lieNum + xIndex;
		 if(yIndex[1]==-1||xIndex == -1){
			 return -1;
		 }else{
			 Vector<OneBallView> ballViewVector = areaNums[yIndex[0]].table.getBallViews();
			 if(index<ballViewVector.size()){
				 try {
					 OneBallView ballView = (OneBallView) areaNums[yIndex[0]].table.getBallViews().get(index);
					 id = ballView.getId();
				} catch (Exception e) {
					// TODO: handle exception
				} 
			 }else{
				 return -1;
			 }
		 }
	
		 return id;
	 }
	 /**
	  * ����yֵ�ж���С���������ѡ����
	  * @return -1�������κ�һ��ѡ��
	  */
	 public int[] getHIndex(float y){
		 int indexs[] = new int[2];//0��ѡ����1ѡ��������
		 float deletY = y;
		 for(int i=0;i<areaNumTopY.length;i++){
	          deletY -= tableTopInt;
	          if(deletY<0){
	        		 indexs[0] = -1; indexs[1] = -1;
	    			 return indexs; 
	          }
	          if(y>areaNumTopY[i]&&y<areaNumBotY[i]){
	        	  indexs[0] = i;
	        	  float lineNum = (y - areaNumTopY[i])%(ballWith+textHeight);
	        	  if(lineNum>ballWith){
	        		  indexs[1] = -1; 
	        	  }else{
	        		  int line = (int) ((y - areaNumTopY[i])/(ballWith+textHeight));
	        		  indexs[1] = line;
	        	  }
	        	  
	        	  return indexs;
	          }
	          deletY -= areaNumBotY[i];
		 } 
		 return indexs; 
	 }
	 /**
	  * ����xֵ�ж����ĸ�С��
	  * @return
	  */
	 public int getLIndex(float x){
		 int index = (int) (x/ballWith);
		 if(index<ballTable.lieNum){
			 return index; 
		 }else{
			 return -1;
		 }
		
	 }
	 public void initAreaNumY(){
		 for(int i=0;i<areaNumTopY.length;i++){
			 areaNumTopY[i] = areaNums[i].layout.getTop() + tableTopInt;
			 areaNumBotY[i] = areaNumTopY[i] + ballTable.lineNum*(ballWith+textHeight);
		 }
	 }
}
