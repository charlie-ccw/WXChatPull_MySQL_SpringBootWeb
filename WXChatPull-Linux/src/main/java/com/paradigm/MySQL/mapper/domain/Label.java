package com.paradigm.MySQL.mapper.domain;

import org.springframework.stereotype.Component;

@Component
public class Label {
    private String type;
    private String value;
    private String msgid;

    public Label() {
    }

    public Label(String type, String value, String msgid) {
        this.type = type;
        this.value = value;
        this.msgid = msgid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    @Override
    public String toString() {
        return "Label{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", msgid='" + msgid + '\'' +
                '}';
    }
}
