package com.cskaoyan.hackernews.async.handler;


import com.cskaoyan.hackernews.async.EventHandler;
import com.cskaoyan.hackernews.async.EventModel;
import com.cskaoyan.hackernews.async.EventType;
import com.cskaoyan.hackernews.model.Message;
import com.cskaoyan.hackernews.model.User;
import com.cskaoyan.hackernews.service.MessageService;
import com.cskaoyan.hackernews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(3);
        message.setToId(model.getEntityOwnerId());
        //message.setToId(model.getActorId());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                + "赞了你的资讯,http://127.0.0.1:8080/news/" + model.getEntityId());
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
