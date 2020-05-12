package com.ego.manage.controller;

import com.ego.manage.service.ContentManageService;
import com.ego.pojo.TbContent;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/24 10:22
 * @Description:
 */
@Controller
public class ContentController {
    @Autowired
    private ContentManageService contentManageService;


    //根据内容分类id查询对应的所有内容并分页
    @RequestMapping("/content/query/list")
    @ResponseBody
    public Map<String,Object> showAllContentByCatId(Long categoryId,int page,int rows){
        return contentManageService.showAllContentByCatId(categoryId, page, rows);
    }


    //修改
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public EgoResult updateContent(TbContent tbContent){
        return contentManageService.updateContent(tbContent);
    }

    //新增
    @RequestMapping("/content/save")
    @ResponseBody
    public EgoResult insertContent(TbContent tbContent){
        return contentManageService.insertContent(tbContent);
    }
    //删除
    @RequestMapping("/content/delete")
    @ResponseBody
    public EgoResult deleteContent(@RequestParam List<Long> ids){
        return contentManageService.deleteContent(ids);
    }
}
