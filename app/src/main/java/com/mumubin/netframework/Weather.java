package com.mumubin.netframework;

/**
 * 天气的实体
 * Created by junbin on 2016/1/20.
 */
public class Weather {
    /** 当前区域 **/
    private String distrct;
    /** 温度 **/
    private String temperature;

    public Weather() {

    }

    public Weather(String distrct, String temperature) {
        this.distrct = distrct;
        this.temperature = temperature;
    }

    public String getDistrct() {
        return distrct;
    }

    public void setDistrct(String distrct) {
        this.distrct = distrct;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "distrct='" + distrct + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
