package com.vadim.hasdfa.udacity.baking_app.Controller.SelectRecipe;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vadim.hasdfa.udacity.baking_app.Controller.SavedHelpers.AppCompatSavedActivity;
import com.vadim.hasdfa.udacity.baking_app.Model.NetworkUtils.NetworkController;
import com.vadim.hasdfa.udacity.baking_app.Model.NetworkUtils.OnLoadListener;
import com.vadim.hasdfa.udacity.baking_app.Model.Recipe;
import com.vadim.hasdfa.udacity.baking_app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatSavedActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    SelectRecipeAdapter mAdapter;
    private GridLayoutManager glm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        if (findViewById(R.id.isTablet) != null) {
            glm = new GridLayoutManager(this, 3);
        } else {
            glm = new GridLayoutManager(this, 1);
        }

        if (savedInstanceState != null) {
            glm.onRestoreInstanceState(savedInstanceState.getParcelable("glm"));
        }

        mRecyclerView.setLayoutManager(glm);

        mAdapter = new SelectRecipeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        NetworkController.load(new OnLoadListener() {
            @Override
            public void onLoadFinished(ArrayList<Recipe> recipes) {
                mAdapter.notifyDataSetChanged(recipes);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("glm", glm.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        glm.onRestoreInstanceState(savedInstanceState.getParcelable("glm"));
    }
}
