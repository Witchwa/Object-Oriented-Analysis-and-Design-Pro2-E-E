package com.example.android.navigationdrawerexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ExpandableListActivity; 
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle; 
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleExpandableListAdapter; 
import android.widget.TextView;
import android.widget.Toast;
public class RegistLoginActivity extends Activity{ 
    /** Called when the activity is first created. */
	TextView suibianTextView;
	static EditText zhanghaoEditText;
	static EditText mimaEditText;
	Button registButton;
	Button loginButton;
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_registlogin); 
        getActionBar().hide();
        suibianTextView = (TextView)findViewById(R.id.textView_suibian);
        zhanghaoEditText = (EditText)findViewById(R.id.editText_zhanghao);
        mimaEditText = (EditText)findViewById(R.id.editText_mima);
        registButton = (Button)findViewById(R.id.btn_register);
        loginButton = (Button)findViewById(R.id.btn_login);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final SharedPreferences spPreferences = getPreferences(MODE_PRIVATE);
        //娉ㄥ唽
        registButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(RegistLoginActivity.this, RegistInfoActivity.class);
				startActivity(intent);
			}
		});
        //鐧诲綍
        loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uriString = "http://172.18.158.24:8000/login";
				try {
					if (post_login(uriString) == 150) {
						Editor editor = spPreferences.edit();
						editor.putString("userName", zhanghaoEditText.getText().toString());
						editor.putString("password", mimaEditText.getText().toString());
						editor.commit();
						Toast.makeText(RegistLoginActivity.this, "鐧诲綍鎴愬姛锛�", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(RegistLoginActivity.this, MainActivity.class);
						startActivity(intent);
					}else if (post_login(uriString) == 151) {
						Toast.makeText(RegistLoginActivity.this, "鐢ㄦ埛涓嶅瓨鍦紒", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(RegistLoginActivity.this, "鐢ㄦ埛鍚嶄笌瀵嗙爜涓嶅尮閰嶏紒", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        //闅忎究鐪嬬湅
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
    
    //鐧诲綍鏃朵互post鏂瑰紡浼犻�鐢ㄦ埛鍚嶅拰瀵嗙爜缁欐湇鍔″櫒
    private static int post_login(String uriString) throws JSONException {
		BufferedReader reader = null;
		StringBuffer sbBuffer = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost requestHttpPost = new HttpPost(uriString);
		int code = 0;
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("userName", zhanghaoEditText.getText().toString()));
		paramsList.add(new BasicNameValuePair("password", mimaEditText.getText().toString()));
		
		try {
			HttpEntity entity = new UrlEncodedFormEntity(paramsList, "utf-8");
			requestHttpPost.setEntity(entity);
			HttpResponse response = client.execute(requestHttpPost);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//璇诲彇杩斿洖鏁版嵁
				reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				sbBuffer = new StringBuffer();
				sbBuffer.append(reader.readLine());
				JSONObject object = new JSONObject(sbBuffer.toString());
				String phase = object.getString("phase");
				code = object.getInt("code");
			}
		} catch (ClientProtocolException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			 try {  
	                //鍏抽棴娴� 
	                if (null != reader){  
	                    reader.close();  
	                    reader = null;  
	                }  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return code;  
		}

}
    
    
