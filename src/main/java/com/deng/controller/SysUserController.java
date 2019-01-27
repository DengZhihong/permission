package com.deng.controller;

import com.deng.bean.PageQuery;
import com.deng.bean.PageResult;
import com.deng.common.JsonData;
import com.deng.model.SysUser;
import com.deng.param.UserParam;
import com.deng.service.SysTreeService;
import com.deng.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysTreeService sysTreeService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(UserParam param) {
        sysUserService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
        sysUserService.update(param);
        return JsonData.success();
    }
}
