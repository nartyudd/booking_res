package com.example.booking_res.model;

public class Category {

    private String uuid;
    private String name;
    private String uriImage;

    public Category(String uuid, String name, String uriImage){
        this.setUuid(uuid);
        this.setName(name);
        this.setUriImage(uriImage);
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

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }
}
