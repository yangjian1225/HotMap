package com.example.basemodel.mvp.view;

public interface IActivity {
    int layoutID();
    void initView();
    void initData();
    void setupDagger();
}
