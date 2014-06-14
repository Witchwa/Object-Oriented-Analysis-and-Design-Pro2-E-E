package com.example.android.invite;

import com.example.android.navigationdrawerexample.R;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.Service;
import android.content.DialogInterface.OnClickListener;
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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
public class main extends Activity implements SensorEventListener  
{  
    SensorManager sensorManager = null;  
    Vibrator vibrator = null;  
  
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.invite_main);  

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);  
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);  
  
    }  
  
    @Override  
    protected void onPause()  
    {  
        super.onPause();  
        sensorManager.unregisterListener(this);  
    }  
  
    @Override  
    protected void onResume()  
    {  
        super.onResume();  
        sensorManager.registerListener(this,  
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),  
                SensorManager.SENSOR_DELAY_NORMAL);  
    }  
  
    @Override  
    public void onAccuracyChanged(Sensor sensor, int accuracy)  
    {  
        //当传感器精度改变时回调该方法，Do nothing.  
    }  
  
    @Override  
    public void onSensorChanged(SensorEvent event)  
    {  
  
        int sensorType = event.sensor.getType();  
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴  
        float[] values = event.values;  
        if (sensorType == Sensor.TYPE_ACCELEROMETER)  
        {  
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math  
                    .abs(values[2]) > 17))  
            {  
                Log.d("sensor x ", "============ values[0] = " + values[0]);  
                Log.d("sensor y ", "============ values[1] = " + values[1]);  
                Log.d("sensor z ", "============ values[2] = " + values[2]);  
                
				Intent intent = new Intent();
				intent.setClass(main.this, getList.class);
				startActivity(intent);
				main.this.finish();
                
                vibrator.vibrate(500);  
            }  
  
        }  
    }  
  
}  */

public class main extends Activity
{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_main);  
        
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        TextView tv = (TextView)findViewById(R.id.tv_group);
        
	}
	
}
