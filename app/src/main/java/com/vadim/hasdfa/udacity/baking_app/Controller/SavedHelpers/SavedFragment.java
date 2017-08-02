package com.vadim.hasdfa.udacity.baking_app.Controller.SavedHelpers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.vadim.hasdfa.udacity.baking_app.Model.RecipesHelper;

/**
 * Created by Raksha Vadim on 02.08.17, 14:31.
 */

public class SavedFragment extends Fragment {
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        RecipesHelper.onSavedInstance(outState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipesHelper.onRestoreInstance(savedInstanceState);
    }
}
