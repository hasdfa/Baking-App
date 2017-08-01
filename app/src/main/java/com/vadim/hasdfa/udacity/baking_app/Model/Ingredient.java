package com.vadim.hasdfa.udacity.baking_app.Model;

/**
 * Created by Raksha Vadim on 01.08.17, 20:47.
 */

public class Ingredient {
    private int quantity;
    private String measure;
    private String ingredientName;
    /*
    "ingredients": [
      {
        "quantity": 2,
        "measure": "CUP",
        "ingredient": "Graham Cracker crumbs"
      }
    ],
     */

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return ingredientName;
    }
}
