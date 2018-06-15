package com.jungu.zootycoon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//아이콘, 제목, 내용의 어댑터
public class IconTitleContentsAdapter extends BaseAdapter {
    private ArrayList<IconTitleContentsItem> items = new ArrayList<IconTitleContentsItem>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_icon_title_contents, parent, false);
        }

        ImageView iconImage = convertView.findViewById(R.id.imageIcon);
        TextView titleText = convertView.findViewById(R.id.textTitle);
        TextView textContents = convertView.findViewById(R.id.textContents);

        IconTitleContentsItem listViewItem = items.get(position);

        iconImage.setImageDrawable(listViewItem.getIcon());
        titleText.setText(listViewItem.getTitle());
        textContents.setText(listViewItem.getContents());

        return convertView;
    }

    public void addItem(Drawable icon, String title, String contents) {
        IconTitleContentsItem item = new IconTitleContentsItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setContents(contents);

        items.add(item);
    }
}