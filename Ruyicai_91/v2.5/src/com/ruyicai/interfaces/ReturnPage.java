/**
 * 
 */
package com.ruyicai.interfaces;

import android.content.Context;
import android.view.View;

/**
 * �����л��Ľӿ�
 * @author Administrator
 *
 */
public interface ReturnPage {
   public int iQuitFlag = 0; // �����˳�
   public void returnMain();//�������б�
   public void switchView(View view);//�л�����
   public Context getContext();//�õ�context����
   public void showDialog();//�����ȴ�����
   public void dismissDialog();//ȡ���ȴ�����
}
