package com.ego.service;

import com.ego.pojo.TbUser;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/9 10:20
 * @Description:
 */
public interface UserService {
    //根据用户信息查询对应用户
    TbUser selectByUser(TbUser tbUser);
}
