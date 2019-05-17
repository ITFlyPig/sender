package com.wangyuelin.sender.net;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * 这个类的作用：因为Okgo自带的回调方法不是想要的，封装一下改为想要的返回形式
 * @param <T>
 */
public abstract class ChCallBack<T> extends AbsCallback<T> {
    @Override
    public void onSuccess(Response<T> response) {
        if (response != null) {
            onSuccessBiz(response.body());
        } else {
            onErrorBiz(-1, "解析数据出错");
        }
    }

    @Override
    public void onError(Response<T> response) {
        onErrorBiz(response != null ? response.code() : 500, response.getException().getLocalizedMessage());
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        onStartBiz(request.getUrl());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        onFinishBiz();
    }

    public abstract void onSuccessBiz(T t);//直接返回传递进去的数据格式

    public abstract void onErrorBiz(int code, String msg); //网络请求错误

    public  void onFinishBiz() {};   //网络请求结束

    public  void onStartBiz(String url){}; //网络请求开始

}
