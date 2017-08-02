package com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Activityes;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.vadim.hasdfa.udacity.baking_app.Controller.SavedHelpers.AppCompatSavedActivity;
import com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments.IngridientsFragment;
import com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments.RecipeDetail;
import com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments.RecipeStepList;
import com.vadim.hasdfa.udacity.baking_app.Model.RecipesHelper;
import com.vadim.hasdfa.udacity.baking_app.R;

/**
 * Created by Raksha Vadim on 01.08.17, 20:00.
 */

public class RecipeViewActivity extends AppCompatSavedActivity implements RecipeStepList.OnSelectRecipe {
    boolean isTwoPanel = false;

    int selectedR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Bundle toFragments = new Bundle();

        isTwoPanel = findViewById(R.id.detailFrame) != null;
        selectedR = getIntent().getExtras().getInt("selectedR");

        if (savedInstanceState == null) {
            toFragments.putInt("selectedR", selectedR);
            RecipeStepList fragment = new RecipeStepList();
            fragment.setArguments(toFragments);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.masterFragment, fragment)
                    .commit();

            if (isTwoPanel) {
                onItemSelected(null, -1);
                Fragment fragment2 = new IngridientsFragment();
                fragment2.setArguments(toFragments);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.detailFrame, fragment2)
                        .commit();
            }
        } else {
            selectedR = savedInstanceState.getInt("selectedR");
        }

        if (!isTwoPanel) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar()
                        .setTitle(RecipesHelper.shared()
                                .getRecipes()
                                .get(selectedR)
                                .getName()
                        );
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemSelected(View view, int position) {
        Bundle toSend = new Bundle();
        int r = getIntent().getExtras().getInt("selectedR");
        Log.d("myLog", "["+r+"]["+position+"]");
        toSend.putInt("selectedR", r);
        toSend.putInt("selectedS", position);

        if (isTwoPanel) {
            Fragment newFragment;
            if (position == -1) {
                newFragment = new IngridientsFragment();
            } else {
                newFragment = new RecipeDetail();
            }

            newFragment.setArguments(toSend);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailFrame, newFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtras(toSend);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("selectedR", selectedR);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedR = savedInstanceState.getInt("selectedR");
    }
}
