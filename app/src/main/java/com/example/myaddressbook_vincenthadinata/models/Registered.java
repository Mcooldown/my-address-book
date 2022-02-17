package com.example.myaddressbook_vincenthadinata.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Registered {

    @SerializedName("date")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
