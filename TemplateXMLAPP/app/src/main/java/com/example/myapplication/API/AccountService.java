package com.example.myapplication.API;

import com.example.myapplication.Model.Account;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface AccountService {
    @FormUrlEncoded
    @POST("CreateAccount.php")
    Call<APIReponse>CreateAccount(
            @Field("Username")String Username
            ,@Field("Password")String Password
            ,@Field("Email") String Email
    );

    @FormUrlEncoded
    @POST("SignIn.php")
    Call<Account>SignIn(
            @Field("Username")String Username
            ,@Field("Password")String Password
    );

    @FormUrlEncoded
    @POST("getAccountByID.php")
    Call<Account>getAccountByID(
            @Field("AccountID")int ID
    );

    @FormUrlEncoded
    @POST("SendEmail.php")
    Call<APIReponse>SendEmail(
            @Field("Email")String Email,
            @Field("Header")String Header,
            @Field("Content")String Content,
            @Field("Footer")String Footer
    );
    @FormUrlEncoded
    @POST("forgetPassWord.php")
    Call<APIReponse>forgetPassWord(
            @Field("Email")String Email,
            @Field("PassWord")String PassWord,
            @Field("PassWordMD5")String PassWordMD5,
            @Field("Header")String Header,
            @Field("Content")String Content,
            @Field("Footer")String Footer
    );
    @Multipart
    @POST("UploadImageAccount.php")
    Call<APIReponse> uploadFile(@Part MultipartBody.Part file,
                                @Part("file") RequestBody name,
                                @Query("ID")int AccountID);


    @PUT("UpdateAccount.php")
    Call<Void> update(
            @Query("id") int id,
            @Body Account account
    );

    @GET("GetAllAccountBlock.php")
    Call<ArrayList<Account>>GetAllAccountBlock();
    @GET("GetAllAccountUnBlock.php")
    Call<ArrayList<Account>>GetAllAccountUnBlock();

    @GET("removeAccount.php")
    Call<APIReponse>removeAccount(@Query("ID")int id);
    @GET("blockAccount.php")
    Call<APIReponse>blockAccount(@Query("ID")int id);
    @GET("unblockAccount.php")
    Call<APIReponse>unblockAccount(@Query("ID")int id);

}
