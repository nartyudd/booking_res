package com.example.booking_res.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Table implements Parcelable {
    private String uuid;
    private String name;
    private boolean status;

    public Table(String uuid, String name, boolean status){
        this.setUuid(uuid);
        this.setName(name);
        this.setStatus(status);
    }

    public Table(String uuid, String name){
        this.setUuid(uuid);
        this.setName(name);
        this.setStatus(false);
    }

    public Table(){}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(status);
        }
    }

    protected Table(Parcel in) {
        uuid = in.readString();
        name = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            status = in.readBoolean();
        }
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

}
