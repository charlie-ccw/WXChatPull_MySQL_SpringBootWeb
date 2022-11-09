package com.paradigm.wework;

import com.tencent.wework.Finance;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class WeChatDemo {

    private static String priKey = "Prikey";

    private int seq = 0;

    public ArrayList<String> demo() {
        System.out.println("Seq:" + seq);
        //初始化sdk对象
        long sdk = Finance.NewSdk();
        /*
         * 初始化函数
         * Return值=0表示该API调用成功
         *
         *
         * @param [in]  sdk			NewSdk返回的sdk指针
         * @param [in]  corpid     	调用企业的企业id，例如：wwd08c8exxxx5ab44d，可以在企业微信管理端--我的企业--企业信息查看
         * @param [in]  secret		 聊天内容存档的Secret，可以在企业微信管理端--管理工具--聊天内容存档查看
         *
         *
         * @return 返回是否初始化成功
         *      0   - 成功
         *      !=0 - 失败
         */
        //初始化函数，调用接口 -返回0表示成功，否则失败
        int retInit = Finance.Init(sdk, "corpid", "secret");    //此处需要填写对应的corpid和secret内容 ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        if(retInit != 0){   //初始化失败
            System.out.println("初始化错误，代码："+retInit);
            return new ArrayList<>();
        }

        //------------------------------------- 拉取聊天记录的内容 -文本内容 ---------------------------------------
        /**
         * 拉取聊天记录函数
         * Return值=0表示该API调用成功
         *
         * @param [in]  sdk				NewSdk返回的sdk指针
         * @param [in]  seq				从指定的seq开始拉取消息，注意的是返回的消息从seq+1开始返回，seq为之前接口返回的最大seq值。首次使用请使用seq:0
         * @param [in]  limit			一次拉取的消息条数，最大值1000条，超过1000条会返回错误
         * @param [in]  proxy			使用代理的请求，需要传入代理的链接。如：socks5://10.0.0.1:8081 或者 http://10.0.0.1:8081
         * @param [in]  passwd			代理账号密码，需要传入代理的账号密码。如 user_name:passwd_123
         * @param [in]  timeout			超时时间，单位秒
         * @param [out] chatDatas		返回本次拉取消息的数据，slice结构体.内容包括errcode/errmsg，以及每条消息内容。示例如下：

        {"errcode":0,"errmsg":"ok","chatdata":[{"seq":196,"msgid":"CAQQ2fbb4QUY0On2rYSAgAMgip/yzgs=","publickey_ver":3,"encrypt_random_key":"ftJ+uz3n/z1DsxlkwxNgE+mL38H42/KCvN8T60gbbtPD+Rta1hKTuQPzUzO6Hzne97MgKs7FfdDxDck/v8cDT6gUVjA2tZ/M7euSD0L66opJ/IUeBtpAtvgVSD5qhlaQjvfKJc/zPMGNK2xCLFYqwmQBZXbNT7uA69Fflm512nZKW/piK2RKdYJhRyvQnA1ISxK097sp9WlEgDg250fM5tgwMjujdzr7ehK6gtVBUFldNSJS7ndtIf6aSBfaLktZgwHZ57ONewWq8GJe7WwQf1hwcDbCh7YMG8nsweEwhDfUz+u8rz9an+0lgrYMZFRHnmzjgmLwrR7B/32Qxqd79A==","encrypt_chat_msg":"898WSfGMnIeytTsea7Rc0WsOocs0bIAerF6de0v2cFwqo9uOxrW9wYe5rCjCHHH5bDrNvLxBE/xOoFfcwOTYX0HQxTJaH0ES9OHDZ61p8gcbfGdJKnq2UU4tAEgGb8H+Q9n8syRXIjaI3KuVCqGIi4QGHFmxWenPFfjF/vRuPd0EpzUNwmqfUxLBWLpGhv+dLnqiEOBW41Zdc0OO0St6E+JeIeHlRZAR+E13Isv9eS09xNbF0qQXWIyNUi+ucLr5VuZnPGXBrSfvwX8f0QebTwpy1tT2zvQiMM2MBugKH6NuMzzuvEsXeD+6+3VRqL"}]}

         *
         * @return 返回是否调用成功
         *      0   - 成功
         *      !=0 - 失败
         */
        long ret;
        // 从指定的seq开始拉取消息，注意的是返回的消息从seq+1开始返回，seq为之前接口返回的最大seq值。首次使用请使用seq:0（这个值需要记录下来，以便下一次的拉去）
        int limit = 100;
        long slice = Finance.NewSlice(); //创建slice结构体，用于存放数据
        ret = Finance.GetChatData(sdk, seq, limit, null, null, 5, slice);   //此处需要填写对应的proxy和passwd内容 ！！！！！！！！！！！！！！！！！！！！！！
        String getChatData; //创建字符串用于存储slice结构体内的
        if (ret != 0) {  //拉取聊天记录失败
            System.out.println("getchatdata ret: " + ret);
            return new ArrayList<>();
        } else {
            //提取拉取到的信息
            getChatData = Finance.GetContentFromSlice(slice);

        }

        //------------------------------------- 解密会话存档内容 ---------------------------------------
        /**
         * @brief 解析密文.企业微信自有解密内容
         * @param [in]  encrypt_key, getchatdata返回的encrypt_random_key,使用企业自持对应版本秘钥RSA解密后的内容
         * @param [in]  encrypt_msg, getchatdata返回的encrypt_chat_msg
         * @param [out] msg, 解密的消息明文
         * @return 返回是否调用成功
         *      0   - 成功
         *      !=0 - 失败
         */
        //将slice提取的文本转成JSON格式 - key, value
        JSONObject jo = new JSONObject(getChatData);
        //将JSON中的chatdata这个key对应的值提出来生成array
        JSONArray chatdata = jo.getJSONArray("chatdata");
        System.out.println("消息数：" + chatdata.length());
        seq += chatdata.length();
        ArrayList<String> list = new ArrayList<>();

        //对每一条信息进行操作
        for (int i = 0; i < chatdata.length(); i++) {
            JSONObject data = new JSONObject(chatdata.get(i).toString());
            String encryptRandomKey = data.getString("encrypt_random_key");
            String encryptChatMsg = data.getString("encrypt_chat_msg");
            //创建slice结构体存储最终解密出来的内容
            long msg = Finance.NewSlice();

            try {
                // 聊天记录密文解密
                String message = RSAdecrypt.decryptRSA(encryptRandomKey, priKey);   //RSA解密
                ret = Finance.DecryptData(sdk, message, encryptChatMsg, msg);   //调用API接口再次解密
                if (ret != 0) {
                    System.out.println("decrypt ret:" + ret);
                    return new ArrayList<>();
                }
                //获取解密后的内容
                String plaintext = Finance.GetContentFromSlice(msg);

                list.add(plaintext);
                Finance.FreeSlice(msg);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("解密出问题 error！");
                return new ArrayList<>();
            }
        }
        Finance.FreeSlice(slice);
        Finance.DestroySdk(sdk);
        return list;
    }

    // 拉去媒体信息
    public static String pullMediaFiles(String msgtype, JSONObject plaintextJson) throws IOException {
        long sdk = Finance.NewSdk();
        int retInit = Finance.Init(sdk, "ww886bfb1ac15fe1e8", "FM7N6swe7cEjNiXwm4piY5A4Y3ckTKM95oVnYxaS3rI");    //此处需要填写对应的corpid和secret内容 ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        if(retInit != 0){   //初始化失败
            System.out.println("拉取媒体初始化错误，代码："+retInit);
            return null;
        }
        String[] msgtypeStr = {"image", "voice", "video", "emotion", "file"};
        List<String> msgtypeList = Arrays.asList(msgtypeStr);
        String savefile = "";
        if (msgtypeList.contains(msgtype)) {
            String savefileName;
            JSONObject file;
            if (!plaintextJson.isNull("msgid")) {
                file = plaintextJson.getJSONObject(msgtype);    //得到整个媒体文件的json格式
                savefileName = plaintextJson.getString("msgid");    //得到msgid的值
            } else {
                // 混合消息
                file = plaintextJson;
                savefileName = file.getString("md5sum");
            }

            /* ============ 文件存储目录及文件名 Start ============ */
            String suffix = "";
            switch (msgtype) {
                case "image":
                    suffix = ".jpg";
                    break;
                case "voice":
                    suffix = ".amr";
                    break;
                case "video":
                    suffix = ".mp4";
                    break;
                case "emotion":
                    int type = (int) file.get("type");
                    if (type == 1) suffix = ".gif";
                    else if (type == 2) suffix = ".png";
                    break;
                case "file":
                    suffix = "." + file.getString("fileext");
                    break;
            }
            savefileName += suffix; //文件名
            System.out.println("----- 正在拉取文件：" + savefileName);
            String path = "MediaResource/";  //路径
            savefile = path + savefileName;
            File targetFile = new File(savefile);
            if (!targetFile.getParentFile().exists())
                //创建父级文件路径
                targetFile.getParentFile().mkdirs();
            if(targetFile.exists()) targetFile.delete();
            targetFile.createNewFile();
            /* ============ 文件存储目录及文件名 End ============ */

            /* ============ 拉去文件 Start ============ */
            int i = 0;
            boolean isSave = true;
            String indexbuf = "";
            String sdkfileid = file.get("sdkfileid").toString();
            while (true) {
                long mediaData = Finance.NewMediaData();
                int ret = Finance.GetMediaData(sdk, indexbuf, sdkfileid, null, null, 5, mediaData);
                if (ret != 0) {
                    System.out.println("getmediadata ret:" + ret);
                    Finance.FreeMediaData(mediaData);
                    return null;
                }
                System.out.printf("outindex len:%d, data_len:%d, is_finis:%d\n",
                        Finance.GetIndexLen(mediaData), Finance.GetDataLen(mediaData),
                        Finance.IsMediaDataFinish(mediaData));
                try {
                    // 大于512k的文件会分片拉取，此处需要使用追加写，避免后面的分片覆盖之前的数据。
                    FileOutputStream outputStream = new FileOutputStream(new File(savefile), true);
                    outputStream.write(Finance.GetData(mediaData));
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Finance.IsMediaDataFinish(mediaData) == 1) {
                    // 已经拉取完成最后一个分片
                    Finance.FreeMediaData(mediaData);
                    break;
                } else {
                    // 获取下次拉取需要使用的indexbuf
                    indexbuf = Finance.GetOutIndexBuf(mediaData);
                    Finance.FreeMediaData(mediaData);
                }
            }
        }
        return savefile;
    }
}

