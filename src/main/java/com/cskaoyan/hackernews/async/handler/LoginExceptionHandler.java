package com.cskaoyan.hackernews.async.handler;

import com.cskaoyan.hackernews.async.EventHandler;
import com.cskaoyan.hackernews.async.EventModel;
import com.cskaoyan.hackernews.async.EventType;
import com.cskaoyan.hackernews.model.Message;
import com.cskaoyan.hackernews.service.MessageService;
import com.cskaoyan.hackernews.utils.MailSender;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    MailSender mailSender;


    @Override
    public void doHandle(EventModel model) {
        // 判断是否有异常登陆
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("你上次的登陆ip异常");
        message.setFromId(3);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", model.getExt("username"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆异常", "mails/welcome.html",
                map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
