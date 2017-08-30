package com.msemyak.carsdb.model;

public class Car {

    private int id;
    private int owner_id;
    private String brand;
    private String model;
    private String year;
    private String regnumber;

    public Car() {
    }

    public Car(int id, int owner_id, String brand, String model, String year, String regnumber) {
        this.id = id;
        this.owner_id = owner_id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.regnumber = regnumber;
    }

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(int owner_id) {
        this.owner_id = owner_id;
    }
}
