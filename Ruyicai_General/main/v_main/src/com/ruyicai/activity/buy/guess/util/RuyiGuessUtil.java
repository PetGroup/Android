package com.ruyicai.activity.buy.guess.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.palmdream.RuyicaiAndroid.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class RuyiGuessUtil {

	public static boolean isExist(String path, String fileName) {
		File directory = new File(path);
		if (directory.isDirectory() && !directory.exists()) {
			directory.mkdirs();
		}
		File file = new File(path + fileName);
		return file.exists();
	}
	
	public static String getSaveFilePath(String filePath) {
		return getRootFilePath() + filePath;
	}
	
	public static String getRootFilePath() {
		if (hasSDCard()) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			return sdcardDir.getParent() + "/" + sdcardDir.getName();// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath: /data/data/
		}
	}
	
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		} 
		return true;
	}

	public static Bitmap decodeFile(File f) {
		try {
			BitmapFactory.Options option = new BitmapFactory.Options();
			option.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, option);

			final int REQUIRED_SIZE = 100;
			int width = option.outWidth;
			int height = option.outHeight;
			int scale = 1;
			while (true) {
				if (width / 2 < REQUIRED_SIZE
						|| height / 2 < REQUIRED_SIZE)
					break;
				width /= 2;
				height /= 2;
				scale *= 2;
			}

			BitmapFactory.Options option2 = new BitmapFactory.Options();
			option2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, option2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void downLoadImage(final File file, final String url, 
			final ImageView imageView) {
		new Thread(new Runnable() {
			public void run() {
				try {
					int buffer = 1024;
					URL imageUrl = new URL(url);
					HttpURLConnection conn = (HttpURLConnection) imageUrl
							.openConnection();
					conn.setConnectTimeout(30000);
					conn.setReadTimeout(30000);
					conn.setInstanceFollowRedirects(true);
					InputStream inStream = conn.getInputStream();
					OutputStream outStream = new FileOutputStream(file);
					byte[] bytes = new byte[buffer];
					for (;;) {
						int count = inStream.read(bytes, 0, buffer);
						if (count == -1)
							break;
						outStream.write(bytes, 0, count);
					}
					outStream.close();
					final Bitmap bitmap = decodeFile(file);
					Activity a = (Activity) imageView.getContext();
					a.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					});
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * ɾ�����ͼƬ
	 */
	public static void deleteSharePicture(String sharePictureName) {
		if (!"".equals(sharePictureName)) {
			File image = new File(sharePictureName);
			if (image.exists()) {
				image.delete();
			}
		}
	}
	
	/**
	 * 格式化剩余时间 
	 */
	public static String formatLongToString(long time) {
		if (!(time > 0)) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		int day = 0;
		int hour = 0;
		long minute = 0;
		buffer.append("剩");
		if (time > 60) {
			minute = time / 60;
			time = time % 60;
		}

		if (minute >= 60) {
			hour = (int) (minute / 60);
			minute = minute % 60;
		}

		if (hour >= 24) {
			day = hour / 24;
			hour = hour % 24;
		}

		buffer.append(day).append("天");
		buffer.append(hour).append("时");
		buffer.append(minute).append("分");
		buffer.append(time).append("秒");
		return buffer.toString();
	}
	
	public static void showToast(Context context, int resId) {
		ImageView image = new ImageView(context);
		image.setImageResource(resId);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(300);
		toast.setView(image);
		toast.show();
	}
	
	public static void showToast(Context context, View view, int gravity, int duration) {
		Toast toast = new Toast(context);
		toast.setGravity(gravity, 0, 0);
		toast.setDuration(duration);
		toast.setView(view);
		toast.show();
	}
	
	public static GridView createPopupWindow(Context context, String[] info,
			int count) {
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflate.inflate(R.layout.buy_join__window, null);
		GridView gridView = (GridView) layout.findViewById(R.id.gridView);
		gridView.setNumColumns(count);
		return gridView;
	}
	
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
