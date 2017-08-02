package com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vadim.hasdfa.udacity.baking_app.Controller.SavedHelpers.SavedFragment;
import com.vadim.hasdfa.udacity.baking_app.Model.Ingredient;
import com.vadim.hasdfa.udacity.baking_app.Model.Recipe;
import com.vadim.hasdfa.udacity.baking_app.Model.RecipesHelper;
import com.vadim.hasdfa.udacity.baking_app.R;

import java.util.ArrayList;

/**
 * Created by Raksha Vadim on 02.08.17, 00:56.
 */

public class IngridientsFragment extends SavedFragment {
    private RecipeDetail.OnButtonPressed mCallback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mCallback == null) {
            try {
                mCallback = (RecipeDetail.OnButtonPressed) context;
            } catch (Exception e) {
                e.printStackTrace();
                mCallback = null;
            }
        }
    }

    ArrayList<Ingredient> ingredients;
    Recipe currentRecipe;

    RecyclerView mRecyclerView;

    int selectedR;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingridients_fragment, container, false);

        selectedR = getArguments().getInt("selectedR");
        currentRecipe = RecipesHelper.shared()
                .getRecipes()
                .get(selectedR);
        ingredients = currentRecipe
                .getIngredients();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);

        IngridientsAdapter mAdapter = new IngridientsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        Button nextButton = view.findViewById(R.id.next_button);
        Button previousButton = view.findViewById(R.id.previous_button);
        if (nextButton != null
                && previousButton != null) {
            previousButton.setEnabled(false);
            nextButton.setEnabled(true);

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCallback != null) {
                        mCallback.onNext((Button) view);
                    }
                }
            });
        }

        return view;
    }


//    private void addShortcut() {
//        //where this is a context (e.g. your current activity)
//        final Intent shortcutIntent = new Intent(getContext(), IngridientsShoppingListActivity.class);
//
//        final Intent intent = new Intent();
//        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
//        // Sets the custom shortcut's title
//        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "TEST");
//        // Set the custom shortcut icon
//        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getContext(), R.drawable.ic_format_list));
//        // add the shortcut
//        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//        getContext().sendBroadcast(intent);
//    }

    class IngridientsAdapter extends RecyclerView.Adapter<IngridientsAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0 || viewType == getItemCount()-1) {
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.text_item, parent, false)
                );
            }
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.ingridient_list_item, parent, false)
            );
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            position -= 1;
            Log.d("myLog", "getItemCount(): " + getItemCount());
            if (position == getItemCount()-2) {
                holder.cardView.setVisibility(View.GONE);
            } else if (position >= 0) {
                Ingredient i = ingredients.get(position);
                holder.checkbox.setText(i.getIngredientName());
                int q = i.getQuantity();
                holder.quntity.setText(""+q);
                String measure;
                if (q > 1) {
                    measure = getMeasure(i.getMeasure());
                } else {
                    measure = i.getMeasure();
                }
                holder.measure.setText("\t\t" + measure);
            } else {
                holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
                holder.title.setText("Ingredients list");
                holder.title.setTextColor(Color.WHITE);
                holder.title.setTextSize(21f);
                Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/PetitFormalScript-Regular.ttf");
                holder.title.setTypeface(type);
                holder.clickable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: add shopping list to homescreen
                        // How can i do that?
                    }
                });
            }
        }

        private String getMeasure(String measure) {
            int lenght = measure.length();
            if (lenght == 1)
                return measure;
            char lastLetter = measure.charAt(lenght-1);
            if (lastLetter == ('a' | 'e' | 'i' | 'o' | 'u')) {
                return measure + "s";
            }
            return measure + "es";
        }

        @Override
        public int getItemCount() {
            return ingredients == null ? 0 : ingredients.size() + 2;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkbox;
            TextView measure;
            TextView quntity;

            TextView title;
            View clickable;
            CardView cardView;
            public ViewHolder(View itemView) {
                super(itemView);
                checkbox = (CheckBox) itemView.findViewById(R.id.checkbox_title_view);
                measure = (TextView) itemView.findViewById(R.id.measure_text_view);
                quntity = (TextView) itemView.findViewById(R.id.quantity_text_view);

                title = (TextView) itemView.findViewById(R.id.title);
                clickable = itemView.findViewById(R.id.clickable);
                cardView = (CardView) itemView.findViewById(R.id.cardView);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedR", selectedR);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            selectedR = savedInstanceState.getInt("selectedR");
    }
}
