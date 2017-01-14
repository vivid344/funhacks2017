package com.google.android.gms.samples.vision.barcodereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.List;

public class ListActivty extends AppCompatActivity {
    private final String TAG = "BookList";
    protected ListView listView;
    protected TextView titleText;
    BookListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activty);
        listView = (ListView) findViewById(R.id.booklist);
        adapter = new BookListAdapter(getApplicationContext());

        loadNCMBforBook();
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
    public void loadNCMBforBook(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("book");
        query.addOrderByAscending("areaid");
        //データストアからデータを検索
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> results, NCMBException e) {
                if (e != null) {
                    Log.d(TAG, "done: "+e);
                    //検索失敗時の処理
                } else {
                    for(NCMBObject result : results) {
                        adapter.add(new Book(result.getString("name"),result.getInt("locat"),result.getInt("areaid")));
                    }
                    //検索成功時の処理
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new SampleListItemClickListener(titleText));
                }
            }
        });
    }
}
