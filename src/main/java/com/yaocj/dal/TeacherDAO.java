package com.yaocj.dal;


import com.yaocj.dal.model.TeacherDO;

public interface TeacherDAO {
    int deleteByName(String name);

    int insert(TeacherDO record);

    TeacherDO selectByPrimaryKey(Integer id);

    TeacherDO selectByName(String name);

    int updateByPrimaryKeySelective(TeacherDO record);
}