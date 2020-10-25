package com.example.accountbook.fragments;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mfragmentManager;
    private List<Fragment> mlist;

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mlist = list;
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }
    //显示第几个页面
    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }
    //一共有几个页面，注意，使用Fragment特有的构造器时，和ViewPager的原生构造器的方法不同
    @Override
    public int getCount() {
        return mlist.size();
    }

}
