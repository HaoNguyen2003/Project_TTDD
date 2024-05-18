package com.example.myapplication.Util;

import static android.content.Context.*;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class Checkconnect {
    public static boolean isInterNet(Context context){
        if(context==null)
            return false;
        ConnectivityManager connectivityManager= (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null)
            return false;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q)
        {
            Network network=connectivityManager.getActiveNetwork();
            if(network==null)
                return false;
            NetworkCapabilities networkCapabilities=connectivityManager.getNetworkCapabilities(network);
            boolean check= networkCapabilities!=null&&networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
            return check;
        }
        else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean check = (networkInfo != null) && networkInfo.isConnected();
            return check;
        }
    }
    public static void toast_confirm(Context context,String thongbao)
    {
        Toast.makeText(context, thongbao, Toast.LENGTH_SHORT).show();
    }
}
