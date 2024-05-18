package com.example.myapplication.API;
import com.example.myapplication.Model.Category;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("getAllCategory.php")
    Call<ArrayList<Category>> getAllCategory();
}
