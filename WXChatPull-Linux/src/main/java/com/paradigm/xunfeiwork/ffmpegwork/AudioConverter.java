package com.paradigm.xunfeiwork.ffmpegwork;


import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class AudioConverter {

    public static void getAudioFromVideo(String videoResourcesPath, boolean toolarge) {
        //输入转换音频格式所需要的指令
        ArrayList<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-y");
        command.add("-i");
        command.add(videoResourcesPath);
        command.add("-ac");
        command.add("1");
        if(toolarge){
            command.add("-t");
            command.add("55");
        }
        command.add("-ar");
        command.add("16000");
        command.add("-aq");
        command.add("0");
        command.add("MediaResource/test.wav");
        //调用指令运行方法
        commandStart(command);
    }
    public static void commandStart(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder();
        //正常信息和错误信息合并输出
        builder.redirectErrorStream(true);
        builder.command(command);
        //开始执行命令
        Process process = null;
        try {
            process = builder.start();
            //让java程序等待command的运行结束，防止程序混乱
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
