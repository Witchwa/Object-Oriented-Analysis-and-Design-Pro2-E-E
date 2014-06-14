package com.example.android.sportsrecording;

import com.example.android.navigationdrawerexample.R;
import com.example.android.navigationdrawerexample.R.id;
import com.example.android.navigationdrawerexample.R.layout;


import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;



public class TimeTracker extends Activity{
	
	private Chronometer ch;
	private Button start, stop;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_sportrecording);
		ch=(Chronometer)findViewById(R.id.chro);
		start = (Button)findViewById(R.id.startRecord);
		stop = (Button)findViewById(R.id.endRecord);
		
		start.setOnClickListener(clickListener);
		stop.setOnClickListener(clickListener);
		}
	
	private  OnClickListener  clickListener = new OnClickListener() {

	
           @Override
		   public void onClick(View v){
        	   switch(v.getId()){
        	   case R.id.startRecord:
        		   ch.setBase(SystemClock.elapsedRealtime());
        		   ch.start();
        		   break;
        	   case R.id.endRecord:	  
        		   ch.stop();
        		   break;
        	   default:
        	       break;
		      
		      }
           }
	};  
}
