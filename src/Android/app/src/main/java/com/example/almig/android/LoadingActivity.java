package com.example.almig.android;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.almig.android.view.ProgressWheel;
import com.example.almig.android.view.kbv.KenBurnsView;

public class LoadingActivity extends AppCompatActivity {
    private KenBurnsView mKbvBg;
    private ImageView mIvAppname;
    private ProgressWheel mPgwhlLoading;

    private void initBinding() {
        mKbvBg = (KenBurnsView)findViewById(R.id.kbv_background);
        mKbvBg.setImageResource(R.drawable.loading_background);
        mIvAppname = (ImageView)findViewById(R.id.iv_title);
        mPgwhlLoading = (ProgressWheel)findViewById(R.id.pgwhl_loading);

        mPgwhlLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initPgwhl() {
        mPgwhlLoading.spin();
    }

    private void initAnimation() {
        mIvAppname.setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mIvAppname.startAnimation(anim);

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mPgwhlLoading, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }

    private void callMainActivity() {
        mIvAppname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        initBinding();
        initAnimation();
        initPgwhl();

        callMainActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
