package com.example.map_im.mvp.model.entity.response;

import com.example.basemodel.mvp.model.BaseEntity;

/**
 * 登陆注册的响应体
 */
public class ResUserInfoEntity extends BaseEntity {

    public Data data;

    public class Data{
        public int id;
        public String usercode;
        public String username;
        public String pwd;
        public String sex;
        public String birthday;
        public String headerimg;
        public String nick;
        public int utype;
        public String imuseraccount;
        public String signdesc;
        public int openlocation;
        public int openmsgalert;
    }

}
