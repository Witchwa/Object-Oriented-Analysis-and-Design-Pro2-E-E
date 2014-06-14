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
    
        /**BaseExpandableListAdapter实现了ExpandableListAdapter*/
        final ExpandableListAdapter adapter = new BaseExpandableListAdapter(){
            private String[] types = new String[]{"早餐","午餐","晚餐"};      
            private List<List<Recipe>> recipeLists = RecipeMenu.getRecipe(ExpandableListViewActivity.this);   
/*===========组元素表示可折叠的列表项，子元素表示列表项展开后看到的多个子元素项=============*/

/**----------得到types和arms中每一个元素的ID-------------------------------------------*/
           
            //获取组在给定的位置编号，即types中元素的ID
            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }
        
            //获取在给定的组的儿童的ID，就是arms中元素的ID
            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }
            
/**----------根据上面得到的ID的值，来得到types和arms中元素的个数 ------------------------*/
            
            //获取的群体数量，得到types里元素的个数
            @Override
            public int getGroupCount() {
                return types.length;
            }
            
            //取得指定组中的儿童人数，就是types中每一个种族它军种的个数
            @Override
            public int getChildrenCount(int groupPosition) {
                return recipeLists.get(groupPosition).size();
            }
            
/**----------利用上面getGroupId得到ID，从而根据ID得到types中的数据，并填到TextView中 -----*/
            
            //获取与给定的组相关的数据，得到数组types中元素的数据
            @Override
            public Object getGroup(int groupPosition) {
                return types[groupPosition];
            }

            //获取一个视图显示给定组，存放types
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                    View convertView, ViewGroup parent) {
            	LayoutInflater inflater = (LayoutInflater) ExpandableListViewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.group,null);
                TextView textView = (TextView) convertView.findViewById(R.id.tv_group);//调用定义的getTextView()方法
                textView.setText(types[groupPosition]);//添加数据
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
            
            
            
            //获取一个视图显示在给定的组 的儿童的数据，就是存放arms
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
           
            
/**-------------------其他设置-------------------------------------------------------------------*/
            
            //孩子在指定的位置是可选的，即：arms中的元素是可点击的
            @Override
            public boolean isChildSelectable(int groupPosition,
                    int childPosition) {
                return true;
            }

            //表示孩子是否和组ID是跨基础数据的更改稳定
            public boolean hasStableIds() {
                return true;
            }
            
            
        };
        
            /**使用适配器*/
         final ExpandableListView expandableListView = (ExpandableListView) this.findViewById(R.id.list);
         expandableListView.setAdapter(adapter);
            

    		//只展开一个group的实现方法
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
        builder.setTitle("健多食广");
        String msg = "食物总共卡路里为：" + ans;
        builder.setMessage(msg);
        builder.setPositiveButton("确认", null);
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
