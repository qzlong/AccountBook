package com.example.accountbook.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class CAFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> mList;
    private String[] tabs = {"饼状图", "折线图"};

    public CAFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
