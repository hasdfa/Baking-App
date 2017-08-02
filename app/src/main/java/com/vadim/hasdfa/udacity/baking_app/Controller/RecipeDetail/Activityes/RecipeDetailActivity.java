package com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Activityes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments.IngridientsFragment;
import com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments.RecipeDetail;
import com.vadim.hasdfa.udacity.baking_app.Controller.SavedHelpers.AppCompatSavedActivity;
import com.vadim.hasdfa.udacity.baking_app.Model.RecipesHelper;
import com.vadim.hasdfa.udacity.baking_app.R;

/**
 * Created by Raksha Vadim on 02.08.17, 00:00.
 */

public class RecipeDetailActivity extends AppCompatSavedActivity implements RecipeDetail.OnButtonPressed {
    int selectedR;
    int selectedS = -2;
    boolean isToolbarHidden = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
            if (getActionBar() != null)
                getActionBar().hide();
            isToolbarHidden = true;
        }
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {
            selectedR = getIntent().getExtras().getInt("selectedR");
            selectedS = getIntent().getExtras().getInt("selectedS");
            setFragment();
        } else {
            selectedR = savedInstanceState.getInt("selectedR");
            selectedS = savedInstanceState.getInt("selectedS");
        }
        updateBar();
    }

    private void updateBar(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            String title;
            if (selectedS == -2) {
                title = "";
            } else if (selectedS == -1) {
                title = "Ingredients";
            } else {
                title = RecipesHelper.shared()
                        .getRecipes()
                        .get(selectedR)
                        .getSteps()
                        .get(selectedS)
                        .getShortDescription();
            }
            getSupportActionBar()
                    .setTitle(
                            title
                    );
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setFragment(){
        updateBar();
        Bundle toFragment = new Bundle();
        toFragment.putInt("selectedR", selectedR);
        toFragment.putInt("selectedS", selectedS);
        toFragment.putBoolean("isToolbarHidden", isToolbarHidden);
        Fragment fragment;
        if (selectedS >= 0){
            fragment = new RecipeDetail();
        } else {
            fragment = new IngridientsFragment();
        }

        fragment.setArguments(toFragment);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detailFrame, fragment)
                .commit();
    }

    @Override
    public void onPrevious(Button button) {
        selectedS--;
        setFragment();
    }

    @Override
    public void onNext(Button button) {
        selectedS++;
        setFragment();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("selectedR", selectedR);
        outState.putInt("selectedS", selectedS);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedR = savedInstanceState.getInt("selectedR");
        selectedS = savedInstanceState.getInt("selectedS");
        updateBar();
    }
}
