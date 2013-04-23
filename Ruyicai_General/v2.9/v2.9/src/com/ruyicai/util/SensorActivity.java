/**
 * 
 */
package com.ruyicai.util;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

/** 
 * ʵ���ֻ��𶯵���
 * @author Administrator
 * 
 */
public abstract class SensorActivity {
	/** Called when the activity is first created. */

	private long initTime = 0;
	private long lastTime = 0;
	private long curTime = 0;
	private long duration = 0;
	private float last_x = 0.0f;
	private float last_y = 0.0f;
	private float last_z = 0.0f;
	private float shake = 0.0f;
	private float totalShake = 0.0f;
	private Context context;
	private SensorManager sm = null;
//	private ShellRWSharesPreferences shellRW;
	public void startAction() {
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		int sensorType = android.hardware.Sensor.TYPE_ACCELEROMETER;
		sm.registerListener(mySensorEventListener, sm
				.getDefaultSensor(sensorType),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void getContext(Context context) {
		this.context = context;
	}

	public void stopAction() {
		if (sm != null) {
			sm.unregisterListener(mySensorEventListener);
		}

	}

	public abstract void action();

	public final SensorEventListener mySensorEventListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(android.hardware.Sensor sensor,
				int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if (event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER) {

				// ��ȡ���ٶȴ���������������

				float x = event.values[SensorManager.DATA_X];
				float y = event.values[SensorManager.DATA_Y];
				float z = event.values[SensorManager.DATA_Z];

				// ��ȡ��ǰʱ�̵ĺ�����

				curTime = System.currentTimeMillis();

				// 100������һ��

				float aa = curTime - lastTime;

				if ((curTime - lastTime) > 100) {
					duration = (curTime - lastTime);
					// ���ǲ��Ǹտ�ʼ�ζ�
					if (last_x == 0.0f && last_y == 0.0f && last_z == 0.0f) {
						// last_x��last_y��last_zͬʱΪ0ʱ����ʾ�ոտ�ʼ��¼
						initTime = System.currentTimeMillis();
					} else {
						// ���λζ�����
						shake = (Math.abs(x - last_x) + Math.abs(y - last_y) + Math
								.abs(z - last_z));

					}

					// ��ÿ�εĻζ�������ӣ��õ�����ζ�����
					totalShake += shake;
					// �ж��Ƿ�Ϊҡ��
					if (shake > 20) {
						action();
						onVibrator();
						initShake();
					}
					last_x = x;
					last_y = y;
					last_z = z;
					lastTime = curTime;
				}
			}
		}
	};

    /**
     * ʵ���𶯵ķ���
     * @���ߣ�
     * @���ڣ� 
     * @������
     * @����ֵ��
     * @�޸��ˣ�
     * @�޸����ݣ�
     * @�޸����ڣ�
     * @�汾��
     */
	public void onVibrator() {
		// ��ȡ�Ƿ��𶯲���
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(context, "addInfo");
		boolean isOn= shellRW.getBoolean("isOn");
		if(!isOn){
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (vibrator == null) {
			Vibrator localVibrator = (Vibrator) context.getApplicationContext()
					.getSystemService("vibrator");
			vibrator = localVibrator;
		}
		vibrator.vibrate(100L);
		}
	}

	// ҡ����ʼ��

	public void initShake() {
		lastTime = 0;
		duration = 0;
		curTime = 0;
		initTime = 0;
		last_x = 0.0f;
		last_y = 0.0f;
		last_z = 0.0f;
		shake = 0.0f;
		totalShake = 0.0f;
	}
}
