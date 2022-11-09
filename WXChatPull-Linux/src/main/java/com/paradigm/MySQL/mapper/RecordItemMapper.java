package com.paradigm.MySQL.mapper;

import com.paradigm.MySQL.mapper.domain.RecordItem;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
@Mapper
public interface RecordItemMapper {
    @Insert("insert into recorditem values(#{fromChatroom}, #{msgtime}, #{type}, #{content}, #{msgid})")
    void insert(RecordItem recordItem);

    @Delete("delete from recorditem where msgid = #{msgid}")
    void delete(String msgid);

    @Select("select * from recorditem")
    ArrayList<RecordItem> findall();

    @Select("select * from recorditem where msgid = #{msgid}")
    ArrayList<RecordItem> findByMId(String msgid);
}
