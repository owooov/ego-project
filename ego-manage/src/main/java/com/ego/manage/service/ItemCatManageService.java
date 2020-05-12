package com.ego.manage.service;

import com.ego.vo.EgoTree;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 11:35
 * @Description:
 */
public interface ItemCatManageService {
    //根据父节点id查询所有状态正常的子节点
    List<EgoTree> selectItemCatsByPid(Long pid);
}
