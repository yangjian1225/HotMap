package com.example.commonmodel.entity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommonApi {

    @FormUrlEncoded
    @POST("/token")
    Call<TokenEntity> getToken(@FieldMap HashMap<String, String> map);
}
