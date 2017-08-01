package com.vadim.hasdfa.udacity.baking_app.Model;

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
}
