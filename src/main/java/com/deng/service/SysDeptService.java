package com.deng.service;

import com.deng.dao.SysDeptMapper;
import com.deng.exception.ParamException;
import com.deng.model.SysDept;
import com.deng.param.DeptParam;
import com.deng.util.BeanValidator;
import com.deng.util.LevelUtils;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 部门模块服务类
 *
 * @author deng.z.h
 */
@Service
public class SysDeptService {

    @Autowired
    SysDeptMapper sysDeptMapper;


    /**
     * 新增部门
     *
     * @param deptParam
     */
    public void save(DeptParam deptParam) {
        BeanValidator.check(deptParam);

        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept sysDept = SysDept.builder()
                .name(deptParam.getName())
                .remark(deptParam.getRemark())
                .seq(deptParam.getSeq())
                .parentId(deptParam.getParentId())
                .build();

        sysDept.setLevel(LevelUtils.caculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        sysDept.setOperateIp("127.0.0.1");//TODO
        sysDept.setOperator("sys");//TODO
        sysDept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(sysDept);//插入不为空的参数
    }

    /**
     * 返回传入部门ID所属的层级
     *
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }

    /**
     * 判断在当前层级下是否存在相同名字的部门
     *
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId,deptName,deptId) > 0;
    }

    /**
     * 更新部门
     *
     * @param param
     */
    public void update(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());

        Preconditions.checkNotNull(before, "待更新的部门不存在");

        SysDept after = SysDept.builder()
                .name(param.getName())
                .remark(param.getRemark())
                .seq(param.getSeq())
                .parentId(param.getParentId())
                .id(param.getId())
                .build();

        after.setLevel(LevelUtils.caculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperateIp("127.0.0.1");//TODO
        after.setOperator("sys update");//TODO
        after.setOperateTime(new Date());

        updateWithChild(before, after);
    }

    @Transactional//不能加再private,待解决
    protected void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        //如果层级发生变化,才修改该部门所属的部门的层级
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SysDept> sysDeptList = sysDeptMapper.getChildDeptListByLevel(oldLevelPrefix);

            if (CollectionUtils.isNotEmpty(sysDeptList)) {
                for (SysDept child : sysDeptList) {
                    String level = child.getLevel();
                    //如果是和原来的同一层级,则返回-1,不修改
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        child.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(sysDeptList);
            }
        }

        sysDeptMapper.updateByPrimaryKey(after);
    }
}
