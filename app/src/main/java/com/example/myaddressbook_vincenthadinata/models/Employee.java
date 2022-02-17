package com.example.myaddressbook_vincenthadinata.models;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("employeeId")
    private Integer employeeId;

    @SerializedName("name")
    private Name name;

    @SerializedName("location")
    private Location location;

    @SerializedName("email")
    private String email;

    @SerializedName("registered")
    private Registered registered;

    @SerializedName("phone")
    private String phone;

    @SerializedName("cell")
    private String cell;

    @SerializedName("picture")
    private Picture picture;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name=" + name.getFirst() +
                ", location=" + location.toString() +
                ", email='" + email + '\'' +
                ", registered=" + registered.toString() +
                ", phone='" + phone + '\'' +
                ", cell='" + cell + '\'' +
                ", picture=" + picture.toString() +
                '}';
    }
}
