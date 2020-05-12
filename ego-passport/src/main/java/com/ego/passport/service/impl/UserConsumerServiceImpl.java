package com.ego.passport.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.passport.service.UserConsumerService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
import com.ego.service.UserService;
import com.ego.utils.CookieUtils;
import com.ego.utils.JsonUtil;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/9 10:28
 * @Description:
 */
@Service
public class UserConsumerServiceImpl implements UserConsumerService {
    @Reference
    private UserService userService;
    @Autowired
    private JedisDao jedisDao;
    @Value("${redis.user.key}")
    private String key;

    //根据用户信息查询对应用户
    @Override
    public EgoResult selectByUser(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();
        //判断mysql中是否有该用户信息
        TbUser user = userService.selectByUser(tbUser);
        if(user != null){
            //对Cookie赋值
            String cookieValue = UUID.randomUUID().toString();
            CookieUtils.setCookie(request,response,"TT_TOKEN",cookieValue);
            //将当前用户信息放到redis中
            String userKey = key+cookieValue;
            jedisDao.insert(userKey, JsonUtil.objectToJson(user));
            er.setStatus(200);
            er.setMsg("OK");
            er.setData(cookieValue);

        }else{
            er.setMsg("用户名或密码不正确！");
        }

        return er;
    }


    //通过token查询用户信息
    @Override
    public EgoResult selectUserByToken(String token, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();
        if(jedisDao.exists(key+token)){
            String userJson = jedisDao.get(key + token);
            TbUser tbUser = JsonUtil.jsonToPojo(userJson, TbUser.class);
            TbUser cookieUser = new TbUser();
            cookieUser.setId(tbUser.getId());
            cookieUser.setCreated(tbUser.getCreated());
            cookieUser.setEmail(tbUser.getEmail());
            cookieUser.setPhone(tbUser.getPhone());
            cookieUser.setUpdated(tbUser.getUpdated());
            cookieUser.setUsername(tbUser.getUsername());

            er.setStatus(200);
            er.setMsg("OK");
            er.setData(cookieUser);
        }
        return er;
    }


    //安全退出
    @Override
    public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();
        String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
        //清空redis中用户数据
        String userKey = key+cookieValue;
        Long redisResult = jedisDao.del(userKey);
        //清空Cookie中用户数据
        CookieUtils.deleteCookie(request,response,"TT_TOKEN");
        if(redisResult >0){
            er.setStatus(200);
            er.setMsg("OK");
        }

        return er;
    }
}
