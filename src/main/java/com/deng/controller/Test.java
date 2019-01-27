package com.deng.controller;

import com.deng.common.ApplicationContextHelper;
import com.deng.common.JsonData;
import com.deng.dao.SysAclModuleMapper;
import com.deng.exception.PermissionException;
import com.deng.model.SysAclModule;
import com.deng.param.TestVo;
import com.deng.util.BeanValidator;
import com.deng.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/test")
@Slf4j
public class Test {

    @RequestMapping("/hello.page")
    @ResponseBody
    public JsonData test() {
        throw new PermissionException("" +
                "cuowu la ");
//        return JsonData.success("hello");
    }


    @ResponseBody
    @RequestMapping("/validate.json")
    public JsonData validate(TestVo vo) {

        log.info("validate");
        BeanValidator.check(vo);

        Test test = ApplicationContextHelper.popBean(Test.class);
        SysAclModuleMapper sysAclModuleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(1);

        log.info(JsonMapper.obj2String(sysAclModule));

        return JsonData.success("validate vo");
    }
}

