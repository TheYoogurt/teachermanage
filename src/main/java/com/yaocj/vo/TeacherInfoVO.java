package com.yaocj.vo;

import java.util.List;

/**
 * 返回前端的老师与学生信息
 */
public class TeacherInfoVO {
    private String name;

    private String sex;

    private String address;

    private List<StudentVO> studentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<StudentVO> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentVO> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "TeacherInfoVO{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", studentList=" + studentList +
                '}';
    }
}
