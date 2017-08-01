package com.vadim.hasdfa.udacity.baking_app.Model.NetworkUtils;

import com.vadim.hasdfa.udacity.baking_app.Model.Recipe;

import java.util.ArrayList;

/**
 * Created by Raksha Vadim on 01.08.17, 21:38.
 */

public interface OnLoadListener {
    void onLoadFinished(ArrayList<Recipe> recipes);
}
