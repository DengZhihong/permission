package com.deng.dao;

import com.deng.bean.PageQuery;
import com.deng.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByKeyWord(@Param("keyword") String keyword);

    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    int countByTelephone(@Param("telephone")String telephone, @Param("id")Integer id);

    int countByDeptId(@Param("id")Integer id);

    List<SysUser> getPageByDeptId(@Param("id")Integer id, @Param("page") PageQuery page);
}