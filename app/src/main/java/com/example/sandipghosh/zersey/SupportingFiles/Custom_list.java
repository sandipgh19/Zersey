package com.example.sandipghosh.zersey.SupportingFiles;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sandipghosh.zersey.R;

/**
 * Created by sandipghosh on 10/05/17.
 */

public class Custom_list extends ArrayAdapter<String> {
    private String[] name;
    private String[] comment;
    private Activity context;

    public Custom_list(Activity context, String[] name, String[] comment) {
        super(context, R.layout.list_item, name);
        this.context = context;
        this.name = name;
        this.comment = comment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);
        TextView textView_name = (TextView) listViewItem.findViewById(R.id.name);
        TextView textView_gender = (TextView) listViewItem.findViewById(R.id.comment);

        textView_name.setText(name[position]);
        textView_gender.setText(comment[position]);

        return listViewItem;
    }
}
