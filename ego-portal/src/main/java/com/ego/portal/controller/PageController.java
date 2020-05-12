package com.ego.portal.controller;

import com.ego.portal.service.ContentPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/19 14:13
 * @Description:
 */
@Controller
public class PageController {
    @Autowired
    private ContentPortalService contentPortalService;
    //显示首页
    @RequestMapping("/")
    public String showIndex(Model model){
        model.addAttribute("ad1",contentPortalService.showBigAddContent());
        return "/WEB-INF/jsp/index.jsp";
    }
}
