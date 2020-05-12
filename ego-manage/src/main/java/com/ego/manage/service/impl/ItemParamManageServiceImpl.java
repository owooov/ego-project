package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.pojo.ItemParamChild;
import com.ego.manage.service.ItemParamManageService;
import com.ego.pojo.TbItemParam;
import com.ego.service.ItemCatService;
import com.ego.service.ItemParamService;
import com.ego.vo.EgoResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 16:33
 * @Description:
 */
@Service
public class ItemParamManageServiceImpl implements ItemParamManageService {
    @Reference
    private ItemParamService itemParamService;
    @Reference
    private ItemCatService itemCatService;

    //查询所有商品规格参数并分页显示
    @Override
    public Map<String, Object> showAllItemParams(int page, int rows) {

        Map<String, Object> map = itemParamService.selectAllItemParams(page, rows);
        List<ItemParamChild> itemParamChildList = new ArrayList<>();
        List<TbItemParam> itemParams = (List<TbItemParam>) map.get("rows");
        for (TbItemParam itemParam : itemParams) {
            ItemParamChild itemParamChild = new ItemParamChild();
            itemParamChild.setId(itemParam.getId());
            itemParamChild.setItemCatId(itemParam.getItemCatId());
            itemParamChild.setCreated(itemParam.getCreated());
            itemParamChild.setUpdated(itemParam.getUpdated());
            itemParamChild.setParamData(itemParam.getParamData());

            itemParamChild.setItemCatName(itemCatService.selectById(itemParam.getItemCatId()).getName());
            itemParamChildList.add(itemParamChild);

        }

        map.put("rows",itemParamChildList);

        return map;
    }


    //根据商品分类id查询对应的商品规格参数
    @Override
    public EgoResult selectItemParamByCatId(Long catId) {
        EgoResult er = new EgoResult();
        TbItemParam tbItemParam = itemParamService.selectByItemCatId(catId);
        if(tbItemParam != null){
            er.setStatus(200);
            er.setData(tbItemParam);
        }
        return er;
    }


    //新增
    @Override
    public EgoResult insertItemParam(TbItemParam tbItemParam) {
        EgoResult er= new EgoResult();
        Date date = new Date();
        tbItemParam.setCreated(date);
        tbItemParam.setUpdated(date);
        int result = itemParamService.insertItemParam(tbItemParam);
        if(result > 0){
            er.setStatus(200);
        }
        return er;
    }

    //删除
    @Override
    public EgoResult deleteItemParamById(List<Long> ids) {
        EgoResult er = new EgoResult();
        int result = 0;
        for (Long id : ids) {

            result += itemParamService.deleteItemParamById(id);
        }
        if(result == ids.size()){
            er.setStatus(200);
        }
        return er;
    }
}
