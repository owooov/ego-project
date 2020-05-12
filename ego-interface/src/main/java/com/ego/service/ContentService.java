package com.ego.service;

import com.ego.pojo.TbContent;

import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/24 10:03
 * @Description:
 */
public interface ContentService {

    //根据内容分类id查询对应的所有内容并分页
    Map<String,Object> selectAllContentByCatId(Long catId, int page, int rows);

    //修改
    int updateContent(TbContent tbContent);

    //根据内容分类id查询对应的所有内容
    List<TbContent> selectContentByCatId(Long catId);

    //新增
    int insertContent(TbContent tbContent);

    //删除
    int deleteContent(Long id);
}
