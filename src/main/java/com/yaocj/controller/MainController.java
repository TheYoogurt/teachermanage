package com.yaocj.controller;

import com.yaocj.form.CommonForm;
import com.yaocj.form.TeacherAddForm;
import com.yaocj.service.MainService;
import com.yaocj.vo.Response;
import com.yaocj.vo.TeacherInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    /**
     * 新增老师与学生
     */
    @RequestMapping(value = "/add")
    public Response addTeacher(@RequestBody TeacherAddForm teacherAddForm){
        return mainService.addTeacher(teacherAddForm);
    }

    /**
     * 根据Teacher name删除Teacher与其关联的Student数据
     */
    @RequestMapping(value = "/teacher/delete")
    public Response deleteTeacher(@RequestBody CommonForm commonForm){
        return mainService.deleteTeacher(commonForm.getName());
    }

    /**
     * 根据Student name删除数据; 如果关联的Teacher下无Student, 也删除
     */
    @RequestMapping(value = "/student/delete")
    public Response deleteStudent(@RequestBody CommonForm commonForm){
        return mainService.deleteStudent(commonForm.getName());
    }

    /**
     * 根据Teacher的id修改Teacher的name
     */
    @RequestMapping(value = "/teacher/update")
    public Response updateTeacherName(@RequestBody CommonForm commonForm){
        return mainService.updateTeacherName(commonForm.getId(), commonForm.getName());
    }

    /**
     * 根据Student的id修改Teacher的name
     */
    @RequestMapping(value = "/student/update")
    public Response updateStudentName(@RequestBody CommonForm commonForm){
        return mainService.updateStudentName(commonForm.getId(), commonForm.getName());
    }

    /**
     * 根据Teacher name查询出Teacher及所有Student
     */
    @RequestMapping(value = "/teacher/get")
    public Response<TeacherInfoVO> getTeacherStudentInfo(@RequestBody CommonForm commonForm){
        return mainService.getTeacherStudentInfo(commonForm.getTeacherName());
    }

}
