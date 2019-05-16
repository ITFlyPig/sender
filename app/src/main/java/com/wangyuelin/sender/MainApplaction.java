package com.wangyuelin.sender;

import android.app.Application;

import com.lzy.okgo.OkGo;

public class MainApplaction extends Application {
    private static MainApplaction applaction;

    @Override
    public void onCreate() {
        super.onCreate();
        applaction = this;
        OkGo.getInstance().init(this);
    }

    public static MainApplaction getApplaction() {
        return applaction;
    }
}
