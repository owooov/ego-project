package com.ego.search.controller;

import com.ego.search.service.SearchService;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/27 10:20
 * @Description:
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    //显示搜索页
    @RequestMapping({"/search.html","/search"})
    public String showSearchPage(String q, @RequestParam(defaultValue = "1") int page, Model model){
        try {
            q = new String(q.getBytes("ISO-8859-1"),"UTF-8");
            model.addAttribute("query",q);
            model.addAttribute("page",page);
            Map<String, Object> map = searchService.showSolrItem(q, page);
            model.addAttribute("totalPages",map.get("totalPages"));
            model.addAttribute("itemList",map.get("itemList"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/WEB-INF/jsp/search.jsp";
    }

    //初始化索引库
    @RequestMapping(value = "/initSolr",produces = "appication/json;charset=utf-8")
    @ResponseBody
    public String initSolr(){
        long start = System.currentTimeMillis();
        searchService.initSolr();
        long end = System.currentTimeMillis();
        return "solr初始化用时："+(end-start)/1000/60+"分";
    }


    //根据id删除solr中数据
    @RequestMapping("/deleteSolrById")
    @ResponseBody
    public EgoResult deleteSolrById(@RequestParam Map<String,String> map){
        return searchService.deleteSolrById(map);
    }
    //删除solr中所有数据
    @RequestMapping("/deleteAll")
    @ResponseBody
    public EgoResult deleteSolrAll(){
        return searchService.deleteSolrAll();
    }
    //向solr中新增数据
    @RequestMapping("/insertSolr")
    @ResponseBody
    public EgoResult insertSolr(@RequestParam Map<String,String> map){
        return searchService.insertSolr(map);
    }
}
