package com.example.basemodel.mvp.presenter;

import com.example.basemodel.mvp.model.IModel;
import com.example.basemodel.mvp.view.IView;

public abstract class BasePresenter<M extends IModel,V extends IView> implements IPresenter {

    protected M mModel;
    protected V mView;

    public BasePresenter(M mModel, V mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void destory() {
        if (mModel!=null) {
            mModel.destory();
        }
        mModel = null;
        mView = null;
    }
}
