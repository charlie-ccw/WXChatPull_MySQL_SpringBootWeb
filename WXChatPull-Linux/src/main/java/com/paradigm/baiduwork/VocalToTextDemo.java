package com.paradigm.baiduwork;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class VocalToTextDemo {

    //设置APPID/AK/SK
    public static final String APP_ID = "APP_ID";
    public static final String API_KEY = "API_KEY";
    public static final String SECRET_KEY = "SECRET_KEY";

    public static JSONObject VocalTOText(String path) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");


        // 调用接口
        return client.asr(path, "wav", 16000, null);
    }
}
