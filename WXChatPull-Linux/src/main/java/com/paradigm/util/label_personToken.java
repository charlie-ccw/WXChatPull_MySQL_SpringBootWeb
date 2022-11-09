package com.paradigm.util;

import com.alibaba.fastjson.JSON;
import com.paradigm.MySQL.mapper.LabelMapper;
import com.paradigm.MySQL.mapper.PersonMapper;
import com.paradigm.MySQL.mapper.domain.Label;
import com.paradigm.MySQL.mapper.domain.Person;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Component
public class label_personToken {

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private PersonMapper personMapper;

    private static String type;
    private static String value;
    private static String name;
    private static String age;
    private static String sex;
    private static String income;
    private static String organization;
    private static String job;

    public ArrayList<Label> getLabel(List<JSONObject> input, String MessageId){
        ArrayList<Label> labels = new ArrayList<>();
        // 遍历列表中的JSON
        for(int i = 0; i < input.size(); i++){
            String jsonStr= JSON.toJSONString(input.get(i));
            //提取出总结果
            if(Objects.equals(jsonStr, "{}")) return labels;  //判断是否要细分类
            //细分类结果
            JSONObject curr = new JSONObject(jsonStr);
            Iterator<String> keys = curr.keys();
            while(keys.hasNext()){
                type = keys.next();
                value = curr.get(type).toString();
                labels.add(new Label(type, value, MessageId));
            }
        }
        return labels;
    }

    public ArrayList<Person> getPerson(List<JSONObject> input){
        ArrayList<Person> people = new ArrayList<>();
        // 遍历列表中的JSON
        for(int i = 0; i < input.size(); i++){
            String jsonStr= JSON.toJSONString(input.get(i));
            //提取出总结果
            if(Objects.equals(jsonStr, "{}")) return people;  //判断是否要细分类
            //细分类赋值
            JSONObject curr = new JSONObject(jsonStr);
            name = curr.opt("姓名") != null ? curr.opt("姓名").toString() : "";
            age = curr.opt("年龄") != null ? curr.opt("年龄").toString() : "";
            sex = curr.opt("性别") != null ? curr.opt("性别").toString() : "";
            income = curr.opt("收入") != null ? curr.opt("收入").toString() : "";
            organization =  curr.opt("组织") != null ? curr.opt("组织").toString() : "";
            job =  curr.opt("职位") != null ? curr.opt("职位").toString() : "";
            // 如果这个人的信息在表中已经有存储内容，则修改信息
            if(personMapper.findByName(name) != null){
                if(!Objects.equals(age, "")) personMapper.updateAge(name, age);
                if(!Objects.equals(sex, "")) personMapper.updateSex(name, sex);
                if(!Objects.equals(income, "")) personMapper.updateIncome(name, income);
                if(!Objects.equals(organization, "")) personMapper.updateOrganization(name, organization);
                if(!Objects.equals(job, "")) personMapper.updateJob(name, job);
            }
            // 如果表中没有这个人的信息，则新建存储
            else{
                people.add(new Person(name, age, sex, income, organization, job));
            }
        }
        return people;
    }
}
