package com.ego.service.impl;

import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.ego.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 16:25
 * @Description:
 */
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper tbItemParamMapper;
    //查询所有商品规格参数并分页显示
    @Override
    public Map<String,Object> selectAllItemParams(int page, int rows) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(page, rows);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(tbItemParams);
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }


    //根据商品分类id查询对应的商品规格参数
    @Override
    public TbItemParam selectByItemCatId(Long catId) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        tbItemParamExample.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> itemParams = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        if(itemParams != null && itemParams.size()>0){
            return itemParams.get(0);
        }
        return null;
    }


    //新增
    @Override
    public int insertItemParam(TbItemParam tbItemParam) {

        return tbItemParamMapper.insertSelective(tbItemParam);
    }


    //根据OID删除
    @Override
    public int deleteItemParamById(Long id) {
        return tbItemParamMapper.deleteByPrimaryKey(id);
    }
}
