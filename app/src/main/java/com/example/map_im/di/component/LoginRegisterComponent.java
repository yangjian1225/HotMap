package com.example.map_im.di.component;

import com.example.map_im.di.modules.LoginRegisterModules;
import com.example.map_im.mvp.view.activity.LoginActivity;
import com.example.map_im.mvp.view.activity.RegisterActivity;

import dagger.Component;

@Component(modules = LoginRegisterModules.class)
public interface LoginRegisterComponent {

    void injectLogin(LoginActivity loginActivity);
    void injectRegister(RegisterActivity registerActivity);
}
