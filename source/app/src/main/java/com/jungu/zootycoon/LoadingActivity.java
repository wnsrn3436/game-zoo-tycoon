package com.jungu.zootycoon;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {
    //Member
    private boolean isCompleted;

    //View
    private ConstraintLayout layoutLoading;
    private ProgressBar progressLoading;
    private TextView textState;

    //Animation
    private Animation animFadeToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.anime_fox);
            bar.setDisplayUseLogoEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }
        setTitle(getResources().getString(R.string.app_name) + " <Loading>");

        //View
        layoutLoading = findViewById(R.id.layoutLoading);
        progressLoading = findViewById(R.id.progressLoading);
        textState = findViewById(R.id.textState);

        //Animation
        animFadeToggle = AnimationUtils.loadAnimation(this, R.anim.fade_toggle);

        //Event
        layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCompleted) {
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        start();
    }

    void start() {
        //Animation
        textState.startAnimation(animFadeToggle);

        //DB 불러오기
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ZooTycoonDB.setHelper(new ZooTycoonHelper(LoadingActivity.this));
                ZooTycoonDB.loadTable();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onLoadingCompleted();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    void onLoadingCompleted() {
        isCompleted = true;

        progressLoading.setVisibility(View.GONE);
        textState.setText(getResources().getText(R.string.loading_completed));
    }
}