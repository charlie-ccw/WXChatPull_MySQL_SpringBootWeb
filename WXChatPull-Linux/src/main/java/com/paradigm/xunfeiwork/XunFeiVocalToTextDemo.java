package com.paradigm.xunfeiwork;

import com.iflytek.cloud.speech.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class XunFeiVocalToTextDemo {

    static StringBuilder curRet;

    static String text;

    private static Object lock = new Object();

    private static final RecognizerListener recListener = new RecognizerListener() {

        @Override
        public void onEndOfSpeech() {
        }
        /**
         * 获取听写结果
         */
        public void onResult(RecognizerResult results, boolean islast) {
            //获取听写返回的结果
            String text = results.getResultString();
            //将结果叠加起来
            curRet.append(text);
            //如果检测当前内容为最后一句话，唤醒线程
            if( islast ) {
                synchronized (lock) {
                    lock.notify();//子线程唤醒
                }
            }

        }
        @Override
        public void onVolumeChanged(int volume) {
        }

        @Override
        public void onBeginOfSpeech() {
        }

        @Override
        public void onError(SpeechError error) {
            if (null != error) {
                System.out.println("onError Code:" + error.getErrorCode() + "," + error.getErrorDescription(true));
            }
        }
        @Override
        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            //以下代码用于调试，如果出现问题可以将sid提供给讯飞开发者，用于问题定位排查
			/*if(eventType == SpeechEvent.EVENT_SESSION_ID) {
				DebugLog.Log("sid=="+msg);
			}*/
        }
    };


    public static String RecognizePcmfileByte(String path, String file) {
        curRet = new StringBuilder();

        // 将“XXXXXXXX”替换成您申请的APPID
        SpeechUtility.createUtility( SpeechConstant.APPID +"XXXXXXXX");

        //创建Recognizer单例类
        SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer();
        //设置参数
        recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        recognizer.setParameter( SpeechConstant.RESULT_TYPE, "plain" );
        recognizer.setParameter( SpeechConstant.VAD_EOS, "10000");

        //开始会话
        recognizer.startListening(recListener);
        //创建数据流
        FileInputStream fis = null;
        //一次读取64KB内容
        final byte[] buffer = new byte[64*1024];
        try {
            fis = new FileInputStream(path);
            //检测数据流是否为空
            if (0 == fis.available()) {
                System.out.println("no audio avaible!");
                recognizer.cancel();
            } else {
                //不为空则开始不断的读取并调用接口写入
                int lenRead = buffer.length;
                while( buffer.length==lenRead ){
                    lenRead = fis.read( buffer );
                    //调用接口写入音频内容
                    recognizer.writeAudio( buffer, 0, lenRead );
                }
                //关闭会话
                recognizer.stopListening();
                //停止java主线程，等待讯飞返回结果
                double start = System.currentTimeMillis();
                synchronized (lock) {
                    lock.wait(20 * 1000);//主线程等待最多20秒
                }
                if(System.currentTimeMillis() - start > 20000) System.out.println("----------- " + file + " 文件语音转文字调取失败 ----------");
                //返回结果
                return curRet.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end of try-catch-finally
        text = "";
        //释放资源
        recognizer.destroy();
        return text;
    }
}
