package com.yaocj.dal;


import com.yaocj.dal.model.StudentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDAO {
    StudentDO selectByPrimaryKey(Integer id);

    int deleteByName(String name);

    int deleteByTeacherName(String teacherName);

    int insert(@Param("studentDOList") List<StudentDO> studentDOList);

    StudentDO selectByName(String name);

    List<StudentDO> selectByTeacherName(String teacherName);

    int updateByPrimaryKeySelective(StudentDO record);

    /**
     * 修改老师的名字
     * @param name1 原来的名字
     * @param name2 修改的名字
     * @return
     */
    int updateTeacherName(@Param("name1") String name1, @Param("name2") String name2);
}