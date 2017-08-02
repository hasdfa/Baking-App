package com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vadim.hasdfa.udacity.baking_app.Controller.SavedHelpers.SavedFragment;
import com.vadim.hasdfa.udacity.baking_app.Model.Recipe;
import com.vadim.hasdfa.udacity.baking_app.Model.RecipesHelper;
import com.vadim.hasdfa.udacity.baking_app.Model.Step;
import com.vadim.hasdfa.udacity.baking_app.R;

/**
 * Created by Raksha Vadim on 01.08.17, 19:53.
 */

public class RecipeStepList extends SavedFragment {
    private Recipe recipe;

    OnSelectRecipe mCallback = null;


    public interface OnSelectRecipe {
        void onItemSelected(View view, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mCallback == null) {
            try {
                mCallback = (OnSelectRecipe) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + " must implement OnImageClickListener. Or mCallback must be set by setOnImageClickListener(OnImageClickListener)");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recycler_view, container, false);

        RecyclerView mRecylerView = view.findViewById(R.id.mRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(llm);

        recipe = RecipesHelper.shared().getRecipes().get(getArguments().getInt("selectedR"));
        RecipeStepListAdapter mAdapter = new RecipeStepListAdapter();
        mRecylerView.setAdapter(mAdapter);

        return view;
    }


    class RecipeStepListAdapter extends RecyclerView.Adapter<RecipeStepListAdapter.ViewHolder> {
        @Override
        public RecipeStepListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecipeStepListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.text_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecipeStepListAdapter.ViewHolder holder, int position) {
            position -= 1;
            if (position >= 0) {
                final Step step = recipe.getSteps().get(position);
                holder.title.setText(step.getShortDescription());
            } else {
                holder.title.setText("Ingredients");
            }
            final int i = position;
            holder.clickable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onItemSelected(view, i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return recipe.getSteps() == null ? 0 : recipe.getSteps().size() + 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            View clickable;
            ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                clickable = itemView.findViewById(R.id.clickable);
            }
        }
    }
}
