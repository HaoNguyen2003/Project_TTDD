package com.example.myapplication.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.AccountDBActivity;
import com.example.myapplication.Adapter.ProductDashBoard;
import com.example.myapplication.ChartActivity;
import com.example.myapplication.OrderDBActivity;
import com.example.myapplication.ProductDBActivity;
import com.example.myapplication.R;
public class DashBoardFragment extends Fragment {
    ConstraintLayout Product;
    ConstraintLayout Order;
    ConstraintLayout Account;
    ConstraintLayout Chart;
    ImageView imageView_Return;
    callbackhome callbackhome;
    public void setCallbackhome(com.example.myapplication.Fragment.callbackhome callbackhome) {
        this.callbackhome = callbackhome;
    }
    public DashBoardFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dash_board, container, false);
        Init(view);
        goManagerOption();
        Returnhome();
        return view;
    }
    public void Init(View view)
    {
        Product=view.findViewById(R.id.Product);
        Order=view.findViewById(R.id.Order);
        Account=view.findViewById(R.id.Account);
        Chart=view.findViewById(R.id.Chart);
        imageView_Return=view.findViewById(R.id.imageView_Return);
    }

    public void goManagerOption()
    {
        Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ProductDBActivity.class);
                startActivity(intent);
            }
        });
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), OrderDBActivity.class);
                startActivity(intent);
            }
        });
        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AccountDBActivity.class);
                startActivity(intent);
            }
        });
        Chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ChartActivity.class);
                startActivity(intent);
            }
        });
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
}