package com.mumubin.netframework;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvWeather;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case CommonNetUtil.GETWEATHER_SUCCESS:
                    Weather weather = (Weather) msg.obj;
                    String weatherShow = weather.getDistrct() + "/" + weather.getTemperature();
                    tvWeather.setText(weatherShow);
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWeather= (TextView) findViewById(R.id.tv_weather);

        //get请求示例
        CommonNetUtil.getWeather("香港",handler);
    }
}
