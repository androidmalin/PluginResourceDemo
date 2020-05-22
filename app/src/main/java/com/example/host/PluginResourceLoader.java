package com.example.host;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PluginResourceLoader {

    public static Map<String, Resources> pluginResMap = new HashMap<>();

    /**
     * 获取Plugin中的资源
     */
    public static Resources getPluginResource(Context context) {
        //AssetsManager.copyAllAssetsApk(context);
        File dir = context.getDir(AssetsManager.APK_DIR, Context.MODE_PRIVATE);
        String apkPath = dir.getAbsolutePath() + "/plugin-debug.apk";
        System.out.println("debug:apkPath = " + apkPath + ",exists=" + (new File(apkPath).exists()));
        AssetManager assetManager = createAssetManager(apkPath);
        Resources resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        pluginResMap.put("bundle_apk", resources);
        return resources;
    }

    @SuppressWarnings("JavaReflectionMemberAccess")
    @SuppressLint({"PrivateApi", "DiscouragedPrivateApi"})
    private static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, apkPath);
            return assetManager;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
