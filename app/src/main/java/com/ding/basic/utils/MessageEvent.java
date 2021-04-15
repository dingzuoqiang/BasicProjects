package com.ding.basic.utils;

/**
 * Created by ding on 2020/04/17.
 */

public class MessageEvent {
    public static final int SWITCH_TO_ZHIBO = 1;// 点击首页banner，跳转到直播列表

    public int businessType;
    private String message;
    public boolean flag;
    public int userId;

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(int businessType) {
        this.businessType = businessType;
    }

    public MessageEvent(int businessType, boolean flag) {
        this.businessType = businessType;
        this.flag = flag;
    }

    public MessageEvent(int businessType, int userId, boolean flag) {
        this.businessType = businessType;
        this.userId = userId;
        this.flag = flag;
    }

    public MessageEvent(int businessType, String message) {
        this.businessType = businessType;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}