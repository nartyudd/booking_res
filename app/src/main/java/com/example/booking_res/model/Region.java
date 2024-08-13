package com.example.booking_res.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Region implements Parcelable {

    private String uuid;
    private String name;
    private String res_id;
    private int priority;
    private List<Table> tables;

    public Region(String uuid, String name, String res_id, int priority){
        this.setUuid(uuid);
        this.setName(name);
        this.setRes_id(res_id);
        this.setPriority(priority);
    }

    public Region(String uuid, String name, String res_id, int priority, List<Table> tables){
        this.setUuid(uuid);
        this.setName(name);
        this.setRes_id(res_id);
        this.setPriority(priority);
        this.setTables(tables);
    }
    public Region(){}

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

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }



    // implement parceLable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(name);
        parcel.writeString(res_id);
    }

    protected Region(Parcel in) {
        uuid = in.readString();
        name = in.readString();
        res_id = in.readString();
    }

    public static final Creator<Region> CREATOR = new Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel in) {
            return new Region(in);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
        }
    };

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
