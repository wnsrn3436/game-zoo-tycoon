package com.jungu.zootycoon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;

public class NewActivity extends AppCompatActivity {
    //Member
    private boolean optionMoneyInfinite;
    private boolean optionVisitorFeeling;
    private boolean optionZookeeperExists;
    private float optionGameSpeed;

    //View
    private Switch switchMoneyInfinite;
    private Switch switchVisitorFeeling;
    private Switch switchZookeeperExists;
    private RatingBar ratingGameSpeed;
    private Button buttonNewGame;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.anime_fox);
            bar.setDisplayUseLogoEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }
        setTitle(getResources().getString(R.string.app_name) + " <NewGame>");

        //View
        switchMoneyInfinite = findViewById(R.id.switchMoneyInfinite);
        switchVisitorFeeling = findViewById(R.id.switchVisitorFeeling);
        switchZookeeperExists = findViewById(R.id.switchZookeeperExists);
        ratingGameSpeed = findViewById(R.id.ratingGameSpeed);
        buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonBack = findViewById(R.id.buttonBack);

        //Event
        switchMoneyInfinite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                optionMoneyInfinite = b;
            }
        });

        switchVisitorFeeling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                optionVisitorFeeling = b;
            }
        });

        switchZookeeperExists.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                optionZookeeperExists = b;
            }
        });

        ratingGameSpeed.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                optionGameSpeed = v;
            }
        });

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNewGame();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Start
        optionMoneyInfinite = switchMoneyInfinite.isChecked();
        optionVisitorFeeling = switchVisitorFeeling.isChecked();
        optionZookeeperExists = switchZookeeperExists.isChecked();
        optionGameSpeed = ratingGameSpeed.getRating();
    }

    void onNewGame() {
        //DB초기화 및 옵션을 저장
        ZooTycoonDB.cleanTable();
        ZooTycoonDB.setOption("'optionMoneyInfinite'", (optionMoneyInfinite) ? 1 : 0);
        ZooTycoonDB.setOption("'optionVisitorFeeling'", (optionVisitorFeeling) ? 1 : 0);
        ZooTycoonDB.setOption("'optionZookeeperExists'", (optionZookeeperExists) ? 1 : 0);
        ZooTycoonDB.setOption("'optionGameSpeed'", (int) optionGameSpeed);

        Intent intent = new Intent(NewActivity.this, GameActivity.class);
        intent.putExtra("optionMoneyInfinite", optionMoneyInfinite);
        intent.putExtra("optionVisitorFeeling", optionVisitorFeeling);
        intent.putExtra("optionZookeeperExists", optionZookeeperExists);
        intent.putExtra("optionGameSpeed", optionGameSpeed);
        startActivity(intent);
        finish();
    }
}