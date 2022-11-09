package com.paradigm.MySQL.mapper.domain;

import org.springframework.stereotype.Component;

@Component
public class Message {
    private String msgid;
    private String action;
    private String from;
    private String tolist;
    private String roomid;
    private String msgtime;
    private String msgtype;
    private String content;
    private String sdkfileid;
    private String md5sum;
    private String filesize;
    private String pre_msgid;
    private String userid;
    private String agree_time;
    private String voice_size;
    private String play_length;
    private String corpname;
    private String longitude;
    private String latitude;
    private String address;
    private String title;
    private String zoom;
    private String width;
    private String height;
    private String imagesize;
    private String fileext;
    private String filename;
    private String description;
    private String image_url;
    private String link_url;
    private String username;
    private String displayname;
    private String item;
    private String votetitle;
    private String voteitem;
    private String votetype;
    private String voteid;
    private String room_name;
    private String creator;
    private String create_time;
    private String details;
    private String id;
    private String ques;
    private String type;
    private String wish;
    private String totalcnt;
    private String totalamount;
    private String topic;
    private String starttime;
    private String endtime;
    private String remarks;
    private String meetingtype;
    private String meetingid;
    private String status;
    private String user;
    private String doc_creator;
    private String info;
    private String creatorname;
    private String attendeename;
    private String place;
    private String demooperator;
    private String share;
    private String voipid;
    private String feed_type;
    private String sph_name;
    private String feed_desc;

    public Message() {
    }

    public Message(String msgid, String action, String from, String tolist, String roomid, String msgtime, String msgtype, String content, String sdkfileid, String md5sum, String filesize, String pre_msgid, String userid, String agree_time, String voice_size, String play_length, String corpname, String longitude, String latitude, String address, String title, String zoom, String width, String height, String imagesize, String fileext, String filename, String description, String image_url, String link_url, String username, String displayname, String item, String votetitle, String voteitem, String votetype, String voteid, String room_name, String creator, String create_time, String details, String id, String ques, String type, String wish, String totalcnt, String totalamount, String topic, String starttime, String endtime, String remarks, String meetingtype, String meetingid, String status, String user, String doc_creator, String info, String creatorname, String attendeename, String place, String demooperator, String share, String voipid, String feed_type, String sph_name, String feed_desc) {
        this.msgid = msgid;
        this.action = action;
        this.from = from;
        this.tolist = tolist;
        this.roomid = roomid;
        this.msgtime = msgtime;
        this.msgtype = msgtype;
        this.content = content;
        this.sdkfileid = sdkfileid;
        this.md5sum = md5sum;
        this.filesize = filesize;
        this.pre_msgid = pre_msgid;
        this.userid = userid;
        this.agree_time = agree_time;
        this.voice_size = voice_size;
        this.play_length = play_length;
        this.corpname = corpname;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.title = title;
        this.zoom = zoom;
        this.width = width;
        this.height = height;
        this.imagesize = imagesize;
        this.fileext = fileext;
        this.filename = filename;
        this.description = description;
        this.image_url = image_url;
        this.link_url = link_url;
        this.username = username;
        this.displayname = displayname;
        this.item = item;
        this.votetitle = votetitle;
        this.voteitem = voteitem;
        this.votetype = votetype;
        this.voteid = voteid;
        this.room_name = room_name;
        this.creator = creator;
        this.create_time = create_time;
        this.details = details;
        this.id = id;
        this.ques = ques;
        this.type = type;
        this.wish = wish;
        this.totalcnt = totalcnt;
        this.totalamount = totalamount;
        this.topic = topic;
        this.starttime = starttime;
        this.endtime = endtime;
        this.remarks = remarks;
        this.meetingtype = meetingtype;
        this.meetingid = meetingid;
        this.status = status;
        this.user = user;
        this.doc_creator = doc_creator;
        this.info = info;
        this.creatorname = creatorname;
        this.attendeename = attendeename;
        this.place = place;
        this.demooperator = demooperator;
        this.share = share;
        this.voipid = voipid;
        this.feed_type = feed_type;
        this.sph_name = sph_name;
        this.feed_desc = feed_desc;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTolist() {
        return tolist;
    }

    public void setTolist(String tolist) {
        this.tolist = tolist;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSdkfileid() {
        return sdkfileid;
    }

    public void setSdkfileid(String sdkfileid) {
        this.sdkfileid = sdkfileid;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getPre_msgid() {
        return pre_msgid;
    }

    public void setPre_msgid(String pre_msgid) {
        this.pre_msgid = pre_msgid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAgree_time() {
        return agree_time;
    }

    public void setAgree_time(String agree_time) {
        this.agree_time = agree_time;
    }

    public String getVoice_size() {
        return voice_size;
    }

    public void setVoice_size(String voice_size) {
        this.voice_size = voice_size;
    }

    public String getPlay_length() {
        return play_length;
    }

    public void setPlay_length(String play_length) {
        this.play_length = play_length;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getImagesize() {
        return imagesize;
    }

    public void setImagesize(String imagesize) {
        this.imagesize = imagesize;
    }

    public String getFileext() {
        return fileext;
    }

    public void setFileext(String fileext) {
        this.fileext = fileext;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getVotetitle() {
        return votetitle;
    }

    public void setVotetitle(String votetitle) {
        this.votetitle = votetitle;
    }

    public String getVoteitem() {
        return voteitem;
    }

    public void setVoteitem(String voteitem) {
        this.voteitem = voteitem;
    }

    public String getVotetype() {
        return votetype;
    }

    public void setVotetype(String votetype) {
        this.votetype = votetype;
    }

    public String getVoteid() {
        return voteid;
    }

    public void setVoteid(String voteid) {
        this.voteid = voteid;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getTotalcnt() {
        return totalcnt;
    }

    public void setTotalcnt(String totalcnt) {
        this.totalcnt = totalcnt;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(String meetingtype) {
        this.meetingtype = meetingtype;
    }

    public String getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(String meetingid) {
        this.meetingid = meetingid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDoc_creator() {
        return doc_creator;
    }

    public void setDoc_creator(String doc_creator) {
        this.doc_creator = doc_creator;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCreatorname() {
        return creatorname;
    }

    public void setCreatorname(String creatorname) {
        this.creatorname = creatorname;
    }

    public String getAttendeename() {
        return attendeename;
    }

    public void setAttendeename(String attendeename) {
        this.attendeename = attendeename;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDemooperator() {
        return demooperator;
    }

    public void setDemooperator(String demooperator) {
        this.demooperator = demooperator;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getVoipid() {
        return voipid;
    }

    public void setVoipid(String voipid) {
        this.voipid = voipid;
    }

    public String getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(String feed_type) {
        this.feed_type = feed_type;
    }

    public String getSph_name() {
        return sph_name;
    }

    public void setSph_name(String sph_name) {
        this.sph_name = sph_name;
    }

    public String getFeed_desc() {
        return feed_desc;
    }

    public void setFeed_desc(String feed_desc) {
        this.feed_desc = feed_desc;
    }
}
