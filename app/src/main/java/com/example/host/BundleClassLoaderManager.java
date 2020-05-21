package com.example.host;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class BundleClassLoaderManager {

    public static List<PluginDexClassLoader> pluginDexClassLoaderList = new ArrayList<>();

    /**
     * 加载Assets里的apk文件
     */
    public static void install(Context context) {
        AssetsManager.copyAllAssetsApk(context);
        // 获取dex文件列表
        File dexDir = context.getDir(AssetsManager.APK_DIR, Context.MODE_PRIVATE);
        File[] szFiles = dexDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(AssetsManager.FILE_FILTER);
            }
        });
        if (szFiles == null) return;
        for (File f : szFiles) {
            System.out.println("debug:load file:" + f.getName());
            //加载apk，生成对应的ClassLoader
            PluginDexClassLoader pluginDexClassLoader = new PluginDexClassLoader(
                    f.getAbsolutePath(),
                    dexDir.getAbsolutePath(),
                    null,
                    context.getClassLoader()
            );
            pluginDexClassLoaderList.add(pluginDexClassLoader);
        }
    }

    /**
     * 查找类
     */
    public static Class<?> loadClass(Context context, String className) throws ClassNotFoundException {
        try {
            Class<?> clazz = context.getClassLoader().loadClass(className);
            if (clazz != null) {
                System.out.println("debug: class find in main classLoader");
                return clazz;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (PluginDexClassLoader pluginDexClassLoader : pluginDexClassLoaderList) {
            try {
                Class<?> clazz = pluginDexClassLoader.loadClass(className);
                if (clazz != null) {
                    System.out.println("debug: class find in bundle classLoader");
                    return clazz;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new ClassCastException(className + " not found exception");
    }
}
