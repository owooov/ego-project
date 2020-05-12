package com.ego.service.impl;

import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import com.ego.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/20 10:13
 * @Description:
 */
public class ContentCatServiceImpl implements ContentCatService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    //根据父节点id查询所有子节点
    @Override
    public List<TbContentCategory> selectContentCatByPid(Long pid) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.setOrderByClause("sort_order asc");
        example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);

        return tbContentCategoryMapper.selectByExample(example);
    }


    //新增
    @Override
    public int insertContentCat(TbContentCategory tbContentCategory) {
        return tbContentCategoryMapper.insertSelective(tbContentCategory);
    }


    //通用查询
    @Override
    public List<TbContentCategory> selectContentCatByCondition(TbContentCategory tbContentCategory) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        if(tbContentCategory.getSortOrder() != null){
            example.setOrderByClause("sort_order asc");
        }
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        if(tbContentCategory.getId()!= null){
            criteria.andIdEqualTo(tbContentCategory.getId());
        }
        if(tbContentCategory.getParentId()!= null){
            criteria.andParentIdEqualTo(tbContentCategory.getParentId());
        }
        if(tbContentCategory.getIsParent()!= null){
            criteria.andIsParentEqualTo(tbContentCategory.getIsParent());
        }
        if(tbContentCategory.getName() != null && ""!=tbContentCategory.getName()){
            criteria.andNameEqualTo(tbContentCategory.getName());
        }
        if(tbContentCategory.getStatus() != null){
            criteria.andStatusEqualTo(tbContentCategory.getStatus());
        }


        return tbContentCategoryMapper.selectByExample(example);
    }


    //修改
    @Override
    public int updateContentCat(TbContentCategory tbContentCategory) {
        return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }
}
