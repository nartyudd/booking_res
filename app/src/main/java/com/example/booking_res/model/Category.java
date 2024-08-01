package com.example.booking_res.model;

public class Category {

    private String uuid;
    private String name;
    private String idRestaurant;

    public Category(String uuid, String name, String idRestaurant){
        this.setUuid(uuid);
        this.setName(name);
        this.setIdRestaurant(idRestaurant);
    }

    public Category(){}

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

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
}
