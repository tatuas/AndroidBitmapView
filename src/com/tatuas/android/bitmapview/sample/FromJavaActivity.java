package com.tatuas.android.bitmapview.sample;

import com.tatuas.android.bitmapview.BitmapView;
import com.tatuas.android.bitmapview.sample.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class FromJavaActivity extends Activity {

    public BitmapView bitmapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        bitmapView = new BitmapView(this, "/storage/emulated/0/test.jpg");
        bitmapView.setImageQuality(Bitmap.Config.RGB_565);

        LinearLayout l = (LinearLayout) findViewById(R.id.imgLayout);
        l.addView(bitmapView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapView.updateImageFromFile("/storage/emulated/0/test2.jpg");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub, menu);
        return true;
    }

}
