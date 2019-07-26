package com.salam.bakingapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class ItemNames implements Parcelable {
    public ItemNames(String name, String id) {
        this.name = name;
        this.id = id;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String name;
    String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemNames(Parcel in) {

        id = in.readString();
        name = in.readString();


    }

    public static final Creator<ItemNames> CREATOR = new Creator<ItemNames>() {
        @Override
        public ItemNames createFromParcel(Parcel in) {
            return new ItemNames(in);
        }

        @Override
        public ItemNames[] newArray(int size) {
            return new ItemNames[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);


    }
}