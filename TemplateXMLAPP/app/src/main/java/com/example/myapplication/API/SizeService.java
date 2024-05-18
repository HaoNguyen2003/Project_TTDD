package com.example.myapplication.API;

import com.example.myapplication.Model.Size;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SizeService {

    @GET("getSizeByCategoryID.php")
    Call<ArrayList<Size>> getSizeByCategoryID(@Query("categoryID") int categoryID);
}
