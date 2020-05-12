package com.ego.passport.controller;

import com.ego.passport.service.UserConsumerService;
import com.ego.pojo.TbUser;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/9 09:35
 * @Description:
 */
@Controller
public class LoginController {
    @Autowired
    private UserConsumerService userConsumerService;
    //跳转到登录页面
    @RequestMapping("/user/showLogin")
    public String showLoginPage(@RequestHeader("Referer") String referer, Model model){
        if(referer == null){
            model.addAttribute("redirect","");
        }else{
            model.addAttribute("redirect",referer);
        }
        return "/WEB-INF/jsp/login.jsp";
    }

    //完成登录
    @RequestMapping("/user/login")
    @ResponseBody
    public EgoResult login(TbUser tbUser, HttpServletRequest request, HttpServletResponse response){

        return userConsumerService.selectByUser(tbUser,request,response);
    }

    //通过token查询用户信息
    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object showUserByToken(String callback,@PathVariable String token,HttpServletRequest request, HttpServletResponse response){
        EgoResult er = userConsumerService.selectUserByToken(token, request, response);
        if(callback != null && !"".equalsIgnoreCase(callback)){
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }else{
            return er;
        }

    }

    //安全退出
    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public Object logout(String callback,@PathVariable String token,HttpServletRequest request,HttpServletResponse response){
        EgoResult er = userConsumerService.logout(token, request, response);
        if(callback != null && !"".equalsIgnoreCase(callback)){
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }else{
            return er;
        }
    }
}
