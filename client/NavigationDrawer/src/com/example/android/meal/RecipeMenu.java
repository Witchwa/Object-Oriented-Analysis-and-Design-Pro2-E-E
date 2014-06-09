package com.example.android.meal;

import java.io.*; 
import java.util.ArrayList;
import java.util.List; 

import com.example.android.navigationdrawerexample.R;
import com.example.android.navigationdrawerexample.R.raw;

import android.content.Context;
import android.util.Log;
 

public class RecipeMenu
{
	public static List<List<Recipe>> getRecipe(Context context)
    {
        List<Recipe> foodList = new ArrayList<Recipe>();
        List<Recipe> foodList2 = new ArrayList<Recipe>();
        List<Recipe> foodList3= new ArrayList<Recipe>();
        List<List<Recipe>> menu = new ArrayList<List<Recipe>>();
        Recipe food = null;
        
        try {
        InputStream inputStream = context.getResources().openRawResource(R.raw.breakfast);
    	InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8"); ; 
    	BufferedReader br = new BufferedReader(inputStreamReader);
    	String line = "";
    	
    	while((line = br.readLine())!=null){ 
    		String info[] = line.split(" "); 
    		food = new Recipe();
    		food.setRecipeName(info[0]);
    		food.setKaluli(Integer.parseInt(info[1]));
    		food.setSelected(false);
            foodList.add(food);
            Log.v("menu", food.getRecipeName());
    	}
    	br.close();
    	
    	InputStream inputStream2 = context.getResources().openRawResource(R.raw.lunch);
    	InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream2, "utf-8"); ; 
    	BufferedReader br2 = new BufferedReader(inputStreamReader2);
    	line = "";
    	
    	while((line = br2.readLine())!=null){ 
    		String info[] = line.split(" "); 
    		food = new Recipe();
    		food.setRecipeName(info[0]);
    		food.setKaluli(Integer.parseInt(info[1]));
    		food.setSelected(false);
            foodList2.add(food);
            Log.v("menu", food.getRecipeName());
    	}
    	br2.close();
    	
    	InputStream inputStream3 = context.getResources().openRawResource(R.raw.dinner);
    	InputStreamReader inputStreamReader3 = new InputStreamReader(inputStream3, "utf-8"); ; 
    	BufferedReader br3 = new BufferedReader(inputStreamReader3);
    	line = "";
    	
    	while((line = br3.readLine())!=null){ 
    		String info[] = line.split(" "); 
    		food = new Recipe();
    		food.setRecipeName(info[0]);
    		food.setKaluli(Integer.parseInt(info[1]));
    		food.setSelected(false);
            foodList3.add(food);
            Log.v("menu", food.getRecipeName());
    	}
    	br3.close();
        }
        catch(Exception e) 
        {  
            e.printStackTrace();  
        }
    	menu.add(foodList);
    	menu.add(foodList2);
    	menu.add(foodList3);
        return menu;
    }
}
