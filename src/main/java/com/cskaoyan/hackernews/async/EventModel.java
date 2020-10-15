package com.cskaoyan.hackernews.async;

import java.util.HashMap;
import java.util.Map;


public class EventModel {

    //比如，我给你的新闻点赞了
    //我触发的，actor是我， 点赞是给你点的 entityOwner是你
    //事件类型是点赞
    //给什么点赞？
    //新闻 还是评论 ？ entityType
    //哪一条新闻？ entityId

    private EventType type; //什么事件
    private int actorId;  //事件谁触发的，哪个用户触发的。用户id即可。
    private int entityType; //事件作用对象类型
    private int entityId;   //事件作用对象的具体id
    private int entityOwnerId; //事件属于谁
    private Map<String, String> exts = new HashMap<String, String>(); //事件发生是的一些信息

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public EventModel() {

    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }
}
