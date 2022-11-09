package com.paradigm.MySQL.mapper;


import com.paradigm.MySQL.mapper.domain.Label;
import com.paradigm.MySQL.mapper.domain.Person;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface PersonMapper {

    @Insert("insert into person values(#{name}, #{age}, #{sex}, #{income}, #{organization}, #{job})")
    void insert(Person person);

    @Delete("delete from person where name = #{name}")
    void delete(String name);

    @Select("select * from person")
    ArrayList<Person> findall();

    @Update("update person set age = #{age} where name = #{name}")
    void updateAge(String name, String age);

    @Update("update person set sex = #{sex} where name = #{name}")
    void updateSex(String name, String sex);

    @Update("update person set income = #{income} where name = #{name}")
    void updateIncome(String name, String income);

    @Update("update person set organization = #{organization} where name = #{name}")
    void updateOrganization(String name, String organization);

    @Update("update person set job = #{job} where name = #{name}")
    void updateJob(String name, String job);

    @Select("select * from person where name = #{name}")
    Person findByName(String name);
}


