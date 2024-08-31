package com.example.booking_res.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Restaurant {
    private String uuid;
    private String name;
    private String address;
    private float rating;
    private String uriImage;
    private String userId;
    private boolean active;

    public Restaurant(){}

    public Restaurant(String uuid, String name, String address, float rating, String uriImage, String userId){
        this.setUuid(uuid);
        this.setName(name);
        this.setAddress(address);
        this.setRating(rating);
        this.setUriImage(uriImage);
        this.setUserId(userId);
        this.setActive(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
