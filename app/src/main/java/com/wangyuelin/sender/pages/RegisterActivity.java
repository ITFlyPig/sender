package com.wangyuelin.sender.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.wangyuelin.sender.R;
import com.wangyuelin.sender.helper.TitleHelper;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleHelper = new TitleHelper();
        titleHelper.setContentView(R.layout.activity_register, this);
        titleHelper.getIvBack().setOnClickListener(this);
        titleHelper.getTvTitle().setText("注册");
        ButterKnife.bind(this, titleHelper.getContent());
        tvRegister.setOnClickListener(this);

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
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
        }

    }
}
