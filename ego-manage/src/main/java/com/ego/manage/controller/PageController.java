package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Victor Wu
 * @Date: 2019/11/7 15:25
 * @Description:
 */
@Controller
public class PageController {
    //显示首页
    @RequestMapping("/")
    public String showIndex(){
        return "/WEB-INF/jsp/index.jsp";
    }

    //显示其他页面
    @RequestMapping("/{page}")
    public String showOtherPage(@PathVariable String page){
        return "/WEB-INF/jsp/"+page+".jsp";
    }
}
