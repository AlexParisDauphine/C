package com.example.alexandrepc.kanji2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.util.List;
import java.util.Map;

/**
 * Created by Jibril on 12/02/2015.
 */
public class ListSimpleAdapter extends SimpleAdapter {


    public ListSimpleAdapter (Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to){
        super (context, data, resource, from, to);

    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = super.getView(position, convertView, parent);

        row.setBackgroundColor(Color.TRANSPARENT);
        return row;
    }*/



}
