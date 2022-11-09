package com.paradigm.MySQL.mapper.domain;

import org.springframework.stereotype.Component;

@Component
public class RecordItem {
    private String fromChatroom;
    private String msgtime;
    private String type;
    private String content;
    private String msgid;

    public RecordItem() {
    }

    public RecordItem(String fromChatroom, String msgtime, String type, String content, String msgid) {
        this.fromChatroom = fromChatroom;
        this.msgtime = msgtime;
        this.type = type;
        this.content = content;
        this.msgid = msgid;
    }

    public String getFromChatroom() {
        return fromChatroom;
    }

    public void setFromChatroom(String fromChatroom) {
        this.fromChatroom = fromChatroom;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    @Override
    public String toString() {
        return "RecordItem{" +
                "fromChatroom='" + fromChatroom + '\'' +
                ", msgtime='" + msgtime + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", msgid='" + msgid + '\'' +
                '}';
    }
}
