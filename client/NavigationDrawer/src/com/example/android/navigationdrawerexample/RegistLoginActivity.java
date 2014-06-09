package com.example.android.navigationdrawerexample;

import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 
  






import android.app.Activity;
import android.app.ExpandableListActivity; 
import android.content.Intent;
import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SimpleExpandableListAdapter; 
import android.widget.TextView;
//首先继承ExpandableListActivity 
public class RegistLoginActivity extends Activity{ 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_registlogin); 
        getActionBar().hide();
        TextView suibianTextView = (TextView)findViewById(R.id.textView_suibian);
        suibianTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(RegistLoginActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
    } 
}