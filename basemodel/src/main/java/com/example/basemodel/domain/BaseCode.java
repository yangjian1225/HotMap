package com.example.basemodel.domain;

public class BaseCode {

    /**
     * 判断是否第一次运行软件
     */
    public static final String SP_IS_FIRST_OPEN = "firstState";
    public static final String SP_IS_FIRST_OPEN_KEY = "is_first_open";

    /**
     * 判断是否登陆成功
     */
    public static final String SP_LOGIN = "loginState";
    public static final String SP_IS_LOGIN_KEY = "isLogin";
    public static final String SP_NOW_LOGIN_USERNAME_KEY = "nowLoginUserName";

    /**
     * 用户信息数据库
     */
    public static final String SQLITE_USER_DB = "userDB";
    public static final String SQLITE_USER_TABLE_NAME = "users";

    /**
     * ARouter 跳转
     */
    //登陆后的首页
    public static final String AROUTER_MAIN_MODULE_MAPACTIVITY = "/mainmodule/MapActivity";

}
