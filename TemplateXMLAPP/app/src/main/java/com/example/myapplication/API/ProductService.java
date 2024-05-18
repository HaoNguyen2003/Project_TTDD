package com.example.myapplication.API;

import com.example.myapplication.Model.Brand;
import com.example.myapplication.Model.Product;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ProductService {
    @GET("getAllProduct.php")
    Call<ArrayList<Product>> getAllProduct();

    @GET("getProduct.php")
    Call<Product> getProduct();

    @PUT("pushViewerProduct.php")
    Call<APIReponse>pushViewerProduct(@Query("ProductID")int ID);

    @GET("Removeproduct.php")
    Call<APIReponse>Removeproduct(@Query("ID")int ID);

    @FormUrlEncoded
    @POST("newUpdateProduct.php")
    Call<APIReponse> updateProduct(
            @Query("ID") int id,
            @Field("BrandID") int brandID,
            @Field("CategoryID") int categoryID,
            @Field("ProductName") String productName,
            @Field("ProductTitle") String productTitle,
            @Field("Price") double price,
            @Field("PriceIn") String priceIn,
            @Field("Description") String description,
            @Field("Discount") double discount
    );


    @Multipart
    @POST("UploadImageProduct.php")
    Call<Void> uploadFile(@Part MultipartBody.Part file,
                                @Part("file") RequestBody name,
                                @Query("ID")int ProductID);

    @POST("CreateNewProduct.php")
    Call<APIReponse>CreateProduct(@Body Product product);
}
