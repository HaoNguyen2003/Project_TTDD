package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.APIReponse;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.OrderService;
import com.example.myapplication.Adapter.ProductInOrderAdapter;
import com.example.myapplication.Model.Cart;
import com.example.myapplication.Model.Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends AppCompatActivity {

    public ImageView imageView_return;
    Order order;
    AppCompatButton btn_yes,btn_No;
    TextView inforName,inforSDT,inforAdress;
    RecyclerView listProduct;
    ArrayList<Cart>listCarts=new ArrayList<Cart>();
    ProductInOrderAdapter productInOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        Init();
        GetIntent();
        ReturnManagerOrder();
        setInfo();
        setUpDetailOrder();
        CancelAnddeliveredOrder();
    }
    public void GetIntent()
    {
        Intent intent=getIntent();
        order= (Order) intent.getSerializableExtra("Order");
        if(order.checkOrder==1)
        {
            btn_yes.setVisibility(View.VISIBLE);
            btn_No.setVisibility(View.VISIBLE);
        } else{
            btn_yes.setVisibility(View.GONE);
            btn_No.setVisibility(View.GONE);
        }

    }
    public void Init()
    {
        imageView_return=findViewById(R.id.imageView_return);
        btn_yes=findViewById(R.id.btn_yes);
        btn_No=findViewById(R.id.btn_No);
        inforName=findViewById(R.id.inforName);
        inforSDT=findViewById(R.id.inforSDT);
        inforAdress=findViewById(R.id.inforAdress);
        listProduct=findViewById(R.id.listProduct);
    }
    public void setInfo()
    {
        inforName.setText(order.getCustomerName());
        inforSDT.setText(order.getPhoneNumber());
        inforAdress.setText(order.getAddress());
    }
    public void setDetai()
    {
        productInOrderAdapter=new ProductInOrderAdapter(listCarts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listProduct.setLayoutManager(layoutManager);
        listProduct.setAdapter(productInOrderAdapter);
    }
    public void setUpDetailOrder()
    {
        OrderService orderService= APIService.retrofit.create(OrderService.class);
        Call<ArrayList<Cart>> call=orderService.GetAllDetailOrderByOrderID(order.OrderID);
        call.enqueue(new Callback<ArrayList<Cart>>() {
            @Override
            public void onResponse(Call<ArrayList<Cart>> call, Response<ArrayList<Cart>> response) {
                if(response.isSuccessful()) {
                    listCarts=response.body();
                    setDetai();
                }
                else{
                    Toast.makeText(DetailOrderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Cart>> call, Throwable t) {
                Toast.makeText(DetailOrderActivity.this, "Lỗi kết nối"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ReturnManagerOrder()
    {
        imageView_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void CancelAnddeliveredOrder()
    {
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderService orderService=APIService.retrofit.create(OrderService.class);
                Call<APIReponse>call=orderService.DeliveredOrder(order.OrderID);
                call.enqueue(new Callback<APIReponse>() {
                    @Override
                    public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                        if(response.isSuccessful()){
                            APIReponse apiReponse=response.body();
                            Toast.makeText(DetailOrderActivity.this, apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                            btn_yes.setVisibility(View.GONE);
                            btn_No.setVisibility(View.GONE);
                            Notification("notification of your order","Đơn hàng bạn đã đặt với số tiền: "+order.totalPrice+
                                    "dưới tên: "+order.CustomerName+" vào ngày: "+order.CreateDay +" đã được giao đi.\n Vui lòng Kiểm tra điện thoại",order.OrderID);
                        }
                        else{
                            Toast.makeText(DetailOrderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIReponse> call, Throwable t) {

                        Toast.makeText(DetailOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderService orderService=APIService.retrofit.create(OrderService.class);
                Call<APIReponse>call=orderService.cancelOrder(order.OrderID);
                call.enqueue(new Callback<APIReponse>() {
                    @Override
                    public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                        if(response.isSuccessful()){
                            APIReponse apiReponse=response.body();
                            Toast.makeText(DetailOrderActivity.this, apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                            btn_yes.setVisibility(View.GONE);
                            btn_No.setVisibility(View.GONE);
                            Notification("notification of your order","Đơn hàng bạn đã đặt với số tiền: "+order.totalPrice+
                                    "dưới tên: "+order.CustomerName+" vào ngày: "+order.CreateDay +" đã bị hủy đi.\n liên hệ để biết thêm thông tin",order.OrderID);
                        }
                        else{
                            Toast.makeText(DetailOrderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIReponse> call, Throwable t) {

                        Toast.makeText(DetailOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void Notification(String header,String Content,int ID)
    {
        OrderService orderService=APIService.retrofit.create(OrderService.class);
        Call<Void>call=orderService.SendMailAdmin(header,Content,ID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}