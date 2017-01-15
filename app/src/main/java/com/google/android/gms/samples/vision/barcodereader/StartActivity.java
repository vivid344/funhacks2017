package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.FindCallback;
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

public class StartActivity extends AppCompatActivity {
ArrayList<Area> areas = new ArrayList<>();

    int max = 0, min = 0;
    final  String TAG ="TEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
        loadNCMBforArea();
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


        fixedNMBCforBook(max,min);

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


    public void fixedNMBCforBook(final int first, final int second){
        //TestClassを検索するためのNCMBQueryインスタンスを作成

        NCMBQuery<NCMBObject> firstquery = new NCMBQuery<>("book");
        NCMBQuery<NCMBObject> secondquery = new NCMBQuery<>("book");

        firstquery.whereEqualTo("areaid",first);
        secondquery.whereEqualTo("areaid",second);

        //データストアからデータを検索
        secondquery.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> results, NCMBException e) {
                if (e != null) {
                    //検索失敗時の処理
                    Log.d(TAG, "done: "+e);
                } else {
                    //検索成功時の処理
                    for(NCMBObject result : results) {
                        NCMBObject firstobj = new NCMBObject("book");
                        firstobj.setObjectId(result.getObjectId());
                        firstobj.put("name", result.getString("name"));
                        firstobj.put("locat",result.getInt("locat"));
                        firstobj.put("areaid", first);
                        firstobj.saveInBackground(new DoneCallback() {
                            @Override
                            public void done(NCMBException e) {
                                if (e != null) {
                                    //エラー発生時の処理
                                } else {
                                    //成功時の処理
                                }
                            }
                        });
                        deleatNMBCforBooks(max);
                    }
                }
            }
        });

        firstquery.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> results, NCMBException e) {
                if (e != null) {
                    //検索失敗時の処理
                    Log.d(TAG, "done: "+e);
                } else {
                    //検索成功時の処理
                    int i = 0;
                    for(NCMBObject result : results) {
                        NCMBObject firstobj = new NCMBObject("book");
                        firstobj.setObjectId(result.getObjectId());
                        firstobj.put("name", result.getString("name"));
                        firstobj.put("locat",result.getInt("locat"));
                        firstobj.put("areaid", second);
                        firstobj.saveInBackground(new DoneCallback() {
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
            }
        });
    }

    public void deleatNMBCforBooks(final int most){
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("Books");
        query.whereEqualTo("position",most);
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> results, NCMBException e) {
                if (e != null) {
                    //検索失敗時の処理
                    Log.d(TAG, "done: "+e);
                } else {
                    //検索成功時の処理
                    for(NCMBObject result : results) {
                        NCMBObject obj = new NCMBObject("Books");
                        obj.setObjectId(result.getObjectId());
                        obj.deleteObjectInBackground(new DoneCallback() {
                            @Override
                            public void done(NCMBException e) {
                                if (e != null) {
                                    Assert.fail("delete object method should not raise exception:");
                                }
                            }
                        });
                    }
                    loadNCMBforArea();
                }
            }
        });

    }

    public void loadNCMBforArea(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("Books");
        //init areas
        areas = new ArrayList<>();
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
                        Log.d(TAG, "done: i,freq ="+result.getInt("position")+","+areas.get(result.getInt("position")).getFreq());
                    }
                    WriteAreaCsv();
                }
//                for(int i = 0 ; i < 9 ; i++)
//                    Log.d(TAG, "done: i,freq ="+i+","+areas.get(i).getFreq());
            }
        });
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
