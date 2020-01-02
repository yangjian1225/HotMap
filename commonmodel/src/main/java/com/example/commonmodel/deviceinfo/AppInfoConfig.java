package com.example.commonmodel.deviceinfo;

import android.content.Context;
import android.content.pm.PackageManager;

public class AppInfoConfig {

    private static volatile AppInfoConfig appInfoConfig;

    private AppInfoConfig() {
    }

    public static AppInfoConfig getInstance(){
        if (appInfoConfig == null){
            synchronized (AppInfoConfig.class){
                if (appInfoConfig == null){
                    appInfoConfig = new AppInfoConfig();
                }
            }
        }
        return appInfoConfig;
    }

    private Context context;

    public void init(Context context){
        this.context = context;
    }

    public String getPackageName(){
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(),0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getVersionCode(){
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(),0).versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getVersionName(){
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}
