package com.vadim.hasdfa.udacity.baking_app.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raksha Vadim on 01.08.17, 20:47.
 */

public class Ingredient implements Parcelable {
    private int quantity;
    private String measure;
    private String ingredientName;
    private boolean isChecked;
    /*
    "ingredients": [
      {
        "quantity": 2,
        "measure": "CUP",
        "ingredient": "Graham Cracker crumbs"
      }
    ],
     */

    public Ingredient(){

    }

    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredientName = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return ingredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredientName);
    }
}
