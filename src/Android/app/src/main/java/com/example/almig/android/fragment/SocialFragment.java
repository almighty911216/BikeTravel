package com.example.almig.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.almig.android.R;
import com.example.almig.android.adapter.ParallaxSocialAdapter;
import com.example.almig.android.util.DummyContent;
import com.example.almig.android.view.pzv.PullToZoomListViewEx;

/**
 * Created by almig on 2017-06-20.
 */

public class SocialFragment extends Fragment{
    private View mRootView;

    ImageView iv;
    PullToZoomListViewEx mListView;

    public static SocialFragment newInstance() {
        return new SocialFragment();
    }

    private void initBinding(View rootView) {
        mRootView = rootView;
        iv = (ImageView) mRootView.findViewById(R.id.header_parallax_social_new_image);
        mListView = (PullToZoomListViewEx) mRootView.findViewById(R.id.parallax_social_list_view);
    }

    private void initBoard() {
        mListView.setShowDividers(0);
        mListView.setAdapter(new ParallaxSocialAdapter(getContext(), DummyContent.getDummyModelListSocial(), false));
    }

    public SocialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_social, container, false);

        initBinding(rootView);
        initBoard();

        return rootView;
    }
}
