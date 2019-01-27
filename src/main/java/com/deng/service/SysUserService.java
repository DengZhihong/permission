package com.deng.service;

import com.deng.bean.PageQuery;
import com.deng.bean.PageResult;
import com.deng.common.RequestHolder;
import com.deng.dao.SysUserMapper;
import com.deng.exception.ParamException;
import com.deng.model.SysUser;
import com.deng.param.DeptParam;
import com.deng.param.UserParam;
import com.deng.util.BeanValidator;
import com.deng.util.IpUtil;
import com.deng.util.MD5Util;
import com.deng.util.PasswordUtils;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }

        String password = PasswordUtils.randomPassword();
        password = "123456";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();

        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//        user.setOperateIp("128.0.0.1");
//        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperator("sys");
        user.setOperateTime(new Date());
        //TODO send email,
        sysUserMapper.insertSelective(user);
    }

    private boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);

    }

    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyWord(keyword);
    }

    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0) {
            List<SysUser> sysUserList = sysUserMapper.getPageByDeptId(deptId, page);
            return PageResult.<SysUser>builder().data(sysUserList).total(count).build();
        }
        return PageResult.<SysUser>builder().build();
    }
}
