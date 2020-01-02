package com.example.map_im.di.modules;

import com.example.map_im.mvp.contract.ILoginRegisterContract;
import com.example.map_im.mvp.model.LoginRegisterModel;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginRegisterModules {

    private ILoginRegisterContract.ILoginRegisterView view;

    public LoginRegisterModules(ILoginRegisterContract.ILoginRegisterView view) {
        this.view = view;
    }

    @Provides
    public ILoginRegisterContract.ILoginRegisterView provideView(){
        return view;
    }

    @Provides
    public ILoginRegisterContract.ILoginRegisterModel provideModel(LoginRegisterModel model){
        return model;
    }
}
