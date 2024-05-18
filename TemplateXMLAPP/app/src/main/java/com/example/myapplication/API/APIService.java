package com.example.myapplication.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {
    //public static final String BaseURL="http://192.168.2.13/ServerAndroid/";
    public static final String BaseURL="http://192.168.65.102/ServerAndroid/";
    //public static final String BaseURL="http://192.168.1.5/ServerAndroid/";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
