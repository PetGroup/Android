package com.ruyicai.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.home.HomeActivity;

/**
 * ���Ϳ�ݷ�ʽ����
 * @author miaoqiang
 *
 */
public class Shortcut extends  Activity{
	Intent addShortcut;//Ҫ��ӿ�ݷ�ʽ��Intent
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addShortcut();
	}

	
	   private void addShortcut() {
			// TODO Auto-generated method stub
	    	
	    	if(getIntent().getAction().equals(Intent.ACTION_CREATE_SHORTCUT)){
	    		addShortcut = new Intent();
	    		/*���ÿ�ݷ�ʽ������*/
	    		addShortcut.putExtra(Intent.ACTION_CREATE_SHORTCUT,"�����Ʊ");
	    		/*���ÿ�ݷ�ʽ��ͼ��*/
	    		Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
	    		/*��ӿ�ݷ�ʽͼ��*/
	    		addShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
	    		/*������ݷ�ʽִ�е�Intent*/
	    		Intent startEisen = new Intent(this,HomeActivity.class);
	    		/*��ӿ�ݷ�ʽ��Intent*/
	    		addShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, startEisen);
	    		setResult(RESULT_OK,addShortcut);
	    		
	    	}else{
	    		setResult(RESULT_CANCELED);
	    	}
	    	finish();

		}
	    /*
	     * ��ʵ��ݷ�ʽ��Ϣ�Ǳ�����com.android.launcher��launcher.db��favorites���У� 
��ش��룺 

boolean isInstallShortcut = false ;  
    final ContentResolver cr = context.getContentResolver();  
    final String AUTHORITY = "com.android.launcher.settings";  
    final Uri CONTENT_URI = Uri.parse("content://" +  
                     AUTHORITY + "/favorites?notify=true");  
      
    Cursor c = cr.query(CONTENT_URI,  
    new String[] {"title","iconResource" },  
    "title=?",  
    new String[] {"XXX" }, null);//XXX��ʾӦ�����ơ�  
            if(c!=null && c.getCount()>0){  
        isInstallShortcut = true ;  
    }  
    /*try { 
        while (c.moveToNext()) { 
                                    String tmp = ""; 
            tmp = c.getString(0); 
        } 
        } catch (Exception e) { 
 
        } finally { 
            c.close(); 
        }*/  
    //return isInstallShortcut ;  
//}*/

}
