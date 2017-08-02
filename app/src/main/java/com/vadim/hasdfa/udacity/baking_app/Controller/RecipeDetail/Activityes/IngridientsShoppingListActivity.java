package com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Activityes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.vadim.hasdfa.udacity.baking_app.Controller.SavedHelpers.AppCompatSavedActivity;
import com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments.IngridientsFragment;
import com.vadim.hasdfa.udacity.baking_app.R;

/**
 * Created by Raksha Vadim on 02.08.17, 11:55.
 */

public class IngridientsShoppingListActivity extends AppCompatSavedActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingridients_fragment_activity);

        if (savedInstanceState == null) {
            Fragment fragment = new IngridientsFragment();

            Bundle args = getIntent().getExtras();
            fragment.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detailFrame, fragment)
                    .commit();
        }
    }
}
