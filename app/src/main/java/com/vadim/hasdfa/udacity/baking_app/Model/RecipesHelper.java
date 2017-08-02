package com.vadim.hasdfa.udacity.baking_app.Model;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Raksha Vadim on 01.08.17, 21:28.
 */

public class RecipesHelper {
    // Some kind of Singleton
    private RecipesHelper() {}
    private static RecipesHelper sharedInstance;
    public static RecipesHelper shared(){
        if (sharedInstance == null) {
            sharedInstance = new RecipesHelper();
        }
        return sharedInstance;
    }

    private ArrayList<Recipe> recipes = new ArrayList<>();

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }



    private static final String key = "RecipesHelperSavedArray";
    public static void onSavedInstance(Bundle outSate) {
        if (outSate != null)
            outSate.putParcelableArrayList(key, shared().recipes);
    }
    public static void onRestoreInstance(Bundle savedInstance) {
        if (savedInstance != null) {
            ArrayList<Recipe> restoreRecipes = savedInstance.getParcelableArrayList(key);
            if (restoreRecipes != null)
                shared().recipes = restoreRecipes;
        }
    }
}
