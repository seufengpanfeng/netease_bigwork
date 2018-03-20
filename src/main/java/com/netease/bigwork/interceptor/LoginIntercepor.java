package com.netease.bigwork.interceptor;

import com.netease.bigwork.Model.HostHolder;
import com.netease.bigwork.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by feng on 2018/2/7.
 */
public class LoginIntercepor implements HandlerInterceptor {
    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (httpServletRequest.getCookies()!=null) {// != null
            for (Cookie cookie : httpServletRequest.getCookies()) {
                    if(cookie.getName().equals("user")){
                    User user = new User();
                    user.setUsername(cookie.getValue());
                    hostHolder.setUser(user);
                }
            }
        } else{
                httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
                return false;
        }
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
