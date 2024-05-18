package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.API.APIReponse;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.AccountService;
import com.example.myapplication.Model.Account;
import com.example.myapplication.R;
import com.example.myapplication.Util.MD5;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Cache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.ContentResolver;

public class userFragment extends Fragment {

    TextView textView_nameUser;
    EditText editText_username;
    EditText editText_email;
    EditText editText_numberphone;
    EditText editText_Address;
    EditText editText_password;
    ImageView logout;
    ImageView avatar;
    AppCompatButton btn_Update;
    AppCompatButton btn_upload;
    Account account=new Account();
    ISendDataListener iSendDataListener;
    Ilogout ilogout;
    callbackhome callbackhome;
    ImageView imageView_Return;
    public  String mediaPath;
    public void setCallbackhome(com.example.myapplication.Fragment.callbackhome callbackhome) {
        this.callbackhome = callbackhome;
    }
    public void setIlogout(Ilogout ilogout) {
        this.ilogout = ilogout;
    }

    public void setiSendDataListener(ISendDataListener iSendDataListener) {
        this.iSendDataListener = iSendDataListener;
    }

    public userFragment(){}


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @Override
    public void onResume() {
        super.onResume();
        setUpUserName();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user, container, false);
        Init(view);
        setUpUserName();
        ActionUpdateUser();
        Logout();
        UpLoadImage();
        Returnhome();
        return view;
    }
    public void Init(View view) {
        textView_nameUser=view.findViewById(R.id.textView_nameUser);
        editText_password=view.findViewById(R.id.editText_password);
        editText_username=view.findViewById(R.id.editText_username);
        editText_email=view.findViewById(R.id.editText_email);
        editText_Address=view.findViewById(R.id.editText_Address);
        editText_numberphone=view.findViewById(R.id.editText_numberphone);
        logout=view.findViewById(R.id.logout);
        btn_Update=view.findViewById(R.id.btn_Update);
        avatar=view.findViewById(R.id.avatar);
        btn_upload=view.findViewById(R.id.btn_upload);
        imageView_Return=view.findViewById(R.id.imageView_Return);
    }
    public void setUpUserName()
    {
        account= (Account) getArguments().get("Account");
        textView_nameUser.setText(account.getUsername());
        editText_username.setText(account.getUsername());
        editText_email.setText(account.getEmail());
        editText_Address.setText(account.getAddress());
        editText_numberphone.setText(account.getPhonenumber());
        editText_password.setText(account.getPassword());
        //Picasso.get().load(account.getImageURL()).into(avatar);
        Picasso.get()
                .load(account.getImageURL())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(avatar);
    }
    public void updateUserInThemes() {
        this.account.Username = editText_username.getText().toString();
        this.account.Email = editText_email.getText().toString();
        this.account.Phonenumber = editText_numberphone.getText().toString();
        this.account.Address = editText_Address.getText().toString();
        String Password = "";
        if (!editText_password.getText().toString().isEmpty() && editText_password.getText().toString() != null) {
            String check = "";
            check = editText_password.getText().toString();
            if (!check.equals(this.account.Password)) {
                try {
                    Password = MD5.ConvertMD5(editText_password.getText().toString());
                    this.account.Password = Password;
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void updateUserInData(){
        AccountService accountService = APIService.retrofit.create(AccountService.class);
        Call<Void> call = accountService.update(account.AccountID, account);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("check", "checksuc: "+response.message());
                } else {
                    Toast.makeText(getContext(), "Failed to update account", Toast.LENGTH_SHORT).show();
                    Log.e("check", "check: "+response.message());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("loi", "onFailure: "+t.getMessage());
            }
        });
    }
    public void ActionUpdateUser()
    {
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInThemes();
                updateUserInData();
                iSendDataListener.onDataChanged(account);
            }
        });
    }


    public void UpLoadImage()
    {
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }
    private void openGallery() {
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath=data.getDataString();
        if(mediaPath==null||mediaPath.isEmpty())
            return;
        uploadFile();
        Log.e("checkresult", "image: "+mediaPath);
    }

    public void Logout()
    {
        this.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilogout.onclickLogout();
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

    private void uploadFile() {
        Uri uri=Uri.parse(mediaPath);
        File file = new File(getPathFromUri(uri,getContext().getContentResolver()));
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        AccountService accountService = APIService.retrofit.create(AccountService.class);
        Call<APIReponse> call = accountService.uploadFile(fileToUpload, filename,account.AccountID);
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
    public static String getPathFromUri(Uri uri, ContentResolver contentResolver) {
        String path = null;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if(cursor==null)
        {
            path=uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int index=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path=cursor.getString(index);
            cursor.close();;
        }
        return path;
    }
}