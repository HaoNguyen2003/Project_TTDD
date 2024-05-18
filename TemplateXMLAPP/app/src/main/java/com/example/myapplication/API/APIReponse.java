package com.example.myapplication.API;

import com.google.gson.annotations.SerializedName;

public class APIReponse {
    @SerializedName("message")
    private String message;
    public String getMessage() {
        return message;
    }
}
