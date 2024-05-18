package com.example.myapplication.Fragment;
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

import com.example.myapplication.API.APIReponse;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.AccountService;
import com.example.myapplication.R;
import com.example.myapplication.API.SendEmail;
import com.example.myapplication.Util.MD5;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetFragment extends Fragment {

    private EditText editText_Email;
    private ImageView imageView2 ;
    private AppCompatButton SendEmail;
    private boolean keyboardVisible = false;

    public ForgetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_forget, container, false);
        Init(view);
        Hiddenkeyboard(view);
        SendEmai();
        return view;
    }
    void Init(View view)
    {
        imageView2=view.findViewById(R.id.imageView2);
        SendEmail=view.findViewById(R.id.SendEmail);
        editText_Email=view.findViewById(R.id.editText_Email);
    }

    void SendEmai(){
        SendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_Email.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please Input email", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Password=com.example.myapplication.API.SendEmail.generateRandomString(10);
                String MD5PassWord="";
                try {
                    MD5PassWord=MD5.ConvertMD5(Password);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                String email=editText_Email.getText().toString();
                String Header="Forget PassWord";
                String Content="Your password has been reset";
                String Footer="Please Update YourPassWord In profile";
                AccountService accountService= APIService.retrofit.create(AccountService.class);
                Call<APIReponse>call=accountService.forgetPassWord(email,Password,MD5PassWord,Header,Content,Footer);
                call.enqueue(new Callback<APIReponse>() {
                    @Override
                    public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                        if(response.isSuccessful())
                        {
                            APIReponse apiReponse=response.body();
                            Toast.makeText(getContext(), apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIReponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    imageView2.setVisibility(View.GONE);
                } else {
                    imageView2.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}