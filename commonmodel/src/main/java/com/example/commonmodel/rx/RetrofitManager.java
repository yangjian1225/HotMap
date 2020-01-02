package com.example.commonmodel.rx;

import com.example.commonmodel.deviceinfo.AppInfoConfig;
import com.example.commonmodel.deviceinfo.DeviceInfoConfig;
import com.example.commonmodel.domain.CommonCode;
import com.example.commonmodel.entity.CommonApi;
import com.example.commonmodel.entity.TokenEntity;
import com.example.commonmodel.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static volatile RetrofitManager retrofitManager;

    private RetrofitManager() {
    }

    public static RetrofitManager getInstance(){
        if (retrofitManager == null){
            synchronized (RetrofitManager.class){
                if (retrofitManager == null){
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    private Retrofit retrofit;

    public Retrofit getRetrofit(){
        if (retrofit == null){
            build();
        }
        return retrofit;
    }

    private void build() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addNetworkInterceptor(initLogInterceptor())
                .addInterceptor(createRequestInterceptor())
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES);

        retrofit = new Retrofit.Builder().baseUrl(CommonCode.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okBuilder.build())
                .build();
    }

    //log拦截器
    private HttpLoggingInterceptor initLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    //自定义拦截器
    private Interceptor createRequestInterceptor(){
        return new Interceptor(){

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                Request.Builder builder = request.newBuilder()
                        .addHeader("Content-Type", "application-json")
                        .addHeader("charset", "utf-8")
                        .addHeader("manufacturer", DeviceInfoConfig.getInstance().getManufacturer())
                        .addHeader("model",DeviceInfoConfig.getInstance().getModel())
                        .addHeader("deviceid",DeviceInfoConfig.getInstance().getDeviceID())
                        .addHeader("packagename", AppInfoConfig.getInstance().getPackageName())
                        .addHeader("versioncode",AppInfoConfig.getInstance().getVersionCode())
                        .addHeader("versionname",AppInfoConfig.getInstance().getVersionName())
                        .addHeader("osversion",DeviceInfoConfig.getInstance().getOSVersion());
                if (response.code() == 401){
                    builder.addHeader("Authorization", "bearer " + requestToken());
                }
                return chain.proceed(builder.build());
            }

        };
    }

    /**
     * 获取token
     * @return
     */
    private String requestToken() {
        HashMap<String,String> map = new HashMap<>();
        map.put("grant_type","password");
        map.put("username", CommonCode.AUTH_CODE);
        map.put("password","");
        try {
            TokenEntity body = RetrofitManager.getInstance().getRetrofit().create(CommonApi.class).getToken(map).execute().body();
            return body.access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
