package com.example.map_im.mvp.contract;

import com.example.basemodel.mvp.model.IModel;
import com.example.basemodel.mvp.view.IView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * 登陆注册契约接口
 */
public interface ILoginRegisterContract {

    interface ILoginRegisterView extends IView{
        void response(Object o);
    }

    interface ILoginRegisterModel extends IModel{
        Observable<Object> request(Map<String,Object> map);
    }

}
