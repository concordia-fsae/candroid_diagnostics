package com.example.candroid_diagnostics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.candroid_diagnostics.CustomConfigMenu;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<CustomConfigMenu> {

    public SpinnerAdapter(Context context, List<CustomConfigMenu> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        CustomConfigMenu item = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(item.getDisplayText());

        return convertView;
    }
}
