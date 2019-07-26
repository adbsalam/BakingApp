package com.salam.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients_Model implements Parcelable {

    String quantity;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Ingredients_Model(String quantity, String measure, String ingredients) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }

    String measure;
    String ingredients;

    protected Ingredients_Model(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredients = in.readString();
    }

    public static final Creator<Ingredients_Model> CREATOR = new Creator<Ingredients_Model>() {
        @Override
        public Ingredients_Model createFromParcel(Parcel in) {
            return new Ingredients_Model(in);
        }

        @Override
        public Ingredients_Model[] newArray(int size) {
            return new Ingredients_Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredients);
    }
}
