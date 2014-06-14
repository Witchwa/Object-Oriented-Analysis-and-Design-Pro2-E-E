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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ExpandableListActivity; 
import android.content.Intent;
import android.os.Bundle; 
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleExpandableListAdapter; 
import android.widget.TextView;
import android.widget.Toast;
public class RegistInfoActivity extends Activity{ 
    /** Called when the activity is first created. */
	static EditText nicknameEditText;
	static EditText phonenumberEditText;
	static EditText heightEditText;
	static EditText weightEditText;
	static EditText mimaEditText;
	Button submitButton;
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_registinfo); 
        getActionBar().hide();
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        nicknameEditText = (EditText)findViewById(R.id.editText_register_nickname);
        phonenumberEditText = (EditText)findViewById(R.id.editText_register_phonenumber);
        heightEditText = (EditText)findViewById(R.id.editText_register_height);
        weightEditText = (EditText)findViewById(R.id.editText_register_weight);
        mimaEditText = (EditText)findViewById(R.id.editText_register_mima);
        submitButton = (Button)findViewById(R.id.btn_submit);
        //鎻愪氦
        submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uriString = "http://172.18.158.24:8000/registe";
				Log.e("res", "123");
				try {
					if (post_regist(uriString) == 100) {
						Toast.makeText(RegistInfoActivity.this, "娉ㄥ唽鎴愬姛锛�", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(RegistInfoActivity.this, RegistLoginActivity.class);
						startActivity(intent);
					} else if (post_regist(uriString) == 101) {
						Toast.makeText(RegistInfoActivity.this, "璇ョ敤鎴峰凡缁忓瓨鍦紒", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(RegistInfoActivity.this, "璇ョ敤鎴峰凡缁忕櫥褰曪紝璇峰厛鐧诲嚭锛�", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
   } 
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.regist, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
  //娉ㄥ唽鏃朵互post鏂瑰紡浼犻�鐢ㄦ埛淇℃伅缁欐湇鍔″櫒
    private static int post_regist(String uriString) throws JSONException {
		BufferedReader reader = null;
		StringBuffer sbBuffer = null;
		HttpClient client = new DefaultHttpClient();
		Log.e("res", "post");
		int code = 0;
		HttpPost requestHttpPost = new HttpPost(uriString);
		Log.e("res", "post1");

		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("userName", nicknameEditText.getText().toString()));
		paramsList.add(new BasicNameValuePair("password", mimaEditText.getText().toString()));
		paramsList.add(new BasicNameValuePair("phoneNum", phonenumberEditText.getText().toString()));
		paramsList.add(new BasicNameValuePair("weight", weightEditText.getText().toString()));
		paramsList.add(new BasicNameValuePair("height", heightEditText.getText().toString()));

		try {
			HttpEntity entity = new UrlEncodedFormEntity(paramsList, "utf-8");
			requestHttpPost.setEntity(entity);
			HttpResponse response = client.execute(requestHttpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//璇诲彇杩斿洖鏁版嵁
				reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				sbBuffer = new StringBuffer();
				sbBuffer.append(reader.readLine());
				Log.e("tag", sbBuffer.toString());
				JSONObject object = new JSONObject(sbBuffer.toString());
				String phase = object.getString("phase");
				code = object.getInt("code");
				Log.e("tag", code+"");
				Log.e("tag", phase);
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
		Log.e("tag", code+"");
	        return code;  
		}
}
    
    
