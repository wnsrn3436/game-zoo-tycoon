package com.jungu.zootycoon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContinueActivity extends AppCompatActivity {
    //View
    private TextView textAnimalCount;
    private Button buttonContinue;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.anime_fox);
            bar.setDisplayUseLogoEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }
        setTitle(getResources().getString(R.string.app_name) + " <ContinueGame>");

        //View
        textAnimalCount = findViewById(R.id.textAnimalCount);
        buttonContinue = findViewById(R.id.buttonContinue);
        buttonBack = findViewById(R.id.buttonBack);

        //Event
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContinueGame();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        start();
    }

    void start() {
        textAnimalCount.setText(ZooTycoonDB.getAnimalsCount() + "마리");
    }

    void onContinueGame() {
        //DB에 저장된 옵션을 불러와서 엑티비티를 스타트
        Intent intent = new Intent(ContinueActivity.this, GameActivity.class);
        intent.putExtra("optionMoneyInfinite", (ZooTycoonDB.getOption("'optionMoneyInfinite'", 1) == 1) ? true : false);
        intent.putExtra("optionVisitorFeeling", (ZooTycoonDB.getOption("'optionVisitorFeeling'", 1) == 1) ? true : false);
        intent.putExtra("optionZookeeperExists", (ZooTycoonDB.getOption("'optionZookeeperExists'", 1) == 1) ? true : false);
        intent.putExtra("optionGameSpeed", (float) ZooTycoonDB.getOption("'optionGameSpeed'", 3));
        startActivity(intent);
        finish();
    }
}