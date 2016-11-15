package com.kit.netlibrary.model;

/**
 * 规范化的json解析通用实体
 * Created by ares on 16/4/6.
 */
public class KitResponseBean<T> {


    private T data;
    private String userMsg;
    private String statusMsg;
    private int statusCode;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public String toString() {
        return "KitResponseBean{" +
                "data=" + data +
                ", userMsg='" + userMsg + '\'' +
                ", statusMsg='" + statusMsg + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
