package com.example.host;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AssetsManager {

    public static final String TAG = "AssetsApkLoader";

    //从assets复制出去的apk的目标目录
    public static final String APK_DIR = "plugin_apk";

    //文件结尾过滤
    public static final String FILE_FILTER = ".apk";

    /**
     * 将资源文件中的apk文件拷贝到私有目录中
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void copyAllAssetsApk(Context context) {
        AssetManager assetManager = context.getAssets();
        long startTime = System.currentTimeMillis();
        InputStream in = null;
        OutputStream out = null;
        try {
            File dex = context.getDir(APK_DIR, Context.MODE_PRIVATE);
            dex.mkdir();
            String[] fileNames = assetManager.list("");
            if (fileNames == null) return;
            for (String fileName : fileNames) {
                if (!fileName.endsWith(FILE_FILTER)) {
                    continue;
                }
                in = assetManager.open(fileName);
                File pluginApkFile = new File(dex, fileName);
                if (pluginApkFile.exists() && pluginApkFile.length() == in.available()) {
                    Log.i(TAG, fileName + " no change");
                    continue;
                }
                Log.i(TAG, fileName + " changed");
                out = new FileOutputStream(pluginApkFile);
                byte[] buffer = new byte[2048];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                Log.i(TAG, fileName + " copy over");
            }
            Log.i(TAG, "copyAssets time = " + (System.currentTimeMillis() - startTime));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (out != null) {
                    out.flush();
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
