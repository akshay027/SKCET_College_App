package com.example.exalogic.mobioticsapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class VideoList implements Serializable {


    String description, id, title, url, thumb;

    public VideoList(String description, String id, String title, String url, String thumb) {
        this.description = description;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }


}