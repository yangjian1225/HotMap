package com.example.map_im.mvp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baweigame.xmpplibrary.XmppManager;
import com.example.basemodel.domain.BaseCode;
import com.example.basemodel.mvp.view.BaseActivity;
import com.example.commonmodel.utils.LogUtils;
import com.example.map_im.db.UserSQLite;
import com.example.map_im.mvp.view.adapter.MyPagerAdapter;
import com.example.map_im.R;
import com.example.map_im.service.MyServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 起平页
 * @author yangjian
 * @date 2019/12/27
 */
public class MainActivity extends BaseActivity {

    ViewPager viewPager;
    List<View> mList;
    MyPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, MyServer.class));
        SharedPreferences firstSp = getSharedPreferences(BaseCode.SP_IS_FIRST_OPEN, MODE_PRIVATE);
        //判断是否是第一次登陆
        if (firstSp.getBoolean(BaseCode.SP_IS_FIRST_OPEN_KEY, true)){
            initPager();
            SharedPreferences.Editor edit = firstSp.edit();
            edit.putBoolean(BaseCode.SP_IS_FIRST_OPEN_KEY,false);
            edit.apply();
            LogUtils.e("firstSp");
        } else {
            SharedPreferences loginSp = getSharedPreferences(BaseCode.SP_LOGIN, MODE_PRIVATE);
            if (loginSp.getBoolean(BaseCode.SP_IS_LOGIN_KEY,false)){
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }else {
                new LoginThread().start();
                ARouter.getInstance().build(BaseCode.AROUTER_MAIN_MODULE_MAPACTIVITY).navigation();
            }
            MainActivity.this.finish();
        }

    }

    private void initPager() {
        viewPager = findViewById(R.id.app_viewpager);
        mList = new ArrayList<>();
        View view1 = View.inflate(this,R.layout.item_one,null);
        View view2 = View.inflate(this,R.layout.item_two,null);
        View view3 = View.inflate(this,R.layout.item_three,null);
        View view4 = View.inflate(this,R.layout.item_four,null);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        mList.add(view4);
        pagerAdapter = new MyPagerAdapter(mList);
        viewPager.setAdapter(pagerAdapter);
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }

    class LoginThread extends Thread{
        @Override
        public void run() {
            super.run();
            String name = getSharedPreferences(BaseCode.SP_LOGIN,MODE_PRIVATE).getString(BaseCode.SP_NOW_LOGIN_USERNAME_KEY,null);
            String pwd = UserSQLite.getInstance(MainActivity.this).getPwdByName(name);
            XmppManager.getInstance().getXmppUserManager().login(name, pwd);

        }
    }
}
