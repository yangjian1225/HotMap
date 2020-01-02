package com.example.map_im.mvp.view.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.basemodel.domain.BaseCode;
import com.example.basemodel.mvp.model.BaseEntity;
import com.example.basemodel.mvp.view.BaseMVPActivity;
import com.example.commonmodel.utils.LogUtils;
import com.example.map_im.R;
import com.example.map_im.di.component.DaggerLoginRegisterComponent;
import com.example.map_im.di.modules.LoginRegisterModules;
import com.example.map_im.domain.AppCode;
import com.example.map_im.mvp.contract.ILoginRegisterContract;
import com.example.map_im.mvp.model.entity.request.ReqUserLoginEntity;
import com.example.map_im.mvp.model.entity.request.ReqUserRegisterEntity;
import com.example.map_im.mvp.model.entity.response.ResUserInfoEntity;
import com.example.map_im.mvp.presenter.LoginRegisterPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseMVPActivity<LoginRegisterPresenter> implements ILoginRegisterContract.ILoginRegisterView {

    /**
     * 设置布局文件
     * @return
     */
    @Override
    public int layoutID() {
        return R.layout.activity_register;
    }

    @BindView(R.id.app_reg_user_name_edt)
    EditText editName;
    @BindView(R.id.app_reg_user_pwd_edt)
    EditText editPwd;
    @BindView(R.id.app_reg_register_btn)
    Button btnRegister;

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

    /**
     * dagger注入
     */
    @Override
    public void setupDagger() {
        DaggerLoginRegisterComponent.builder().loginRegisterModules(new LoginRegisterModules(this)).build().injectRegister(this);
    }

    //TODO im的注册是否成功
    private boolean isSuccess;
    //TODO 注册的响应体
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
                RegisterActivity.this.finish();
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

    @OnClick({R.id.app_reg_register_btn})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.app_reg_register_btn){
            String name = editName.getText().toString().trim();
            String pwd = editPwd.getText().toString().trim();
            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
                Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String,Object> map = new HashMap<>();
            map.put(AppCode.TAG,AppCode.register);
            map.put("registerEntity",new ReqUserRegisterEntity(name,pwd,null,null,null));
            mPresenter.request(map);
        }
    }
}
