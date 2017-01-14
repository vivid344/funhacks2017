package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.google.android.gms.vision.barcode.Barcode;

public class subActivty extends Activity {
private final String TAG = "SubActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_activty);
        getBookName();
    }

    public void getBookName(){
        Intent data = getIntent();
        Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
    }
}
