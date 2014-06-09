package com.example.android.invite;

import com.example.android.navigationdrawerexample.MainActivity;
import com.example.android.navigationdrawerexample.R;
import com.example.android.navigationdrawerexample.RegistLoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;


public class main extends Activity {
	private SensorManager sensorManager;
	private Vibrator vibrator;
	private static final String TAG = "TestSensorActivity";
	private static final int SENSOR_SHAKE = 10;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shake);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (sensorManager != null) {// ע�������
			sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
			// ��һ��������Listener���ڶ������������ô��������ͣ�����������ֵ��ȡ��������Ϣ��Ƶ��
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		if (sensorManager != null) {// ȡ��������
			sensorManager.unregisterListener(sensorEventListener);
		}
	}
	/**
	* ������Ӧ����
	*/
	private SensorEventListener sensorEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			// ��������Ϣ�ı�ʱִ�и÷���
			float[] values = event.values;
			float x = values[0]; // x�᷽����������ٶȣ�����Ϊ��
			float y = values[1]; // y�᷽����������ٶȣ���ǰΪ��
			float z = values[2]; // z�᷽����������ٶȣ�����Ϊ��
			Log.i(TAG, "x�᷽����������ٶ�" + x + "��y�᷽����������ٶ�" + y + "��z�᷽����������ٶ�" + z);
			// һ����������������������ٶȴﵽ40�ʹﵽ��ҡ���ֻ���״̬��
			int medumValue = 19;// ���� i9250��ô�ζ����ᳬ��20��û�취��ֻ����19��
			if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
				vibrator.vibrate(200);
				Message msg = new Message();
				msg.what = SENSOR_SHAKE;
				handler.sendMessage(msg);
			}
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	/**
	* ����ִ��
	*/
	Handler handler = new Handler() {
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		Intent intent = new Intent();
		intent.setClass(main.this, invite.class);
		startActivity(intent);
		
		}
	};
}