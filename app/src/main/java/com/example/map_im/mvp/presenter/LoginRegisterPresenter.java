package com.example.map_im.mvp.presenter;

import com.example.basemodel.mvp.presenter.BasePresenter;
import com.example.commonmodel.rx.BaseObservable;
import com.example.commonmodel.rx.BaseObserver;
import com.example.map_im.mvp.contract.ILoginRegisterContract;

import java.util.Map;

import javax.inject.Inject;

/**
 * 登陆注册功能实现的presenter类
 */
public class LoginRegisterPresenter extends BasePresenter<ILoginRegisterContract.ILoginRegisterModel, ILoginRegisterContract.ILoginRegisterView> {

    @Inject
    public LoginRegisterPresenter(ILoginRegisterContract.ILoginRegisterModel mModel, ILoginRegisterContract.ILoginRegisterView mView) {
        super(mModel, mView);
    }

    public void request(Map<String,Object> map){
        BaseObservable.doObserve(mModel.request(map),new BaseObserver<Object>(){

            @Override
            public void onNext(Object o) {
                super.onNext(o);
                mView.response(o);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showMessage("请求出错："+e.getMessage());
                mView.hideLoading();
            }

        },mView.getOwner());
    }

}
