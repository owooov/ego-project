package com.ego.cart.interceptor;

import com.ego.redis.dao.JedisDao;
import com.ego.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/9 14:30
 * @Description:
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisDao jedisDao;
    @Value("${redis.user.key}")
    private String key;
    @Value("${passport.url}")
    private String redirectUrl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userKey = key+cookieValue;
        String userJson = jedisDao.get(userKey);
        if(userJson != null && !"".equalsIgnoreCase(userJson)){
            return true;
        }
        response.sendRedirect(redirectUrl+"user/showLogin");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
