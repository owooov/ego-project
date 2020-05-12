package com.ego.manage.service;

import com.ego.pojo.TbItemParam;
import com.ego.vo.EgoResult;

import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 16:31
 * @Description:
 */
public interface ItemParamManageService {
    //查询所有商品规格参数并分页显示
    Map<String,Object> showAllItemParams(int page,int rows);

    //根据商品分类id查询对应的商品规格参数
    EgoResult selectItemParamByCatId(Long catId);

    //新增
    EgoResult insertItemParam(TbItemParam tbItemParam);

    //删除
    EgoResult deleteItemParamById(List<Long> ids);
}
