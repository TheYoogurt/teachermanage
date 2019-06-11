package com.yaocj.service;

import com.yaocj.form.StudentAddForm;
import com.yaocj.form.TeacherAddForm;
import com.yaocj.vo.TeacherInfoVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainServiceTest {

    @Autowired
    private MainService mainService;

    @Before
    public void before(){
        System.out.printf("开始单元测试。。。。。。。。。\n");
    }

    @Test
    public void addTeacher() {
        TeacherAddForm teacherAddForm = new TeacherAddForm();
        teacherAddForm.setAddress("中国杭州XXXX");
        teacherAddForm.setName("王老师");
        teacherAddForm.setSex("男");
        List<StudentAddForm> studentAddFormList = new ArrayList<>();
        StudentAddForm studentAddForm = new StudentAddForm();
        studentAddForm.setAddress("中国上海XXX");
        studentAddForm.setAge(15);
        studentAddForm.setName("张三");
        studentAddForm.setSex("男");
        studentAddFormList.add(studentAddForm);
        StudentAddForm studentAddForm1 = new StudentAddForm();
        studentAddForm1.setAddress("中国北京XXX");
        studentAddForm1.setAge(15);
        studentAddForm1.setName("李四");
        studentAddForm1.setSex("女");
        studentAddFormList.add(studentAddForm1);
        teacherAddForm.setStudentList(studentAddFormList);
        mainService.addTeacher(teacherAddForm);
        TeacherInfoVO teacherInfoVO = mainService.getTeacherStudentInfo("王老师").getResult();
        if(teacherInfoVO != null) {
            System.out.printf(teacherInfoVO.toString() + "\n");
        } else {
            System.out.printf("王老师不存在\n");
        }
    }

    @Test
    public void deleteTeacher() {
        mainService.deleteTeacher("王老师");
        TeacherInfoVO teacherInfoVO = mainService.getTeacherStudentInfo("王老师").getResult();
        if(teacherInfoVO != null) {
            System.out.printf(teacherInfoVO.toString() + "\n");
        } else {
            System.out.printf("王老师不存在\n");
        }
    }

    @Test
    public void deleteStudent() {
        mainService.deleteStudent("张三");
        TeacherInfoVO teacherInfoVO = mainService.getTeacherStudentInfo("王老师").getResult();
        if(teacherInfoVO != null) {
            System.out.printf(teacherInfoVO.toString());
        } else {
            System.out.printf("王老师不存在\n");
        }

        mainService.deleteStudent("李四");
        TeacherInfoVO teacherInfoVO1 = mainService.getTeacherStudentInfo("王老师").getResult();
        if(teacherInfoVO1 != null) {
            System.out.printf(teacherInfoVO1.toString() + "\n");
        } else {
            System.out.printf("王老师不存在\n");
        }
    }

    @Test
    public void updateTeacherName() {
        mainService.updateTeacherName(1, "张老师");
        TeacherInfoVO teacherInfoVO = mainService.getTeacherStudentInfo("张老师").getResult();
        if(teacherInfoVO != null) {
            System.out.printf(teacherInfoVO.toString() + "\n");
        } else {
            System.out.printf("张老师不存在\n");
        }
        TeacherInfoVO teacherInfoVO1 = mainService.getTeacherStudentInfo("王老师").getResult();
        if(teacherInfoVO1 != null) {
            System.out.printf(teacherInfoVO1.toString() + "\n");
        } else {
            System.out.printf("王老师不存在\n");
        }
    }

    @Test
    public void updateStudentName() {
        mainService.updateStudentName(1, "白老师");
        TeacherInfoVO teacherInfoVO = mainService.getTeacherStudentInfo("白老师").getResult();
        if(teacherInfoVO != null) {
            System.out.printf(teacherInfoVO.toString() + "\n");
        } else {
            System.out.printf("白老师不存在\n");
        }
        TeacherInfoVO teacherInfoVO1 = mainService.getTeacherStudentInfo("王老师").getResult();
        if(teacherInfoVO1 != null) {
            System.out.printf(teacherInfoVO1.toString() + "\n");
        } else {
            System.out.printf("王老师不存在\n");
        }
        TeacherInfoVO teacherInfoVO2 = mainService.getTeacherStudentInfo("张老师").getResult();
        if(teacherInfoVO2 != null) {
            System.out.printf(teacherInfoVO2.toString() + "\n");
        } else {
            System.out.printf("张老师不存在\n");
        }
    }

    @After
    public void after(){
        System.out.printf("结束单元测试。。。。。。。。。");
    }
}