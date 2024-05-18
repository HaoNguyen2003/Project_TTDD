package com.example.myapplication.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class viewPagerFragment extends FragmentStateAdapter{
    ArrayList<Fragment>list_Fragments;

    public viewPagerFragment(@NonNull FragmentActivity fragmentActivity,ArrayList<Fragment>list_Fragments) {
        super(fragmentActivity);
        this.list_Fragments=list_Fragments;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list_Fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return list_Fragments.size();
    }
}
