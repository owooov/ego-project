package com.ego.service;

import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/20 10:11
 * @Description:
 */
public interface ContentCatService {
    //根据父节点id查询所有子节点
    List<TbContentCategory> selectContentCatByPid(Long pid);

    //新增
    int insertContentCat(TbContentCategory tbContentCategory);

    //通用查询
    List<TbContentCategory> selectContentCatByCondition(TbContentCategory tbContentCategory);

    //修改
    int updateContentCat(TbContentCategory tbContentCategory);
}
