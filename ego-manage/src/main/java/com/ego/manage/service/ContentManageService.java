package com.ego.manage.service;

import com.ego.pojo.TbContent;
import com.ego.vo.EgoResult;

import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/24 10:20
 * @Description:
 */
public interface ContentManageService {

    //根据内容分类id查询对应的所有内容并分页
    Map<String,Object> showAllContentByCatId(Long catId,int page,int rows);

    //修改
    EgoResult updateContent(TbContent tbContent);

    //新增
    EgoResult insertContent(TbContent tbContent);

    //删除
    EgoResult deleteContent(List<Long> ids);

}
