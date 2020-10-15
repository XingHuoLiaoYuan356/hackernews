package com.cskaoyan.hackernews.interceptor;

import com.cskaoyan.hackernews.configuration.ToutiaoConfig;
import com.cskaoyan.hackernews.dao.LoginTicketDAO;
import com.cskaoyan.hackernews.dao.UserDAO;
import com.cskaoyan.hackernews.model.HostHolder;
import com.cskaoyan.hackernews.model.LoginTicket;
import com.cskaoyan.hackernews.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 *这个拦截器的作用是：
 * prehandler里判断用户是否是有自动登录的cookie信息，如果有，就查询出user信息，并放行。
 * postHandle里判断，如果返回的视图是一个模板视图，则把user信息放到model里，这样渲染出来的
 * 页面的header里，就会显示用户的信息
 *
 */
 @Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HostHolder hostHolder;


    @Autowired
    private ToutiaoConfig toutiaoConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }

            User user = userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }

         modelAndView.addObject("contextPath",toutiaoConfig.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
