package com.example.map_im.mvp.model.entity.request;

/**
 * 登陆请求体
 */
public class ReqUserLoginEntity {

    public String username;
    public String pwd;

    public ReqUserLoginEntity(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
    }

}

