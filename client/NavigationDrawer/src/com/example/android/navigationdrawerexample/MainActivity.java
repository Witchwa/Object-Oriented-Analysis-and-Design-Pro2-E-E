/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.ls.LSException;

import com.example.android.invite.getList;
import com.example.android.invite.main;
import com.example.android.meal.*;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.StaticLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    LocationManager locationm;
    String provider;
    Location location0;
    LocationListener GPS_listener;
    Criteria criteria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
//        case R.id.action_websearch:
//            // create intent to perform web search for this planet
//            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//            // catch event that there's no activity to handle intent
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//                Toast.makeText(this, "button clicked !", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//            }
//            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	int i = getArguments().getInt(ARG_PLANET_NUMBER);
        	String planet = getResources().getStringArray(R.array.planets_array)[i];
        	View rootView = null;
			List<Recipe> recipeList = null;
        	if (i == 0) {
        		rootView = inflater.inflate(R.layout.fragment_sportrecording, container, false);        		        		
                Toast.makeText(getActivity(), "运动记录", Toast.LENGTH_SHORT).show();
                
                //记录运动时间：
                final Chronometer ch=(Chronometer) rootView.findViewById(R.id.chro);
                final Button start = (Button) rootView.findViewById(R.id.startRecord);
                final TextView distanceTextView = (TextView)rootView.findViewById(R.id.distance);
                final TextView speedTextView = (TextView)rootView.findViewById(R.id.speed);
                final TextView caloTextView = (TextView)rootView.findViewById(R.id.calories);
                Button stop = (Button) rootView.findViewById(R.id.endRecord);
				start.setText("开始记录");
        		start.setOnClickListener(new OnClickListener() {      			
        	           @Override
        			   public void onClick(View v){  
        	        	   if(start.getText() == "开始记录"){
        	        		   ch.setBase(SystemClock.elapsedRealtime());
        	        		   distanceTextView.setText("0");
        	        		   speedTextView.setText("0");
        	        		   caloTextView.setText("0");
        	        		   ch.start();
        	        		   start.setText("正在记录");
        	        	   }	
        	        	   MainActivity mainActivity = (MainActivity) getActivity();
        	        	   mainActivity.init_location_listener(distanceTextView);
        	        	   Log.e("GPS", "jilujuli");
        	           }
        		});
        		stop.setOnClickListener(new OnClickListener() {        			
     	           @Override
     			   public void onClick(View v){    	        	   
     	        	 ch.stop();
     	        	 start.setText("开始记录");
     	        	 final long time = (long) (Double.parseDouble(ch.getText().toString().split(":")[1]))
     	        	  + (long) (Double.parseDouble(ch.getText().toString().split(":")[0])) * 60;
     	        	 long distance = (long) Double.parseDouble(distanceTextView.getText().toString());
     	        	
     	        	 speedTextView.setText(distance / time + "");
     	        	long k = 30 ;
     	        	if (distance / time != 0) {
						k = 30 / (distance / time * 60);
					}
     	        	final long k1 = k;
     	        	 //dialog
     	        	final EditText et = new EditText(getActivity());
     	   		new AlertDialog.Builder(getActivity()).setTitle("请输入体重(单位:kg)")
     	   				.setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
     	   					@Override
     	   					public void onClick(DialogInterface arg0, int arg1) {
     	   						long weight = (long) Double.parseDouble(et.getText().toString());
     	   						long result = weight * time / 3600 * k1;
     	   						caloTextView.setText(result + "");
     	   					}
     	   				}).setNegativeButton("取消", null).show();

     	        	 
     	           }
     		});
			
			}else if(i == 1){
                Toast.makeText(getActivity(), "跑步邀约", Toast.LENGTH_SHORT).show();
                rootView = inflater.inflate(R.layout.invite_main, container, false);
                Button cs = (Button)rootView.findViewById(R.id.cs);
                
                cs.setOnClickListener(new View.OnClickListener(){

        			@Override
        			public void onClick(View arg0) {
        				// TODO Auto-generated method stub

        				Intent intent = new Intent();
        				Log.e("jump", "succeed");
        				intent.setClass(getActivity(), getList.class);
        				startActivity(intent);
        			}
                	
                });
			}else if(i == 2){
				//记录我的饮食
				rootView = inflater.inflate(R.layout.sex, container, false);
				Toast.makeText(getActivity(), "我的饮食", Toast.LENGTH_SHORT).show();
			}else {
				rootView = inflater.inflate(R.layout.fragment_planet, container, false);
				Toast.makeText(getActivity(), "饮食推荐", Toast.LENGTH_SHORT).show();
				/*ListView newListView = (ListView)rootView.findViewById(R.id.listview_kaluli);
				try {
					recipeList = RecipeMenu.getRecipe(getActivity());
				}
				catch(Exception e)  
		        {  
		            e.printStackTrace();  
		        } 
        		RecipeAdapter mRecipeAdapter = new RecipeAdapter(getActivity(), recipeList);
        		newListView.setAdapter(mRecipeAdapter);*/
//                int imageId = getResources().getIdentifier("earth",
//                                "drawable", getActivity().getPackageName());
//                ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
//                int imageId1 = getResources().getIdentifier("mars",
//                        "drawable", getActivity().getPackageName());
//                ((ImageView) rootView.findViewById(R.id.image1)).setImageResource(imageId1);
//                int imageId2 = getResources().getIdentifier("venus",
//                "drawable", getActivity().getPackageName());
//                ((ImageView) rootView.findViewById(R.id.image2)).setImageResource(imageId2);
//                Toast.makeText(getActivity(), "拓展功能", Toast.LENGTH_SHORT).show();
			}
            
            getActivity().setTitle(planet);
            return rootView;
        }
    }
	
	 public void femaleOnClick(View view) {
    	LayoutInflater flater = LayoutInflater.from(this);
    	View v = flater.inflate(R.layout.weigh, null);
    	setContentView(v); 	
    	ImageView im = (ImageView) v.findViewById(R.id.sex_image);
    	im.setImageDrawable(getResources().getDrawable(R.drawable.female));
    	EditText editText=(EditText)findViewById(R.id.weight);  
    	
    }
    
    public void maleOnClick(View view) {
    	LayoutInflater flater = LayoutInflater.from(this);
    	View v = flater.inflate(R.layout.weigh, null);
    	setContentView(v); 	
    	ImageView im = (ImageView) v.findViewById(R.id.sex_image);
    	im.setImageDrawable(getResources().getDrawable(R.drawable.male));
    }
    
    public void previousOnClick(View view) {
    	LayoutInflater flater = LayoutInflater.from(this);
    	View v = flater.inflate(R.layout.sex, null);
    	setContentView(v); 	
    }
    
    public void nextOnClick(View view) {
    	Intent intent = new Intent();
        intent.setClass(MainActivity.this,ExpandableListViewActivity.class);
        startActivity(intent);
    	
    }
    private final static double EARTH_RADIUS = 6378137.0; 
    private static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
               + Math.cos(radLat1) * Math.cos(radLat2)
               * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
     }
    //GPS function listener
    public void init_location_listener(final TextView tv) {
    	 locationm = (LocationManager) getSystemService(LOCATION_SERVICE);
         criteria = new Criteria();
         criteria.setAccuracy(Criteria.ACCURACY_FINE);
         criteria.setAltitudeRequired(false);
         criteria.setBearingRequired(false);
         criteria.setCostAllowed(true);
         criteria.setPowerRequirement(Criteria.POWER_LOW);
         provider = locationm.getBestProvider(criteria, true);
   
         location0 = locationm.getLastKnownLocation(provider);
             //获得上次的记录
         Log.e("current location0", location0.getLatitude()+ " + " + location0.getLongitude());
         GPS_listener = new LocationListener() {
         //监听位置变化，实时获取位置信息
             @Override
             public void onStatusChanged(String provider, int status,
                    Bundle extras) {
                // TODO Auto-generated method stub
   
             }
   
             @Override
             public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
   
             }
   
             @Override
             public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
   
             }
   
             @Override
             public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
            	 //位置发生改变时,如果Provider传进相同的坐标，它就不会被触发 
            	 Log.e("current location", location.getLatitude()+ " + " + location.getLongitude());
            	 double distance = gps2m(location0.getLatitude(), location0.getLongitude(), location.getLatitude(), location.getLongitude());
            	 tv.setText(String.valueOf(distance));
             }
         };
         locationm.requestLocationUpdates(provider, 0, 0, GPS_listener);
         
	} 

}