package com.yaocj.service;

import com.yaocj.dal.StudentDAO;
import com.yaocj.dal.TeacherDAO;
import com.yaocj.dal.model.StudentDO;
import com.yaocj.dal.model.TeacherDO;
import com.yaocj.form.StudentAddForm;
import com.yaocj.form.TeacherAddForm;
import com.yaocj.vo.Response;
import com.yaocj.vo.StudentVO;
import com.yaocj.vo.TeacherInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 数据更新服务类
 */
@Service
public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainService.class);

    @Autowired
    private TeacherDAO teacherDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 新增老师
     * @param addForm
     * @return
     */
    public Response addTeacher(TeacherAddForm addForm){
        // check
        if(addForm == null){
            return Response.getFailureResult("001", "入参不得为空");
        }
        if(StringUtils.isEmpty(addForm.getName())){
            return Response.getFailureResult("002", "入参姓名不得为空");
        }
        if(StringUtils.isEmpty(addForm.getAddress())){
            return Response.getFailureResult("003", "入参地址不得为空");
        }
        if(StringUtils.isEmpty(addForm.getSex())){
            return Response.getFailureResult("004", "入参性别不得为空");
        }
        if(!"男".equals(addForm.getSex()) && !"女".equals(addForm.getSex())){
            return Response.getFailureResult("005", "老师性别入参有误");
        }
        // 不能重名
        TeacherDO teacherDO = teacherDAO.selectByName(addForm.getName());
        if(!Objects.isNull(teacherDO)){
            return Response.getFailureResult("006", "有重名的老师");
        }
        List<StudentAddForm> studentAddFormList = addForm.getStudentList();
        List<StudentDO> studentDOList = new ArrayList<>();
        List<String> studentNameList = new ArrayList<>();
        for(StudentAddForm studentAddForm : studentAddFormList){
            if(studentAddFormList.contains(studentAddForm.getName())){
                return Response.getFailureResult("007", "同一个老师下的学生不得重名");
            }
            studentNameList.add(studentAddForm.getName());

            Integer age = studentAddForm.getAge();
            if(age < 10 || age > 15){
                return Response.getFailureResult("008", "学生年龄必须在[0,15]区间内");
            }

            if(StringUtils.isNotEmpty(studentAddForm.getSex())) {
                if (!"男".equals(studentAddForm.getSex()) && !"女".equals(studentAddForm.getSex())) {
                    return Response.getFailureResult("009", "学生性别入参有误");
                }
            }

            // 封装学生参数
            StudentDO param = new StudentDO();
            param.setAddress(studentAddForm.getAddress());
            param.setAge(studentAddForm.getAge());
            param.setCreateTime(new Date());
            param.setName(studentAddForm.getName());
            param.setSex(studentAddForm.getSex());
            param.setTeacherName(addForm.getName());
            param.setUpdateTime(new Date());
            studentDOList.add(param);
        }
        if(CollectionUtils.isEmpty(studentDOList)){
            return Response.getFailureResult("010", "学生至少有一个");
        }
        // 封装老师数据
        TeacherDO param = new TeacherDO();
        param.setAddress(addForm.getAddress());
        param.setCreateTime(new Date());
        param.setName(addForm.getName());
        param.setSex(addForm.getSex());
        param.setUpdateTime(new Date());

        // 插入数据
        try {
            return transactionTemplate.execute((TransactionCallback<Response>) transactionStatus -> {
                teacherDAO.insert(param);

                studentDAO.insert(studentDOList);

                return Response.getSuccessResult(true);
            });
        }catch (Exception e){
            logger.error("新增老师" + e);
            return Response.getFailureResult("100", "插入数据异常");
        }
    }

    /**
     * 根据Teacher name删除Teacher与其关联的Student数据
     */
    public Response deleteTeacher(String name){
        // check
        TeacherDO teacherDO = teacherDAO.selectByName(name);
        if(Objects.isNull(teacherDO)){
            return Response.getFailureResult("0011", "找不到对应的老师信息");
        }

        List<StudentDO> studentDOList = studentDAO.selectByTeacherName(name);
        if(CollectionUtils.isEmpty(studentDOList)){
            return Response.getFailureResult("0012", "数据有误：该老师名下没有学生信息");
        }

        // 删除数据
        try {
            return  transactionTemplate.execute((TransactionCallback<Response>) transactionStatus ->{
                teacherDAO.deleteByName(name);

                studentDAO.deleteByTeacherName(name);
                return Response.getSuccessResult(true);
            });
        }catch (Exception e){
            logger.error("根据Teacher name删除Teacher与其关联的Student数据" + e);
            return Response.getFailureResult("101", "删除数据异常");
        }
    }

    /**
     * 根据Student name删除数据; 如果关联的Teacher下无Student, 也删除
     */
    public Response deleteStudent(String name){
        // check
        StudentDO studentDO = studentDAO.selectByName(name);
        if(Objects.isNull(studentDO)){
            return Response.getFailureResult("0013", "找不到对应的学生信息");
        }
        List<StudentDO> studentDOList = studentDAO.selectByTeacherName(studentDO.getTeacherName());
        if(CollectionUtils.isEmpty(studentDOList)){
            return Response.getFailureResult("0014", "数据有误：该学生不在任何老师名下");
        }
        // 判断是否需要删除Teacher，如果只有一名学生则删除
        boolean deleteTeacherFlag = false;
        if(studentDOList.size() == 1){
            deleteTeacherFlag = true;
        }
        boolean finalFlag = deleteTeacherFlag;

        // 删除数据
        try {
            return  transactionTemplate.execute((TransactionCallback<Response>) transactionStatus ->{
                studentDAO.deleteByName(name);

                if(finalFlag){
                    teacherDAO.deleteByName(studentDO.getTeacherName());
                }
                return Response.getSuccessResult(true);
            });
        }catch (Exception e){
            logger.error("根据Student name删除数据; 如果关联的Teacher下无Student, 也删除" + e);
            return Response.getFailureResult("101", "删除数据异常");
        }
    }

    /**
     * 根据Teacher的id修改Teacher的name
     */
    public Response updateTeacherName(Integer id, String name){
        // check
        TeacherDO teacherDO = teacherDAO.selectByPrimaryKey(id);
        if(Objects.isNull(teacherDO)){
            return Response.getFailureResult("0011", "找不到对应的老师信息");
        }
        TeacherDO teacherDO1 = teacherDAO.selectByName(name);
        if(!Objects.isNull(teacherDO1)){
            return Response.getFailureResult("0015", "已有同名的老师存在");
        }
        List<StudentDO> studentDOList = studentDAO.selectByTeacherName(teacherDO.getName());
        if(CollectionUtils.isEmpty(studentDOList)){
            return Response.getFailureResult("0012", "数据有误：该老师名下没有学生信息");
        }

        // 修改
        String originalName = teacherDO.getName();
        teacherDO.setUpdateTime(new Date());
        teacherDO.setName(name);
        try{
            return transactionTemplate.execute((TransactionCallback<Response>) transactionStatus ->{
                teacherDAO.updateByPrimaryKeySelective(teacherDO);

                // 修改该老师下的所有学生关联的老师名字
                studentDAO.updateTeacherName(originalName, name);

                return Response.getSuccessResult(true);
            });
        } catch (Exception e){
            logger.error("根据Teacher的id修改Teacher的name" + e);
            return Response.getFailureResult("102", "修改数据异常");
        }
    }

    /**
     * 根据Student的id修改Teacher的name
     */
    public Response updateStudentName(Integer id, String name){
        // check
        StudentDO studentDO = studentDAO.selectByPrimaryKey(id);
        if(Objects.isNull(studentDO)){
            return Response.getFailureResult("0013", "找不到对应的学生信息");
        }

        // 修改
        TeacherDO teacherDO = teacherDAO.selectByName(studentDO.getTeacherName());
        teacherDO.setName(name);
        teacherDO.setUpdateTime(new Date());
        try{
            return transactionTemplate.execute((TransactionCallback<Response>) transactionStatus ->{
                teacherDAO.updateByPrimaryKeySelective(teacherDO);

                // 修改该老师下的所有学生关联的老师名字
                studentDAO.updateTeacherName(studentDO.getTeacherName(), name);

                return Response.getSuccessResult(true);
            });
        } catch (Exception e){
            logger.error("根据Student的id修改Teacher的name" + e);
            return Response.getFailureResult("102", "修改数据异常");
        }
    }

    /**
     * 根据Teacher name查询出Teacher及所有Student
     */
    public Response<TeacherInfoVO> getTeacherStudentInfo(String teacherName){
        // check
        TeacherDO teacherDO = teacherDAO.selectByName(teacherName);
        if(Objects.isNull(teacherDO)){
            return Response.getFailureResult("0011", "找不到对应的老师信息");
        }

        List<StudentDO> studentDOList = studentDAO.selectByTeacherName(teacherName);
        if(CollectionUtils.isEmpty(studentDOList)){
            return Response.getFailureResult("0012", "数据有误：该老师名下没有学生信息");
        }

        // 数据封装
        List<StudentVO> studentVOList = new ArrayList<>();
        for(StudentDO studentDO : studentDOList){
            StudentVO studentVO = new StudentVO();
            studentVO.setAddress(studentDO.getAddress());
            studentVO.setAge(studentDO.getAge());
            studentVO.setName(studentDO.getName());
            studentVO.setSex(studentDO.getSex());
            studentVOList.add(studentVO);
        }
        TeacherInfoVO teacherInfoVO = new TeacherInfoVO();
        teacherInfoVO.setAddress(teacherDO.getAddress());
        teacherInfoVO.setName(teacherDO.getName());
        teacherInfoVO.setSex(teacherDO.getSex());
        teacherInfoVO.setStudentList(studentVOList);
        return Response.getSuccessResult(teacherInfoVO);
    }

}
