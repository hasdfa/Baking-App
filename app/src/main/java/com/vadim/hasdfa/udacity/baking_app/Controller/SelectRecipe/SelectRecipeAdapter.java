package com.vadim.hasdfa.udacity.baking_app.Controller.SelectRecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Activityes.RecipeViewActivity;
import com.vadim.hasdfa.udacity.baking_app.Model.Recipe;
import com.vadim.hasdfa.udacity.baking_app.R;

import java.util.ArrayList;

/**
 * Created by Raksha Vadim on 01.08.17, 20:37.
 */

public class SelectRecipeAdapter extends RecyclerView.Adapter<SelectRecipeAdapter.ViewHolder> {
    private ArrayList<Recipe> recipes;

    private Context mContext;
    SelectRecipeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe r = recipes.get(position);
        holder.title.setText(r.getName());
        holder.servings.setText(""+r.getServings());

        if (!r.getImage().isEmpty()) {
            Picasso.with(holder.thumbnail.getContext())
                    .load(r.getImage())
                    .into(holder.thumbnail);
        }

        final int item = position;
        holder.clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, RecipeViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selectedR", item);
                i.putExtras(bundle);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }


    void notifyDataSetChanged(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView servings;
        View clickable;
        ImageView thumbnail;
        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            servings = (TextView) itemView.findViewById(R.id.servings_count);
            clickable = itemView.findViewById(R.id.clickable);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
