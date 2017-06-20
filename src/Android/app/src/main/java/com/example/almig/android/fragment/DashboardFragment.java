package com.example.almig.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.almig.android.R;
import com.example.speedviewlib.PointerSpeedometer;

/**
 * Created by almig on 2017-06-20.
 */

public class DashboardFragment extends Fragment {
    private View mRootView;
    PointerSpeedometer mPointerSpeedometer;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    private void initBinding(View rootView) {
        mRootView = rootView;
        mPointerSpeedometer = (PointerSpeedometer) mRootView.findViewById(R.id.pointerSpeedometer);

    }

    private void initSpeedometer() {
        mPointerSpeedometer.setMinSpeed(0);
        mPointerSpeedometer.setMaxSpeed(40);
        mPointerSpeedometer.speedTo(35);
    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initBinding(rootView);
        initSpeedometer();

        return rootView;
    }
}
