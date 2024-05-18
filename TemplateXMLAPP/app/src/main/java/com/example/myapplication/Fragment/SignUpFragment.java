package com.example.myapplication.Fragment;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.API.APIReponse;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.AccountService;
import com.example.myapplication.Model.Account;
import com.example.myapplication.R;
import com.example.myapplication.Util.MD5;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private boolean keyboardVisible = false;
    private ImageView imageView3;
    private EditText editText_username,
            editText_email,
            editText_password,
            editText_Repassword;
    private AppCompatButton SignUp;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        Init(view);
        Hiddenkeyboard(view);
        SignUp();
        return view;
    }
    void Init(View view) {
        imageView3=view.findViewById(R.id.imageView3);
        editText_username=view.findViewById(R.id.editText_username);
        editText_email=view.findViewById(R.id.editText_email);
        editText_password=view.findViewById(R.id.editText_password);
        editText_Repassword=view.findViewById(R.id.editText_Repassword);
        SignUp=view.findViewById(R.id.SignUp);
    }
    void SignUp(){
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_username.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please Input Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editText_email.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please Input email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(editText_email.getText()).matches()) {
                    Toast.makeText(getContext(), "Email not valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editText_password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please Input password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!editText_password.getText().toString().equals(editText_Repassword.getText().toString())) {
                    Toast.makeText(getContext(), "Password and Repassword not same", Toast.LENGTH_SHORT).show();
                    return;
                }
                Account account = new Account();
                account.setUsername(String.valueOf(editText_username.getText()));
                account.setEmail(String.valueOf(editText_email.getText()));
                try {
                    String Pass = MD5.ConvertMD5(String.valueOf(editText_password.getText()));
                    account.setPassword(Pass);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                AccountService accountService = APIService.retrofit.create(AccountService.class);
                Call<APIReponse> call = accountService.CreateAccount(account.Username, account.Password, account.Email);
                call.enqueue(new Callback<APIReponse>()
                {
                    @Override
                    public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                        if (response.isSuccessful()) {
                            APIReponse responseBody = response.body();
                            if (responseBody != null) {
                                Toast.makeText(getContext(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Empty response", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<APIReponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("error", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
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
                    imageView3.setVisibility(View.GONE);
                } else {
                    imageView3.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}