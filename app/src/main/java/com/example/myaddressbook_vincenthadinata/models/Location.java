package com.example.myaddressbook_vincenthadinata.models;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("coordinates")
    private Coordinates coordinates;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
