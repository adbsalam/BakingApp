package com.salam.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipieDetailModel implements Parcelable {
    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public RecipieDetailModel(String shortdescription, String id, String itemID) {
        this.shortdescription = shortdescription;
        this.id = id;
        this.itemID = itemID;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String shortdescription;
    String id;

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    String itemID;

    public RecipieDetailModel(Parcel in) {

        id = in.readString();
        shortdescription = in.readString();
        itemID = in.readString();
    }

    public static final Creator<RecipieDetailModel> CREATOR = new Creator<RecipieDetailModel>() {
        @Override
        public RecipieDetailModel createFromParcel(Parcel in) {
            return new RecipieDetailModel(in);
        }

        @Override
        public RecipieDetailModel[] newArray(int size) {
            return new RecipieDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shortdescription);
        dest.writeString(itemID);
    }
}