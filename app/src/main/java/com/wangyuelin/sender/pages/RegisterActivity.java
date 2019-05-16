package com.wangyuelin.sender.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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
import com.wangyuelin.sender.util.RegexUtils;
import com.wangyuelin.sender.util.Server;
import com.wangyuelin.sender.util.Urls;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.etl_phone)
    TextInputLayout etlPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.etl_password)
    TextInputLayout etlPassword;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.etl_code)
    TextInputLayout etlCode;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.tv_register)
    TextView tvRegister;


    TitleHelper titleHelper;
    StatusUIHelper statusUIHelper;
    CountDownTimer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleHelper = new TitleHelper();
        titleHelper.setContentView(R.layout.activity_register, this);
        titleHelper.getIvBack().setOnClickListener(this);
        titleHelper.getTvTitle().setText("注册");
        ButterKnife.bind(this, titleHelper.getContent());
        tvRegister.setOnClickListener(this);
        tvTimer.setOnClickListener(this);
        statusUIHelper = new StatusUIHelper();
    }

    /**
     * 打开页面
     *
     * @param context
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_register:

                register(etPhone.getText().toString(), etPassword.getText().toString(), etCode.getText().toString());
                break;
            case R.id.tv_timer:
                sendCode(etPhone.getText().toString());
                break;
        }
    }

    /**
     * 校验输入的合法性
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    private boolean verification(String phone, String password, String code) {
        String errorMsg = null;
        if (TextUtils.isEmpty(phone)) {
            errorMsg = "手机号不能为空";
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            errorMsg = "请输入正确的手机号";
        }

        if (TextUtils.isEmpty(password)) {
            errorMsg = "密码不能为空";
        }
        if (TextUtils.isEmpty(code)) {
            errorMsg = "验证码不能为空";
        }

        if (!TextUtils.isEmpty(errorMsg)) {
            statusUIHelper.showToast(errorMsg);
            return false;
        }
        return true;

    }

    /**
     * 注册
     * @param phone
     * @param password
     * @param code
     */
    public void register(String phone, String password, String code) {
        if (!verification(phone,password, code)) {
            return;
        }

        OkGo.<FastBaseResp<Map<String, String>>>get(Server.HOST + Urls.REGISTER)
                .params("phone", phone)
                .params("password", password)
                .params("code", code)
                .execute(new FastJsonCallback<FastBaseResp<Map<String, String>>>() {
                    @Override
                    public void onSuccessBiz(FastBaseResp<Map<String, String>> resp) {
                        if (resp.res != null) {
                            if (TextUtils.equals(resp.res.get("loginSuccess"), String.valueOf(1))) {//登陆成功
                                String tip = resp.res.get("tip");
                                statusUIHelper.showToast(tip);
                                String token = resp.res.get("token");//获取token
                                return;
                            }
                        }

                    }

                    @Override
                    public void onErrorBiz(int code, String msg) {
                        statusUIHelper.showToast(msg);

                    }

                    @Override
                    public void onStartBiz(String url) {
                        super.onStartBiz(url);
                        statusUIHelper.showLoading(getSupportFragmentManager());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        statusUIHelper.dismissLoading();
                    }
                });
    }

    /**
     * 发送验证码
     * @param phone
     */
    private void  sendCode(String phone) {
        String errorMsg = null;
        if (TextUtils.isEmpty(phone)) {
            errorMsg = "手机号不能为空";
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            errorMsg = "请输入正确的手机号";
        }
        if (!TextUtils.isEmpty(errorMsg)) {
            statusUIHelper.showToast(errorMsg);
            return;
        }

        OkGo.<FastBaseResp<String>>get(Server.HOST + Urls.SEND)
                .params("phone", phone)
                .execute(new FastJsonCallback<FastBaseResp<String>>() {
                    @Override
                    public void onSuccessBiz(FastBaseResp<String> resp) {
                        //发送验证码成功
                        if (resp.isSuccessful()) {
                            statusUIHelper.showToast(resp.message);
                            timer();
                        }
                    }

                    @Override
                    public void onErrorBiz(int code, String msg) {
                        statusUIHelper.showToast(msg);

                    }

                    @Override
                    public void onStartBiz(String url) {
                        super.onStartBiz(url);
                        statusUIHelper.showLoading(getSupportFragmentManager());
                    }

                    @Override
                    public void onFinishBiz() {
                        super.onFinishBiz();
                        statusUIHelper.dismissLoading();

                    }

                });
    }


    /**
     * 开始倒计时
     */
    private void timer() {
        if (mTimer == null) {
            mTimer = new CountDownTimer(30 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int second = (int) (millisUntilFinished / 1000);
                    tvTimer.setText("剩余 " + second + " 秒");
                }

                @Override
                public void onFinish() {
                    tvTimer.setEnabled(true);
                    tvTimer.setText("发送验证码");
                    tvTimer.setTextColor(getResources().getColor(R.color.color_btn_text_normal));

                }
            };

        }

        mTimer.cancel();
        mTimer.start();
        tvTimer.setEnabled(false);
        tvTimer.setTextColor(getResources().getColor(R.color.text_color_gray));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
