package com.example.myapplication.API;
import com.example.myapplication.Model.Cart;
import com.example.myapplication.Model.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderService {
    @POST("CreateOrder.php")
    Call<APIReponse> addOrder(
            @Body Order order
    );
    @FormUrlEncoded
    @POST("NotificationOrder.php")
    Call<Void>SendMailAdmin(
            @Field("Header")String Header,
            @Field("Content")String Content,
            @Field("Name")String Name
    );
    @FormUrlEncoded
    @POST("NotificationAdmin.php")
    Call<Void>SendMailAdmin(
            @Field("Header")String Header,
            @Field("Content")String Content,
            @Field("ID") int ID
    );
    @GET("GetAllOrderNew.php")
    Call<ArrayList<Order>>GetAllOrderNew();

    @GET("GetAllOrderCancel.php")
    Call<ArrayList<Order>>GetAllOrderCancel();

    @GET("GetAllOrderDelivered.php")
    Call<ArrayList<Order>>GetAllOrderDelivered();

    @GET("GetAllDetailOrderByOrderID.php")
    Call<ArrayList<Cart>>GetAllDetailOrderByOrderID(@Query("OrderID")int orderId);

    @GET("DeliveredOrder.php")
    Call<APIReponse>DeliveredOrder(@Query("OrderID")int id);

    @GET("CancelOrder.php")
    Call<APIReponse>cancelOrder(@Query("OrderID")int id);
}
