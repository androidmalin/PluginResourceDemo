package com.example.host;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 如何加载未安装apk中的资源文件呢？我们从android.content.res.AssetManager.java的源码中发现，它有一个私有方法addAssetPath，只需要将apk的路径作为参数传入，我们就可以获得对应的AssetsManager对象，然后我们就可以使用AssetsManager对象，创建一个Resources对象，然后就可以从Resource对象中访问apk中的资源了。总结如下：
 * 1.新建一个AssetManager对象
 * 2.通过反射调用addAssetPath方法
 * 3.以AssetsManager对象为参数，创建Resources对象即可。
 */
public class MainActivity extends Activity {

    private TextView invokeTv;
    private TextView tvPlugin;
    private ImageView ivPlugin;

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
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Resources resources = PluginResourceLoader.getPluginResource(getApplicationContext());
                //0x7f040001;test_str;I am in bundle str
                String pluginString = resources.getString(resources.getIdentifier("test_str", "string", "com.example.plugin"));
                tvPlugin.setText("pluginString:" + pluginString);

                //0x7f010001;plugin_img;res/drawable-xxhdpi-v4/plugin_img.png
                @SuppressLint("UseCompatLoadingForDrawables")
                Drawable pluginDrawable = resources.getDrawable(resources.getIdentifier("plugin_img", "drawable", "com.example.plugin"));//注意，id参照Bundle apk中的R文件
                ivPlugin.setImageDrawable(pluginDrawable);
            }
        });

    }

    private void initPluginApk() {
        BundleClassLoaderManager.install(getApplicationContext());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        invokeTv = findViewById(R.id.iv_load_plugin_resource);
        ivPlugin = findViewById(R.id.iv_plugin_img);
        tvPlugin = findViewById(R.id.tv_plugin_text);
    }
}
