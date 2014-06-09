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

import com.example.android.meal.*;
import com.example.android.meal.ExpandableListViewActivity;
import com.example.android.meal.Recipe;
import com.example.android.meal.RecipeMenu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;

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
        		
        		/*ListView ls = (ListView)rootView.findViewById(R.id.listview_sportrecording);
        		ArrayList<HashMap<String, Object>> listitem = new ArrayList<HashMap<String,Object>>();
        		String[] textStrings = {"查看时长","查看路径","查看里程数","查看消耗卡路里"};
        		Object[] objects = {R.drawable.earth, R.drawable.mars,R.drawable.jupiter,R.drawable.venus};
        		for (int j = 0; j < 4; j++) {
        			HashMap<String, Object> map = new HashMap<String, Object>();
        			map.put("text", textStrings[j]);
        			map.put("image", objects[j]);
        			listitem.add(map);
				}
        		SimpleAdapter mSimpleAdapter = new SimpleAdapter(getActivity(), listitem , R.layout.item_fragment_sportrecording, new String[]{"text", "image"}, new int[]{R.id.textView1, R.id.imageView1});
        		ls.setAdapter(mSimpleAdapter);
        		ls.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						
					}
				});*/
                Toast.makeText(getActivity(), "运动记录", Toast.LENGTH_SHORT).show();
			}else if(i == 1){
				//腾讯地图API获取位置，查看附近跑友
				rootView = inflater.inflate(R.layout.fragment_planet, container, false);
        
                Toast.makeText(getActivity(), "跑步邀约", Toast.LENGTH_SHORT).show();
			}else if(i == 2){
				rootView = inflater.inflate(R.layout.fragment_planet, container, false);
			}else if(i== 3){
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
}