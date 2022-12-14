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
        System.out.println("------------------------- ???????????????????????????Message?????? -------------------------");
        for (String string : strings) {
            JSONObject curr = new JSONObject(string);
            //??????????????????????????????????????????????????????
            if(messageMapper.findById(curr.getString("msgid")) != null){
                continue;
            }
            //---- ?????????????????? ----
            if (Objects.equals(curr.get("action").toString(), "switch")) {
                from = "";
                tolist = "";
                roomid = "";
                msgtype = "";

                //??????????????????
                SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //???????????????Date??????
                Long dateStr1 = Long.valueOf(curr.opt("time").toString());
                Date date=new Date(dateStr1);
                //??????format()???????????????Date????????????????????????????????????
                msgtime =  sdf1.format(date);
                msgid = curr.getString("msgid");
                action = curr.getString("action");
                user = curr.getString("user");
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                msgid = "";
                action = "";
                msgtime = "";
                user = "";

            }
            //---- ???????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "text")) {
                //?????????????????????
                basicDataSet(curr);
                //??????????????????
                content = new JSONObject(curr.opt("text").toString()).get("content").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                content = "";
            }
            //---- ???????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "image")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("image").toString());
                //??????????????????
                sdkfileid = weChatDemo.pullMediaFiles("image", curr);
                md5sum = jsonObject.get("md5sum").toString();
                filesize = jsonObject.get("filesize").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                sdkfileid = "";
                md5sum = "";
                filesize = "";

            }
            //---- ???????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "revoke")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("revoke").toString());
                //??????????????????
                pre_msgid = jsonObject.get("pre_msgid").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                pre_msgid = "";
            }
            //---- ??????/??????????????????????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "agree")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("agree").toString());
                //??????????????????
                userid = jsonObject.get("userid").toString();
                agree_time = jsonObject.get("agree_time").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                userid = "";
                agree_time = "";
            } else if (Objects.equals(curr.get("msgtype").toString(), "disagree")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("disagree").toString());
                //??????????????????
                userid = jsonObject.get("userid").toString();
                agree_time = jsonObject.get("agree_time").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                userid = "";
                agree_time = "";
            }
            //---- ?????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "voice")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("voice").toString());
                //??????????????????
                sdkfileid = weChatDemo.pullMediaFiles("voice", curr);
                md5sum = jsonObject.get("md5sum").toString();
                play_length = jsonObject.get("play_length").toString();
                voice_size = jsonObject.get("voice_size").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                sdkfileid = "";
                md5sum = "";
                play_length = "";
                voice_size = "";

            }
            //---- ?????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "video")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("video").toString());
                //??????????????????
                sdkfileid = weChatDemo.pullMediaFiles("video", curr);
                md5sum = jsonObject.get("md5sum").toString();
                filesize = jsonObject.get("filesize").toString();
                play_length = jsonObject.get("play_length").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                sdkfileid = "";
                md5sum = "";
                filesize = "";
                play_length = "";

            }
            //---- ?????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "card")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("card").toString());
                //??????????????????
                corpname = jsonObject.get("corpname").toString();
                userid = jsonObject.get("userid").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                corpname = "";
                userid = "";

            }
            //---- ?????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "location")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("location").toString());
                //??????????????????
                longitude = jsonObject.get("longitude").toString();
                latitude = jsonObject.get("latitude").toString();
                address = jsonObject.get("address").toString();
                title = jsonObject.get("title").toString();
                zoom = jsonObject.get("zoom").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                longitude = "";
                latitude = "";
                address = "";
                title = "";
                zoom = "";
            }
            //---- ?????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "emotion")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("emotion").toString());
                //??????????????????
                sdkfileid = weChatDemo.pullMediaFiles("emotion", curr);
                type = Objects.equals(jsonObject.get("type").toString(), "1") ? "gif" : "png";
                width = jsonObject.get("width").toString();
                height = jsonObject.get("height").toString();
                md5sum = jsonObject.get("md5sum").toString();
                imagesize = jsonObject.get("imagesize").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                sdkfileid = "";
                type = "";
                width = "";
                height = "";
                md5sum = "";
                imagesize = "";

            }
            //---- ?????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "file")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("file").toString());
                //??????????????????
                sdkfileid = weChatDemo.pullMediaFiles("file", curr);
                md5sum = jsonObject.get("md5sum").toString();
                filename = jsonObject.get("filename").toString();
                fileext = jsonObject.get("fileext").toString();
                filesize = jsonObject.get("filesize").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                sdkfileid = "";
                md5sum = "";
                filename = "";
                fileext = "";
                filesize = "";

            }
            //---- ?????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "link")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("link").toString());
                //??????????????????
                title = jsonObject.get("title").toString();
                description = jsonObject.get("description").toString();
                link_url = jsonObject.get("link_url").toString();
                image_url = jsonObject.get("image_url").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                title = "";
                description = "";
                link_url = "";
                image_url = "";

            }
            //---- ??????????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "weapp")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("weapp").toString());
                //??????????????????
                title = jsonObject.get("title").toString();
                description = jsonObject.get("description").toString();
                username = jsonObject.get("username").toString();
                displayname = jsonObject.get("displayname").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                title = "";
                description = "";
                username = "";
                displayname = "";

            }
            //---- ?????????????????? --------------------------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "chatrecord")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("chatrecord").toString());
                //??????????????????
                title = jsonObject.get("title").toString();
                JSONArray items = jsonObject.getJSONArray("item");
                for(int j = 0; j < items.length(); j++){
                    JSONObject item = items.getJSONObject(j);
                    SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //???????????????Date??????
                    Long dateStr1 = Long.valueOf(item.opt("msgtime").toString());
                    Date date=new Date(dateStr1*1000);
                    //??????format()???????????????Date????????????????????????????????????
                    recordItem.setMsgtime(sdf1.format(date));
                    recordItem.setFromChatroom(item.get("from_chatroom").toString());
                    recordItem.setContent(item.get("content").toString());
                    recordItem.setMsgid(msgid);
                    recordItem.setType(item.getString("type"));
                    recordItemMapper.insert(recordItem);
                }

                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                title = "";
            }
            //---- ???????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "todo")) {
                //?????????????????????
                basicDataSet(curr);
                System.out.println(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("todo").toString());
                //??????????????????
                title = jsonObject.get("title").toString();
                content = jsonObject.get("content").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                title = "";
                content = "";

            }
            //---- ???????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "vote")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("vote").toString());
                //??????????????????
                votetitle = jsonObject.get("votetitle").toString();
                voteitem = jsonObject.get("voteitem").toString();
                votetype = jsonObject.get("votetype").toString();
                voteid = jsonObject.get("voteid").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                votetitle = "";
                voteitem = "";
                votetype = "";
                voteid = "";

            }
            //---- ???????????? ----------------------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "collect")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("collect").toString());
                //??????????????????

                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????

            }
            //---- ???????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "redpacket")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("redpacket").toString());
                //??????????????????
                type = Objects.equals(jsonObject.get("type").toString(), "1") ? "????????????" : Objects.equals(jsonObject.get("type").toString(), "2") ? "??????????????????" : "???????????????";
                wish = jsonObject.get("wish").toString();
                totalcnt = jsonObject.get("totalcnt").toString();
                totalamount = jsonObject.get("totalamount").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                type = "";
                wish = "";
                totalcnt = "";
                totalamount = "";

            }
            //---- ?????????????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "meeting")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("meeting").toString());
                //??????????????????
                topic = jsonObject.get("topic").toString();
                starttime = jsonObject.get("starttime").toString();
                endtime = jsonObject.get("endtime").toString();
                address = jsonObject.get("address").toString();
                remarks = jsonObject.get("remarks").toString();
                meetingtype = Objects.equals(jsonObject.get("meetingtype").toString(), "101") ? "????????????????????????" : "????????????????????????";
                meetingid = jsonObject.get("meetingid").toString();
                status = Objects.equals(jsonObject.get("meetingtype").toString(), "102") ? jsonObject.getString("status") : "";
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                topic = "";
                starttime = "";
                endtime = "";
                address = "";
                remarks = "";
                meetingtype = "";
                meetingid = "";
                status = "";

            }
            //---- ?????????????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "docmsg")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("doc").toString());
                //??????????????????
                title = jsonObject.get("title").toString();
                link_url = jsonObject.get("link_url").toString();
                doc_creator = jsonObject.getString("doc_creator");
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                title = "";
                link_url = "";
                doc_creator = "";

            }
            //---- Markdown???????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "markdown")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("info").toString());
                //??????????????????
                info = jsonObject.getString("content");
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                info = "";

            }
            //---- ???????????? --------------------------------------------------------------------------
            else if (curr.get("msgtype") == "news") {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("info").toString());
                //??????????????????

                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
            }
            //---- ???????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "calendar")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("calendar").toString());
                //??????????????????
                SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //???????????????Date??????
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
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                title = "";
                creatorname = "";
                attendeename = "";
                starttime = "";
                endtime = "";
                place = "";
                remarks = "";

            }
            //---- ???????????? ----------------------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "mixed")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("revoke").toString());
                //??????????????????

                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????

            }
            //---- ?????????????????? -------------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "meeting_voice_call")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("revoke").toString());
                //??????????????????

                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????

            }
            //---- ???????????????????????? -----------------------------------------------------------------------
            else if (Objects.equals(curr.get("msgtype").toString(), "voip_doc_share")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("revoke").toString());
                //??????????????????

                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????

            }
            //---- ?????????????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "external_redpacket")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("redpacket").toString());
                //??????????????????
                type = Objects.equals(jsonObject.get("type").toString(), "1") ? "????????????" : "??????????????????";
                wish = jsonObject.get("wish").toString();
                totalcnt = jsonObject.get("totalcnt").toString();
                totalamount = jsonObject.get("totalamount").toString();
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                type = "";
                wish = "";
                totalcnt = "";
                totalamount = "";

            }
            //---- ??????????????? ----
            else if (Objects.equals(curr.get("msgtype").toString(), "sphfeed")) {
                //?????????????????????
                basicDataSet(curr);
                JSONObject jsonObject = new JSONObject(curr.opt("sphfeed").toString());
                //??????????????????
                feed_type = Objects.equals(jsonObject.get("feed_type").toString(), "2") ? "??????" : Objects.equals(jsonObject.get("feed_type").toString(), "4") ? "??????" : "??????";
                sph_name = jsonObject.getString("sph_name");
                feed_desc = jsonObject.getString("feed_desc");
                //??????message??????arraylist
                constructMessage(messages);
                //??????????????????
                feed_type = "";
                sph_name = "";
                feed_desc = "";

            }

        }
        System.out.println("------------------------- ???????????? ?????????-------------------------");
        System.out.println();
        return messages;
    }

    private static void basicDataSet(JSONObject curr) {
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //???????????????Date??????
        Long dateStr1 = Long.valueOf(curr.opt("msgtime") != null ? curr.opt("msgtime").toString() : "");
        Date date=new Date(dateStr1);
        //??????format()???????????????Date????????????????????????????????????
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
