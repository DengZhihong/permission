package com.deng.controller;

import com.deng.common.JsonData;
import com.deng.dto.DeptLevelDto;
import com.deng.param.DeptParam;
import com.deng.service.SysDeptService;
import com.deng.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 部门模块
 *
 * @author deng.z.h
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    SysTreeService sysTreeService;


    @RequestMapping("/dept.page")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    /**
     * 新增部门部门
     *
     * @param deptParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(DeptParam deptParam) {
        sysDeptService.save(deptParam);
        return JsonData.success();
    }


    /**
     * 获取部门的树结构
     * @return
     */
    @ResponseBody
    @RequestMapping("/tree.json")
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    /**
     * 更新部门
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        sysDeptService.update(param);
        return JsonData.success();
    }

}
