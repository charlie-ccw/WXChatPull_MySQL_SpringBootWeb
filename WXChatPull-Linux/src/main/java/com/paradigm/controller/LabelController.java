package com.paradigm.controller;

import com.alibaba.fastjson.JSON;
import com.paradigm.MySQL.mapper.LabelMapper;
import com.paradigm.MySQL.mapper.MessageMapper;
import com.paradigm.MySQL.mapper.PersonMapper;
import com.paradigm.MySQL.mapper.domain.Label;
import com.paradigm.MySQL.mapper.domain.Message;
import com.paradigm.MySQL.mapper.domain.Person;
import com.paradigm.baiduwork.VocalToTextDemo;
import com.paradigm.util.label_personToken;
import com.paradigm.util.messageToken;
import com.paradigm.wework.WeChatDemo;
import com.paradigm.xunfeiwork.XunFeiVocalToTextDemo;
import com.paradigm.xunfeiwork.ffmpegwork.AudioConverter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.*;

@RestController
@RequestMapping("/Labels")
public class LabelController {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private messageToken messageToken;

    @Autowired
    private WeChatDemo WeChatDemo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private label_personToken label_personToken;


    //将所有微信消息整理存档
    @GetMapping
    public String getService() throws IOException {

        //获取微信会话内容
        ArrayList<String> strings = WeChatDemo.demo();

        //转换类型与MySQL对应
        ArrayList<Message> sentences = messageToken.getTokens(strings);

        //遍历文本集
        for(int i = 0; i < sentences.size(); i++) {
            System.out.println("---------- 正在处理并存档会话：" + i);

            //如果本条内容是音频消息，则调用百度和讯飞的语音识别接口
            if(Objects.equals(sentences.get(i).getMsgtype(), "voice")){
                System.out.print("此会话为语音，正在转文字...  ");
                //修改音频类型 amr to wav
                boolean toolarge = false;
                if(Integer.parseInt(sentences.get(i).getPlay_length()) > 55) toolarge = true;
                AudioConverter.getAudioFromVideo(sentences.get(i).getSdkfileid(), toolarge);
                //调用百度接口
//                JSONObject Content = VocalToTextDemo.VocalTOText("MediaResource/test.wav");
//                sentences.get(i).setContent(Content.get("result").toString());
//                System.out.println(Content.get("result").toString());
//                System.out.println("-----------------------------------------------------------------------");
                //调用讯飞接口
                String VocalText = XunFeiVocalToTextDemo.RecognizePcmfileByte("MediaResource/test.wav", sentences.get(i).getSdkfileid());
                sentences.get(i).setContent(VocalText);
                System.out.println(VocalText);
            }

            //----- 发送http请求给model, 并得到预测结果集 -----
            String sentence = sentences.get(i).getContent();
            if (sentence != null && sentence.length() > 0) {
                String url = "http://10.100.114.117:5000/";
                //---发送请求给Model服务器并获取预测结果
                LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
                body.set("sentence", sentence);
                List result = restTemplate.postForObject(url, body, List.class);
                System.out.println(result);
                //---对获取到的结果进行提取并存入对应的表
                for(int j = 0; j < 3; j++){
                    List<JSONObject> jsonStr= (List<JSONObject>) result.get(j);
                    if(j == 0){     // 人物表存储
                        List<Person> people = label_personToken.getPerson(jsonStr);
                        for (Person person: people) {
                            personMapper.insert(person);
                        }
                    }
                    else{       // 标注表存储
                        List<Label> labels = label_personToken.getLabel(jsonStr, sentences.get(i).getMsgid());
                        for (Label label: labels) {
                            labelMapper.insert(label);
                        }
                    }
                }
            }

            //-- MySQL-message存储语句 --
            messageMapper.insert(sentences.get(i));

            System.out.println();
        }
        System.out.println("---------- 拉取结束！！！----------");
        //返回结果
        return "运行结束！";
    }

    //查询相对应的微信消息内容和标注
    @GetMapping("/{id}")
    public Map<String, Map<String,String>> searchById(@PathVariable String id){
        Map<String, Map<String,String>> response = new HashMap<>();
        ArrayList<Label> labels= labelMapper.findByMId(id);
        String sentence = messageMapper.findById(id).getContent();
        Map<String,String> LabelValue = new HashMap<>();
        for (Label i: labels) {
            LabelValue.put(i.getType(), i.getValue());
        }
        response.put(sentence, LabelValue);
        return response;
    }

    //查询相对应的微信消息内容和标注
    @PostMapping
    @ResponseBody
    public Person searchByName(HttpServletRequest request){
        String name = request.getParameter("name");
        Person response = personMapper.findByName(name);
        return response;
    }
}
