package com.yaocj.form;

import java.util.List;

/**
 * 新增老师入参
 */
public class TeacherAddForm {

    private String name;

    private String sex;

    private String address;

    private List<StudentAddForm> studentList;

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

    public List<StudentAddForm> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentAddForm> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "TeacherAddForm{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", studentAddFormList=" + studentList.toString() +
                '}';
    }
}
