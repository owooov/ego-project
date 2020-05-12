package com.ego.service.impl;

import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.ego.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/24 10:05
 * @Description:
 */
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;
    //根据内容分类id查询对应的所有内容并分页
    @Override
    public Map<String,Object> selectAllContentByCatId(Long catId, int page, int rows) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        if (catId != 0) {
            example.createCriteria().andCategoryIdEqualTo(catId);
        }
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }


    //修改
    @Override
    public int updateContent(TbContent tbContent) {

        return tbContentMapper.updateByPrimaryKeySelective(tbContent);
    }


    //根据内容分类id查询对应的所有内容
    @Override
    public List<TbContent> selectContentByCatId(Long catId) {
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(catId);

        return tbContentMapper.selectByExample(example);
    }


    //新增
    @Override
    public int insertContent(TbContent tbContent) {
        return tbContentMapper.insertSelective(tbContent);
    }


    //删除
    @Override
    public int deleteContent(Long id) {
        return tbContentMapper.deleteByPrimaryKey(id);
    }
}
