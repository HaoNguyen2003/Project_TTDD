package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.Fragment.DashBoardFragment;
import com.example.myapplication.Fragment.ISendDataListener;
import com.example.myapplication.Fragment.Ilogout;
import com.example.myapplication.Fragment.callbackhome;
import com.example.myapplication.Fragment.cartFragment;
import com.example.myapplication.Fragment.homeFragment;
import com.example.myapplication.Fragment.userFragment;
import com.example.myapplication.Fragment.viewPagerFragment;
import com.example.myapplication.Model.Account;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class bottomnavigationActivity extends AppCompatActivity implements
        ISendDataListener, Ilogout, callbackhome {

    private ViewPager2 slideViewPagerLayout;
    BottomNavigationView bottomnavigation;
    ArrayList<Fragment>list_Fragments=new ArrayList<Fragment>();
    homeFragment homeFragment=new homeFragment();
    cartFragment cartFragment=new cartFragment();
    userFragment userFragment=new userFragment();
    DashBoardFragment dashBoardFragment=new DashBoardFragment();
    public Account account;
    int targetFragmentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigation);
        slideViewPagerLayout=findViewById(R.id.slideViewPagerLayout);
        bottomnavigation=findViewById(R.id.bottomnavigation);
        getAccount();
        PutDataToFragment();
        userFragment.setiSendDataListener(this);
        userFragment.setIlogout(this);
        cartFragment.setCallbackhome(this);
        userFragment.setCallbackhome(this);
        viewPagerFragment viewPagerFragment=new viewPagerFragment(this,this.list_Fragments);
        slideViewPagerLayout.setAdapter(viewPagerFragment);
        slideViewPagerLayout.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        bottomnavigation.setSelectedItemId(R.id.btn_home);
                        break;
                    case 1:
                        bottomnavigation.setSelectedItemId(R.id.btn_cart);
                        break;
                    case 2:
                        bottomnavigation.setSelectedItemId(R.id.btn_user);
                        break;
                    case 3:
                        bottomnavigation.setSelectedItemId(R.id.btn_dashboard);
                }
                super.onPageSelected(position);
            }
        });
        bottomnavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btn_home) {
                    slideViewPagerLayout.setCurrentItem(0);
                    return true;
                } else if (item.getItemId() == R.id.btn_cart) {
                    slideViewPagerLayout.setCurrentItem(1);
                    return true;
                } else if (item.getItemId() == R.id.btn_user) {
                    slideViewPagerLayout.setCurrentItem(2);
                    return true;
                }
                else if(item.getItemId() == R.id.btn_dashboard)
                {
                    slideViewPagerLayout.setCurrentItem(3);
                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }
    public void PutDataToFragment()
    {
        Bundle bundle=new Bundle();
        bundle.putSerializable("Account",this.account);
        homeFragment.setArguments(bundle);
        cartFragment.setArguments(bundle);
        userFragment.setArguments(bundle);
        dashBoardFragment.setArguments(bundle);
    }
    public void decentralization()
    {
        MenuItem menuItem=this.bottomnavigation.getMenu().findItem(R.id.btn_dashboard);
        if(this.account.MissionID==0)
        {
            menuItem.setVisible(false);
        }else if(this.account.MissionID==1)
        {
            menuItem.setVisible(true);
            list_Fragments.add(dashBoardFragment);
            dashBoardFragment.setCallbackhome(this);
        }
    }
    public void getAccount()
    {
        Intent intent=getIntent();
        this.account= (Account) intent.getSerializableExtra("Account");
        targetFragmentIndex = intent.getIntExtra("targetFragment", 0);
        list_Fragments.add(homeFragment);
        list_Fragments.add(cartFragment);
        list_Fragments.add(userFragment);
        decentralization();
        viewPagerFragment viewPagerFragment=new viewPagerFragment(this,this.list_Fragments);
        slideViewPagerLayout.setAdapter(viewPagerFragment);
        slideViewPagerLayout.setCurrentItem(targetFragmentIndex);
    }

    @Override
    public void onDataChanged(Account account) {
        this.account=account;
        Bundle bundle=new Bundle();
        bundle.putSerializable("Account",this.account);
        homeFragment.setArguments(bundle);
        cartFragment.setArguments(bundle);
        userFragment.setArguments(bundle);
    }

    @Override
    public void onclickLogout(){
        Intent intent = new Intent(this,MyloginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack Activity cũ
        startActivity(intent);
        finish();
    }
    @Override
    public void gohomelistener() {
        if(targetFragmentIndex==0){
            slideViewPagerLayout.setCurrentItem(0);
        }
        else{
        finish();
        }
    }
}