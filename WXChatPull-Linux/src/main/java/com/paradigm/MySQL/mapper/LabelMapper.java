package com.paradigm.MySQL.mapper;


import com.paradigm.MySQL.mapper.domain.Label;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface LabelMapper {

    @Insert("insert into label values(#{type}, #{value}, #{msgid})")
    void insert(Label label);

    @Delete("delete from label where msgid = #{msgid}")
    void delete(String msgid);

    @Select("select * from label")
    ArrayList<Label> findall();

    @Select("select * from label where msgid = #{msgid}")
    ArrayList<Label> findByMId(String msgid);
}


