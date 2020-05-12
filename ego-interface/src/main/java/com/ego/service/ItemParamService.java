package com.ego.service;

import com.ego.pojo.TbItemParam;

import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 16:23
 * @Description:
 */
public interface ItemParamService {
    //查询所有商品规格参数并分页显示
    Map<String,Object> selectAllItemParams(int page, int rows);

    //根据商品分类id查询对应的商品规格参数
    TbItemParam selectByItemCatId(Long catId);

    //新增商品规格参数
    int insertItemParam(TbItemParam tbItemParam);

    //根据OID删除
    int deleteItemParamById(Long id);
}
