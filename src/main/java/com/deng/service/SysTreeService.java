package com.deng.service;

import com.deng.dao.SysDeptMapper;
import com.deng.dto.DeptLevelDto;
import com.deng.model.SysDept;
import com.deng.util.LevelUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


/**
 * 获取层级树的服务类
 */
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {

    @Autowired
    SysDeptMapper sysDeptMapper;

    /**
     * 返回部门层级数
     *
     * @return
     */
    public List<DeptLevelDto> deptTree() {
        //先获取全部部门
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> deptlevelDtoList = Lists.newArrayList();
        //转成部门层级DTO的list
        for (SysDept sysDept : deptList) {
            deptlevelDtoList.add(DeptLevelDto.adapt(sysDept));
        }

        return deptListToTree(deptlevelDtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptlevelDtoList) {
        if (CollectionUtils.isEmpty(deptlevelDtoList)) {
            return Collections.emptyList();
        }
        //multimap相当于 key是String, value是一个list<T>
        Multimap<String, DeptLevelDto> deptLevelDtoMultimap = ArrayListMultimap.create();
        //根层级
        List<DeptLevelDto> rootList = Lists.newArrayList();
        //将每一个dto添加到对应的level key中
        for (DeptLevelDto deptLevelDto : deptlevelDtoList) {
            deptLevelDtoMultimap.put(deptLevelDto.getLevel(), deptLevelDto);
            //如果是属于根层级,添加到rootList中
            if (LevelUtils.ROOT.equals(deptLevelDto.getLevel())) {
                rootList.add(deptLevelDto);
            }
        }

        Collections.sort(rootList, deptSeqComparator);

        transformDeptTree(rootList, LevelUtils.ROOT, deptLevelDtoMultimap);
        return rootList;
    }

    /**
     * 生成递归树
     *
     * @param deptLevelList
     * @param level
     * @param deptLevelDtoMultimap
     */
    private void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> deptLevelDtoMultimap) {
        //处理当前层
        for (DeptLevelDto curDto : deptLevelList) {
            //得出下一层级
            String nextLevel = LevelUtils.caculateLevel(level, curDto.getId());
            //得出下一层级的list
            List<DeptLevelDto> nextList = (List<DeptLevelDto>) deptLevelDtoMultimap.get(nextLevel);
//            如果有下一层级,则继续递归
            if (!CollectionUtils.isEmpty(nextList)) {
                //给下一层级排序
                Collections.sort(nextList, deptSeqComparator);
                //给当前部门设置它所属的下一层级的list
                curDto.setDeptList(nextList);
                //进入下一层
                transformDeptTree(nextList, nextLevel, deptLevelDtoMultimap);
            }

        }

    }

    /**
     * 比较器,序号从小到大排列部门
     */
    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
