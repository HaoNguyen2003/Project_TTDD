package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.APIReponse;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.AccountService;
import com.example.myapplication.Adapter.AccountAdapter;
import com.example.myapplication.Model.Account;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDBActivity extends AppCompatActivity implements AccountAdapter.optionAccount {

    ImageView imageView_Return;
    ArrayList<Account>list_account_access=new ArrayList<Account>();
    ArrayList<Account>list_account_Block=new ArrayList<Account>();
    ProgressBar LoadAccess;
    ProgressBar LoadBlock;
    AccountAdapter accountAdapterblock;
    AccountAdapter accountAdapteraccess;
    RecyclerView AccountAccess;
    RecyclerView AccountBlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_dbactivity);
        Init();
        loadAccountBlock();
        loadAccountUnBlock();
        ReturnDashBoard();
    }
    public void Init()
    {
        imageView_Return=findViewById(R.id.imageView_Return);
        AccountBlock=findViewById(R.id.cancelorder);
        AccountAccess=findViewById(R.id.neworder);
        LoadBlock=findViewById(R.id.LoadCancel);
        LoadAccess=findViewById(R.id.LoadNew);
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
    public void setUpAccountBlock()
    {
        LoadBlock.setVisibility(View.VISIBLE);
        accountAdapterblock=new AccountAdapter(list_account_Block);
        accountAdapterblock.setOptionAccount(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        AccountBlock.setLayoutManager(layoutManager);
        AccountBlock.setAdapter(accountAdapterblock);
    }
    public void loadAccountBlock()
    {
        AccountService accountService= APIService.retrofit.create(AccountService.class);
        Call<ArrayList<Account>> call=accountService.GetAllAccountBlock();
        call.enqueue(new Callback<ArrayList<Account>>() {
            @Override
            public void onResponse(Call<ArrayList<Account>> call, Response<ArrayList<Account>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Account> List_ac = response.body();
                        list_account_Block = List_ac;
                        setUpAccountBlock();
                        LoadBlock.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Account>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }
    public void setUpAccountUnBlock()
    {
        LoadAccess.setVisibility(View.VISIBLE);
        accountAdapteraccess=new AccountAdapter(list_account_access);
        accountAdapteraccess.setOptionAccount(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        AccountAccess.setLayoutManager(layoutManager);
        AccountAccess.setAdapter(accountAdapteraccess);
    }
    public void loadAccountUnBlock()
    {
        AccountService accountService= APIService.retrofit.create(AccountService.class);
        Call<ArrayList<Account>> call=accountService.GetAllAccountUnBlock();
        call.enqueue(new Callback<ArrayList<Account>>() {
            @Override
            public void onResponse(Call<ArrayList<Account>> call, Response<ArrayList<Account>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Account> List_ac = response.body();
                        list_account_access = List_ac;
                        setUpAccountUnBlock();
                        LoadAccess.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Account>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }

    @Override
    public void removeAccount(int ID) {
        AccountService accountService=APIService.retrofit.create(AccountService.class);
        Call<APIReponse>call=accountService.removeAccount(ID);
        call.enqueue(new Callback<APIReponse>() {
            @Override
            public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                if (response.isSuccessful()) {
                    APIReponse apiReponse=response.body();
                    Toast.makeText(AccountDBActivity.this, apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                    loadAccountBlock();
                    loadAccountUnBlock();
                }
                else
                {
                    Toast.makeText(AccountDBActivity.this, "remove fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIReponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }

    @Override
    public void blockAccount(int ID) {
        AccountService accountService=APIService.retrofit.create(AccountService.class);
        Call<APIReponse>call=accountService.blockAccount(ID);
        call.enqueue(new Callback<APIReponse>() {
            @Override
            public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                if (response.isSuccessful()) {
                    APIReponse apiReponse=response.body();
                    Toast.makeText(AccountDBActivity.this, apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                    loadAccountBlock();
                    loadAccountUnBlock();
                }
                else
                {
                    Toast.makeText(AccountDBActivity.this, "block fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIReponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }

    @Override
    public void unblockAccount(int ID) {
        AccountService accountService=APIService.retrofit.create(AccountService.class);
        Call<APIReponse>call=accountService.unblockAccount(ID);
        call.enqueue(new Callback<APIReponse>() {
            @Override
            public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                if (response.isSuccessful()) {
                    APIReponse apiReponse=response.body();
                    Toast.makeText(AccountDBActivity.this, apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                    loadAccountBlock();
                    loadAccountUnBlock();
                }
                else
                {
                    Toast.makeText(AccountDBActivity.this, "Unblock fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIReponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }
}