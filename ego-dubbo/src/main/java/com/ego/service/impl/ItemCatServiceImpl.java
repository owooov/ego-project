package com.ego.service.impl;

import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 11:23
 * @Description:
 */
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    //根据父节点id查询所有状态正常的子节点
    @Override
    public List<TbItemCat> selectItemCatsByPid(Long pid) {
        //创建条件构造器
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        tbItemCatExample.setOrderByClause("sort_order asc");
        tbItemCatExample.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);

        return tbItemCatMapper.selectByExample(tbItemCatExample);
    }


    //根据OID查询对应的商品分类详情
    @Override
    public TbItemCat selectById(Long id) {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }


    //查询所有商品分类
    @Override
    public List<TbItemCat> selectAllItemCat(Long pid) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        tbItemCatExample.setOrderByClause("sort_order asc");
        tbItemCatExample.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
        //判断当前节点是否是父节点，如果是父节点，给所有的子节点赋值
        for (TbItemCat tbItemCat : tbItemCats) {
            if(tbItemCat.getIsParent()){
                tbItemCat.setChildren(selectAllItemCat(tbItemCat.getId()));
            }
        }

        return tbItemCats;
    }
}
