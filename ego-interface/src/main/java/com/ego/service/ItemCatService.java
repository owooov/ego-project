package com.ego.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 11:20
 * @Description:
 */
public interface ItemCatService {
    //根据父节点id查询所有状态正常的子节点
    List<TbItemCat> selectItemCatsByPid(Long pid);

    //根据OID查询对应的商品分类详情
    TbItemCat selectById(Long id);

    //查询所有商品分类
    List<TbItemCat> selectAllItemCat(Long pid);
}
