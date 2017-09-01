package com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

        IngredientsAdapter mAdapter = new IngredientsAdapter();
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
//        //Adding shortcut for MainActivity
//        //on Home screen
//        Intent shortcutIntent = new Intent(
//                getActivity().getApplicationContext(),
//                IngridientsShoppingListActivity.class);
//
//        shortcutIntent.setAction(Intent.ACTION_MAIN);
//        shortcutIntent.setAction(Intent.CATEGORY_LAUNCHER);
//
//        Intent addIntent = new Intent();
//        addIntent
//                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Ingredients");
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
//                Intent.ShortcutIconResource.fromContext(getActivity().getApplicationContext(),
//                        R.mipmap.ic_launcher_round));
//        addIntent
//                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//        getActivity().getApplicationContext().sendBroadcast(addIntent);
//    }

    class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
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
        public void onBindViewHolder(final ViewHolder holder, int position) {
            position -= 1;
            Log.d("myLog", "getItemCount(): " + getItemCount());
            if (position == getItemCount()-2) {
                holder.cardView.setVisibility(View.GONE);
            } else if (position >= 0) {
                final Ingredient i = ingredients.get(position);
                holder.checkbox.setText(i.getIngredientName());
                holder.checkbox.setChecked(getChecked(i.getIngredientName()));
                holder.checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        i.setChecked(holder.checkbox.isChecked());
                        saveChecked(holder.checkbox.isChecked(), i.getIngredientName());
                    }
                });
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
//                holder.clickable.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        addShortcut();
//                    }
//                });
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

        private static final String sharedKEY = "checked_key";
        private void saveChecked(boolean isChecked, String title){
            String key = getKeyByTitle(currentRecipe.getName()+title);
            SharedPreferences sp = getContext().getSharedPreferences(sharedKEY, Context.MODE_PRIVATE);
            sp.edit().putBoolean(key, isChecked).apply();
        }

        private boolean getChecked(String title) {
            String key = getKeyByTitle(currentRecipe.getName()+title);
            SharedPreferences sp = getContext().getSharedPreferences(sharedKEY, Context.MODE_PRIVATE);
            return sp.getBoolean(key, false);
        }

        private String getKeyByTitle(String title){
            String key = "";
            for (char i: title.toCharArray()) {
                key += (int) i;
            }
            return key;
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
