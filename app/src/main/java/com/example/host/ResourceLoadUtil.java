package com.example.host;

import android.content.Context;

/**
 * 资源反射类
 * https://blog.csdn.net/luzhenyuxfcy/article/details/52225414
 */
public class ResourceLoadUtil {

    private static int getResourceId(Context context, String name, String type) {
        int id;
        id = context.getResources().getIdentifier(name, type, context.getPackageName());
        return id;
    }

    public static int getViewId(Context context, String name) {
        return getResourceId(context, name, "id");
    }

    public static int getLayoutId(Context context, String name) {
        return getResourceId(context, name, "layout");
    }

    public static int getStringId(Context context, String name) {
        return getResourceId(context, name, "string");
    }

    public static int getDrawableId(Context context, String name) {
        return getResourceId(context, name, "drawable");
    }

    public static int getStyleId(Context context, String name) {
        return getResourceId(context, name, "style");
    }

    public static int getDimenId(Context context, String name) {
        return getResourceId(context, name, "dimen");
    }

    public static int getArrayId(Context context, String name) {
        return getResourceId(context, name, "array");
    }

    public static int getColorId(Context context, String name) {
        return getResourceId(context, name, "color");
    }

    public static int getAnimId(Context context, String name) {
        return getResourceId(context, name, "anim");
    }
}