package com.example.almig.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.almig.android.R;
import com.example.almig.android.adapter.FragmentViewPagerAdapter;
import com.example.navtabstrip.NavigationTabStrip;

/**
 * Created by almig on 2017-06-20.
 */

public class BicycleRoadFragment extends Fragment {
    private View mRootView;
    private NavigationTabStrip mNavigationTabStrip;
    private ViewPager mViewPager;

    public static BicycleRoadFragment newInstance() {
        return new BicycleRoadFragment();
    }

    private void initBinding(View rootView) {
        mRootView = rootView;
        mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_contents);
        mNavigationTabStrip = (NavigationTabStrip) mRootView.findViewById(R.id.nts_center);
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentViewPagerAdapter(getFragmentManager()));
        mNavigationTabStrip.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    public BicycleRoadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bicycle_road, container, false);

        initBinding(rootView);
        initViewPager();

        return rootView;
    }
}
