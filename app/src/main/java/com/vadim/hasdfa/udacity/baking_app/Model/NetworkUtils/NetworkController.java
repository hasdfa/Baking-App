package com.vadim.hasdfa.udacity.baking_app.Model.NetworkUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.vadim.hasdfa.udacity.baking_app.Model.Ingredient;
import com.vadim.hasdfa.udacity.baking_app.Model.Recipe;
import com.vadim.hasdfa.udacity.baking_app.Model.RecipesHelper;
import com.vadim.hasdfa.udacity.baking_app.Model.Step;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Raksha Vadim on 01.08.17, 20:44.
 */

public class NetworkController extends AsyncTask<Void, Void, ArrayList<Recipe>> {
    private NetworkController(){}


    private OnLoadListener mListener;
    public static void load(OnLoadListener mListener) {
        NetworkController nc = new NetworkController();
        nc.mListener = mListener;
        if (RecipesHelper.shared().getRecipes().size() == 0) {
            nc.execute();
        } else {
            if (mListener != null) {
                mListener.onLoadFinished(RecipesHelper.shared().getRecipes());
            }
        }
    }

    @Override
    protected ArrayList<Recipe> doInBackground(Void... voids) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String json = null;
        try {
            URL url = new URL(
                    "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json"
            );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try {
                InputStream is = connection.getInputStream();

                Scanner scanner = new Scanner(is);

                while (scanner.hasNext()) {
                    if (scanner.hasNext()) {
                        String next = scanner.nextLine();
                        if (next != null)
                            json += next;
                    }
                }
            } finally {
                connection.disconnect();
            }
            if (json != null) {
                json = json.replace("null", "");
            }

            Log.d("myLog", "JSON: " + json);
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                Recipe r = new Recipe();
                r.setId(o.getInt("id"));
                r.setName(o.getString("name"));
                r.setServings(o.getInt("servings"));
                r.setImage(o.getString("image"));

                JSONArray ingredients = o.getJSONArray("ingredients");
                for (int j = 0; j < ingredients.length(); j++) {
                    JSONObject iO = ingredients.getJSONObject(j);
                    Ingredient ingr = new Ingredient();
                    ingr.setQuantity(iO.getInt("quantity"));
                    ingr.setMeasure(iO.getString("measure"));
                    ingr.setIngredientName(iO.getString("ingredient"));

                    r.getIngredients().add(ingr);
                }

                JSONArray steps = o.getJSONArray("steps");
                for (int j = 0; j < steps.length(); j++) {
                    JSONObject sO = steps.getJSONObject(j);
                    Step s = new Step();
                    s.setId(sO.getInt("id"));
                    s.setShortDescription(sO.getString("shortDescription"));
                    s.setDescription(sO.getString("description"));
                    s.setVideoUrl(sO.getString("videoURL"));
                    s.setThumbnailUrl(sO.getString("thumbnailURL"));

                    r.getSteps().add(s);
                }

                recipes.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }
    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        RecipesHelper.shared().setRecipes(recipes);
        Log.d("myLog", recipes.toString());
        if (mListener != null) {
            mListener.onLoadFinished(recipes);
        }
    }
}
