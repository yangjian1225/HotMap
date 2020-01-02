package com.example.map_im;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonmodel.deviceinfo.AppInfoConfig;
import com.example.commonmodel.deviceinfo.DeviceInfoConfig;

public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //分割DEX
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //ARouter初始化
        initARouter();
        //设备信息初始化
        DeviceInfoConfig.getInstance().init(this);
        //应用信息初始化
        AppInfoConfig.getInstance().init(this);

    }

    /**
     * ARouter初始化
     */
    private void initARouter() {
        if (isApkDebug(this)){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    /**
     * 是否为debug
     * @param context
     * @return
     */
    public static boolean isApkDebug(Context context){
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            return (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }catch (Exception e){
            return false;
        }
    }
}
