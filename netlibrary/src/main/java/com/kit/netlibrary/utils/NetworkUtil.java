package com.kit.netlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

/**
 * 网络相关工具类
 * Created by junbin on 2016/11/8.
 */

public class NetworkUtil {
    private static Gson gson;

    /**
     * 得到解析工具gson
     *
     * @return
     */
    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * 检测网络是否可用
     *
     * @return true为网络可用，否则不可用
     */
    public static boolean networkEnable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifiInfo != null && wifiInfo.isConnected())
                || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        }
        return false;
    }

    /**
     * 跳转到网络设置界面
     */
    public static void toNetWorkSetting(Context context) {
        Intent intent = null;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
//			intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        } else {
//			intent = new Intent();
//			ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
//			intent.setComponent(component);
//			intent.setAction("android.intent.action.VIEW");
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }
        context.startActivity(intent);
    }

    /**
     * 获取设备的网络类型
     * @param context
     */
    public static int checkNetWorkState(Context context){
        //检测当前网络类型
        //获取手机的连接服务管理器，这里是连接管理器类
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
            //处于移动网络
            return 0;
        }else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
            //处于无线网络，继续下面的操作
            return 1;
        } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
            //无网络
            return 2;
        }

        return -1;
    }
}
