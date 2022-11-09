package com.paradigm.MySQL.mapper.domain;

import org.springframework.stereotype.Component;

@Component
public class Person {

    private String name;
    private String age;
    private String sex;
    private String income;
    private String organization;
    private String job;

    public Person() {
    }

    public Person(String name, String age, String sex, String income, String organization, String job) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.income = income;
        this.organization = organization;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", income='" + income + '\'' +
                ", organization='" + organization + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
