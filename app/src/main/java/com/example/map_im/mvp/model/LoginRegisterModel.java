package com.example.map_im.mvp.model;

import android.annotation.SuppressLint;

import com.baweigame.xmpplibrary.XmppManager;
import com.example.basemodel.mvp.model.BaseModel;
import com.example.commonmodel.rx.RetrofitManager;
import com.example.commonmodel.utils.LogUtils;
import com.example.map_im.domain.AppCode;
import com.example.map_im.mvp.contract.ILoginRegisterContract;
import com.example.map_im.mvp.model.entity.AppApi;
import com.example.map_im.mvp.model.entity.request.ReqUserLoginEntity;
import com.example.map_im.mvp.model.entity.request.ReqUserRegisterEntity;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;

/**
 * 登陆注册功能实现的model类
 */
public class LoginRegisterModel extends BaseModel implements ILoginRegisterContract.ILoginRegisterModel {

    @Inject
    public LoginRegisterModel() {
    }

    @Override
    public Observable<Object> request(Map<String, Object> map) {
        int code = (int) map.get(AppCode.TAG);
        if (code == AppCode.login){
            ReqUserLoginEntity loginEntity = (ReqUserLoginEntity) map.get("loginEntity");
            Observable observable1 = Observable.fromArray(RetrofitManager.getInstance().getRetrofit().create(AppApi.class).login(loginEntity)).flatMap((Function) Functions.identity());
            Observable<Boolean> observable2 = Observable.create(new ObservableOnSubscribe<Boolean>() {

                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                    emitter.onNext(XmppManager.getInstance().getXmppUserManager().login(loginEntity.username, loginEntity.pwd));
                }

            });
            Observable ob = Observable.merge(observable1, observable2);
            return ob;
        }else if (code == AppCode.register){
            ReqUserRegisterEntity registerEntity = (ReqUserRegisterEntity) map.get("registerEntity");
            Observable observable1 = Observable.fromArray(RetrofitManager.getInstance().getRetrofit().create(AppApi.class).register(registerEntity)).flatMap((Function) Functions.identity());
            Observable<Boolean> observable2 = Observable.create(new ObservableOnSubscribe<Boolean>() {

                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                    emitter.onNext(XmppManager.getInstance().getXmppUserManager().createAccount(registerEntity.username, registerEntity.pwd));
                }
            });
            Observable ob = Observable.merge(observable1, observable2);
            return ob;
        }
        return null;
    }

    @Override
    public void destory() {

    }
}
