package com.example.myapplication.API;

import com.example.myapplication.Model.Brand;
import com.example.myapplication.Model.ContentItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ContentService {
    @GET("getAllContent.php")
    Call<ArrayList<ContentItem>> getAllContent();
    @GET("getNewContent.php")
    Call<ContentItem> getNewContent();
}
