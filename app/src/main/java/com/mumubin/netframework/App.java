package com.mumubin.netframework;

import android.app.Application;
import android.content.Context;

import com.kit.netlibrary.KitNetUtil;
import com.kit.netlibrary.manager.okhttp.OkHttpManager;

/**
 * Created by junbin on 2016/11/9.
 */

public class App extends Application{

    /** 对外提供整个应用生命周期的Context **/
    private static Context instance;
    private static KitNetUtil kitNetUtil;

    public static Context getInstance() {
        return instance;
    }

    public static KitNetUtil getKitNetUtil() {
        return kitNetUtil;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance=getApplicationContext();

        OkHttpManager manager=OkHttpManager.init(instance);
        kitNetUtil=KitNetUtil.init(manager);
    }
}
