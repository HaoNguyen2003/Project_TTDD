package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.Fragment.ForgetFragment;
import com.example.myapplication.Fragment.SignInFragment;
import com.example.myapplication.Fragment.SignUpFragment;
import com.example.myapplication.Fragment.cartFragment;
import com.example.myapplication.Fragment.homeFragment;
import com.example.myapplication.Fragment.userFragment;
import com.example.myapplication.Fragment.viewPagerFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MyloginActivity extends AppCompatActivity {
    private ViewPager2 slideViewPagerLayout;
    ArrayList<Fragment> list_Fragments=new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylogin);
        slideViewPagerLayout=findViewById(R.id.slideViewPager);
        list_Fragments.add(new SignInFragment());
        list_Fragments.add(new SignUpFragment());
        list_Fragments.add(new ForgetFragment());
        viewPagerFragment viewPagerFragment=new viewPagerFragment(this,this.list_Fragments);
        slideViewPagerLayout.setAdapter(viewPagerFragment);
    }
}