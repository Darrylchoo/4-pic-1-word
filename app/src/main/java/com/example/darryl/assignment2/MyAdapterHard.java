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

public class MyAdapterHard extends ArrayAdapter<String> {
    private int[] hard_level;
    private int[] hard_imgId;
    private Activity context;

    public MyAdapterHard(@NonNull Activity context, int[] level, int[] imgId) {
        super(context, R.layout.hard_selection_list_layout);
        this.context = context;
        this.hard_level = level;
        this.hard_imgId = imgId;
    }

    //get the number of hard levels
    @Override
    public int getCount() {
        return hard_level.length;
    }

    //get more view
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAdapterHard.ViewHolder viewHolder = new MyAdapterHard.ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.hard_selection_list_layout, parent, false);
            viewHolder.level = (TextView) convertView.findViewById(R.id.txtLevel);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyAdapterHard.ViewHolder) convertView.getTag();
        }

        viewHolder.level.setText("Level " + hard_level[position]);
        viewHolder.image.setImageResource(hard_imgId[position]);
        return convertView;
    }

    static class ViewHolder {
        TextView level;
        ImageView image;
    }
}
