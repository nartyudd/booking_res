package com.example.booking_res.viewmodels;

public class Item {
    private String id;
    private String name;
    private int priority;

    public Item(String id, String name, int priority) {
        this.setId(id);
        this.setName(name);
        this.setPriority(priority);
    }
    public Item(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
