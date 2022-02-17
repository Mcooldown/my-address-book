package com.example.myaddressbook_vincenthadinata.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetEmployeeClient {

    public static Retrofit employeeClientInstance = null;
    public static final String BASE_URL = "https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/stage2/people/";

    public static Retrofit getEmployeeClientInstance(){

        if(employeeClientInstance == null){
            employeeClientInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return employeeClientInstance;
    }

}
