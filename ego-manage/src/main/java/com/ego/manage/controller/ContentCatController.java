package com.ego.manage.controller;

import com.ego.manage.service.ContentCatManageService;
import com.ego.pojo.TbContentCategory;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/20 10:22
 * @Description:
 */
@Controller
public class ContentCatController {
    @Autowired
    private ContentCatManageService contentCatManageService;


    //根据父节点id查询所有子节点
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EgoTree> showContentCatByPid(@RequestParam(defaultValue = "0") Long id){
        return contentCatManageService.showContentCatByPid(id);
    }

    //新增
    @RequestMapping("/content/category/create")
    @ResponseBody
    public EgoResult insertContentCat(TbContentCategory tbContentCategory){
        return contentCatManageService.insertContentCat(tbContentCategory);
    }

    //重命名
    @RequestMapping("/content/category/update")
    @ResponseBody
    public EgoResult updateContentCat(TbContentCategory tbContentCategory){
        return contentCatManageService.updateContentCat(tbContentCategory);
    }

    //删除
    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public EgoResult deleteContentCat(TbContentCategory tbContentCategory){
        return contentCatManageService.deleteContentCat(tbContentCategory);
    }
}
