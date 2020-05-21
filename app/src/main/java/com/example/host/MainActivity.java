package com.example.host;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView invokeTv;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initPluginApk();
        initListener();
    }

    @SuppressLint("ResourceType")
    private void initListener() {
        invokeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources resources = BundleResourceLoader.getBundleResource(getApplicationContext());
                //0x7f040001;test_str;I am in bundle str
                String str = resources.getString(resources.getIdentifier("test_str", "string", "com.example.plugin"));
                String strById = resources.getString(0x7f040001);//注意，id参照Bundle apk中的R文件
                System.out.println("debug:" + str);
                Toast.makeText(getApplicationContext(), strById, Toast.LENGTH_SHORT).show();

                //0x7f010001;plugin_img;res/drawable-xxhdpi-v4/plugin_img.png
                @SuppressLint("UseCompatLoadingForDrawables")
                Drawable drawable = resources.getDrawable(0x7f010001);//注意，id参照Bundle apk中的R文件
                imageView.setImageDrawable(drawable);
            }
        });

    }

    private void initPluginApk() {
        BundleClassLoaderManager.install(getApplicationContext());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        invokeTv = findViewById(R.id.iv_load_plugin_resource);
        imageView = findViewById(R.id.image_view_iv);
    }
}
