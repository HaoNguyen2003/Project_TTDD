package com.example.myapplication.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.AccountService;
import com.example.myapplication.Model.Account;
import com.example.myapplication.R;
import com.example.myapplication.Util.MD5;
import com.example.myapplication.bottomnavigationActivity;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment{

    private EditText editTextUsername;
    private EditText editTextPassword;
    private AppCompatButton signInButton;
    private boolean keyboardVisible = false;
    private ImageView imageView2 ;

    public SignInFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_in, container, false);
        Init(view);
        Hiddenkeyboard(view);
        SignIn();
        return view;
    }
    void SignIn()
    {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUsername.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please Input Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextPassword.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please Input password", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Username = String.valueOf(editTextUsername.getText());
                String Password;
                try {
                    Password = MD5.ConvertMD5(String.valueOf(editTextPassword.getText()));
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                AccountService accountService = APIService.retrofit.create(AccountService.class);
                Call<Account> call = accountService.SignIn(Username,Password);
                call.enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        Account account=response.body();
                        if(account.getUsername()!=null)
                        {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getContext(), bottomnavigationActivity.class);
                            intent.putExtra("Account",account);
                            Log.e("CheckAvatar", "CheckAvatar: "+account.toString());
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getContext(), "wrong password or account", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    void Init(View view)
    {
        imageView2=view.findViewById(R.id.imageView2);
        editTextPassword=view.findViewById(R.id.editText_password);
        editTextUsername=view.findViewById(R.id.editText_username);
        signInButton=view.findViewById(R.id.SignIn);
    }
    //---------------//
    void Hiddenkeyboard(View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int screenHeight = view.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                double thresholdPercentage = 0.15;
                int threshold = (int) (screenHeight * thresholdPercentage);
                if (keypadHeight > threshold) {
                    imageView2.setVisibility(View.GONE);
                } else {
                    imageView2.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}