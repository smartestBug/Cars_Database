package com.msemyak.carsdb.data.model;

public class Owner {

    // объект владельца
    // id
    private int id;
    // имя
    private String name;
    // отчество
    private String midname;
    // фамилия
    private String surname;
    // номер паспорта
    private String passport;
    // адрес
    private String address;

    public Owner() {
    }

    public Owner(int id, String name, String midname, String surname, String passport, String address) {
        this.id = id;
        this.name = name;
        this.midname = midname;
        this.surname = surname;
        this.passport = passport;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
