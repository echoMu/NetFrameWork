package com.mumubin.netframework;

import android.util.Log;


/**
 * 日志控制类
 */
public class CommonLog {
	/**
     * 是否开启debug
     */
    public static boolean isDebug= BuildConfig.DEBUG;
    /**
     * tag
     */
    public static String TAG="CommonLog";

    /**
     * 调试
     * @param clazz
     * @param msg
     */
    public static void d(Class<?> clazz, String msg){
        if(isDebug){
            Log.d(TAG, clazz.getSimpleName()+"--"+msg);
        }
    }
    /**
     * 错误
     * @param clazz
     * @param msg
     */
    public static void e(Class<?> clazz, String msg){
        if(isDebug){
            Log.e(TAG, clazz.getSimpleName()+"--"+msg);
        }
    }
    /**
     * 信息
     * @param clazz
     * @param msg
     */
    public static void i(Class<?> clazz, String msg){
        if(isDebug){
            Log.i(TAG, clazz.getSimpleName()+"--"+msg);
        }
    }
    /**
     * 警告
     * @param clazz
     * @param msg
     */
    public static void w(Class<?> clazz, String msg){
        if(isDebug){
            Log.w(TAG, clazz.getSimpleName()+"--"+msg);
        }
    }
}