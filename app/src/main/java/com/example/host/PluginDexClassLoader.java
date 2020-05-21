package com.example.host;

import dalvik.system.DexClassLoader;


public class PluginDexClassLoader extends DexClassLoader {

    public PluginDexClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

}
