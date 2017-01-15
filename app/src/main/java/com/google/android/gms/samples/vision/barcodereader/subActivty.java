package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class subActivty extends Activity {
private final String TAG = "SubActivity";
TextView Bookname;
    ImageView Bookimage;
    String BookImageResouce;
    Button button;
    private BookLoadThread task ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_activty);

        //get id
        Bookname = (TextView) findViewById(R.id.test);
        Bookimage = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+Bookname.getText());
            }
        });

        getBookName();
    }

    public void getBookName(){
        Intent data = getIntent();
        Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
        Log.d(TAG, "Barcode read: " + barcode.displayValue);
        task = new BookLoadThread(Bookname,Bookimage,button,barcode.displayValue);
        task.execute("TEST");
    }

    public void kanryo(View v) {
        Intent sub_activity = new Intent();
        sub_activity.setClassName("com.google.android.gms.samples.vision.barcodereader", "com.google.android.gms.samples.vision.barcodereader.AddActivity");
        startActivity(sub_activity);
    }

    public void cancel(View v){
        Intent sub_activity = new Intent();
        sub_activity.setClassName("com.google.android.gms.samples.vision.barcodereader", "com.google.android.gms.samples.vision.barcodereader.StartActivity");
        startActivity(sub_activity);
    }
}
