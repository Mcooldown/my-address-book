package com.example.myaddressbook_vincenthadinata.retrofit;

import com.example.myaddressbook_vincenthadinata.models.GetResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetEmployeeInterface {

    @GET("?nim=2301850430&nama=Vincent%20Hadinata")
    Call<GetResponseModel> getAllEmployee();

    @GET("{id}/?nim=2301850430&nama=Vincent%20Hadinata")
    Call<GetResponseModel> getEmployeeDetail(@Path("id") int id);
}
