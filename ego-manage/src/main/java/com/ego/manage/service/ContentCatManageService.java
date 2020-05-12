package com.ego.manage.service;

import com.ego.pojo.TbContentCategory;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/20 10:17
 * @Description:
 */
public interface ContentCatManageService {
    //根据父节点id查询所有子节点
    List<EgoTree> showContentCatByPid(Long pid);

    //新增
    EgoResult insertContentCat(TbContentCategory tbContentCategory);

    //重命名
    EgoResult updateContentCat(TbContentCategory tbContentCategory);

    //删除
    EgoResult deleteContentCat(TbContentCategory tbContentCategory);
}
