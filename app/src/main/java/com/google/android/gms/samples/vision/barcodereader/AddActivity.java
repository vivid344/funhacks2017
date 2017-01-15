package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void add(View v){
        Intent sub_activity = new Intent();
        saveNCMBforBook("人工知能の基礎", 3 , 0);
        sub_activity.setClassName("com.google.android.gms.samples.vision.barcodereader", "com.google.android.gms.samples.vision.barcodereader.StartActivity");
        startActivity(sub_activity);
    }

    public void saveNCMBforBook(String name, int location , int areaid){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBObject obj = new NCMBObject("book");
        obj.put("name", name);
        obj.put("locat",location);
        obj.put("areaid", areaid);
        obj.saveInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    //エラー発生時の処理
                } else {
                    //成功時の処理
                }
            }
        });
    }
}
