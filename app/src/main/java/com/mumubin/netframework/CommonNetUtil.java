package com.mumubin.netframework;

import android.os.Handler;
import android.os.Message;

import com.kit.netlibrary.callback.ResultCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junbin on 2016/11/9.
 */

public class CommonNetUtil {

    /**
     * 获取天气成功
     */
    public static final int GETWEATHER_SUCCESS = 47;

    /**
     * shareSDK调用天气API 的 key
     */
    public final static String KEY_SHARESDK_WEATHER="e73b2dcf3a78";

    /**
     * 调用shareSDK天气API获取天气信息
     *
     * @param city    城市名 例如广州市
     * @param handler
     */
    public static void getWeather(String city, final Handler handler) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", KEY_SHARESDK_WEATHER);
        params.put("city", city.replace("市", ""));

        App.getKitNetUtil().requestGet("http://apicloud.mob.com/v1/weather/query", params, new ResultCallback<String>() {
            @Override
            public void onResponse(String response) {
                CommonLog.d(getClass(), "获得天气信息：" + response);

                JSONObject resultJSON;
                try {
                    resultJSON = new JSONObject(response);

                    String retCode = resultJSON.getString("retCode");
                    String resultMsg = resultJSON.getString("msg");
                    String weatherString = resultJSON.getString("result");

                    if ("200".equals(retCode)) {
                        // 构建JSON数组
                        JSONArray weatherArray = new JSONArray(weatherString);
                        JSONObject weatherJSON = new JSONObject(weatherArray.get(0).toString());
                        //获取区域
                        String distrct = weatherJSON.getString("distrct");
                        //获取温度
                        String temperature = weatherJSON.getString("temperature");
                        Weather weather = new Weather(distrct, temperature);

                        Message msg = new Message();
                        msg.what = GETWEATHER_SUCCESS;
                        msg.obj = weather;
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    CommonLog.d(getClass(), "发生错误");
                }
            }

            @Override
            public void onNetWorkUnable() {

            }

            @Override
            public void onErrorResponse(Exception e, String errorMsg) {
                CommonLog.d(getClass(), "请求失败+" + e.getMessage() + e.getCause());
            }
        });

    }
}
