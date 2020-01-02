package com.example.map_im.mvp.model.entity;

import com.example.basemodel.mvp.model.BaseEntity;
import com.example.map_im.mvp.model.entity.request.ReqUserLoginEntity;
import com.example.map_im.mvp.model.entity.request.ReqUserRegisterEntity;
import com.example.map_im.mvp.model.entity.response.ResUserInfoEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 登陆注册网络请求接口
 */
public interface AppApi {

    @POST("api/User/login")
    Observable<ResUserInfoEntity>login(@Body ReqUserLoginEntity loginEntity);

    @POST("api/User/register")
    Observable<ResUserInfoEntity>register(@Body ReqUserRegisterEntity registerEntity);
}
