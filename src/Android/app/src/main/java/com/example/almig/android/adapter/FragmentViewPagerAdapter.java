package com.example.almig.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.almig.android.fragment.NationalBicycleRoadFragment;
import com.example.almig.android.fragment.PathFindingFragment;
import com.example.almig.android.fragment.ThemePathFragment;

/**
 * Created by almig on 2017-06-21.
 */

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {
    public FragmentViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = PathFindingFragment.newInstance();
                break;

            case 1:
                fragment = NationalBicycleRoadFragment.newInstance();
                break;

            case 2:
                fragment = ThemePathFragment.newInstance();
                break;
        }

        return fragment;
    }
}
