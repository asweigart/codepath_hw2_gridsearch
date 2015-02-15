package com.al.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Model for a single image result.
 */
public class ImageResult implements Parcelable
{
    public String fullUrl;
    public String thumbUrl;
    public String title;
    public int width;
    public int height;

    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.width = json.getInt("width");
            this.height = json.getInt("height");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray jsonArr) {
        // Static factory method to create ImageResult objects from JSONArray, and return them in an ArrayList.
        ArrayList<ImageResult> results = new ArrayList();
        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                results.add(new ImageResult(jsonArr.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullUrl);
        dest.writeString(this.thumbUrl);
        dest.writeString(this.title);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }
    @SuppressWarnings("unused")
    public static final Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
        @Override
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }

        @Override
        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };

    private ImageResult(Parcel source) {
        this.fullUrl = source.readString();
        this.thumbUrl = source.readString();
        this.title = source.readString();
        this.width = source.readInt();
        this.height = source.readInt();
    }
}
