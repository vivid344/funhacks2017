/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.vision.barcodereader;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.app.Activity;

import android.util.Log;
import android.view.View;

import android.widget.TextView;


import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import junit.framework.Assert;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.

    private TextView statusMessage;
    private TextView barcodeValue;
    ArrayList<Area> areas = new ArrayList<>();
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private final int MAX_SZIE = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NCMB.initialize(this.getApplicationContext(),"756b1daf9fec0415980c27527ffc3bda367dac55314756ad760c3095e3983f90","143921116f66ecb1f39062f21ff7bb7d5fbea8698b3146eb7b73d82b85abaa8c");

        findViewById(R.id.read_barcode).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
       // if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            // Intent intent = new Intent(this, BarcodeCaptureActivity.class);

            //startActivityForResult(intent, RC_BARCODE_CAPTURE);
       // }
        Intent intent = new Intent(this, ListActivty.class);
        startActivity(intent);
    }

    public void loadNCMBforArea(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("Books");
        //init areas
        for(int i = 0 ; i < 9 ; i++)
            areas.add(new Area(i,0));
        //データストアからデータを検索
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> results, NCMBException e) {
                if (e != null) {
                    //検索失敗時の処理
                    Log.d(TAG, "done: "+e);
                } else {
                    //検索成功時の処理
                    for(NCMBObject result : results) {
                        areas.get(result.getInt("position")).addfreq();
                    }
                    WriteAreaCsv();
                }
            }
        });
    }

    public void loadNCMBforMode(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("Books");

        final int modeNum[] = new int[MAX_SZIE];
        for(int i = 0 ; i <MAX_SZIE ; i++)
            modeNum[i] = 0;
        //降順+14
        query.addOrderByDescending("updateDate");
        query.setLimit(14);
        //データストアからデータを検索
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> results, NCMBException e) {
                if (e != null) {
                    Log.d(TAG, "done: "+e);
                    //検索失敗時の処理
                } else {
                    for(NCMBObject result : results) {
                        String cm = result.getString("cm");
                        int mode = new Integer(cm).intValue();
                        modeNum[mode]++;
                    }
                    //検索成功時の処理
                    //getModd
                    Log.d(TAG, "done: "+getMax(modeNum));
                }
            }
        });
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
                        Log.d(TAG, "done: name = "+result.getString("name"));
                    }
                    //検索成功時の処理
                }
            }
        });
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

    public int getMax(int nums[]){
        int max = -1;
        for(int i = 0 ; i < nums.length ; i++)
            if(max < nums[i])
                max = nums[i];

        return max;
    }

    public void ReadAreaCsv() {
        // AssetManagerの呼び出し
        AssetManager assetManager = getResources().getAssets();
        try {
            String openFileName = "area.csv";
            FileInputStream input = this.openFileInput(openFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(input);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line = "";
            int i = 0;
            while ((line = bufferReader.readLine()) != null) {
                // 各行が","で区切られていて4つの項目があるとす
                StringTokenizer st = new StringTokenizer(line, ",");
                while (st.hasMoreTokens()){
                    areas.add(new Area(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())));
                }
            }
            bufferReader.close();
            // ストリームを閉じる
            input.close();
                for(Area area : areas)
                    Log.d(TAG, "ReadAreaCsv: "+area.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteAreaCsv() {
        String FileName = "area.csv";
        try {
            // 書き込み先のストリームを開く
            FileOutputStream output = this.openFileOutput(FileName, MODE_PRIVATE);

            for(Area area : areas) {
                String line = area.getId()+","+area.getFreq();//id,fre q
                output.write(line.getBytes());
                output.write("\n".getBytes());
            }
            // ストリームを閉じる
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
