package com.example.booking_res.model;

public class Table {
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

}
