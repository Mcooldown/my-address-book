package com.example.myaddressbook_vincenthadinata.models;

import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("first")
    private String first;

    @SerializedName("last")
    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
