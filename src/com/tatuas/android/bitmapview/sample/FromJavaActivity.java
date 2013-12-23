package com.tatuas.android.bitmapview.sample;

import java.io.File;

import com.tatuas.android.bitmapview.BitmapView;
import com.tatuas.android.bitmapview.sample.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class FromJavaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        BitmapView bitmapView = new BitmapView(this);
        bitmapView.setImageFromFile(new File("/storage/emulated/0/20131223064159.jpg"), 600, 400);
        this.addContentView(bitmapView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub, menu);
        return true;
    }

}
