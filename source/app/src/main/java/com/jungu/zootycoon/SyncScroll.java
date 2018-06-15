package com.jungu.zootycoon;

import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

//수직, 수평 스크롤을 지원해주는 클래스
public class SyncScroll implements View.OnTouchListener {
    //Member
    private int preX, preY;
    private boolean isClicked;

    //View
    private ScrollView scrollV;
    private HorizontalScrollView scrollH;

    SyncScroll(ScrollView scrollV, HorizontalScrollView scrollH) {
        this.scrollV = scrollV;
        this.scrollH = scrollH;
    }

    public void start() {
        scrollV.setFadingEdgeLength(0);
        scrollH.setFadingEdgeLength(0);

        scrollV.setOnTouchListener(this);
        scrollH.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!isClicked) {
                    isClicked = true;
                    preX = (int) event.getRawX();
                    preY = (int) event.getRawY();
                } else {
                    int currentX = (int) event.getRawX();
                    int currentY = (int) event.getRawY();
                    scrollH.scrollBy(preX - currentX, 0);
                    scrollV.scrollBy(0, preY - currentY);
                    preX = currentX;
                    preY = currentY;
                }
                break;
            case MotionEvent.ACTION_UP:
                isClicked = false;
                break;
        }
        return true;
    }
}