package com.example.myapplication.API;

import com.example.myapplication.Model.itemBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChartService {
    @GET("piechart.php")
    Call<ArrayList<itemBar>>getquantityCategory();

    @GET("ChartBar.php")
    Call<ArrayList<itemBar>>getquantityBrand();
}
