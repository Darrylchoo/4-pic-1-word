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

public class MyAdapterEasy extends ArrayAdapter<String> {
    private int[] easy_level;
    private int[] easy_imgId;
    private Activity context;

    public MyAdapterEasy(@NonNull Activity context, int[] level, int[] imgId) {
        super(context, R.layout.easy_selection_list_layout);
        this.context = context;
        this.easy_level = level;
        this.easy_imgId = imgId;
    }

    //get the number of easy levels
    @Override
    public int getCount() {
        return easy_level.length;
    }

    //get more view
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.easy_selection_list_layout, parent, false);
            viewHolder.level = (TextView) convertView.findViewById(R.id.txtLevel);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.level.setText("Level " + easy_level[position]);
        viewHolder.image.setImageResource(easy_imgId[position]);
        return convertView;
    }

    static class ViewHolder {
        TextView level;
        ImageView image;
    }
}
