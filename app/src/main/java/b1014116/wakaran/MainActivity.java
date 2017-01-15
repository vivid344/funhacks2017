package b1014116.wakaran;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


        TranslateAnimation translate = new TranslateAnimation(0, 100, 0, 100); // (0,0)から(100,100)に移動

        translate.setDuration(3000); // 3000msかけてアニメーションする
        img4.startAnimation(translate); // アニメーション適用
    }

}
