package com.yaocj.form;

/**
 * Controller 通用入参
 */
public class CommonForm {
    private String name;

    private Integer id;

    private String teacherName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "CommonForm{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
