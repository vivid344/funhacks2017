package com.google.android.gms.samples.vision.barcodereader;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by b1014100_2 on 2017/01/15.
 */

public class BookLoadThread extends AsyncTask<String, String, String> {

    private TextView textView;
    private ImageView imageView;
    private String bookName;
/**
 * コンストラクタ
 */
public BookLoadThread(TextView textView, ImageView imageView, String bookName) {
        super();
        this.textView   = textView;
        this.imageView = imageView;
        this.bookName = bookName;
    }

/**
 * バックグランドで行う処理
 */
@Override
protected String doInBackground(String... value) {
        HttpURLConnection con = null;
        URL url = null;
        String urlSt = "http://52.198.212.162/server/Title/gettitle?message="+bookName;
        byte line[] = new byte[1024];
        String src = new String();
        try {
        // URLの作成
        url = new URL(urlSt);
        // 接続用HttpURLConnectionオブジェクト作成
        con = (HttpURLConnection)url.openConnection();
        // リクエストメソッドの設定
        con.setRequestMethod("GET");
        // リダイレクトを自動で許可しない設定
        con.setInstanceFollowRedirects(false);
        // URL接続からデータを読み取る場合はtrue
        con.setDoInput(true);
        // URL接続にデータを書き込む場合はtrue
        con.setDoOutput(true);

        // 接続
        con.connect(); // ①
        // 本文の取得
        InputStream in = con.getInputStream();
        int size;


        while (true) {
        size = in.read(line);
        Log.d("laod", "doInBackground: size = "+size);
        src += new String(line);
        if(src.contains("<br />")){
        int end = src.indexOf("<br />");
        src =  src.substring(0,end);
        break;
        }
        }

        Log.d("TEST", "doInBackground: "+src);
        in.close();

        } catch (MalformedURLException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }
        return src;
        }

/**
 * バックグランド処理が完了し、UIスレッドに反映する
 */
@Override
protected void onPostExecute(String result) {

        textView.setText(result);

        Uri uri = Uri.parse("http://thumbnail.image.rakuten.co.jp/@0_mall/book/cabinet/1711/9784088801711.jpg?_ex=200x200");
        Uri.Builder builder = uri.buildUpon();
        AsyncTaskHttpRequest task = new AsyncTaskHttpRequest(imageView);
        task.execute(builder);
        }
 }
