package com.example.android.meal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.android.navigationdrawerexample.R;
import com.example.android.navigationdrawerexample.R.id;
import com.example.android.navigationdrawerexample.R.layout;

public class RecipeAdapter extends BaseAdapter{

    private Context context;
    private List<Recipe> recipeList;
    private final String TAG = "disorderlist";

    public RecipeAdapter(Context context, List<Recipe> list){

        this.context = context;
        recipeList = list;

    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null != recipeList?recipeList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        private TextView recipeName;
        private TextView kaluli;
        private CheckBox followCheckBox;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        Recipe recipe = (Recipe) getItem(i);

        if(null == view){
          
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child,null);

            viewHolder = new ViewHolder();
            viewHolder.recipeName = (TextView) view.findViewById(R.id.tv_child);
            viewHolder.kaluli = (TextView) view.findViewById(R.id.kaluli_child);
            viewHolder.followCheckBox = (CheckBox) view.findViewById(R.id.checkBox_kaluli);
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.followCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Recipe info = (Recipe) finalViewHolder.followCheckBox.getTag();
                    info.setSelected(compoundButton.isChecked());
                }
            });
            view.setTag(viewHolder);
            viewHolder.followCheckBox.setTag(recipe);
           
        }else {
           
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.followCheckBox.setTag(recipe);
        }

        viewHolder.recipeName.setText(recipe.getRecipeName());
        viewHolder.kaluli.setText("" + recipe.getKaluli());
        viewHolder.followCheckBox.setChecked(recipe.isSelected());

        return view;
    }

}
