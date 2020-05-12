package com.ego.passport.service;

import com.ego.pojo.TbUser;
import com.ego.vo.EgoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/9 10:27
 * @Description:
 */
public interface UserConsumerService {
    //根据用户信息查询对应用户
    EgoResult selectByUser(TbUser tbUser, HttpServletRequest request, HttpServletResponse response);

    //通过token查询用户信息
    EgoResult selectUserByToken(String token,HttpServletRequest request, HttpServletResponse response);

    //安全退出
    EgoResult logout(String token,HttpServletRequest request, HttpServletResponse response);
}
