package com.paradigm.util;

import com.paradigm.MySQL.mapper.MessageMapper;
import com.paradigm.MySQL.mapper.RecordItemMapper;
import com.paradigm.MySQL.mapper.domain.Message;
import com.paradigm.MySQL.mapper.domain.RecordItem;
import com.paradigm.wework.WeChatDemo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class messageToken {

    @Autowired
    private WeChatDemo weChatDemo;

    @Autowired
    private RecordItemMapper recordItemMapper;

    @Autowired
    private RecordItem recordItem;

    @Autowired
    private MessageMapper messageMapper;

    private static String msgid;
    private static String action;
    private static String from;
    private static String tolist;
    private static String roomid;
    private static String msgtime;
    private static String msgtype;
    private static String content;
    private static String sdkfileid;
    private static String md5sum;
    private static String filesize;
    private static String pre_msgid;
    private static String userid;
    private static String agree_time;
    private static String voice_size;
    private static String play_length;
    private static String corpname;
    private static String longitude;
    private static String latitude;
    private static String address;
    private static String title;
    private static String zoom;
    private static String width;
    private static String height;
    private static String imagesize;
    private static String fileext;
    private static String filename;
    private static String description;
    private static String image_url;
    private static String link_url;
    private static String username;
    private static String displayname;
    private static String item;
    private static String votetitle;
    private static String voteitem;
    private static String votetype;
    private static String voteid;
    private static String room_name;
    private static String creator;
    private static String create_time;
    private static String details;
    private static String id;
    private static String ques;
    private static String type;
    private static String wish;
    private static String totalcnt;
    private static String totalamount;
    private static String topic;
    private static String starttime;
    private static String endtime;
    private static String remarks;
    private static String meetingtype;
    private static String meetingid;
    private static String status;
    private static String user;
    private static String doc_creator;
    private static String info;
    private static String creatorname;
    private static String attendeename;
    private static String place;
    private static String demooperator;
    private static String share;
    private static String voipid;
    private static String feed_type;
    private static String sph_name;
    private static String feed_desc;

    public ArrayList<Message> getTokens(ArrayList<String> strings) throws IOException {
        ArrayList<Message> messages = new ArrayList<>();
        System.out.println("------------------------- 正在转换微信文本至Message类型 -------------------------");
        for (String string : strings) {
            JSONObject curr = new JSONObject(string);
            //检查当前会话信息是否已经拉取并储存过
            if(messageMapper.findById(curr.getString("msgid")) != null){
                continue;
            }
            //---- 切换企业日志 ----
            if (Objects.equals(curr.get("action").toString(), "switch")) {
                from = "";
                tolist = "";
                roomid = "";
                msgtype = "";

                //针对性的赋值
                SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //要格式化的Date对象
                Long dateStr1 = Long.valueOf(curr.opt("time").toString());
                Date date=new Date(dateStr1);
                //使用format()方法格式化Date对象为字符串，返回字符串
                msgtime =  sdf1.format(date);
                msgid = curr.getString("msgid");
                action = curr.getString("action");
                user = curr.getString("user");
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                msgid = "";
                action = "";
                msgtime = "";
                user = "";

            }
            //---- 文本消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "text")) {
                //初始化一些配置
                basicDataSet(curr);
                //针对性的赋值
                content = new JSONObject(curr.opt("text").toString()).get("content").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                content = "";
            }
            //---- 图片消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "image")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("image").toString());
                //针对性的赋值
                sdkfileid = weChatDemo.pullMediaFiles("image", curr);
                md5sum = jsonObject.get("md5sum").toString();
                filesize = jsonObject.get("filesize").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                sdkfileid = "";
                md5sum = "";
                filesize = "";

            }
            //---- 撤回消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "revoke")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("revoke").toString());
                //针对性的赋值
                pre_msgid = jsonObject.get("pre_msgid").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                pre_msgid = "";
            }
            //---- 同意/不同意会话聊天内容 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "agree")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("agree").toString());
                //针对性的赋值
                userid = jsonObject.get("userid").toString();
                agree_time = jsonObject.get("agree_time").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                userid = "";
                agree_time = "";
            } else if (Objects.equals(curr.get("msgtype").toString(), "disagree")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("disagree").toString());
                //针对性的赋值
                userid = jsonObject.get("userid").toString();
                agree_time = jsonObject.get("agree_time").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                userid = "";
                agree_time = "";
            }
            //---- 语音 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "voice")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("voice").toString());
                //针对性的赋值
                sdkfileid = weChatDemo.pullMediaFiles("voice", curr);
                md5sum = jsonObject.get("md5sum").toString();
                play_length = jsonObject.get("play_length").toString();
                voice_size = jsonObject.get("voice_size").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                sdkfileid = "";
                md5sum = "";
                play_length = "";
                voice_size = "";

            }
            //---- 视频 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "video")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("video").toString());
                //针对性的赋值
                sdkfileid = weChatDemo.pullMediaFiles("video", curr);
                md5sum = jsonObject.get("md5sum").toString();
                filesize = jsonObject.get("filesize").toString();
                play_length = jsonObject.get("play_length").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                sdkfileid = "";
                md5sum = "";
                filesize = "";
                play_length = "";

            }
            //---- 名片 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "card")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("card").toString());
                //针对性的赋值
                corpname = jsonObject.get("corpname").toString();
                userid = jsonObject.get("userid").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                corpname = "";
                userid = "";

            }
            //---- 位置 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "location")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("location").toString());
                //针对性的赋值
                longitude = jsonObject.get("longitude").toString();
                latitude = jsonObject.get("latitude").toString();
                address = jsonObject.get("address").toString();
                title = jsonObject.get("title").toString();
                zoom = jsonObject.get("zoom").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                longitude = "";
                latitude = "";
                address = "";
                title = "";
                zoom = "";
            }
            //---- 表情 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "emotion")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("emotion").toString());
                //针对性的赋值
                sdkfileid = weChatDemo.pullMediaFiles("emotion", curr);
                type = Objects.equals(jsonObject.get("type").toString(), "1") ? "gif" : "png";
                width = jsonObject.get("width").toString();
                height = jsonObject.get("height").toString();
                md5sum = jsonObject.get("md5sum").toString();
                imagesize = jsonObject.get("imagesize").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                sdkfileid = "";
                type = "";
                width = "";
                height = "";
                md5sum = "";
                imagesize = "";

            }
            //---- 文件 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "file")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("file").toString());
                //针对性的赋值
                sdkfileid = weChatDemo.pullMediaFiles("file", curr);
                md5sum = jsonObject.get("md5sum").toString();
                filename = jsonObject.get("filename").toString();
                fileext = jsonObject.get("fileext").toString();
                filesize = jsonObject.get("filesize").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                sdkfileid = "";
                md5sum = "";
                filename = "";
                fileext = "";
                filesize = "";

            }
            //---- 链接 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "link")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("link").toString());
                //针对性的赋值
                title = jsonObject.get("title").toString();
                description = jsonObject.get("description").toString();
                link_url = jsonObject.get("link_url").toString();
                image_url = jsonObject.get("image_url").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                title = "";
                description = "";
                link_url = "";
                image_url = "";

            }
            //---- 小程序消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "weapp")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("weapp").toString());
                //针对性的赋值
                title = jsonObject.get("title").toString();
                description = jsonObject.get("description").toString();
                username = jsonObject.get("username").toString();
                displayname = jsonObject.get("displayname").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                title = "";
                description = "";
                username = "";
                displayname = "";

            }
            //---- 会话记录消息 --------------------------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "chatrecord")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("chatrecord").toString());
                //针对性的赋值
                title = jsonObject.get("title").toString();
                JSONArray items = jsonObject.getJSONArray("item");
                for(int j = 0; j < items.length(); j++){
                    JSONObject item = items.getJSONObject(j);
                    SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //要格式化的Date对象
                    Long dateStr1 = Long.valueOf(item.opt("msgtime").toString());
                    Date date=new Date(dateStr1*1000);
                    //使用format()方法格式化Date对象为字符串，返回字符串
                    recordItem.setMsgtime(sdf1.format(date));
                    recordItem.setFromChatroom(item.get("from_chatroom").toString());
                    recordItem.setContent(item.get("content").toString());
                    recordItem.setMsgid(msgid);
                    recordItem.setType(item.getString("type"));
                    recordItemMapper.insert(recordItem);
                }

                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                title = "";
            }
            //---- 代办消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "todo")) {
                //初始化一些配置
                basicDataSet(curr);
                System.out.println(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("todo").toString());
                //针对性的赋值
                title = jsonObject.get("title").toString();
                content = jsonObject.get("content").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                title = "";
                content = "";

            }
            //---- 投票消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "vote")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("vote").toString());
                //针对性的赋值
                votetitle = jsonObject.get("votetitle").toString();
                voteitem = jsonObject.get("voteitem").toString();
                votetype = jsonObject.get("votetype").toString();
                voteid = jsonObject.get("voteid").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                votetitle = "";
                voteitem = "";
                votetype = "";
                voteid = "";

            }
            //---- 填表消息 ----------------------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "collect")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("collect").toString());
                //针对性的赋值

                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容

            }
            //---- 红包消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "redpacket")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("redpacket").toString());
                //针对性的赋值
                type = Objects.equals(jsonObject.get("type").toString(), "1") ? "普通红包" : Objects.equals(jsonObject.get("type").toString(), "2") ? "拼手气群红包" : "激励群红包";
                wish = jsonObject.get("wish").toString();
                totalcnt = jsonObject.get("totalcnt").toString();
                totalamount = jsonObject.get("totalamount").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                type = "";
                wish = "";
                totalcnt = "";
                totalamount = "";

            }
            //---- 会议邀请消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "meeting")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("meeting").toString());
                //针对性的赋值
                topic = jsonObject.get("topic").toString();
                starttime = jsonObject.get("starttime").toString();
                endtime = jsonObject.get("endtime").toString();
                address = jsonObject.get("address").toString();
                remarks = jsonObject.get("remarks").toString();
                meetingtype = Objects.equals(jsonObject.get("meetingtype").toString(), "101") ? "发起会议邀请消息" : "处理会议邀请消息";
                meetingid = jsonObject.get("meetingid").toString();
                status = Objects.equals(jsonObject.get("meetingtype").toString(), "102") ? jsonObject.getString("status") : "";
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                topic = "";
                starttime = "";
                endtime = "";
                address = "";
                remarks = "";
                meetingtype = "";
                meetingid = "";
                status = "";

            }
            //---- 在线文档消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "docmsg")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("doc").toString());
                //针对性的赋值
                title = jsonObject.get("title").toString();
                link_url = jsonObject.get("link_url").toString();
                doc_creator = jsonObject.getString("doc_creator");
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                title = "";
                link_url = "";
                doc_creator = "";

            }
            //---- Markdown文件消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "markdown")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("info").toString());
                //针对性的赋值
                info = jsonObject.getString("content");
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                info = "";

            }
            //---- 图文消息 --------------------------------------------------------------------------
            else if (curr.get("msgtype") == "news") {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("info").toString());
                //针对性的赋值

                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
            }
            //---- 日程消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "calendar")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("calendar").toString());
                //针对性的赋值
                SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //要格式化的Date对象
                Long dateStr1 = Long.valueOf(jsonObject.opt("starttime").toString());
                Long dateStr2 = Long.valueOf(jsonObject.opt("endtime").toString());
                Date date1=new Date(dateStr1*1000);
                Date date2=new Date(dateStr2*1000);
                title = jsonObject.get("title").toString();
                creatorname = jsonObject.getString("creatorname");
                attendeename = jsonObject.get("attendeename").toString();
                starttime = sdf1.format(date1);
                endtime = sdf1.format(date2);
                place = jsonObject.getString("place");
                remarks = jsonObject.get("remarks").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                title = "";
                creatorname = "";
                attendeename = "";
                starttime = "";
                endtime = "";
                place = "";
                remarks = "";

            }
            //---- 混合消息 ----------------------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "mixed")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("revoke").toString());
                //针对性的赋值

                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容

            }
            //---- 音频存档消息 -------------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "meeting_voice_call")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("revoke").toString());
                //针对性的赋值

                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容

            }
            //---- 音频共享文档消息 -----------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "voip_doc_share")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("revoke").toString());
                //针对性的赋值

                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容

            }
            //---- 互通红包消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "external_redpacket")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("redpacket").toString());
                //针对性的赋值
                type = Objects.equals(jsonObject.get("type").toString(), "1") ? "普通红包" : "拼手气群红包";
                wish = jsonObject.get("wish").toString();
                totalcnt = jsonObject.get("totalcnt").toString();
                totalamount = jsonObject.get("totalamount").toString();
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                type = "";
                wish = "";
                totalcnt = "";
                totalamount = "";

            }
            //---- 视频号消息 ----
            else if (Objects.equals(curr.get("msgtype").toString(), "sphfeed")) {
                //初始化一些配置
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("sphfeed").toString());
                //针对性的赋值
                feed_type = Objects.equals(jsonObject.get("feed_type").toString(), "2") ? "图片" : Objects.equals(jsonObject.get("feed_type").toString(), "4") ? "视频" : "直播";
                sph_name = jsonObject.getString("sph_name");
                feed_desc = jsonObject.getString("feed_desc");
                //添加message类进arraylist
                constructMessage(messages);
                //还原特殊内容
                feed_type = "";
                sph_name = "";
                feed_desc = "";

            }

        }
        System.out.println("------------------------- 转换成功 ！！！-------------------------");
        System.out.println();
        return messages;
    }

    private static void basicDataSet(JSONObject curr) {
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //要格式化的Date对象
        Long dateStr1 = Long.valueOf(curr.opt("msgtime") != null ? curr.opt("msgtime").toString() : "");
        Date date=new Date(dateStr1);
        //使用format()方法格式化Date对象为字符串，返回字符串
        msgtime =  sdf1.format(date);

        msgid = curr.opt("msgid") != null ? curr.opt("msgid").toString() : "";
        action = curr.opt("action") != null ? curr.opt("action").toString() : "";
        from = curr.opt("from") != null ? curr.opt("from").toString() : "";
        tolist = curr.opt("tolist") != null ? curr.opt("tolist").toString() : "";
        roomid =  curr.opt("roomid") != null ? curr.opt("roomid").toString() : "";
        msgtype =  curr.opt("msgtype") != null ? curr.opt("msgtype").toString() : "";
    }

    private static void constructMessage(ArrayList<Message> messages){
        messages.add(new Message(msgid, action, from, tolist, roomid, msgtime, msgtype, content, sdkfileid, md5sum, filesize, pre_msgid, userid, agree_time, voice_size, play_length, corpname, longitude, latitude, address, title, zoom, width, height, imagesize, fileext, filename, description, image_url, link_url, username, displayname, item, votetitle, voteitem, votetype, voteid, room_name, creator, create_time, details, id, ques, type, wish, totalcnt, totalamount, topic, starttime, endtime, remarks, meetingtype, meetingid, status, user, doc_creator, info, creatorname, attendeename, place, demooperator, share, voipid, feed_type, sph_name, feed_desc));
    }
}
