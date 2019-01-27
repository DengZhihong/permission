package com.deng.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 计算层级工具
 */
public class LevelUtils {


    private final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    /**
     * 根据父层级的level和parentId计算出当前的层级
     * 层级形式如下
     * 0
     * 0.1
     * 0.1.2
     * 0.1.3
     * 0.4
     *
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String caculateLevel(String parentLevel, Integer parentId) {
        if (null == parentLevel) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }

}
