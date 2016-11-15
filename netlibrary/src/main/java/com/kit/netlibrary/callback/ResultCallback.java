package com.kit.netlibrary.callback;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author ares
 * 基于Gson请求回调的抽象类，传入目标泛型请求即可自动解析得到对应的实体类
 * Created by ares on 16/4/7.
 */
public abstract class ResultCallback<T> {

    public Type mType;

    public ResultCallback()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    //请求成功的回调
    public abstract void onResponse(T response);
    //网络异常的回调
    public abstract void onNetWorkUnable();
    //请求失败的回调
    public abstract void onErrorResponse(Exception e,String errorMsg);
}
