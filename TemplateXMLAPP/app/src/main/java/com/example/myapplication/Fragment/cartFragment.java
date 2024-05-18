package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.APIReponse;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.OrderService;
import com.example.myapplication.Adapter.CartRecyler;
import com.example.myapplication.DataBaseSQLite.SQLiteHelper;
import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.Cart;
import com.example.myapplication.Model.Order;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cartFragment extends Fragment implements CartRecyler.optionCart {

    CartRecyler cartRecyler;
    RecyclerView listCart;
    ImageView imageView_Return;
    TextView textView_confirmCart;
    TextView PriceTotal;
    Button btn_checkout;
    ConstraintLayout order;
    ConstraintLayout maincart;
    ImageView imageView_close;
    SQLiteHelper sqLiteDatabase;
    callbackhome callbackhome;
    ArrayList<Cart> list_Carts=new ArrayList<Cart>();
    Account account;
    EditText editTextNameCustomer,editText_phonenumber,editTextText_Address;
    AppCompatButton btn_COD;
    View view;

    public void setCallbackhome(com.example.myapplication.Fragment.callbackhome callbackhome) {
        this.callbackhome = callbackhome;
    }

    public cartFragment() {}
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllCart();
        SetCart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_cart, container, false);
        Init(view);
        getAllCart();
        SetCart();
        Returnhome();
        hideOrder();
        buynow();
        return view;
    }

    public void Init(View view) {
        imageView_Return = view.findViewById(R.id.imageView_Return);
        listCart = view.findViewById(R.id.listCart);
        PriceTotal = view.findViewById(R.id.PriceTotal);
        textView_confirmCart = view.findViewById(R.id.textView_confirmCart);
        btn_checkout = view.findViewById(R.id.btn_checkout);
        order=view.findViewById(R.id.order);
        imageView_close=view.findViewById(R.id.imageView_close);
        maincart=view.findViewById(R.id.maincart);
        editTextNameCustomer=view.findViewById(R.id.editTextNameCustomer);
        editText_phonenumber=view.findViewById(R.id.editText_phonenumber);
        editTextText_Address=view.findViewById(R.id.editTextText_Address);
        btn_COD=view.findViewById(R.id.btn_COD);
    }
    public void getAllCart()
    {
        account= (Account) getArguments().get("Account");
        sqLiteDatabase=new SQLiteHelper(getContext());
        list_Carts=sqLiteDatabase.getAllByAccountId(account.AccountID);
    }
    public void SetCart()
    {
        if(list_Carts.size()==0) {
            this.textView_confirmCart.setVisibility(View.VISIBLE);
        }else {
            this.textView_confirmCart.setVisibility(View.GONE);
        }
        cartRecyler = new CartRecyler(list_Carts, this.getContext());
        cartRecyler.setOptionCart(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        listCart.setLayoutManager(layoutManager);
        listCart.setAdapter(cartRecyler);
        PriceTotal.setText("$" + String.valueOf(this.cartRecyler.GetPriceTotal()));
    }
    void Returnhome()
    {
        imageView_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackhome.gohomelistener();
            }
        });
    }

    @Override
    public void deleteCart(int Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete this item?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sqLiteDatabase.removeFromCart(Id);
                        list_Carts = sqLiteDatabase.getAllByAccountId(account.AccountID);
                        cartRecyler.filter(list_Carts);
                        PriceTotal.setText("$" + String.valueOf(cartRecyler.GetPriceTotal()));
                        if(list_Carts.isEmpty()||list_Carts==null)
                        {
                            textView_confirmCart.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void plusCart(int Id,int amount,double price) {
        sqLiteDatabase.plusCart(Id,amount,price);
        list_Carts=sqLiteDatabase.getAllByAccountId(account.AccountID);
        cartRecyler.filter(list_Carts);
        PriceTotal.setText("$" + String.valueOf(this.cartRecyler.GetPriceTotal()));
    }

    @Override
    public void minusCart(int Id,int amount,double price) {
        sqLiteDatabase.minusCart(Id,amount,price);
        list_Carts=sqLiteDatabase.getAllByAccountId(account.AccountID);
        cartRecyler.filter(list_Carts);
        PriceTotal.setText("$" + String.valueOf(this.cartRecyler.GetPriceTotal()));
    }
    public void hideOrder()
    {
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setVisibility(View.VISIBLE);
                maincart.setVisibility(View.GONE);
            }
        });
        imageView_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setVisibility(View.GONE);
                maincart.setVisibility(View.VISIBLE);
            }
        });
    }
    public void buynow()
    {
        btn_COD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_Carts==null|| list_Carts.isEmpty()){
                    Toast.makeText(getContext(), "your cart is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userName=editTextNameCustomer.getText()+"";
                String phonenumber=editText_phonenumber.getText()+"";
                String address=editTextText_Address.getText()+"";
                String ImageURL= account.ImageURL;
                if(userName.isEmpty()||phonenumber.isEmpty()||address.isEmpty()||userName.equals("")||phonenumber.equals("")||address.equals("")){
                    Toast.makeText(getContext(), "please input your information", Toast.LENGTH_SHORT).show();
                    return;
                }
                double price=cartRecyler.GetPriceTotal();
                Order order=new Order(1,account.getAccountID(),userName,phonenumber,address,"",price,ImageURL,list_Carts);
                OrderService orderService= APIService.retrofit.create(OrderService.class);
                Call<APIReponse> call=orderService.addOrder(order);
                call.enqueue(new Callback<APIReponse>() {
                    @Override
                    public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                        if(response.isSuccessful())
                        {
                            APIReponse apiReponse=response.body();
                            Toast.makeText(getContext(), apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                            for(Cart cart:list_Carts){
                            sqLiteDatabase.removeFromCart(cart.getId());
                            }
                            String header="New order notification";
                            String content=account.Username+" đã đặt 1 đơn hàng mới có giá :"+String.valueOf(cartRecyler.GetPriceTotal())+
                                    " dưới tên: ";

                            Notification(header,content,userName);
                            list_Carts=sqLiteDatabase.getAllByAccountId(account.AccountID);
                            cartRecyler.filter(list_Carts);
                            PriceTotal.setText("$" + String.valueOf(cartRecyler.GetPriceTotal()));
                            editTextNameCustomer.setText("");
                            editText_phonenumber.setText("");
                            editTextText_Address.setText("");
                            textView_confirmCart.setVisibility(View.VISIBLE);
                        }
                        else{
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIReponse> call, Throwable t) {
                        Toast.makeText(getContext(), "lỗi kết nối"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void Notification(String header,String Content,String Name)
    {
        OrderService orderService=APIService.retrofit.create(OrderService.class);
        Call<Void>call=orderService.SendMailAdmin(header,Content,Name);
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