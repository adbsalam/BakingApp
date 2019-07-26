package com.salam.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class InstructionsModel implements Parcelable {
    String id;
    String description;
    String videoURL;
    String thumbnailURL;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    protected InstructionsModel(Parcel in) {
        id = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InstructionsModel> CREATOR = new Creator<InstructionsModel>() {
        @Override
        public InstructionsModel createFromParcel(Parcel in) {
            return new InstructionsModel(in);
        }

        @Override
        public InstructionsModel[] newArray(int size) {
            return new InstructionsModel[size];
        }
    };
}
