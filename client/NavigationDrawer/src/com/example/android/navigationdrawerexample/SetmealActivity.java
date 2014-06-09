package com.example.android.navigationdrawerexample;

import java.io.*; 
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 

import android.app.AlertDialog;
import android.app.ExpandableListActivity; 
import android.os.Bundle; 
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter; 
import android.widget.TextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import android.widget.CheckBox;
import android.widget.CompoundButton;  
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener; 

//妫ｆ牕鍘涚紒褎澹橢xpandableListActivity 
public class SetmealActivity extends ExpandableListActivity { 
    int ans = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_setmeal); 
        
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();//缂佸嫬锟�
        Map<String,String> map1 = new HashMap<String,String>(); 
        map1.put("group", "鏃╅"); 
        Map<String,String> map2 = new HashMap<String,String>(); 
        map2.put("group", "鍗堥"); 
        Map<String,String> map3 = new HashMap<String,String>(); 
        map3.put("group", "鏅氶"); 
        list.add(map1); 
        list.add(map2); 
        list.add(map3); 
        
        List<Map<String,String>> listChild1 = new ArrayList<Map<String,String>>();//child
        List<Map<String,String>> listChild2= new ArrayList<Map<String,String>>();//child  
        List<Map<String,String>> listChild3 = new ArrayList<Map<String,String>>();//child  
        try
        {
        	//StringBuffer sb = new StringBuffer();
        	InputStream inputStream1 = getResources().openRawResource(R.raw.breakfast);
        	InputStreamReader inputStreamReader1 = new InputStreamReader(inputStream1, "utf-8"); ; 
        	BufferedReader br1 = new BufferedReader(inputStreamReader1);
        	String line1 = "";
        	while((line1 = br1.readLine())!=null){
        		Map<String,String> map4 = new HashMap<String,String>(); 
        		String info[] = line1.split(" ");  		
                map4.put("country", info[0]); 
                map4.put("kaluli", info[1]); 
                listChild1.add(map4); 
        	}
        	br1.close();  
        	
        	InputStream inputStream2 = getResources().openRawResource(R.raw.lunch);
        	InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream2, "utf-8"); ; 
        	BufferedReader br2 = new BufferedReader(inputStreamReader2);
        	String line2 = "";
        	while((line2 = br2.readLine())!=null){
        		Map<String,String> map5 = new HashMap<String,String>(); 
        		String info[] = line2.split(" ");  		
                map5.put("country", info[0]); 
                map5.put("kaluli", info[1]); 
                listChild2.add(map5); 
        	}
        	br2.close(); 
        	
        	InputStream inputStream3 = getResources().openRawResource(R.raw.dinner);
        	InputStreamReader inputStreamReader3 = new InputStreamReader(inputStream3, "utf-8"); ; 
        	BufferedReader br3 = new BufferedReader(inputStreamReader3);
        	String line3 = "";
        	while((line3 = br3.readLine())!=null){
        		Map<String,String> map6 = new HashMap<String,String>(); 
        		String info[] = line3.split(" ");  		
                map6.put("country", info[0]); 
                map6.put("kaluli", info[1]); 
                listChild3.add(map6); 
        	}
        	br3.close(); 
        }
        catch (Exception e)
        {
           
        }

        List<List<Map<String,String>>> childs = new  ArrayList<List<Map<String,String>>>();//鐏忓棔琚辨稉鐚歨ild閸旂姴鍙嗛惃鍕肠閸氬牅锟�
        childs.add(listChild1); 
        childs.add(listChild2); 
        childs.add(listChild3); 
        
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this, list, R.layout.group, new String[]{"group"}, 
                new int[]{R.id.tv_group}, childs, R.layout.child, new String[]{"country", "kaluli"}, new int[]{R.id.tv_child, R.id.kaluli_child}); 
        setListAdapter(adapter);//闁倿鍘ら崳锟�     
        
    } 
    
    public void onClick(View view) {
    	AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("鍋ュ椋熷箍");
        String msg = "鎬绘秷鑰楃殑椋熺墿鍗¤矾閲�" + ans + "鍗�";
        builder.setMessage(msg);
        builder.setPositiveButton("纭", null);
        builder.create().show();
    }
    
   public void onClick_linear(View view) {
    	CheckBox check = (CheckBox) view.findViewById(R.id.checkBox_kaluli);
		TextView text = (TextView) view.findViewById(R.id.kaluli_child);
		if (check.isChecked()) {
			check.setChecked(true);
			ans -= Integer.parseInt(text.getText().toString());
		}
		else{
			check.setChecked(false);
			ans += Integer.parseInt(text.getText().toString());
		}
		
    }
}