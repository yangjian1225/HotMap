package com.example.commonmodel.deviceinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class DeviceInfoConfig {

    private static volatile DeviceInfoConfig deviceInfoConfig;

    private DeviceInfoConfig() {
    }

    public static DeviceInfoConfig getInstance(){
        if (deviceInfoConfig  == null){
            synchronized (DeviceInfoConfig.class){
                if (deviceInfoConfig == null){
                    deviceInfoConfig = new DeviceInfoConfig();
                }
            }
        }
        return deviceInfoConfig;
    }

    private Context context;

    public void init(Context context){
        this.context = context;
    }

    /**
     * 获取厂商信息
     * @return
     */
    public String getManufacturer(){
        return Build.MANUFACTURER;
    }

    /**
     * 获取机型信息
     * @return
     */
    public String getModel(){
        return Build.MODEL;
    }

    /**
     * android系统版本
     * @return
     */
    public String getOSVersion(){
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    /**
     * 获取设备号  包括：GMS - IMEI   CDMA - MEID
     * @return
     */
    public String  getDeviceID(){
        if (Build.VERSION.SDK_INT < 23){


        }else {
            String deviceID = getIMEI();
            if (TextUtils.isEmpty(deviceID)){
                deviceID = getMEID();
            }
            return deviceID;
        }

        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String getMEID() {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String meid = telephonyManager.getDeviceId(TelephonyManager.PHONE_TYPE_CDMA);
        return meid;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId(TelephonyManager.PHONE_TYPE_GSM);
        return imei;
    }

//    /**
//     * 获取utdid
//     * @return
//     */
//    public String getUtdid(){
//        return UMUtils.getUTDID(context);
//    }
//
//    /**
//     * 获取物理地址
//     * @return
//     */
//    public String getMacAddress(){
//        return UMUtils.getMac(context);
//    }
//
//    /**
//     * 获取屏幕分辨率
//     * @return
//     */
//    public String getDisplay(){
//        return UMUtils.getDisplayResolution(context);
//    }

}
