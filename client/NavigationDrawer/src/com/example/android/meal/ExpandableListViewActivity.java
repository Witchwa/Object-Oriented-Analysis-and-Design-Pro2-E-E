package com.example.android.meal;
import java.util.ArrayList;
import java.util.List;

import com.example.android.navigationdrawerexample.R;
import com.example.android.navigationdrawerexample.R.id;
import com.example.android.navigationdrawerexample.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;  
import android.widget.ExpandableListView.OnGroupExpandListener;  

public class ExpandableListViewActivity extends Activity{
    int ans = 0;
    int flag = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setmeal);
    
        /**BaseExpandableListAdapter瀹炵幇浜咵xpandableListAdapter*/
        final ExpandableListAdapter adapter = new BaseExpandableListAdapter(){
            private String[] types = new String[]{"鏃╅","鍗堥","鏅氶"};      
            private List<List<Recipe>> recipeLists = RecipeMenu.getRecipe(ExpandableListViewActivity.this);   
/*===========缁勫厓绱犺〃绀哄彲鎶樺彔鐨勫垪琛ㄩ」锛屽瓙鍏冪礌琛ㄧず鍒楄〃椤瑰睍寮�悗鐪嬪埌鐨勫涓瓙鍏冪礌椤�============*/

/**----------寰楀埌types鍜宎rms涓瘡涓�釜鍏冪礌鐨処D-------------------------------------------*/
           
            //鑾峰彇缁勫湪缁欏畾鐨勪綅缃紪鍙凤紝鍗硉ypes涓厓绱犵殑ID
            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }
        
            //鑾峰彇鍦ㄧ粰瀹氱殑缁勭殑鍎跨鐨処D锛屽氨鏄痑rms涓厓绱犵殑ID
            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }
            
/**----------鏍规嵁涓婇潰寰楀埌鐨処D鐨勫�锛屾潵寰楀埌types鍜宎rms涓厓绱犵殑涓暟 ------------------------*/
            
            //鑾峰彇鐨勭兢浣撴暟閲忥紝寰楀埌types閲屽厓绱犵殑涓暟
            @Override
            public int getGroupCount() {
                return types.length;
            }
            
            //鍙栧緱鎸囧畾缁勪腑鐨勫効绔ヤ汉鏁帮紝灏辨槸types涓瘡涓�釜绉嶆棌瀹冨啗绉嶇殑涓暟
            @Override
            public int getChildrenCount(int groupPosition) {
                return recipeLists.get(groupPosition).size();
            }
            
/**----------鍒╃敤涓婇潰getGroupId寰楀埌ID锛屼粠鑰屾牴鎹甀D寰楀埌types涓殑鏁版嵁锛屽苟濉埌TextView涓�-----*/
            
            //鑾峰彇涓庣粰瀹氱殑缁勭浉鍏崇殑鏁版嵁锛屽緱鍒版暟缁則ypes涓厓绱犵殑鏁版嵁
            @Override
            public Object getGroup(int groupPosition) {
                return types[groupPosition];
            }

            //鑾峰彇涓�釜瑙嗗浘鏄剧ず缁欏畾缁勶紝瀛樻斁types
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                    View convertView, ViewGroup parent) {
            	LayoutInflater inflater = (LayoutInflater) ExpandableListViewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.group,null);
                TextView textView = (TextView) convertView.findViewById(R.id.tv_group);//璋冪敤瀹氫箟鐨刧etTextView()鏂规硶
                textView.setText(types[groupPosition]);//娣诲姞鏁版嵁
                return textView;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return recipeLists.get(groupPosition).get(childPosition);
            }
            
            class ViewHolder{
                private TextView recipeName;
                private TextView kaluli;
                private CheckBox followCheckBox;
            }
            
            
            
            //鑾峰彇涓�釜瑙嗗浘鏄剧ず鍦ㄧ粰瀹氱殑缁�鐨勫効绔ョ殑鏁版嵁锛屽氨鏄瓨鏀綼rms
            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                    View convertView, ViewGroup parent) {
            	 ViewHolder viewHolder = null;
                 Recipe recipe = recipeLists.get(groupPosition).get(childPosition);

                 if(null ==convertView){
                   
                     LayoutInflater inflater = (LayoutInflater) ExpandableListViewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                     convertView = inflater.inflate(R.layout.child,null);

                     viewHolder = new ViewHolder();
                     viewHolder.recipeName = (TextView) convertView.findViewById(R.id.tv_child);
                     viewHolder.kaluli = (TextView) convertView.findViewById(R.id.kaluli_child);
                     viewHolder.followCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox_kaluli);
                     final ViewHolder finalViewHolder = viewHolder;
                     viewHolder.followCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                         @Override
                         public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                             Recipe info = (Recipe) finalViewHolder.followCheckBox.getTag();
                             info.setSelected(compoundButton.isChecked());
                         }
                     });
                     convertView.setTag(viewHolder);
                     viewHolder.followCheckBox.setTag(recipe);
                    
                 }else {
                    
                     viewHolder = (ViewHolder) convertView.getTag();
                     viewHolder.followCheckBox.setTag(recipe);
                 }

                 viewHolder.recipeName.setText(recipe.getRecipeName());
                 viewHolder.kaluli.setText("" + recipe.getKaluli());
                 viewHolder.followCheckBox.setChecked(recipe.isSelected());

                 return convertView;
            }
           
            
/**-------------------鍏朵粬璁剧疆-------------------------------------------------------------------*/
            
            //瀛╁瓙鍦ㄦ寚瀹氱殑浣嶇疆鏄彲閫夌殑锛屽嵆锛歛rms涓殑鍏冪礌鏄彲鐐瑰嚮鐨�
            @Override
            public boolean isChildSelectable(int groupPosition,
                    int childPosition) {
                return true;
            }

            //琛ㄧず瀛╁瓙鏄惁鍜岀粍ID鏄法鍩虹鏁版嵁鐨勬洿鏀圭ǔ瀹�
            public boolean hasStableIds() {
                return true;
            }
            
            
        };
        
         /**浣跨敤閫傞厤鍣**/
         final ExpandableListView expandableListView = (ExpandableListView) this.findViewById(R.id.list);
         expandableListView.setAdapter(adapter);
            

    		//鍙睍寮�竴涓猤roup鐨勫疄鐜版柟娉�
         expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {  
        	  
             @Override  
             public void onGroupExpand(int groupPosition) {  
                 // TODO Auto-generated method stub  
                 for (int i = 0; i < adapter.getGroupCount(); i++) {  
                     if (groupPosition != i) {  
                    	 expandableListView.collapseGroup(i);  
                     }  
                 }  
   
             }  
   
         }); 
    }
    
    public void onClick(View view) {
    	AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("鍋ュ椋熷箍");
        String msg = "椋熺墿鎬诲叡鍗¤矾閲屼负锛� + ans";
        builder.setMessage(msg);
        builder.setPositiveButton("纭", null);
        builder.create().show();
    }
  
    public void onClick_linear(View view) {
    	CheckBox check = (CheckBox) view.findViewById(R.id.checkBox_kaluli);
		TextView text = (TextView) view.findViewById(R.id.kaluli_child);
		if (check.isChecked()) {
			check.setChecked(check.isChecked()?false:true);
			ans -= Integer.parseInt(text.getText().toString());
		}
		else{
			check.setChecked(check.isChecked()?false:true);
			ans += Integer.parseInt(text.getText().toString());
		}
		
    }  
}
