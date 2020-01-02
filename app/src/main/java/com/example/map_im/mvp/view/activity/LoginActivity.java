package com.example.map_im.mvp.view.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.basemodel.domain.BaseCode;
import com.example.basemodel.mvp.model.BaseEntity;
import com.example.basemodel.mvp.view.BaseMVPActivity;
import com.example.commonmodel.utils.LogUtils;
import com.example.map_im.R;
import com.example.map_im.db.UserSQLite;
import com.example.map_im.di.component.DaggerLoginRegisterComponent;
import com.example.map_im.di.modules.LoginRegisterModules;
import com.example.map_im.domain.AppCode;
import com.example.map_im.mvp.contract.ILoginRegisterContract;
import com.example.map_im.mvp.model.entity.request.ReqUserLoginEntity;
import com.example.map_im.mvp.model.entity.response.ResUserInfoEntity;
import com.example.map_im.mvp.presenter.LoginRegisterPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMVPActivity<LoginRegisterPresenter> implements ILoginRegisterContract.ILoginRegisterView {

    /**
     * 设置布局文件
     * @return
     */
    @Override
    public int layoutID() {
        return R.layout.activity_login;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    @BindView(R.id.app_login_btn)
    Button btnLogin;
    @BindView(R.id.app_register_btn)
    Button btnRegister;
    @BindView(R.id.app_user_name_edt)
    EditText editName;
    @BindView(R.id.app_user_pwd_edt)
    EditText editPwd;
    @BindView(R.id.app_forger_pwd_tv)
    TextView tvFP;

    /**
     * dagger注入
     */
    @Override
    public void setupDagger() {
        DaggerLoginRegisterComponent.builder().loginRegisterModules(new LoginRegisterModules(this)).build().injectLogin(this);
    }

    //TODO im的登陆是否成功
    private boolean isSuccess;
    //TODO 登陆的响应体
    private ResUserInfoEntity userInfoEntity;
    //TODO 临时变量
    private int temp = 0;

    /**
     * 响应体
     * @param o
     */
    @Override
    public void response(Object o) {
        if (o instanceof Boolean){
            isSuccess = (Boolean) o;
            temp++;
        }else if (o instanceof BaseEntity){
            userInfoEntity = (ResUserInfoEntity) o;
            temp++;
        }
        if (temp == 2){
            if (isSuccess && userInfoEntity.code == 200){
                ARouter.getInstance().build(BaseCode.AROUTER_MAIN_MODULE_MAPACTIVITY).navigation();
                LoginActivity.this.finish();
            }
            Toast.makeText(this, userInfoEntity.msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 请求出错
     * @param msg
     */
    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        LogUtils.e(msg);
    }

    /**
     * rx生命周期管理所需的LifecycleOwner
     * @return
     */
    @Override
    public LifecycleOwner getOwner() {
        return this;
    }

    /**
     * 将用户添加到数据库，并在sp中写入当前登陆的用户
     */
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onStop() {
        super.onStop();
        if (userInfoEntity != null){
            if (userInfoEntity.code == 200){
                long l = UserSQLite.getInstance(this).addUser(userInfoEntity.data);
                SharedPreferences sp = getSharedPreferences(BaseCode.SP_LOGIN, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(BaseCode.SP_NOW_LOGIN_USERNAME_KEY,userInfoEntity.data.username+"&"+userInfoEntity.data.usercode);
                edit.apply();
                edit.commit();
            }
        }
    }

    @OnClick({R.id.app_login_btn,R.id.app_register_btn,R.id.app_forger_pwd_tv})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.app_login_btn){
            String name = editName.getText().toString().trim();
            String pwd = editPwd.getText().toString().trim();
            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
                Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String,Object> map = new HashMap<>();
            map.put(AppCode.TAG,AppCode.login);
            map.put("loginEntity",new ReqUserLoginEntity(name,pwd));
            mPresenter.request(map);
        }else if (id == R.id.app_register_btn){
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }else if (id == R.id.app_forger_pwd_tv){

        }
    }
}
