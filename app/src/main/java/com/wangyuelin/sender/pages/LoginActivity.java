package com.wangyuelin.sender.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.lzy.okgo.OkGo;
import com.wangyuelin.sender.R;
import com.wangyuelin.sender.helper.StatusUIHelper;
import com.wangyuelin.sender.helper.TitleHelper;
import com.wangyuelin.sender.net.FastBaseResp;
import com.wangyuelin.sender.net.FastJsonCallback;
import com.wangyuelin.sender.util.Server;
import com.wangyuelin.sender.util.Urls;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.etl_phone)
    TextInputLayout etlPhone;
    @BindView(R.id.etl_password)
    TextInputLayout etlPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    TitleHelper titleHelper;
    StatusUIHelper statusUIHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleHelper = new TitleHelper();
        titleHelper.setContentView(R.layout.activity_login, this);
        titleHelper.getIvBack().setOnClickListener(this);
        titleHelper.getTvTitle().setText("登陆");
        ButterKnife.bind(this, titleHelper.getContent());
        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        statusUIHelper = new StatusUIHelper();

    }

    /**
     * 打开页面
     *
     * @param context
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                RegisterActivity.open(this);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_login:
                login(etPhone.getText().toString(), etPassword.getText().toString());
                break;
        }

    }

    private void login(String phone, String password) {
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            statusUIHelper.showToast("用户名和密码不能为空");
            return;
        }


        OkGo.<FastBaseResp<Map<String, String>>>get(Server.HOST + Urls.LOGIN)
                .params("phone", phone)
                .params("password", password)
                .execute(new FastJsonCallback<FastBaseResp<Map<String, String>>>() {

                    @Override
                    public void onSuccessBiz(FastBaseResp<Map<String, String>> resp) {
                        if (resp.code == 0) {//登陆成功

                            statusUIHelper.showToast("登陆成功");
                        } else {//登陆失败
                            if (resp.res != null) {
                                statusUIHelper.showToast(resp.res.get("tip"));
                            }
                        }


                    }

                    @Override
                    public void onErrorBiz(int code, String msg) {


                    }

                    @Override
                    public void onStartBiz(String url) {
                        statusUIHelper.showLoading(getSupportFragmentManager());
                    }


                    @Override
                    public void onFinishBiz() {
                        statusUIHelper.dismissLoading();

                    }
                });
    }



}
