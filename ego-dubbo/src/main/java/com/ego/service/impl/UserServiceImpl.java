package com.ego.service.impl;

import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import com.ego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/9 10:21
 * @Description:
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper tbUserMapper;

    //根据用户信息查询对应用户
    @Override
    public TbUser selectByUser(TbUser tbUser) {
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> userList = tbUserMapper.selectByExample(tbUserExample);
        if(userList!= null && userList.size()>0){
            return userList.get(0);
        }
        return null;
    }
}
