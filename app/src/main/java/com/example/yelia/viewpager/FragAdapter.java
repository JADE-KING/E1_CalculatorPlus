package com.example.yelia.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yelia on 2017/10/14.
 */

public class FragAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragment;

    public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragment = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
