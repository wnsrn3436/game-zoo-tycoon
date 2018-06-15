package com.jungu.zootycoon;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;

public class GameActivity extends AppCompatActivity {
    //Member
    private SyncScroll syncScroll;
    private boolean optionMoneyInfinite;
    private boolean optionVisitorFeeling;
    private boolean optionZookeeperExists;
    private float optionGameSpeed;

    //View
    private ScrollView scrollV;
    private HorizontalScrollView scrollH;
    private ConstraintLayout layoutGameMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.anime_fox);
            bar.setDisplayUseLogoEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }
        setTitle(getResources().getString(R.string.app_name) + " <Game>");

        //View
        scrollV = findViewById(R.id.scrollV);
        scrollH = findViewById(R.id.scrollH);
        layoutGameMap = findViewById(R.id.layoutGameMap);

        //Event
        layoutGameMap.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        int viewId = event.getClipData().getItemAt(0).getIntent().getIntExtra("Id", -1);
                        createAnimal(viewId, event.getX(), event.getY());
                        ZooTycoonDB.addAnimal(new ZooTycoonDB.Animal(viewId, (int) event.getX(), (int) event.getY()));
                        return true;
                }
                return false;
            }
        }); //동물들의 드롭 처리

        View.OnLongClickListener animalDragDrop = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Id", view.getId());
                ClipData data = ClipData.newIntent("Image", intent);
                view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
                return false;
            }
        }; //동물들의 드래그 스타트
        findViewById(R.id.imagePenguin).setOnLongClickListener(animalDragDrop);
        findViewById(R.id.imageCrocodile).setOnLongClickListener(animalDragDrop);
        findViewById(R.id.imageDeer).setOnLongClickListener(animalDragDrop);
        findViewById(R.id.imageFox).setOnLongClickListener(animalDragDrop);
        findViewById(R.id.imageKoala).setOnLongClickListener(animalDragDrop);
        findViewById(R.id.imageSquirrel).setOnLongClickListener(animalDragDrop);
        findViewById(R.id.imageTurtle).setOnLongClickListener(animalDragDrop);

        View.OnLongClickListener zookeeperDragDrop = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (optionZookeeperExists) {
                    Intent intent = new Intent();
                    intent.putExtra("Id", view.getId());
                    ClipData data = ClipData.newIntent("Image", intent);
                    view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
                } else {
                    TooltipDialog tooltipDialog = new TooltipDialog(GameActivity.this);
                    tooltipDialog.show();
                    tooltipDialog.textContents.setText(getResources().getText(R.string.game_cant_hire_zookeeper));
                }
                return false;
            }
        }; //사육사의 드래그 스타트
        findViewById(R.id.zookeeper1).setOnLongClickListener(zookeeperDragDrop);
        findViewById(R.id.zookeeper2).setOnLongClickListener(zookeeperDragDrop);
        findViewById(R.id.zookeeper3).setOnLongClickListener(zookeeperDragDrop);

        start();
    }

    void start() {
        //Extras
        Intent intent = getIntent();
        optionMoneyInfinite = intent.getBooleanExtra("optionMoneyInfinite", true);
        optionVisitorFeeling = intent.getBooleanExtra("optionVisitorFeeling", true);
        optionZookeeperExists = intent.getBooleanExtra("optionZookeeperExists", true);
        optionGameSpeed = intent.getFloatExtra("optionGameSpeed", 3.0f);

        //SyncScroll
        syncScroll = new SyncScroll(scrollV, scrollH);
        syncScroll.start();

        //TooltipDialog
        TooltipDialog tooltipDialog = new TooltipDialog(this);
        tooltipDialog.show();
        tooltipDialog.textContents.setText(getResources().getText(R.string.game_tooltip));
        tooltipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                TooltipDialog tooltipDialog = new TooltipDialog(GameActivity.this);
                tooltipDialog.show();
                tooltipDialog.textContents.setText(getResources().getText(R.string.game_tooltip2));
            }
        });

        //DB에서 로드한 동물들을 불러옴
        int count = ZooTycoonDB.getAnimalsCount();
        for (int i = 0; i < count; i++) {
            ZooTycoonDB.Animal animal = ZooTycoonDB.getAnimal(i);
            createAnimal(animal.getViewId(), animal.getX(), animal.getY());
        }
    }

    private void createAnimal(final int viewId, float x, float y) {
        ImageView imageView = new ImageView(GameActivity.this);
        imageView.setImageDrawable(((ImageView) findViewById(viewId)).getDrawable());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        layoutGameMap.addView(imageView);

        imageView.setX(x);
        imageView.setY(y);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ZooTycoonDB.removeAnimal(new ZooTycoonDB.Animal(viewId, (int) view.getX(), (int) view.getY()));
                view.setX(-1000);
                view.setY(-1000);
                return false;
            }
        }); //동물 제거

        Animation animAnimeAlive = AnimationUtils.loadAnimation(GameActivity.this, R.anim.anime_alive);
        animAnimeAlive.setDuration(animAnimeAlive.getDuration() / (long) (optionGameSpeed + 1));
        imageView.startAnimation(animAnimeAlive);
    }
}