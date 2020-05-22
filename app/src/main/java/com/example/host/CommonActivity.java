package com.example.host;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Field;

public class CommonActivity extends Activity {

    private Resources mBundleResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
    }

    private void loadView() {
        int layoutId = mBundleResources.getIdentifier("activity_main", "layout", "com.example.plugin");
        View pluginView = LayoutInflater.from(this).inflate(layoutId, null);
        setContentView(pluginView);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    /**
     * 使用反射的方式，使用Bundle的Resource对象，替换Context的mResources对象
     */
    public void replaceContextResources(Context context) {
        try {
            //Activity ==>ContextThemeWrapper ==>ContextWrapper ==>Context
            //ContextThemeWrapper private Resources mResources;   ContextImpl private @NonNull Resources mResources;
            Field mResourcesField = context.getClass().getDeclaredField("mResources");
            mResourcesField.setAccessible(true);
            if (mBundleResources == null) {
                mBundleResources = PluginResourceLoader.pluginResMap.get("bundle_apk");
            }
            mResourcesField.set(context, mBundleResources);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

