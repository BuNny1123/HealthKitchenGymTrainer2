package com.bunny.healthkitchengymtrainer.Model;

public class UserGym {
    private String name;
    private String address;
    private String contact_number;
    private String city;

    public UserGym(){

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

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "UserGym{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public UserGym(String name, String address, String contact_number, String city) {

        this.name = name;
        this.address = address;
        this.contact_number = contact_number;
        this.city = city;
    }
}
