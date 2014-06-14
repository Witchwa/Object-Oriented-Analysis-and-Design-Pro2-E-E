package com.example.android.invite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
//如果使用地理围栏功能，需要import如下类
import com.baidu.location.BDGeofence;
import com.baidu.location.BDLocationStatusCodes;
import com.baidu.location.GeofenceClient;
import com.baidu.location.GeofenceClient.OnAddBDGeofencesResultListener;
import com.baidu.location.GeofenceClient.OnGeofenceTriggerListener;
import com.baidu.location.GeofenceClient.OnRemoveBDGeofencesResultListener;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.android.navigationdrawerexample.R;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class getList extends Activity{ 

	public double longitude, latitude;

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Log.i("k", "1");
        setContentView(R.layout.near_list);
        Log.i("k", "2");
        final ListView listview = (ListView) findViewById(R.id.listview);
    	final SharedPreferences spPreferences = getPreferences(MODE_PRIVATE);
    	String url = "http://192.168.2.12:8000/runner/getNearShakingRunner";
    	String username = spPreferences.getString("username", "");
        
        Log.i("k", "1");
	    mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
	    mLocationClient.registerLocationListener( myListener );    //注册监听函数

        Log.i("k", "1");
		HttpPost httpRequest = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();

        Log.i("k", "1");
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
		mLocationClient.setLocOption(option);
		if (mLocationClient != null && mLocationClient.isStarted())
		{
			mLocationClient.requestLocation();
	        Log.i("k", "1");
		}
		else 
			Log.d("LocSDK3", "locClient is null or not started");
		
		params.add(new BasicNameValuePair(username, "(" + String.valueOf(latitude) + ","+ String.valueOf(longitude) +")"));
		ArrayList<HashMap<String, String>> listitem = new ArrayList<HashMap<String,String>>();
	
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, "utf-8");
			httpRequest.setEntity(entity);
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{
				JSONArray arr = new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
				for (int i=0; i<arr.length();i++)
				{
					JSONObject obj = arr.getJSONObject(i);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("username", (String) obj.get("username"));
					map.put("phoneNum", (String) obj.get("phoneNumber"));
					listitem.add(map);
				}
			}
			SimpleAdapter mSimpleAdapter = new SimpleAdapter(null, listitem , R.layout.near_list_item, new String[]{"username", "phoneNumber"}, new int[]{R.id.username, R.id.phonenumber});
			listview.setAdapter(mSimpleAdapter);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public class MyLocationListener implements BDLocationListener {
	    @Override
	   public void onReceiveLocation(BDLocation location) {
	      if (location == null)
	          return ;
	      StringBuffer sb = new StringBuffer(256);
	      sb.append("time : ");
	      sb.append(location.getTime());
	      sb.append("\nerror code : ");
	      sb.append(location.getLocType());
	      sb.append("\nlatitude : ");
	      sb.append(location.getLatitude());
	      sb.append("\nlontitude : ");
	      sb.append(location.getLongitude());
	      sb.append("\nradius : ");
	      sb.append(location.getRadius());
	      if (location.getLocType() == BDLocation.TypeGpsLocation){
	           sb.append("\nspeed : ");
	           sb.append(location.getSpeed());
	           sb.append("\nsatellite : ");
	           sb.append(location.getSatelliteNumber());
	           } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
	           sb.append("\naddr : ");
	           sb.append(location.getAddrStr());
	        } 
	      longitude = location.getLongitude();
	      latitude = location.getLatitude();
	    }
	public void onReceivePoi(BDLocation poiLocation) {
	//将在下个版本中去除poi功能
	         if (poiLocation == null){
	                return ;
	          }
	         StringBuffer sb = new StringBuffer(256);
	          sb.append("Poi time : ");
	          sb.append(poiLocation.getTime());
	          sb.append("\nerror code : ");
	          sb.append(poiLocation.getLocType());
	          sb.append("\nlatitude : ");
	          sb.append(poiLocation.getLatitude());
	          sb.append("\nlontitude : ");
	          sb.append(poiLocation.getLongitude());
	          sb.append("\nradius : ");
	          sb.append(poiLocation.getRadius());
	          if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
	              sb.append("\naddr : ");
	              sb.append(poiLocation.getAddrStr());
	         } 
	          if(poiLocation.hasPoi()){
	               sb.append("\nPoi:");
	               sb.append(poiLocation.getPoi());
	         }else{             
	               sb.append("noPoi information");
	          }
	        }
	}
}