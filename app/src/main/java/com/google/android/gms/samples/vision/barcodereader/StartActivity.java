package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StartActivity extends AppCompatActivity {
ArrayList<Area> areas = new ArrayList<>();
    private final String TAG = "sort";
    int max = -1,min = 10000000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
    }

    public void touroku(View v){
        Intent sub_activity = new Intent();
        sub_activity.setClassName("com.google.android.gms.samples.vision.barcodereader", "com.google.android.gms.samples.vision.barcodereader.MainActivity");
        startActivity(sub_activity);
    }

    public void list(View v){
        Intent list_activity = new Intent();
        list_activity.setClassName("com.google.android.gms.samples.vision.barcodereader", "com.google.android.gms.samples.vision.barcodereader.ListActivty");
        startActivity(list_activity);
    }
    public void koukan(View v){
        ImageView img1 = (ImageView) findViewById(R.id.imageView1);
        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        ImageView img4 = (ImageView) findViewById(R.id.imageView4);
        ImageView img5 = (ImageView) findViewById(R.id.imageView5);
        ImageView img6 = (ImageView) findViewById(R.id.imageView6);
        ImageView img7 = (ImageView) findViewById(R.id.imageView7);
        ImageView img8 = (ImageView) findViewById(R.id.imageView8);
        ImageView img9 = (ImageView) findViewById(R.id.imageView9);

        ReadAreaCsv();//最大、最小の取得
        TranslateAnimation translate = new TranslateAnimation(0, 100, 0, 100); // (0,0)から(100,100)に移動

        translate.setDuration(3000); // 3000msかけてアニメーションする
        img4.startAnimation(translate); // アニメーション適用


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
                    int id  = Integer.parseInt(st.nextToken());
                    int freq = Integer.parseInt(st.nextToken());
                    areas.add(new Area(id,freq));
                if(max < freq)
                    max = freq;
                    if(min > freq)
                        min = freq;
                }
            }
            bufferReader.close();
            // ストリームを閉じる
            input.close();
            for(Area area : areas)
                Log.d(TAG, "ReadAreaCsv: "+area.getFreq());
            Log.d(TAG, "ReadAreaCsv: max = "+max+", min = "+ min);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
