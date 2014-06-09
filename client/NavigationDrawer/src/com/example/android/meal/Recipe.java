package com.example.android.meal;

public class Recipe {

    private String recipeName;
    //    private String brandImage;
    private int kaluli;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setKaluli(int kaluli) {
        this.kaluli = kaluli;
    }
    
    public int getKaluli() {
        return kaluli;
    }

}
