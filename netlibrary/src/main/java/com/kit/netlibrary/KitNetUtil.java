package com.kit.netlibrary;

import android.util.Log;

import com.kit.netlibrary.callback.ResultCallback;
import com.kit.netlibrary.manager.base.BaseNetManager;
import com.kit.netlibrary.manager.okhttp.OkHttpManager;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 网络请求通用层
 * Created by junbin on 2016/11/4.
 */

public class KitNetUtil {

    public static final String TAG="CommonLog";
    private BaseNetManager manager;

    private KitNetUtil(BaseNetManager manager) {
        //请求的属性由这个类设置
        this.manager = manager;
    }

    private static KitNetUtil mInstance;

    public static KitNetUtil init(BaseNetManager manager) {
        if (mInstance == null) {
            synchronized (OkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new KitNetUtil(manager);
                } else {

                }
            }
        } else {
        }
        return mInstance;
    }

    public void requestGet(String url, Map<String, String> params, ResultCallback callBack) {
        Log.d(TAG,"get...");
        manager.requestGet(url, params, callBack);
    }

    public void requestPost(Object tag, String url, Map<String, String> params, ResultCallback callback) {
        Log.d(TAG,"post...");
        manager.requestPost(tag, url, params, callback);
    }

    public void downloadFile(Object tag, String url, FileCallBack callBack) {
        Log.d(TAG,"downloadFile...");
        manager.downloadFile(tag, url, callBack);
    }

    public void uploadFile(Object tag, String url, File file, Map<String, String> params, Callback<String> callBack) {
        Log.d(TAG,"uploadFile...");
        manager.uploadFile(tag, url, file, params, callBack);
    }

    public void uploadFile(Object tag, String url, List<File> filePathList, Map<String, String> params, Callback<String> callBack) {
        Log.d(TAG,"uploadFile...");
        manager.uploadFile(tag, url, filePathList, params, callBack);
    }
}
