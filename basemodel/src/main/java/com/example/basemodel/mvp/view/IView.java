package com.example.basemodel.mvp.view;

import android.arch.lifecycle.LifecycleOwner;


public interface IView {

    void showLoading();
    void hideLoading();
    void showMessage(String msg);
    LifecycleOwner getOwner();
}
