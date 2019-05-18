package com.wangyuelin.sender.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wangyuelin.sender.MainActivity;
import com.wangyuelin.sender.R;
import com.wangyuelin.sender.util.Constant;
import com.wangyuelin.sender.util.SPUtils;
import com.wangyuelin.sender.widgtes.WaveViewByBezier;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.wave)
    WaveViewByBezier wave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示设置
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        wave.startAnimation();

        new Handler().postDelayed(() -> {
            check();

        }, 3 * 1000);
    }

    /**
     * 检查是否登录
     */
    private void check() {
        //检查是否登录
        String token = SPUtils.getInstance().getString(Constant.SpKey.TOKEN, "");

        Intent intent = null;
        if (TextUtils.isEmpty(token)) {//没有登录
            intent = new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        } else {//已经登录
            intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wave != null) {
            wave.stopAnimation();
            wave = null;
        }

    }
}
