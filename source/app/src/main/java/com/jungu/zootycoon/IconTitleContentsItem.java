package com.jungu.zootycoon;

import android.graphics.drawable.Drawable;

public class IconTitleContentsItem {
    private Drawable icon;
    private String title;
    private String contents;

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}