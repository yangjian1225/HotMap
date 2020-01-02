package com.example.map_im.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.basemodel.domain.BaseCode;
import com.example.map_im.mvp.model.entity.response.ResUserInfoEntity;

/**
 * 用户信息数据库
 */
public class UserSQLite extends SQLiteOpenHelper {

    private static volatile UserSQLite userSQLite;

    /**
     * 单例获取数据库
     * @param context
     * @return
     */
    public static UserSQLite getInstance(Context context){
        if (userSQLite == null){
            synchronized (UserSQLite.class){
                if (userSQLite == null){
                    userSQLite = new UserSQLite(context);
                }
            }
        }
        return userSQLite;
    }


    private UserSQLite(@Nullable Context context) {
        super(context, BaseCode.SQLITE_USER_DB, null, 1);
    }

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+BaseCode.SQLITE_USER_TABLE_NAME+"(id Integer,usercode Long,username varchar(100),pwd varchar(100)," +
                "sex varchar(50),birthday varchar(100),headerimg text,nick varchar(100),utype Integer," +
                "imuseraccount varchar(50),signdesc varchar(500),openlocation Integer,openmsgalert Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加登陆的用户
     * @param userRegisterEntity
     * @return
     */
    public long addUser(ResUserInfoEntity.Data userRegisterEntity){
        if (userRegisterEntity == null){
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues(13);
        values.put("id",userRegisterEntity.id);
        values.put("usercode",userRegisterEntity.usercode);
        values.put("username",userRegisterEntity.username);
        values.put("pwd",userRegisterEntity.pwd);
        values.put("sex",userRegisterEntity.sex);
        values.put("birthday",userRegisterEntity.birthday);
        values.put("headerimg",userRegisterEntity.headerimg);
        values.put("nick",userRegisterEntity.nick);
        values.put("utype",userRegisterEntity.utype);
        values.put("imuseraccount",userRegisterEntity.imuseraccount);
        values.put("signdesc",userRegisterEntity.signdesc);
        values.put("openlocation",userRegisterEntity.openlocation);
        values.put("openmsgalert",userRegisterEntity.openmsgalert);
        return db.insert(BaseCode.SQLITE_USER_TABLE_NAME, null, values);
    }

    /**
     * 根据用户名修改密码
     * @param name
     * @param pwd
     * @return
     */
    public int modifyPwdByName(String name,String pwd){
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pwd",pwd);
        return db.update(BaseCode.SQLITE_USER_TABLE_NAME, values, "username", new String[]{name});
    }

    /**
     * 根据用户名修改该用户的信息
     * @param name
     * @param signdesc
     * @param nick
     * @param headerimg
     * @param birthday
     * @param sex
     * @return
     */
    public int modifySigndescByName(String name,String signdesc,String nick,String headerimg,String birthday,String sex){
        if (TextUtils.isEmpty(nick)){
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("signdesc",signdesc);
        values.put("nick",nick);
        values.put("headerimg",headerimg);
        values.put("birthday",birthday);
        values.put("sex",sex);
        return db.update(BaseCode.SQLITE_USER_TABLE_NAME, values, "username", new String[]{name});
    }

    /**
     * 修改用户定位的开关
     * @param name
     * @param openlocation
     * @return
     */
    public int modifyLocationStateByName(String name,int openlocation){
        if (TextUtils.isEmpty(name)){
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("openlocation",openlocation);
        return db.update(BaseCode.SQLITE_USER_TABLE_NAME, values, "username", new String[]{name});
    }

    /**
     * 修改用户消息提醒的模式
     * @param name
     * @param openmsgalert
     * @return
     */
    public int modifyMsgalertStateByName(String name,int openmsgalert){
        if (TextUtils.isEmpty(name)){
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("openmsgalert",openmsgalert);
        return db.update(BaseCode.SQLITE_USER_TABLE_NAME, values, "username", new String[]{name});
    }

    /**
     * 获取用户定位的状态
     * @param name
     * @return
     */
    @SuppressLint("Recycle")
    public int getLocationStateByName(String name){
        if (TextUtils.isEmpty(name)){
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(BaseCode.SQLITE_USER_TABLE_NAME, new String[]{"openlocation"}, "username=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()){
            return cursor.getInt(cursor.getColumnIndex("openlocation"));
        }else {
            return -1;
        }
    }

    /**
     * 获取用户提醒的状态
     * @param name
     * @return
     */
    @SuppressLint("Recycle")
    public int getMsgalertStateByName(String name){
        if (TextUtils.isEmpty(name)){
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(BaseCode.SQLITE_USER_TABLE_NAME, new String[]{"openmsgalert"}, "username=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()){
            return cursor.getInt(cursor.getColumnIndex("openmsgalert"));
        }else {
            return -1;
        }
    }

    /**
     * 根据用户名获取密码
     * @param name
     * @return
     */
    @SuppressLint("Recycle")
    public String getPwdByName(String name){
        if (TextUtils.isEmpty(name)){
            return "-1";
        }
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(BaseCode.SQLITE_USER_TABLE_NAME, new String[]{"pwd"}, "username=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex("pwd"));
        }else {
            return null;
        }
    }
}
