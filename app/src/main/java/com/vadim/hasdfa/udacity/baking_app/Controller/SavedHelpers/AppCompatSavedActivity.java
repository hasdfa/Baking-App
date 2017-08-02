package com.vadim.hasdfa.udacity.baking_app.Controller.SavedHelpers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.vadim.hasdfa.udacity.baking_app.Model.RecipesHelper;

/**
 * Created by Raksha Vadim on 02.08.17, 14:29.
 */

public class AppCompatSavedActivity extends AppCompatActivity {

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        RecipesHelper.onSavedInstance(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        RecipesHelper.onRestoreInstance(savedInstanceState);
    }
}
