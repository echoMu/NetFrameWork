package com.kit.netlibrary.manager.okhttp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import com.kit.netlibrary.manager.base.BaseNetManager;
import com.kit.netlibrary.utils.NetworkUtil;
import com.kit.netlibrary.callback.ResultCallback;
import com.kit.netlibrary.callback.StringCallback;

/**
 * OkHttpManager
 * Created by junbin on 16/5/25.
 */
public class OkHttpManager extends BaseNetManager {

    public static final String TAG="CommonLog";

    private Context context;
    private Gson mGson;

    private OkHttpManager(Context context) {
        //请求的属性由这个类设置
        this.context = context;
        mGson = new Gson();
    }

    private static OkHttpManager mInstance;

    public static OkHttpManager init(Context context) {
        if (mInstance == null) {
            synchronized (OkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpManager(context);
                } else {

                }
            }
        } else {
        }
        return mInstance;
    }

    /**
     * 上传单文件
     *
     * @param tag      标签
     * @param url      服务器路径
     * @param file     目标文件
     * @param params   参数
     * @param callBack 回调
     */
    @Override
    public void uploadFile(Object tag, String url, File file, Map<String, String> params, Callback<String> callBack) {
        OkHttpUtils
                .post().tag(tag)
                .params(params)
                .addFile("mFile", file.getName(), file)
                .url(url)
                .params(params).build().execute(callBack);
    }

    /**
     * 上传多文件
     *
     * @param tag          标签
     * @param url          服务器路径
     * @param filePathList 目标文件
     * @param params       参数
     * @param callBack     回调
     */
    @Override
    public void uploadFile(Object tag, String url, List<File> filePathList, Map<String, String> params, Callback<String> callBack) {
        PostFormBuilder builder = OkHttpUtils
                .post().tag(tag)
                .params(params);
        for (File file : filePathList) {
            Log.d(TAG,"添加文件：" + file.getName());
            builder.addFile("mFile", file.getName(), file);
        }
        builder.url(url)
                .params(params).build().execute(callBack);
    }

    /**
     * 下载文件
     *
     * @param tag
     * @param url
     * @param callBack
     */
    @Override
    public void downloadFile(Object tag, String url, FileCallBack callBack) {
        //String url = "https://workmap.b0.upaiyun.com/workmap_dianAn/WorkMapForDA1.2.0.apk";
        OkHttpUtils
                .get()
                .url(url).tag(tag)
                .build()
                .execute(
                        callBack);
    }

    /**
     * 取消某条请求
     *
     * @param tag 标签
     */
    public static void cancelRequest(Object tag) {

        OkHttpUtils.getInstance().cancelTag(tag);
    }

    /**
     * get请求
     *
     * @param url
     * @param stringCallback
     */
    public void requestGet(String url, StringCallback stringCallback) {
        Log.d(TAG,"get请求:url " + url);
        OkHttpUtils.get().url(url).build().execute(stringCallback);
    }

    /**
     * get请求 参数以键值对形式传入
     *
     * @param url
     * @param callBack
     * @param params
     */
    @Override
    public void requestGet(String url, Map<String, String> params, final ResultCallback callBack) {
        Log.d(TAG,"get请求:url " + url);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Log.d(TAG,"get请求:" + key + " " + params.get(key));
            }
        }

        StringCallback stringCallback=new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                callBack.onErrorResponse(e,e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                callBack.onResponse(response);
            }
        };

        if (NetworkUtil.networkEnable(context)) {
            OkHttpUtils.get().url(url).params(params).build().execute(stringCallback);
        } else {
            Log.d(TAG,"网络不可用");
            callBack.onNetWorkUnable();
        }
    }

    /**
     * post请求
     *
     * @param tag
     * @param url 完整地址
     * @param params
     * @param callback
     */
    @Override
    public void requestPost(Object tag, final String url, Map<String, String> params, final ResultCallback callback) {

        if (tag == null) {
            tag = url;
        }

        //输出请求的信息
        Log.d(TAG,"请求的完整路径：" + url);

        Map<String, String> params0 = new HashMap<String, String>();
        if (params != null) {
            for (String key : params.keySet()) {
                if (!TextUtils.isEmpty(params.get(key))) {
                    //过滤空的参数
                    Log.d(TAG,"请求参数:" + key + " " + params.get(key));
                    params0.put(key, params.get(key));
                }
            }
        }

        final PostFormBuilder builder = buildRequestBuilder(tag, url, params0);

        final RequestCall call = builder.build();
        final Callback lastCallback = new Callback<String>() {

            @Override
            public String parseNetworkResponse(Response response, int id) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d(TAG,"发生错误:" + e.getMessage());
                callback.onErrorResponse(e, e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d(TAG,"onResponse:" + url + "\n 请求结果：" + response);

                try {

                    if (callback.mType == String.class) {
                        callback.onResponse(response);
                    } else {
                        Object o = mGson.fromJson(response, callback.mType);
                        callback.onResponse(o);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onErrorResponse(e, getResponseMsg(response));
//                        callback.onErrorResponse(null, e);
                }
            }
        };

        if (NetworkUtil.networkEnable(context)) {
            call.execute(lastCallback);
        } else {
            Log.d(TAG,"网络不可用");
            callback.onNetWorkUnable();
        }

    }


    /**
     * 获取服务器返回的数据
     *
     * @param json
     * @return
     */
    private String getResponseMsg(String json) {
        String msg;
        JSONObject object;
        try {
            object = new JSONObject(json);
            msg = object.getString("userMsg");
            if (!object.has("userMsg")) {
                msg = json;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return json;
        }


        return msg;
    }

    /**
     * 构造请求
     *
     * @param tag    标签
     * @param url    目标URL
     * @param params 参数
     * @return
     */
    private PostFormBuilder buildRequestBuilder(Object tag, String url, Map<String, String> params) {


        return OkHttpUtils.post().url(url).params(params).tag(tag);

    }

}
