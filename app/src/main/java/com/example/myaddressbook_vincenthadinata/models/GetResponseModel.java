package com.example.myaddressbook_vincenthadinata.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetResponseModel {

    @SerializedName("statusCode")
    private Integer statusCode;

    @SerializedName("nim")
    private String nim;

    @SerializedName("nama")
    private String nama;

    @SerializedName("employeeId")
    private String employeeId;

    @SerializedName("employees")
    private List<Employee> employees;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
