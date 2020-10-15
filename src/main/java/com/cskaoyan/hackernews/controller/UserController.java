package com.cskaoyan.hackernews.controller;

import com.cskaoyan.hackernews.async.EventModel;
import com.cskaoyan.hackernews.async.EventProducer;
import com.cskaoyan.hackernews.async.EventType;
import com.cskaoyan.hackernews.configuration.ToutiaoConfig;
import com.cskaoyan.hackernews.model.User;
import com.cskaoyan.hackernews.service.UserService;

import com.cskaoyan.hackernews.utils.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    ToutiaoConfig toutiaoConfig;


    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0, "注册成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }
    }

    @RequestMapping(path = {"/login/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);  //5day
                }
                response.addCookie(cookie);
                // eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                //        .setActorId((int) map.get("userId"))
                //       .setExt("username", username).setExt("email", "zjuyxy@qq.com"));
                return ToutiaoUtil.getJSONString(0, "成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }
    }

    @RequestMapping(path = {"/logout/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/"+ toutiaoConfig.getContextPath();
    }


    @RequestMapping(path = {"/user/{id}/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String getUserInfo(@PathVariable("id") int id ,Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user",user);
        return "personal";
    }

    @RequestMapping(path = {"/user/tosendmsg"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String toSendMsgPage( ) {

        return "sendmsg";
    }


}
