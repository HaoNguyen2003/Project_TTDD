package com.example.myapplication.API;
import com.example.myapplication.Model.Brand;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BrandService {
    @GET("getAllBrand.php")
    Call<ArrayList<Brand>>getAllBrand();
}
