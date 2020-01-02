package com.example.map_im.mvp.model.entity.request;

/**
 * 注册请求体
 */
public class ReqUserRegisterEntity {

    /**
     * {
     * "id": 1,
     * "usercode": "123",    用户编码
     * "username": "123456",
     * "pwd": "123456",
     * "sex": "男",
     * "birthday": "12.25",
     * "headerimg": "http://b-ssl.duitang.com/uploads/item/201808/18/20180818005540_usocu.thumb.700_0.jpg",
     * "nick": "哈哈",
     * "utype": 0,    账户类型 0-密码账户 1-微信 2-qq 3-新浪微博
     * "imuseraccount": "123456",   im系统中账号
     * "signdesc": "好好学习",    签名信息
     * "openlocation": 0,     0-开启定位 1-关闭定位
     * "openmsgalert": 0      0-关闭提醒 1-震动 2-响铃 3-震动并响铃
     * }
     */

    public String usercode = System.currentTimeMillis()+"";
    public String username;
    public String pwd;
    public String sex;
    public String birthday;
    public String headerimg = "http://b-ssl.duitang.com/uploads/item/201808/18/20180818005540_usocu.thumb.700_0.jpg";
    public String nick;
    public int utype = 0;
    public String imuseraccount;
    public String signdesc = "好好学习";
    public int openlocation = 1;
    public int openmsgalert = 3;

    public ReqUserRegisterEntity(String username, String pwd, String sex,
                                 String birthday, String nick) {
        this.username = username;
        this.pwd = pwd;
        this.sex = sex;
        this.birthday = birthday;
        this.nick = nick;
        this.imuseraccount = username;
    }
}
