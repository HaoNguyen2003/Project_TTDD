package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.APIService;
import com.example.myapplication.API.OrderService;
import com.example.myapplication.Adapter.OrderAdapter;
import com.example.myapplication.Model.Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDBActivity extends AppCompatActivity implements OrderAdapter.listenerOrder {

    OrderAdapter NewOrderAdapter;
    OrderAdapter CancelOrderAdapter;
    OrderAdapter DeliveredOrderAdapter;
    ImageView imageView_Return;
    RecyclerView neworder;
    RecyclerView cancelorder;
    RecyclerView DeliveredOrder;

    ImageView reload;
    ProgressBar LoadNew;
    ProgressBar LoadCancel;
    ProgressBar LoadDelivered;
    ArrayList<Order>list_OrdersNew=new ArrayList<Order>();
    ArrayList<Order>list_OrdersCancel=new ArrayList<Order>();
    ArrayList<Order>list_OrdersDelivered=new ArrayList<Order>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dbactivity);
        Init();
        ReturnDashBoard();
        setUpNewOrder();
        setUpCancelOrder();
        setUpDeliveredOrder();
        ReloadData();
    }
    public void ReloadData()
    {
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpNewOrder();
                setUpCancelOrder();
                setUpDeliveredOrder();
            }
        });
    }
    public void Init()
    {
        imageView_Return=findViewById(R.id.imageView_Return);
        neworder=findViewById(R.id.neworder);
        cancelorder=findViewById(R.id.cancelorder);
        DeliveredOrder=findViewById(R.id.DeliveredOrder);
        LoadNew=findViewById(R.id.LoadNew);
        LoadCancel=findViewById(R.id.LoadCancel);
        LoadDelivered=findViewById(R.id.LoadDelivered);
        reload=findViewById(R.id.reload);
    }
    public void ReturnDashBoard()
    {
        imageView_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void GetNewOrder()
    {
        LoadNew.setVisibility(View.VISIBLE);
        NewOrderAdapter=new OrderAdapter(list_OrdersNew);
        NewOrderAdapter.setListenerOrder(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        neworder.setLayoutManager(layoutManager);
        neworder.setAdapter(NewOrderAdapter);

    }
    public void setUpNewOrder()
    {
        OrderService orderService= APIService.retrofit.create(OrderService.class);
        Call<ArrayList<Order>>call=orderService.GetAllOrderNew();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if(response.isSuccessful()){
                    list_OrdersNew=response.body();
                    GetNewOrder();
                    LoadNew.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(OrderDBActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(OrderDBActivity.this, "Lỗi kêt nối "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void GetCancelOrder()
    {
        LoadCancel.setVisibility(View.VISIBLE);
        CancelOrderAdapter=new OrderAdapter(list_OrdersCancel);
        CancelOrderAdapter.setListenerOrder(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        cancelorder.setLayoutManager(layoutManager);
        cancelorder.setAdapter(CancelOrderAdapter);
    }
    public void setUpCancelOrder()
    {
        OrderService orderService= APIService.retrofit.create(OrderService.class);
        Call<ArrayList<Order>>call=orderService.GetAllOrderCancel();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if(response.isSuccessful()){
                    list_OrdersCancel=response.body();
                    GetCancelOrder();
                    LoadCancel.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(OrderDBActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(OrderDBActivity.this, "Lỗi kêt nối "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void GetDeliveredOrder()
    {
        LoadDelivered.setVisibility(View.VISIBLE);
        DeliveredOrderAdapter=new OrderAdapter(list_OrdersDelivered);
        DeliveredOrderAdapter.setListenerOrder(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        DeliveredOrder.setLayoutManager(layoutManager);
        DeliveredOrder.setAdapter(DeliveredOrderAdapter);
    }
    public void setUpDeliveredOrder()
    {
        OrderService orderService= APIService.retrofit.create(OrderService.class);
        Call<ArrayList<Order>>call=orderService.GetAllOrderDelivered();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if(response.isSuccessful()){
                    list_OrdersDelivered=response.body();
                    GetDeliveredOrder();
                    LoadDelivered.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(OrderDBActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(OrderDBActivity.this, "Lỗi kêt nối "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void seeDetailOrder(Order order) {
        Intent intent=new Intent(OrderDBActivity.this,DetailOrderActivity.class);
        intent.putExtra("Order",order);
        startActivity(intent);
    }


}