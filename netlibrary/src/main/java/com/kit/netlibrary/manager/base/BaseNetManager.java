package com.kit.netlibrary.manager.base;

import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.kit.netlibrary.callback.ResultCallback;

/**
 * 第三方网络库的规范基础类
 * 接入其它网络库需继承此类
 * Created by junbin on 2016/11/4.
 */

public abstract class BaseNetManager {
    public abstract void requestGet(String url, Map<String, String> params, ResultCallback callBack);

    public abstract void requestPost(Object tag, String url, Map<String, String> params, final ResultCallback callBack);

    public abstract void downloadFile(Object tag, String url, FileCallBack callBack);

    public abstract void uploadFile(Object tag, String url, File file, Map<String, String> params, Callback<String> callBack);

    public abstract void uploadFile(Object tag, String url, List<File> filePathList, Map<String, String> params, Callback<String> callBack);
}
