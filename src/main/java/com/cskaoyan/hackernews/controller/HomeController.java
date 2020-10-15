package com.cskaoyan.hackernews.controller;


import com.cskaoyan.hackernews.configuration.ToutiaoConfig;
import com.cskaoyan.hackernews.model.EntityType;
import com.cskaoyan.hackernews.model.HostHolder;
import com.cskaoyan.hackernews.model.News;
import com.cskaoyan.hackernews.model.ViewObject;
import com.cskaoyan.hackernews.service.LikeService;
import com.cskaoyan.hackernews.service.NewsService;
import com.cskaoyan.hackernews.service.UserService;
import com.cskaoyan.hackernews.utils.MailSender;
import com.cskaoyan.hackernews.utils.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MailSender mailSender;

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }

        return vos;
    }

    @Autowired
     public  ToutiaoConfig toutiaoConfig;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {

        model.addAttribute("contextPath", toutiaoConfig.ContextPath);
        model.addAttribute("vos", getNews(0, 0, 10));
        if (hostHolder.getUser() != null) {//用户已经存在 不要弹出登录。
            pop = 0;
        }
        model.addAttribute("pop", pop);
        return "home";
    }

    /*@RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        return "home";
    }*/





}
