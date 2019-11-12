package com.example.darryl.assignment2;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapterMedium extends ArrayAdapter<String> {
    private int[] medium_level;
    private int[] medium_imgId;
    private Activity context;

    public MyAdapterMedium(@NonNull Activity context, int[] level, int[] imgId) {
        super(context, R.layout.medium_selection_list_layout);
        this.context = context;
        this.medium_level = level;
        this.medium_imgId = imgId;
    }

    //get the number of medium levels
    @Override
    public int getCount() {
        return medium_level.length;
    }

    //get more view
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAdapterMedium.ViewHolder viewHolder = new MyAdapterMedium.ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.medium_selection_list_layout, parent, false);
            viewHolder.level = (TextView) convertView.findViewById(R.id.txtLevel);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyAdapterMedium.ViewHolder) convertView.getTag();
        }

        viewHolder.level.setText("Level " + medium_level[position]);
        viewHolder.image.setImageResource(medium_imgId[position]);
        return convertView;
    }

    static class ViewHolder {
        TextView level;
        ImageView image;
    }
}
