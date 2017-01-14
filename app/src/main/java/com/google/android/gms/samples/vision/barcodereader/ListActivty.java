package com.google.android.gms.samples.vision.barcodereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivty extends AppCompatActivity {

    protected ListView listView;
    protected TextView titleText;
    private String[] names = {"taro", "jiro", "saburo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activty);
        if (savedInstanceState == null) {
            listView = (ListView) findViewById(R.id.sample_list);
            titleText = (TextView) findViewById(R.id.title_sample_list_text);
        }
        listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names));
        listView.setOnItemClickListener(new SampleListItemClickListener(titleText));
    }

    static class SampleListItemClickListener implements ListView.OnItemClickListener {

        private final TextView textView;

        SampleListItemClickListener(TextView titleView) {
            this.textView = titleView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            String item = (String) listView.getItemAtPosition(position);
            textView.setText(item);
        }
    }
}