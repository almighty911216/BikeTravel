package com.example.almig.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.almig.android.R;

/**
 * Created by almig on 2017-06-21.
 */

public class ThemePathFragment extends Fragment {
    private View mRootView;

    public static ThemePathFragment newInstance() {
        return new ThemePathFragment();
    }

    private void initBinding(View rootView) {
        mRootView = rootView;
    }

    public ThemePathFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_theme_path, container, false);

        initBinding(rootView);

        return rootView;
    }
}
