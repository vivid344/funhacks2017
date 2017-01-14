package com.google.android.gms.samples.vision.barcodereader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookListAdapter extends ArrayAdapter<Book> {

    LayoutInflater mInflater;


    public BookListAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.booklist, parent, false);
        }
Book book = getItem(position);
        TextView tv = (TextView) convertView.findViewById(R.id.booklist_text);
tv.setText(book.getName());
        return convertView;
    }
}
