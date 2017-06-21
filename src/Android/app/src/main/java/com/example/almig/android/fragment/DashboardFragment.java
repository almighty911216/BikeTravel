package com.example.almig.android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almig.android.R;
import com.example.almig.android.model.Cycling;
import com.example.almig.android.model.GoogleGpsApi;
import com.example.androidbootstrap.BootstrapButton;
import com.example.speedviewlib.PointerSpeedometer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by almig on 2017-06-20.
 */

public class DashboardFragment extends Fragment {
    private View mRootView;
    private PointerSpeedometer mPointerSpeedometer;
    private BootstrapButton mBtnStart;
    private BootstrapButton mBtnQuit;
    private TextView mTime;
    private TextView mRpm;
    private TextView mDistance;
    private TextView mAvgSpeed;
    private TextView mCalory;
    private Handler mHandler;
    private ExecutorService mExecutorService;
    private GoogleGpsApi mGoogleGpsApi;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    private void initBinding(View rootView) {
        mRootView = rootView;
        mPointerSpeedometer = (PointerSpeedometer) mRootView.findViewById(R.id.pointerSpeedometer);
        mBtnStart = (BootstrapButton)mRootView.findViewById(R.id.btn_start);
        mBtnQuit = (BootstrapButton)mRootView.findViewById(R.id.btn_quit);
        mTime = (TextView)mRootView.findViewById(R.id.tv_time);
        mRpm = (TextView)mRootView.findViewById(R.id.tv_rpm);
        mDistance = (TextView)mRootView.findViewById(R.id.tv_distance);
        mAvgSpeed = (TextView)mRootView.findViewById(R.id.tv_avg_speed);
        mCalory = (TextView)mRootView.findViewById(R.id.tv_calorie);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0){
                    Object[] objects = (Object[])msg.obj;
                    mPointerSpeedometer.speedTo(Float.valueOf(String.valueOf(objects[0])));
                    mAvgSpeed.setText(sliceString(4,String.valueOf(objects[1])));
                    mRpm.setText(String.valueOf(objects[2]));
                    mCalory.setText(sliceString(5,String.valueOf(objects[3])));
                    mDistance.setText(sliceString(5,String.valueOf(objects[4])));
                    mTime.setText(String.valueOf(objects[5]));
                }
            }
        };

        mExecutorService = Executors.newFixedThreadPool(1);
        Cycling cycling = new Cycling();
        mGoogleGpsApi = new GoogleGpsApi(getActivity(), mHandler, cycling);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "GPS 시작", Toast.LENGTH_SHORT).show();
                mExecutorService.execute(mGoogleGpsApi);
            }
        });

        mBtnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "GPS 종료", Toast.LENGTH_SHORT).show();
                mExecutorService.shutdown();
            }
        });
    }

    public String sliceString(int slicelength,String str){
        if(str != null){
            if(str.length() >= slicelength){
                return str.substring(0,slicelength);
            }else{
                return str.substring(0,str.length());
            }
        }
        return "";
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
