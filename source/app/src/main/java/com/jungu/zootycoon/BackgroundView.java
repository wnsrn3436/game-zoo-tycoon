package com.jungu.zootycoon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

//게임의 백그라운드를 그려주는 뷰
public class BackgroundView extends ScrollView {
    public BackgroundView(Context context) {
        super(context);
        initView();
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.view_background, this, false);
        addView(layout);
    }
}