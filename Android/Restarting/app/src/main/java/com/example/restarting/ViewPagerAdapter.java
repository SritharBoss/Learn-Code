package com.example.restarting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.restarting.fragments.BlankFragment;
import com.example.restarting.fragments.FragmentOne;
import com.example.restarting.fragments.FragmentTwo;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] a={"Chats","Status","Calls"};
     public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new BlankFragment();
            case 1: return new FragmentOne();
            case 2: return new FragmentTwo();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return a[position];
    }
}
