package com.paradigm.MySQL.mapper;


import com.paradigm.MySQL.mapper.domain.Message;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface MessageMapper {

    @Insert("insert into message values(#{msgid}, #{action}, #{from}, #{tolist}, #{roomid}, #{msgtime}, #{msgtype}, #{content}," +
            "#{sdkfileid}, #{md5sum}, #{filesize}, #{pre_msgid}, #{userid}, #{agree_time}, #{voice_size}, #{play_length}, #{corpname}," +
            "#{longitude}, #{latitude}, #{address}, #{title}, #{zoom}, #{width}, #{height}, #{imagesize}, #{fileext}," +
            "#{filename}, #{description}, #{image_url}, #{link_url}, #{username}, #{displayname}, #{item}, #{votetitle}, #{voteitem}," +
            "#{votetype}, #{voteid}, #{room_name}, #{creator}, #{create_time}, #{details}, #{id}, #{ques}, #{type}," +
            "#{wish}, #{totalcnt}, #{totalamount}, #{topic}, #{starttime}, #{endtime}, #{remarks}, #{meetingtype}, #{meetingid}," +
            "#{status}, #{user}, #{doc_creator}, #{info}, #{creatorname}, #{attendeename}, #{place}, #{demooperator}, #{share}," +
            "#{voipid}, #{feed_type}, #{sph_name}, #{feed_desc})")
    void insert(Message message);

    @Delete("delete from message where msgid = #{msgid}")
    void delete(String msgid);


    @Select("select * from message")
    ArrayList<Message> findall();

    @Select("select * from message where msgid = #{msgid}")
    Message findById(String msgid);
}


